package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Review;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReviewVH extends DataClsVH<Review>{
    public static final String REVIEW_KEY = "review";
    public static final String USER_KEY = "user";

    private static final Map<Review, Bundle> localCache = new HashMap<>();

    private final TextView textViewUsername;
    private final ImageView imageViewUserIcon;
    private final TextView textViewGameReview;
    private final TextView textViewMsgNoReviews;
    private final ImageButton imageButtonDeleteReview;

    public ReviewVH(@NonNull View reviewView, DataClsAdapter.OnItemListener onItemListener) {
        super(reviewView, onItemListener);
        textViewUsername = reviewView.findViewById(R.id.item_username);
        imageViewUserIcon = reviewView.findViewById(R.id.item_user_icon);
        textViewGameReview = reviewView.findViewById(R.id.item_game_review);
        textViewMsgNoReviews = reviewView.findViewById(R.id.tv_msg_no_reviews);
        imageButtonDeleteReview = reviewView.findViewById(R.id.btn_delete_review);
    }

    @Override
    public void toggleVisibility(int visibility) {
        textViewUsername.setVisibility(visibility);
        imageViewUserIcon.setVisibility(visibility);
        textViewGameReview.setVisibility(visibility);
        imageButtonDeleteReview.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, Review review) {
        toggleVisibility(View.INVISIBLE);
        if (localCache.containsKey(review)){
            performBind(activity, (User) Objects.requireNonNull(localCache.get(review)).getSerializable(USER_KEY), review);
        } else {
            retrofitClient.getCall(User.class, new HashMap<String, String>(){{
                put("id", String.valueOf(review.getUserId()));
            }}).subscribe(jsonArray -> {
                List<User> users = getObjectList(jsonArray, User.class);
                if (users.size() > 1) {
                    new Exception("Got many Users on primary key: " + users).printStackTrace();
                } else {
                    performBind(activity, users.get(0), review);
                    contextBundle.putSerializable(USER_KEY, users.get(0));
                    localCache.put(review, contextBundle);
                }
            });
        }

        imageButtonDeleteReview.setOnClickListener(view -> retrofitClient.deleteCall(Review.class, new HashMap<String, String>() {{
            put("id", String.valueOf(review.getId()));
        }}).blockingSubscribe(jsonArray -> {
            Log.i(TAG, "Review deleted!");
        }));
    }

    private void performBind(Activity activity, User user, Review review){
        activity.runOnUiThread(()->{
            toggleVisibility(View.VISIBLE);
            textViewUsername.setText(user.getUsername());
            textViewGameReview.setText(review.getContent());
        });
    }
}
