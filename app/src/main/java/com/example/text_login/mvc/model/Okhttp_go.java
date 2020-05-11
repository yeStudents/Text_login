package com.example.text_login.mvc.model;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.example.text_login.R;
import com.example.text_login.mvc.controller.MainActivity;
import com.example.text_login.mvc.model.Callbacks;
import com.example.text_login.mvc.model.MvcModel;
import com.example.text_login.mvp.model.Account;

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

public class Okhttp_go implements MvcModel {
    OkHttpClient okHttpClient;
    private static final String TAG = "Okhttp_go";
    String Username, Password;


    @Override
    public void getuser(String usernum, String userpwd, final Callbacks callbacks) {
        //初始化okhttpclient
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(6000, TimeUnit.MILLISECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("username", usernum)
                .add("password", userpwd)
                .build();
        Request request = new Request.Builder().url("http://106.53.18.103:80/api/users").post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mes = response.body().string();
                Log.i(TAG, "onResponse: " + mes);
                try {
                    JSONObject jsonObject = new JSONObject(mes);
                    String flag = jsonObject.getString("Flag");
                    String message = jsonObject.getString("Message");

                    if (flag.equals("01")) {
                        String list = jsonObject.getString("List");

                        JSONArray jsonArray = new JSONArray(list);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String lists = String.valueOf(jsonArray.get(i));
                            JSONObject JS = new JSONObject(lists);
                            Username = JS.getString("Username");
                            Password = JS.getString("Password");
                        }
                        callbacks.login_success(new Account(Username, Password, message));


                    } else if (flag.equals("02")) {
                        callbacks.logi_error(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
