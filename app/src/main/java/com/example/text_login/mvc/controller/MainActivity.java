package com.example.text_login.mvc.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.text_login.R;
import com.example.text_login.mvc.model.Callbacks;
import com.example.text_login.mvc.model.Okhttp_go;
import com.example.text_login.mvp.model.Account;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements Callbacks,View.OnClickListener {
    private EditText edit_name, edit_pwd;
    private Button bt_login, bt_exit;

    private static final String TAG = "MainActivity";
    ProgressDialog waitingDialog;
    AlertDialog.Builder normalDialog;
    String Username,Password;
    Okhttp_go okhttp_go=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_view();
        okhttp_go=new Okhttp_go();


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
                    okhttp_go.getuser(et_name, et_pwd,this);
                }

                break;
            case R.id.bt_exit:
                finish();
                break;
        }
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

    @Override
    public void login_success(Account account) {
        showWaitingDialog(account.getMessage());
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intent.putExtra("Username",account.getUsername());
        intent.putExtra("Password",account.getPwd());
        startActivity(intent);

    }

    @Override
    public void logi_error(String result) {
        showNormalDialog(result);
    }
}
