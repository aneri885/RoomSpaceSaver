package com.example.shant.roomspacesaver;

import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RoomsActivity extends AppCompatActivity {
    Button addRoomButton;
    AddRoomDialogFragment addRoomDialogFragment;
    DBHelper myDb;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        //storing userid for further use in room manipulation
        userId = Integer.parseInt(b.getString("userId"));
        Log.d("Bundle - id ",b.getString("userId"));
        Log.d("Bundle - username ",b.getString("username"));
        Log.d("Bundle - password ",b.getString("password"));
        myDb = new DBHelper(this);
        setContentView(R.layout.activity_rooms);
    }

    public void addRoom(View view){
        addRoomButton=(Button)findViewById(R.id.addRoomButton);
        addRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText roomName = (EditText) findViewById(R.id.room_name);

                Log.d("add room inside"," Clicked");
                addRoomDialogFragment =new AddRoomDialogFragment();
                Log.d(getFragmentManager().toString(),"   dsfsdf");
                addRoomDialogFragment.show(getFragmentManager(),"Show addRoom Dialog fragment");
            }
        });
        Log.d("add room outside"," Clicked");

    }

    public void passNewRoom(String roomName, String roomLength, String roomWidth) {
        Log.d("New room name:  ",roomName);
        Log.d("New room length:  ",roomLength);
        Log.d("New room width:  ",roomWidth);
        myDb.addRoom(userId,roomName, roomLength, roomWidth, "[]");
    }
}

