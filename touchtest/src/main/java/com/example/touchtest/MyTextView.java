package com.example.touchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jason on 2016/3/17.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("MyTextView", "onTouchEvent");
        return false;
    }

}
