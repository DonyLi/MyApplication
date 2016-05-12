package com.example.meihaoshiguang;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.example.meihaoshiguang.view.Menu_menu;
import com.example.meihaoshiguang.view.Menu_photo;
import com.example.meihaoshiguang.view.Menu_setting;
import com.example.slidingmenulib.SlidingMenuActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    CheckedTextView home, menu, photo, setting;

    Class[] classes = {HomeFragment.class, Menu_menu.class, Menu_photo.class, Menu_setting.class};
    ArrayList<CheckedTextView> checkedTextViews = new ArrayList<>();

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        home = (CheckedTextView) getView().findViewById(R.id.menu_home);
        menu = (CheckedTextView) getView().findViewById(R.id.menu_menu);
        photo = (CheckedTextView) getView().findViewById(R.id.menu_photo);
        setting = (CheckedTextView) getView().findViewById(R.id.menu_setting);
        checkedTextViews.add(home);
        checkedTextViews.add(menu);
        checkedTextViews.add(photo);
        checkedTextViews.add(setting);
        for (int i = 0; i < checkedTextViews.size(); i++) {
            final int finalI = i;
            checkedTextViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlidingMenuActivity sa = (SlidingMenuActivity) getActivity();
                    sa.switchFragment(classes[finalI]);
                    for (int i = 0; i < checkedTextViews.size(); i++) {
                        if (i != finalI) {
                            checkedTextViews.get(i).setChecked(false);
                        }else {
                            checkedTextViews.get(i).setChecked(true);
                        }
                    }

                }
            });
        }

    }


}

