package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Serializable {
    private String mTitle;
    private String mDate;
    private String mSummary;
    private ArrayList<Roll> mRolls;

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

    public ArrayList<Roll> getRolls() {
        return mRolls;
    }

    public void setRolls(ArrayList<Roll> rolls) {
        mRolls = rolls;
    }

    public void addRoll(Roll rl) {
        if(mRolls==null) {
            mRolls = new ArrayList<>();
        }
        mRolls.add(rl);
    }
}
