package com.example.andrew.noteplus;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class NoteActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.example.android.noteplus.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, NoteActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return NoteFragment.newInstance(crimeId);
    }

}
