package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {
    private String mName;
    private String mSummary;
    private int mHitpoints;
    private String mUserId;
    private ArrayList<Attack> mAttacks;

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

    public ArrayList<Attack> getAttacks() {
        return mAttacks;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        mAttacks = attacks;
    }

    public void addAttack(Attack at){
        if(mAttacks == null) {
            mAttacks = new ArrayList<>();
        }
        mAttacks.add(at);
    }

    public void removeAttack(Attack at) {
        if (mAttacks!= null) {
            mAttacks.remove(at);
        }
    }

    public int getHitpoints() {
        return mHitpoints;
    }

    public void setHitpoints(int hitpoints) {
        mHitpoints = hitpoints;
    }
}
