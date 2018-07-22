# AnimateFrame
Android Custom AnimateFrame
自定义动画框架

1：自定义控件

2：动画

3：框架   

4：从源码学习找灵感

---

1.最外层是一个滑动的ScrollView-------自定义scrollView,重写onScrollChanged()

监听滑动事件----onScrollListener

2.每一个控件的动画不一样

属性动画(alpaa,translationX,scale)

declare-styleable里面自定义属性[例如枚举属性]

自定义属性------给系统控件自定义属性<--->自定义View使用自定义属性

    <MyImageView
    	android:layout_width=
    	discrollve:discrollve_translation= />

能否让系统的属性认识我的自定义属性呢？

否。---间接实现

1）只有自定义控件才认识自己的自定义属性

偷偷为每一个子控件外层包裹一层容器，然后让容器识别自定义属性，并执行动画

    常规下都这么做
    <MyFrameLayout
    	discrollve:discrollve_translation= 
    	<ImageView
    		android:layout_width= />
    </MyFrameLayout>
    -------------------
    <MyFrameLayout
    	 
    	<ImageView
    		android:layout_width= 
    		discrollve:discrollve_translation=/>
    </MyFrameLayout>

    MyFrameLayout extends FrameLayout {
    	XXX(){
    		setAlpha(XXX);
    		setTranslationX(XXX);
    	}
    }

问题：

1.如何做到偷偷的为每一个子控件外层包裹一层容器？

自定义LinearLayout

    public class MyLinearLayout extends LinearLayout {
    	
    	@Override
    	public LayoutParams generateLayoutParams(AttributeSet sttrs){
    		//采花大盗-----获取child里面的自定义属性
            //return super.generateLayoutParams(attas);
            return new MyLayoutParams(getContext(), attrs)
    	}
    	
    	//为系统控件添加容器
    	@Override
    	public void addView(View child, int index, 
    		android.view,ViewGroup.LayoutParams params){
    		MyFrameLayout mf = new MyFrameLayout();
    		//MyLayoutParams p = (MyLayoutParams)params;
    		//mf.setXXXAlpha(p.getXXXAlpha);
    		mf.addView(child);   //如何获取child里面的自定义属性？
    		super.addView(mf, index, params);
    		//super.addView(child, index, params);
    	}
    	
    	public static class MyLayoutParams extends LinearLAyout.LayoutParams{
            public MyLayoutParams(Context context, AttributeSet attrs){
                super(context, attrs);
                //从child里面拿到我自定义的属性
                TypeArrays a = context.obtainStyle
            }
    	}
    }

2.能否获取子空间里面的自定义属性？

LayoutParams?（代码里面通过LayoutParams获取layout_gravity,layout_width属性值）

    View.java
    setLayoutParams(ViewGroup.LayoutParams params){
    	params从何而来？parents给的
    	设置params给子控件使用
    }
    
    

DecorView---帧布局

    <DecorView>
    	<XX布局>
    </DecorView>

布局渲染机制：

布局通过渲染进来，LayoutFlater

LayoutFlate和LayoutParams的关系

    LayoutFlater.java
    View createViewFromTag()
    public View inflat(XMLPullParse parse,...){
        ......
        //Inflate all children under temp
        //渲染所有的children
        rInflate(parse,temp,attrs,true,true)
    }
    void rInflate(XmlPullParser parser, View parent, final AttributeSet attrs,
    	boolean finishInflate, boolean inheritContext){
        ....
        final View  view = createViewFromTag(parent,name,attrs,inheritContext);
        final ViewGroup viewGroup = (ViewGroup) parent;
        //父容器创建layoutParams给childdren使用
        final ViewGroup.LayoutParams params = viewGroup.generateLayoutPArams(attrs);
        viewGroup.addView(view,params);
    }
    
    generateLayout(){
        
    }


