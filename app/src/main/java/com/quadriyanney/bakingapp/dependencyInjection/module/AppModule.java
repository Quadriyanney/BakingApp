package com.quadriyanney.bakingapp.dependencyInjection.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quadriyanney.bakingapp.App;
import com.quadriyanney.bakingapp.BuildConfig;
import com.quadriyanney.bakingapp.data.remote.ApiService;
import com.quadriyanney.bakingapp.util.ImageUtil;
import com.quadriyanney.bakingapp.util.NetworkUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.quadriyanney.bakingapp.helper.Constants.BASE_URL;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return app;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(
                BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder().setLenient().create();
    }


    @Provides
    @Singleton
    Retrofit.Builder providesRetrofitBuilder(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);
    }

    @Provides
    @Singleton
    ApiService providesApiService(Retrofit.Builder builder) {
        return builder.baseUrl(BASE_URL)
                .build()
                .create(ApiService.class);
    }

    @Provides
    @Singleton
    NetworkUtil providesNetworkUtil(Context context) {
        return new NetworkUtil(context);
    }

    @Provides
    @Singleton
    ImageUtil providesImageUtil(Context context) {
        return new ImageUtil(context);
    }

    @Provides
    @Singleton
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }

}
