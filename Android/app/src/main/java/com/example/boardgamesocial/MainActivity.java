package com.example.boardgamesocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // adapting ActionBar
        // https://youtu.be/DMkzIOLppf4?t=245
        Toolbar nav_bar = findViewById(R.id.nav_bar);
        nav_bar.setTitle("");
        setSupportActionBar(nav_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // https://www.youtube.com/watch?v=oh4YOj9VkVE&list=PLH_uDgcDMwAxUzPvH66AKEZfVh5iaF1nB&index=1
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navbar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // TODO: as pages are worked on, swap out placeholders!
        switch (item.getItemId()) {
            case R.id.home_option:
                Toast.makeText(this, "home option selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.games_option:
                Toast.makeText(this, "games option selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.search_option:
                Toast.makeText(this, "search option selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.profile_option:
                Toast.makeText(this, "profile option selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout_option:
                Toast.makeText(this, "logout option selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}