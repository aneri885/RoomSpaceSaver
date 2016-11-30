package com.example.shant.roomspacesaver;

import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.StringPrepParseException;
import android.text.LoginFilter;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

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
    public final static String ID = "_id";
    // users table
    public final static String USERNAME = "USERNAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String ROOM_IDS = "ROOM_IDS";
    //rooms table
    public final static String ROOM_NAME = "ROOM_NAME";
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
        SQLiteDatabase db = this.getReadableDatabase();// just used to check if db was created when constructor was called
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table whenever class is called, takes is SQLiteDatabse class
        // create all  tables i.e users, rooms, furnitures
        Log.d("hey","there on create");
        db.execSQL("create table " + USERS_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, ROOM_IDS TEXT)");//Exposes methods to manage a SQLite database.
        db.execSQL("create table " + ROOMS_TABLE +  " (_id INTEGER PRIMARY KEY AUTOINCREMENT,ROOM_NAME, ROOM_LENGTH TEXT, ROOM_WIDTH TEXT, FURNITURE_IDS TEXT)");
        db.execSQL("create table " + FURNITURES_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, FURNITURE_LENGTH TEXT, FURNITURE_WIDTH TEXT, X_POSITION TEXT, Y_POSITION TEXT)");
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

    public boolean addRoom(int userId, String roomName, String roomLength, String roomWidth, String furnitureIds){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ROOM_NAME,roomName);
        contentValues.put(ROOM_LENGTH,roomLength);
        contentValues.put(ROOM_WIDTH,roomWidth);
        contentValues.put(FURNITURE_IDS,furnitureIds);
        //id of inserted room is returned by db.insert
        long roomNumber = db.insert(ROOMS_TABLE,null,contentValues);
        //need to add result to array of rooms of user
        int result = addRoomToUser(userId,roomNumber);
        Log.d("Room inserted: ",String.valueOf(roomNumber));
        return roomNumber == -1 ? false : true;
    }
    public int addRoomToUser(int userId,long roomNumber){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select room_ids from users where _id=?",new String[]{String.valueOf(userId)});//in java single quotes can take only once charater
        Log.d("result count: ", String.valueOf(result.getCount()));
        Log.d("column count: ", String.valueOf(result.getColumnCount()));
        String room_ids="";
        while (result.moveToNext()){
            Log.d("ids: ",result.toString());
            room_ids = result.getString(0);
        }
        Log.d("Room ids: ",room_ids);
        String[] rooms = room_ids.split(",");
        Log.d("Room ids: ",Arrays.toString(rooms));
        ArrayList<String> roomsList = new ArrayList<>(Arrays.asList(rooms));
        roomsList.add(roomsList.size(),String.valueOf(roomNumber));
        Log.d("Room ids: ",roomsList.toString());
        String test = roomsList.toString();
        Log.d("Room ids: ",test.substring(1,test.length()-1));
        test = test.substring(1,test.length()-1);
        ContentValues contentValues= new ContentValues();
        contentValues.put(ROOM_IDS,test);
        int count = db.update(USERS_TABLE,contentValues,ID+"=?",new String[]{String.valueOf(userId)});
        Log.d("count=",String.valueOf(count));
        return count;
    }

//    public ArrayList getRoomsList(String userId){
//        SQLiteDatabase myDb = this.getReadableDatabase();
//        Cursor result = myDb.rawQuery("select room_ids from users where id=?",new String[]{userId});
//        Log.d("result count: ", String.valueOf(result.getCount()));
//        String roomList="";
//        while (result.moveToNext()){
//            Log.d("Room ids for list view:",result.getString(0));
//            roomList = result.getString(0);
//        }
//        roomList = roomList.replaceAll("\\s+","");// remove all spaces from string (why are spaces being inserted?)
//        String[] rooms=roomList.split(",");
//        ArrayList<String> roomsList = new ArrayList<>(Arrays.asList(rooms));
//        return roomsList;
//    }

//    public ArrayList<Room> getRooms(ArrayList<String> rooms){
//        SQLiteDatabase myDb = this.getReadableDatabase();
//        String temp = rooms.toString();
//        temp = temp.substring(1,temp.length()-1);
//        Log.d("getRooms",temp);
//        Cursor result = myDb.rawQuery("select id,room_name,room_length,room_width,furniture_ids from rooms where id in ("+temp+")",new String[]{});
////        Log.d("getRooms",temp.replaceAll("[0-9]+g","?"));
//        //can also do temp.replaceAll("/[0-9]]","?") for multiple question marks, regex doesn't work
//        Log.d("result count: ", String.valueOf(result.getCount()));
//        Log.d("column count: ", String.valueOf(result.getColumnCount()));
//        Room[] room=new Room[result.getCount()];
//        ArrayList<Room> roomsData = new ArrayList<Room>();
//        int i=0;
//        while (result.moveToNext()){
//            Log.d("id:",result.getString(0));
//            Room roomData=new Room();
//            roomData.roomName = result.getString(0);
//            roomData.roomLength= result.getString(1);
//            roomsData.add(i,roomData);
//            Log.d("room_name:",result.getString(0));
//            Log.d("room_length:",result.getString(1));
//            Log.d("room_width:",result.getString(2));
//            Log.d("furniture_ids:",result.getString(3));
//            i++;
//        }
//        return roomsData;
//    }

    public Cursor getRooms(ArrayList<String> rooms){
        SQLiteDatabase myDb = this.getReadableDatabase();
        Cursor result = myDb.rawQuery("select * from rooms where _id in ("+rooms.toString().replace("[","").replace("]","")+")",new String[]{});
        return result;

    }

    public Cursor getRoomDetails(long id){
        SQLiteDatabase myDb = this.getReadableDatabase();
        Cursor result = myDb.rawQuery("select * from rooms where _id in ?",new String[]{String.valueOf(id)});
        return result;
    }

    public String[] checkCredentials(String username,String password){
        Log.d("Inside ","checkCredentials");
        /*was getting an error: unable to open database file at line below, solution chmod 777 from terminal (data/data/appname/databases)
         to appData.db and appData.db-journal files, file permission error */
        SQLiteDatabase myDb = this.getWritableDatabase();
        Log.d(username,password);
        Cursor result = myDb.rawQuery("select _id,username,password,room_ids from users where username=? and password=?",new String[]{username,password});//in java single quotes can take only once charater
        Log.d("result count: ", String.valueOf(result.getCount()));
        Log.d("column count: ", String.valueOf(result.getColumnCount()));
        int tempId = -1;
        String tempUsername="";
        String tempPassword="";
        String tempRooms="";
        while (result.moveToNext()){
            tempId=result.getInt(0);
            tempUsername = result.getString(1);
            tempPassword = result.getString(2);
            tempRooms = result.getString(3);
//        Log.d("password: ", result.getString(1));
        }
        Log.d(String.valueOf(tempId),tempPassword);
        Log.d(username,password);
        Log.d("",""+(result.getCount() ==1));
//        Log.d(tempUsername.getClass().getName(),username.getClass().getName()+(tempUsername.equals(username)));
//        Log.d(tempPassword.getClass().getName(),password.getClass().getName()+(tempPassword.equals(password)));
        if (result.getCount() ==1 && tempUsername.equals(username)&& tempPassword.equals(password)){
//            Log.d("username: ", result.getString(0));
//            Log.d("password: ", result.getString(1));
            String[] answer=new String[]{String.valueOf(tempId),"true"};
            return new String[]{"true",String.valueOf(tempId),tempUsername,tempPassword,tempRooms};
        }else
            return new String[]{"false",String.valueOf(tempId)};

    }
}
