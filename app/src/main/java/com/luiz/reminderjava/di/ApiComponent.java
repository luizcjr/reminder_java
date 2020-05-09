package com.luiz.reminderjava.di;

import com.luiz.reminderjava.api.services.ApiRepository;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(ApiRepository repository);
}
