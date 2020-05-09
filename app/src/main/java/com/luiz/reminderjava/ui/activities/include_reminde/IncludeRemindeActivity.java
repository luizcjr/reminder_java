package com.luiz.reminderjava.ui.activities.include_reminde;

import android.os.Bundle;

import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;

import com.luiz.reminderjava.R;
import com.luiz.reminderjava.databinding.IncludeRemindeBinding;
import com.luiz.reminderjava.ui.base.BaseActivity;
import com.luiz.reminderjava.util.MaskType;
import com.luiz.reminderjava.util.Masks;

public class IncludeRemindeActivity extends BaseActivity<IncludeRemindeBinding, IncludeRemindeViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewDataBinding().toolbar.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        this.viewModel().loading.observe(this, loadingLiveDataObserver);
        this.viewModel().loadError.observe(this, errorLiveDataObserver);
        this.viewModel().context = this;

        this.viewDataBinding().etHour.addTextChangedListener(Masks.insert(this.viewDataBinding().etHour, MaskType.Hora));
        this.viewDataBinding().etDate.addTextChangedListener(Masks.insert(this.viewDataBinding().etDate, MaskType.Data));
    }

    @Override
    public int layoutId() {
        return R.layout.activity_include_reminde;
    }

    @Override
    public int binding() {
        return BR.includeRemindeViewModel;
    }

    @Override
    public IncludeRemindeViewModel viewModel() {
        return ViewModelProviders.of(this).get(IncludeRemindeViewModel.class);
    }
}
