package com.example.administrator.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends Activity {

    private ProgressBar pb_contacts;

    private ListView listView;

    private List<Map<String, String>> list;

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            listView.setAdapter(new MyAdapter());
//        }
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        new MyAsycnTask(){

            @Override
            public void preTask() {
                listView = (ListView) findViewById(R.id.lv_contact);
                pb_contacts = (ProgressBar) findViewById(R.id.pb_contacts);
                pb_contacts.setVisibility(View.VISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent();
                        intent.putExtra("num", list.get(i).get("phone"));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }

            @Override
            public void doinBack() {
                list = getContactList();
            }

            @Override
            public void postTask() {
                listView.setAdapter(new MyAdapter());
                pb_contacts.setVisibility(View.INVISIBLE);
            }
        }.execute();


//        new AsyncTask<Void,Void,Void>(){
//            @Override
//            protected void onPreExecute() {
//
//            }
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//
//            }
//        };


    }


    public List<Map<String, String>> getContactList() {
        SystemClock.sleep(3000);

        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        for (int i = 1; i <= 10; i++) {
            map = new HashMap();
            map.put("name", "我是" + i + "号");
            map.put("phone", i + "");
            list.add(map);
        }
        return list;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View item = View.inflate(getApplicationContext(), R.layout.contact_item, null);
            TextView tv_name = (TextView) item.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) item.findViewById(R.id.tv_phone);
            tv_name.setText(list.get(i).get("name"));
            tv_phone.setText(list.get(i).get("phone"));
            return item;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
    }
}
