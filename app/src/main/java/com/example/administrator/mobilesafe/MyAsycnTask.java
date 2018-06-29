package com.example.administrator.mobilesafe;

import android.os.Handler;
import android.os.Message;

public abstract class MyAsycnTask {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            postTask();
        }
    };

    public abstract void preTask();

    public abstract void doinBack();

    public abstract void postTask();

    public void execute(){
        preTask();
        new Thread(){
            @Override
            public void run() {
                doinBack();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

}
