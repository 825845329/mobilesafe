package com.example.administrator.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class SetUp4Activity extends SetUpBaseActivity{

    private SharedPreferences sp;
    private CheckBox ck_protect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        setContentView(R.layout.activity_setup4);

        ck_protect = (CheckBox)findViewById(R.id.ck_protect);

        boolean ckeckBox_boolean = sp.getBoolean("ck_protect",false);
        ck_protect.setChecked(ckeckBox_boolean);
        if(ckeckBox_boolean){
            ck_protect.setText("您已经开启防护保护");
        }else{
            ck_protect.setText("您已经关闭防护保护");
        }

        ck_protect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor edit = sp.edit();
               if(b){
                   ck_protect.setText("您已经开启防护保护");
                   ck_protect.setChecked(true);
                   edit.putBoolean("ck_protect",true);
               }else{
                   ck_protect.setText("您已经关闭防护保护");
                   ck_protect.setChecked(false);
                   edit.putBoolean("ck_protect",false);
               }
               edit.commit();
            }
        });
    }


    @Override
    public void pre_activity() {
        Intent intent = new Intent(this,SetUp3Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.setup_pre_enter,R.anim.setup_pre_exit);
    }

    @Override
    public void next_activity() {

        Intent intent = new Intent(this,LostFindActivity.class);
        startActivity(intent);
        finish();

        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("first",false);
        edit.commit();

    }
}
