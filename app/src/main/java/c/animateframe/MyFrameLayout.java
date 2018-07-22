package c.animateframe;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wn on 2018/6/2.
 */

public class MyFrameLayout extends FrameLayout implements DiscrollInterface {

    //定义很多的自定义属性
    /**
     *
     <attr name="discollve_translation">
     <flag name="fromTop" value="0x01"/>
     <flag name="fromBottom" value="0x02"/>
     <flag name="fromLeft" value="0x04"/>
     <flag name="fromRight" value="0x08"/>
     </attr>
      0000000001
      0000000010
      0000000100
      0000001000

     0000000001   top
     0000000100   left  或运算|
     ------------------------
     0000000101
     */
    //平移属性
    private static final int TRANSLATION_FROM_TOP = 0x01 ;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02 ;
    private static final int TRANSLATION_FROM_LEFT = 0x04 ;
    private static final int TRANSLATION_FROM_RIGHT = 0x08 ;

    //颜色估值器
    private static ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();

    //自定义属性的接收变量
    private int mDiscrollveFromBgColor ; //背景颜色变化开始值
    private int mDiscrollveToBgColor ;    //背景设色变化结束值
    private boolean mDiscrollveAlpha ;    //是否需要透明度变化
    private int mDiscrollveTranslation ; //平移值
    private boolean mDiscrollveScaleX ;   //是否需要X轴方向缩放
    private boolean mDiscrollveScaleY ;   //是否需要Y轴方向缩放
    private int mHeight ;                //本View的高度
    private int mWidth ;                 //本View的宽度


    public static int getTranslationFromTop() {
        return TRANSLATION_FROM_TOP;
    }

    public static int getTranslationFromBottom() {
        return TRANSLATION_FROM_BOTTOM;
    }

    public static int getTranslationFromLeft() {
        return TRANSLATION_FROM_LEFT;
    }

    public static int getTranslationFromRight() {
        return TRANSLATION_FROM_RIGHT;
    }

    public static ArgbEvaluator getsArgbEvaluator() {
        return sArgbEvaluator;
    }

    public static void setsArgbEvaluator(ArgbEvaluator sArgbEvaluator) {
        MyFrameLayout.sArgbEvaluator = sArgbEvaluator;
    }

    public int getmDiscrollveFromBgColor() {
        return mDiscrollveFromBgColor;
    }

    public void setmDiscrollveFromBgColor(int mDiscrollveFromBgColor) {
        this.mDiscrollveFromBgColor = mDiscrollveFromBgColor;
    }

    public int getmDiscrollveToBgColor() {
        return mDiscrollveToBgColor;
    }

    public void setmDiscrollveToBgColor(int mDiscrollveToBgColor) {
        this.mDiscrollveToBgColor = mDiscrollveToBgColor;
    }

    public boolean ismDiscrollveAlpha() {
        return mDiscrollveAlpha;
    }

    public void setmDiscrollveAlpha(boolean mDiscrollveAlpha) {
        this.mDiscrollveAlpha = mDiscrollveAlpha;
    }

    public int getmDiscrollveTranslation() {
        return mDiscrollveTranslation;
    }

    public void setmDiscrollveTranslation(int mDiscrollveTranslation) {
        this.mDiscrollveTranslation = mDiscrollveTranslation;
    }

    public boolean ismDiscrollveScaleX() {
        return mDiscrollveScaleX;
    }

    public void setmDiscrollveScaleX(boolean mDiscrollveScaleX) {
        this.mDiscrollveScaleX = mDiscrollveScaleX;
    }

    public boolean ismDiscrollveScaleY() {
        return mDiscrollveScaleY;
    }

    public void setmDiscrollveScaleY(boolean mDiscrollveScaleY) {
        this.mDiscrollveScaleY = mDiscrollveScaleY;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w ;
        mHeight = h ;
        //onResetDiscroll();
    }

    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDiscoll(float ratio) {
        //ratio:0-1
        System.out.println("ratio:"+ratio+" "+mDiscrollveToBgColor+" "+mDiscrollveScaleX);
        if (mDiscrollveAlpha){
            setAlpha(ratio);
        }
        if (mDiscrollveScaleX){
            setScaleX(ratio);
        }
        if (mDiscrollveScaleY){
            setScaleY(ratio);
        }
        //判断平移mDiscrollTranstion--int
        // int = fromLeft|fromBottom
        // Bottom
        if (isTranslationFrom(TRANSLATION_FROM_BOTTOM)){
            setTranslationY(mHeight*(1-ratio));  //height-->0(0代表恢复到原来的位置)
        }
        if (isTranslationFrom(TRANSLATION_FROM_TOP)){
            setTranslationY(-mHeight*(1-ratio));  //height-->0(0代表恢复到原来的位置)
        }
        if (isTranslationFrom(TRANSLATION_FROM_LEFT)){
            setTranslationX(-mWidth*(1-ratio));  //width-->0(0代表恢复到原来的位置)
        }
        if (isTranslationFrom(TRANSLATION_FROM_RIGHT)){
            setTranslationX(mWidth*(1-ratio));  //width-->0(0代表恢复到原来的位置)
        }

        //判断从什么颜色到什么颜色
        if (mDiscrollveFromBgColor != -1 && mDiscrollveToBgColor != -1){
            setBackgroundColor((int)sArgbEvaluator.evaluate(ratio,mDiscrollveFromBgColor,mDiscrollveToBgColor));
        }

    }

    private boolean isTranslationFrom(int translationMask){
        if (mDiscrollveTranslation == -1){
            return  false ;
        }
        // fromLeft|fromBottom & fromBottom = fromBottom
        return (mDiscrollveTranslation & translationMask) == translationMask ;
    }

    @Override
    public void onResetDiscoll() {
        if (mDiscrollveAlpha){
            setAlpha(0);
        }
        if (mDiscrollveScaleX){
            setScaleX(0);
        }
        if (mDiscrollveScaleY){
            setScaleY(0);
        }
        //判断平移mDiscrollTranstion--int
        // int = fromLeft|fromBottom
        // Bottom
        if (isTranslationFrom(TRANSLATION_FROM_BOTTOM)){
            setTranslationY(mHeight);  //height-->0(0代表恢复到原来的位置)
        }
        if (isTranslationFrom(TRANSLATION_FROM_TOP)){
            setTranslationY(-mHeight);  //height-->0(0代表恢复到原来的位置)
        }
        if (isTranslationFrom(TRANSLATION_FROM_LEFT)){
            setTranslationY(-mWidth);  //width-->0(0代表恢复到原来的位置)
        }
        if (isTranslationFrom(TRANSLATION_FROM_RIGHT)){
            setTranslationY(mWidth);  //width-->0(0代表恢复到原来的位置)
        }

    }
}
