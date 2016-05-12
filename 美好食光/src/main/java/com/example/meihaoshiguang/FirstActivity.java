package com.example.meihaoshiguang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FirstActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean isend = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_avtivity);
        preferences = getSharedPreferences("time", Context.MODE_PRIVATE);
        editor = preferences.edit();


        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (isend) {
                    return false;
                }
                if (isFirst()) {
                    Intent intent = new Intent(FirstActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                finish();

                return false;
            }
        }).sendEmptyMessageDelayed(0, 2500);

    }

    public boolean isFirst() {

        return preferences.getBoolean("isFirst", true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isend = true;

    }
}
