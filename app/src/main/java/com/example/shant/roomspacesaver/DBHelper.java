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

    public boolean checkCredentials(String username,String password){
        Log.d("Inside ","checkCredentials");
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor result = myDb.rawQuery("select username,password from users where username=? and password=?",new String[]{username,password});//in java single quotes can take only once charater
        Log.d("result count: ", String.valueOf(result.getCount()));
        Log.d("column count: ", String.valueOf(result.getColumnCount()));
        String tempUsername="";
        String tempPassword="";

        while (result.moveToNext()){
            tempUsername = result.getString(0);
            tempPassword = result.getString(1);
//        Log.d("password: ", result.getString(1));
        }
        Log.d(tempUsername,tempPassword);
        Log.d(username,password);
        Log.d("",""+(result.getCount() ==1));
//        Log.d(tempUsername.getClass().getName(),username.getClass().getName()+(tempUsername.equals(username)));
//        Log.d(tempPassword.getClass().getName(),password.getClass().getName()+(tempPassword.equals(password)));
        if (result.getCount() ==1 && tempUsername.equals(username)&& tempPassword.equals(password)){
//            Log.d("username: ", result.getString(0));
//            Log.d("password: ", result.getString(1));
            return true;
        }else
            return false;

    }
}
