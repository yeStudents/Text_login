package com.example.text_login.mvp.presener;


import android.os.Handler;

import com.example.text_login.mvp.model.Account;
import com.example.text_login.mvp.model.Callbacks;
import com.example.text_login.mvp.model.MvpModel;
import com.example.text_login.mvp.view.ImvpView;



public class MvpPresener {
    /*用于view和model的关联，方便activity调用登录方法
    * 1.定义view视图接口
    * 2.定义model，并初始化
    * 3.创建presener的构造方法
    * 4.新建登录方法，里面用model的写好的处理登录方法*/
    private ImvpView imvpView;
    private MvpModel mvpModel;


    public MvpPresener(ImvpView imvpView) {
        this.imvpView = imvpView;
        mvpModel = new MvpModel();
    }

    public void go_login(Account account) {

        mvpModel.getdata(account, new Callbacks() {
            @Override
            public void login_success(Account account) {
                  imvpView.loginSuccess(account);
            }

            @Override
            public void logi_error(String result) {
                imvpView.loginFail(result);
            }
        });
    }

}
