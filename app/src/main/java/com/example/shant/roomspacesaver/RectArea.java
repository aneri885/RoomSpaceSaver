package com.example.shant.roomspacesaver;

/**
 * Created by South on 11/30/2016.
 */
public class RectArea {
    int _id;
    float length;
    float width;
    float X;
    float Y;

    RectArea(int _id,float X, float Y, float length, float width) {
        this._id = _id;
        this.X = X;
        this.Y = Y;
        this.length = length;
        this.width = width;
    }

    @Override
    public String toString() {
        return "Rectangle["+ _id + X + ", " + Y + ", "  + length + "," + width + "]";
    }
}