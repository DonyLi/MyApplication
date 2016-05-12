package com.example.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Jason on 2016/3/14.
 */
public class FishView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private boolean hasSurface;
    private Bitmap back;
    private UpdateViewThread updateViewThread;
    private Bitmap[] fishs;
    private int fishIndex = 0;
    private float fishX = new Random().nextInt(MainActivity.widthPixels);
    private float fishY = new Random().nextInt(MainActivity.heightPixels);
    private float fishSpeed = 6;
    private int fishAngle = new Random().nextInt(60);
    Matrix matrix = new Matrix();


    public FishView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        hasSurface = false;
        back = BitmapFactory.decodeResource(context.getResources(), R.drawable.fishbg);
        fishs = new Bitmap[10];
        for (int i = 0; i < 10; i++) {
            try {
                int fishId = (int) R.drawable.class.getField("fish" + i).get(null);
                fishs[i] = BitmapFactory.decodeResource(context.getResources(), fishId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        if (updateViewThread == null) {
            updateViewThread = new UpdateViewThread();
            if (hasSurface == true) {
                updateViewThread.start();
            }
        }
    }

    public void onPause() {
        if (updateViewThread != null) {
            updateViewThread.requestExitAndWait();
            updateViewThread = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hasSurface = true;
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (updateViewThread != null) {
            updateViewThread.onWindowResize(width, height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
        onPause();
    }

    class UpdateViewThread extends Thread {
        private boolean done;

        UpdateViewThread() {
            super();
            done = false;
        }

        @Override
        public void run() {
            SurfaceHolder surfaceHolder = holder;
            while (!done) {
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawBitmap(back, 0, 0, null);
                if (fishX < 0) {
                    fishX = MainActivity.widthPixels;
                    fishY = MainActivity.heightPixels;
                    fishAngle = new Random().nextInt(60);

                }
                if (fishY < 0) {
                    fishX = MainActivity.widthPixels;
                    fishY = MainActivity.heightPixels;
                    fishAngle = new Random().nextInt(60);
                }
                matrix.reset();
                matrix.setRotate(fishAngle);
                matrix.postTranslate(fishX -= fishSpeed * Math.cos(Math.toRadians(fishAngle)), fishY -= fishSpeed *
                        Math.sin(Math.toRadians(fishAngle)));
                canvas.drawBitmap(fishs[fishIndex++ % fishs.length], matrix, null);
                surfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void requestExitAndWait() {
            done = true;
            try {
                join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void onWindowResize(int w, int h) {

        }


    }


}

