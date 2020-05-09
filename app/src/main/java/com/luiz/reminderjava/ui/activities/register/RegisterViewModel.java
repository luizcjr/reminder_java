package com.luiz.reminderjava.ui.activities.register;

import android.widget.Toast;

import com.google.gson.JsonObject;
import com.luiz.reminderjava.api.responses.RegisterResponse;
import com.luiz.reminderjava.ui.activities.main.MainActivity;
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

public class RegisterViewModel extends BaseViewModel {

    public final ValiFieldText name = new ValiFieldText().addNotEmptyValidator("Campo obrigatório!");
    public final ValiFieldEmail email = (ValiFieldEmail) new ValiFieldEmail().addEmailValidator("E-mail inválido!");
    public final ValiFieldText password = new ValiFieldText().addMinLengthValidator("Senha curta!", 6);
    public final ValiFiForm form = new ValiFiForm(name, email, password);

    private RequestBody createBody() {

        JsonObject json = new JsonObject();
        json.addProperty("name", name.getValue());
        json.addProperty("email", email.getValue());
        json.addProperty("password", password.getValue());

        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    public void sendRegisterUser() {
        this.beforeRequest();

        disposable.add(
                apiRepository.register(createBody())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<RegisterResponse>() {

                            @Override
                            public void onSuccess(RegisterResponse registerResponse) {
                                afterRequest();

                                Utils.setApiToken(registerResponse.getToken());
                                Utils.setLastUserSession(registerResponse.getUser());
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

    private void redirectToHome() {
        Toast.makeText(context, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
        Utils.startActivity(context, MainActivity.class);
    }

    @Override
    protected void onCleared() {
        form.destroy();
        disposable.clear();

        super.onCleared();
    }
}
