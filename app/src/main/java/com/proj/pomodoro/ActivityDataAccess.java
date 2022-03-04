package com.proj.pomodoro;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ActivityDataAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    public ActivityDataAccess(@Nullable Context context) {
        openHelper = new DB(context);
    }
    public void openDB(){
        database = openHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }
    public boolean addNewACTIVITY(Activity_promo model){
        try {
            String TITLE = model.getTitle();
            int LONG_REST = model.getLong_rest();
            int  SHORT_REST = model.getShort_rest();
            int  SHORT_REST_STEP = model.getShort_rest_step();
            int  MIN = model.getMin();
            ContentValues cv = new ContentValues();
            cv.put(DB.TITLE,TITLE);
            cv.put(DB.LONG_REST,LONG_REST);
            cv.put(DB.SHORT_REST,SHORT_REST);
            cv.put(DB.SHORT_REST_STEP,SHORT_REST_STEP);
            cv.put(DB.MIN,MIN);
            database.insert(DB.TABLE_NAME,null,cv);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @SuppressLint("Range")
    public ArrayList<Activity_promo> getall() {
        int id ;
        String TITLE;
        int LONG_REST;
        int  SHORT_REST ;
        int  SHORT_REST_STEP ;
        int  MIN ;
        ArrayList<Activity_promo> arrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DB.TABLE_NAME + " ORDER BY id DESC";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                LONG_REST = cursor.getInt(cursor.getColumnIndex(DB.LONG_REST));
                SHORT_REST = cursor.getInt(cursor.getColumnIndex(DB.SHORT_REST));
                SHORT_REST_STEP = cursor.getInt(cursor.getColumnIndex(DB.SHORT_REST_STEP));
                MIN = cursor.getInt(cursor.getColumnIndex(DB.MIN));
                id = cursor.getInt(cursor.getColumnIndex(DB.ID));
                TITLE = cursor.getString(cursor.getColumnIndex(DB.TITLE));
                arrayList.add(new Activity_promo(TITLE,MIN,SHORT_REST,LONG_REST,SHORT_REST_STEP,id));

            }while (cursor.moveToNext());
        }
        return arrayList;
    }
    public boolean deleteBYid(int id) {
        try {
            database.delete(DB.TABLE_NAME, "id = " + id, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
