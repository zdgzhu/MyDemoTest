package com.mydemotest.UI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;

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
     * 路径：用来获取对勾的路径
     */
    private Path path = new Path();
    /**
     * 取路径的长度
     */
    PathMeasure pathMeasure;
    /**
     * 对路径处理，实现绘制效果
     */
    private PathEffect effect;

    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();

    /**
     * 矩形到圆角过度的动画
     */
    private ValueAnimator animator_rect_to_angle;
    /**
     * 圆角矩形过度到圆的动画
     */
    private ValueAnimator animator_rect_to_square;

    /**
     * view 上移动画
     */
    private ValueAnimator animator_move_to_up;

    /**
     * 绘制对勾√
     */
    private ValueAnimator animator_draw_ok;
    /**
     * 是否开始绘制对勾
     */
    private boolean startDrawOk = false;


    /**
     * view 上移的距离
     */
    private float move_distance = 300;

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

        this.animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animationButtonListener != null) {
                    animationButtonListener.animationFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
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
//        okPaint.setColor(Color.RED);

    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: " );
        width = w;
        height = h;
        default_two_circle_distance = (w-h) / 2;
        initOk();
        initAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: " );
        draw_oval_to_circle(canvas);

        drawText(canvas);//绘制文字
        if (startDrawOk) {
            canvas.drawPath(path, okPaint);
        }

    }

    /**
     * 初始化所有的动画
     */
    private void initAnimation() {
        set_rect_to_angle_animation();
        set_rect_to_circl_animation();
        set_move_to_up_animation();
        set_draw_ok_animation();

        animatorSet.play(animator_move_to_up)
                .before(animator_draw_ok)
                .after(animator_rect_to_square)
                .after(animator_rect_to_angle);

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
     * 设置圆角矩形过度到圆的动画
     */
    private void set_rect_to_circl_animation() {
        animator_rect_to_square = ValueAnimator.ofInt(0, default_two_circle_distance);
        animator_rect_to_square.setDuration(duration);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                two_circle_distance = (int) valueAnimator.getAnimatedValue();
                int alpha = 255 - (two_circle_distance * 255) / default_two_circle_distance;
                textPaint.setAlpha(alpha);
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
        textRect.bottom = height;
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        //绘制文字到整个布局的中心 位置
        canvas.drawText(buttonString, textRect.centerX(), baseline, textPaint);
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
     * 设置view上移的动画
     */
    private void set_move_to_up_animation() {
        final float curTranslationY = this.getTranslationY();
        animator_move_to_up = ObjectAnimator.ofFloat(this, "translationY", curTranslationY, curTranslationY - move_distance);
        animator_move_to_up.setDuration(duration);
        animator_move_to_up.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    /**
     * 绘制对勾的动画
     */
    private void set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);

        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                float value = (Float) animation.getAnimatedValue();

                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();
            }
        });

    }

    /**
     * 绘制对勾
     */
    private void initOk() {
        //对勾的路径
        path.moveTo(default_two_circle_distance + height / 8 * 3, height / 2);
        path.lineTo(default_two_circle_distance + height / 2, height / 5 * 3);
        path.lineTo(default_two_circle_distance + height / 3 * 2, height / 5 * 2);
        pathMeasure = new PathMeasure(path, true);

    }


    /**
     * 启动动画
     */
    public void  start() {
        animatorSet.start();
    }

    /**
     * 动画还原
     */
    public void reset() {
        startDrawOk = false;
        circleAngle = 0;
        two_circle_distance = 0;
        default_two_circle_distance = (width - height) / 2;
        textPaint.setAlpha(255);
        setTranslationY(getTranslationY() + move_distance);
        invalidate();

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
