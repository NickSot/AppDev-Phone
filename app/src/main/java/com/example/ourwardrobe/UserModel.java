package com.example.ourwardrobe;

public class UserModel {

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public UserModel(String userName) {
        this.userName = userName;
    }
}
