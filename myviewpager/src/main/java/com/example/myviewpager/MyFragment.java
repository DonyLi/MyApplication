package com.example.myviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jason on 2016/3/16.
 */
public class MyFragment extends Fragment {
    int[] resids = {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3,
            R.drawable.pic_welcome
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AutoScrollViewPager autoScrollViewPager = new AutoScrollViewPager(getContext());
        AutoScrollViewPager.MyAdapter adapter = new AutoScrollViewPager(getContext()).new MyAdapter(resids);
        autoScrollViewPager.setAdapter(adapter);

        return autoScrollViewPager;
    }
}
