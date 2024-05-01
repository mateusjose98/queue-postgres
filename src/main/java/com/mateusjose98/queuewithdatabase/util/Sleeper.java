package com.mateusjose98.queuewithdatabase.util;

public class Sleeper {

    public static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
