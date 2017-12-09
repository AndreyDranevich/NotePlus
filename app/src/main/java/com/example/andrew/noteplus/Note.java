package com.example.andrew.noteplus;

import java.util.Date;
import java.util.UUID;

public class Note {
    private UUID mId;
    private String mTitle;
    private String mSummary;
    private Date mDate;

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public Date getDate() {
        System.out.println(mDate);
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Note() {
        this(UUID.randomUUID());
    }

    public Note(UUID id){
        mId=id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
