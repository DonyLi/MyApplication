package com.example.meihaoshiguang;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meihaoshiguang.adapter.StepPagerAdapter;
import com.example.meihaoshiguang.entity.RecipeDetailInfo;
import com.example.meihaoshiguang.view.SlideableView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener,
        View.OnTouchListener {
    RecipeDetailInfo info;
    ImageView backImg;
    TextView currentPageTv, totalPageTv;
    ViewPager viewPager;
    int position;
    SlideableView recipe_step_slideableview;
    CheckedTextView recipe_step_tipsBtn;
    TextView recipe_step_tipsTv;
    int mScrollHeiht;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        info = (RecipeDetailInfo) getIntent().getSerializableExtra("object");
        position = getIntent().getIntExtra("position", 0);
        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (!TextUtils.isEmpty(info.tips) && mScrollHeiht == 0) {
                mScrollHeiht = recipe_step_slideableview.getHeight() - recipe_step_tipsBtn.getHeight();
                recipe_step_slideableview.scrollTo(0, -mScrollHeiht);
                recipe_step_tipsBtn.setChecked(true);
            }
        }
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.backImg);
        backImg.setOnClickListener(this);
        currentPageTv = (TextView) findViewById(R.id.currentPageTv);
        totalPageTv = (TextView) findViewById(R.id.totalPageTv);
        totalPageTv.setText(info.steps.size() + "");
        if (!TextUtils.isEmpty(info.tips)) {
            recipe_step_slideableview = (SlideableView) findViewById(R.id.recipe_step_slideableview);
            recipe_step_tipsBtn = (CheckedTextView) findViewById(R.id.recipe_step_tipsBtn);
            recipe_step_tipsTv = (TextView) findViewById(R.id.recipe_step_tipsTv);
            recipe_step_tipsTv.setText(Html.fromHtml(info.tips));
            recipe_step_slideableview.setVisibility(View.VISIBLE);
            recipe_step_tipsBtn.setOnClickListener(this);
        }
        initViewPager();

    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        StepPagerAdapter adapter = new StepPagerAdapter(getApplicationContext(), info.steps);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        currentPageTv.setText(position + 1 + "");
        viewPager.addOnPageChangeListener(this);
        viewPager.setOnTouchListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPageTv.setText(position + 1 + "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if (v == backImg) {
            onBackPressed();
        } else if (v == recipe_step_tipsBtn) {
            if (recipe_step_tipsBtn.isChecked()) {
                recipe_step_slideableview.smoothScrollTo(0, 0, 800);
            } else {
                recipe_step_slideableview.smoothScrollTo(0, -mScrollHeiht, 800);
            }
            recipe_step_tipsBtn.toggle();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !TextUtils.isEmpty(info.tips)) {
            if (!recipe_step_tipsBtn.isChecked()) {
                recipe_step_tipsBtn.toggle();
                recipe_step_slideableview.smoothScrollTo(0, -mScrollHeiht, 800);
            }
        }

        return false;
    }
}
