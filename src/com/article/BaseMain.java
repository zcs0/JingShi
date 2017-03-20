package com.article;

import android.view.View;
/**
 * 所有Activity需要实现类(最顶层)
 * @author ZCS
 *
 */
public interface BaseMain {
	
	/**
	 * 创建当前的 View 需要返回当前R.layout.
	 */
	public int createView(int layoutResID);
	//public EProgressDialogs loadingDialog=null;//弹出显示加载中
	public View createView(View v);
	/**
	 * 得到控件
	 * 
	 * @param layoutId
	 *            控件id
	 * @return 根据id返回指定的类型
	 */
	public <T extends View> T getView(int layoutId);
	/**
	 * 设置点击事件
	 * @param view
	 */
	public <T extends View> T setOnClick(View view);
	/**
	 * 设置点击事件
	 * @param layoutId
	 */
	public <T extends View> T setOnClick(int layoutId);
	
	<T extends View> T setGone(int layoutId);
	<T extends View> T setGone(View View);
	<T extends View> T setVisible(int layoutId);
	<T extends View> T setVisible(View view);
	<T extends View> T setInvisible(int layoutId);
	<T extends View> T setInvisible(View View);
}
