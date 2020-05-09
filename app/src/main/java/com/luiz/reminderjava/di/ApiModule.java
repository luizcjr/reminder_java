package com.luiz.reminderjava.di;

import com.luiz.reminderjava.api.constants.Constants;
import com.luiz.reminderjava.api.services.ApiDataSource;
import com.luiz.reminderjava.api.services.ApiRepository;
import com.luiz.reminderjava.util.Utils;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final int CONNECTION_TIMEOUT = 20 * 1000;

    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {

        Request newRequest = chain.request();

        if (Utils.getApiToken() != null) {
            newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + Utils.getApiToken())
                    .build();
        }

        return chain.proceed(newRequest);
    }).connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build();

    @Provides
    ApiDataSource provideApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiDataSource.class);
    }

    @Provides
    ApiRepository providesService() {
        return new ApiRepository();
    }
}
