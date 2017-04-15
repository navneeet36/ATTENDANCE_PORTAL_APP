package com.example.hp.attendamce_portal.Utils;

import android.graphics.Bitmap;

/**
 * Created by arpitkh96 on 15-04-2017.
 */

public class BitmapHandler {
    private static Bitmap bitmap;

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {

        BitmapHandler.bitmap = bitmap;
    }
}
