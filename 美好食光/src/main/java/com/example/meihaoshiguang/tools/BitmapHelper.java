package com.example.meihaoshiguang.tools;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Jason on 2016/3/22.
 */
public class BitmapHelper {
    public static BitmapUtils bitmapUtils;

    public static BitmapUtils CreateBitmapUtils(Context context) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(context);
        }
        return bitmapUtils;
    }
}
