package com.example.meihaoshiguang.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meihaoshiguang.R;
import com.example.meihaoshiguang.entity.RecipeDetailInfo;
import com.example.meihaoshiguang.tools.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/3/31.
 */
public class StepPagerAdapter extends PagerAdapter {
    public Context context;
    public ArrayList<RecipeDetailInfo.Steps> steps;
    LayoutInflater inflater;
    View v;
    List<View> view = new ArrayList<>();
    BitmapUtils utils;

    public StepPagerAdapter(Context context, ArrayList<RecipeDetailInfo.Steps> steps) {
        this.context = context;
        this.steps = steps;
        inflater = LayoutInflater.from(context);
        utils = BitmapHelper.CreateBitmapUtils(context);
        for (int i = 0; i < steps.size(); i++) {
            v = inflater.inflate(R.layout.recipe_step_cusom_pager, null);
            TextView recipe_step_pager_content = (TextView) v.findViewById(R.id.recipe_step_pager_content);
            ImageView recipe_step_pager_img = (ImageView) v.findViewById(R.id.recipe_step_pager_img);
            recipe_step_pager_content.setText(Html.fromHtml(steps.get(i).title));
            utils.display(recipe_step_pager_img, steps.get(i).img);

            view.add(v);
        }
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(view.get(position));
        return view.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(view.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
