package com.luiz.reminderjava.ui.fragments.profile;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.luiz.reminderjava.api.models.User;
import com.luiz.reminderjava.api.responses.UserResponse;
import com.luiz.reminderjava.ui.activities.include_reminde.IncludeRemindeActivity;
import com.luiz.reminderjava.ui.activities.login.LoginActivity;
import com.luiz.reminderjava.ui.base.BaseViewModel;
import com.luiz.reminderjava.util.Utils;
import com.mlykotom.valifi.ValiFiForm;
import com.mlykotom.valifi.fields.ValiFieldEmail;
import com.mlykotom.valifi.fields.ValiFieldText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProfileViewModel extends BaseViewModel {

    public final ValiFieldText name = new ValiFieldText().addNotEmptyValidator("Campo obrigatório!");
    public final ValiFieldEmail email = (ValiFieldEmail) new ValiFieldEmail().addEmailValidator("E-mail inválido!");
    public final ValiFiForm form = new ValiFiForm(name, email);
    public MutableLiveData<User> user = new MutableLiveData<>();

    private RequestBody createBody() {

        JsonObject json = new JsonObject();
        json.addProperty("name", name.getValue());
        json.addProperty("email", email.getValue());

        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    public void updateUser() {
        this.beforeRequest();

        disposable.add(
                apiRepository.updateUser(createBody(), Utils.lastUserSession().getId())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserResponse>() {

                            @Override
                            public void onSuccess(UserResponse userResponse) {
                                afterRequest();

                                user.postValue(userResponse.getUser());
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);
                            }
                        })
        );
    }

    public void getUserInfo() {
        this.beforeRequest();

        disposable.add(
                apiRepository.getUserInfo(Utils.lastUserSession().getId())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserResponse>() {

                            @Override
                            public void onSuccess(UserResponse userResponse) {
                                afterRequest();

                                user.postValue(userResponse.getUser());
                                name.setValue(userResponse.getUser().getName());
                                email.setValue(userResponse.getUser().getEmail());
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);

                                user.postValue(null);
                            }
                        })
        );
    }

    public void redirectToIncludeReminder() {
        Utils.startActivity(context, IncludeRemindeActivity.class);
    }

    private void redirectToLogin() {
        Utils.startActivity(context, LoginActivity.class);
    }

    public void logOut() {
        Utils.setApiToken(null);
        Utils.setLastUserSession(null);
        redirectToLogin();
    }

    @Override
    protected void onCleared() {
        form.destroy();
        disposable.clear();

        super.onCleared();
    }

}
