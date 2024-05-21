# Canvas-Drawing

## Table of Contents

1. [Overview](#Overview)
2. [Project Structure](#Project-Structure)
3. [Purpose and Functionality](#Purpose-and-Functionality)
    - MainActivity
    - MyView
    - Point
4. [Layout Files](#Layout-Files)
    - activity_main.xml
5. [Usage Instructions](#Usage-Instructions)
6. [Expected Output](#Expected-Output)
7. [Image Visuals](#Image-Visuals)
8. [Video Walkthrough](#Video-Walkthrough)

## Overview

This project is an Android application that demonstrates custom drawing on a **Canvas** using a **View**. It allows users to interact with the UI by clicking to move a circle and change the color of a rectangle.

## Project Structure

- **com.canvas.MainActivity:** The main activity that initializes and sets the custom view.
- **com.canvas.MyView:** Custom view that handles drawing on the canvas and user interactions.
- **com.canvas.Point:** A simple class to store the coordinates of points for drawing lines.

## Purpose and Functionality

### MainActivity
```
package com.canvas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        MyView myView = new MyView(this);
        setContentView(myView);
    }
}
```
The **MainActivity** class extends **AppCompatActivity** and overrides the **onCreate** method to initialize and set the custom **MyView** as the content view.

### MyView
```
package com.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.LinkedList;

public class MyView extends View {

    // Variable declarations
    String titleText;
    float titleTextBeginX;
    float titleTextBeginY;
    Paint titlePaint;

    float rectangleCenterX;
    float rectangleCenterY;
    Rect rect;
    int[] rectColors;
    int rectColorIndex = 0;
    Paint rectPaint;

    String rectText;
    float rectTextX;
    float rectTextY;
    Paint rectTextPaint;

    float circleCenterX;
    float circleCenterY;
    float circleRadius;
    Paint circlePaint;

    LinkedList<Point> pointList;
    Paint linePaint;

    // Constructor
    public MyView(Context context) {
        super(context);
        initBackground();
        initTitle();
        initRectangle();
        initRectangleText();
        initCircle();
        initLines();
    }

    // Initializers
    void initBackground() {
        setBackgroundColor(Color.LTGRAY);
    }

    void initTitle() {
        titleText = "Click To Move Circle";
        titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(100);
        titleTextBeginX = 100;
        titleTextBeginY = 100;
    }

    void initRectangle() {
        rectangleCenterX = 500;
        rectangleCenterY = 400;
        float rectangleWidth = 200;
        float rectangleHeight = 200;
        rect = new Rect(
            (int) (rectangleCenterX - rectangleWidth / 2),
            (int) (rectangleCenterY - rectangleHeight / 2),
            (int) (rectangleCenterX + rectangleWidth / 2),
            (int) (rectangleCenterY + rectangleHeight / 2)
        );
        rectPaint = new Paint();
        rectPaint.setStrokeWidth(1);
        rectPaint.setStyle(Paint.Style.FILL);
        rectColors = new int[]{
            Color.GRAY, Color.GREEN, Color.CYAN, Color.YELLOW,
            Color.MAGENTA, Color.BLUE, Color.BLACK, Color.RED
        };
    }

    void initRectangleText() {
        rectText = "Click Here";
        rectTextPaint = new Paint();
        rectTextPaint.setTextSize(40);
        rectTextX = rectangleCenterX - 90;
        rectTextY = rectangleCenterY;
    }

    void initCircle() {
        circleCenterX = 200;
        circleCenterY = 800;
        circleRadius = 25;
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    void initLines() {
        pointList = new LinkedList<>();
        pointList.add(new Point(circleCenterX, circleCenterY));
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5);
    }

    // Drawing on Canvas
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(titleText, titleTextBeginX, titleTextBeginY, titlePaint);

        int rectColor = rectColors[rectColorIndex];
        rectPaint.setColor(rectColor);
        canvas.drawRect(rect, rectPaint);

        int textColor = (rectColor == Color.BLACK || rectColor == Color.BLUE) ? Color.WHITE : Color.BLACK;
        rectTextPaint.setColor(textColor);
        canvas.drawText(rectText, rectTextX, rectTextY, rectTextPaint);

        canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, circlePaint);

        Iterator<Point> iter = pointList.iterator();
        Point pointA = iter.next();
        while (iter.hasNext()) {
            Point pointB = iter.next();
            canvas.drawLine(pointA.getX(), pointA.getY(), pointB.getX(), pointB.getY(), linePaint);
            pointA = pointB;
        }
    }

    // Handling Touch Events
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        super.performClick();
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return true;
        }

        float x = event.getX();
        float y = event.getY();

        if (rect.contains((int) x, (int) y)) {
            rectColorIndex = (rectColorIndex + 1) % rectColors.length;
        } else {
            circleCenterX = x;
            circleCenterY = y;
            pointList.add(new Point(x, y));
        }

        invalidate();
        return true;
    }
}
```
The **MyView** class extends **View** and overrides the **onDraw** and **onTouchEvent** methods to handle drawing and touch interactions.

### Point
```
package com.canvas;

public class Point {
    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
```
The **Point** class is used to store the coordinates of points for drawing lines between them.

## Layout Files

### activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.canvas.MyView
        android:id="@+id/myView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</RelativeLayout>
```
This layout file defines a **RelativeLayout** with a custom **MyView** that takes up the entire screen.

## Usage Instructions

1. Create a new Android project in Android Studio.
2. Replace the contents of the generated **MainActivity.java** with the provided **MainActivity** code.
3. Add the **MyView** and Point classes to the **com.canvas** package.
4. Replace the contents of the **activity_main.xml** and **styles.xml** files with the provided code.
5. Run the application on an emulator or a physical device.

## Expected Output

When the application runs, it displays a custom view with the following elements:

- A title text saying "Click To Move Circle".
- A rectangle at the center of the screen that changes color when clicked.
- A circle that moves to the location where the user clicks, leaving a trail of lines.

## Image Visuals

<img src="Canvas Screen.png" width=200>
    
## Video Walkthrough

<img src='walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
