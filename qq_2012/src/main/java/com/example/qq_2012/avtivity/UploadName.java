package com.example.qq_2012.avtivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qq_2012.R;

import messageEntity.MyMessage;

public class UploadName extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    Button save;
    EditText changeName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_name);
        back = (ImageView) findViewById(R.id.back);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        changeName = (EditText) findViewById(R.id.changeName);
        back.setOnClickListener(this);
        intent = getIntent();
        changeName.setText(intent.getStringExtra("name"));
    }

    @Override
    public void onClick(View v) {
        if (v == back) {
            onBackPressed();
        } else if (v == save) {
            MyMessage message = new MyMessage();
            final String name = changeName.getText().toString();
            message.message = "setname" + name;
            message.imei = Params.CreateParams().imei;
            HandleMessage.dealTCP(message, new HandleMessage.OnCallback() {
                @Override
                public void callback(String get) {
                    if (get.equals("ok")) {
                        Params.CreateParams().persons.get(Params.CreateParams().myID).user.setName(name);
                        Toast.makeText(UploadName.this, "设置昵称成功", Toast.LENGTH_LONG).show();
                        intent.putExtra("name", name);
                        setResult(0x2, intent);
                        UploadName.this.finish();
                    }
                }
            });
        }
    }
}
