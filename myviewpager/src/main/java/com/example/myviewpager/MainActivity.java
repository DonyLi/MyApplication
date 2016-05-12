package com.example.myviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    int[] resids = {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3,
            R.drawable.pic_welcome
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoScrollViewPager vp = (AutoScrollViewPager) findViewById(R.id.vp);
        AutoScrollViewPager.MyAdapter adapter = new AutoScrollViewPager(MainActivity.this).new MyAdapter(resids);
        vp.setAdapter(adapter);

    }
}
