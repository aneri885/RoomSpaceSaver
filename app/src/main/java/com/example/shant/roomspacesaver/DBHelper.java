package com.example.shant.roomspacesaver;

import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shant on 22-10-2016.
 */

public class DBHelper extends SQLiteOpenHelper{
    public final static String DATABASE_NAME = "appData.db";
    public final static String TABLE_NAME = "users";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "USERNAME";
    public final static String COL_3 = "PASSWORD";
    public final static String COL_4 = "ROOM_IDS";

    // contructor to create table
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getReadableDatabase(); just used to check if db was created when constructor was called
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table whenever class is called, takes is SQLiteDatabse class
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, ROOM_IDS TEXT)");//Exposes methods to manage a SQLite database.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if extists" + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertUser(String username, String password, String room_ids){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        contentValues.put(COL_4,room_ids);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result == -1 ? false : true;
    }
//
//    public Cursor getUsers(){
//        SQLiteDatabase myDb = this.getWritableDatabase();
//        Cursor result = myDb.rawQuery("select * from " + TABLE_NAME, null);
//        return result;
//    }

    public Cursor checkCredentials(){
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor result = myDb.rawQuery("select * from users",null);
        return result;
    }
}
