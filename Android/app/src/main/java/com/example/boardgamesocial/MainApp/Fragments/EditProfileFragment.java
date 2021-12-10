package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getClient;
import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Profile;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.LoginAndSignUp.LoginAndSignUpActivity;
import com.example.boardgamesocial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

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
    public static final String TAG = "EditProfileFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String username;
    private String secret;
    private TextView textViewGreeting;
    private TextView textViewCurrUsername;
    private EditText editTextNewUsername;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextBio;
    private ImageView imageViewUserIcon;
    private Button buttonEditIcon;
    private Button buttonEditUsername;
    private Button buttonEditPassword;
    private Button buttonEditBio;
    private Button buttonDeleteAcc;
    private Button buttonDeleteAccConfirm;
    private Button buttonDeleteAccDeny;
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
        editTextBio = view.findViewById(R.id.edit_profile_editText_bio);
        imageViewUserIcon = view.findViewById(R.id.edit_profile_icon);
        buttonEditIcon = view.findViewById(R.id.edit_profile_btn_edit_icon);
        buttonEditUsername = view.findViewById(R.id.edit_profile_btn_edit_username);
        buttonEditPassword = view.findViewById(R.id.edit_profile_btn_edit_password);
        buttonEditBio = view.findViewById(R.id.edit_profile_btn_edit_bio);
        buttonDeleteAcc = view.findViewById(R.id.edit_profile_btn_delete_account);

        FloatingActionButton fab = view.findViewById(R.id.bottom_app_bar_fab);
        fab.setVisibility(View.INVISIBLE);

        retrofitClient.getCall(User.class, new HashMap<String, String>() {{
            put("id", String.valueOf(Utils.getUserId()));
        }}).subscribe(jsonArray -> {
            List<User> userList = getObjectList(jsonArray, User.class);
            getActivity().runOnUiThread(() -> {
                username = userList.get(0).getUsername();
                secret = userList.get(0).getPassword();
                Log.i(TAG, "onViewCreated: secret: " + secret);
                textViewGreeting.setText(getString(R.string.edit_profile_greeting, username));
                textViewCurrUsername.setText(username);
            });
        });

        retrofitClient.getCall(Profile.class, new HashMap<String, String>() {{
            put("userId", String.valueOf(Utils.getUserId()));
        }}).subscribe(jsonArray -> {
            List<Profile> profiles = getObjectList(jsonArray, Profile.class);
            if (profiles.size() == 1) {
                requireActivity().runOnUiThread(() -> {
                    editTextBio.setText(profiles.get(0).getBio());
                    Picasso
                            .with(getContext())
                            .load(profiles.get(0).getIconUrl())
                            .fit()
                            .placeholder(R.drawable.show_circular_image)
                            .into(imageViewUserIcon);
                });
            }
        });

        buttonEditIcon.setOnClickListener(v->{
            NavHostFragment.findNavController(EditProfileFragment.this)
                    .navigate(R.id.action_editProfileFragment_to_editIconFragment);
        });

        buttonEditUsername.setOnClickListener(v -> validateUsername(v));

        buttonEditPassword.setOnClickListener(v -> {
            if(validatePassword(v)) {
                updatePassword(v);
            }
        });

        buttonEditBio.setOnClickListener(v -> updateBio(v));

        buttonDeleteAcc.setOnClickListener(v -> deleteAccount(v));
    }

    private void validateUsername(View view) {
        if (editTextNewUsername.getText().toString().isEmpty() || username.equals(editTextNewUsername.getText().toString())) {
            Snackbar.make(view, "Please enter a new username for update.", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.edit_profile_btn_edit_username).setAction("Action", null).show();
            return;
        }

        retrofitClient.getCall(User.class, new HashMap<String, String>() {{
            put("username", editTextNewUsername.getText().toString());
        }}).subscribe(jsonArray -> {
            getActivity().runOnUiThread(() -> {
                List<User> users = getObjectList(jsonArray, User.class);
                if (users.size() > 0) {
                    Snackbar.make(view, "Username not available.", Snackbar.LENGTH_SHORT)
                            .setAnchorView(R.id.edit_profile_btn_edit_username).setAction("Action", null).show();
                } else {
                    updateUsername(view);
                }
            });
        });
    }

    private void updateUsername(View view) {
        retrofitClient.putCall(User.class, new HashMap<String, String>() {{
            put("id", Utils.getUserId().toString());
            put("password", secret);
            put("username", editTextNewUsername.getText().toString());
        }}).subscribe(jsonObject -> {
            User user = getObject(jsonObject, User.class);
            getActivity().runOnUiThread(() -> {
                textViewGreeting.setText(getString(R.string.edit_profile_greeting, user.getUsername()));
                textViewCurrUsername.setText(user.getUsername());
                Snackbar.make(view, "Username updated", Snackbar.LENGTH_SHORT)
                        .setAnchorView(R.id.edit_profile_btn_edit_username).setAction("Action", null).show();
            });
        }, throwable -> {
            Snackbar.make(view, "Unable to update username.", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.edit_profile_btn_edit_username).setAction("Action", null).show();
        });
    }

    private boolean validatePassword(View view) {
        if (editTextNewPassword.getText().toString().isEmpty()
                || editTextConfirmPassword.getText().toString().isEmpty()) {
            Snackbar.make(view, "Please enter a new password for update.", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.edit_profile_btn_edit_password).setAction("Action", null).show();
            return false;
        }
        if (!editTextNewPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())){
            Snackbar.make(view, "Passwords do not match.", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.edit_profile_btn_edit_password).setAction("Action", null).show();
            return false;
        }
        if (editTextConfirmPassword.getText().toString().length() < 6) {
            Snackbar.make(view, "Password must be 6 or more characters.", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.edit_profile_btn_edit_password).setAction("Action", null).show();
            return false;
        }
        return true;
    }

    private void updatePassword(View view) {
        retrofitClient.putCall(User.class, new HashMap<String, String>() {{
            put("id", Utils.getUserId().toString());
            put("username", textViewCurrUsername.getText().toString());
            put("password", editTextConfirmPassword.getText().toString());
        }}).subscribe(jsonObject -> {
            User user = getObject(jsonObject, User.class);
            getActivity().runOnUiThread(() -> {
                secret = editTextConfirmPassword.getText().toString();
                editTextNewPassword.setText("");
                editTextConfirmPassword.setText("");
                Snackbar.make(view, "Password updated.", Snackbar.LENGTH_SHORT)
                        .setAnchorView(R.id.edit_profile_btn_edit_password).setAction("Action", null).show();
            });
        }, throwable -> {
            Snackbar.make(view, "Unable to update password.", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.edit_profile_btn_edit_password).setAction("Action", null).show();
        });
    }

    private void updateBio(View view) {
        retrofitClient.putCall(Profile.class, new HashMap<String, String>() {{
            put("id", Utils.getProfileId().toString());
            put("userId", Utils.getUserId().toString());
            put("username", username);
            put("password", secret);
            put("bio", editTextBio.getText().toString());
        }}).subscribe(jsonObject -> {
            Profile profile = getObject(jsonObject, Profile.class);
            if (profile.getBio().equals(editTextBio.getText().toString())) {
                getActivity().runOnUiThread(() -> {
                    Snackbar.make(view, "Bio updated.", Snackbar.LENGTH_SHORT)
                            .setAnchorView(R.id.edit_profile_btn_edit_bio).setAction("Action", null).show();
                });
            }
        }, throwable -> requireActivity().runOnUiThread(() -> {
            Snackbar.make(view, "Unable to update Bio", Snackbar.LENGTH_SHORT)
                    .setAnchorView(R.id.edit_profile_btn_edit_bio).setAction("Action", null).show();
        }));
//        Snackbar.make(view, "inside updateBio function :D", Snackbar.LENGTH_SHORT)
//                .setAnchorView(R.id.edit_profile_btn_edit_bio).setAction("Action", null).show();
    }

    private void deleteAccount(View view) {
        // TODO: popup confirmation needed!
        // https://developer.android.com/reference/android/widget/PopupWindow
        // https://newbedev.com/how-to-create-a-popup-window-popupwindow-in-android

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        buttonDeleteAccConfirm = popupView.findViewById(R.id.btn_popup_confirm);
        buttonDeleteAccDeny = popupView.findViewById(R.id.btn_popup_deny);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        buttonDeleteAccConfirm.setOnClickListener(v -> {
            retrofitClient.deleteCall(User.class, new HashMap<String, String>() {{
                put("id", Utils.getUserId().toString());
            }}).subscribe(jsonArray-> {
                List<User> users = getObjectList(jsonArray, User.class);
                getActivity().runOnUiThread(() -> {
                    if (users.size() == 1 && users.get(0).getUsername().equals(textViewCurrUsername.getText().toString())) {
                        Toast.makeText(getContext(), "Account deleted.", Toast.LENGTH_SHORT).show();
//                            Snackbar.make(v, "Account deleted.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        Intent goToHomePostActivity = LoginAndSignUpActivity.getIntent(getContext());
                        startActivity(goToHomePostActivity);
                    }
                });
            }, throwable -> {
                Snackbar.make(view, "Unable to delete account.", Snackbar.LENGTH_SHORT)
                        .setAnchorView(R.id.edit_profile_btn_delete_account).setAction("Action", null).show();
            });
        });

        buttonDeleteAccDeny.setOnClickListener(v -> popupWindow.dismiss());
    }
}