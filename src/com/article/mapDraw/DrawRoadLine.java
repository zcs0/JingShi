package com.article.mapDraw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.SystemConstants;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.article.R;
import com.article.entity.PostionInfo;
import com.z.netUtil.HttpUtils;
import com.z.utils.JSONUtils;
import com.z.utils.LogUtils;
import com.z.utils.TimeUtil;
import com.z.utils.TimerUtil;

/**
 * @ClassName:     DrawRoad.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月16日 下午12:51:58 
 * @Description:   TODO(用一句话描述该文件做什么) 
 */
public class DrawRoadLine implements OnMarkerClickListener, InfoWindowAdapter {
	private final int ROAD_HANDLER = ++SystemConstants.handlerMessageBasis;
	private AMap aMap;
	Context mContext;
	long time;
	public DrawRoadLine(AMap aMap, Context mContext, long time) {
		this.aMap = aMap;
		this.mContext = mContext;
		this.time = time;
//		aMap.setOnMarkerClickListener(this);//设置点击锚点的事件
		aMap.setInfoWindowAdapter(this);//设置点锚点显示的信息
	}
	/**
	 * 开始绘制道路线
	 */
	boolean test = false;
	public void runDrawStar(String url,String devId){
		if(options==null){
			options = new PolylineOptions();
		}
		options.width(10);// 设置宽度
		if(test){
			String path = Environment.getExternalStorageDirectory()+"/postion.txt";
			File file = new File(path);
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine())!=null) {
					line = line.replace("\"", "");
					if(line!=null){
						String[] split = line.split(",");
//						System.out.println(split[0]+"   "+split[1]);
						options.add(new LatLng(Double.valueOf(split[0]), Double.valueOf(split[1])));
					}
				}
				setDrawOptions(options);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return;
		}
		isClose = false;
		lastTime =  -1;
		if(httpRequestThread==null){
			httpRequestThread = new HttpRequestThread(url, devId);
		}
		new Thread(httpRequestThread).start();
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(isClose)return;//已关闭
			if(msg.what==ROAD_HANDLER){
				List<PostionInfo> info =(List<PostionInfo>) msg.obj;
				addPolylinesWithColors(info);
				if(httpRequestThread!=null){
					TimerUtil.getInstance().schedule(new TimerTask() {
						
						@Override
						public void run() {
							new Thread(httpRequestThread).start();
							
						}
					}, time);
					
				}
			}
		};
	};
	private List<PostionInfo> listPoint;
	private String TAG = "DrawRoadLine";
	private HttpRequestThread httpRequestThread;
	private boolean isFirst = true;
	private Marker busMK;
	private Marker startMK;
	private boolean isClose;
	private long lastTime;
	private PolylineOptions options;
	
	class HttpRequestThread implements Runnable{
		private String url;
		private String devId;
		public HttpRequestThread(String url,String devId){
			this.url = url;
			this.devId=devId;
		}
		@Override
		public void run() {
			requestPostion(url, devId);
		}
	}
	private void requestPostion(String url,String devId) {
		if(isClose)return;//已关闭
		String SERVER_URL = url;
		Map<String, String> data = new HashMap<String, String>();
		data.put("devId", devId);
		String json = JSONUtils.toJson(data);
		LogUtils.i(TAG, "url:"+url+" msg"+json);
		String httpClientPost;
		try {
			httpClientPost = HttpUtils.httpClientPost(SERVER_URL, json,3000,3000);
			int code = JSONUtils.getInt(httpClientPost, "code", 400);
			if (code == 200) {
				String result = JSONUtils.getString(httpClientPost, "result","");// sessionId
				List<String> stringList = JSONUtils.getStringList(result,
						"rows", null);
				if (stringList != null) {
					List<PostionInfo> info = new ArrayList<PostionInfo>();
					for (int i = 0; i < stringList.size(); i++) {
						String rows = stringList.get(i);
						PostionInfo jsonToObj = JSONUtils.jsonToObj(rows,PostionInfo.class);
						info.add(jsonToObj);
					}
					Message msg = new Message();
					msg.obj = info;
					msg.what = ROAD_HANDLER;
					handler.sendMessage(msg);
				}else{
					LogUtils.w(TAG, "未请求到数据 url:"+url+" msg:"+json);
				}
			}else{
				LogUtils.e(TAG, "http:code:"+url+" msg:"+httpClientPost);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, "http:"+e.toString());
			e.printStackTrace();
		}

	}
	/**
	 * 多段颜色（非渐变色）
	 */
	private void addPolylinesWithColors(List<PostionInfo> info) {
		if(info==null||info.size()<=0)return;
		// 用一个数组来存放颜色，四个点对应三段颜色
		List<Integer> colorList = new ArrayList<Integer>();
		if(listPoint!=null){
//			aMap.clear(true);
		}
		listPoint = info;
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		// colorList.add(Color.BLACK);

		// 加入四个点
		for (int i = 0; i < info.size(); i++) {
			PostionInfo postionInfo = info.get(i);
			Date dateTime = TimeUtil.getDateTime(postionInfo.getTs());
			long time2 = dateTime.getTime();
			if(time2>=lastTime){
				lastTime = time2;
				options.add(new LatLng(postionInfo.getLat(), postionInfo.getLng()));
			}
		}
		setDrawOptions(options);
		PostionInfo postionInfo = info.get(info.size()-1);
		LatLng latLng = new LatLng(postionInfo.getLat(), postionInfo.getLng());
		PostionInfo postionInfo2 = info.get(0);
		if(isFirst){//第一次进入
			isFirst = false;
			startMK = addMarker(new LatLng(postionInfo2.getLat(), postionInfo2.getLng()),R.drawable.amap_start);
			busMK = addMarker(latLng,R.drawable.amap_bus);
			aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
			busMK.setTitle("测试");
		}else{
			aMap.getMapScreenMarkers();//所在Markers
			busMK.setPosition(latLng);
//			mLocMarker.setPosition(arg0);
		}
		busMK.setObject(postionInfo);
		
	}
	
	private void setDrawOptions(PolylineOptions options){
		// 加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
//		options.colorValues(colorList);
		options.color(Color.GREEN);
		aMap.addPolyline(options);
	}
	
	/**
	 * 添加Marker
	 */
	private Marker addMarker(LatLng latlng,int resourceId) {
//		if (mLocMarker != null) {
//			mLocMarker = aMap.addMarker(options);
//			return;
//		}
		Bitmap bMap = BitmapFactory.decodeResource(mContext.getResources(),
				resourceId);
		BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

		// BitmapDescriptor des = 
		// BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
		MarkerOptions options = new MarkerOptions();
		options.icon(des);
		options.anchor(0.5f, 0.5f);
		options.position(latlng);
		return aMap.addMarker(options);
//		mLocMarker.setTitle(LOCATION_MARKER_FLAG);
	}
	
	public void close(){
		this.isClose = true;
	}
	@Override
	public boolean onMarkerClick(Marker arg0) {
		if(arg0==busMK){
			if(arg0.equals(busMK)){
				busMK.setTitle("测试显示");
				PostionInfo info = (PostionInfo) busMK.getObject();
			}
		}
		return false;
	}
	@Override
	public View getInfoContents(final Marker arg0) {//提供了一个给默认信息窗口定制内容的方法。
		TextView tv = new TextView(mContext);
		tv.setText("测试测试\n测试");
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				arg0.hideInfoWindow();
			}
		});
		tv.setBackgroundColor(Color.BLUE);
		if(arg0.equals(busMK)){
			PostionInfo info = (PostionInfo) busMK.getObject();
			tv.setText(info.getDevIdTitle()+info.getDevId()+"\n"+info.getLatTitle()+info.getLat()+
					"\n"+info.getLngTitle()+info.getLng()+"\n"+info.getImpactTitle()+info.getImpact()+
					"\n"+info.getRhTitle()+info.getRh()+"\n"+info.getSpdTitle()+info.getSpd()+
					"\n"+info.getTempTitle()+info.getTemp()+"\n"+info.getVibrateTitle()+info.getVibrate()+
					"\n"+info.getTsTitle()+info.getTs()
					);
		}
		return tv;
	}
	@Override
	public View getInfoWindow(Marker arg0) {///提供了一个个性化定制信息窗口的marker对象。
		// TODO Auto-generated method stub
		return null;
	}

}
