package com.example.boardgamesocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class GamesCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_collection);

        Toolbar nav_bar = findViewById(R.id.nav_bar_games_collection);
        nav_bar.setTitle("");
        setSupportActionBar(nav_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navbar_options, menu);
        return true;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, GamesCollectionActivity.class);
    }
}