package com.example.rasekgala.firstaid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rasekgala on 2016/09/23.
 * This class is used to give us the date and the time from the system everytime we send message
 * It contains two impotant methods 1. getCurrentTime and getCurrent date
 */
public class CommonMethod
{
    //create te date format and time format objects and format them accordingly
    private static DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
    private static DateFormat timeFormat = new SimpleDateFormat("K:mma");

    /*
    *this method get the cuurent date from the Calender object
    * return the time as a string
     */
    public static String getCurrentTime()
    {
        //create date object and initialize it with the calendar
        Date time = Calendar.getInstance().getTime();
        //return formatted time
        return timeFormat.format(time);
    }

    /*
    *This method get the current date from the Calendar object
    * return the date
     */
    public  static String getCurrentDate()
    {
        //create the date object and initialize it with Calendar object
        Date date = Calendar.getInstance().getTime();

        //return the formatted date
        return timeFormat.format(date);
    }
}
