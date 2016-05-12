package com.example.touchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Jason on 2016/3/17.
 */
public class MyGroup2 extends LinearLayout {
    public MyGroup2(Context context) {
        super(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("MyGroup2", "onInterceptTouchEvent");

        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("MyGroup2", "onTouchEvent");

        return false;
    }
}
