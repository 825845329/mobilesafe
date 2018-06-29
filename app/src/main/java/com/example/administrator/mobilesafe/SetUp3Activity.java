package com.example.administrator.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class SetUp3Activity extends SetUpBaseActivity {

    private EditText et_safePhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        et_safePhone = (EditText) findViewById(R.id.et_safePhone);

    }


    @Override
    public void pre_activity() {
        Intent intent = new Intent(this, SetUp2Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.setup_pre_enter, R.anim.setup_pre_exit);
    }

    @Override
    public void next_activity() {
        Intent intent = new Intent(this, SetUp4Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.setup_next_enter, R.anim.setup_next_exit);
    }


    /**
     * 获取联系人
     *
     * @param view
     */
    public void getContact(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String num = data.getStringExtra("num");
            et_safePhone.setText(num);
        }

    }


}
