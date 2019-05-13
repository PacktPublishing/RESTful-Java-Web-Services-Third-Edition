/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Common utility class for date relates features
 * @author Jobinesh
 */
public abstract class DateUtil {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Returns the current time as a String.
     */
    public static String getDate(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

    /**
     * Returns the current time as a String.
     */
    public static Date getDate(String date) {

        try {
            return SIMPLE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
