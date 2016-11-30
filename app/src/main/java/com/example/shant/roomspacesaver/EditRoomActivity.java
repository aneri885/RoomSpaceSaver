package com.example.shant.roomspacesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditRoomActivity extends AppCompatActivity {
    Button addFurnitureButton;
    AddFurnitureDialogFragment addFurnitureDialogFragment;
    DBHelper myDb;
    int roomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        Bundle b = getIntent().getExtras();
        //storing room id for further use in room manipulation
        roomId = Integer.parseInt(b.getString("_id"));
        myDb = new DBHelper(this);
        Log.d("Bundle - _id ",b.getString("_id"));
        Log.d("Bundle - room name ",b.getString("room_name"));
        Log.d("Bundle - room length ",b.getString("room_length"));
        Log.d("Bundle - room width",b.getString("room_width"));
        Log.d("Bundle - furniture ids",b.getString("furniture_ids"));
    }
    public void addFurniture(View view){
        addFurnitureButton=(Button)findViewById(R.id.addFurnitureButton);
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
//        addRoomDialogFragment =new AddRoomDialogFragment();
        addFurnitureDialogFragment = new AddFurnitureDialogFragment();
        Log.d(getFragmentManager().toString(),"   dsfsdf");
        addFurnitureDialogFragment.show(getFragmentManager(),"Show add furniture Dialog fragment");
    }
    public boolean passNewFurniture(String furnitureLength, String furnitureWidth) {
        Log.d("roomid",String.valueOf(roomId));
        Log.d("New furniture length:  ",furnitureLength);
        Log.d("New furniture width:  ",furnitureWidth);
        return myDb.addFurniture(roomId,furnitureLength,furnitureWidth, "0","0");//add furniture at origin
    }
    public void addFurnitureResult(String toastMsg){
        Toast.makeText(EditRoomActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
    }

}
