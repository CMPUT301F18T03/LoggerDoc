package com.example.loggerdoc;

public class IntentTestUtils {

    public static void waitForUserListUpdate() {
        try {
            Thread.sleep(500); // a delay to ensure that new accounts get properly loaded
        }
        catch (InterruptedException e) {
            // never
        }
    }
}
