package com.example.administrator.bottomguide.View;
import java.util.logging.Logger;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.util.TypedValue;

import com.example.administrator.bottomguide.GuideActivity;
import com.example.administrator.bottomguide.MainActivity;
import com.example.administrator.bottomguide.R;
import com.example.administrator.bottomguide.RankActivity;

public class HealthyView extends View {
    private static String TAG="HealthyView";

    private int mWidth;//自定义View宽
    private  int mHeight;//自定义ViewG高
    private int mBackgroundCorner;//背景四角的弧度

    private int mArcCenterX;
    private int mArcCenterY;
    private RectF mArcRect;

    private Paint mBackgroundPaint;
    private Paint mArcPaint;//最上面弧线的画笔
    private Paint mTextPaint;
    private Paint mDashLinePaint;//虚线的画笔
    private Paint mBarPaint;//竖条的画笔

    private int[] mSteps;
    private float mRatio;

    private Context mContext;

    private int mDefaultThemeColor;//主题色
    private int mDefaultUpBackgroundColor;//上层默认的背景色

    private int mThemeColor;
    private int mUpBackgroundColor;
    private float mArcWidth;
    private float mBarWidth;
    private int mMaxStep;
    private int mAverageStep;
    private int mTotalSteps;

    private int step = 25;
    private float percent = 0.5f;

    private Paint mAvatarPaint;
    private Rect mSrcRect, mDestRect;



    public HealthyView(Context context) {
        this(context,null);
    }
    public HealthyView(Context context, AttributeSet atrrs)
    {
        this(context,atrrs,0);
    }
    public HealthyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        //下面这句是关闭硬件加速，防止某些4.0的设备虚线显示为实线的问题
        //可以在AndroidManifest.xml时的Application标签加上android:hardwareAccelerated=”false”,
        // 这样整件应用都关闭了硬件加速，虚线可以正常显示，但是，关闭硬件加速对性能有些影响，
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        //自定义View的狂傲比例
        mRatio=450.f/450.f;
        //初始化一些默认参数

        mBackgroundCorner = DensityUtils.dp2px(mContext, 5);
        mDefaultThemeColor = Color.parseColor("#2EC3FD");
        mDefaultUpBackgroundColor = Color.WHITE;
        mThemeColor = mDefaultThemeColor;
        mUpBackgroundColor = mDefaultUpBackgroundColor;
        mSteps = new int[]{2, 3, 4, 2, 1, 2, 0};
        calculateSteps();
        //背景画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mThemeColor);
        //圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setColor(mThemeColor);//画笔颜色
        mArcPaint.setAntiAlias(true);//抗锯齿
        mArcPaint.setStyle(Paint.Style.STROKE);//空心
        mArcPaint.setDither(true);//防抖动
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);//在画笔的连接处是圆滑的
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);//在画笔的起始处是圆滑的
        mArcPaint.setPathEffect(new CornerPathEffect(10));//画笔效果
        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);


        //加入动画
        AnimatorSet animatorSet = new AnimatorSet();

        //时间的动画
        ValueAnimator stepAnimator = ValueAnimator.ofInt(0, mSteps[mSteps.length - 1]);
        stepAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                step = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
//          stepAnimator.setDuration(1000);
//          stepAnimator.start();

        //圆环动画
        ValueAnimator percentAnimator = ValueAnimator.ofFloat(0, 1);
        percentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        // percentAnimator.setDuration(1000);
        // percentAnimator.start();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(stepAnimator, percentAnimator);
        animatorSet.start();


    }

    public void setThemeColor(int color) {
        mThemeColor = color;
        mBackgroundPaint.setColor(mThemeColor);
        mArcPaint.setColor(mThemeColor);
        mBarPaint.setColor(mThemeColor);
        invalidate();
    }

    public void setSteps(int[] steps) {
        if (steps == null || steps.length == 0) throw new IllegalArgumentException("非法参数");
        mSteps = steps;
        calculateSteps();
        invalidate();

    }

    //将原始图片转化为圆形图片
   /* public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r;
        if (width > height) {
            r = height;
        } else {
            r = width;
        }
        Bitmap backgroundBmp = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRoundRect(rect, r / suger_cart, r / suger_cart, paint);
        return backgroundBmp;
    }*/

    //计算时间,最大时间和总时间
    private void calculateSteps() {
        mTotalSteps = 0;
        mMaxStep = 0;
        mAverageStep = 0;
        for (int i = 0; i < mSteps.length; i++) {
            mTotalSteps += mSteps[i];
            if (mMaxStep < mSteps[i]) mMaxStep = mSteps[i];

        }
        mAverageStep = (int) (mTotalSteps * 1.f / mSteps.length);
    }

    //绘制最下层背景
    private void drawBelowBackground( Canvas canvas) {
        /*Path path = new Path();

        path.moveTo(left, top);

        path.lineTo(right - radius, top);
        path.quadTo(right, top, right, top + radius);

        path.lineTo(right, bottom - radius);
        path.quadTo(right, bottom, right - radius, bottom);

        path.lineTo(left + radius, bottom);
        path.quadTo(left, bottom, left, bottom - radius);

        path.lineTo(left, top + radius);
        path.quadTo(left, top, left + radius, top);*/
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.index_top);

        canvas.drawBitmap(bitmap,mSrcRect,mDestRect,mAvatarPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultWidth = Integer.MAX_VALUE;
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //  int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //  int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        } else {
            width = defaultWidth;
        }
        int defaultHeight = (int) (width * 1.f / mRatio);
        height = defaultHeight;
        setMeasuredDimension(width, height);
        Log.i(TAG, "width:" + width + "| height:" + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mArcCenterX = (int) (mWidth / 2.f);
        mArcCenterY = (int) (180.f / 450.f * mHeight);
        mArcRect = new RectF();
        mArcRect.left = mArcCenterX - 125.f / 450.f * mWidth;
        mArcRect.top = mArcCenterY - 125.f / 450.f * mHeight;
        mArcRect.right = mArcCenterX + 125.f / 450.f * mWidth;
        mArcRect.bottom = mArcCenterY + 125.f / 450.f * mHeight;

        mArcWidth = 20.f / 450.f * mWidth;


        //画笔的宽度一定要在这里设置才能自适应
        mArcPaint.setStrokeWidth(mArcWidth);


    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        float startX;
        float startY;
        float stopX;
        float stopY;
        float xPos;
        float yPos;
        //1.绘制最下层背景
       /* mBackgroundPaint.setColor(mThemeColor);*/
      /*  drawBelowBackground(canvas);*/
        //suger_cart.绘制上面的背景
       /* mBackgroundPaint.setColor(mUpBackgroundColor);*/
      /*  drawUpBackground(0, 0, mWidth, mWidth, mBackgroundCorner, canvas, mBackgroundPaint);*/
        //bp_cart.绘制圆弧
      /*  canvas.drawArc(mArcRect, 120, 300 * percent, false, mArcPaint);*/
        //heart.绘制圆弧里面的文字
        xPos = mArcCenterX;
        yPos = (int) (mArcCenterY - 15.f / 450.f * mHeight);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(20.f / 450.f * mWidth);
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawText("截至12:50分已驾驶", xPos, yPos, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(50.f / 450.f * mWidth);
        mTextPaint.setColor(mDefaultUpBackgroundColor);
        canvas.drawText(step + " ", mArcCenterX, mArcCenterY+30.f/450.f*mWidth, mTextPaint);
        yPos=(int)(mArcCenterY+60.f/450.f * mHeight);
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setTextSize(20.f / 450.f * mWidth);
        canvas.drawText("小时",mArcCenterX, yPos, mTextPaint);
        yPos = (int) (mArcCenterY + 150.f / 525.f * mHeight);
        mTextPaint.setColor(Color.parseColor("#C1C1C1"));
        mTextPaint.setTextSize(20.f / 450.f * mWidth);
        canvas.drawText("平均每天驾驶", mArcCenterX, yPos, mTextPaint);
        xPos = (int) (mArcCenterX + 15.f / 450.f * mWidth);
        yPos = (int) (mArcCenterY + 150.f / 450.f * mHeight);
      /*  canvas.drawText("第", xPos, yPos, mTextPaint);*/
       /* xPos = (int) (mArcCenterX + 35.f / 450.f * mWidth);*/
        canvas.drawText(" h", xPos, yPos, mTextPaint);
        mTextPaint.setColor(mThemeColor);
        mTextPaint.setTextSize(24.f / 450.f * mWidth);
        canvas.drawText("10", mArcCenterX, yPos, mTextPaint);
       /* //5.绘制圆弧下面的文字
        xPos = (int) (25.f / 450.f * mWidth);
        yPos = (int) (330.f / 525.f * mHeight);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(Color.parseColor("#C1C1C1"));
        mTextPaint.setTextSize(12.f / 450.f * mWidth);
        canvas.drawText("最近7天", xPos, yPos, mTextPaint);
        xPos = (int) ((450.f - 25.f) / 450.f * mWidth);
        yPos = (int) (330.f / 525.f * mHeight);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setColor(Color.parseColor("#C1C1C1"));
        mTextPaint.setTextSize(12.f / 450.f * mWidth);
        canvas.drawText("平均驾驶" + mAverageStep + "小时/天", xPos, yPos, mTextPaint);
        //6.画虚线
        xPos = (int) (25.f / 450.f * mWidth);
        yPos = (int) (352.f / 525.f * mHeight);
        stopX = xPos + (450.f - 50.f) / 450.f * mWidth;
        stopY = yPos;
        canvas.drawLine(xPos, yPos, stopX, stopY, mDashLinePaint);

        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(10.f / 450.f * mWidth);
        startY = 388.f / 525.f * mHeight;

        for (int i = 0; i < mSteps.length; i++) {
            float barHeight = mSteps[i] * 1.f / mAverageStep * 35.f / 525.f * mHeight;
            startX = 55.f / 450.f * mWidth + i * (57.f / 450.f * mWidth);
            stopX = startX;
            stopY = startY - barHeight;
            if (mSteps[i] < mAverageStep) mBarPaint.setColor(Color.parseColor("#C1C1C1"));
            else mBarPaint.setColor(mThemeColor);
            canvas.drawLine(startX, startY, stopX, stopY, mBarPaint);
            canvas.drawText("0" + (i + 1) + "日", startX, startY + 25.f / 525.f * mHeight, mTextPaint);
        }
        //8.绘制蓝色层的文字以及头像
        yPos = (mHeight - mWidth) / suger_cart.f + mWidth + 20.f / 450.f * mWidth / suger_cart;
        xPos = 80.f / 450.f * mWidth;
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(20.f / 450.f * mWidth);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("Master T获得今日老司机称号", xPos, yPos, mTextPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.touxiang);
        Rect dst = new Rect();//头像绘制到的矩形
        int rectWidth = (int) (30.f / 525.f * mHeight);//矩形的宽度
        dst.top = (int) ((mHeight - mWidth) / suger_cart.f + mWidth - rectWidth / suger_cart.f);
        dst.left = (int) (xPos - 40.f / 450 * mWidth);
        dst.bottom = (int) ((mHeight - mWidth) / suger_cart.f + mWidth + rectWidth / suger_cart.f);
        dst.right = (int) (xPos - 10.f / 450 * mWidth);
        bitmap = toRoundBitmap(bitmap);
        canvas.drawBitmap(bitmap, null, dst, mAvatarPaint);//绘制头像

        xPos = 425.f / 450.f * mWidth;
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(15.f / 450.f * mWidth);
        canvas.drawText("查看 >", xPos, yPos, mTextPaint);*/


    }

    /*@SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        RectF rectF = new RectF();
        rectF.top = mWidth;
        rectF.left = 380.f / 450.f * mWidth;
        rectF.right = mWidth;
        rectF.bottom = mHeight;
        if (rectF.contains(event.getX(), event.getY())) {//当前点击的坐标在右下角的范围内
            //在这里可以做点击事件的监听
            mContext.startActivity(new Intent(mContext,RankActivity.class));
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }*/






}
