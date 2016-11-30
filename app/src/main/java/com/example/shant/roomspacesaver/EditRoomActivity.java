package com.example.shant.roomspacesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EditRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        Bundle b = getIntent().getExtras();
        //storing userid for further use in room manipulation
        Log.d("Bundle - _id ",b.getString("_id"));
        Log.d("Bundle - room name ",b.getString("room_name"));
        Log.d("Bundle - room length ",b.getString("room_length"));
        Log.d("Bundle - room width",b.getString("room_width"));
        Log.d("Bundle - furniture ids",b.getString("furniture_ids"));
    }
}
