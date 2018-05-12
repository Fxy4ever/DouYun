package util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by mac on 2018/5/12.
 */


public class FProgressBar extends View {//通过自定义view
    private int sweepIndex = 0;

    //交换执行动画的颜色数组
    private int[] colors = new int[]{Color.YELLOW,Color.RED,Color.BLUE};

    //中间到最左边的距离
    private Float maxWidth = 200f;

    private Float radius = 40f;

    private Float currentX = 0f;

    private Paint paint;
    //属性动画
    private ValueAnimator valueAnimator;
    public FProgressBar(Context context) {
        super(context);
        startAnimator();
    }

    public FProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        startAnimator();
    }

    public FProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startAnimator();
    }

    private void startAnimator(){
        valueAnimator = ValueAnimator.ofFloat(0f,maxWidth,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (Float) animation.getAnimatedValue();//获得更新后的坐标
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                sweep(sweepIndex);
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());//设置匀速插值器
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    //交换颜色
    private void sweep(int a){
        int temp = colors[2];
        colors[2] = colors[a];
        colors[a] = temp;

        if(a==0){
            sweepIndex = 1;
        }else{
            sweepIndex = 0;
        }
    }
    //交换颜色后会重新绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        //画左边的圆
        paint.setColor(colors[0]);
        canvas.drawCircle(centerX - currentX, centerY, radius, paint);

        //画右边的圆
        paint.setColor(colors[1]);
        canvas.drawCircle(centerX + currentX, centerY, radius, paint);

        //画中间的圆
        paint.setColor(colors[2]);
        canvas.drawCircle(centerX, centerY, radius, paint);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valueAnimator.cancel();
    }
}
