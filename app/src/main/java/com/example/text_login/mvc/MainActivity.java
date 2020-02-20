package com.example.text_login.mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.text_login.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_name, edit_pwd;
    private Button bt_login, bt_exit;
    OkHttpClient okHttpClient;
    private static final String TAG = "MainActivity";
    ProgressDialog waitingDialog;
     AlertDialog.Builder normalDialog;
    String Username,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_view();

        //初始化okhttpclient
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(6000, TimeUnit.MILLISECONDS)
                .build();
    }

    private void init_view() {
        edit_name = findViewById(R.id.et_username);
        edit_pwd = findViewById(R.id.et_pwd);
        bt_login = findViewById(R.id.bt_login);
        bt_exit = findViewById(R.id.bt_exit);
        bt_login.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String et_name = edit_name.getText().toString();
                String et_pwd = edit_pwd.getText().toString();
                if (et_name != null && et_pwd != null) {
                    init_succees(et_name, et_pwd);
                }

                break;
            case R.id.bt_exit:
                finish();
                break;
        }
    }

    private void init_succees(String name, String pwd) {
        RequestBody body = new FormBody.Builder()
                .add("username", name)
                .add("password", pwd)
                .build();
        Request request = new Request.Builder().url("http://106.53.18.103:80/api/users").post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String mes= response.body().string();
                Log.i(TAG, "onResponse: " +mes);
                try {
                    JSONObject jsonObject=new JSONObject(mes);
                    String flag=jsonObject.getString("Flag");
                    String message=jsonObject.getString("Message");

                    if(flag.equals("01")){
                        String list=jsonObject.getString("List");
                        showWaitingDialog(message);
                        JSONArray jsonArray=new JSONArray(list);
                        for (int i=0;i<jsonArray.length();i++){
                          String lists= String.valueOf(jsonArray.get(i));
                          JSONObject JS=new JSONObject( lists  );
                            Username= JS.getString("Username");
                            Password= JS.getString("Password");
                        }
                        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                        Thread.sleep(5000);
                        intent.putExtra("Username",Username);
                        intent.putExtra("Password",Password);
                        startActivity(intent);

                    }else if(flag.equals("02")) {
                       showNormalDialog(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showWaitingDialog(final String mss) {
            /* 等待Dialog具有屏蔽其他控件的交互能力
             * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
             * 下载等事件完成后，主动调用函数关闭该Dialog
             */
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    waitingDialog=
                            new ProgressDialog(MainActivity.this);
                    waitingDialog.setTitle("登录提示");
                    waitingDialog.setMessage(mss +"正在登录...");
                    waitingDialog.setIndeterminate(true);
                    waitingDialog.setCancelable(false);
                    waitingDialog.show();

                }
            });

    }
    private void showNormalDialog(final String mes){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                normalDialog =
                        new AlertDialog.Builder(MainActivity.this);
                normalDialog.setIcon(R.mipmap.ic_launcher);
                normalDialog.setTitle("登录提示");
                normalDialog.setMessage(mes);
                normalDialog.setPositiveButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                normalDialog.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                // 显示
                normalDialog.show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waitingDialog.dismiss();
    }
}
