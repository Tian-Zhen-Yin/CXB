package com.example.administrator.bottomguide.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

public class CardiographView extends View {
    //画笔
    protected Paint mPaint;
    //折线的颜色
    protected int mLineColor=Color.parseColor("#00BFFF");
    //网格颜色
    protected int mGridColor=Color.parseColor("#FFDAC181");

    //小网格颜色
    protected int msGridColor=Color.parseColor("#FFDAC181");
    //背景颜色
    protected int mBackgroundColor= Color.WHITE;
    //自身的大小
    protected int mWidth,mHeight;

    //网格宽度
    protected int mGridWidth=50;
    //小网格宽度
    protected int msGridWidth=10;
    //心电图的折线
    protected Path mPath;



    //在构造器中初始化画笔和Path
    public CardiographView(Context context) {
        this(context,null);
    }
    public CardiographView(Context context, AttributeSet attrs)
        {
        this(context,attrs,0);

    }
    public CardiographView(Context context, AttributeSet attrs,int defStyleAttr)
    {   super(context,attrs,defStyleAttr);
        mPaint=new Paint();
        mPath=new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        initBackground(canvas);
    }

    //测得自身宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth=w;
        mHeight=h;

        super.onSizeChanged(w,h,oldh,oldw);
    }
   //初始化bg
    public void initBackground(Canvas canvas)
    {
        canvas.drawColor(mBackgroundColor);

        //竖线个数
        int vSNum=mWidth/msGridWidth;

        //横线gesgh
        int vhSNum=mHeight/msGridWidth;

        mPaint.setColor(msGridColor);
        mPaint.setStrokeWidth(2);

        //画竖线
        for(int i=0;i<vSNum;i++)
        {
            canvas.drawLine(i*msGridWidth,0,i*msGridWidth,mHeight,mPaint);
        }
        //画横线
        for(int i=0;i<vhSNum;i++){
            canvas.drawLine(0,i*msGridWidth,mWidth,i*msGridWidth,mPaint);
        }
    }
}
