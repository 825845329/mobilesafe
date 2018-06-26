package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SetUp1Activity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);

    }

    public void next(View view){
        Intent intent = new Intent(SetUp1Activity.this,SetUp2Activity.class);
        startActivity(intent);
    }
}
