package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.HostedGame;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;

public class HostedGameVH extends DataClsVH<HostedGame> {
    public static final String HOSTED_GAME_KEY = "hosted_game";
    // TODO: Double Check views
    private final ImageView ivGameIcon;
    private final TextView tvGameTitle;
    private final TextView tvGameGenre;
    private final CheckBox ckHostedGame;

    public HostedGameVH(@NonNull View hostedGameView, DataClsAdapter.OnItemListener onItemListener) {
        super(hostedGameView, onItemListener);
        ivGameIcon = hostedGameView.findViewById(R.id.game_icon);
        tvGameTitle = hostedGameView.findViewById(R.id.tv_game_title);
        tvGameGenre = hostedGameView.findViewById(R.id.tv_game_genre);
        ckHostedGame = hostedGameView.findViewById(R.id.ck_hosted_game);
    }

    @Override
    public void toggleVisibility(int visibility) {
        ivGameIcon.setVisibility(visibility);
        tvGameTitle.setVisibility(visibility);
        tvGameGenre.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, HostedGame hostedGame) {
       //TODO: Make calls to get game info from game id
    }
}
