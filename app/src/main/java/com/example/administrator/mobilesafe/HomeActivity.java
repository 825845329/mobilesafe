package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {


    private GridView gv_content;
    private AlertDialog passwordDialog;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences("config",MODE_PRIVATE);

        initView();

        initAdapter();
    }


    private void initView() {
        gv_content = findViewById(R.id.gv_content);
        gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {

                    case 0:
                        String sp_password = sp.getString("password","");
                        if(TextUtils.isEmpty(sp_password)){
                            showEnterPasswordDialog();
                        }else{
                            enterInputPassword();
                        }

                        break;
                    case 8:
                        intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }


            }
        });

    }


    /**
     * 输入验证密码
     */
    private void enterInputPassword() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);

        View view = View.inflate(getApplicationContext(), R.layout.activity_enterpassword, null);

        final EditText et_setPassword = view.findViewById(R.id.et_setPassword);
        Button bnt_ok = view.findViewById(R.id.bnt_ok);
        Button bnt_cancel = view.findViewById(R.id.bnt_cancel);



        bnt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  etp = et_setPassword.getText().toString().trim();
                String sp_password = sp.getString("password","");
                if(sp_password.equals(etp)){
                    Toast.makeText(HomeActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
                    passwordDialog.dismiss();
                    Intent intent = new Intent(HomeActivity.this , LostFindActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(HomeActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });


        bnt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordDialog.dismiss();
                //passwordDialog.cancel();
            }
        });
        builder.setView(view);
        passwordDialog = builder.create();
        passwordDialog.show();

    }

    /**
     * 输入密码对话框
     */
    private void showEnterPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);

        View view = View.inflate(getApplicationContext(), R.layout.activity_setpassword, null);

        final EditText et_setPassword = view.findViewById(R.id.et_setPassword);
        final EditText et_confirmPassword = view.findViewById(R.id.et_confirmPassword);
        Button bnt_ok = view.findViewById(R.id.bnt_ok);
        Button bnt_cancel = view.findViewById(R.id.bnt_cancel);

        bnt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String  password = et_setPassword.getText().toString().trim();
               String  confirmPassword = et_confirmPassword.getText().toString().trim();
               if(password.equals(confirmPassword)){
                   Toast.makeText(HomeActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                   SharedPreferences.Editor edit = sp.edit();
                   edit.putString("password",password);
                   edit.commit();
                   passwordDialog.dismiss();
               }
            }
        });

        bnt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordDialog.dismiss();
                //passwordDialog.cancel();
            }
        });
        builder.setView(view);
        passwordDialog = builder.create();
        passwordDialog.show();


    }

    private void initAdapter() {
        gv_content.setAdapter(new MyAdapter());

    }


    private class MyAdapter extends BaseAdapter {

        int[] imageId = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
                R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
                R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings};
        String[] names = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理",
                "高级工具", "设置中心"};
        // 设置条目的个数

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.activity_home_itme, null);
            ImageView imageView = view.findViewById(R.id.iv_icon);
            TextView textView = view.findViewById(R.id.tv__text);

            imageView.setImageResource(imageId[position]);
            textView.setText(names[position]);
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
