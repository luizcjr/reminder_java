package com.luiz.reminderjava.api.services;

import com.luiz.reminderjava.api.responses.FCMResponse;
import com.luiz.reminderjava.api.responses.LoginResponse;
import com.luiz.reminderjava.api.responses.NoteResponse;
import com.luiz.reminderjava.api.responses.NotesResponse;
import com.luiz.reminderjava.api.responses.RegisterResponse;
import com.luiz.reminderjava.api.responses.UserResponse;
import com.luiz.reminderjava.di.DaggerApiComponent;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.RequestBody;

public class ApiRepository {

    @Inject
    public ApiDataSource api;

    public ApiRepository() {
        DaggerApiComponent.create().inject(this);
    }

    public Single<RegisterResponse> register(RequestBody body) {
        return api.register(body);
    }

    public Single<LoginResponse> login(RequestBody body) {
        return api.login(body);
    }

    public Single<LoginResponse> resetPassword(RequestBody body) {
        return api.resetPassword(body);
    }

    public Single<LoginResponse> sendToken(RequestBody body) {
        return api.forgotPassword(body);
    }

    public Single<UserResponse> getUserInfo(String id) {
        return api.getUserInfo(id);
    }

    public Single<UserResponse> updateUser(RequestBody body, String id) {
        return api.updateUser(body, id);
    }

    public Single<NoteResponse> registerNote(RequestBody body) {
        return api.registerNote(body);
    }

    public Single<NotesResponse> getAllNotes() {
        return api.getAllNotes();
    }

    public Single<NoteResponse> getNote(String id) {
        return api.getNote(id);
    }

    public Single<FCMResponse> sendFCMToken(RequestBody body) {
        return api.sendFCMToken(body);
    }
}
