package com.example.shant.roomspacesaver;

import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.LoginFilter;
import android.util.Log;

/**
 * Created by shant on 22-10-2016.
 */

public class DBHelper extends SQLiteOpenHelper{
    public final static String DATABASE_NAME = "appData.db";
    //create 3 required tables
    public final static String USERS_TABLE = "users";
    public final static String ROOMS_TABLE = "rooms";
    public final static String FURNITURES_TABLE = "furniture";
    //common variable id for all tables
    public final static String ID = "ID";
    // users table
    public final static String USERNAME = "USERNAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String ROOM_IDS = "ROOM_IDS";
    //rooms table
    public final static String ROOM_LENGTH = "ROOM_LENGTH";
    public final static String ROOM_WIDTH = "ROOM_WIDTH";
    public final static String FURNITURE_IDS = "FURNITURE_IDS";
    //furnitures table
    public final static String FURNITURE_LENGTH = "FURNITURE_LENGTH";
    public final static String FURNITURE_WIDTH = "FURNITURE_WIDTH";
    public final static String X_POSITION = "X_POSITION";
    public final static String Y_POSITION = "Y_POSITION";

    // contructor to create table
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getReadableDatabase();// just used to check if db was created when constructor was called
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table whenever class is called, takes is SQLiteDatabse class
        // create all  tables i.e users, rooms, furnitures
        Log.d("hey","there on create");
        db.execSQL("create table " + USERS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, ROOM_IDS TEXT)");//Exposes methods to manage a SQLite database.
        db.execSQL("create table " + ROOMS_TABLE +  " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ROOM_LENGTH TEXT, ROOM_WIDTH TEXT, FURNITURE_IDS TEXT)");
        db.execSQL("create table " + FURNITURES_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FURNITURE_LENGTH TEXT, FURNITURE_WIDTH TEXT, X_POSITION TEXT, Y_POSITION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("hey","there on upgrade");
        db.execSQL("drop table if exists" + USERS_TABLE);
        db.execSQL("drop table if exists" + ROOMS_TABLE);
        db.execSQL("drop table if exists" + FURNITURES_TABLE);
        onCreate(db);

    }

    public boolean insertUser(String username, String password, String room_ids){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME,username);
        contentValues.put(PASSWORD,password);
        contentValues.put(ROOM_IDS,room_ids);
        long result = db.insert(USERS_TABLE, null, contentValues);
        return result == -1 ? false : true;
    }
//
//    public Cursor getUsers(){
//        SQLiteDatabase myDb = this.getWritableDatabase();
//        Cursor result = myDb.rawQuery("select * from " + USERS_TABLE, null);
//        return result;
//    }

    public boolean checkCredentials(String username,String password){
        Log.d("Inside ","checkCredentials");
        /*was getting an error: unable to open databsse file at line below, solution chmod 777 from terminal (data/data/appname/databases)
         to appData.db and appData.db-journal files, file permission error */
        SQLiteDatabase myDb = this.getWritableDatabase();
        Log.d(username,password);
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
