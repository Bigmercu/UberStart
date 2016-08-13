package com.example.bigmercu.uberstart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by bigmercu on 2016/8/12.
 */

public class uberStart extends View {

    private static final String TAG = uberStart.class.getSimpleName();

    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private int screenWidth;
    private int screenHeight;
    //渐变
    private ValueAnimator valueAnimator;
    //半径
    private ValueAnimator valueAnimator1;
    //角度
    private ValueAnimator valueAnimator2;
    //角度
    private ValueAnimator valueAnimator3;
    //宽度
    private ValueAnimator valueAnimator4;
    //中间正方形
    private ValueAnimator valueAnimator5;
    //小正方形消失
    private ValueAnimator valueAnimator6;
    private float radius;
    private float radius1;
    private float radius1Start;
    private float bianchang;
    private float centerLineWidth;
    private float centerRect = 44;


    public static final byte STATE_A = 1<<1;
    public static final byte STATE_B = 1<<2;
    public static final byte STATE_C = 1<<3;
    private byte status = STATE_A;

//    @IntDef({STATE_A,STATE_B,STATE_C})
//    public @interface AnimationState{};



    public uberStart(Context context) {
        super(context);
    }

    public uberStart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public uberStart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public uberStart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.WHITE);

        mPaint1 = new Paint();
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setAntiAlias(true);
        mPaint1.setStrokeWidth(1);
        mPaint1.setColor(Color.BLACK);
        mPaint1.setAlpha(0);

        mPaint2 = new Paint();
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(2);
        mPaint2.setColor(Color.rgb(65,112,138));

        valueAnimator = new ValueAnimator().ofFloat(255,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bianchang = (float) animation.getAnimatedValue();
                if(bianchang < 200 && (status != STATE_B)){
                    controlAnimation(STATE_B);
                }
            }
        });

        valueAnimator1 = new ValueAnimator().ofFloat(0,76);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator2 = new ValueAnimator().ofFloat(0,360);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius1 = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator3 = new ValueAnimator().ofFloat(200,270);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius1Start = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator4 = new ValueAnimator().ofFloat(3,12);
        valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerLineWidth = (float) animation.getAnimatedValue();
                if((STATE_B & status) > 0) {
                    invalidate();
                }
            }
        }); 
        valueAnimator5 = new ValueAnimator().ofFloat(38,44);
        valueAnimator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerRect = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(1500);
        valueAnimator1.setDuration(2000);
        valueAnimator2.setDuration(2000);
        valueAnimator3.setDuration(2000);
        valueAnimator4.setDuration(2000);
        valueAnimator5.setDuration(1500);


        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator1.setInterpolator(new AccelerateInterpolator());
        valueAnimator2.setInterpolator(new AccelerateInterpolator());
        valueAnimator3.setInterpolator(new AccelerateInterpolator());
        valueAnimator4.setInterpolator(new AccelerateInterpolator());
        valueAnimator5.setInterpolator(new AccelerateInterpolator());


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               controlAnimation(STATE_A);
            }
        });
    }


    private void controlAnimation(byte state){
        if((state & STATE_A) > 0){
//            valueAnimator.setDuration(500);
//            valueAnimator5.setDuration(500);
            valueAnimator.start();
            valueAnimator5.start();
            status = STATE_A;
        }else if((state & STATE_B) > 0){
//            valueAnimator.setDuration(2000);
//            valueAnimator5.setDuration(2000);
//            valueAnimator.start();
            valueAnimator1.start();
            valueAnimator2.start();
            valueAnimator3.start();
            valueAnimator4.start();
//            valueAnimator5.start();
            status = STATE_B;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if((status & STATE_A) > 0 || (STATE_B & status) > 0) {
            mPaint.setAlpha((int) (bianchang));
            float temp = 82 - centerRect;
            canvas.drawRect(screenWidth / 2 - temp / 2, screenHeight / 2 - temp / 2, screenWidth / 2 + temp / 2, screenHeight / 2 + temp / 2, mPaint);
            if ((STATE_B & status) > 0) {

                Path path = new Path();
                Path path1 = new Path();

                mPaint.setAlpha(255);

                path1.moveTo(screenWidth / 2, screenHeight / 2);
                float startAngle = radius1Start;
                float sweepAngle = radius1Start + radius1;
                path1.lineTo((float) (screenWidth / 2 + radius * Math.cos(startAngle * Math.PI / 180)), (float) (screenHeight / 2 + radius * Math.sin(startAngle * Math.PI / 180)));
                path1.lineTo((float) (screenWidth / 2 + radius * Math.cos(sweepAngle * Math.PI / 180)), (float) (screenHeight / 2 + radius * Math.sin(sweepAngle * Math.PI / 180)));
                path.addArc(screenWidth / 2 - radius, screenHeight / 2 - radius, screenWidth / 2 + radius, screenHeight / 2 + radius, startAngle, sweepAngle - startAngle);
                path.op(path1, Path.Op.XOR);
                canvas.drawPath(path1, mPaint1);
                canvas.drawPath(path, mPaint);
                mPaint2.setAlpha(255);
                mPaint2.setStrokeWidth(centerLineWidth);
                canvas.drawLine(screenWidth / 2, screenHeight / 2, (float) (screenWidth / 2 + radius * Math.cos(startAngle * Math.PI / 180)), (float) (screenHeight / 2 + radius * Math.sin(startAngle * Math.PI / 180)), mPaint2);
                mPaint2.setAlpha((int) (255 - bianchang));
                canvas.drawRect(screenWidth / 2 - centerRect / 2, screenHeight / 2 - centerRect / 2, screenWidth / 2 + centerRect / 2, screenHeight / 2 + centerRect / 2, mPaint2);
//                mPaint.setAlpha((int) bianchang);
//                canvas.drawRect(screenWidth / 2 - temp / 2, screenHeight / 2 - temp / 2, screenWidth / 2 + temp / 2, screenHeight / 2 + temp / 2, mPaint);
            }
        }
    }
}

/**
 * Path.Op.DIFFERENCE 减去path1中path1与path2都存在的部分;
 path1 = (path1 - path1 ∩ path2)
 Path.Op.INTERSECT 保留path1与path2共同的部分;
 path1 = path1 ∩ path2
 Path.Op.UNION 取path1与path2的并集;
 path1 = path1 ∪ path2
 Path.Op.REVERSE_DIFFERENCE 与DIFFERENCE刚好相反;
 path1 = path2 - (path1 ∩ path2)
 Path.Op.XOR 与INTERSECT刚好相反;
 path1 = (path1 ∪ path2) - (path1 ∩ path2)
 * */