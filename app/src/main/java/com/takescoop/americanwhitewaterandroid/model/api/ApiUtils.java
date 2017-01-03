package com.takescoop.americanwhitewaterandroid.model.api;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.takescoop.americanwhitewaterandroid.BuildConfig;

import org.threeten.bp.Instant;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class ApiUtils {
    public static final String AW_ENDPOINT = "https://www.americanwhitewater.org/content/";

    // Logs requests on non production builds.
    public static Retrofit getRestAdapter() {
        Gson gson = getGson();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(AW_ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(getClientWithInterceptor())
                .build();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    private static OkHttpClient getClientWithInterceptor() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(loggingInterceptor);
        }

//        if (options.contains(RetrofitOptions.HandleErrors)) {
//            builder.addInterceptor(new ScoopErrorInterceptor());
//        }

        return builder.build();
    }
}
