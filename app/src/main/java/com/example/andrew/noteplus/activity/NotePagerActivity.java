package com.example.andrew.noteplus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.andrew.noteplus.Note;
import com.example.andrew.noteplus.fragment.NoteFragment;
import com.example.andrew.noteplus.NoteLab;
import com.example.andrew.noteplus.R;

import java.util.List;
import java.util.UUID;

public class NotePagerActivity extends AppCompatActivity {
    private static final String EXTRA_NOTE_ID = "com.example.android.noteplus.note_id";
    private static UUID noteID;
    private ViewPager mViewPager;
    private List<Note> mNotes;


    public static Intent newIntent(Context packageContext, UUID noteId) {
        Intent intent = new Intent(packageContext, NotePagerActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        noteID = noteId;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pager);

        mViewPager = findViewById(R.id.note_view_pager);
        mNotes = NoteLab.get(this).getNotes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Note note = mNotes.get(position);
                return NoteFragment.newInstance(note.getId());
            }

            @Override
            public int getCount() {
                return mNotes.size();
            }
        });
        for (int i = 0; i < mNotes.size(); i++) {
            if (mNotes.get(i).getId().equals(noteID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
//        if(id == R.id.menu_item_new_colour){
//           //finish();
//        }

        return true;
    }
}
