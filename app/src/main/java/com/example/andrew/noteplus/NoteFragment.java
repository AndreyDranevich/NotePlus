package com.example.andrew.noteplus;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

//представление, отображает Note. Контроллер
public class NoteFragment extends Fragment {
    private static final String ARG_CRIME_ID = "note_id";
    private Note mNote;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static NoteFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mNote = NoteLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);

        mTitleField = v.findViewById(R.id.note_title);
        mTitleField.setText(mNote.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mNote.setTitle(c.toString());
            }

            @Override
            public void afterTextChanged(Editable c) {
            }
        });

        mDateButton = v.findViewById(R.id.note_date);
        mDateButton.setText(android.text.format.DateFormat.format("EEE, dd MMMM yyyy HH:mm", mNote.getDate()));
        mDateButton.setEnabled(false);

        mSolvedCheckBox = v.findViewById(R.id.note_solved);
        mSolvedCheckBox.setChecked(mNote.isSolved());


        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mNote.setSolved(b);
            }
        });

        return v;
    }

    public void returnResult() {
        getActivity().setResult(Activity.RESULT_OK, null);
    }

}
