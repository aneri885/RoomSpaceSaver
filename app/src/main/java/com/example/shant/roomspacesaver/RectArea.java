package com.example.shant.roomspacesaver;

/**
 * Created by South on 11/30/2016.
 */
public class RectArea {
    float length;
    float width;
    float X;
    float Y;

    RectArea(float X, float Y, float length, float width) {
        this.X = X;
        this.Y = Y;
        this.length = length;
        this.width = width;
    }

    @Override
    public String toString() {
        return "Rectangle[" + X + ", " + Y + ", "  + length + "," + width + "]";
    }
}