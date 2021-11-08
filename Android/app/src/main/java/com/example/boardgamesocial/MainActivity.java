package com.example.boardgamesocial;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import com.example.boardgamesocial.API.HeaderInterceptor;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Game;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireUpDisplay();
    }

    private void wireUpDisplay() {
        etUsername = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(view -> {
            //TODO: 1) Verify user based on credentials
            //TODO: 2) If valid user then switch to landing page activity!
            Intent goToHomePostActivity = HomePostActivity.getIntent(MainActivity.this);
            startActivity(goToHomePostActivity);
        });

        btnSignup.setOnClickListener(view -> {
            Intent goToSignupActivity = SignUpActivity.getIntent(MainActivity.this);
            startActivity(goToSignupActivity);
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}