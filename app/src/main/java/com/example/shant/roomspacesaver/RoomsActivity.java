package com.example.shant.roomspacesaver;

import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RoomsActivity extends AppCompatActivity {
    Button addRoomButton;
    AddRoomDialogFragment addRoomDialogFragment;
    ListView roomsListView ;
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
        roomsListView = (ListView)findViewById(R.id.rooms_list_view);
        final ArrayList<String> roomsList = myDb.getRoomsList(b.getString("userId"));
        Log.d("Rooms count",String.valueOf(roomsList.size()));
        String[] rooms=new String[roomsList.size()];
        for(int i = 0; i < roomsList.size(); i++){
            Log.d("Room details",String.valueOf(roomsList.get(i)));
            rooms[i]= String.valueOf(roomsList.get(i));
        }
        //myDb.getRooms(roomsList);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,rooms);
        roomsListView.setAdapter(adapter);
    }

    public void addRoom(View view){
        addRoomButton=(Button)findViewById(R.id.addRoomButton);
//        addRoomButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("add room inside"," Clicked");
//                addRoomDialogFragment =new AddRoomDialogFragment();
//                Log.d(getFragmentManager().toString(),"   dsfsdf");
//                addRoomDialogFragment.show(getFragmentManager(),"Show addRoom Dialog fragment");
//            }
//        });
        Log.d("add room outside"," Clicked");
        addRoomDialogFragment =new AddRoomDialogFragment();
        Log.d(getFragmentManager().toString(),"   dsfsdf");
        addRoomDialogFragment.show(getFragmentManager(),"Show addRoom Dialog fragment");
    }

    public boolean passNewRoom(String roomName, String roomLength, String roomWidth) {
        Log.d("New room name:  ",roomName);
        Log.d("New room length:  ",roomLength);
        Log.d("New room width:  ",roomWidth);
        return myDb.addRoom(userId,roomName, roomLength, roomWidth, "");
    }

    public void addRoomResult(String toastMsg){
        Toast.makeText(RoomsActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
    }
}

