package com.article.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import charts.tree.model.Axis;
import charts.tree.model.Line;
import charts.tree.model.LineChartData;
import charts.tree.model.PointValue;
import charts.tree.model.ValueShape;
import charts.tree.model.Viewport;
import charts.tree.view.LineChartView;

import com.SystemConstants;
import com.article.R;
import com.article.view.ColumnHorizontalScrollView;

/**
 * @ClassName: ArticleInfo.java
 * @author zcs
 * @version V1.0
 * @Date 2017年3月27日 下午3:24:25
 * @Description: 货物信息
 */
public class ArticleInfoActivity extends BaseActivity {
	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private boolean hasLines = false;
	private boolean hasPoints = true;
	private ValueShape shape = ValueShape.CIRCLE;
	private boolean isFilled = false;
	private boolean hasLabels = false;
	private boolean isCubic = false;
	private boolean hasLabelForSelected = false;
	private boolean pointsHaveDifferentColor;
	private boolean hasGradientToTransparent = false;
	List<Line> lines = new ArrayList<Line>();// 保存线的个数
	private LineChartData data;
	private LineChartView chartView;
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	private int mItemWidth;
	protected ImageView shade_left;
    protected ImageView shade_right;
    private List<String> listType = new ArrayList<String>();
    /** 当前选中的栏目 */
    private int columnSelectIndex = 0;
	@Override
	protected void onCreateView(Bundle savedInstanceState) {
		createView(R.layout.activity_article_info);
		TextView view = getView(R.id.tv_title);
		view.setText("浪潮服务器");
		chartView = getView(R.id.chart_tree);

		chartView.setViewportCalculationEnabled(false);
		hasAxes = true;
		hasAxesNames = true;
		hasLines = false;
		hasPoints = true;
		shape = ValueShape.CIRCLE;// 形状
		isFilled = false;
		hasLabels = false;
		isCubic = false;
		hasLabelForSelected = false;
		pointsHaveDifferentColor = false;
//		resetViewport();
		chartView.setValueSelectionEnabled(hasLabelForSelected);
		resetViewport();
		listType.add("总计");
		listType.add("跌落");
		listType.add("冲击");
		listType.add("振动");
		listType.add("抛物");
		listType.add("开箱");
		listType.add("高空");
		// findViewById(R.id.btn_reset).setOnClickListener(new
		// View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// resetViewport();
		// // String url = SystemConstants.getUrl(true,
		// SystemConstants.vibrate_url),dev ="1001";
		// // runDrawStarPoint(url,dev);
		//
		// }
		// });

		// String url2 =
		// "http://192.168.0.141:8001/EmfcRciWeb/MobileCtrl/vibrateDemo",dev
		// ="1001";
		String url = SystemConstants.getUrl(true, SystemConstants.vibrate_url), dev = "1001";
		List<String> strList = new ArrayList<String>();
		strList.add("0,0");
		strList.add("10,15");
		strList.add("20,10");
		strList.add("25,25");
		strList.add("30,10");
		strList.add("35,35");
		strList.add("40,30");
		List<String> strList2 = new ArrayList<String>();
		strList2.add("0,0");
		strList2.add("10,-15");
		strList2.add("20,-10");
		strList2.add("25,-25");
		strList2.add("30,-10");
		strList2.add("35,-35");
		strList2.add("40,-30");
		createLine(strList, Color.RED);
		createLine(strList2, Color.RED);
		mColumnHorizontalScrollView = findViewById2(R.id.mColumnHorizontalScrollView);
		LinearLayout mRadioGroup_content = findViewById2(R.id.mRadioGroup_content);
		int mScreenWidth = getWindowsWidth(this);
		mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
		shade_left = getView(R.id.shade_left);
    	shade_right = getView(R.id.shade_right);
		mColumnHorizontalScrollView.setParam(this,mScreenWidth, mRadioGroup_content, shade_left,
                shade_right, null, null);
        for (int i = 0; i < listType.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth,
                    LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            // TextView localTextView = (TextView)
            // mInflater.inflate(R.layout.column_radio_item, null);
            TextView columnTextView = new TextView(this);
            columnTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
            // localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
            columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setText(listType.get(i));
            columnTextView.setTextColor(getResources().getColorStateList(
                    R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
//                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
//                        View localView = mRadioGroup_content.getChildAt(i);
//                        if (localView != v)
//                            localView.setSelected(false);
//                        else {
//                            localView.setSelected(true);
//                            mViewPager.setCurrentItem(i);
//                        }
//                    }
                }
            });
            mRadioGroup_content.addView(columnTextView, i, params);
        }
	}

	private void createLine(List<String> strList, int color) {// [0.0,3.1257]

		List<PointValue> values = new ArrayList<PointValue>();
		for (String pointValue : strList) {// [0,3.0933]
			pointValue = pointValue.replace("[", "");
			pointValue = pointValue.replace("]", "");
			String[] split = pointValue.split(",");
			if (split != null && split.length > 1) {
				values.add(new PointValue(Float.valueOf(split[0]), Float
						.valueOf(split[1])));
			}
		}
		Line line = new Line(values);
		// 设置线的属性
		line.setColor(color).setShape(shape).setCubic(isCubic)
				.setFilled(isFilled).setHasLabels(hasLabels)
				.setHasLabelsOnlyForSelected(hasLabelForSelected)
				.setHasLines(hasLines).setHasPoints(hasPoints)
				.setHasGradientToTransparent(hasGradientToTransparent);
		if (pointsHaveDifferentColor) {
			line.setPointColor(Color.BLACK);
			// line.setPointColor(Color.CYAN);
			// line.setPointColor(ChartUtils.COLORS[(i + 1) %
			// ChartUtils.COLORS.length]);
		}
		lines.add(line);// 添加一组线
		data = new LineChartData(lines);
		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("Axis X");
				axisY.setName("Axis Y");
			}
			data.setAxisXBottom(axisX);
			data.setAxisYLeft(axisY);
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}
		data.setBaseValue(Float.NEGATIVE_INFINITY);
		chartView.setLineChartData(data);
	};

	/**
	 * view复位
	 */
	private void resetViewport() {
		// Reset viewport height range to (0,100)
		Viewport v = new Viewport(chartView.getMaximumViewport());// 设置最大最小值
		v.bottom = -65;// 底部
		v.top = 65;// 顶部
		v.left = -3f;// 左
		v.right = 45f;// 右
		chartView.setMaximumViewport(v);// 设置最大窗口
		chartView.setCurrentViewport(v);
		lines.clear();// 清除已添加的线
		chartView.setLineChartData(null);// 清除所显示的线
	}
	/** 获取屏幕的宽度 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
}
