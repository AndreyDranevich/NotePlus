package com.example.andrew.noteplus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.andrew.noteplus.Note;
import com.example.andrew.noteplus.NoteLab;
import com.example.andrew.noteplus.R;
import com.example.andrew.noteplus.activity.NotePagerActivity;
import com.example.andrew.noteplus.activity.SettingsActivity;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.List;

public class NoteListFragment extends Fragment {
    private RecyclerView mNoteRecyclerView;
    private CrimeAdapter mAdapter;
    private static final int REQUEST_NOTE = 1;
    // private int holderPosition = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        mNoteRecyclerView = view.findViewById(R.id.note_recycler_view);
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //GridLayoutManager for Grid
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Note note = new Note();
                NoteLab.get(getActivity()).addNote(note);

                Intent intent = NotePagerActivity.newIntent(getActivity(), note.getId());
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Intent intent1 = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateUI() {
        NoteLab noteLab = NoteLab.get(getActivity());
        List<Note> notes = noteLab.getNotes();
        deleteCard();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(notes);
            mNoteRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNotes(notes);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void deleteCard() {
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mNoteRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Toast.makeText(getActivity(), R.string.delete_item, Toast.LENGTH_SHORT).show();
                                    NoteLab.get(getActivity()).deleteNote(mAdapter.getNote(position));
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Toast.makeText(getActivity(), R.string.delete_item, Toast.LENGTH_SHORT).show();
                                    NoteLab.get(getActivity()).deleteNote(mAdapter.getNote(position));
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        mNoteRecyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mSummaryTextView;
        private Note mNote;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.list_item_note_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_note_date_text_view);
            mSummaryTextView = itemView.findViewById(R.id.list_item_note_summary_text_view);
        }

        public void bindCrime(Note note) {
            mNote = note;
            mTitleTextView.setText(mNote.getTitle());
            mSummaryTextView.setText(mNote.getSummary());
            mDateTextView.setText(android.text.format.DateFormat.format("EEE, dd MMMM yyyy", mNote.getDate()));
        }

        @Override
        public void onClick(View v) {
            //holderPosition = this.getAdapterPosition();
            Intent intent = NotePagerActivity.newIntent(getActivity(), mNote.getId());
            startActivityForResult(intent, REQUEST_NOTE);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Note> mNotes;

        public CrimeAdapter(List<Note> notes) {
            mNotes = notes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_note, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bindCrime(note);
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }

        public void setNotes(List<Note> notes) {
            mNotes = notes;
        }

        public Note getNote(int position) {
            return mNotes.remove(position);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_NOTE) {
        }
    }
}