package com.example.shant.roomspacesaver;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by shant on 30-11-2016.
 */

public class AddFurnitureDialogFragment extends DialogFragment {
    private EditText furnitureLength, furnitureWidth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Log.d("on create ","dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addFurnitureView = inflater.inflate((R.layout.dialog_add_furniture),null);
//        roomName = (EditText)addRoomView.findViewById(R.id.room_name);
        furnitureLength = (EditText)addFurnitureView.findViewById(R.id.furniture_length);
        furnitureWidth = (EditText)addFurnitureView.findViewById(R.id.furniture_width);

        builder.setView(addFurnitureView);
        builder.setMessage("Enter Furniture Details")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Log.d(dialog.toString(),"dialog");
                        Log.d(dialog.getClass().toString(),"dialog.getClass");
//                        String newRoomName = roomName.getText().toString();
                        String newFurnitureLength = furnitureLength.getText().toString();
                        String newFurnitureWidth = furnitureWidth.getText().toString();
//                        Log.d("Room name value: ",newRoomName);
                        Log.d("Furniture length : ",newFurnitureLength);
                        Log.d("Furniture width : ",newFurnitureLength);
//                        RoomsActivity roomsActivity = (RoomsActivity) getActivity();
                        EditRoomActivity editRoomActivity = (EditRoomActivity) getActivity();
//                        boolean result = roomsActivity.passNewRoom(newRoomName, newRoomLength, newRoomWidth);
                        boolean result = editRoomActivity.passNewFurniture(newFurnitureLength, newFurnitureWidth);
                        if (result){
                            editRoomActivity.addFurnitureResult("Furniture created and updated");
//                            Toast.makeText(AddRoomDialogFragment.this, "User created, Please login", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            editRoomActivity.addFurnitureResult("Furniture creation failed");
//                            Toast.makeText(RoomsActivity.this, "User creation failed, retry again", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        RoomsActivity roomsActivity = (RoomsActivity) getActivity();
                        roomsActivity.addRoomResult("Room creation cancelled");

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
