package com.example.andrew.noteplus;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//синглетный класс
public class NoteLab {
    private static NoteLab sNoteLab;

    private List<Note> mNotes;

    public static NoteLab get(Context context) {
        if (sNoteLab == null) {
            sNoteLab = new NoteLab(context);
        }
        return sNoteLab;
    }

    private NoteLab(Context сontext) {
        mNotes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Note note = new Note();
            note.setTitle("Note #" + i);
            note.setSolved(i % 2 == 0); // Для каждого второго объекта
            mNotes.add(note);
        }
    }

    public List<Note> getNotes() {
        return mNotes;
    }

    public Note getCrime(UUID id) {
        for (Note note : mNotes) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        return null;
    }
}
