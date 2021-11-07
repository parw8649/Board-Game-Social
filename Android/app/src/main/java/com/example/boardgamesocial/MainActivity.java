package com.example.boardgamesocial;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


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

    private RetrofitClient retrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireUpDisplay();
    }

    private void wireUpDisplay() {
        retrofitClient = RetrofitClient.getClient();

        retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
//            put("gameTitle", "TestGame8");
            put("gameTitle", "7 Wonders Duel");
        }}).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<Game> list = getObjectList(response.body(), Game.class);
                    Log.i("Game", list.get(0).getGameTitle());
                } else {
                    new Exception("Request failed, code: " + response.code()).printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        etUsername = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(view -> {
            //TODO: 1) Verify user based on credentials
            //TODO: 2) If valid user then switch to landing page activity!
            /*Intent goToLandingPageActivity = LandingPageActivity.getIntent(MainActivity.this);
            startActivity(goToLandingPageActivity);*/
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