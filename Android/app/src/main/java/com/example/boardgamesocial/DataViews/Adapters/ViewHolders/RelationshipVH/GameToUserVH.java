package com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Relationships.GameToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;
import com.example.boardgamesocial.R;

public class GameToUserVH extends DataClsVH<GameToUser> {

    public static final String GAME_TO_USER_KEY = "game_to_user";
    private final ImageView ivImageUrl;
    private final TextView tvGameTitle;
    private final TextView tvGenre;
    private final TextView tvDescription;
    private final TextView tvMinPlayer;
    private final TextView tvMaxPlayer;
    private final TextView tvOverallPlayCount;
    private final ImageButton ibtnDeleteGame;

    public GameToUserVH(@NonNull View gameToUserView, DataClsAdapter.OnItemListener onItemListener) {
        super(gameToUserView, onItemListener);
        ivImageUrl = gameToUserView.findViewById(R.id.game_icon);
        tvGameTitle = gameToUserView.findViewById(R.id.tv_game_title);
        tvGenre = gameToUserView.findViewById(R.id.tv_game_genre);
        tvDescription = gameToUserView.findViewById(R.id.game_description);
        tvMinPlayer = gameToUserView.findViewById(R.id.tv_min_player_count);
        tvMaxPlayer = gameToUserView.findViewById(R.id.tv_max_player_count);
        tvOverallPlayCount = gameToUserView.findViewById(R.id.overall_play_count);
        ibtnDeleteGame = gameToUserView.findViewById(R.id.btn_delete_game);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, GameToUser gameToUser) {

    }
}
