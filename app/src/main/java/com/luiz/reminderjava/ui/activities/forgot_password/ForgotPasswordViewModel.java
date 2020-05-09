package com.luiz.reminderjava.ui.activities.forgot_password;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.luiz.reminderjava.R;
import com.luiz.reminderjava.api.responses.LoginResponse;
import com.luiz.reminderjava.ui.activities.login.LoginActivity;
import com.luiz.reminderjava.ui.base.BaseViewModel;
import com.luiz.reminderjava.util.Utils;
import com.mlykotom.valifi.ValiFiForm;
import com.mlykotom.valifi.fields.ValiFieldEmail;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ForgotPasswordViewModel extends BaseViewModel {

    public final ValiFieldEmail email = (ValiFieldEmail) new ValiFieldEmail().addEmailValidator("E-mail inválido!");
    public final ValiFiForm form = new ValiFiForm(email);

    private RequestBody createBodyResetPassword(String emailReset, String tokenReset, String passwordReset) {

        JsonObject json = new JsonObject();
        json.addProperty("email", emailReset);
        json.addProperty("token", tokenReset);
        json.addProperty("password", passwordReset);

        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    private void sendResetPassword(String emailReset, String tokenReset, String passwordReset) {
        this.beforeRequest();

        disposable.add(
                apiRepository.resetPassword(createBodyResetPassword(emailReset, tokenReset, passwordReset))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<LoginResponse>() {

                            @Override
                            public void onSuccess(LoginResponse loginResponse) {
                                afterRequest();

                                redirectToLogin();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);
                            }
                        })
        );
    }

    private RequestBody createBodySendToken() {

        JsonObject json = new JsonObject();
        json.addProperty("email", email.getValue());

        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    public void sendToken() {
        this.beforeRequest();

        disposable.add(
                apiRepository.sendToken(createBodySendToken())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<LoginResponse>() {

                            @Override
                            public void onSuccess(LoginResponse loginResponse) {
                                afterRequest();

                                dialogResetToken();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);
                            }
                        })
        );
    }

    public void dialogResetToken() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View v = Objects.requireNonNull(inflater).inflate(R.layout.dialog_reset_password, null);
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);

        Button btnSend = v.findViewById(R.id.btnResetPass);
        TextInputEditText etEmail = v.findViewById(R.id.tietEmail);
        TextInputEditText etToken = v.findViewById(R.id.tietToken);
        TextInputEditText etPassword = v.findViewById(R.id.tietPassword);
        TextInputLayout tilEmail = v.findViewById(R.id.tilEmail);
        TextInputLayout tilToken = v.findViewById(R.id.tilToken);
        TextInputLayout tilPassword = v.findViewById(R.id.tilPassword);

        etEmail.setText(email.getValue());

        alertDialogBuilder.setView(v);
        android.app.AlertDialog d = alertDialogBuilder.create();
        d.setCancelable(true);
        d.show();

        Objects.requireNonNull(d.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        btnSend.setOnClickListener(v1 -> {
            if (etEmail.getText() != null && etEmail.getText().equals("")) {
                tilEmail.setError("E-mail é obrigatório!");
            } else if (!Utils.emailValidator(etEmail.getText().toString())) {
                tilEmail.setError("E-mail inválido!");
            } else if (etToken.getText() != null && etToken.getText().equals("")) {
                tilToken.setError("Código é obrigatório!");
            } else if (etPassword.getText() != null && etPassword.getText().equals("")) {
                tilPassword.setError("Senha é obrigatório!");
            } else if (etPassword.getText().length() < 6) {
                tilPassword.setError("Senha curta! Mínimo seis caracteres.");
            } else {
                d.dismiss();
                sendResetPassword(
                        etEmail.getText().toString(),
                        etToken.getText().toString(),
                        etPassword.getText().toString()
                );
            }
        });
    }

    private void redirectToLogin() {
        Utils.startActivity(context, LoginActivity.class);
    }

    @Override
    protected void onCleared() {
        form.destroy();
        disposable.clear();

        super.onCleared();
    }
}
