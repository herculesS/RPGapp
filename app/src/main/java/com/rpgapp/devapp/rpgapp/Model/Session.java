package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;

public class Session implements Serializable {
    private String mTitle;
    private String mDate;
    private String mSummary;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
