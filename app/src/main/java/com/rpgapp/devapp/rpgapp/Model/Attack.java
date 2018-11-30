package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;

public class Attack implements Serializable {
    private int mDiceFace;
    private int mNumberOfDice;
    private int mBonus;
    private String mName;
    private String mUserId;

    public Attack(){

    }

    public Attack(int diceFace, int numberOfDice, int bonus, String name, String userId) {
        mDiceFace = diceFace;
        mNumberOfDice = numberOfDice;
        mBonus = bonus;
        mName = name;
        mUserId = userId;
    }

    public Roll rollAttack() {
        return new Roll(mNumberOfDice, mNumberOfDice, mBonus, mUserId);
    }


    public int getDiceFace() {
        return mDiceFace;
    }

    public void setDiceFace(int diceFace) {
        mDiceFace = diceFace;
    }

    public int getNumberOfDice() {
        return mNumberOfDice;
    }

    public void setNumberOfDice(int numberOfDice) {
        mNumberOfDice = numberOfDice;
    }

    public int getBonus() {
        return mBonus;
    }

    public void setBonus(int bonus) {
        mBonus = bonus;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
