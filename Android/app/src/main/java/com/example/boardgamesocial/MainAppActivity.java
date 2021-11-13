package com.example.boardgamesocial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.boardgamesocial.databinding.ActivityMainAppBinding;

public class MainAppActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainAppBinding binding;
    private NavController navController;
    private BottomAppBar bottomAppBar;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_app);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);

        fab = findViewById(R.id.home_post_fab);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Fragment selectedfragment = null;

                switch (item.getItemId()) {
                    case R.id.home_option:
                        selectedfragment = HomePostFragment.newInstance(null, null);
                        break;
                    case R.id.events_option:
                        selectedfragment = new EventsFragment();
                        break;
                    case R.id.games_option:
                        // this should direct to a fragment showing all games available in database
                        selectedfragment = GameCollectionFragment.newInstance(null, null);
                        break;
                    case R.id.search_option:
                        selectedfragment = SearchFragment.newInstance(null, null);
                        break;
                    case R.id.profile_option:
                        selectedfragment = ProfileFragment.newInstance(null, null);
                        break;
                    case R.id.logout_option:
                        Intent goToHomePostActivity = LoginAndSignUpActivity.getIntent(MainAppActivity.this);
                        startActivity(goToHomePostActivity);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main_app, selectedfragment).commit();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Eventually, you'll be able to add a new post ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main_app, new AddPostFragment()).commit();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_app);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.navbar_options, menu);
        return true;
    }

    public static Intent getIntent(Context context) {
        System.out.println("Inside MainAppActivity getIntent!");
        return new Intent(context, MainAppActivity.class);
    }
}