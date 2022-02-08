package com.proj.pomodoro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public static final String DB_NAME = "pomodoro.db";
    public static final int DB_VERSION =2;
    //Activity table
    public static final String TABLE_NAME = "pomodoro";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String MIN = "min";
    public static final String SHORT_REST = "short_rest" ;
    public static final String LONG_REST = "long_rest" ;
    public static final String SHORT_REST_STEP = "Short_rest_step" ;
    //result table
    public static final String RESULT_TABLE_NAME = "result";
    public static final String RES_ID = "id";
    public static final String RES_TITLE = "title";
    public static final String DATE = "date";
    public static final String TOTAL_FOCUS = "total_focus" ;
    public static final String TOTAL_REST = "total_rest" ;

    public DB (@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query  = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                TITLE + " TEXT , " +
                MIN + " INTEGER , " +
                SHORT_REST + " INTEGER , " +
                LONG_REST + " INTEGER , " +
                SHORT_REST_STEP + " INTEGER ) ";

        sqLiteDatabase.execSQL(query);
        String query2  = "CREATE TABLE IF NOT EXISTS " + RESULT_TABLE_NAME + " ( " +
                RES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                RES_TITLE + " TEXT , " +
                DATE + " TEXT , " +
                TOTAL_REST + " INTEGER , " +
                TOTAL_FOCUS + " INTEGER ) ";

        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME ;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}
