package com.example.text_login.mvc.bean;

public class Account {
    private String username;
    private String pwd;
    private String message;

    public Account() {
    }

    public Account(String username, String pwd, String message) {
        this.username = username;
        this.pwd = pwd;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
