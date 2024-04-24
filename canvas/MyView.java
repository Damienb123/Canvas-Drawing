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

    public MyView(Context context) {
        super(context);
        initBackground();
        initTitle();
        initRectangle();
        initRectangleText();
        initCircle();
        initLines();
    }

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
        rect =
                new Rect((int) (rectangleCenterX - rectangleWidth / 2),
                        (int) (rectangleCenterY - rectangleHeight / 2),
                        (int) (rectangleCenterX + rectangleWidth / 2),
                        (int) (rectangleCenterY + rectangleHeight / 2));
        rectPaint = new Paint();
        rectPaint.setStrokeWidth(1);
        rectPaint.setStyle(Paint.Style.FILL);
        rectColors =
                new int[]{Color.GRAY, Color.GREEN, Color.CYAN, Color.YELLOW,
                        Color.MAGENTA, Color.BLUE, Color.BLACK, Color.RED};
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

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(titleText, titleTextBeginX, titleTextBeginY, titlePaint);
        int rectColor = rectColors[rectColorIndex];
        rectPaint.setColor(rectColor);
        canvas.drawRect(rect, rectPaint);
        int textColor = Color.BLACK;
        if (rectColor == Color.BLACK || rectColor == Color.BLUE) {
            textColor = Color.WHITE;
        }
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

    @Override
    public boolean performClick() {
        super.performClick();
        // Perform the action associated with a click event
        return true; // Return true to indicate that the click was handled
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