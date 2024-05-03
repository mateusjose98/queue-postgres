package com.mateusjose98.queuewithdatabase.util;

public class Sleeper {

    public static void sleep(long hundredMilliseconds) {
        try {
            Thread.sleep(hundredMilliseconds * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
