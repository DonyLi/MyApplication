package com.example.qq_2012.avtivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qq_2012.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import messageEntity.AllUsers;
import messageEntity.User;

public class FriendListActivity extends AppCompatActivity implements View.OnClickListener {
    BitmapUtils bitmapUtils;
    ExpandableListView listView;
    AllUsers allusers;
    ImageView myimg;
    TextView myname;
    List<ArrayList<User>> userlist = new ArrayList<>();
    String[] strs = new String[]{
            "我", "我的好友"
    };
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        bitmapUtils = new BitmapUtils(this);
        listView = (ExpandableListView) findViewById(R.id.expandlist);
        myimg = (ImageView) findViewById(R.id.friend_list_myImg);
        myimg.setOnClickListener(this);
        myname = (TextView) findViewById(R.id.friend_list_myName);
        allusers = Params.CreateParams().all;
        bitmapUtils.display(myimg, allusers.me.getHeadCode());
        myname.setText(allusers.me.getName());
        ArrayList<User> me = new ArrayList<>();
        me.add(allusers.me);
        userlist.add(me);
        userlist.add(allusers.list);
        inflater = LayoutInflater.from(FriendListActivity.this);
        ExpandableListAdapter adapter = new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getGroupCount() {
                return userlist.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return userlist.get(groupPosition).size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return null;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return null;
            }

            @Override
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                TextView tv = new TextView(getApplicationContext());
                tv.setText(strs[groupPosition]);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(25);
                return tv;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                                     ViewGroup parent) {
                View v = inflater.inflate(R.layout.friendlist_item, null);
                ImageView imageView = (ImageView) v.findViewById(R.id.header);
                TextView name = (TextView) v.findViewById(R.id.name);
                TextView des = (TextView) v.findViewById(R.id.des);
                bitmapUtils.display(imageView, userlist.get(groupPosition).get(childPosition).getHeadCode());
                name.setText(userlist.get(groupPosition).get(childPosition).getName());
                des.setText(userlist.get(groupPosition).get(childPosition).getDes());

                return v;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupPosition) {

            }

            @Override
            public void onGroupCollapsed(int groupPosition) {

            }

            @Override
            public long getCombinedChildId(long groupId, long childId) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupId) {
                return 0;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(FriendListActivity.this, ChatActivity.class);
                intent.putExtra("name", userlist.get(groupPosition).get(childPosition).getName());
                intent.putExtra("id", userlist.get(groupPosition).get(childPosition).getId());
                startActivity(intent);

                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FriendListActivity.this, MySettings.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 2) {
            bitmapUtils.display(myimg, allusers.me.getHeadCode());
            myname.setText(allusers.me.getName());
        }
    }
}
