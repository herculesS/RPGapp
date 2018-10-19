package com.rpgapp.devapp.rpgapp.Utils;

import java.util.Calendar;

public class Utils {

    static public String getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        return day + "/" + month;
    }
}
