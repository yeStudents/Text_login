package com.example.text_login.mvp.model;

public interface Callbacks {
    void login_success(Account account);
    void logi_error(String result);
}
