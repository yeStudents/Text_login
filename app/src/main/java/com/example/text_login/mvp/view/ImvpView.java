package com.example.text_login.mvp.view;

import android.service.autofill.UserData;

import com.example.text_login.mvp.model.Account;

public interface ImvpView {
    /*视图只展示数据登录数据*/
    void loginSuccess(Account data);
    void loginFail(String result);
}
