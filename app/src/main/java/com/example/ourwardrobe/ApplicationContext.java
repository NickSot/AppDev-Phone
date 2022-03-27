package com.example.ourwardrobe;

import outwardrobemodels.User;

public class ApplicationContext {
    private static ApplicationContext instance;
    private User user;

    private ApplicationContext() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ApplicationContext getInstance() {
        if (instance == null)
            instance = new ApplicationContext();

        return instance;
    }


}
