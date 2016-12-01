package com.example.shant.roomspacesaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

public class EditRoomActivity extends AppCompatActivity {


    Button addFurnitureButton;
    AddFurnitureDialogFragment addFurnitureDialogFragment;
    DBHelper myDb;
    int roomId;
    float Length;
    float Width;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_room);
        layout = (RelativeLayout)findViewById(R.id.activity_edit_room);
        layout.addView(new RectsDrawingView(EditRoomActivity.this));
        Bundle b = getIntent().getExtras();
        //storing room id for further use in room manipulation
        roomId = Integer.parseInt(b.getString("_id"));
        myDb = new DBHelper(this);
        Log.d("Bundle - _id ", b.getString("_id"));
        Log.d("Bundle - room name ", b.getString("room_name"));
        Log.d("Bundle - room length ", b.getString("room_length"));
        Log.d("Bundle - room width", b.getString("room_width"));
        Log.d("Bundle - furniture ids", b.getString("furniture_ids"));
    }

    public void addFurniture(View view) {
        addFurnitureButton = (Button) findViewById(R.id.addFurnitureButton);
//        addRoomButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("add room inside"," Clicked");
//                addRoomDialogFragment =new AddRoomDialogFragment();
//                Log.d(getFragmentManager().toString(),"   dsfsdf");
//                addRoomDialogFragment.show(getFragmentManager(),"Show addRoom Dialog fragment");
//            }
//        });
        Log.d("add room outside", " Clicked");
//        addRoomDialogFragment =new AddRoomDialogFragment();
        addFurnitureDialogFragment = new AddFurnitureDialogFragment();
        Log.d(getFragmentManager().toString(), "   dsfsdf");
        addFurnitureDialogFragment.show(getFragmentManager(), "Show add furniture Dialog fragment");
    }

    public boolean passNewFurniture(String furnitureLength, String furnitureWidth) {
        Log.d("roomid", String.valueOf(roomId));
        Log.d("New furniture length:  ", furnitureLength);
        Log.d("New furniture width:  ", furnitureWidth);
        Length = Float.parseFloat(furnitureLength);
        Width = Float.parseFloat(furnitureWidth);
        RectArea rect;
        rect = new RectArea(50,50,Length,Width);
        RectsDrawingView.obtainTouchedRect(50,50,Length,Width);
        return myDb.addFurniture(roomId, furnitureLength, furnitureWidth, "0", "0");//add furniture at origin
    }


    public void addFurnitureResult(String toastMsg) {
        Toast.makeText(EditRoomActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
    }

    public static class RectsDrawingView extends View {

        private static final String TAG = "RectDrawingView";

        /** Main bitmap */
        private Bitmap mBitmap = null;

        private Rect mMeasuredRect;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int windowwidth = metrics.widthPixels;
        int windowheight = metrics.heightPixels;

        /** Stores data about single circle */


        /** Paint to draw rects */
        public Paint mRectPaint;

        // Radius limit in pixels
        private final static int RECT_LIMIT = 50;

        private static final int RECTS_LIMIT = 6;

        /** All available circles */
        public  static HashSet<RectArea> mRects = new HashSet<RectArea>(RECTS_LIMIT);
        private SparseArray<RectArea> mCirclePointer = new SparseArray<RectArea>(RECTS_LIMIT);

        /**
         * Default constructor
         *
         * @param ct {@link Context}
         */
        public RectsDrawingView(final Context ct) {
            super(ct);

            init(ct);
        }

        public RectsDrawingView(final Context ct, final AttributeSet attrs) {
            super(ct, attrs);

            init(ct);
        }

        public RectsDrawingView(final Context ct, final AttributeSet attrs, final int defStyle) {
            super(ct, attrs, defStyle);

            init(ct);
        }

        private void init(final Context ct) {
            // Generate bitmap used for background
            mBitmap = BitmapFactory.decodeResource(ct.getResources(), R.drawable.up_image);

            mRectPaint = new Paint();

            mRectPaint.setColor(Color.GREEN);
            mRectPaint.setStrokeWidth(40);
            mRectPaint.setStyle(Paint.Style.FILL);
        }


        @Override
        public void onDraw(final Canvas canv) {
            // background bitmap to cover all area
            canv.drawBitmap(mBitmap, null, mMeasuredRect, null);

            for (RectArea rect : mRects) {
                canv.drawRect(rect.X - rect.length/2, rect.Y - rect.width/2, rect.X + rect.length/2, rect.Y + rect.width/2, mRectPaint);
            }
        }

        @Override
        public boolean onTouchEvent(final MotionEvent event) {
            boolean handled = false;

            RectArea touchedRect;
            int xTouch;
            int yTouch;
            int pointerId;
            int actionIndex = event.getActionIndex();

            // get touch event coordinates and make transparent circle from it
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // it's the first pointer, so clear all existing pointers data
                    clearCirclePointer();

                    xTouch = (int) event.getX(0);
                    yTouch = (int) event.getY(0);

                    // check if we've touched inside some circle
                    touchedRect = getTouchedRect(xTouch, yTouch);
                    if(touchedRect == null)
                        break;
                    touchedRect.X = xTouch;
                    touchedRect.Y = yTouch;
                    mCirclePointer.put(event.getPointerId(0), touchedRect);

                    invalidate();
                    handled = true;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.w(TAG, "Pointer down");
                    // It secondary pointers, so obtain their ids and check circles
                    pointerId = event.getPointerId(actionIndex);

                    xTouch = (int) event.getX(actionIndex);
                    yTouch = (int) event.getY(actionIndex);

                    // check if we've touched inside some circle
                    touchedRect = getTouchedRect(xTouch, yTouch);
                    if(touchedRect == null)
                        break;
                    mCirclePointer.put(pointerId, touchedRect);
                    touchedRect.X = xTouch;
                    touchedRect.Y = yTouch;
                    invalidate();
                    handled = true;
                    break;

                case MotionEvent.ACTION_MOVE:
                    final int pointerCount = event.getPointerCount();

                    Log.w(TAG, "Move");

                    for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {
                        // Some pointer has moved, search it by pointer id
                        pointerId = event.getPointerId(actionIndex);

                        xTouch = (int) event.getX(actionIndex);
                        yTouch = (int) event.getY(actionIndex);



                        touchedRect = mCirclePointer.get(pointerId);

                        if (null != touchedRect) {

                            if(xTouch<touchedRect.length/2+5){xTouch=(int)touchedRect.length/2+5;}
                            if(yTouch<touchedRect.width/2+5){yTouch=(int)touchedRect.width/2+5;}
                            if(xTouch>windowwidth - touchedRect.length/2-50){xTouch = windowwidth-(int)touchedRect.length/2-50;}
                            if(yTouch>windowheight - touchedRect.length/2-170){yTouch = windowheight-(int)touchedRect.length/2-170;}
                            if(xTouch*xTouch+yTouch*yTouch <= (100+(int)touchedRect.width/2)*(100+(int)touchedRect.length/2)){
                                xTouch=80+(int)touchedRect.length/2;
                                yTouch= 80+(int)touchedRect.width/2;
                        }
                            touchedRect.X = xTouch;
                            touchedRect.Y = yTouch;

                        }
                    }
                    invalidate();
                    handled = true;
                    break;

                case MotionEvent.ACTION_UP:
                    clearCirclePointer();
                    invalidate();
                    handled = true;
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    // not general pointer was up
                    pointerId = event.getPointerId(actionIndex);

                    mCirclePointer.remove(pointerId);
                    invalidate();
                    handled = true;
                    break;

                case MotionEvent.ACTION_CANCEL:
                    handled = true;
                    break;

                default:
                    // do nothing
                    break;
            }

            return super.onTouchEvent(event) || handled;
        }

        /**
         * Clears all CircleArea - pointer id relations
         */
        public void clearCirclePointer() {
            Log.w(TAG, "clearCirclePointer");

            mCirclePointer.clear();
        }

        /**
         * Search and creates new (if needed) circle based on touch area
         *
         * @param xTouch float x of touch
         * @param yTouch float y of touch
         *
         * @return obtained {@link RectArea}
         */
        public static RectArea obtainTouchedRect(final float xTouch, final float yTouch,float length, float width) {
            RectArea touchedRect = getTouchedRect(xTouch, yTouch);


                touchedRect = new RectArea(xTouch-length/2, yTouch-width/2, xTouch + length/2, yTouch + width/2);


                Log.w(TAG, "Added rect " + touchedRect);
                mRects.add(touchedRect);


            return touchedRect;
        }

        /**
         * Determines touched circle
         *
         * @param xTouch int x touch coordinate
         * @param yTouch int y touch coordinate
         *
         * @return {@link RectArea} touched circle or null if no circle has been touched
         */
        public static RectArea getTouchedRect(final float xTouch, final float yTouch) {
            RectArea touched = null;

            for (RectArea rect : mRects) {
                if ((xTouch >= rect.X-rect.length  && xTouch <= rect.X + rect.length && yTouch  >= rect.Y -rect.width && yTouch <= rect.Y + rect.width)) {
                    Log.i("Touched","");
                    touched = rect;
                    break;
                }
            }
            if(touched==null)
                return null;
            return touched;
        }
        /*avoid overlap the furnture
        public Boolean avoidOverlap(final float xTouch, final float yTouch){
            for (RectArea rect : mRects) {
                if ((xTouch >= rect.X-rect.length  && xTouch <= rect.X + rect.length && yTouch  >= rect.Y -rect.width && yTouch <= rect.Y + rect.width)) {
                    Log.i("Touched","");
                    touched = rect;
                    break;
                }
            }
        }*/

        @Override
        protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            mMeasuredRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
    }



}