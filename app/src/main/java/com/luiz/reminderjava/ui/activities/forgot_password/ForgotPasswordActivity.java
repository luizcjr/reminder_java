package com.luiz.reminderjava.ui.activities.forgot_password;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.luiz.reminderjava.R;
import com.luiz.reminderjava.databinding.ForgotPasswordBinding;
import com.luiz.reminderjava.ui.base.BaseActivity;
import com.mlykotom.valifi.BR;

public class ForgotPasswordActivity extends BaseActivity<ForgotPasswordBinding, ForgotPasswordViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewModel().loading.observe(this, loadingLiveDataObserver);
        this.viewModel().loadError.observe(this, errorLiveDataObserver);
        this.viewModel().context = this;
    }

    @Override
    public int layoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public int binding() {
        return BR.forgotViewModel;
    }

    @Override
    public ForgotPasswordViewModel viewModel() {
        return ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
    }
}
