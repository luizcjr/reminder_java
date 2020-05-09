package com.luiz.reminderjava.ui.activities.login;

import com.google.gson.JsonObject;
import com.luiz.reminderjava.api.responses.LoginResponse;
import com.luiz.reminderjava.ui.activities.forgot_password.ForgotPasswordActivity;
import com.luiz.reminderjava.ui.activities.main.MainActivity;
import com.luiz.reminderjava.ui.activities.register.RegisterActivity;
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

public class LoginViewModel extends BaseViewModel {

    public final ValiFieldEmail email = (ValiFieldEmail) new ValiFieldEmail().addEmailValidator("E-mail inválido!");
    public final ValiFieldText password = new ValiFieldText().addMinLengthValidator("Senha curta!", 6);
    public final ValiFiForm form = new ValiFiForm(email, password);

    private RequestBody createBody() {
        JsonObject json = new JsonObject();
        json.addProperty("email", email.getValue());
        json.addProperty("password", password.getValue());

        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    public void sendLogin() {
        this.beforeRequest();

        disposable.add(
                apiRepository.login(createBody())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<LoginResponse>() {

                            @Override
                            public void onSuccess(LoginResponse loginResponse) {
                                afterRequest();

                                Utils.setApiToken(loginResponse.getToken());
                                Utils.setLastUserSession(loginResponse.getUser());
                                redirectToHome();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);
                            }
                        })
        );
    }


    public void register() {
        Utils.startActivity(context, RegisterActivity.class);
    }

    public void forgotPassword() {
        Utils.startActivity(context, ForgotPasswordActivity.class);
    }

    private void redirectToHome() {
        Utils.startActivity(context, MainActivity.class);
    }

    @Override
    protected void onCleared() {
        form.destroy();
        disposable.clear();

        super.onCleared();
    }
}
