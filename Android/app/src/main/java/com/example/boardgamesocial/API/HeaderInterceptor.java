package com.example.boardgamesocial.API;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

//  TODO: Get from shared preferences
    public static final String TOKEN = "e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .header("Authorization", String.format("Token %s", TOKEN))
                .build());
    }
}
