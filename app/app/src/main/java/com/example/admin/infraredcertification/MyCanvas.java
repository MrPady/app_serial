package com.example.admin.infraredcertification;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import static android.R.attr.x;
import static android.R.attr.y;

public class MyCanvas extends Canvas{
    private float mX;
    private float mY;
    private Paint mPaint,mSmallPaint;
    private float mSize;
    private float mSmallSize;
    private int mStrength;

    public MyCanvas(Bitmap bitmap) {
        super(bitmap);
        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0xFFFFD700);
        mPaint.setTextSize(35);
        mSmallPaint=new Paint();
        mSmallPaint.setStrokeWidth(2);
        mSmallPaint.setColor(0xFF8B4513);
        mSmallPaint.setStyle(Paint.Style.STROKE);
        mSmallPaint.setAntiAlias(true);
        mX=400;
        mY=350;
        mSize=100;
        mSmallSize=30;
        mPaint.setTextSize(30);
    }

    public MyCanvas setXY(float x, float y){
        mX=x;
        mY=y;
        return this;
    }

    public MyCanvas setColor(int color){
        mPaint.setColor(color);
        mSmallPaint.setColor(color);
        return this;
    }

    public MyCanvas setSize(float size){
        mSize=size;
        mSmallSize=mSize/5;
        return this;
    }

    public MyCanvas setwidth(float width){
        mPaint.setStrokeWidth(width);
        mSmallPaint.setStrokeWidth(width/2);
        return this;
    }

    public void draw(int strength){
        if(strength>(8*256))
            strength=(8*256);
//        drawText(String.valueOf((int)(strength/(8.0*256.0)*100))+"%",900,100,mPaint);
//        drawText(String.valueOf(strength),1000,100,mPaint);
        mStrength=strength;
        drawCircle(mX,mY,10,mSmallPaint);
        drawLine(mX+10,mY,mX+14,mY,mSmallPaint);
        drawLine(mX-10,mY,mX-14,mY,mSmallPaint);
        drawLine(mX,mY+10,mX,mY+14,mSmallPaint);
        drawLine(mX,mY-10,mX,mY-14,mSmallPaint);
        drawMyArc(this,mSmallPaint, (int) mSmallSize,mX,mY);
        if(mStrength>128*2){
            drawMyArc(this,mSmallPaint,2*(int) mSmallSize,mX,mY);
        }
        if(mStrength>256*2){
            drawMyArc(this,mSmallPaint, 3*(int) mSmallSize,mX,mY);
        }
        if(mStrength>256*2*2){
            drawMyArc(this,mSmallPaint, 4*(int) mSmallSize,mX,mY);
        }
        if(mStrength>256*3*2){
            drawMyArc(this,mSmallPaint, 5*(int) mSmallSize,mX,mY);
        }

    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    private void drawMyArc(Canvas canvas,Paint paint,int length,float x,float y){
        RectF rectF=new RectF(x-length,y-length,x+length,y+length);
        canvas.drawArc(rectF,10,70,false,paint);
        canvas.drawArc(rectF,100,70,false,paint);
        canvas.drawArc(rectF,190,70,false,paint);
        canvas.drawArc(rectF,280,70,false,paint);
    }



}
