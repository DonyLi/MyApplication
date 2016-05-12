package com.example.meihaoshiguang.tools;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meihaoshiguang.R;
import com.example.meihaoshiguang.entity.Materials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/3/30.
 */
public class MaterialsHandler {
    public LayoutInflater inflater;

    public MaterialsHandler(Context context) {
        super();
        inflater = LayoutInflater.from(context);
    }

    public List<Materials> getList(String materialStr) {
        List<Materials> list = new ArrayList<>();
        String[] strings = materialStr.split(";");
        for (String string : strings) {
            String[] strings1 = string.split(",");
            if (strings1.length == 1) {
                list.add(new Materials(strings1[0], ""));
            } else if (strings1.length == 2) {
                list.add(new Materials(strings1[0], strings1[1]));
            }
        }

        return list;
    }

    public void setDatas(LinearLayout container, String mainMaterialsJson, String assistMaterialsJson) {
        List<Materials> materials = new ArrayList<>();
        if (!TextUtils.isEmpty(mainMaterialsJson)) {
            materials.addAll(getList(mainMaterialsJson));
        }
        if (!TextUtils.isEmpty(assistMaterialsJson)) {
            materials.addAll(getList(assistMaterialsJson));
        }
        if (materials.size() == 0) {
            container.setVisibility(View.GONE);
            return;
        }
        for (Materials material : materials) {
            View v = inflater.inflate(R.layout.v_recipe_detail_food_material_item, null);
            TextView name = (TextView) v.findViewById(R.id.recipe_detail_food_material_name);
            TextView count = (TextView) v.findViewById(R.id.recipe_detail_food_material_amount);
            name.setText(material.name);
            if (!TextUtils.isEmpty(material.count)) {
                count.setText(material.count);
            }
            container.addView(v);

        }
    }

}
