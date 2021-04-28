package com.exercise.api.data.helpers;

import java.sql.Timestamp;

import static java.lang.Thread.sleep;

public class Utils {

    public static void delay(int milliseconds){
        try {
            sleep(milliseconds);
        }
        catch (Exception e){
            System.out.println("Exception is :" + e.getMessage());
        }
    }

    public static void log(String message){
        System.out.println(new Timestamp(System.currentTimeMillis()) + " [" + Thread.currentThread().getName() +"] - " + message);
    }
}
