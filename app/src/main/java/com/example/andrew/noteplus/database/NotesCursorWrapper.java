package com.example.andrew.noteplus.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.andrew.noteplus.Note;
import com.example.andrew.noteplus.database.NoteDbSchema.NoteTable;

import java.util.Date;
import java.util.UUID;

public class NotesCursorWrapper extends CursorWrapper {
    public NotesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String uuidString = getString(getColumnIndex(NoteTable.Cols.UUID));
        String title = getString(getColumnIndex(NoteTable.Cols.TITLE));
        String summary = getString(getColumnIndex(NoteTable.Cols.SUMMARY));
        long date = getLong(getColumnIndex(NoteTable.Cols.DATE));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setSummary(summary);
        note.setDate(new Date(date));

        return note;
    }
}
