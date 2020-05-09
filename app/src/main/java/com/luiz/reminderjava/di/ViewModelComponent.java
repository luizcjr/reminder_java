package com.luiz.reminderjava.di;

import com.luiz.reminderjava.ui.base.BaseViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ViewModelComponent {
    void inject(BaseViewModel viewModel);
}
