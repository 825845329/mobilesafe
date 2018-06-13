package com.example.administrator.mobilesafe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.DownloadUtil;

public class SplashActivity extends Activity {


    protected static final int MSG_UPDATE_DIALOG = 1;
    private static final int MSG_HTTP_FAILURE = 2;
    private static final int MSG_IO_ERROR = 3;
    private static final int MSG_JSON_ERROR = 4 ;
    private TextView tv_version;
    private ProgressBar pb_bar;
    private String code;
    private String apkUrl;
    private String desc;

    private String versionName;
    private String sdPath;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

         switch(msg.what){
             case MSG_UPDATE_DIALOG:
             showDialog();
          break;
             case MSG_HTTP_FAILURE:
                 Toast.makeText(getApplicationContext(),"请检查你的网络",Toast.LENGTH_LONG);
                 enterHome();
                 break;
             case MSG_IO_ERROR:
                 Toast.makeText(getApplicationContext(),"错误号"+MSG_IO_ERROR,Toast.LENGTH_LONG);
                 enterHome();
                 break;
             case MSG_JSON_ERROR:
                 Toast.makeText(getApplicationContext(),"错误号"+MSG_JSON_ERROR,Toast.LENGTH_LONG);
                 enterHome();
                 break;
             default:
          break;
         }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        initView();

        update();

        initData();

        requestPermission();

    }


    private void initView() {
        tv_version = findViewById(R.id.tv_version);
        pb_bar = findViewById(R.id.pb_bar);
    }


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
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getPath();

//
//            File file = new File(sdPath,"app-debug.apk");

            DownloadUtil.get().download("http://116.62.124.102:28080/app-debug.apk", sdPath, new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    System.out.println("下载完成");
                    //installApk();

                    checkIsAndroidO();
//                    installApkFile(getBaseContext(),"/mnt/sdcard/Download/app-debug.apk");
                }

                @Override
                public void onDownloading(int progress) {
                    pb_bar.setProgress(progress);
                }

                @Override
                public void onDownloadFailed() {
                    System.out.println("下载失败");
                }
            });
        }
    }


    /**
     * 弹出安卓apk的弹窗
     */
    private void installApk() {
        /**
         *  <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="content" /> //content : 从内容提供者中获取数据  content://
         <data android:scheme="file" /> // file : 从文件中获取数据
         <data android:mimeType="application/vnd.android.package-archive" />
         </intent-filter>
         */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File( sdPath,"app-debug.apk")),"application/vnd.android.package-archive");
        startActivityForResult(intent,0);


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
                   message.what = MSG_HTTP_FAILURE;
               }
               //请求成功执行的方法
               @SuppressLint("ShowToast")
               @Override
               public void onResponse(Call call, Response response)  {
                   System.out.println("请求成功");
                   try {

                       String dataJson = response.body().string();
                       JSONObject jsonObject = new JSONObject(dataJson);
                       code = jsonObject.get("code").toString();
                       apkUrl = jsonObject.get("apkUrl").toString();
                       desc = jsonObject.get("desc").toString();

                       //如果code的版本和软件的版本不一致就弹出对话框
                       if(!code.equals(versionName)){
                           message.what = MSG_UPDATE_DIALOG;
                       }

                   } catch (IOException e) {
                       e.printStackTrace();
                       message.what = MSG_IO_ERROR;
                   } catch (JSONException e) {
                       e.printStackTrace();
                       message.what = MSG_JSON_ERROR;
                   } finally {
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


    /**
     * 请求授权
     */
    private void requestPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){ //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){ //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED){ //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},3);
        }

    }


    /**
     * 权限申请返回结果
     * @param requestCode 请求码
     * @param permissions 权限数组
     * @param grantResults  申请结果数组，里面都是int类型的数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //同意权限申请

                }else { //拒绝权限申请
                    requestPermission();
                }
                break;
            default:
                break;
        }
    }


    public static void installApkFile(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.example.administrator.mobilesafe", new File(filePath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType( Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }



    /**
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    private void checkIsAndroidO() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            System.out.println("是否有安装权限:" + b);
            if (b) {
                installApkFile(getBaseContext(),sdPath+"/app-debug.apk");//安装应用的逻辑(写自己的就可以)
            } else {
                //请求安装未知应用来源的权限
                requestPermission();
            }
        } else {
            installApkFile(getBaseContext(),sdPath+"/app-debug.apk");
        }

    }
}
