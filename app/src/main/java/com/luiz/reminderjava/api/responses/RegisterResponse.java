package com.luiz.reminderjava.api.responses;

import com.luiz.reminderjava.api.models.User;

public class RegisterResponse {
    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
