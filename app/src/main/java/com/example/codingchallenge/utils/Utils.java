package com.example.codingchallenge.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {


    /**
     * Function to format the date string
     * Input Format: 2024-11-25T21:08:52Z
     * Output Format: Nov 25, 2024
     */
    public static String formatDateString(String dateString) {

        // Define input date format
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Define output format (MMM dd, yyyy)
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

        try {
            // Parse the input date string to Date object
            Date date = isoFormat.parse(dateString);
            // Format the Date object
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

}
