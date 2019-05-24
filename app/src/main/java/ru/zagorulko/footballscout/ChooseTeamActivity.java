package ru.zagorulko.footballscout;

import android.content.Intent;

import android.os.Bundle;
/* mport android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView; */
import android.widget.Toast;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseTeamActivity extends AppCompatActivity implements RecyclerAdapter.OnItemListener{

    private int[] images = Settings.getTeamImages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team);

        RecyclerView recyclerView = findViewById(R.id.teamsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Objects.requireNonNull(getSupportActionBar()).setTitle(Settings.getChooseTeamTitle(this));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(Settings.getTeamImages(), Settings.getTeamNames(this), this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Settings.setSelectedTeam(this, position);
        Toast toast = Toast.makeText(this, (Settings.getLanguage(this).equals("RU") ? "Вы выбрали " :
                Settings.getLanguage(this).equals("EN") ? "You selected " : "") +
                        Settings.getTeamNames(this)[Settings.getSelectedTeam(this)], Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_data_generation);
        finish();
    }
}
