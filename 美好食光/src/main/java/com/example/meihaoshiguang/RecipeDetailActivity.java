package com.example.meihaoshiguang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meihaoshiguang.adapter.StepAdapter;
import com.example.meihaoshiguang.entity.RecipeDetailInfo;
import com.example.meihaoshiguang.tools.BitmapHelper;
import com.example.meihaoshiguang.tools.MaterialsHandler;
import com.example.meihaoshiguang.tools.SharedPrefrenceTool;
import com.example.meihaoshiguang.view.AutoScrollViewPager;
import com.example.meihaoshiguang.view.ParallaxScrollListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class RecipeDetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View
        .OnClickListener, AbsListView.OnScrollListener {
    HttpUtils mHttpUtils;
    RecipeDetailInfo recipeDetailInfo;
    int recipeId;
    ParallaxScrollListView listView;
    ImageView back;
    ImageView btnback;
    BitmapUtils mBitmapUtils;
    LayoutInflater minflater;
    ImageView imageView;
    TextView name;
    TextView indoc;
    LinearLayout mMaterialsContainer;
    View mHeaderIconLayout;
    TextView trickContent;
    LinearLayout trickLayout;
    ImageView backimg;
    LinearLayout recipe_detail_top_right_icon_layout;
    RelativeLayout v_header_hideableview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        initView();
        loadDatas();

    }

    private void initView() {
        recipe_detail_top_right_icon_layout = (LinearLayout) findViewById(R.id
                .recipe_detail_top_right_icon_layout);
        backimg = (ImageView) findViewById(R.id.btn_back);
        backimg.setOnClickListener(this);
        minflater = LayoutInflater.from(this);
        listView = (ParallaxScrollListView) findViewById(R.id.listView);
        back = (ImageView) findViewById(R.id.back);
        btnback = (ImageView) findViewById(R.id.btn_back);
        listView.addHeaderView(getHeaderView());
        listView.addFooterView(getFooterView());

    }

    private View getFooterView() {
        View v = minflater.inflate(R.layout.v_recipe_detail_foot, null);
        trickContent = (TextView) v.findViewById(R.id.recipe_detail_footer_trick_content);
        trickLayout = (LinearLayout) v.findViewById(R.id.recipe_detail_footer_trick_layout);
        return v;
    }

    private View getHeaderView() {
        View v = minflater.inflate(R.layout.v_recipe_detail_head, null);
        v_header_hideableview = (RelativeLayout) v.findViewById(R.id
                .v_header_hideableview);
        imageView = (ImageView) v.findViewById(R.id.v_recipe_detail_head_imageview);
        name = (TextView) v.findViewById(R.id.v_recipe_detail_head_recipeNameTv);
        indoc = (TextView) v.findViewById(R.id.v_recipe_detail_head_introduceTv);
        mMaterialsContainer = (LinearLayout) v.findViewById(R.id.v_recipe_header_detail_material_container);
        mHeaderIconLayout = v.findViewById(R.id.v_header_hideableview);
        return v;
    }

    private void loadDatas() {

        mHttpUtils = new HttpUtils();
        recipeId = getIntent().getIntExtra("recipe_id", 0);
        //如果有缓存就先读取缓存
        String jsonData = SharedPrefrenceTool.getStr(getApplicationContext(), "recipe_details", "" + recipeId);
        if (!TextUtils.isEmpty(jsonData)) {
            recipeDetailInfo = new Gson().fromJson(jsonData, RecipeDetailInfo.class);
            String url = Constants.URL_GET_BRIEF_COOK_COMMENTS.replace("_cook_id", recipeId + "").replace("_time_stamp",
                    recipeDetailInfo.comment_time_stamp);
            mHttpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<Object>() {

                @Override
                public void onSuccess(ResponseInfo<Object> responseInfo) {
                    String result = responseInfo.result.toString();
                    Gson gson = new Gson();
                    RecipeDetailInfo.Comments comments = gson.fromJson(result, RecipeDetailInfo.Comments.class);

                    if (comments.totalCount != 0) {
                        recipeDetailInfo.comments = comments;
                        //存入本地
                        SharedPrefrenceTool.put(getApplicationContext(), "recipe_details", recipeId + "", new Gson().toJson
                                (recipeDetailInfo));
                    }
                    loaddataFromObject(recipeDetailInfo);

                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });

        } else {
            String url = Constants.URL_RECIPE_DETAIL.replace("_id", recipeId + "");
            mHttpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<Object>() {
                @Override
                public void onSuccess(ResponseInfo<Object> responseInfo) {
                    String s = responseInfo.result.toString();
                    Gson gson = new Gson();
                    recipeDetailInfo = gson.fromJson(s, RecipeDetailInfo.class);
                    //缓存进去
                    SharedPrefrenceTool.put(getApplicationContext(), "recipe_details", recipeId + "", s);
                    loaddataFromObject(recipeDetailInfo);
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });


        }
    }

    public void loaddataFromObject(RecipeDetailInfo recipeDetailInfo) {
        mBitmapUtils = BitmapHelper.CreateBitmapUtils(getApplicationContext());
        mBitmapUtils.display(imageView, recipeDetailInfo.img);
        name.setText(recipeDetailInfo.name);
        if (!TextUtils.isEmpty(recipeDetailInfo.introduce)) {
            indoc.setText(Html.fromHtml(recipeDetailInfo.introduce));
        }
        //设置食材
        MaterialsHandler materialsHandler = new MaterialsHandler(getApplicationContext());
        materialsHandler.setDatas(mMaterialsContainer, recipeDetailInfo.main_materials, recipeDetailInfo.assist_materials);
        if (!TextUtils.isEmpty(recipeDetailInfo.tips)) {
            trickContent.setText(Html.fromHtml(recipeDetailInfo.tips));
            trickLayout.setVisibility(View.VISIBLE);
        }
        StepAdapter stepAdapter = new StepAdapter(getApplicationContext(), recipeDetailInfo.steps);
        listView.setAdapter(stepAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0 && position != recipeDetailInfo.steps.size() + 1) {
            Intent intent = new Intent(RecipeDetailActivity.this, ShowDetailActivity.class);
            intent.putExtra("object", recipeDetailInfo);
            intent.putExtra("position", position - 1);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == backimg) {
            onBackPressed();
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0 && listView.getChildAt(0) != null) {

            Log.e(-listView.getChildAt(0).getTop() + "", v_header_hideableview.getTop() + "");
            if (-listView.getChildAt(0).getTop() >= v_header_hideableview.getTop()) {
                recipe_detail_top_right_icon_layout.setVisibility(View.VISIBLE);
                v_header_hideableview.setVisibility(View.INVISIBLE);
            } else {
                recipe_detail_top_right_icon_layout.setVisibility(View.INVISIBLE);
                v_header_hideableview.setVisibility(View.VISIBLE);
            }
        }

    }
}
