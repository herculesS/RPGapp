package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;

public class Character implements Serializable {
    private String mName;
    private String mSummary;
    private String mUserId;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
