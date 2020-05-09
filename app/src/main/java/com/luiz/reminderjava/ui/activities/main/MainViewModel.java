package com.luiz.reminderjava.ui.activities.main;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.luiz.reminderjava.api.responses.FCMResponse;
import com.luiz.reminderjava.ui.base.BaseViewModel;
import com.luiz.reminderjava.util.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainViewModel extends BaseViewModel {

    private RequestBody createBody(String fcmToken) {

        JsonObject json = new JsonObject();
        json.addProperty("fcm_token", fcmToken);
        json.addProperty("is_accepted", true);

        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    void verifyHasToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("_res", "Is not successful");
                Toast.makeText(
                        context,
                        "Erro ao obter token do firebase para esse dispositivo",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            // First access (F-Register FCM Token)
            if (Utils.getApiFCMToken() == null) {
                Log.d("_res", "First access");
                sendFcmToken(createBody(task.getResult().getToken()));
            } else {
                // Get new Instance ID token
                if (!Utils.getApiFCMToken()
                        .equals(task.getResult().getToken())
                ) {
                    Log.d("_res", "Get new Instance ID token");
                    sendFcmToken(createBody(task.getResult().getToken()));
                } else {
                    sendFcmToken(createBody(Utils.getApiFCMToken()));
                }
            }
        });
    }

    void sendFcmToken(RequestBody body) {
        this.beforeRequest();

        disposable.add(
                apiRepository.sendFCMToken(body)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<FCMResponse>() {

                            @Override
                            public void onSuccess(FCMResponse fcmResponse) {
                                afterRequest();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        disposable.clear();

        super.onCleared();
    }
}
