package com.example.qq_2012.avtivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq_2012.R;
import com.google.gson.Gson;

import messageEntity.AllUsers;
import messageEntity.MyMessage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnRegister;
    private Button mBtnLogin;
    private EditText mAccounts, mPassword;
    private CheckBox mAutoSavePassword;
    TelephonyManager telephonyManager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CheckBox checkBox;
    AllUsers all;
    private MenuInflater mi;// 菜单
    String str1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("account", MODE_PRIVATE);
        editor = preferences.edit();
        checkBox = (CheckBox) findViewById(R.id.auto_save_password);
        mi = new MenuInflater(this);
        mBtnRegister = (Button) findViewById(R.id.regist_btn);
        mBtnRegister.setOnClickListener(this);
        mAccounts = (EditText) findViewById(R.id.lgoin_accounts);
        mPassword = (EditText) findViewById(R.id.login_password);
        mAccounts.setText(preferences.getString("id", null));
        mPassword.setText(preferences.getString("pwd", null));
        mBtnLogin = (Button) findViewById(R.id.login_btn);
        mBtnLogin.setOnClickListener(this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regist_btn:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                MyMessage ms = new MyMessage();
                final String id = mAccounts.getText().toString();
                final String pwd = mPassword.getText().toString();
                Log.e(id, pwd);
                ms.message = "login*id=" + id + "&password=" + pwd;
                //"sethead"+url
                Params.CreateParams().imei = ms.imei = telephonyManager.getDeviceId();
                HandleMessage.dealTCP(ms, new HandleMessage.OnCallback() {
                    @Override
                    public void callback(String get) {
                        if (get.equals("ok")) {
                            if (checkBox.isChecked()) {
                                editor.putString("id", id);
                                editor.putString("pwd", pwd);
                            } else {
                                editor.putString("id", "");
                                editor.putString("pwd", "");
                            }
                            editor.commit();
                            MyMessage ms1 = new MyMessage();
                            ms1.message = "getdata";
                            ms1.imei = telephonyManager.getDeviceId();
                            HandleMessage.dealTCP(ms1, new HandleMessage.OnCallback() {
                                @Override
                                public void callback(String get) {
                                    Log.e("444444", get);
                                    Gson gson = new Gson();
                                    all = gson.fromJson(get, AllUsers.class);
                                    Params.CreateParams().all = all;
                                    Params.CreateParams().persons.put(Params.CreateParams().myID = all.me.getId(), new
                                            Person(all
                                            .me));
                                    for (int i = 0; i < all.list.size(); i++) {
                                        Params.CreateParams().persons.put(all.list.get(i).getId(), new Person(all.list.get
                                                (i)));
                                    }
                                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, FriendListActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }
                            });


                        }
                    }
                });


                break;


        }

    }
}
