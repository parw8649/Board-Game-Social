package com.example.boardgamesocial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etUsername, etPassword;
    private Button btnRegister, btnSignIn;

    private RetrofitClient retrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        wireUpDisplay();

        retrofitClient = RetrofitClient.getClientWithoutHeaderInterceptor();
    }

    private void wireUpDisplay() {
        etFirstName = findViewById(R.id.et_signup_firstname);
        etLastName = findViewById(R.id.et_signup_lastname);
        etEmail = findViewById(R.id.et_signup_email);
        etUsername = findViewById(R.id.et_signup_username);
        etPassword = findViewById(R.id.et_signup_password);
        btnRegister = findViewById(R.id.btn_register);
        btnSignIn = findViewById(R.id.btn_back_to_login);

        btnRegister.setOnClickListener(view -> {
            //TODO: verify if user already exits!
            //TODO: if user doesn't exists then save user data
            String firstName = Objects.nonNull(etFirstName.getText()) ? etFirstName.getText().toString() : null;
            String lastName = Objects.nonNull(etLastName.getText()) ? etLastName.getText().toString() : null;
            String email = Objects.nonNull(etEmail.getText()) ? etEmail.getText().toString() : null;
            String username = Objects.nonNull(etUsername.getText()) ? etUsername.getText().toString() : null;
            String password = Objects.nonNull(etPassword.getText()) ? etPassword.getText().toString() : null;

            retrofitClient.signUpCall(new User(firstName, lastName, email, username, password)
            ).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        User user = response.body();
                        Log.i("User", user.toString());
                        Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_LONG).show();
                        new Exception("Request failed, code: " + response.code()).printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    try {
                        throw t;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });


            //After successful registration, redirecting user to login page.
            Intent goToMainActivity = LoginAndSignUpActivity.getIntent(SignUpActivity.this);
            startActivity(goToMainActivity);
        });

        btnSignIn.setOnClickListener(view -> finish());
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}