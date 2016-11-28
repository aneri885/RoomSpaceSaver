package com.example.shant.roomspacesaver;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by shant on 09-11-2016.
 */
//https://guides.codepath.com/android/Using-DialogFragment
//http://android-coding.blogspot.com/2012/07/dialogfragment-with-interface-to-pass.html

public class AddRoomDialogFragment extends DialogFragment {
    private EditText roomName, roomLength, roomWidth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Log.d("on create ","dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addRoomView = inflater.inflate((R.layout.dialog_add_room),null);
        roomName = (EditText)addRoomView.findViewById(R.id.room_name);
        roomLength = (EditText)addRoomView.findViewById(R.id.room_length);
        roomWidth = (EditText)addRoomView.findViewById(R.id.room_width);

        builder.setView(addRoomView);
        builder.setMessage("Enter Room Details")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Log.d(dialog.toString(),"dialog");
                        Log.d(dialog.getClass().toString(),"dialog.getClass");
                        String newRoomName = roomName.getText().toString();
                        String newRoomLength = roomLength.getText().toString();
                        String newRoomWidth = roomWidth.getText().toString();
                        Log.d("Room name value: ",newRoomName);
                        Log.d("Room length value: ",newRoomLength);
                        Log.d("Room width value: ",newRoomWidth);
                        RoomsActivity roomsActivity = (RoomsActivity) getActivity();
                        boolean result = roomsActivity.passNewRoom(newRoomName, newRoomLength, newRoomWidth);
                        if (result){
                            roomsActivity.addRoomResult("Room created and updated");
//                            Toast.makeText(AddRoomDialogFragment.this, "User created, Please login", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            roomsActivity.addRoomResult("Room creation failed");
//                            Toast.makeText(RoomsActivity.this, "User creation failed, retry again", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
