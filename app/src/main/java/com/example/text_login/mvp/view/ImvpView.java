package com.example.text_login.mvp.view;

import android.service.autofill.UserData;

import com.example.text_login.mvp.model.Account;

public interface ImvpView {

    void loginSuccess(Account data);
    void loginFail(String result);
}
