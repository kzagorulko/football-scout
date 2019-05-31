package ru.zagorulko.footballscout;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
// import android.support.v5.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class DataGeneration extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_generation);



        generate( this,10,true);

        Settings.setEnergy(this, 20);

        Intent intent = new Intent(this, ChooseTeamActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_data_generation);
        finish();
    }

    static void generate(Context context, int numbersOfPlayers, boolean firstGeneration) {

        DBHelper dbHelper = new DBHelper(context);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        for (String team : Settings.getTeamNames(context)) {

            for (int i = 0; i < numbersOfPlayers; i++) {


                String name = Settings.getName(context);
                int age = firstGeneration ? (int) (Math.random() * 17 + 17) : 16;

                // skills
                int assaultSkill = (int) (Math.random() * 20 + 1);
                int defendSkill = (int) (Math.random() * 20 + 1);
                int physicalSkill = (int) (Math.random() * 20 + 1);

                String position = Settings.getPositions(context)[
                        firstGeneration ? i % Settings.getPositions(context).length:
                        (int)(Math.random() * Settings.getPositions(context).length)
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