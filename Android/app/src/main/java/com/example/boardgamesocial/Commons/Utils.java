package com.example.boardgamesocial.Commons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.boardgamesocial.LoginAndSignUp.LoginAndSignUpActivity;
import com.example.boardgamesocial.MainApp.MainAppActivity;

import java.util.Objects;

public class Utils {

    private static SharedPreferences mPreferences;
    private static int userId;

    public static void checkForUser(Context context) {
        boolean isLoginPage = context.getClass().getSimpleName().equals(LoginAndSignUpActivity.class.getSimpleName());
        if(Objects.nonNull(userId)) {
            if(isLoginPage) switchToMainAppActivity(context);
            return;
        }
        if(mPreferences == null) {
            getPrefs(context);
        }
        userId = mPreferences.getInt(Constants.USER_ID, -1);
        if(Objects.nonNull(userId)) {
            if(isLoginPage) switchToMainAppActivity(context);
            return;
        }
        Intent goToLoginActivity = LoginAndSignUpActivity.getIntent(context);
        context.startActivity(goToLoginActivity);
    }

    public static void getPrefs(Context context) {
        mPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
    }

    public static void addUserIdToPreferences(Context context, Integer userId) {
        if(mPreferences == null) { getPrefs(context); }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.USER_ID, String.valueOf(userId));
        editor.apply();
    }

    public static Integer getUserId() {
        return userId;
    }

    private static void switchToMainAppActivity(Context context) {
        Intent goToLoginActivity = MainAppActivity.getIntent(context);
        context.startActivity(goToLoginActivity);
    }
}
