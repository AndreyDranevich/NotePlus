package com.example.andrew.noteplus.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.andrew.noteplus.Note;
import com.example.andrew.noteplus.NoteLab;
import com.example.andrew.noteplus.activity.NotePagerActivity;


public class InsertNoteTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Note note;
    final private String LOG_TAG = "myLogs";

    public InsertNoteTask(Context mContext, Note note) {
        this.mContext = mContext;
        this.note = note;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        NoteLab.get(mContext).addNote(note);
        Log.d(LOG_TAG, "Inserted in another Thread");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Intent intent = NotePagerActivity.newIntent(mContext, note.getId());
        mContext.startActivity(intent);
    }
}
