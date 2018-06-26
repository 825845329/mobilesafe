package com.example.administrator.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class SetUp3Activity extends SetUpBaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setup3);
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(this,SetUp2Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void next_activity() {
        Intent intent = new Intent(this,SetUp4Activity.class);
        startActivity(intent);
        finish();
    }
}
