package com.example.donkey.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;

// A Custom view used to define the on screen drawing space
public class PaintView extends View {

    // Set default attributes
    public static int BRUSH_SIZE = 20;
    public static final int DEFAULT_COLOR = Color.argb(255, 100, 167, 11);
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerStroke> strokes = new ArrayList<>();
    private int currentColor = DEFAULT_COLOR;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    // Constructors for the PaintView Objects
    public PaintView(Context context) {
        this(context, null);
    }

    //Default constructor that is called to define the paint path style
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

    }

    // Method to initialize the paintbrush and the drawing area
    public void init(DisplayMetrics metrics) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;

    }

    // Method for color selection
    public void setColor(int color) {
        currentColor = color;
    }

    // Method for clearing the strokes ArrayList and re-render the screen
    public void clear() {

        strokes.clear();
        invalidate();
    }

    /**
     * Override the View onDraw() method for the custom view.
     */


    @Override

    protected void onDraw(Canvas mCanvas) {
        mCanvas.save();
        mCanvas.drawColor(backgroundColor);

        for (FingerStroke fs : strokes) {
            mPaint.setColor(fs.color);
            mPaint.setStrokeWidth(fs.strokeWidth);
            mCanvas.drawPath(fs.path, mPaint);

        }
        if (mBitmap != null) {
            mCanvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        } else {
            Log.d("DEBUG", "background is null");
        }

        mCanvas.restore();

    }

    // Creates the finger path object to draw a line
    private void touchStart(float x, float y) {
        mPath = new Path();
        FingerStroke fp = new FingerStroke(currentColor, strokeWidth, mPath);
        strokes.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

    }

    // Fills the line between touch events and smooths it
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        // smooths the line
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

    }

    // Method for when the touch action has ended i.e. finger is off the screen
    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    // Handles the touch event sequence and updates the view on events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;

        }

        return true;
    }
}
