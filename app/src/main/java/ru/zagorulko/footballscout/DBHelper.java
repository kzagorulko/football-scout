package ru.zagorulko.footballscout;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "playerDB";
    private static final String TABLE_PLAYERS = "players";

    // database fields
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_ASSAULTER_SKILL = "assaulter_skill";
    private static final String KEY_DEFENDER_SKILL = "defender_skill";
    private static final String KEY_PHYSICAL_SKILL = "physical_skill";
    private static final String KEY_POSITION = "position";
    private static final String KEY_POTENTIAL = "potential";
    private static final String KEY_TEAM = "team";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PLAYERS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_AGE + " INTEGER," +
                KEY_ASSAULTER_SKILL + " INTEGER," +
                KEY_DEFENDER_SKILL + " INTEGER," +
                KEY_PHYSICAL_SKILL + " INTEGER," +
                KEY_POTENTIAL + " INTEGER," +
                KEY_POSITION + " TEXT," +
                KEY_TEAM + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dropTable(String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tablename + ";");
    }

    public void insert(String name, int age, int assaulterSkill, int defenderSkill, int physicalSkill, String position, int potential,
                       String team, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_AGE, age);
        contentValues.put(KEY_ASSAULTER_SKILL, assaulterSkill);
        contentValues.put(KEY_DEFENDER_SKILL, defenderSkill);
        contentValues.put(KEY_PHYSICAL_SKILL, physicalSkill);
        contentValues.put(KEY_POTENTIAL, potential);
        contentValues.put(KEY_POSITION, position);
        contentValues.put(KEY_TEAM, team);



        database.insert(TABLE_PLAYERS, null, contentValues);
    }
}
