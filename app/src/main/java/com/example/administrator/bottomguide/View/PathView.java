package com.example.administrator.bottomguide.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.example.administrator.bottomguide.View.CardiographView;

import java.util.ArrayList;

public class PathView extends CardiographView {
    private ArrayList<Integer> dataList;
    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPath = new Path();
        dataList= new ArrayList<Integer>();
    }


    private void drawPath(Canvas canvas) {
        // 重置path
        mPath.reset();

        //用path模拟一个心电图样式
        mPath.moveTo(0,mHeight);
        int tmp = 0;
        for(int i = 0;i<dataList.size();i++) {
            mPath.lineTo(tmp+20, 100);
            mPath.lineTo(tmp+60,mHeight/2);
            mPath.lineTo(tmp+100,mHeight/2+50);
            mPath.lineTo(tmp+120,mHeight/2);
            mPath.lineTo(tmp+180,mHeight/2);
            mPath.lineTo(tmp+200,mHeight/2+100);
            tmp = tmp+200;
        }
        //设置画笔style
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath,mPaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPath(canvas);
        scrollBy(1,0);
        postInvalidateDelayed(10);
    }
    public void setDataList(ArrayList<Integer> data)
    {
        this.dataList=data;
        invalidate();
    }
}
