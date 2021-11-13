package com.example.boardgamesocial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Commons.*;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.databinding.FragmentLoginBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private RetrofitClient retrofitClient;

    private EditText etUsername, etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: Uncomment below line once logout functionality is completed!
        //Utils.checkForUser(requireContext());

        Button signUpBtn = view.findViewById(R.id.btn_signup);
        Button loginBtn = view.findViewById(R.id.btn_login);
        etUsername = view.findViewById(R.id.et_login_username);
        etPassword = view.findViewById(R.id.et_login_password);

        retrofitClient = RetrofitClient.getClientWithoutHeaderInterceptor();

        signUpBtn.setOnClickListener(view1 -> NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));

        loginBtn.setOnClickListener(view1 -> processUserLogin());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void processUserLogin() {

        String username = Objects.nonNull(etUsername.getText()) ? etUsername.getText().toString() : null;
        String password = Objects.nonNull(etPassword.getText()) ? etPassword.getText().toString() : null;

        Log.i("Login", "Username: "+ username +", Password: "+ password);

        retrofitClient.loginCall(new User(username, password)
        ).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Token token = response.body();
                    Log.i("Token", token.toString());

                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_LONG).show();

                    Utils.addUserToPreferences(getContext(), token);
                    Intent goToMainAppActivity = MainAppActivity.getIntent(getContext());
                    startActivity(goToMainAppActivity);

                } else {
                    Toast.makeText(getContext(), "Unable to log in with provided credentials", Toast.LENGTH_LONG).show();
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
    }
}