package ru.zagorulko.footballscout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
// import android.support.v5.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class DataGeneration extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_generation);

        dbHelper = new DBHelper(this);

        generate(10, true);

        Settings.setEnergy(this, 20);

        Intent intent = new Intent(this, ChooseTeamActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_data_generation);
        finish();
    }

    private void generate(int numbersOfPlayers, boolean firstGeneration) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        for (String team : Settings.getTeamNames(this)) {

            for (int i = 0; i < numbersOfPlayers; i++) {


                String name = Settings.getName(this);
                int age = firstGeneration ? (int) (Math.random() * 17 + 17) : 16;

                // skills
                int assaultSkill = (int) (Math.random() * 20 + 1);
                int defendSkill = (int) (Math.random() * 20 + 1);
                int physicalSkill = (int) (Math.random() * 20 + 1);

                String position = Settings.getPositions(this)[
                        firstGeneration ? i % Settings.getPositions(this).length:
                        (int)(Math.random() * Settings.getPositions(this).length)
                        ];

                int potential = assaultSkill + physicalSkill + defendSkill +
                        (int) (Math.random() * (61 - (assaultSkill + physicalSkill + defendSkill)));

                int[] views = {0, 1, 10, 11, 100, 101, 110, 111};
                dbHelper.insert(name, age, assaultSkill, defendSkill, physicalSkill, position, potential, team, database,
                        views[(int)(Math.random() * views.length)]);
            }
        }
    }

}