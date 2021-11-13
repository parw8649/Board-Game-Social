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
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.databinding.FragmentSignUpBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    private EditText etFirstName, etLastName, etEmail, etUsername, etPassword;

    private RetrofitClient retrofitClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button registerBtn = view.findViewById(R.id.btn_register);
        Button backToLoginBtn = view.findViewById(R.id.btn_back_to_login);
        etFirstName = view.findViewById(R.id.et_signup_firstname);
        etLastName = view.findViewById(R.id.et_signup_lastname);
        etEmail = view.findViewById(R.id.et_signup_email);
        etUsername = view.findViewById(R.id.et_signup_username);
        etPassword = view.findViewById(R.id.et_signup_password);

        retrofitClient = RetrofitClient.getClientWithoutHeaderInterceptor();

        registerBtn.setOnClickListener(view1 -> registerUser());
        backToLoginBtn.setOnClickListener(view1 -> NavHostFragment.findNavController(SignUpFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void registerUser() {

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
                    Toast.makeText(getContext(), "User registered!", Toast.LENGTH_LONG).show();
                    NavHostFragment
                            .findNavController(SignUpFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
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
    }

}