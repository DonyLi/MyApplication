package com.example.qq_2012.avtivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.qq_2012.R;
import com.example.qq_2012.util.Constants;
import com.example.qq_2012.util.SharePreferenceUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class WelcomeActivity extends AppCompatActivity {
    private SharePreferenceUtil util;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        util = new SharePreferenceUtil(WelcomeActivity.this, Constants.SAVE_USER);
        if (util.getisFirst()) {
            moveSound();
        }
        if (util.getIsStart()) {
            goFriendlistActivity();
        } else {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goLoginActivity();
                }


            }, 1000);
        }

    }

    private void goLoginActivity() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }

    private void goFriendlistActivity() {
        Intent intent = new Intent(WelcomeActivity.this, FriendListActivity.class);
        startActivity(intent);
        util.setIsStart(false);
        WelcomeActivity.this.finish();
    }

    private void moveSound() {
        InputStream is = getResources().openRawResource(R.raw.msg);
        File file = new File(getFilesDir(), "msg.mp3");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
