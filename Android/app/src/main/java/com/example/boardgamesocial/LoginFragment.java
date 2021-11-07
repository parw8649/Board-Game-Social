package com.example.boardgamesocial;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boardgamesocial.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.btn_signup);
        Button buttonLogin = view.findViewById(R.id.btn_login);

        button.setOnClickListener(view1 -> NavHostFragment.findNavController(LoginFragment.this)
               .navigate(R.id.action_FirstFragment_to_SecondFragment));

//        buttonLogin.setOnClickListener(view1 -> NavHostFragment.findNavController(LoginFragment.this)
//                .navigate(R.id.action_FirstFragment_to_SecondFragment));

        buttonLogin.setOnClickListener(v -> {
            Intent goToHomePostActivity = MainAppActivity.getIntent(getActivity().getApplicationContext());
            startActivity(goToHomePostActivity);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}