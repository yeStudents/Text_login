package com.example.text_login.mvc.model;

import com.example.text_login.mvp.model.Account;

public interface Callbacks {
    /*该接口调用，用在mvp的model层，在使用方法参数里面定义，最后在结果里面直接使用*/
    void login_success(Account account);
    void logi_error(String result);
}
