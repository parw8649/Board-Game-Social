package com.example.boardgamesocial.LoginAndSignUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.boardgamesocial.R;
import com.example.boardgamesocial.databinding.ActivityLoginAndSignUpBinding;

public class LoginAndSignUpActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginAndSignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginAndSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_app);

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
}