
package com.article.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.R;
import com.article.fragment.BaseFragment;
import com.article.view.ColumnHorizontalScrollView;

public class MainFragment extends BaseFragment {
    /** 自定义HorizontalScrollView */
    protected ColumnHorizontalScrollView mColumnHorizontalScrollView;
    protected LinearLayout mRadioGroup_content;
    protected LinearLayout ll_more_columns;
    protected RelativeLayout rl_column;
    protected ImageView button_more_columns;
    protected ViewPager mViewPager;
    protected ImageView shade_left;
    protected ImageView shade_right;

    /** 屏幕宽度 */
    private int mScreenWidth = 0;
    /** Item宽度 */
    private int mItemWidth = 0;
    /** head 头部 的左侧菜单 按钮 */
    protected ImageView top_head;
    /** head 头部 的右侧菜单 按钮 */
    protected ImageView top_more;
    /** 用户选择的新闻分类列表 */
    /** 请求CODE */
    public final static int CHANNELREQUEST = 1;
    /** 调整返回的RESULTCODE */
    public final static int CHANNELRESULT = 10;
    /** 当前选中的栏目 */
    private int columnSelectIndex = 0;
    private ArrayList<Fragment> fragments;

    private Fragment newfragment;
    private double back_pressed;

    public static boolean isChange = false;

//    void init() {
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//        mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
//        fragments = new ArrayList<Fragment>();
//    }



    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
    	mScreenWidth = getWindowsWidth(getActivity());
    	mRadioGroup_content = findViewById(R.id.mRadioGroup_content);
    	mColumnHorizontalScrollView = findViewById(R.id.mColumnHorizontalScrollView);
    	shade_left = findViewById(R.id.shade_left);
    	shade_right = findViewById(R.id.shade_right);
    	ll_more_columns = findViewById(R.id.ll_more_columns);
    	rl_column = findViewById(R.id.rl_column);
        mRadioGroup_content.removeAllViews();
//        int count = userChannelLists.size();
        mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
        mColumnHorizontalScrollView.setParam(getActivity(),mScreenWidth, mRadioGroup_content, shade_left,
                shade_right, null, null);
        for (int i = 0; i < 20; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth,
                    LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            // TextView localTextView = (TextView)
            // mInflater.inflate(R.layout.column_radio_item, null);
            TextView columnTextView = new TextView(getActivity());
            columnTextView.setTextAppearance(getActivity(), R.style.top_category_scroll_view_item_text);
            // localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
            columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setText("测试");
//            columnTextView.setTextColor(Color.BLACK);
            columnTextView.setTextColor(getResources().getColorStateList(
                    R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
//            columnTextView.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
//                        View localView = mRadioGroup_content.getChildAt(i);
//                        if (localView != v)
//                            localView.setSelected(false);
//                        else {
//                            localView.setSelected(true);
//                            mViewPager.setCurrentItem(i);
//                        }
//                    }
//                }
//            });
            mRadioGroup_content.addView(columnTextView, i, params);
        }
    }

/*    public Fragment initFragment(String channelName) {
        if (channelName.equals("头条")) {
            newfragment = new NewsFragment_();
        } else if (channelName.equals("足球")) {
            newfragment = new FoodBallFragment_();
        } else if (channelName.equals("娱乐")) {
            newfragment = new YuLeFragment_();
        } else if (channelName.equals("体育")) {
            newfragment = new TiYuFragment_();
        } else if (channelName.equals("财经")) {
            newfragment = new CaiJingFragment_();
        } else if (channelName.equals("科技")) {
            newfragment = new KeJiFragment_();
        } else if (channelName.equals("电影")) {
            newfragment = new DianYingFragment_();
        } else if (channelName.equals("汽车")) {
            newfragment = new QiCheFragment_();
        } else if (channelName.equals("笑话")) {
            newfragment = new XiaoHuaFragment_();
        } else if (channelName.equals("时尚")) {
            newfragment = new ShiShangFragment_();
        } else if (channelName.equals("北京")) {
            newfragment = new BeiJingFragment_();
        } else if (channelName.equals("军事")) {
            newfragment = new JunShiFragment_();
        } else if (channelName.equals("房产")) {
            newfragment = new FangChanFragment_();
        } else if (channelName.equals("游戏")) {
            newfragment = new YouXiFragment_();
        } else if (channelName.equals("情感")) {
            newfragment = new QinGanFragment_();
        } else if (channelName.equals("精选")) {
            newfragment = new JingXuanFragment_();
        } else if (channelName.equals("电台")) {
            newfragment = new DianTaiFragment_();
        } else if (channelName.equals("图片")) {
            newfragment = new TuPianFragment_();
        } else if (channelName.equals("NBA")) {
            newfragment = new NBAFragment_();
        } else if (channelName.equals("数码")) {
            newfragment = new ShuMaFragment_();
        } else if (channelName.equals("移动")) {
            newfragment = new YiDongFragment_();
        } else if (channelName.equals("彩票")) {
            newfragment = new CaiPiaoFragment_();
        } else if (channelName.equals("教育")) {
            newfragment = new JiaoYuFragment_();
        } else if (channelName.equals("论坛")) {
            newfragment = new LunTanFragment_();
        } else if (channelName.equals("旅游")) {
            newfragment = new LvYouFragment_();
        } else if (channelName.equals("手机")) {
            newfragment = new ShouJiFragment_();
        } else if (channelName.equals("博客")) {
            newfragment = new BoKeFragment_();
        } else if (channelName.equals("社会")) {
            newfragment = new SheHuiFragment_();
        } else if (channelName.equals("家居")) {
            newfragment = new JiaJuFragment_();
        } else if (channelName.equals("暴雪")) {
            newfragment = new BaoXueYouXiFragment_();
        } else if (channelName.equals("亲子")) {
            newfragment = new QinZiFragment_();
        } else if (channelName.equals("CBA")) {
            newfragment = new CBAFragment_();
        }
        return newfragment;
    }
*/


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

	@Override
	public int createView() {
		// TODO Auto-generated method stub
		return R.layout.main_home;
	}
	/** 获取屏幕的宽度 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}



	@Override
	public void initView(Bundle bundle, View view) {
		initTabColumn();
		
	}
}
