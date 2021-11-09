package com.example.boardgamesocial.API;

import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.i("API",
                String.format(
                        "Sending request %s as %s with headers: %s Body: %s Chain Connection: %s",
                        request.url(),
                        request.method(),
                        request.headers(),
                        request.body() == null ? "Empty Body" : request.body().toString(),
                        chain.connection()
                )
        );

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        assert response.body() != null;
        Log.i("API",
                String.format(
                        "Received response for %s in %.1fms%n%sn%s",
                        response.request().url(),
                        (t2 - t1) / 1e6d,
                        response.headers(),
                        response.peekBody(2048).string()
                )
        );

        return response;
    }
}
