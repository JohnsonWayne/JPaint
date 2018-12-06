package com.example.donkey.paint;

import android.graphics.Path;

// A class that defines the strokes drawn by a user touch.
public class FingerStroke {

    public int color;
    public int strokeWidth;
    public Path path;

    public FingerStroke(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;

    }

}
