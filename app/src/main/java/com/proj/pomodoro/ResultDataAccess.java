package com.proj.pomodoro;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ResultDataAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    public ResultDataAccess(@Nullable Context context) {
        openHelper = new DB(context);
    }
    public void openDB(){
        database = openHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }
    public boolean addNewresult(result_time model){
        try {
            String TITLE = model.getTitle();
            int total_focus_time = model.getTotal_focus_time();
            int  total_rest = model.getTotal_rest();
            Date date = model.getDate();
            ContentValues cv = new ContentValues();
            String time_str = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
            Log.i("time_str", time_str);
            cv.put(DB.RES_TITLE,TITLE);
            cv.put(DB.TOTAL_FOCUS,total_focus_time);
            cv.put(DB.TOTAL_REST,total_rest);
            cv.put(DB.DATE,time_str);
            database.insert(DB.RESULT_TABLE_NAME,null,cv);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @SuppressLint("Range")
    public ArrayList<result_time> getall() throws ParseException {
        String TITLE;
        int total_focus_time;
        int  total_rest ;
        int  id ;
        String date ;
        ArrayList<result_time> arrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DB.RESULT_TABLE_NAME + " ORDER BY id DESC";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                total_focus_time = cursor.getInt(cursor.getColumnIndex(DB.TOTAL_FOCUS));
                total_rest = cursor.getInt(cursor.getColumnIndex(DB.TOTAL_REST));
                date = cursor.getString(cursor.getColumnIndex(DB.DATE));
                id = cursor.getInt(cursor.getColumnIndex(DB.RES_ID));
                TITLE = cursor.getString(cursor.getColumnIndex(DB.RES_TITLE));
                //String string = "January 2, 2010";
                Date thedate = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(date);
                arrayList.add(new result_time(TITLE,total_focus_time,total_rest,thedate,id));
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
    public boolean deleteBYid(String id) {
        try {
            database.delete(DB.RESULT_TABLE_NAME, "id = " + id, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
