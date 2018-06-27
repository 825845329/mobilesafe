package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/6/26.
 */

public abstract class SetUpBaseActivity extends Activity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureDetector = new GestureDetector(this ,new MyOnGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float startX = e1.getX();
            float endX = e2.getX();
            float startY = e1.getY();
            float endY = e2.getY();

            if(Math.abs(startY-endY) > 50){
                Toast.makeText(getApplicationContext(),"你又乱来了",Toast.LENGTH_SHORT).show();
                return false;
            }

            if((startX - endX) > 0){
                next_activity();
            }
            if((startX - endX) < 0){
                pre_activity();
            }

            return true;
        }
    }

    public void pre(View view){
        pre_activity();
    }

    public void next(View view){
        next_activity();
    }

   public abstract void  pre_activity();
   public abstract void  next_activity();


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            pre_activity();
        }
        return super.onKeyDown(keyCode, event);
    }
}
