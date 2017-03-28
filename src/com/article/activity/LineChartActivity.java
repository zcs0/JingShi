package com.article.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import charts.tree.listener.LineChartOnValueSelectListener;
import charts.tree.model.Axis;
import charts.tree.model.Line;
import charts.tree.model.LineChartData;
import charts.tree.model.PointValue;
import charts.tree.model.ValueShape;
import charts.tree.model.Viewport;
import charts.tree.util.ChartUtils;
import charts.tree.view.LineChartView;

import com.SystemConstants;
import com.article.R;
import com.z.netUtil.HttpUtils;
import com.z.utils.JSONUtils;
import com.z.utils.LogUtils;

public class LineChartActivity extends BaseActivity {
	private LineChartView chart;
	private LineChartData data;
	List<Line> lines = new ArrayList<Line>();//保存线的个数
	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private boolean hasLines = true;
	private boolean hasPoints = true;
	private ValueShape shape = ValueShape.CIRCLE;
	private boolean isFilled = false;
	private boolean hasLabels = false;
	private boolean isCubic = false;
	private boolean hasLabelForSelected = false;
	private boolean pointsHaveDifferentColor;
	private boolean hasGradientToTransparent = false;
	private String TAG="LineChartActivity";
	private final int POINT_HANDLER = ++SystemConstants.handlerMessageBasis;
	private final int NET_ERROR = ++SystemConstants.handlerMessageBasis;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			dismissLoadingDialog();
			if(msg.what==POINT_HANDLER){
				if(msg.obj!=null){
					List<List<String>> strList = (List<List<String>>) msg.obj;
					drawLine(strList);
				}
			}else if(msg.what==NET_ERROR){
				Toast.makeText(LineChartActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	protected void onCreateView(Bundle savedInstanceState) {
		createView(R.layout.fragment_line_chart);
		chart = (LineChartView) findViewById(R.id.chart);
		chart.setOnValueTouchListener(new ValueTouchListener());

		// Disable viewport recalculations, see toggleCubic() method for more
		// info.
		chart.setViewportCalculationEnabled(false);
		hasAxes = true;
		hasAxesNames = true;
		hasLines = true;
		hasPoints = true;
		shape = ValueShape.CIRCLE;//形状
		isFilled = false;
		hasLabels = false; 
		isCubic = false;
		hasLabelForSelected = false;
		pointsHaveDifferentColor = false;
		resetViewport();
		chart.setValueSelectionEnabled(hasLabelForSelected);
		resetViewport();
		findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resetViewport();
				String url = SystemConstants.getUrl(true, SystemConstants.vibrate_url),dev ="1001";
				runDrawStarPoint(url,dev);
				
			}
		});
		
		//String url2 = "http://192.168.0.141:8001/EmfcRciWeb/MobileCtrl/vibrateDemo",dev ="1001";
		String url = SystemConstants.getUrl(true, SystemConstants.vibrate_url),dev ="1001";
		runDrawStarPoint(url,dev);
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
//		data.setBaseValue(Float.NEGATIVE_INFINITY);
//		chart.setLineChartData(data);
//		createLine(strList2, Color.BLUE);
//		data.setBaseValue(Float.NEGATIVE_INFINITY);
//		chart.setLineChartData(data);
	};
	
	private void drawLine(List<List<String>> strList){
		int i=0;
		for (List<String> list : strList) {
			createLine(list,ChartUtils.COLORS[i++]);
		}
		data.setBaseValue(Float.NEGATIVE_INFINITY);
		chart.setLineChartData(data);
	}
	
	private void createLine(List<String> strList,int color) {//[0.0,3.1257]
		
		List<PointValue> values = new ArrayList<PointValue>();
		for (String pointValue : strList) {//[0,3.0933]
			pointValue = pointValue.replace("[", "");
			pointValue = pointValue.replace("]", "");
			String[] split = pointValue.split(",");
			if(split!=null&&split.length>1){
				values.add(new PointValue(Float.valueOf(split[0]), Float.valueOf(split[1])));
			}
		}
		Line line = new Line(values);
		//设置线的属性
		line.setColor(color).setShape(shape).setCubic(isCubic).setFilled(isFilled).
			setHasLabels(hasLabels).setHasLabelsOnlyForSelected(hasLabelForSelected).
			setHasLines(hasLines).setHasPoints(hasPoints).setHasGradientToTransparent(hasGradientToTransparent);
		if (pointsHaveDifferentColor) {
			line.setPointColor(Color.BLACK);
//			line.setPointColor(Color.CYAN);
			// line.setPointColor(ChartUtils.COLORS[(i + 1) %
			// ChartUtils.COLORS.length]);
		}
		lines.add(line);//添加一组线
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
		chart.setLineChartData(data);
	};

	/**
	 * view复位
	 */
	private void resetViewport() {
		// Reset viewport height range to (0,100)
		Viewport v = new Viewport(chart.getMaximumViewport());// 设置最大最小值
		v.bottom = -65;//底部
		v.top = 65;//顶部
		v.left = -3f;//左
		v.right = 45f;//右
		chart.setMaximumViewport(v);//设置最大窗口
		chart.setCurrentViewport(v);
		lines.clear();//清除已添加的线
		chart.setLineChartData(null);//清除所显示的线
	}

	private void prepareDataAnimation() {
		for (Line line : data.getLines()) {
			for (PointValue value : line.getValues()) {
				// Here I modify target only for Y values but it is OK to modify
				// X targets as well.
				value.setTarget(value.getX(), (float) Math.random() * 100);
			}
		}
	}

	public void runDrawStarPoint(final String url, final String devId) {
		showLoadingDialog("加载中...");
		new Thread() {
			@Override
			public void run() {
				requestPoint(url, devId);
				super.run();
			}

		}.start();

	}

	private void requestPoint(String url, String devId) {
		String SERVER_URL = url;
		Map<String, String> data = new HashMap<String, String>();
		data.put("devId", devId);
		String json = JSONUtils.toJson(data);
		LogUtils.i(TAG, "url:" + url + " msg" + json);
		String httpClientPost;
		Message msg = new Message();//vibrate
		
		try {
			httpClientPost = HttpUtils.httpClientPost(SERVER_URL, json, 3000,
					3000);
			int code = JSONUtils.getInt(httpClientPost, "code", 400);
			if (code == 200) {
				String result = JSONUtils.getString(httpClientPost, "result",
						"");// sessionId
				LogUtils.e("result", result);
				String string = JSONUtils.getString(result, "psd", "");
				List<String> psds = JSONUtils.getStringList(result,"psd", null);
				List<String> vibrate = JSONUtils.getStringList(result,"vibrate", null);
				List<List<String>> list = new ArrayList<List<String>>();
				list.add(psds);
				list.add(vibrate);
				msg.obj = list;
				msg.what = POINT_HANDLER;
				handler.sendMessage(msg);
			} else {
				LogUtils.e(TAG, "http:code:" + url + " msg:" + httpClientPost);
			}
		} catch (Exception e) {
			msg.obj = e.toString();
			msg.what = NET_ERROR;
			handler.sendMessage(msg);
			LogUtils.e(TAG, "http:" + e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * A fragment containing a line chart.
	 */
	private class ValueTouchListener implements LineChartOnValueSelectListener {

		@Override
		public void onValueSelected(int lineIndex, int pointIndex,
				PointValue value) {
			Toast.makeText(LineChartActivity.this, "Selected: " + value,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onValueDeselected() {
			// TODO Auto-generated method stub

		}

	}
}
