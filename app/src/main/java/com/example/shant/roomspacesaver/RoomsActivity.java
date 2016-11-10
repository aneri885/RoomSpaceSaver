package com.example.shant.roomspacesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RoomsActivity extends AppCompatActivity {
    Button addRoomButton;
    AddRoomDialogFragment addRoomDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
    }

    public void addRoom(View view){
        addRoomButton=(Button)findViewById(R.id.addRoomButton);
        addRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("add room inside"," Clicked");
                addRoomDialogFragment =new AddRoomDialogFragment();
                addRoomDialogFragment.show(getFragmentManager(),"addas");
            }
        });
        Log.d("add room outside"," Clicked");

    }
}

