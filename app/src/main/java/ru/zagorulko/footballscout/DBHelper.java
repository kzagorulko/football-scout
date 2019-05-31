package ru.zagorulko.footballscout;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "playerDB";
    static final String TABLE_PLAYERS = "players";

    // database fields
    static final String KEY_ID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_AGE = "age";
    static final String KEY_ASSAULTER_SKILL = "assaulter_skill";
    static final String KEY_DEFENDER_SKILL = "defender_skill";
    static final String KEY_PHYSICAL_SKILL = "physical_skill";
    static final String KEY_POSITION = "position";
    static final String KEY_POTENTIAL = "potential";
    static final String KEY_TEAM = "team";
    static final String KEY_VIEW = "preview";
    static final String KEY_RECOMMENDED = "recommended";
    static final String KEY_GROWTH = "growth";
    static final String KEY_BY_PLAYER = "found_by_player";


    DBHelper(Context context) {
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
                KEY_VIEW + " TINYINT," +
                KEY_RECOMMENDED + " BIT," +
                KEY_GROWTH + " SMALLINT," +
                KEY_BY_PLAYER + " BIT," +
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
                       String team, SQLiteDatabase database, Integer preview) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_AGE, age);
        contentValues.put(KEY_ASSAULTER_SKILL, assaulterSkill);
        contentValues.put(KEY_DEFENDER_SKILL, defenderSkill);
        contentValues.put(KEY_PHYSICAL_SKILL, physicalSkill);
        contentValues.put(KEY_POTENTIAL, potential);
        contentValues.put(KEY_POSITION, position);
        contentValues.put(KEY_TEAM, team);
        contentValues.put(KEY_VIEW, preview);
        contentValues.put(KEY_RECOMMENDED, 0);
        contentValues.put(KEY_GROWTH, 0);
        contentValues.put(KEY_BY_PLAYER, 0);



        database.insert(TABLE_PLAYERS, null, contentValues);
    }
}
