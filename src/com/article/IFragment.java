package com.article;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


/**
 * @ClassName: IFragment.java
 * @author zcs
 * @version V1.0
 * @Date 2016年4月22日 上午9:46:04
 * @Description:
 */
public abstract class IFragment extends Fragment {
//	protected FragmentManager fraM = MainActivity.fraM;
	 protected View fragmentV;// 此卡片所用的View
//	protected OnBackHandleInterface mBackHandledInterface;

	/**
	 * 
	 * @param isVisible
	 *            是否显示
	 * @param isAdded
	 *            是否已添加到FragmentManager
	 * @param isBack
	 *            在当前界面是否触发返回
	 * @return 是否拦截返回
	 */
	public abstract boolean onBackPressed(boolean isVisible, boolean isAdded,
			boolean isBack);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getContext();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		// 告诉FragmentActivity，当前Fragment在栈顶
//		mBackHandledInterface.setSelectedFragment(this);
	}
	public Context getContext() {
		return super.getActivity();
	}
	protected <T extends View> T findViewById(int id){
		View view = fragmentV.findViewById(id);
		return (T) view;
	}

	@Override
	public void onDetach() {
		super.onDetach();
//		mBackHandledInterface.setSelectedFragment(null);
	}

	// /**
	// * 监听Fragment的显示和跳转
	// * @author Administrator
	// *
	// */
	// class onFragmentBackListener implements
	// FragmentManager.OnBackStackChangedListener{
	//
	// @Override
	// public void onBackStackChanged() {
	// List<Fragment> fragments = fraM.getFragments();
	// if(fragments==null||fragments.size()<=0)return;
	// for (Fragment fragment : fragments) {
	// if(fragment!=null){
	// onBackPressed(fragment.isVisible(), fragment.isAdded());
	// System.out.println("返回监听"+fragment.getClass()+"    显示  "+fragment.isVisible()+"   添加  "+fragment.isAdded());
	// }
	// }
	// // System.out.println("返回监听");
	// }
	// }

}
