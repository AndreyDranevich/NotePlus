package com.example.andrew.noteplus.task;

import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;

import com.example.andrew.noteplus.Note;
import com.example.andrew.noteplus.NoteLab;


public class UpdateNoteTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Note note;
    final private String LOG_TAG = "myLogs";

    public UpdateNoteTask(Context mContext, Note note) {
        this.mContext = mContext;
        this.note = note;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        NoteLab.get(mContext)
                .updateNote(note);
        Log.d(LOG_TAG, "Updated in another Thread");
        return null;
    }

}
