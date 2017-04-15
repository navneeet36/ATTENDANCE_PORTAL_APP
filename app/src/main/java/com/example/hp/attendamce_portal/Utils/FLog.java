package com.example.hp.attendamce_portal.Utils;

import android.util.Log;

/**
 * Created by hp on 22-Mar-17.
 */

public class FLog {
    private static String TAG = "JMIT";
    private static boolean showLog = true;

    public static void d(String VAL) {
        if (showLog) {
            d(TAG, VAL);
        }
    }


    public static void d(String TAG, String VAL) {
        if (showLog) {
            Log.d(TAG, "" + VAL);
        }
    }
}
