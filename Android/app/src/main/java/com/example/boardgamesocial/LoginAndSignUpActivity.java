package com.example.boardgamesocial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.databinding.ActivityLoginAndSignUpBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAndSignUpActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginAndSignUpBinding binding;

    private RetrofitClient retrofitClient;

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignup;

    private Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginAndSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wireUpDisplay();

        retrofitClient = RetrofitClient.getClientWithoutHeaderInterceptor();

        // This token needs to be acquired with login request
        //HeaderInterceptor.token.setToken("e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1");
        /*retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
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
        });*/


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_app);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_app);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static Intent getIntent(Context context) {
        // Barbara added getIntent Sat Nov 6 for bottom app bar navigation
        System.out.println("Inside LoginAndSignUpActivity getIntent!");
        return new Intent(context, LoginAndSignUpActivity.class);
    }

    private void wireUpDisplay() {
        etUsername = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(view -> {
            //TODO: 1) Verify user based on credentials
            String username = Objects.nonNull(etUsername.getText()) ? etUsername.getText().toString() : null;
            String password = Objects.nonNull(etPassword.getText()) ? etPassword.getText().toString() : null;

            Log.i("Login", "Username: "+ username +", Password: "+ password);

            retrofitClient.loginCall(new User(username, password)
            ).enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        token = response.body();
                        Log.i("Token", token.toString());
                        Toast.makeText(LoginAndSignUpActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent goToHomePostActivity = HomePostActivity.getIntent(LoginAndSignUpActivity.this);
                        startActivity(goToHomePostActivity);
                    } else {
                        Toast.makeText(LoginAndSignUpActivity.this, "Unable to log in with provided credentials", Toast.LENGTH_LONG).show();
                        new Exception("Request failed, code: " + response.code()).printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    try {
                        throw t;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        });

        btnSignup.setOnClickListener(view -> {
            Intent goToSignupActivity = SignUpActivity.getIntent(LoginAndSignUpActivity.this);
            startActivity(goToSignupActivity);
        });
    }
}