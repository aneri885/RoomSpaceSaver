package com.example.shant.roomspacesaver;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        Log.d("on create:",b.getString("userId"));
        userId = Integer.parseInt(b.getString("userId"));
        Log.d("Bundle - id ",b.getString("userId"));
        Log.d("Bundle - username ",b.getString("username"));
        Log.d("Bundle - password ",b.getString("password"));
        Log.d("Bundle - rooms ",b.getString("rooms"));
        String[] test = b.getString("rooms").split(",");
//        Log.d("Test",String.valueOf(test.length));

        myDb = new DBHelper(this);
        setContentView(R.layout.activity_rooms);
        roomsListView = (ListView)findViewById(R.id.rooms_list_view);
//        final ArrayList<String> roomsList = myDb.getRoomsList(b.getString("userId"));
        //.replace("[","").replace("]","")
        final ArrayList<String> roomsList = new ArrayList<>(Arrays.asList(b.getString("rooms").replaceAll("\\s+","").split(",")));
//        String[] roomsList=b.getString("rooms").replaceAll("\\s+","").split(",");
//        Array<String> roomsList = new Array()
        Log.d("Rooms count",String.valueOf(roomsList));
        Log.d("Rooms",String.valueOf(roomsList));
//        for(int i = 0; i < roomsList.size(); i++){
//            Log.d("Room details",String.valueOf(roomsList.get(i)));
//        }
////        ArrayList<Room> roomsData = myDb.getRooms(roomsList);
        Cursor roomsData = myDb.getRooms(roomsList);
        while (roomsData.moveToNext()){
            Log.d(roomsData.getColumnName(0),roomsData.getString(0));
            Log.d(roomsData.getColumnName(1),roomsData.getString(1));
            Log.d(roomsData.getColumnName(2),roomsData.getString(2));
        }
        RoomsListCursorAdapter roomsListCursorAdapter= new RoomsListCursorAdapter(this,roomsData,0);
        //roomsListCursorAdapter.notifyDataSetChanged();

//        myDb.getRooms(roomsList);
//        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,rooms);
//        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,roomsData);

//        CursorAdapter cursorAdapter=new CursorAdapter(this,roomsData) {
//            @Override
//            public View newView(Context context, Cursor cursor, ViewGroup parent) {
//                return null;
//            }
//
//            @Override
//            public void bindView(View view, Context context, Cursor cursor) {
//
//            }
//        }
        roomsListView.setAdapter(roomsListCursorAdapter);// works with arrayList as well as array

        roomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("Room clicked",view.toString());
//                Log.d("Room clicked",String.valueOf(position));
                Log.d("Room _id clicked",String.valueOf(id));// id is _id so room details can be fetched form this.
                Cursor roomDetails = myDb.getRoomDetails(id);
                Intent intent = new Intent(RoomsActivity.this, EditRoomActivity.class);
                Bundle b = new Bundle();
                while (roomDetails.moveToNext()){
                    b.putString("_id",roomDetails.getString(0));
                    b.putString("room_name",roomDetails.getString(1));
                    b.putString("room_length",roomDetails.getString(2));
                    b.putString("room_width",roomDetails.getString(3));
                    b.putString("furniture_ids",roomDetails.getString(4));
                }
                intent.putExtras(b);
                startActivity(intent);
            }
        });

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
        Log.d("User id:  ",String.valueOf(userId));

        return myDb.addRoom(userId,roomName, roomLength, roomWidth, "");
    }


    public void addRoomResult(String toastMsg){
        Toast.makeText(RoomsActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
    }

}

