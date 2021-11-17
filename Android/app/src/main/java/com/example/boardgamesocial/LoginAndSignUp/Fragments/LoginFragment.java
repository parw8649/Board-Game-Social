package com.example.boardgamesocial.LoginAndSignUp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.MainApp.MainAppActivity;
import com.example.boardgamesocial.R;
import com.example.boardgamesocial.databinding.FragmentLoginBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment";

    private FragmentLoginBinding binding;
    private RetrofitClient retrofitClient;
    private EditText etUsername, etPassword;
    private Token token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button signUpBtn = view.findViewById(R.id.btn_signup);
        Button loginBtn = view.findViewById(R.id.btn_login);
        etUsername = view.findViewById(R.id.et_login_username);
        etPassword = view.findViewById(R.id.et_login_password);

        retrofitClient = RetrofitClient.getClient();

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

        retrofitClient.loginCall(new User(username, password))
                .flatMap(token -> {
                    retrofitClient.setAuthToken(token.getToken());
                    return retrofitClient.getCall(User.class, new HashMap<String, String>(){{
                        put("username", username);
                    }});
                })
                .subscribe(jsonArray -> {
                    List<User> userList = getObjectList(jsonArray, User.class);
                    assert userList.size() == 1;
                    Utils.addUserIdToPreferences(getContext(), userList.get(0).getId());
                    Intent goToMainAppActivity = MainAppActivity.getIntent(getContext());
                    startActivity(goToMainAppActivity);
                });

    }
}