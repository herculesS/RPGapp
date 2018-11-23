package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;

public class DungeonMaster implements Serializable {
    private String mUserId;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
