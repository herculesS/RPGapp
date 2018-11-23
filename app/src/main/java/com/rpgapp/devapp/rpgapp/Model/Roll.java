package com.rpgapp.devapp.rpgapp.Model;

import java.io.Serializable;
import java.util.Random;

public class Roll implements Serializable {
    private int mNumberOfDice;
    private int mDiceFacesNumber;
    private int mBonus;
    private boolean mWasCriticalSuccess;
    private boolean mWasCriticalFailure;
    private String mUserId;

    private int mNumberRolled;

    public int getNumberOfDice() {
        return mNumberOfDice;
    }

    public void setNumberOfDice(int numberOfDice) {
        mNumberOfDice = numberOfDice;
    }

    public int getDiceFacesNumber() {
        return mDiceFacesNumber;
    }

    public void setDiceFacesNumber(int diceFacesNumber) {
        mDiceFacesNumber = diceFacesNumber;
    }

    public int getBonus() {
        return mBonus;
    }

    public void setBonus(int bonus) {
        mBonus = bonus;
    }

    public int getNumberRolled() {
        return mNumberRolled;
    }

    public void setNumberRolled(int numberRolled) {
        mNumberRolled = numberRolled;
    }

    public Roll() {

    }
    public Roll(int numberOfDice, int diceFacesNumber, int bonus, String userId) {
        mNumberOfDice = numberOfDice;
        mDiceFacesNumber = diceFacesNumber;
        mBonus = bonus;
        mUserId = userId;

        roll();
    }

    public void roll() {
        mNumberRolled = 0;
        for (int i = 0; i < mNumberOfDice; i++) {
            Random rdn = new Random();
            mNumberRolled += 1 + rdn.nextInt(mDiceFacesNumber);
        }

        mWasCriticalSuccess = mNumberRolled == mNumberOfDice * mDiceFacesNumber;
        mWasCriticalFailure = mNumberRolled == mNumberOfDice;

        mNumberRolled += mBonus;
    }

    public boolean isWasCriticalSuccess() {
        return mWasCriticalSuccess;
    }

    public void setWasCriticalSuccess(boolean wasCriticalSuccess) {
        mWasCriticalSuccess = wasCriticalSuccess;
    }

    public boolean isWasCriticalFailure() {
        return mWasCriticalFailure;
    }

    public void setWasCriticalFailure(boolean wasCriticalFailure) {
        mWasCriticalFailure = wasCriticalFailure;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
