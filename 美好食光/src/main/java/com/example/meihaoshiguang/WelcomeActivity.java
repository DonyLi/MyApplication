package com.example.meihaoshiguang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    ViewPager show;
    ArrayList<View> list = new ArrayList<>();
    int[] ids = new int[]{R.layout.guide_1, R.layout.guide_2, R.layout.guide_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        show = (ViewPager) findViewById(R.id.show);

        for (int i = 0; i < ids.length + 1; i++) {
            if (i != ids.length) {
                View view = LayoutInflater.from(WelcomeActivity.this).inflate(ids[i], null);
                list.add(view);
            } else {
                View view = new LinearLayout(WelcomeActivity.this);
                view.setBackgroundColor(Color.WHITE);
                list.add(view);
            }

        }
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        show.setAdapter(adapter);
        show.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size() - 1) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                    SharedPreferences sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirst", false);
                    editor.commit();
                    overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
