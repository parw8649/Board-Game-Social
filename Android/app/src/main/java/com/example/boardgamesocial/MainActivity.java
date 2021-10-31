package com.example.boardgamesocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireUpDisplay();
    }

    void wireUpDisplay() {

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