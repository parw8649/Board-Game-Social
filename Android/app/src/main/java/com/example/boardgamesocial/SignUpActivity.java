package com.example.boardgamesocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        wireUpDisplay();
    }

    private void wireUpDisplay() {
        etUsername = findViewById(R.id.et_signup_username);
        etPassword = findViewById(R.id.et_signup_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(view -> {
            //TODO: verify if user already exits!
            //TODO: if user doesn't exists then save user data
            //After successful registration, redirecting user to login page.
            Intent goToMainActivity = MainActivity.getIntent(SignUpActivity.this);
            startActivity(goToMainActivity);
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}