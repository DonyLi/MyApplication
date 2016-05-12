package com.example.qq_2012.avtivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq_2012.R;
import com.lidroid.xutils.BitmapUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import messageEntity.MyMessage;

public class MySettings extends Activity implements View.OnClickListener {
    RelativeLayout head, name, sex, desc;
    Bitmap bmp;
    ImageView showHead;
    BitmapUtils bitmapUtils;
    TextView showName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        head = (RelativeLayout) findViewById(R.id.setHead);
        head.setOnClickListener(this);
        name = (RelativeLayout) findViewById(R.id.setName);
        showName = (TextView) findViewById(R.id.showName);
        showName.setText(Params.CreateParams().persons.get(Params.CreateParams().myID).user.getName());
        name.setOnClickListener(this);
        sex = (RelativeLayout) findViewById(R.id.setSex);
        sex.setOnClickListener(this);
        desc = (RelativeLayout) findViewById(R.id.setDesc);
        showHead = (ImageView) findViewById(R.id.showHead);
        bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(showHead, Params.CreateParams().persons.get(Params.CreateParams().myID).user.getHeadCode());
    }

    public void setHead() {
        try {
            final File file = new File(Environment.getExternalStorageDirectory()
                    .getCanonicalPath() + "/head.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, b);
            b.reset();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, b);
            fileOutputStream.write(b.toByteArray());
            fileOutputStream.close();
            HandleMessage.upLoadHead(file.getCanonicalPath(), new HandleMessage.OnCallback() {
                @Override
                public void callback(String get) {
                    file.delete();
                    MyMessage message = new MyMessage();
                    message.message = "sethead" + get;
                    message.imei = Params.CreateParams().imei;
                    final String path = get;
                    HandleMessage.dealTCP(message, new HandleMessage.OnCallback() {
                        @Override
                        public void callback(String get) {
                            if (get.equals("ok")) {
                                Params.CreateParams().persons.get(Params.CreateParams().myID).user.setHeadCode(path);
                                Toast.makeText(MySettings.this, "设置成功", Toast.LENGTH_LONG).show();
                                setResult(2, new Intent());
                            }
                        }
                    });
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == head) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        } else if (v == name) {
            Intent intent = new Intent(MySettings.this, UploadName.class);
            intent.putExtra("name", showName.getText().toString());
            startActivityForResult(intent, 0x1);
        }else if (v==sex){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            ContentResolver resolver = MySettings.this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                double bl = width * 1.0 / height;
                if (height > 200) {
                    bmp = Bitmap.createScaledBitmap(bitmap, (int) (200 * bl), 200, true);
                    bitmap.recycle();
                } else {
                    bmp = bitmap;
                }
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, b);
                int options = 100;
                while (b.toByteArray().length / 200 > 1024) {
                    b.reset();
                    bmp.compress(Bitmap.CompressFormat.JPEG, options, b);
                    options -= 10;
                }
                ByteArrayInputStream inputStream = new ByteArrayInputStream(b.toByteArray());
                bmp.recycle();
                bmp = BitmapFactory.decodeStream(inputStream);
                showHead.setImageBitmap(bmp);
                setHead();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 0x1) {
            if (data == null)
                return;
            Log.e("getname", data.getStringExtra("name"));
            showName.setText(data.getStringExtra("name"));
            setResult(2, new Intent());
        }


    }
}
