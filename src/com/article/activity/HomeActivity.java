package com.article.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.article.R;
import com.article.adapter.PagerAdapter;
import com.article.fragment.BindFragment;
import com.article.fragment.MessageFragment;
import com.article.fragment.PersonalFragment;
import com.article.fragment.ProjectFragment;

/**
 * @ClassName: Text.java
 * @Description: 主界面，展示信息所用的
 * 
 * @author zcs
 * @version V1.0
 * @Date 2017年03月20日 上午10:57:59
 */
public class HomeActivity extends BaseActivity {

	private RadioGroup rg_botton;
	private ViewPager viewPager;
	int currentIndex;
	private ProjectFragment proFragment;
	@Override
	public void onCreateView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		createView(R.layout.activity_home);
		viewPager = (ViewPager) getView(R.id.home_viewpager);
		rg_botton = getView(R.id.rg_bottom);
		List<Fragment> fragments = new ArrayList<Fragment>();
		proFragment = new ProjectFragment();
		Fragment msgFragment = new MessageFragment();
		Fragment bindFragment = new BindFragment();
		Fragment perFragment = new PersonalFragment();
		fragments.add(proFragment);
		fragments.add(msgFragment);
		fragments.add(bindFragment);
		fragments.add(perFragment);
		viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),fragments));
		((RadioButton) rg_botton.getChildAt(0)).setChecked(true);// 选中第一个
		// 下方按钮点击事件
		rg_botton.setOnCheckedChangeListener(new MyOnCheckedChangeListener());//下方按钮监听
		viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),
				fragments));
		viewPager.setCurrentItem(0);// 设置当前显示标签页为第一页
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());//滑动
		viewPager.setOffscreenPageLimit(3);
		
//		InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
//		SoftKeyboard softKeyboard;
//		softKeyboard = new SoftKeyboard(mainLayout, im);
	}

	// 下导航栏点击事件
	class MyOnCheckedChangeListener implements
			RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rb_bottom01:
				viewPager.setCurrentItem(0);
				break;
			case R.id.rb_bottom02:
				viewPager.setCurrentItem(1);
				break;
			case R.id.rb_bottom03:
				viewPager.setCurrentItem(2);
				break;
			case R.id.rb_bottom04:
				viewPager.setCurrentItem(3);
				break;
			default:
				viewPager.setCurrentItem(0);
				break;
			}
		}
	}

	// 屏幕滑动的事件
	class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

		View rb;

		@Override
		public void onPageSelected(int arg0) {
			if (currentIndex == arg0) {
				return;
			}
			currentIndex = arg0;
			int i = currentIndex + 1;
//			LogUtils.e("onPageSelected" + arg0 + "页");
			//setTopText("第" + i + "页");
			switch (arg0) {
			case 0:
				rb = rg_botton.getChildAt(0);
				break;
			case 1:
				rb = rg_botton.getChildAt(2);
				break;
			case 2:
				rb = rg_botton.getChildAt(4);
				break;
			case 3:
				rb = (RadioButton) rg_botton.getChildAt(6);
				break;
			}
			if (rb instanceof RadioButton) {
				((RadioButton) rb).setChecked(true);
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
