package com.example.shant.roomspacesaver;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by shant on 29-11-2016.
 */

public class RoomsListCursorAdapter extends CursorAdapter {

    public RoomsListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.room_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView roomName = (TextView)view.findViewById(R.id.room_name);
        String room_name = cursor.getString(1);
        roomName.setText(room_name);
    }
}
