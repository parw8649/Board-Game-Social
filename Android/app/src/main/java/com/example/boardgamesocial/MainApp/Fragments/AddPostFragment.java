package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {

    public static final String TAG = "AddPostFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Post newPost;
    EditText editTextBody;
    Spinner spinnerPostType;
    boolean isPrivate;
    boolean typeChecked;
    private FloatingActionButton fab;

    public AddPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
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
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = requireActivity().findViewById(R.id.bottom_app_bar_fab);
        fab.setVisibility(View.INVISIBLE);

        RadioButton radioButtonPrivate = view.findViewById(R.id.add_post_radioBtn_private);
        RadioButton radioButtonPublic = view.findViewById(R.id.add_post_radioBtn_public);
        Button buttonAddPost = view.findViewById(R.id.add_post_button);
        editTextBody = view.findViewById(R.id.add_post_editText_body);
        spinnerPostType = view.findViewById(R.id.add_post_spinner_type);
        ArrayAdapter<CharSequence> adapterPostType = ArrayAdapter.createFromResource(getContext(), R.array.post_types, android.R.layout.simple_spinner_item);

        adapterPostType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPostType.setAdapter(adapterPostType);

        radioButtonPrivate.setOnClickListener(v -> setPostViewSetting(v));
        radioButtonPublic.setOnClickListener(v -> setPostViewSetting(v));

        typeChecked = false;

        buttonAddPost.setOnClickListener(v -> {
            if (validatePost(v)) {
                makePost(v);
            }
        });
    }

    private boolean validatePost(View view) {
        Log.i(TAG, "validatePost: Post body: " + editTextBody.getText().toString());
        Log.i(TAG, "validatePost: Type selected: " + spinnerPostType.getSelectedItem().toString());
        Log.i(TAG, "validatePost: isPrivate: " + isPrivate);

        if (editTextBody.getText().toString().length() == 0) {
            Snackbar.make(view, R.string.add_post_body_error, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.add_post_button).setAction("Action", null).show();
            return false;
        }
        if (spinnerPostType.getSelectedItem().toString().equals(getString(R.string.add_post_spinner_placeholder))) {
            Snackbar.make(view, R.string.add_post_type_error, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.add_post_button).setAction("Action", null).show();
            return false;
        }
        if (!typeChecked) {
            Snackbar.make(view, R.string.add_post_view_error, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.add_post_button).setAction("Action", null).show();
            return false;
        }

        newPost = new Post(Utils.getUserId(), editTextBody.getText().toString(),
                            spinnerPostType.getSelectedItem().toString(), isPrivate, null);
        return true;
    }

    public void setPostViewSetting(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.add_post_radioBtn_private:
                if (checked) {
                    isPrivate = true;
                }
                break;
            case R.id.add_post_radioBtn_public:
                if (checked) {
                    isPrivate = false;
                }
                break;
        }
        typeChecked = true;
    }

    private void makePost(View view) {
        RetrofitClient retrofitClient = RetrofitClient.getClient();
            retrofitClient.postCall(Post.class, newPost)
                    .subscribe(jsonObject -> {
                        Post madePost = retrofitClient.getObject(jsonObject, Post.class);
                        Log.i(TAG, String.format("makePost: newPost: %s", madePost.toString()));
                        requireActivity().runOnUiThread(() -> {
                            Snackbar.make(view, R.string.add_post_success, Snackbar.LENGTH_LONG)
                                    .setAnchorView(R.id.bottom_app_bar).setAction("Action", null).show();
                            fab.setVisibility(View.VISIBLE);
                            NavHostFragment.findNavController(AddPostFragment.this)
                                    .navigate(R.id.action_addPostFragment_to_HomePostFragment);
                        });

                    }, throwable ->  {
                        Snackbar.make(view, "Unable to make Post.", Snackbar.LENGTH_LONG)
                                .setAnchorView(R.id.bottom_app_bar).setAction("Action", null).show();
                    });
    }
}