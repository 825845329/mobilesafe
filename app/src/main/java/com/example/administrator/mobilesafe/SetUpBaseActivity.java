package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/6/26.
 */

public abstract class SetUpBaseActivity extends Activity {

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
