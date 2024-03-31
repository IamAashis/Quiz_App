package com.android.quizapp.di;

import com.android.quizapp.network.ApiService;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aashis on 30,March,2024
 */

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    String baseURL = "https://opentdb.com/";

    @Singleton
    @Provides
    public ApiService getRetroServiceInterface(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    public Retrofit getRetroInstance() {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
