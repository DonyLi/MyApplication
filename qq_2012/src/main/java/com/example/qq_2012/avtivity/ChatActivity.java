package com.example.qq_2012.avtivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qq_2012.R;
import com.lidroid.xutils.BitmapUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import messageEntity.MyMessage;

public class ChatActivity extends Activity {
    ListView listView;
    EditText input;
    Button send;
    String id;
    LayoutInflater inflater;
    BaseAdapter adapter;
    public static Handler handler;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
    BitmapUtils bitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        bitmapUtils = new BitmapUtils(this);
        initView();
        Intent intent = new Intent(ChatActivity.this, Handle.class);
        startService(intent);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                adapter.notifyDataSetChanged();

                return false;
            }
        });
        inflater = LayoutInflater.from(this);
        id = getIntent().getStringExtra("id");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = input.getText().toString();
                final MyMessage myMessage = new MyMessage();
                myMessage.message = id + message;
                myMessage.time = simpleDateFormat.format(new Date());
                myMessage.imei = Params.CreateParams().imei;
                myMessage.recType = 1;
                Params.CreateParams().persons.get(id).allMessage.add(myMessage);
                adapter.notifyDataSetChanged();
                new Thread() {
                    @Override
                    public void run() {
                        Log.e("sendmessage", myMessage.message);
                        HandleMessage.sendMessage(myMessage);
                    }
                }.start();
                input.setText("");

            }
        });

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return Params.CreateParams().persons.get(id).allMessage.size();
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
                MyMessage myMessage = Params.CreateParams().persons.get(id).allMessage.get(position);
                View v = null;
                if (myMessage.recType == 0) {
                    v = inflater.inflate(R.layout.chatting_item_msg_text_left, null);
                } else {
                    v = inflater.inflate(R.layout.chatting_item_msg_text_right, null);
                }

                TextView sendTime = (TextView) v.findViewById(R.id.tv_sendTime);
                ImageView head = (ImageView) v.findViewById(R.id.iv_userhead);
                TextView text = (TextView) v.findViewById(R.id.tv_chatcontent);
                sendTime.setText(myMessage.time);
                if (myMessage.recType == 0) {
                    bitmapUtils.display(head, Params.CreateParams().persons.get(id)
                            .user.getHeadCode());
                } else {
                    bitmapUtils.display(head, Params.CreateParams().persons.get(Params.CreateParams().myID)
                            .user.getHeadCode());
                }

                text.setText(myMessage.message.substring(5, myMessage.message.length()));
                return v;
            }
        };
        listView.setAdapter(adapter);

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview);
        input = (EditText) findViewById(R.id.chat_editmessage);
        send = (Button) findViewById(R.id.chat_send);
    }


}
