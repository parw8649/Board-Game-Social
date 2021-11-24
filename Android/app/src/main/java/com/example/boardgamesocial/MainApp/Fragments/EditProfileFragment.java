package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getClient;
import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textViewGreeting;
    private TextView textViewCurrUsername;
    private EditText editTextNewUsername;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextNewBio;
    private Button buttonEditUsername;
    private Button buttonEditPassword;
    private Button buttonEditBio;
    private Button buttonDeleteAcc;
    private RetrofitClient retrofitClient;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrofitClient = getClient();
        textViewGreeting = view.findViewById(R.id.edit_profile_greeting);
        textViewCurrUsername = view.findViewById(R.id.edit_profile_current_username);
        editTextNewUsername = view.findViewById(R.id.edit_profile_editText_username);
        editTextNewPassword = view.findViewById(R.id.edit_profile_editText_password);
        editTextConfirmPassword = view.findViewById(R.id.edit_profile_editText_password_confirm);
        editTextNewBio = view.findViewById(R.id.edit_profile_editText_bio);
        buttonEditUsername = view.findViewById(R.id.edit_profile_btn_edit_username);
        buttonEditPassword = view.findViewById(R.id.edit_profile_btn_edit_password);
        buttonEditBio = view.findViewById(R.id.edit_profile_btn_edit_bio);
        buttonDeleteAcc = view.findViewById(R.id.edit_profile_btn_delete_account);

        retrofitClient.getCall(User.class, new HashMap<String, String>() {{
            put("id", String.valueOf(Utils.getUserId()));
        }}).subscribe(jsonArray -> {
            List<User> userList = getObjectList(jsonArray, User.class);
            getActivity().runOnUiThread(() -> {
                textViewGreeting.setText(getString(R.string.edit_profile_greeting, userList.get(0).getUsername()));
                textViewCurrUsername.setText(userList.get(0).getUsername());
            });
        });

        buttonEditUsername.setOnClickListener(v -> validateUsername(view));
    }

    private void validateUsername(View view) {
        try {
            retrofitClient.putCall(User.class, new HashMap<String, String>() {{
                put("username", editTextNewUsername.getText().toString());
            }}).subscribe(jsonObject -> {
                User user = getObject(jsonObject, User.class);
                getActivity().runOnUiThread(() -> {
                    textViewGreeting.setText(getString(R.string.edit_profile_greeting, user.getUsername()));
                    textViewCurrUsername.setText(user.getUsername());
                    return;
                });
            });
        }catch (Exception e) {
            Snackbar.make(view, "Unable to update username", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.bottom_app_bar).setAction("Action", null).show();
        }
    }
}