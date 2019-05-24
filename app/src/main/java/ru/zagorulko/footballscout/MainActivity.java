package ru.zagorulko.footballscout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Settings settings;
    private DBHelper dbHelper;
    Button newGameButton, continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        // settings initialisation


        // THIS IS HARD CODE
        Settings.setLanguage(this, "RU");



        newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(this);

        continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.newGameButton:

                dbHelper = new DBHelper(this);
                dbHelper.dropTable("players");
                dbHelper.onCreate(dbHelper.getWritableDatabase());

                // start another activity
                intent = new Intent(this, DataGeneration.class);
                startActivity(intent);
                setContentView(R.layout.activity_data_generation);

                break;

            case R.id.continueButton:

                SQLiteDatabase db = new DBHelper(this).getReadableDatabase();
                @SuppressLint("Recycle")
                Cursor cursor = db.query("players", new String[]{"age"}, null, null, null,
                        null, null);

                if(cursor == null) {
                    Toast toast = Toast.makeText(this, "Database is empty. Please, create new game.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if(Settings.getSelectedTeam(this) != -1)
                        intent = new Intent(this, GameActivity.class);
                    else
                        intent = new Intent(this, ChooseTeamActivity.class);
                    startActivity(intent);
                    setContentView(R.layout.activity_data_generation);
                }

                break;

            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        setContentView(R.layout.activity_main);

        newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(this);

        continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);
    }
}
