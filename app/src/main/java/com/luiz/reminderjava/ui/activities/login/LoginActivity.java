package com.luiz.reminderjava.ui.activities.login;

import android.os.Bundle;
import android.text.Html;

import androidx.lifecycle.ViewModelProviders;

import com.luiz.reminderjava.R;
import com.luiz.reminderjava.databinding.LoginBinding;
import com.luiz.reminderjava.ui.base.BaseActivity;
import com.mlykotom.valifi.BR;

public class LoginActivity extends BaseActivity<LoginBinding, LoginViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Texto com tag em html
        this.viewDataBinding().tvRegister.setText(Html.fromHtml(getString(R.string.title_sign_up_login)));

        this.viewModel().loading.observe(this, loadingLiveDataObserver);
        this.viewModel().loadError.observe(this, errorLiveDataObserver);
        this.viewModel().context = this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finishAndRemoveTask();
    }

    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int binding() {
        return BR.loginViewModel;
    }

    @Override
    public LoginViewModel viewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

}
