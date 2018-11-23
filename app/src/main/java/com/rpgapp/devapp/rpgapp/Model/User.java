package com.rpgapp.devapp.rpgapp.Model;

import com.rpgapp.devapp.rpgapp.Utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String id;
    private String name;
    private String email;
    private Utils.Sex sex;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private ArrayList<Notification> mNotifications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Utils.Sex getSex() {
        return sex;
    }

    public void setSex(Utils.Sex sex) {
        this.sex = sex;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public ArrayList<Notification> getNotifications() {
        return mNotifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        mNotifications = notifications;
    }

    public void addNotification(Notification nt) {
        if(mNotifications == null) {
            mNotifications = new ArrayList<>();
        }
        mNotifications.add(nt);
    }

    public void removeNotification(Notification nt) {
        if(mNotifications != null) {
            mNotifications.remove(nt);
        }
    }

    public boolean isNotificationAlreadySent(String mAdventureId) {
        if(!userHasNotification()) {
            return false;
        }
        for (Notification nt : mNotifications) {
            if(nt.getAdventureId().equals(mAdventureId)){
                return true;
            }
        }
        return false;
    }

    public boolean userHasNotification() {
        return mNotifications!=null && !mNotifications.isEmpty();
    }


}
