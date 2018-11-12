package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * */
public class Adventure implements Serializable {
    private String mId;
    private String mTitle;
    private String mDescription;
    private ArrayList<Session> mSessions;
    private ArrayList<Character> mCharacters;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public ArrayList<Session> getSessions() {
        return mSessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        mSessions = sessions;
    }

    public void addSession(Session se) {
        if (mSessions == null) {
            mSessions = new ArrayList<>();
        }
        mSessions.add(se);
    }

    public void removeSession(Session se) {
        if (mSessions != null) {
            mSessions.remove(se);
        }
    }

    public ArrayList<Character> getCharacters() {
        return mCharacters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        mCharacters = characters;
    }

    public void addCharacter(Character ca) {
        if (mCharacters == null) {
            mCharacters = new ArrayList<>();
        }
        mCharacters.add(ca);
    }

    public void removeCharacter(Character ca) {
        if (mCharacters != null) {
            mCharacters.remove(ca);
        }
    }
}
