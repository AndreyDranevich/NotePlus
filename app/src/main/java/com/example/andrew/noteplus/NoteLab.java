package com.example.andrew.noteplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.andrew.noteplus.database.NoteBaseHelper;
import com.example.andrew.noteplus.database.NoteDbSchema;
import com.example.andrew.noteplus.database.NoteDbSchema.NoteTable;
import com.example.andrew.noteplus.database.NotesCursorWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


//синглетный класс
public class NoteLab {
    final String LOG_TAG = "myLogs";

    private static NoteLab sNoteLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static NoteLab get(Context context) {
        if (sNoteLab == null) {
            sNoteLab = new NoteLab(context);
        }
        return sNoteLab;
    }

    private NoteLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new NoteBaseHelper(mContext).getWritableDatabase();
        createFiveNotes();
    }

    public void createFiveNotes() {
        NotesCursorWrapper cursor = queryNotes(null, null);
        if (!cursor.moveToFirst()) {
            for (int i = 0; i < 5; i++) {
                Note note = new Note();
                note.setTitle("hello #" + i);
                note.setDate(new Date());
                addNote(note);
            }
        }
        cursor.close();
    }

    public void addNote(Note note) {
        ContentValues values = getContentValues(note);
        mDatabase.insert(NoteTable.NAME, null, values);
    }

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();

        NotesCursorWrapper cursor = queryNotes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return notes;
    }

    public Note getNote(UUID id) {
        NotesCursorWrapper cursor = queryNotes(
                NoteTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

    public void deleteNote(Note note) {
        String uuidString = note.getId().toString();
        Log.d(LOG_TAG, "Delete note id = " + uuidString);
        mDatabase.delete(NoteTable.NAME, NoteTable.Cols.UUID + " = ?", new String[]{uuidString});

    }

    public void updateNote(Note note) {
        String uuidString = note.getId().toString();
        ContentValues values = getContentValues(note);
        mDatabase.update(NoteTable.NAME, values, NoteTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    private static ContentValues getContentValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteTable.Cols.UUID, note.getId().toString());
        values.put(NoteTable.Cols.TITLE, note.getTitle());
        values.put(NoteTable.Cols.SUMMARY, note.getSummary());
        values.put(NoteTable.Cols.DATE, note.getDate().getTime());

        return values;
    }

    private NotesCursorWrapper queryNotes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new NotesCursorWrapper(cursor);
    }

//    private class ReadFromDB extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//        }
//    }
//
//    private class WriteInDB extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//        }
//    }
}
