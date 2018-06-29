package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class QueryPhoneActivity extends Activity {

    private EditText et_queryPhone;
    private TextView tv_address;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryphone);

        et_queryPhone = findViewById(R.id.et_queryPhone);
        tv_address = findViewById(R.id.tv_address);

        et_queryPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = et_queryPhone.getText().toString().trim();
                if ("1".equals(phone)) {
                    tv_address.setText("哈哈我是一");
                } else if ("2".equals(phone)) {
                    tv_address.setText("哈哈我是二");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });


    }
}
