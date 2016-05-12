package com.example.qq_2012.avtivity;

import android.content.Context;

import com.example.qq_2012.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;

import messageEntity.AllUsers;

/**
 * Created by Jason on 2016/3/30.
 */
public class Params {
    public static Params params;
    public AllUsers all;
    public int[] imIDs = new int[]{
            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
            R.drawable.f6,
            R.drawable.f7,
    };

    public static Params CreateParams() {
        if (params == null) {
            params = new Params();
        }
        return params;
    }

    public HashMap<String, Person> persons = new HashMap<>();
    public String imei;
    public String myID;
}
