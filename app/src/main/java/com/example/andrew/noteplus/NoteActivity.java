package com.example.andrew.noteplus;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;
//need to delete
public class NoteActivity extends SingleFragmentActivity {

    private static final String EXTRA_NOTE_ID = "com.example.android.noteplus.note_id";

    public static Intent newIntent(Context packageContext, UUID noteId) {
        Intent intent = new Intent(packageContext, NoteActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        return intent;
    }

    protected Fragment createFragment() {
        UUID noteId = (UUID) getIntent().getSerializableExtra(EXTRA_NOTE_ID);
        return NoteFragment.newInstance(noteId);
    }

}
