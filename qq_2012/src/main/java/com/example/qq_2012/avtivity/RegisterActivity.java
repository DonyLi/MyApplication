package com.example.qq_2012.avtivity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq_2012.R;

import messageEntity.MyMessage;

public class RegisterActivity extends AppCompatActivity {
    EditText reg_name, reg_password, reg_password2;
    Button register_btn;
    String id;

    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_password = (EditText) findViewById(R.id.reg_password);
        reg_password2 = (EditText) findViewById(R.id.reg_password2);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s1 = reg_password.getText().toString();
                String s2 = reg_password2.getText().toString();

                if (s1.length() == 0 || s2.length() == 0 || reg_name.getText().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "不能为空，请输入", Toast.LENGTH_LONG).show();
                } else if (!s1.equals(s2)) {
                    Toast.makeText(RegisterActivity.this, "您两次输入的密码不一样，请重新输入", Toast.LENGTH_LONG).show();
                } else {

                    String s3 = reg_name.getText().toString();
                    MyMessage ms = new MyMessage();
                    ms.message = "regist*name=" + s3 + "&password=" + s1;
                    ms.imei = telephonyManager.getDeviceId();
                    HandleMessage.dealTCP(ms, new HandleMessage.OnCallback() {
                        @Override
                        public void callback(String get) {
                            if (get != null) {
                                id = get.substring(2, get.length());
                                Toast.makeText(RegisterActivity.this, "注册成功,分配的ID为" + id, Toast.LENGTH_LONG).show();

                            }
                        }
                    });


                }

            }
        });

    }
}
