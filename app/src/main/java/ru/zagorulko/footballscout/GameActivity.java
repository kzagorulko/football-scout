package ru.zagorulko.footballscout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Toast toast;

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    toast = Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT);
                    toast.show();
                    // TODO {changeFragment}
                    return true;
                case R.id.navigation_task:
                    fragment = new TaskFragment();
                    toast = Toast.makeText(getApplicationContext(), "Task", Toast.LENGTH_SHORT);
                    toast.show();
                    changeFragment(fragment);
                    Objects.requireNonNull(getSupportActionBar())
                            .setTitle(Settings.getPositionsThatRequireStrengthening(getApplicationContext()));

                    return true;
                case R.id.navigation_search:
                    fragment = new FindFragment();
                    toast = Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT);
                    toast.show();
                    changeFragment(fragment);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(Settings.getTeamsForSearch(getApplicationContext()));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Objects.requireNonNull(getSupportActionBar()).setTitle(Settings.getPositionsThatRequireStrengthening(this));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.game_screen, fragment, "FRAGMENT1");
        transaction.commit();
    }
}
