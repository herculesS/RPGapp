package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;

public class Notification implements Serializable {
    private String mAdventureId;
    private String mAdventureName;

    public String getAdventureId() {
        return mAdventureId;
    }

    public void setAdventureId(String adventureId) {
        mAdventureId = adventureId;
    }

    public String getAdventureName() {
        return mAdventureName;
    }

    public void setAdventureName(String adventureName) {
        mAdventureName = adventureName;
    }
}
