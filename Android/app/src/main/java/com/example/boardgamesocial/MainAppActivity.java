package com.example.boardgamesocial;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.example.boardgamesocial.databinding.ActivityMainAppBinding;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainAppActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainAppBinding binding;
    private NavController navController;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_app);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);

        fab = findViewById(R.id.bottom_app_bar_fab);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_option:
                        navController.navigate(R.id.HomePostFragment);
                        fab.show();
                        break;
                    case R.id.events_option:
                        navController.navigate(R.id.eventsFragment);
                        fab.show();
                        break;
                    case R.id.games_option:
                        // this should direct to a fragment showing all games available in database
                        navController.navigate(R.id.gameCollectionFragment);
                        fab.show();
                        break;
                    case R.id.search_option:
                        navController.navigate(R.id.searchFragment);
                        fab.hide();
                        break;
                    case R.id.profile_option:
                        navController.navigate(R.id.profileFragment);
                        fab.hide();
                        break;
                    case R.id.logout_option:
                        Intent goToHomePostActivity = LoginAndSignUpActivity.getIntent(MainAppActivity.this);
                        startActivity(goToHomePostActivity);
                        break;
                }
                setFabOnClick(item.getItemId());
                return true;
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

    private void setFabOnClick(int fragmentId) {
        switch (fragmentId) {
            case R.id.home_option:
                fab.setOnClickListener(v -> {
                    Snackbar.make(v, "Eventually, you'll be able to add a new post ", Snackbar.LENGTH_SHORT)
                            .setAnchorView(R.id.bottom_app_bar_fab).setAction("Action", null).show();
                    navController.navigate(R.id.addPostFragment);
                });
                return;
            case R.id.events_option:
                fab.setOnClickListener(v -> {
                    Snackbar.make(v, "Eventually, you'll be able to add a new event", Snackbar.LENGTH_SHORT)
                            .setAnchorView(R.id.bottom_app_bar_fab).setAction("Action", null).show();
//                    navController.navigate(R.id.addEventFragment);
                });
                return;
            case R.id.games_option:
                fab.setOnClickListener(v -> {
                    Snackbar.make(v, "Eventually, you'll be able to add a new game to your collection ", Snackbar.LENGTH_SHORT)
                            .setAnchorView(R.id.bottom_app_bar_fab).setAction("Action", null).show();
//                    navController.navigate(R.id.addGameFragment);
                });
                return;
            case R.id.search_option:
                fab.hide();
            case R.id.profile_option:
                fab.hide();
        }
    }
}