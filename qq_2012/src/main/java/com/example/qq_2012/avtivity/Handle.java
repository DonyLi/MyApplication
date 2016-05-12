package com.example.qq_2012.avtivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import messageEntity.MyMessage;

public class Handle extends Service {
    public Handle() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                Log.e("tick", "...................");
                MyMessage message = new MyMessage();
                while (true) {
                    message.imei = Params.CreateParams().imei;
                    message.message = "tick";
                    HandleMessage.sendMessage(message);
                    try {
                        sleep(3000 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                HandleMessage.recieve();

            }
        }.start();
    }
}
