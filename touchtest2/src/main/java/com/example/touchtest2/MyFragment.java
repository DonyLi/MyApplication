package com.example.touchtest2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/3/17.
 */
public class MyFragment extends Fragment {
    List<View> views = new ArrayList<>();
    HttpUtils httpUtils;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, null);
        ViewPager vp1 = (ViewPager) v.findViewById(R.id.vp1);
        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(getContext());
            textView.setText("测试 " + i);
            textView.setBackgroundColor(Color.GREEN);
            views.add(textView);
        }
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        };
        vp1.setAdapter(adapter);

        return v;


    }


}
