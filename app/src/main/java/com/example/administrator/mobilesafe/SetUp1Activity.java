package com.example.administrator.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class SetUp1Activity extends SetUpBaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);

    }

    @Override
    public void pre_activity() {

    }

    @Override
    public void next_activity() {
        Intent intent = new Intent(SetUp1Activity.this,SetUp2Activity.class);
        startActivity(intent);
        finish();
    }
}
