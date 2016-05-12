package com.example.meihaoshiguang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meihaoshiguang.R;
import com.example.meihaoshiguang.entity.RecipeDetailInfo;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

import java.util.List;

/**
 * Created by Jason on 2016/3/30.
 */
public class StepAdapter extends BaseAdapter {
    public Context context;
    public List<RecipeDetailInfo.Steps> steps;
    public LayoutInflater inflater;
    BitmapUtils bitmapUtils;
    public static final Drawable DEFAULT_DRAWABLE = new ColorDrawable(Color.YELLOW);

    public StepAdapter(Context context, List<RecipeDetailInfo.Steps> steps) {
        this.context = context;
        this.steps = steps;
        inflater = LayoutInflater.from(context);
        bitmapUtils = new BitmapUtils(context);
        Bitmap bitmap = null;
        bitmapUtils.configDefaultLoadingImage(bitmap);

    }


    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) {
        return steps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_recipe_step, null);
            holder.setpPosition = (TextView) convertView.findViewById(R.id.recipe_detail_step_position);
            holder.stepContent = (TextView) convertView.findViewById(R.id.recipe_detail_step_content);
            holder.stepImage = (ImageView) convertView.findViewById(R.id.recipe_detail_step_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecipeDetailInfo.Steps step = (RecipeDetailInfo.Steps) getItem(position);
        holder.setpPosition.setText(position + 1 + "");
        holder.stepContent.setText(Html.fromHtml(steps.get(position).title));
        holder.stepContent.setTextColor(Color.BLACK);
        bitmapUtils.display(holder.stepImage, steps.get(position).img, new CustomBitmaploadCallBack());
        return convertView;
    }

    public class CustomBitmaploadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
        @Override
        public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config,
                                    BitmapLoadFrom from) {
            TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{
                    DEFAULT_DRAWABLE, new BitmapDrawable(container.getResources(), bitmap)
            });
            container.setImageDrawable(transitionDrawable);
            transitionDrawable.startTransition(800);

        }
    }
}


class ViewHolder {
    TextView setpPosition;
    TextView stepContent;
    ImageView stepImage;

}
