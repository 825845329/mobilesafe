package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SetUp3Activity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setup3);
    }


    public void pre(View view){
        Intent intent = new Intent(this,SetUp2Activity.class);
        startActivity(intent);
    }

    public void next(View view){
        Intent intent = new Intent(this,SetUp4Activity.class);
        startActivity(intent);
    }
}
