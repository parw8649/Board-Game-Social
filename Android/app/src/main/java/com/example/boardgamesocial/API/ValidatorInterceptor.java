package com.example.boardgamesocial.API;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ValidatorInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() != 200 || response.body() == null) {
            throw new IllegalStateException(
                String.format("Response code %d with body: %s",
                    response.code(),
                    response.body()
            ));
        }
        return response;
    }
}
