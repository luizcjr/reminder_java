package com.luiz.reminderjava.ui.activities.register;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.luiz.reminderjava.R;
import com.luiz.reminderjava.databinding.RegisterBinding;
import com.luiz.reminderjava.ui.base.BaseActivity;
import com.mlykotom.valifi.BR;

public class RegisterActivity extends BaseActivity<RegisterBinding, RegisterViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewModel().loading.observe(this, loadingLiveDataObserver);
        this.viewModel().loadError.observe(this, errorLiveDataObserver);
        this.viewModel().context = this;
    }

    @Override
    public int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    public int binding() {
        return BR.registerViewModel;
    }

    @Override
    public RegisterViewModel viewModel() {
        return ViewModelProviders.of(this).get(RegisterViewModel.class);
    }
}
