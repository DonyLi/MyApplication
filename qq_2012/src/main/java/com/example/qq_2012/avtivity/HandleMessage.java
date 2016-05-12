package com.example.qq_2012.avtivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import messageEntity.Code;
import messageEntity.MyMessage;

/**
 * Created by Jason on 2016/3/29.
 */
public class HandleMessage {
    public static DatagramSocket socket;

    public static void dealTCP(final MyMessage ms, final OnCallback callback) {
        final StringBuffer stringBuffer = new StringBuffer();
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                callback.callback(stringBuffer.toString());

                return false;
            }
        });
        new Thread() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket("120.24.167.185", 1098);
                    OutputStream os = socket.getOutputStream();
                    os.write(Code.code(ms, "mycodes"));
                    os.flush();
                    socket.shutdownOutput();

                    InputStream is = socket.getInputStream();
                    byte[] bs = new byte[5120];
                    StringBuffer sb = new StringBuffer();
                    int total = is.read(bs);
                    sb.append(new String(Code.decodeByte(bs, "mycodes"), 0, total, "UTF-8"));
                    is.close();
                    socket.close();
                    stringBuffer.append(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }.start();


    }

    public interface OnCallback {
        void callback(String get);
    }

    public static void sendMessage(MyMessage ms) {

        if (socket == null) {
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        try {
            InetAddress address = InetAddress.getByName("120.24.167.185");
            byte[] temp = Code.code(ms, "mycodes");
            DatagramPacket packet = new DatagramPacket(temp, temp.length, address, 20998);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void recieve() {
        if (socket == null) {
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            byte[] reciveByte = new byte[1024];
            DatagramPacket packet = new DatagramPacket(reciveByte, reciveByte.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final MyMessage myMessage = Code.decode(reciveByte, 0, packet.getLength(), "mycodes");
            Log.e("recieve", myMessage.message);
            if (!(myMessage.message.equals("tock") || myMessage.message.equals("ok") || myMessage.message.startsWith
                    ("*reply*") || myMessage.message.startsWith("log"))) {

                String id = myMessage.message.substring(0, 5);
                if (!Params.CreateParams().persons.get(id).allMessage.contains(myMessage)) {
                    Params.CreateParams().persons.get(id).allMessage.add(myMessage);
                    ChatActivity.handler.sendEmptyMessage(0x111);
                }
                new Thread() {
                    @Override
                    public void run() {
                        MyMessage message = new MyMessage();
                        message.imei = Params.CreateParams().imei;
                        message.message = "*reply*";
                        message.number = myMessage.number;
                        HandleMessage.sendMessage(message);
                    }
                }.start();
            }


        }

    }

    public static void upLoadHead(final String path, final OnCallback callback) {
       
        final StringBuffer buffer = new StringBuffer();
        final Handler han = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                callback.callback(buffer.toString());
                return false;
            }
        });
        new Thread() {
            @Override
            public void run() {
                try {

                    if (path != null) {
                        File file = new File(path);
                        Socket sock = new Socket("120.24.167.185", 20999);
                        OutputStream os = sock.getOutputStream();
                        sendFile(file, os);
                        sock.shutdownOutput();
                        byte[] tempGet = new byte[1024];
                        InputStream is = sock.getInputStream();
                        StringBuffer sb = new StringBuffer();
                        int number = 0;
                        while ((number = is.read(tempGet)) != -1) {
                            sb.append(new String(tempGet, 0, number, "UTF-8"));
                        }
                        String httpUrl = sb.toString();
                        sock.close();
                        buffer.append(httpUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                han.sendEmptyMessage(0);
            }
        }.start();

    }

    public static void sendFile(File file, OutputStream os) {
        try {
            FileInputStream fis = new FileInputStream(file);
            int total = 0;
            byte[] temp = new byte[1024];
            while ((total = fis.read(temp)) != -1) {
                os.write(temp, 0, total);
            }

            fis.close();
        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }
    }


}
