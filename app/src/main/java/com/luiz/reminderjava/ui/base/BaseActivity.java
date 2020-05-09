package com.luiz.reminderjava.ui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.luiz.reminderjava.util.Utils;

public abstract class BaseActivity<T extends ViewDataBinding, D extends BaseViewModel> extends AppCompatActivity {

    protected void initializeDatabinding() {
        vDatabinding = DataBindingUtil.setContentView(this, layoutId());
        vModel = viewModel();
        vDatabinding.setVariable(binding(), vModel);
        vDatabinding.executePendingBindings();
    }

    public T viewDataBinding() {
        return vDatabinding;
    }

    @LayoutRes
    public abstract int layoutId();

    public abstract int binding();

    public abstract D viewModel();

    private T vDatabinding;
    private D vModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeDatabinding();
    }

    protected Observer<Boolean> loadingLiveDataObserver = isLoading -> {
        if (isLoading) {
            Utils.onStartLoading(BaseActivity.this);
        } else {
            Utils.onStopLoading();
        }
    };

    protected Observer<String> errorLiveDataObserver = error -> {
        if (error != null) {
            Utils.alertError(this, error);
        }
    };
}
