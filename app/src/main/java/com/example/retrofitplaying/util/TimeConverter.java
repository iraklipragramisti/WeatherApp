package com.example.retrofitplaying.util;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConverter {
    private static final String TAG = "TimeConverter";
    public static String getWeekday(long dt, int timezoneInt){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        String res = "";

        Date date = new Date((dt + timezoneInt) * 1000);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int weekdayInt = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekdayInt == 1) res = "Sun";
        if (weekdayInt == 2) res = "Mon";
        if (weekdayInt == 3) res = "Tue";
        if (weekdayInt == 4) res = "Wed";
        if (weekdayInt == 5) res = "Thu";
        if (weekdayInt == 6) res = "Fri";
        if (weekdayInt == 7) res = "Sat";
        return res;

    }


    public static String getHour(long dt, int timezoneInt){

        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

        Date date = new Date((dt + timezoneInt) * 1000);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String[] strings = date.toString().split(" ");
        String hour = strings[3].substring(0, 5);
        //MUSHAOBS!!
        Log.d(TAG, "getHour: " + date.toString());
        return hour;


    }
}
