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

/**
 * Created by shant on 09-11-2016.
 */
//https://guides.codepath.com/android/Using-DialogFragment

public class AddRoomDialogFragment extends DialogFragment {
    private EditText roomName, roomLength, roomWidth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Log.d("on create ","dialog");
        roomName = new EditText(getActivity());
        roomLength = new EditText(getActivity());
        roomWidth = new EditText(getActivity());
        roomName.setInputType(InputType.TYPE_CLASS_TEXT);
        roomLength.setInputType(InputType.TYPE_CLASS_NUMBER);
        roomWidth.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_add_room, null));
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
                        roomsActivity.passNewRoom(newRoomName, newRoomLength, newRoomWidth);

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
//    @Override
//    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,Bundle bundle){
//        //Log.d("ViewGroup",viewGroup.toString());
//        View v = layoutInflater.inflate(R.layout.dialog_add_room,viewGroup,false);
//        View tv = v.findViewById(R.id.room_name);
//        Log.d("Room name:",tv.toString());
//
//        return v;
//    }
}
