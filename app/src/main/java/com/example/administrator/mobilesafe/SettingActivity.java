package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ui.SettingClickView;
import ui.SettingView;

public class SettingActivity extends Activity {

    private SettingView activity_setting;
    private SharedPreferences sp;
    private SettingClickView scv_setting_changedbg;
    private SettingClickView scv_setting_location;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        activity_setting = (SettingView) findViewById(R.id.activity_setting);
        scv_setting_changedbg = (SettingClickView) findViewById(R.id.scv_setting_changedbg);
        scv_setting_location = (SettingClickView) findViewById(R.id.scv_setting_location);

        update();
        changedbg();
        location();
    }

    private void location() {
        scv_setting_location.setTitle("归属地提示框位置");
        scv_setting_location.setDesc("设置归属地提示框的显示位置");

        scv_setting_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,DragViewActivity.class);
                startActivity(intent);
            }
        });

    }


    private void update() {
        sp = getSharedPreferences("config", MODE_PRIVATE);

        if (sp.getBoolean("update", true)) {
            activity_setting.setChecked(true);
        } else {
            activity_setting.setChecked(false);
        }

        activity_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor edit = sp.edit();
                if (activity_setting.isChecked() == true) {
                    activity_setting.setChecked(false);
                    edit.putBoolean("update", false);
                } else {
                    activity_setting.setChecked(true);
                    edit.putBoolean("update", true);
                }
                edit.commit();
            }
        });
    }

    private void changedbg() {
        final String[] items = {"半透明","活力橙","卫士蓝","金属灰","苹果绿"};

        scv_setting_changedbg.setTitle("归属地提示框风格");
        scv_setting_changedbg.setDesc(items[sp.getInt("which",0)]);

        scv_setting_changedbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setIcon(R.drawable.luncher_bg);
                builder.setTitle("归属地提示框风格");

                //设置单选框
                //items : 选项的文本的数组
                //checkedItem : 选中的选项
                //listener : 点击事件
                builder.setSingleChoiceItems(items, sp.getInt("which",0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scv_setting_changedbg.setDesc(items[i]);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putInt("which",i);
                        edit.commit();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });


    }
}
