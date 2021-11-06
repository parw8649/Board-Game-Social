package com.example.boardgamesocial.API;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

//  TODO: Get from shared preferences
    public static final String TOKEN = "a9cec526b3a8775af8878e25e187b48250058e3e";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .header("Authorization", String.format("Token %s", TOKEN))
                .build());
    }
}
