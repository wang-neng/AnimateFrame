package c.animateframe;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by wn on 2018/6/2.
 */

public class MyLinearLayout extends LinearLayout {


    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //采花大盗--获取child里面的自定义属性
        return new MyLayoutParams(getContext(),attrs);
       //return super.generateLayoutParams(attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        //偷梁换柱
        //获取子控件里面的自定义属性： layoutParams
        MyLayoutParams p = (MyLayoutParams) params;
        System.out.println("AAAAA:"+isDiscollvable(p));
        if(!isDiscollvable(p)){
            super.addView(child, index, params);
        }else{
            MyFrameLayout mf = new MyFrameLayout(getContext());
            mf.setmDiscrollveAlpha(p.mDiscrollveAlpha);
            mf.setmDiscrollveScaleX(p.mDiscrollveScaleX);
            mf.setmDiscrollveScaleY(p.mDiscrollveScaleY);
            mf.setmDiscrollveToBgColor(p.mDiscrollveToBgColor);
            mf.setmDiscrollveFromBgColor(p.mDiscrollveFromBgColor);
            mf.setmDiscrollveTranslation(p.mDiscrollveTranslation);
            mf.addView(child);
            super.addView(mf,index,params);
        }


    }

    //判断是否有自定义属性
    private boolean isDiscollvable(MyLayoutParams p){
        return p.mDiscrollveAlpha || p.mDiscrollveScaleX || p.mDiscrollveScaleY || p.mDiscrollveTranslation != -1 ||
        (p.mDiscrollveFromBgColor != -1 && p.mDiscrollveToBgColor != -1);
    }

    public static class MyLayoutParams extends LayoutParams{

        private int mDiscrollveFromBgColor ; //背景颜色变化开始值
        private int mDiscrollveToBgColor ;    //背景设色变化结束值
        private boolean mDiscrollveAlpha ;    //是否需要透明度变化
        private int mDiscrollveTranslation ; //平移值
        private boolean mDiscrollveScaleX ;   //是否需要X轴方向缩放
        private boolean mDiscrollveScaleY ;   //是否需要Y轴方向缩放

        public MyLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            //从child里面获取自定义属性
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.DiscrollView_LayoutParams);
            mDiscrollveAlpha = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_alpha,false);
            mDiscrollveFromBgColor  = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_fromBgColor,-1);
            mDiscrollveToBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_toBgColor,-1);
            mDiscrollveTranslation = a.getInt(R.styleable.DiscrollView_LayoutParams_discrollve_translation,-1);
            mDiscrollveScaleX = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleX,false);
            mDiscrollveScaleY  = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleY,false);
            a.recycle();
        }
    }
}
