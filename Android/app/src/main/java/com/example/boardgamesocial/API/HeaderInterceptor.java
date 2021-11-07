package com.example.boardgamesocial.API;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Token;

import java.io.IOException;
import java.util.function.Function;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    public static Token token;

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .header("Authorization", String.format("Token %s", token.getToken()))
                .build());
    }
}
