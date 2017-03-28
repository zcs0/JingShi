package com.article.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @ClassName:     CViewPager.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月27日 上午11:34:42 
 * @Description:   TODO(用一句话描述该文件做什么) 
 */
public class CViewPager extends ViewPager{

	private boolean isSliding;
	public CViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public void setIsOnSliding(boolean isSliding){
		this.isSliding = isSliding;
	}
	public boolean getIsOnSliding(){
		return isSliding;
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if(isSliding){
			return super.onInterceptTouchEvent(arg0);
		}else{
			
			return false;
		}
	}
//	@Override
//	public boolean onTouchEvent(MotionEvent arg0) {
//		return false;
////		return super.onTouchEvent(arg0);
//	}

}
