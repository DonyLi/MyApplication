package com.example.viewpagertest;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyViewpager pager;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    int[] Ids = new int[]{
            R.layout.show1,
            R.layout.show2,
            R.layout.show3,
            R.layout.show4,
    };
    View v = null;
    List<View> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (MyViewpager) findViewById(R.id.pager);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        for (int i = 0; i < Ids.length; i++) {
            v = LayoutInflater.from(this).inflate(Ids[i], null);
            list.add(v);

        }


        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.removeView(list.get(position % Ids.length));
                container.addView(list.get(position % Ids.length));


                return list.get(position % Ids.length);

            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        pager.setAdapter(adapter);
        pager.setCurrentItem(getFirst(100 / 2, Ids.length));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(getFirst(pager.getCurrentItem(), Ids.length) + 0, true);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(getFirst(pager.getCurrentItem(), Ids.length) + 1, true);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(getFirst(pager.getCurrentItem(), Ids.length) + 2, true);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() % Ids.length - (Ids.length - 1) == 0) {
                    return;
                }
                pager.setCurrentItem(getFirst(pager.getCurrentItem(), Ids.length) - 1, true);
            }
        });
    }

    public int getFirst(int count, int size) {
        int temp = 0;
        if (count % size == 0) {
            temp = count;
        } else {
            count--;
            temp = getFirst(count, size);
        }

        return temp;
    }
}
