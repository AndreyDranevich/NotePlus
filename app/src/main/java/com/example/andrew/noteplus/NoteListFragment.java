package com.example.andrew.noteplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class NoteListFragment extends Fragment {
    private RecyclerView mNoteRecyclerView;
    private CrimeAdapter mAdapter;
    private static final int REQUEST_CRIME = 1;
   // private int holderPosition = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        mNoteRecyclerView = view.findViewById(R.id.note_recycler_view);
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //GridLayoutManager for Grid
        updateUI();
        return view;
    }

    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        NoteLab noteLab = NoteLab.get(getActivity());
        List<Note> notes = noteLab.getNotes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(notes);
            mNoteRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Note mNote;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.list_item_note_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_note_date_text_view);
            mSolvedCheckBox = itemView.findViewById(R.id.list_item_note_solved_check_box);
        }

        public void bindCrime(Note note) {
            mNote = note;
            mTitleTextView.setText(mNote.getTitle());
            mDateTextView.setText(android.text.format.DateFormat.format("EEE, dd MMMM yyyy HH:mm", mNote.getDate()));
            mSolvedCheckBox.setChecked(mNote.isSolved());
        }

        @Override
        public void onClick(View v) {
            //holderPosition = this.getAdapterPosition();
            Intent intent = NotePagerActivity.newIntent(getActivity(), mNote.getId());
            startActivityForResult(intent, REQUEST_CRIME);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CRIME) {
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
    }

}