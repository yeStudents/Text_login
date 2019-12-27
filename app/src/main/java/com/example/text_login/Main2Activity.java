package com.example.text_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_text = findViewById(R.id.tv_text);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Username");
        String pwd = intent.getStringExtra("Password");
        tv_text.setText("账号：" + name + "   " + "密码：" + pwd);
    }
}
