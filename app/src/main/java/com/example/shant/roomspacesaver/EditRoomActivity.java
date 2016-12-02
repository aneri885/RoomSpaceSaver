package com.example.shant.roomspacesaver;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static java.lang.Boolean.TRUE;

public class EditRoomActivity extends AppCompatActivity {


    Button addFurnitureButton;
    AddFurnitureDialogFragment addFurnitureDialogFragment;
    DBHelper myDb;
    int roomId;
    float Length;
    float Width;
    float XPos;
    float YPos;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_room);
        layout = (RelativeLayout)findViewById(R.id.activity_edit_room);
        layout.addView(new RectsDrawingView(EditRoomActivity.this));
        Bundle b = getIntent().getExtras();
        //storing room id for further use in room manipulation
        roomId = Integer.parseInt(b.getString("_id"));
        myDb = new DBHelper(this);
        Log.d("Bundle - _id ", b.getString("_id"));
        Log.d("Bundle - room name ", b.getString("room_name"));
        Log.d("Bundle - room length ", b.getString("room_length"));
        Log.d("Bundle - room width", b.getString("room_width"));
        Log.d("Bundle - furniture ids", b.getString("furniture_ids"));
        final ArrayList<String> furnitureList = new ArrayList<>(Arrays.asList(b.getString("furniture_ids").replaceAll("\\s+","").split(",")));
        Log.d("Furniture count",String.valueOf(furnitureList));
        Log.d("Furniture",String.valueOf(furnitureList));
        Cursor furnitureData = myDb.getFurnitures(furnitureList);
        while (furnitureData.moveToNext()){
            Log.d(furnitureData.getColumnName(0),furnitureData.getString(0));
            Log.d(furnitureData.getColumnName(1),furnitureData.getString(1));
            Log.d(furnitureData.getColumnName(2),furnitureData.getString(2));
            int furnitureId = Integer.parseInt(furnitureData.getString(0));
            Length = Float.parseFloat(furnitureData.getString(1));
            Width = Float.parseFloat(furnitureData.getString(2));
            XPos = Float.parseFloat(furnitureData.getString(3));
            YPos = Float.parseFloat(furnitureData.getString(4));
            RectsDrawingView.obtainTouchedRect(furnitureId,XPos, YPos, Length, Width);

        }
//        HashSet<RectArea> mRects = RectsDrawingView.mRects;
        Log.d("on create","edit room ectivity");

    }

    public void saveRoomLayout(){
        HashSet<RectArea> mRects = RectsDrawingView.mRects;


    }
    public void addFurniture(View view) {
        addFurnitureButton = (Button) findViewById(R.id.addFurnitureButton);
//        addRoomButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("add room inside"," Clicked");
//                addRoomDialogFragment =new AddRoomDialogFragment();
//                Log.d(getFragmentManager().toString(),"   dsfsdf");
//                addRoomDialogFragment.show(getFragmentManager(),"Show addRoom Dialog fragment");
//            }
//        });
        Log.d("add room outside", " Clicked");
//        addRoomDialogFragment =new AddRoomDialogFragment();
        addFurnitureDialogFragment = new AddFurnitureDialogFragment();
        Log.d(getFragmentManager().toString(), "   dsfsdf");
        addFurnitureDialogFragment.show(getFragmentManager(), "Show add furniture Dialog fragment");
    }

    public boolean passNewFurniture(String furnitureLength, String furnitureWidth) {
        Log.d("roomid", String.valueOf(roomId));
        Log.d("New furniture length:  ", furnitureLength);
        Log.d("New furniture width:  ", furnitureWidth);
        Length = Float.parseFloat(furnitureLength);
        Width = Float.parseFloat(furnitureWidth);
//        RectArea rect;
//        rect = new RectArea(150,150,Length,Width);
        RectsDrawingView.obtainTouchedRect(0,50,50,Length,Width);
        return myDb.addFurniture(roomId, furnitureLength, furnitureWidth, "50", "50");//add furniture at origin
    }


    public void furnitureRead(){

    }


    public void addFurnitureResult(String toastMsg) {
        Toast.makeText(EditRoomActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
    }




}