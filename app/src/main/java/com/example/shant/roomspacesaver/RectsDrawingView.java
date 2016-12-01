package com.example.shant.roomspacesaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;

import static java.lang.Boolean.TRUE;

/**
 * Created by shant on 01-12-2016.
 */

public class RectsDrawingView extends View {

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
    private SparseArray<RectArea> mPointer = new SparseArray<RectArea>(RECTS_LIMIT);

    public float mTouchx;
    public float mTouchy;




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
                clearPointer();

                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);

                // check if we've touched inside some circle
                touchedRect = getTouchedRect(xTouch, yTouch);
                if(touchedRect == null)
                    break;
                touchedRect.X = xTouch;
                touchedRect.Y = yTouch;
                mTouchx = xTouch;
                mTouchy = yTouch;
                mPointer.put(event.getPointerId(0), touchedRect);

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
                mPointer.put(pointerId, touchedRect);

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



                    touchedRect = mPointer.get(pointerId);

                    if (null != touchedRect) {

                        if(xTouch<touchedRect.length/2+5){xTouch=(int)touchedRect.length/2+5;}
                        if(yTouch<touchedRect.width/2+5){yTouch=(int)touchedRect.width/2+5;}
                        if(xTouch>windowwidth - touchedRect.length/2-50){xTouch = windowwidth-(int)touchedRect.length/2-50;}
                        if(yTouch>windowheight - touchedRect.length/2-170){yTouch = windowheight-(int)touchedRect.length/2-170;}
                        if(xTouch*xTouch+yTouch*yTouch <= (100+(int)touchedRect.width/2)*(100+(int)touchedRect.length/2)){
                            xTouch=80+(int)touchedRect.length/2;
                            yTouch= 80+(int)touchedRect.width/2;
                        }
                        if(!avoidOverlap(touchedRect)){
                            touchedRect.X = xTouch;
                            touchedRect.Y = yTouch;
                        }
                        touchedRect.X = xTouch;
                        touchedRect.Y = yTouch;


                    }
                }
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_UP:
                xTouch = (int) event.getX(actionIndex);
                yTouch = (int) event.getY(actionIndex);
                touchedRect = getTouchedRect(xTouch, yTouch);
                if(!avoidOverlap(touchedRect)){
                    touchedRect.X = mTouchx;
                    touchedRect.Y = mTouchy;
                }

                clearPointer();
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                // not general pointer was up
                pointerId = event.getPointerId(actionIndex);

                mPointer.remove(pointerId);
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
    public void clearPointer() {
        Log.w(TAG, "clearCirclePointer");

        mPointer.clear();
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
    /*avoid overlap the furnture*/
    public Boolean avoidOverlap(RectArea touched){

        point leftTop = new point(touched.X - touched.length/2,touched.Y-touched.width/2);
        point leftBottom= new point(touched.X - touched.length/2,touched.Y+touched.width/2);
        point rightTop= new point(touched.X + touched.length/2,touched.Y-touched.width/2);
        point rightBottom= new point(touched.X + touched.length/2,touched.Y-touched.width/2);

        for (RectArea rect : mRects) {
            if (touched == rect) {continue;
            }
            if(leftTop.X >= rect.X-rect.length/2  && leftTop.X <= rect.X + rect.length/2 && leftTop.Y  >= rect.Y -rect.width/2 && leftTop.Y <= rect.Y + rect.width/2) {
                Log.w(TAG, "Overlap");
                return false;
            }
            if(leftBottom.X >= rect.X-rect.length/2  && leftBottom.X <= rect.X + rect.length/2 && leftBottom.Y  >= rect.Y -rect.width/2 && leftBottom.Y <= rect.Y + rect.width/2) {
                Log.w(TAG, "Overlap");
                return false;
            }
            if(rightTop.X >= rect.X-rect.length/2  && rightTop.X <= rect.X + rect.length/2 && rightTop.Y  >= rect.Y -rect.width/2 && rightTop.Y <= rect.Y + rect.width/2) {
                Log.w(TAG, "Overlap");
                return false;
            }
            if(rightBottom.X >= rect.X-rect.length/2  && rightBottom.X <= rect.X + rect.length/2 && rightBottom.Y  >= rect.Y -rect.width/2 && rightBottom.Y <= rect.Y + rect.width/2) {
                Log.w(TAG, "Overlap");;
                return false;
            }

        }
        return TRUE;
    }
    public class point{
        float X;
        float Y;
        point(float X, float Y) {
            this.X = X;
            this.Y = Y;
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMeasuredRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }
}
