package com.example.rohit.namethatcolor;

import android.graphics.Bitmap;

/**
 * Created by Rohit on 12/19/2017.
 */

class Result {

    private Bitmap resultantBitmap;
    private String resultantText1;
    private String resultantText2;


    Result(Bitmap resultantBitmap, String resultantText1, String resultantText2) {
        this.resultantBitmap = resultantBitmap;
        this.resultantText1 = resultantText1;
        this.resultantText2 = resultantText2;
    }

    Bitmap getResultantBitmap() {
        return resultantBitmap;
    }

    String getResultantText1() {
        return resultantText1;
    }

    String getResultantText2() {
        return resultantText2;
    }

}
