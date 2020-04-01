package com.example.talk.Model;

import android.app.Application;


public class UserClient extends Application {

    private User user = null;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
