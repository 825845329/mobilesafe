package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LostFindActivity extends Activity{

    private SharedPreferences sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("config",MODE_PRIVATE);


        //如果为第一次就跳转到手机向导界面，如果不是就跳转到设置界面
        if(sp.getBoolean("first",true)){
            Intent intent = new Intent(this,SetUp1Activity.class);
            startActivity(intent);
            finish();
        }else{
            setContentView(R.layout.activity_lostfind);

            TextView textView = (TextView) findViewById(R.id.tv_again);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),SetUp1Activity.class);
                    startActivity(intent);
                }
            });

        }

    }
}
