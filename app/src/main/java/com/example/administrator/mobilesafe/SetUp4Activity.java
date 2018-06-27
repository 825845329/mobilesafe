package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SetUp4Activity extends SetUpBaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
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

    }
}
