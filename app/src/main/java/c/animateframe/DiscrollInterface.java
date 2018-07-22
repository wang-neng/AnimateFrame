package c.animateframe;

/**
 * Created by wn on 2018/6/2.
 */

public interface DiscrollInterface {

    /**
     * 当滑动的时候调用该方法，用来控制里面控件执行相应的动画
     * @param ratio 0-1的范围，动画执行的百分比
     */
    public void onDiscoll(float ratio);

    /**
     * 重置动画：让View的所有属性恢复原来的值
     */
    public void onResetDiscoll();
}
