package com.example.meihaoshiguang;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meihaoshiguang.entity.HeadInfo;
import com.example.meihaoshiguang.entity.IngredinetEntity;
import com.example.meihaoshiguang.entity.RecipeDetailInfo;
import com.example.meihaoshiguang.tools.BitmapHelper;
import com.example.meihaoshiguang.tools.IntentUtil;
import com.example.meihaoshiguang.view.AutoScrollViewPager;
import com.example.meihaoshiguang.view.IndicatorView;
import com.example.meihaoshiguang.view.MyGridView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @param
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, AutoScrollViewPager
        .OnScrollChangeListener, AdapterView.OnItemClickListener {
    HeadInfo headInfo;
    HttpUtils httpUtils;
    AutoScrollViewPager autoScrollViewPager;
    AutoScrollViewPager.MyAdapter adapter;
    TextView home_searchView;
    ListView home_listview;
    ImageView loadingView;
    TextView failedText;
    BaseAdapter baseAdapter, gridviewAdapter, entityAdapter;
    TextView pagerTitle;
    IndicatorView mIndicatorView;
    MyGridView myGridView, ingredGridView;
    BitmapUtils bitmapUtils;
    ArrayList<IngredinetEntity> entities = new ArrayList<>();
    ArrayList<Drawable> drawablelist = new ArrayList<>();
    AssetManager assetManager;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ininitView();
        loadDatas();

    }

    private void ininitView() {
        home_searchView = (TextView) getView().findViewById(R.id.home_searchView);
        home_listview = (ListView) getView().findViewById(R.id.home_listview);
        loadingView = (ImageView) getView().findViewById(R.id.loadingView);
        failedText = (TextView) getView().findViewById(R.id.failedText);
        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                if (headInfo == null)
                    return 0;
                else {
                    return headInfo.headObject.list.size();
                }
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getContext());
                textView.setText(headInfo.headObject.list.get(position).name);
                textView.setTextColor(Color.BLACK);
                return textView;
            }
        };
        home_listview.setAdapter(baseAdapter);
        home_listview.addHeaderView(getHeaderView());

        assetManager = this.getActivity().getAssets();
        entities.add(new IngredinetEntity("白菜", "baicai"));
        entities.add(new IngredinetEntity("大闸蟹", "dazhaxie"));
        entities.add(new IngredinetEntity("冬瓜", "donggua"));
        entities.add(new IngredinetEntity("豆腐", "doufu"));
        entities.add(new IngredinetEntity("黑木耳", "heimuer"));
        entities.add(new IngredinetEntity("红薯", "hongshu"));
        entities.add(new IngredinetEntity("鸡翅", "jichi"));
        entities.add(new IngredinetEntity("鸡蛋", "jidan"));
        entities.add(new IngredinetEntity("鸡肉", "jirou"));
        entities.add(new IngredinetEntity("萝卜", "luobo"));
        entities.add(new IngredinetEntity("南瓜", "nangua"));
        entities.add(new IngredinetEntity("牛肉", "niurou"));
        entities.add(new IngredinetEntity("排骨", "paigu"));
        entities.add(new IngredinetEntity("螃蟹", "pangxie"));
        entities.add(new IngredinetEntity("茄子", "qiezi"));
        entities.add(new IngredinetEntity("秋葵", "qiukui"));
        entities.add(new IngredinetEntity("三文鱼", "sanwenyu"));
        entities.add(new IngredinetEntity("山药", "shanyao"));
        entities.add(new IngredinetEntity("土豆", "tudou"));
        entities.add(new IngredinetEntity("虾", "xia"));
        entities.add(new IngredinetEntity("西红柿", "xihongshi"));
        entities.add(new IngredinetEntity("西蓝花", "xilanhua"));
        entities.add(new IngredinetEntity("羊肉", "yangrou"));
        entities.add(new IngredinetEntity("鱿鱼", "youyu"));
        entities.add(new IngredinetEntity("芋头", "yutou"));
        entities.add(new IngredinetEntity("猪蹄", "zhuti"));

        for (IngredinetEntity entity : entities) {
            try {
                Drawable drawable = Drawable.createFromStream(assetManager.open("shicai/" + entity.imageName + ".jpg"), "");
                drawablelist.add(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        entityAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return entities.size();
            }

            @Override
            public Object getItem(int position) {
                return entities.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_ingredient,
                            null);
                    holder.imageView1 = (ImageView) convertView.findViewById(R.id
                            .item_home_head_ingredient_img);
                    holder.textView1 = (TextView) convertView.findViewById(R.id
                            .item_home_head_ingredient_text);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.imageView1.setImageDrawable(drawablelist.get(position));
                holder.textView1.setText(entities.get(position).name);

                return convertView;
            }
        };
        ingredGridView.setAdapter(entityAdapter);
        ingredGridView.setOnItemClickListener(this);

    }

    private View getHeaderView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View v = layoutInflater.inflate(R.layout.v_home_head, null);
        autoScrollViewPager = (AutoScrollViewPager) v.findViewById(R.id.home_head_viewpager);
        mIndicatorView = (IndicatorView) v.findViewById(R.id.indicator);
        myGridView = (MyGridView) v.findViewById(R.id.home_head_recipe_gridview);
        ingredGridView = (MyGridView) v.findViewById(R.id.home_head_ingredient_gridview);
        pagerTitle = (TextView) v.findViewById(R.id.pager_title);
        autoScrollViewPager.addOnPageChangeListener(this);

        return v;
    }

    private void loadDatas() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, Constants.URL_HOME, new
                RequestCallBack<Object>() {

                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {

                        String s = responseInfo.result.toString();

                        Gson gson = new Gson();
                        headInfo = gson.fromJson(s, HeadInfo.class);
                        adapter = autoScrollViewPager.new MyAdapter(headInfo.headObject.list);
                        autoScrollViewPager.setAdapter(adapter);
                        baseAdapter.notifyDataSetChanged();
                        autoScrollViewPager.setOnScrollChangeListener(HomeFragment.this);
                        mIndicatorView.setIndicatorsCount(headInfo.headObject.list.size());
                        bitmapUtils = BitmapHelper.CreateBitmapUtils(getContext().getApplicationContext());
                        gridviewAdapter = new BaseAdapter() {

                            @Override
                            public int getCount() {
                                return headInfo.recipe_object.list.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return headInfo.recipe_object.list.get(position);
                            }

                            @Override
                            public long getItemId(int position) {
                                return position;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                ViewHolder holder;
                                if (convertView == null) {
                                    holder = new ViewHolder();
                                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_hot_recipe,
                                            null);
                                    holder.imageView = (ImageView) convertView.findViewById(R.id
                                            .item_home_head_recipe_img);
                                    holder.textView = (TextView) convertView.findViewById(R.id
                                            .item_home_head_recipe_title);

                                    convertView.setTag(holder);
                                } else {
                                    holder = (ViewHolder) convertView.getTag();
                                }
                                bitmapUtils.display(holder.imageView, headInfo.recipe_object.list.get(position).img);
                                holder.textView.setText(headInfo.recipe_object.list.get(position).title);

                                return convertView;
                            }
                        };
                        myGridView.setAdapter(gridviewAdapter);
                        myGridView.setOnItemClickListener(HomeFragment.this);


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        pagerTitle.setText(headInfo.headObject.list.get(position % headInfo.headObject.list.size()).name);

//        pagerTitle.setText(adapter.getItem(position).name);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void scroll(int x) {
        mIndicatorView.move(x, autoScrollViewPager.getWidth());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == ingredGridView) {
            IngredinetEntity item = (IngredinetEntity) entityAdapter.getItem(position);
            Intent intent = new Intent(getContext(), RecipeListActivity.class);
            intent.putExtra("tag", Constants.TAG_SEARCH_BY_INGREDIENT);
            intent.putExtra("title", item.name);
            IntentUtil.startActivity(getContext(), intent);
        } else {
            Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
            intent.putExtra("recipe_id", ((HeadInfo.Recipe_object.Recipe_objectInfo) gridviewAdapter.getItem(position)).id);
            IntentUtil.startActivity(getContext(), intent);
        }


    }
}

class ViewHolder {
    ImageView imageView;
    ImageView imageView1;
    TextView textView;
    TextView textView1;
}