package com.example.meihaoshiguang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meihaoshiguang.entity.SelectInfo;
import com.example.meihaoshiguang.tools.BitmapHelper;
import com.example.meihaoshiguang.tools.IntentUtil;
import com.example.meihaoshiguang.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, RefreshListView
        .OnListener {
    RefreshListView refreshList;
    HttpUtils mHttpUtils;
    public int tag;
    TextView mTitleTv;
    SelectInfo selectInfo;
    LayoutInflater inflater;
    BaseAdapter baseAdapter;
    BitmapUtils bitmapUtils;
    public int mCurPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        tag = getIntent().getIntExtra("tag", 1);
        initViews();
        loadDatasFirst();
        inflater = LayoutInflater.from(this);
        bitmapUtils = BitmapHelper.CreateBitmapUtils(this);

    }

    public String getUrl() {
        String url = "";
        switch (tag) {
            case Constants.TAG_SEARCH_BY_RECIPE_NAME:
                String key = getIntent().getStringExtra("title");
                url = Constants.URL_SEARCH_BY_NAME.replace("_key", key).replace("_page", mCurPage + "");
                break;
            case Constants.TAG_SEARCH_BY_INGREDIENT:
                String title = getIntent().getStringExtra("title");
                url = Constants.URL_SEARCH_BY_MATERIAL.replace("_key", title).replace("_page", mCurPage + "");
        }
        return url;
    }

    private void loadDatasFirst() {
        mHttpUtils = new HttpUtils();
        mHttpUtils.send(HttpRequest.HttpMethod.GET, getUrl(), new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String s = responseInfo.result.toString();
                Gson gson = new Gson();
                selectInfo = gson.fromJson(s, SelectInfo.class);
                if (mCurPage == 1) {
                    list = selectInfo.list;
                    showDatas();
                    refreshList.setEmptyView(findViewById(R.id.empty_view));
                } else {
                    list.addAll(selectInfo.list);
                    baseAdapter.notifyDataSetChanged();
                    if (selectInfo.list.size() + 10 * (mCurPage - 1) >= selectInfo.total_count) {
                        refreshList.loadEnd();
                    } else {
                        refreshList.loadSuccess();
                    }

                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    List<SelectInfo.ListBean> list;

    public void showDatas() {

        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = inflater.inflate(R.layout.item_recipe_list, null);
                ImageView img = (ImageView) v.findViewById(R.id.img);
                TextView recipe_name = (TextView) v.findViewById(R.id.recipe_name);
                TextView author_nick = (TextView) v.findViewById(R.id.author_nick);
                TextView recipe_listitem_dish = (TextView) v.findViewById(R.id.recipe_listitem_dish);
                TextView step_count = (TextView) v.findViewById(R.id.step_count);
                TextView recipe_listitem_favor = (TextView) v.findViewById(R.id.recipe_listitem_favor);
                recipe_listitem_dish.setText("评论    " + list.get(position).comment_count);
                recipe_listitem_favor.setText("收藏   " + list.get(position).collection_count);
                bitmapUtils.display(img, list.get(position).img);
                recipe_name.setText(list.get(position).name);
                //  author_nick.setText(selectInfo.list.get(position).cookbook_id + "");
                step_count.setText(list.get(position).step_count + "个步骤");

                return v;
            }
        };
        refreshList.setAdapter(baseAdapter);
        refreshList.setOnItemClickListener(this);
    }

    private void initViews() {
        refreshList = (RefreshListView) findViewById(R.id.refreshlist);
        refreshList.setOnListener(this);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(getIntent().getStringExtra("title"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
        intent.putExtra("recipe_id", selectInfo.list.get(position).cookbook_id);
        IntentUtil.startActivity(RecipeListActivity.this, intent);
    }

    @Override
    public void onLoadNextPage() {
        loadNextPage();
    }

    @Override
    public void onRetry() {
        loadNextPage();
    }

    public void loadNextPage() {
        mCurPage++;
        loadDatasFirst();
    }
}
