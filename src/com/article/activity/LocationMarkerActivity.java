package com.article.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.SystemConstants;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.article.R;
import com.article.SensorEventHelper;
import com.article.mapDraw.DrawRoadLine;

/**
 * AMapV2地图中介绍自定义可旋转的定位图标
 */
public class LocationMarkerActivity extends Activity implements LocationSource,
		AMapLocationListener {
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	// private RadioGroup mGPSModeGroup;

	private TextView mLocationErrText;
	private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
	private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
	private boolean mFirstFix = false;
	private Marker mLocMarker;
	private SensorEventHelper mSensorHelper;
	private Circle mCircle;
	public static final String LOCATION_MARKER_FLAG = "mylocation";

	// 测试点
	private double Lat_A = 39.981167;
	private double Lon_A = 116.345103;

	private double Lat_B = 39.981265;
	private double Lon_B = 116.347152;

	private double Lat_C = 39.979387;
	private double Lon_C = 116.347356;

	private double Lat_D = 39.979313;
	private double Lon_D = 116.348896;
	private DrawRoadLine drawRoadLine;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
		setContentView(R.layout.locationmodesource_activity);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		addPolylineInPlayGround(new LatLng(39.904715, 116.443268));
		addPolylinesWithGradientColors();
		drawRoadLine = new DrawRoadLine(aMap, this, 3000);
		//String url="http://192.168.0.106:8001/EmfcRciWeb/MobileCtrl/findRealtimeData",devId="1001";
		String url = SystemConstants.getUrl(true, SystemConstants.car_position),devId="1001";
		drawRoadLine.runDrawStar(url,devId);
		
	}
	
	

	/**
	 * 初始化
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		mSensorHelper = new SensorEventHelper(this);
		if (mSensorHelper != null) {
			mSensorHelper.registerSensorListener();
		}
		mLocationErrText = (TextView) findViewById(R.id.location_errInfo_text);
		mLocationErrText.setVisibility(View.VISIBLE);

		aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {

			@Override
			public void onCameraChangeFinish(CameraPosition arg0) {//
				// 在可视范围一系列动作改变完成之后（例如拖动、fling、缩放）回调此方法。此方法的调用是在主线程中。
				System.out.println("onCameraChangeFinish++++++++++" + arg0.zoom
						+ "   ");
				Projection projection = aMap.getProjection();

				/*
				 * TileProjection fromBoundsToTile(LatLngBounds lb, int zoom,
				 * int width) 返回按照墨卡托投影切块后的矩形块信息。 LatLng
				 * fromScreenLocation(Point paramPoint) 根据转入的屏幕位置返回一个地图位置（经纬度）。
				 * LatLngBounds getMapBounds(LatLng center, float zoom)
				 * 根据中心点和zoom级别获取地图控件对应的目标区域 VisibleRegion getVisibleRegion()
				 * 返回一个在屏幕坐标与地理经纬度坐标之间锥形视图的映射。 PointF toMapLocation(LatLng
				 * paramLatLng) 已过时。 PointF toOpenGLLocation(LatLng paramLatLng)
				 * 返回一个从经纬度坐标转换来的opengles 需要的坐标。 float toOpenGLWidth(int
				 * screenWidth) 返回一个屏幕宽度转换来的openGL 需要的宽度。 Point
				 * toScreenLocation(LatLng paramLatLng) 返回一个从地图位置转换来的屏幕位置。
				 */

				LatLng fromScreenLocation = projection
						.fromScreenLocation(new Point(0, 0));

				System.out.println("latitude " + fromScreenLocation.latitude
						+ "   long " + fromScreenLocation.longitude);
				LatLng loc = projection.fromScreenLocation(new Point(mapView
						.getWidth(), mapView.getHeight()));
				System.out.println("width = " + mapView.getWidth()
						+ "    height= " + mapView.getHeight());
				System.out.println("--------latitude " + loc.latitude
						+ "   long " + loc.longitude);
				mapView.getHeight();

			}

			@Override
			public void onCameraChange(CameraPosition arg0) {
				// 在可视范围改变完成之后回调此方法。此方法的调用是在主线程中。

				System.out.println("onCameraChange++++++++++" + arg0.zoom);

			}
		});
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}

	// 操场线
	public void addPolylineInPlayGround(LatLng centerpoint) {
		double r = 6371000.79;
		int r1 = 50;
		PolylineOptions options = new PolylineOptions();
		int numpoints = 36;
		double phase = 2 * Math.PI / numpoints;
		// 颜色数组
		List<Integer> colorList = new ArrayList<Integer>();
		int[] colors = new int[] { Color.argb(255, 0, 255, 0),
				Color.argb(255, 255, 255, 0), Color.argb(255, 255, 0, 0) };
		Random random = new Random();
		// 画图
		for (int i = 0; i < numpoints; i++) {
			double dx = (r1 * Math.cos(i * phase));
			double dy = (r1 * Math.sin(i * phase)) * 1.6;// 乘以1.6 椭圆比例

			double dlng = dx
					/ (r * Math.cos(centerpoint.latitude * Math.PI / 180)
							* Math.PI / 180);
			double dlat = dy / (r * Math.PI / 180);
			double newlng = centerpoint.longitude + dlng;

			// 跑道两边为直线
			if (newlng < centerpoint.longitude - 0.00046) {
				newlng = centerpoint.longitude - 0.00046;
			} else if (newlng > centerpoint.longitude + 0.00046) {
				newlng = centerpoint.longitude + 0.00046;
			}
			options.add(new LatLng(centerpoint.latitude + dlat, newlng));
		}

		// 随机颜色赋值
		for (int i = 0; i < numpoints; i = i + 2) {
			int color = colors[random.nextInt(3)];
			colorList.add(color);// 添加颜色
			colorList.add(color);
		}

		// 确保首位相接，添加后一个点及颜色与第一点相同
		options.add(options.getPoints().get(0));
		colorList.add(colorList.get(0));

		List<Integer> colorListnew = new ArrayList<Integer>();
		colorListnew.add(Color.RED);
		colorListnew.add(Color.YELLOW);
		colorListnew.add(Color.GREEN);
		aMap.addPolyline(options.width(15).colorValues(colorList)
				.useGradient(true));
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerpoint, 17));

	}

	/**
	 * 多段颜色（渐变色）
	 */
	private void addPolylinesWithGradientColors() {
		// 四个点
		LatLng A = new LatLng(Lat_A + 0.0004, Lon_A + 0.0004);
		LatLng B = new LatLng(Lat_B + 0.0004, Lon_B + 0.0004);
		LatLng C = new LatLng(Lat_C + 0.0004, Lon_C + 0.0004);
		LatLng D = new LatLng(Lat_D + 0.0004, Lon_D + 0.0004);
		LatLng E = new LatLng(39.904729, 116.44334);

		// 用一个数组来存放颜色，渐变色，四个点需要设置四个颜色
		List<Integer> colorList = new ArrayList<Integer>();
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.BLACK);// 如果第四个颜色不添加，那么最后一段将显示上一段的颜色
		colorList.add(Color.YELLOW);
		PolylineOptions options = new PolylineOptions();
		options.width(15);// 设置宽度

		// 加入四个点
		options.add(A, B, C, D, E);

		// 加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
		options.colorValues(colorList);

		// 加上这个属性，表示使用渐变线
		options.useGradient(true);// 使用颜色过度

		aMap.addPolyline(options);
	}

	/**
	 * 多段颜色（非渐变色）
	 */
	private void addPolylinesWithColors() {
		// 四个点
		LatLng A = new LatLng(Lat_A + 0.0001, Lon_A + 0.0001);
		LatLng B = new LatLng(Lat_B + 0.0001, Lon_B + 0.0001);
		LatLng C = new LatLng(Lat_C + 0.0001, Lon_C + 0.0001);
		LatLng D = new LatLng(Lat_D + 0.0001, Lon_D + 0.0001);

		// 用一个数组来存放颜色，四个点对应三段颜色
		List<Integer> colorList = new ArrayList<Integer>();
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		// colorList.add(Color.BLACK);

		PolylineOptions options = new PolylineOptions();
		options.width(20);// 设置宽度

		// 加入四个点
		options.add(A, B, C, D);

		// 加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
		options.colorValues(colorList);

		aMap.addPolyline(options);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
		if (mSensorHelper != null) {
			mSensorHelper.registerSensorListener();
		} else {
			mSensorHelper = new SensorEventHelper(this);
			if (mSensorHelper != null) {
				mSensorHelper.registerSensorListener();

				if (mSensorHelper.getCurrentMarker() == null
						&& mLocMarker != null) {
					mSensorHelper.setCurrentMarker(mLocMarker);
				}
			}
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (mSensorHelper != null) {
			mSensorHelper.unRegisterSensorListener();
			mSensorHelper.setCurrentMarker(null);
			mSensorHelper = null;
		}
		mapView.onPause();
		deactivate();
		mFirstFix = false;
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		if(null!=drawRoadLine){
			drawRoadLine.close();
		}
		if (null != mlocationClient) {
			mlocationClient.onDestroy();
		}
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				String locationInfo = "当前位置：\n lat: "
						+ amapLocation.getLatitude() + "  long:"
						+ amapLocation.getLongitude() + "\n"
						+ amapLocation.getAddress() + "\n"
						+ amapLocation.getAoiName();
				mLocationErrText.setText(locationInfo);
				// System.out.println(locationInfo);
				mLocationErrText.setVisibility(View.VISIBLE);
				LatLng location = new LatLng(amapLocation.getLatitude(),
						amapLocation.getLongitude());
				if (!mFirstFix) {
					mFirstFix = true;
					addCircle(location, amapLocation.getAccuracy());// 添加定位精度圆
					addMarker(location);// 添加定位图标
					mSensorHelper.setCurrentMarker(mLocMarker);// 定位图标旋转
				} else {
					mCircle.setCenter(location);
					mCircle.setRadius(amapLocation.getAccuracy());
					mLocMarker.setPosition(location);
				}
				// aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,
				// 18));//设置地图显示位置和大小
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode() + ": "
						+ amapLocation.getErrorInfo();
				Log.e("AmapErr", errText);
				mLocationErrText.setVisibility(View.VISIBLE);
				mLocationErrText.setText(errText);
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			// 设置定位监听
			mlocationClient.setLocationListener(this);
			// 设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	/**
	 * 添加Circle
	 * 
	 * @param latlng
	 *            坐标
	 * @param radius
	 *            半径
	 */
	private void addCircle(LatLng latlng, double radius) {
		CircleOptions options = new CircleOptions();
		options.strokeWidth(1f);
		options.fillColor(FILL_COLOR);
		options.strokeColor(STROKE_COLOR);
		options.center(latlng);
		options.radius(radius);
		mCircle = aMap.addCircle(options);
	}

	/**
	 * 添加Marker
	 */
	private void addMarker(LatLng latlng) {
		if (mLocMarker != null) {
			return;
		}
		Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.navi_map_gps_locked);
		BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

		// BitmapDescriptor des =
		// BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
		MarkerOptions options = new MarkerOptions();
		options.icon(des);
		options.anchor(0.5f, 0.5f);
		options.position(latlng);
		mLocMarker = aMap.addMarker(options);
		mLocMarker.setTitle(LOCATION_MARKER_FLAG);
		TextView tv = new TextView(this);
		tv.setText("测试测试\n测试");
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("测试测试\n测试");
				
			}
		});
//		BitmapDescriptor fromView = BitmapDescriptorFactory.fromView(tv);
//		mLocMarker.showInfoWindow();
//		mLocMarker.hideInfoWindow();
//		mLocMarker.setIcon(fromView);
	}

}
