package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;
import com.squareup.picasso.Picasso;

public class GameVH extends DataClsVH<Game> {
    private final ImageView ivImageUrl;
    private final TextView tvGameTitle;
    private final TextView tvGenre;
    private final TextView tvDescription;
    private final TextView tvMinPlayer;
    private final TextView tvMaxPlayer;
    private final TextView tvOverallPlayCount;

    public GameVH(@NonNull View gameView, DataClsAdapter.OnItemListener onItemListener) {
        super(gameView, onItemListener);
        ivImageUrl = gameView.findViewById(R.id.game_icon);
        tvGameTitle = gameView.findViewById(R.id.tv_game_title);
        tvGenre = gameView.findViewById(R.id.tv_game_genre);
        tvDescription = gameView.findViewById(R.id.game_description);
        tvMinPlayer = gameView.findViewById(R.id.tv_min_player_count);
        tvMaxPlayer = gameView.findViewById(R.id.tv_max_player_count);
        tvOverallPlayCount = gameView.findViewById(R.id.overall_play_count);
    }

    @Override
    public void toggleVisibility(int visibility) {
        ivImageUrl.setVisibility(visibility);
        tvGameTitle.setVisibility(visibility);
        tvGenre.setVisibility(visibility);
        tvDescription.setVisibility(visibility);
        tvMinPlayer.setVisibility(visibility);
        tvMaxPlayer.setVisibility(visibility);
        tvOverallPlayCount.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, Game game) {
        tvGameTitle.setText(game.getGameTitle());
        tvGenre.setText(game.getGenre());
        tvDescription.setText(game.getDescription());
        tvMinPlayer.setText(
                String.format(activity.getResources().getString(R.string.game_min_player_text), game.getMinPlayer())
        );
        tvMaxPlayer.setText(
                String.format(activity.getResources().getString(R.string.game_max_player_text), game.getMaxPlayer())
        );
        tvOverallPlayCount.setText(
                String.format(activity.getResources().getString(R.string.game_overall_play_count_text), game.getOverallPlayCount())
        );
        Picasso
                .with(itemView.getContext())
                .load(game.getImageUrl())
                .fit()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(ivImageUrl);
    }
}
