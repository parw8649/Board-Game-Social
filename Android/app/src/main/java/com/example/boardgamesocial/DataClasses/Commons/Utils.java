package com.example.boardgamesocial.DataClasses.Commons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.LoginAndSignUpActivity;
import com.example.boardgamesocial.MainAppActivity;

import java.util.Objects;

public class Utils {

    private static SharedPreferences mPreferences;

    private static String token;

    public static void checkForUser(Context context) {

        boolean isLoginPage = context.getClass().getSimpleName().equals(LoginAndSignUpActivity.class.getSimpleName());

        if(Objects.nonNull(token)) {
            if(isLoginPage) switchToMainAppActivity(context);
            return;
        }

        if(mPreferences == null) {
            getPrefs(context);
        }

        token = mPreferences.getString(Constants.USER_TOKEN, null);

        if(Objects.nonNull(token)) {
            if(isLoginPage) switchToMainAppActivity(context);
            return;
        }

        Intent goToLoginActivity = LoginAndSignUpActivity.getIntent(context);
        context.startActivity(goToLoginActivity);
    }

    public static void getPrefs(Context context) {
        mPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
    }

    public static void addUserToPreferences(Context context, Token userToken) {

        if(mPreferences == null) { getPrefs(context); }

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.USER_TOKEN, userToken.getToken());
        editor.apply();
    }

    public static String getUserToken() {
        return token;
    }

    private static void switchToMainAppActivity(Context context) {
        Intent goToLoginActivity = MainAppActivity.getIntent(context);
        context.startActivity(goToLoginActivity);
    }
}
