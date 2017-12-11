package com.example.andrew.noteplus.activity;

import android.support.v4.app.Fragment;

import com.example.andrew.noteplus.fragment.NoteListFragment;

public class NoteListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new NoteListFragment();
    }
}
