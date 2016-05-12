package com.example.slidingmunulibtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.slidingmenulib.SlidingMenuActivity;

public class MainActivity extends SlidingMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments(new MenuActivity(), new HomeActivity());
    }
}
