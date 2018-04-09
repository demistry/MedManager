package com.medmanager.android.presenter.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ILENWABOR DAVID on 04/04/2018.
 * This class converts and processes strings for various text-views and usages
 */

public class StringProcessor {

    public static int processString(String string){
        StringBuilder stringBuilder = new StringBuilder(string);
        String substring = stringBuilder.substring(0,1);
        return Integer.parseInt(substring);
    }

    public static String extractFirstLetter(String string){
        StringBuilder stringBuilder = new StringBuilder(string);
        return stringBuilder.substring(0,1);
    }

    public static String convertDateToString(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String convertIntToMonthString(int monthType){
        Date date = new Date(0, monthType + 1, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL", Locale.getDefault());
        Log.v("TAG", "Month saved is " + dateFormat.format(date) );
        return dateFormat.format(date);
    }

    public static String convertTimeToString(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        return timeFormat.format(date);
    }

    public static Date convertStringToDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
        try{
            Date date1 = format.parse(date);
            return date1;
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Date convertStringToTime(String time){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        try{
            Date date1 = format.parse(time);
            return date1;
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
