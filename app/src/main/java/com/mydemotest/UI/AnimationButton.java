package com.mydemotest.UI;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class AnimationButton extends View {

    private static String TAG = "TAG_AnimationButton";

    /**
     * view 的宽度
     */
    private int width;

    /**
     * view 的高度
     */
    private int height;

    /**
     * 圆角半径
     */
    private int circleAngle=0;

    /**
     * 默认两圆圆心之间的距离=需要移动的距离
     */
    private int default_two_circle_distance;

    /**
     * 两圆圆心之间的距离
     */
    private int two_circle_distance;
    /**
     * 背景颜色
     */
    private int bg_color = 0xffbc7d53;

    /**
     * 按钮文字字符串
     */
    private String buttonString = "确认完成";

    /**
     * 动画执行时间
     */
    private int duration = 2000;

    /**
     * 圆矩形画笔
     */
    private Paint paint;

    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 对勾画笔
     */
    private Paint okPaint;

    /**
     * 文字绘制所在的矩形
     */
    private Rect textRect = new Rect();

    /**
     * 矩形到圆角过度的动画
     */
    private ValueAnimator animator_rect_to_angle;


    /**
     * 根据view的大小设置成矩形
     */
    private RectF rectF = new RectF();

    /**
     * 点击事件的回调
     */
    private AnimationButtonListener animationButtonListener;

    public void setAnimationButtonListener(AnimationButtonListener listener) {
        animationButtonListener = listener;
    }



    public AnimationButton(Context context) {
        this(context,null);
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimationButton(Context context, @Nullable final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        //点击事件
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (animationButtonListener != null) {
                    animationButtonListener.onClickListener();
                }
            }
        });

    }


    private void initPaint() {
        paint = new Paint(); //圆矩形画笔
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bg_color);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //文字的画笔
        textPaint.setTextSize(40);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        okPaint = new Paint();
        okPaint.setStrokeWidth(10);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.WHITE);

    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: " );
        width = w;
        height = h;

        initAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: " );
        draw_oval_to_circle(canvas);

    }

    /**
     * 初始化所有的动画
     */
    private void initAnimation() {
        set_rect_to_angle_animation();

    }


    /**
     * 设置矩形过度到圆角矩形的动画
     */
    private void set_rect_to_angle_animation() {
        animator_rect_to_angle = ValueAnimator.ofInt(0, height / 2);
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                circleAngle = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas) {
        textRect.left = 0;
        textRect.top = 0;
        textRect.right = width;
        textRect.bottom = width;
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();




    }



    /**
     * 绘制带圆角的矩形
     * @param canvas
     */
    private void draw_oval_to_circle(Canvas canvas) {
        //这里是对矩形位置大小的设置
        rectF.left = two_circle_distance;
        rectF.top = 0;
        rectF.right = width - two_circle_distance;
        rectF.bottom = height;
        /**圆角矩形
         * 参数说明
         * rect：RectF对象。
         *  rx：x方向上的圆角半径。
         *  ry：y方向上的圆角半径。
         * paint：绘制时所使用的画笔。
         */
        canvas.drawRoundRect(rectF, circleAngle, circleAngle, paint);
    }

    /**
     * 启动动画
     */
    public void  start() {
        animator_rect_to_angle.start();
    }

    /**
     * 接口回调
     */
    public interface  AnimationButtonListener{

        /**
         * 按点击事件
         */
        void onClickListener();

        /**
         * 动画完成回调
         */
        void animationFinish();

    }


}
