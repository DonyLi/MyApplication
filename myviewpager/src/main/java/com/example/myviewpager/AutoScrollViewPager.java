package com.example.myviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/3/16.
 */
public class AutoScrollViewPager extends ViewPager {
    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    class MyAdapter extends PagerAdapter {
        int[] imags;
        List<ImageView> imageViews = new ArrayList<>();

        public MyAdapter() {
        }

        public MyAdapter(int[] imags) {

            this.imags = imags;
            for (int i = 0; i < imags.length; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(imags[i]);
                // imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageViews.add(imageView);

            }
        }


        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position % imageViews.size()));
            return imageViews.get(position % imageViews.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(imageViews.get(position % imageViews.size()));
        }
    }
}
