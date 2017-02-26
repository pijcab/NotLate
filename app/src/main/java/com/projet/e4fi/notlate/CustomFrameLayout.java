package com.projet.e4fi.notlate;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;


public class CustomFrameLayout extends FrameLayout {
    private Point size;

    public CustomFrameLayout(Context context) {
        super(context);
        size = new Point();
    }

    public float getXFraction() {
        getDisplay().getRealSize(size);
        if (size.x == 0)
            return 0;
        else
            return getX() / (float) size.x;
    }

    public void setXFraction(float xFraction) {
        getDisplay().getRealSize(size);
        if (size.x > 0)
            setX((xFraction * size.x));
        else
            setX(0);
    }

    public float getYFraction() {
        getDisplay().getRealSize(size);
        if (size.y == 0)
            return 0;
        else
            return getY() / (float) size.y;
    }

    public void setYFraction(float xFraction) {
        getDisplay().getRealSize(size);
        if (size.y > 0)
            setY((xFraction * size.y));
        else
            setY(0);
    }
}
