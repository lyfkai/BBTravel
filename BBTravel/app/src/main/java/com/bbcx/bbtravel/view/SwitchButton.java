package com.bbcx.bbtravel.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.bbcx.bbtravel.R;


/**
 * Created by dandy on 2016/4/12.
 */
public class SwitchButton extends View{

    private static final String TAG = "SwitchButton";

    /**默认宽**/
    private static  final float DEFAULT_WIDTH = 15;

    /**默认高**/
    private static  final float DEFAULT_HEIGHT = 34;

    private static  final long DELAYDURATION = 10;

    /**开启颜色**/
    private int onColor = Color.parseColor("#32D2DC");
    /**关闭颜色**/
    private int offColor = Color.parseColor("#dadbda");
    /**灰色带颜色**/
    private int areaColor = Color.parseColor("#dadbda");
    /**手柄颜色**/
    private int handlerColor = Color.parseColor("#ffffff");
    /**边框颜色**/
    private int borderColor = offColor;
    /**开关状态**/
    private boolean toggleOn = false;
    /**边框宽**/
    private int borderWidth = 2;
    /**纵轴中心**/
    private float centerY;
    /**按钮水平方向开始、结束的位置**/
    private float startX,endX;
    /**手柄x轴方向最小、最大值**/
    private float handlerMinX,handlerMaxX;
    /**手柄大小**/
    private int handlerSize;
    /**手柄在x轴的坐标位置**/
    private float handlerX;
    /**关闭时内部灰色带宽度**/
    private float areaWidth;
    /**是否使用动画效果**/
    private boolean animate = true;
    /**是否默认处于打开状态**/
    private boolean defaultOn = true;
    /**按钮半径**/
    private float radius;

    private RectF mRectF = new RectF();

    private Paint mPaint;

    private OnToggleChangedListener mListener;

    private Handler mHandler = new Handler();

    private double currentDelay;

    private MatchStyle matchStyle;

    private float downX = 0,downY = 0;

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }
    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    /**
     * 初始化
     * @param attrs
     */
    private void setup(AttributeSet attrs){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        onColor = typedArray.getColor(R.styleable.SwitchButton_onColor,onColor);
        borderColor = offColor = typedArray.getColor(R.styleable.SwitchButton_offColor,offColor);
        areaColor = typedArray.getColor(R.styleable.SwitchButton_areaColor,areaColor);
        handlerColor = typedArray.getColor(R.styleable.SwitchButton_handlerColor,handlerColor);
        borderWidth = typedArray.getColor(R.styleable.SwitchButton_bordeWidth, borderWidth);
        animate = typedArray.getBoolean(R.styleable.SwitchButton_animate, animate);
        defaultOn = typedArray.getBoolean(R.styleable.SwitchButton_defaultOn,defaultOn);
        matchStyle = MatchStyle.getValue(typedArray.getInt(R.styleable.SwitchButton_matchStyle,MatchStyle.WHOLE.ordinal()));
        typedArray.recycle();

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        if(defaultOn){
            currentDelay = 1;
            toggleOn();
        }else{
            currentDelay = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();

        if(widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST){
            widthSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFAULT_WIDTH,dm);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY);
        }

        if(heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST){
            heightSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFAULT_HEIGHT,dm);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final int width = getWidth();
        final int height = getHeight();

        radius = Math.min(width,height) * 0.5f;
        centerY = radius;
        startX = centerY;
        endX = width - radius;
        handlerMinX = startX + borderWidth;
        handlerMaxX = endX - borderWidth;
        handlerSize = height - 4*borderWidth;
        handlerX = toggleOn?handlerMaxX:handlerMinX;
        areaWidth = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        /**绘制整个按钮**/
        mRectF.set(0, 0, getWidth(), getHeight());
        mPaint.setColor(borderColor);
        canvas.drawRoundRect(mRectF,radius,radius,mPaint);

        /**绘制关闭灰色区域**/
        if(areaWidth > 0 ){
            final float cy = areaWidth * 0.5f;
            mRectF.set(handlerX - cy,centerY - cy,endX + cy,centerY + cy);
            mPaint.setColor(offColor);
            canvas.drawRoundRect(mRectF,cy,cy,mPaint);
        }

        /**绘制手柄**/
        final float handlerRadius = handlerSize * 0.5f;
        mRectF.set(handlerX - handlerRadius, centerY - handlerRadius, handlerX + handlerRadius, centerY + handlerRadius);
        mPaint.setColor(handlerColor);
        canvas.drawRoundRect(mRectF, handlerRadius, handlerRadius, mPaint);

        super.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(matchStyle == MatchStyle.WHOLE){
                    performClick();
                }else if(matchStyle == MatchStyle.HANDLER && mRectF.contains(downX,downY)){
                    performClick();
                }
                break;
        }
        return true;
    }

    /**
     * 开关状态切换
     */
    public void toggle(){
        toggle(animate);
    }
    /**
     * 开关状态切换
     * @param animate
     */
    public void toggle(boolean animate){
        toggleOn = !toggleOn;
        takeEffect(animate);
    }

    /**
     * 开启状态
     */
    public void toggleOn(){
        toggleOn(animate);
    }
    /**
     * 开启状态
     * @param animate
     */
    public void toggleOn(boolean animate){
        toggleOn = true;
        takeEffect(animate);
    }

    /**
     * 关闭状态
     */
    public void toggleOff(){
        toggleOff(animate);
    }
    /**
     * 关闭状态
     * @param animate
     */
    public void toggleOff(boolean animate){
        toggleOn = false;
        takeEffect(animate);
    }

    /**
     * 开始处理状态切换
     * @param animate
     */
    private void takeEffect(boolean animate){
        if(mListener != null){
            mListener.onToggle(toggleOn);
        }
        if(animate){
            mHandler.postDelayed(toggleRunnable,DELAYDURATION);
        }else {
            caculateEffect(toggleOn ? 1 : 0);
        }
    }

    /**
     * 时时计算
     * @param value
     */
    private void caculateEffect(double value){

        handlerX = (float)mapValueFromRangeToRange(value,0,1.0,handlerMinX,handlerMaxX);

        areaWidth = (float)mapValueFromRangeToRange(1.0-value,0,1.0,10,handlerSize);

        final int fb = Color.blue(onColor);
        final int fr = Color.red(onColor);
        final int fg = Color.green(onColor);

        final int tb = Color.blue(offColor);
        final int tr = Color.red(offColor);
        final int tg = Color.green(offColor);

        int sb = (int) mapValueFromRangeToRange(1.0 - value, 0, 1.0, fb, tb);
        int sr = (int) mapValueFromRangeToRange(1.0 - value, 0, 1.0, fr, tr);
        int sg = (int) mapValueFromRangeToRange(1.0 - value, 0, 1.0, fg, tg);

        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);

        borderColor = Color.rgb(sr, sg, sb);

        postInvalidate();
    }

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }
    /**
     * Map a value within a given range to another range.
     * @param value the value to map
     * @param fromLow the low end of the range the value is within
     * @param fromHigh the high end of the range the value is within
     * @param toLow the low end of the range to map to
     * @param toHigh the high end of the range to map to
     * @return the mapped value
     */
    private  double mapValueFromRangeToRange(
            double value, double fromLow, double fromHigh,
            double toLow, double toHigh) {
        double fromRangeSize = fromHigh - fromLow;
        double toRangeSize = toHigh - toLow;
        double valueScale = (value - fromLow) / fromRangeSize;
        return toLow + (valueScale * toRangeSize);
    }

    private final Runnable toggleRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.removeCallbacks(toggleRunnable);
            if(toggleOn){
                if(currentDelay <= 1){
                    caculateEffect(currentDelay);
                    mHandler.postDelayed(toggleRunnable, DELAYDURATION);
                    currentDelay = currentDelay + 0.1;
                }else{
                    currentDelay = 1;
                }
            }else{
                if(currentDelay >= 0){
                    caculateEffect(currentDelay);
                    mHandler.postDelayed(toggleRunnable, DELAYDURATION);
                    currentDelay = currentDelay - 0.1;
                }else{
                    currentDelay = 0;
                }
            }
        }
    };

    /**
     * 设置开关监听
     */
    public void setOnToggleChangedlistener(OnToggleChangedListener listener){
        this.mListener = listener;
    }
    /**
     * 开关状态监听
     */
    public interface OnToggleChangedListener{
        /**
         * 是否开启
         * @param on
         */
        void onToggle(boolean on);
    }

    /**
     * 点击或者滑动时候响应的区域
     */
    private enum MatchStyle{
        WHOLE,//整个区域响应
        HANDLER;//只有手柄响应
        public static MatchStyle getValue(int index){
            for(MatchStyle style:values()){
                if(style.ordinal() == index){
                    return style;
                }
            }
            return WHOLE;
        }
    }
}
