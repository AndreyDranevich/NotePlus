package com.example.andrew.noteplus;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

//представление, отображает Note. Контроллер
public class NoteFragment extends Fragment {
    private static final String ARG_NOTE_ID = "note_id";
    private static final String DIALOG_COLOUR = "DialogColour";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_COLOUR = 0;

    private Note mNote;
    private EditText mTitleField;
    private EditText mSummaryField;
    private Button mTimeButton;
    private Button mDateButton;

    public static NoteFragment newInstance(UUID noteId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID, noteId);

        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID noteID = (UUID) getArguments().getSerializable(ARG_NOTE_ID);
        mNote = NoteLab.get(getActivity()).getNote(noteID);
    }

    @Override
    public void onPause() {
        super.onPause();
        NoteLab.get(getActivity())
                .updateNote(mNote);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int []colours = {Color.RED,Color.GRAY,Color.GREEN,Color.BLUE,Color.CYAN};
       // R.color.red,R.color.blue,R.color.green,R.color.orange,
        if (id == R.id.menu_item_new_colour) {
//            ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
//            colorPickerDialog.initialize(
//                    R.string.add_colour,colours,Color.GRAY,5,10);
//            //colorPickerDialog.setTargetFragment(,REQUEST_DATE);
//            colorPickerDialog.show(getActivity().getFragmentManager(),DIALOG_COLOUR);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);

        mTitleField = v.findViewById(R.id.note_title);
        mTitleField.setText(mNote.getTitle());

        mSummaryField = v.findViewById(R.id.note_summary);
        mSummaryField.setText(mNote.getSummary());

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

        mSummaryField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setSummary(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = v.findViewById(R.id.note_date);
        mTimeButton = v.findViewById(R.id.note_time);
        updateDate();
        mTimeButton.setEnabled(false);

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dialog = DatePickerFragment.newInstance(mNote.getDate());
                dialog.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                dialog.show(getFragmentManager(), DIALOG_DATE);
            }
        });

        return v;
    }

    public void returnResult() {
        getActivity().setResult(Activity.RESULT_OK, null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mNote.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(android.text.format.DateFormat.format("EEE, dd MMMM yyyy", mNote.getDate()));
        mTimeButton.setText(android.text.format.DateFormat.format("HH:mm", mNote.getDate()));
    }

}
