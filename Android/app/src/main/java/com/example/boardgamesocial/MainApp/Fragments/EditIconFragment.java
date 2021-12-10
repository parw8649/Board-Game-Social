package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.boardgamesocial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditIconFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditIconFragment extends Fragment {
    public static final String TAG = "EditIconFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final HashMap<String, ArrayList<String>> ICON_MAP = new HashMap<String, ArrayList<String>>() {{
            put("icon1", new ArrayList<String>() {{
                add("diceGroup");
                add("https://images.pexels.com/photos/7061817/pexels-photo-7061817.jpeg");
            }});
            put("icon2", new ArrayList<String>() {{
                add("fourPawns");
                add("https://images.pexels.com/photos/209728/pexels-photo-209728.jpeg");
            }});
            put("icon3", new ArrayList<String>() {{
                add("puzzlePieces");
                add("https://images.pexels.com/photos/3852577/pexels-photo-3852577.jpeg");
            }});
            put("icon4", new ArrayList<String>() {{
                add("jokerCard");
                add("https://images.pexels.com/photos/3370381/pexels-photo-3370381.jpeg");
            }});
            put("icon5", new ArrayList<String>() {{
                add("unoHand");
                add("https://images.pexels.com/photos/2689343/pexels-photo-2689343.jpeg");
            }});
            put("icon6", new ArrayList<String>() {{
                add("dominoes");
                add("https://images.pexels.com/photos/585293/pexels-photo-585293.jpeg");
            }});
        }};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditIconFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditIconFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditIconFragment newInstance(String param1, String param2) {
        EditIconFragment fragment = new EditIconFragment();
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
        return inflater.inflate(R.layout.fragment_edit_icon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = requireActivity().findViewById(R.id.bottom_app_bar_fab);
        fab.setVisibility(View.INVISIBLE);

        ImageView imageView1 = view.findViewById(R.id.user_icon_choice_1);
        ImageView imageView2 = view.findViewById(R.id.user_icon_choice_2);
        ImageView imageView3 = view.findViewById(R.id.user_icon_choice_3);
        ImageView imageView4 = view.findViewById(R.id.user_icon_choice_4);
        ImageView imageView5 = view.findViewById(R.id.user_icon_choice_5);
        ImageView imageView6 = view.findViewById(R.id.user_icon_choice_6);

        Picasso
                .with(getContext())
                .load(ICON_MAP.get("icon1").get(1))
                .fit()
                .placeholder(R.drawable.show_circular_image)
                .into(imageView1);

        Picasso
                .with(getContext())
                .load(ICON_MAP.get("icon2").get(1))
                .fit()
                .placeholder(R.drawable.show_circular_image)
                .into(imageView2);

        Picasso
                .with(getContext())
                .load(ICON_MAP.get("icon3").get(1))
                .fit()
                .placeholder(R.drawable.show_circular_image)
                .into(imageView3);

        Picasso
                .with(getContext())
                .load(ICON_MAP.get("icon4").get(1))
                .fit()
                .placeholder(R.drawable.show_circular_image)
                .into(imageView4);

        Picasso
                .with(getContext())
                .load(ICON_MAP.get("icon5").get(1))
                .fit()
                .placeholder(R.drawable.show_circular_image)
                .into(imageView5);

        Picasso
                .with(getContext())
                .load(ICON_MAP.get("icon6").get(1))
                .fit()
                .placeholder(R.drawable.show_circular_image)
                .into(imageView6);
    }
}