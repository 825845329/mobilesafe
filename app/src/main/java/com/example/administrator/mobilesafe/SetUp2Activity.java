package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SetUp2Activity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    public void pre(View view){
        Intent intent = new Intent(this,SetUp1Activity.class);
        startActivity(intent);
    }

    public void next(View view){
        Intent intent = new Intent(this,SetUp3Activity.class);
        startActivity(intent);
    }

}
