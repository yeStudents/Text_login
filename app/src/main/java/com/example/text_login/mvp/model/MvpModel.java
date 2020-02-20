package com.example.text_login.mvp.model;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.Log;

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

public class MvpModel {
    private static final String TAG = "MvpModel";
    String Username,Password;


    public void getdata(Account account, final Callbacks callback) {

        //初始化okhttpclient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(6000,TimeUnit.MILLISECONDS)
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(6000, TimeUnit.MILLISECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("username", account.getUsername())
                .add("password", account.getPwd())
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
                    final  String message = jsonObject.getString("Message");

                    if (flag.equals("01")) {
                        String list = jsonObject.getString("List");;
                        JSONArray jsonArray = new JSONArray(list);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String lists = String.valueOf(jsonArray.get(i));
                            JSONObject JS = new JSONObject(lists);
                            Username = JS.getString("Username");
                            Password = JS.getString("Password");
                        }
                        callback.login_success(new Account(Username,Password,message));

                    } else if (flag.equals("02")) {
                      /*  showNormalDialog(message);*/
                        callback.logi_error(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    }
