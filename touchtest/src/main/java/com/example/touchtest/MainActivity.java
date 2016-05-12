package com.example.touchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGroup myGroup = new MyGroup(this);
        MyGroup2 myGroup2 = new MyGroup2(this);
        MyTextView myTextView = new MyTextView(this);
        myTextView.setText("测试");
        myGroup2.addView(myTextView);
        myGroup.addView(myGroup2);

        setContentView(myGroup);
    }
}
