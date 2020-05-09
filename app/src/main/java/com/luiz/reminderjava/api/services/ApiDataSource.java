package com.luiz.reminderjava.api.services;

import com.luiz.reminderjava.api.constants.Constants;
import com.luiz.reminderjava.api.responses.FCMResponse;
import com.luiz.reminderjava.api.responses.LoginResponse;
import com.luiz.reminderjava.api.responses.NoteResponse;
import com.luiz.reminderjava.api.responses.NotesResponse;
import com.luiz.reminderjava.api.responses.RegisterResponse;
import com.luiz.reminderjava.api.responses.UserResponse;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDataSource {

    @POST(Constants.LOGIN)
    Single<LoginResponse> login(@Body RequestBody body);

    @POST(Constants.REGISTER)
    Single<RegisterResponse> register(@Body RequestBody body);

    @POST(Constants.FORGOT_PASSWORD)
    Single<LoginResponse> forgotPassword(@Body RequestBody body);

    @POST(Constants.RESET_PASSWORD)
    Single<LoginResponse> resetPassword(@Body RequestBody body);

    @GET(Constants.USER + "/{id}")
    Single<UserResponse> getUserInfo(@Path("id") String id);

    @PUT(Constants.USER + "/{id}")
    Single<UserResponse> getUserInfo(@Body RequestBody body, @Path("id") String id);

    @PUT(Constants.USER + "/{id}")
    Single<UserResponse> updateUser(@Body RequestBody body, @Path("id") String id);

    @POST(Constants.NOTES)
    Single<NoteResponse> registerNote(@Body RequestBody body);

    @GET(Constants.NOTES)
    Single<NotesResponse> getAllNotes();

    @GET(Constants.NOTES + "/{id}")
    Single<NoteResponse> getNote(@Path("id") String id);

    @POST(Constants.FCM_TOKEN)
    Single<FCMResponse> sendFCMToken(@Body RequestBody body);
}
