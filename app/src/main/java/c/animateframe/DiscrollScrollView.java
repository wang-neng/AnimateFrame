package c.animateframe;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;


/**
 * Created by wn on 2018/6/3.
 */

public class DiscrollScrollView extends ScrollView {

    private MyLinearLayout mContent ;

    public DiscrollScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View content  = getChildAt(0);
        mContent = (MyLinearLayout) content;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        View first = mContent.getChildAt(0);
        first.getLayoutParams().height = getHeight();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollViewHeight =  getHeight() ;
        //控制动画执行
        //拿到LinearLayout里面每一个子控件，控制其动画
        //动画执行的百分比来控制每一个子控件执行的百分比
        for (int i=0 ;i<mContent.getChildCount();i++){
            View child = mContent.getChildAt(i);
            if (!(child instanceof DiscrollInterface)){
                continue;
            }
            DiscrollInterface discrollInterface = (DiscrollInterface) child;
            //child离parent顶部的高度
            int childTop = child.getTop();
            //什么时候执行动画呢？当child滑进屏幕的时候
            int childHeight = child.getHeight();
            // t:t就是滑出去的高度
            // child离屏幕顶部的高度
            int absoluteTop = (childTop - t) ;
            Log.d("TAG","scrollViewHeight:"+scrollViewHeight+",child离parent的顶部的距离:"+child.getTop()+"," +
                    "滑出屏幕的距离："+t+",child离屏幕顶部的距离："+(child.getTop()-t)+"child的高度："+child.getHeight()+"," +
                    "child滑出的距离："+(scrollViewHeight-absoluteTop)+",linearLayout的高度"+mContent.getHeight());
            if (absoluteTop <= scrollViewHeight){
                //child浮现的高度=ScrollView的高度-child离屏幕顶部的高度
                int visiableGap = scrollViewHeight - absoluteTop ;
                // float ratio = child浮现的高度/child的高度 ;
                float ratio = visiableGap/(float)childHeight;
                //确保ratio是0-1的范围。得到ratio在0-1的中间值
                discrollInterface.onDiscoll(clamp(ratio,1f,0f));
            }else{
                //恢复
                discrollInterface.onResetDiscoll();
            }
        }
    }

    public static float clamp(float value , float max ,float min){
        return Math.max(Math.min(value,max),min);
    }
}
