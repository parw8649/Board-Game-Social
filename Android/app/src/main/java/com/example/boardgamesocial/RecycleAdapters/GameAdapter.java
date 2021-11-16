package com.example.boardgamesocial.RecycleAdapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.Commons.Constants;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.R;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {
    private List<Game> games = new ArrayList<>();

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_items, parent,false);

        return new GameHolder(gameView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        Game currentGame = games.get(position);

        holder.tvGameTitle.setText(currentGame.getGameTitle());
        holder.tvGenre.setText(currentGame.getGenre());
        holder.tvDescription.setText(currentGame.getDescription());

        String minPlayer = String.format(Resources.getSystem().getString(R.string.game_min_player_text), currentGame.getMinPlayer());
        holder.tvMinPlayer.setText(minPlayer);

        String maxPlayer = String.format(Resources.getSystem().getString(R.string.game_max_player_text), currentGame.getMaxPlayer());
        holder.tvMaxPlayer.setText(maxPlayer);

        String playCount = String.format(Resources.getSystem().getString(R.string.game_overall_play_count_text), currentGame.getOverallPlayCount());
        holder.tvOverallPlayCount.setText(playCount);

        Picasso
                .with(holder.itemView.getContext())
                .load(currentGame.getImageUrl())
                .fit()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.ivImageUrl);

    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    static class GameHolder extends RecyclerView.ViewHolder {

        private final ImageView ivImageUrl;
        private final TextView tvGameTitle;
        private final TextView tvGenre;
        private final TextView tvDescription;
        private final TextView tvMinPlayer;
        private final TextView tvMaxPlayer;
        private final TextView tvOverallPlayCount;

        public GameHolder(@NonNull View gameView) {
            super(gameView);

            ivImageUrl = gameView.findViewById(R.id.game_icon);
            tvGameTitle = gameView.findViewById(R.id.tv_game_title);
            tvGenre = gameView.findViewById(R.id.tv_game_genre);
            tvDescription = gameView.findViewById(R.id.game_description);
            tvMinPlayer = gameView.findViewById(R.id.tv_min_player_count);
            tvMaxPlayer = gameView.findViewById(R.id.tv_max_player_count);
            tvOverallPlayCount = gameView.findViewById(R.id.overall_play_count);
        }
    }
}
