package com.example.meihaoshiguang;

import android.os.Bundle;

import com.example.slidingmenulib.SlidingMenuActivity;

public class MainActivity extends SlidingMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments(new MenuFragment(), new HomeFragment());

    }
}
