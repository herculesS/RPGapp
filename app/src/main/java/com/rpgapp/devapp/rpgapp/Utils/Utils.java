package com.rpgapp.devapp.rpgapp.Utils;

import java.util.Calendar;

public class Utils {
    public enum Sex {
        MALE,
        FEMALE
    }

    static public String getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        return day + "/" + month;
    }

    public static String formatSessionDateToDayMonth(String date) {
        String[] s = date.split("/");
        if (s[0].length() == 1) {
            s[0] = 0 + s[0];
        }
        s[1] = String.valueOf(Integer.parseInt(s[1]) + 1);
        if (s[1].length() == 1) {
            s[1] = 0 + s[1];
        }


        return s[0] + "/" + s[1];
    }
}
