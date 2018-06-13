package com.example.administrator.mobilesafe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.DownloadUtil;

public class SplashActivity extends Activity {

    private static final int MSG_HTTP_FAILE = 0;
    private TextView tv_version;
    private String code;
    private String apkUrl;
    private String desc;
    protected static final int MSG_UPDATE_DIALOG = 1;
    private String versionName;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

         switch(msg.what){
             case MSG_UPDATE_DIALOG:
             showDialog();
          break;

             default:
          break;
         }

        }
    };

    /**
     * 更新弹窗
     */
    private void showDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(false);
    builder.setTitle("新版本" + code);
    builder.setIcon(R.drawable.ic_launcher);
    builder.setMessage(desc);
    builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
             downLoadApk();

        }
    });
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            enterHome();
        }
    });
    builder.show();
    }

    /**
     * 下载文件并安装
     */
    private void downLoadApk() {

        DownloadUtil.get().download("http://116.62.124.102:28080/app-debug.apk", "/mnt/sdcard/app-debug.apk", new DownloadUtil.OnDownloadListener() {
        @Override
        public void onDownloadSuccess() {
            System.out.println("下载完成");
        }
        @Override
        public void onDownloading(int progress) {
            progressBar.setProgress(progress);
        }
        @Override
        public void onDownloadFailed() {
            System.out.println("下载失败");
        }
    });
    }


//    DownloadUtil.get().download(url, "download", new DownloadUtil.OnDownloadListener() {
//        @Override
//        public void onDownloadSuccess() {
//            Utils.showToast(MainActivity.this, "下载完成");
//        }
//        @Override
//        public void onDownloading(int progress) {
//            progressBar.setProgress(progress);
//        }
//        @Override
//        public void onDownloadFailed() {
//            Utils.showToast(MainActivity.this, "下载失败");
//        }
//    });




    /**
     * 跳转首页
     */
    private void enterHome() {
        Intent intent = new Intent(this ,HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        initView();

        update();

        initData();


    }

    private void update() {


   new Thread(){
       @Override
       public void run() {

           final long startTime = System.currentTimeMillis();

           final Message message = new Message();
           //1.创建OkHttpClient对象
           OkHttpClient okHttpClient = new OkHttpClient();
           //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
           final Request request = new Request.Builder().url("http://116.62.124.102:28080/updateInfo.html").method("GET",null).build();
           //3.创建一个call对象,参数就是Request请求对象
           Call call = okHttpClient.newCall(request);
           //4.请求加入调度，重写回调方法
           call.enqueue(new Callback() {
               //请求失败执行的方法
               @SuppressLint("ShowToast")
               @Override
               public void onFailure(Call call, IOException e) {
                   System.out.println("请求失败");
                   message.what = MSG_HTTP_FAILE;
               }
               //请求成功执行的方法
               @SuppressLint("ShowToast")
               @Override
               public void onResponse(Call call, Response response) throws IOException {
                   System.out.println("请求成功");
                   String dataJson = response.body().string();
                   try {
                       JSONObject jsonObject = new JSONObject(dataJson);
                       code = jsonObject.get("code").toString();
                       apkUrl = jsonObject.get("apkUrl").toString();
                       desc = jsonObject.get("desc").toString();

                       //如果code的版本和软件的版本不一致就弹出对话框
                       if(!code.equals(versionName)){
                           message.what = MSG_UPDATE_DIALOG;
                       }

                   } catch (Exception e) {
                       e.printStackTrace();
                   }finally {
                       long endTime = System.currentTimeMillis();
                       long time = startTime - endTime;
                       if(time<2000){
                           SystemClock.sleep(2000-time);
                       }
                       handler.sendMessage(message);
                   }


               }
           });

       }
   }.start();





//        new Thread(){
//            @Override
//            public void run() {
//
//                try {
//                    URL url = new URL("http://192.168.2.120:8080/updateInfo.html");
//                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                    conn.setReadTimeout(5000);
//                    conn.setRequestMethod("POST");
//                    int responseCode = conn.getResponseCode();
//                    if(responseCode == 200){
//                        Toast.makeText(SplashActivity.this,"请求成功",Toast.LENGTH_SHORT);
//                        System.out.println("请求成功");
//                    }else{
//                        Toast.makeText(SplashActivity.this,"请求失败",Toast.LENGTH_SHORT);
//                        System.out.println("请求失败");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }

    private void initData() {
        versionName = getVersionName();
        tv_version.setText(versionName);
    }

    private void initView() {
        tv_version = findViewById(R.id.tv_version);
    }


    public String getVersionName() {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        String versionName = packageInfo.versionName;
        return versionName;
    }
}
