package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import ui.SettingView;

public class SettingActivity extends Activity{

    private SettingView activity_setting;
    private SharedPreferences sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        activity_setting = (SettingView)findViewById(R.id.activity_setting);
//        activity_setting.setTitle("提示更新");

        sp = getSharedPreferences("config",MODE_PRIVATE);

        if(sp.getBoolean("update",true)){
//            activity_setting.setDesc("打开提示更新");
            activity_setting.setChecked(true);
        }else{
//            activity_setting.setDesc("关闭提示更新");
            activity_setting.setChecked(false);

        }


        activity_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor edit = sp.edit();

                if(activity_setting.isChecked()==true){
//                    activity_setting.setDesc("关闭提示更新");
                    activity_setting.setChecked(false);
                    edit.putBoolean("update",false);

                }else {
//                    activity_setting.setDesc("打开提示更新");
                    activity_setting.setChecked(true);
                    edit.putBoolean("update",true);
                }
                edit.commit();
            }
        });


    }
}
