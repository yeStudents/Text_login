package com.example.text_login.mvp.presener;


import android.os.Handler;

import com.example.text_login.mvp.model.Account;
import com.example.text_login.mvp.model.Callbacks;
import com.example.text_login.mvp.model.MvpModel;
import com.example.text_login.mvp.view.ImvpView;



public class MvpPresener {
    private ImvpView imvpView;
    private MvpModel mvpModel;
    private Handler handler=new Handler();

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
