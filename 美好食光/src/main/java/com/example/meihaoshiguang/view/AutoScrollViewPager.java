package com.example.meihaoshiguang.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.meihaoshiguang.MainActivity;
import com.example.meihaoshiguang.RecipeDetailActivity;
import com.example.meihaoshiguang.entity.HeadInfo;
import com.example.meihaoshiguang.tools.BitmapHelper;
import com.example.meihaoshiguang.tools.IntentUtil;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jason on 2016/3/16.
 */
public class AutoScrollViewPager extends ViewPager {

    public interface OnScrollChangeListener {
        public void scroll(int x);
    }

    private OnScrollChangeListener onScrollChangeListener;

    private Timer timer;
    private TimerTask task;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            setCurrentItem(getCurrentItem() + 1, true);
            return false;
        }
    });

    public AutoScrollViewPager(Context context) {
        super(context);
        timer = new Timer();

    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        timer = new Timer();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        int currentPage = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % ((MyAdapter) adapter).imageViews.size();
        setCurrentItem(currentPage, false);
        super.setAdapter(adapter);
        startSliding();
    }

    public void startSliding() {
        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0x123);
                }
            };
            timer.schedule(task, 3000, 3000);
        }
    }

    public void stopSliding() {
        handler.removeMessages(0);
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopSliding();
                MainActivity aty = (MainActivity) getContext();
                aty.setIntercept(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startSliding();
                aty = (MainActivity) getContext();
                aty.setIntercept(true);
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startSliding();
                MainActivity aty = (MainActivity) getContext();
                aty.setIntercept(true);


                break;
        }


        return super.onTouchEvent(ev);
    }

    public class MyAdapter extends PagerAdapter {
        BitmapUtils bitmapUtils;
        ArrayList<HeadInfo.HeadObject.HeadObjectInfo> list;
        List<ImageView> imageViews = new ArrayList<>();

        public MyAdapter() {
        }

        public MyAdapter(final ArrayList<HeadInfo.HeadObject.HeadObjectInfo> list) {
            bitmapUtils = BitmapHelper.CreateBitmapUtils(getContext().getApplicationContext());
            this.list = list;
            for (int i = 0; i < list.size(); i++) {
                ImageView imageView = new ImageView(getContext());
                bitmapUtils.display(imageView, list.get(i).img);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setTag(i);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer i = (Integer) v.getTag();
                        HeadInfo.HeadObject.HeadObjectInfo headObjectInfo = list.get(i);
                        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                        intent.putExtra("recipe_id", headObjectInfo.recipeId);
                        IntentUtil.startActivity(getContext(), intent);

                    }
                });
                imageViews.add(imageView);

            }
        }


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (imageViews.get(position % imageViews.size()).getParent() != null) {
                container.removeView(imageViews.get(position % imageViews.size()));
            }

            container.addView(imageViews.get(position % imageViews.size()));
            return imageViews.get(position % imageViews.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(imageViews.get(position % imageViews.size()));
        }

        public HeadInfo.HeadObject.HeadObjectInfo getItem(int position) {
            return list.get(position % list.size());
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null) {
            onScrollChangeListener.scroll(l);
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangelistener) {
        this.onScrollChangeListener = onScrollChangelistener;
    }
}

