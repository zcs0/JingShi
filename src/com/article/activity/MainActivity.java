package com.article.activity;


import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.Constants;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.article.IFragment;
import com.article.R;
import com.z.utils.LogUtils;

public class MainActivity extends BaseActivity implements OnSeekBarChangeListener{
	private static final int WIDTH_MAX = 50;
	private static final int HUE_MAX = 255;
	private static final int ALPHA_MAX = 255;
	public static final String TAG = null;

	private AMap aMap;
	private MapView mapView;
	private Polyline polyline;
	private SeekBar mColorBar;
	private SeekBar mAlphaBar;
	private SeekBar mWidthBar;
	
	/*
	 * 为方便展示多线段纹理颜色等示例事先准备好的经纬度
	 */
	private double Lat_A = 35.909736;
	private double Lon_A = 80.947266;
	
	private double Lat_B = 35.909736;
	private double Lon_B = 89.947266;
	
	private double Lat_C = 31.909736;
	private double Lon_C = 89.947266;
	
	private double Lat_D = 31.909736;
	private double Lon_D = 99.947266;
	private FragmentManager fraM;
	private TextView tv_pos;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private boolean mFirstFix;
	private Circle mCircle;

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        // 隐去标题栏（应用程序的名字）
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fraM = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        tv_pos = findViewById2(R.id.txt_pos);
        
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
	    //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
//        MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		
		addPolylinessoild();//画实线
		addPolylinesdotted();//画虚线
		addPolylinesWithTexture();//画纹理线
//		AMap map = mapView.getMap();
		tv_pos.setText("定位");
    }
    
    /**
	 * 初始化AMap对象
	 */
	private void init() {
		mColorBar = (SeekBar) findViewById(R.id.hueSeekBar);
		mColorBar.setMax(HUE_MAX);
		mColorBar.setProgress(50);

		mAlphaBar = (SeekBar) findViewById(R.id.alphaSeekBar);
		mAlphaBar.setMax(ALPHA_MAX);
		mAlphaBar.setProgress(255);

		mWidthBar = (SeekBar) findViewById(R.id.widthSeekBar);
		mWidthBar.setMax(WIDTH_MAX);
		mWidthBar.setProgress(10);
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}

	private void setUpMap() {
		mColorBar.setOnSeekBarChangeListener(this);
		mAlphaBar.setOnSeekBarChangeListener(this);
		mWidthBar.setOnSeekBarChangeListener(this);
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.300299, 106.347656), 4));
		aMap.setMapTextZIndex(2);
		
	}
	//绘制一条实线
	private void addPolylinessoild() {
		LatLng A = new LatLng(Lat_A, Lon_A);
		LatLng B = new LatLng(Lat_B, Lon_B);
	    LatLng C = new LatLng(Lat_C, Lon_C);
		LatLng D = new LatLng(Lat_D, Lon_D);
		aMap.addPolyline((new PolylineOptions())
				.add(A, B, C, D)
				.width(10)
				.color(Color.argb(255, 1, 1, 1)));
	}
	// 绘制一条虚线
	private void addPolylinesdotted() {
		polyline = aMap.addPolyline((new PolylineOptions())
				.add(Constants.SHANGHAI, Constants.BEIJING, Constants.CHENGDU)
				.width(10)
				.setDottedLine(true)//设置虚线
				.color(Color.argb(255, 1, 1, 1)));
	}
	//绘制一条纹理线
	private void addPolylinesWithTexture() {
		//四个点
		LatLng A = new LatLng(Lat_A+1, Lon_A+1);
		LatLng B = new LatLng(Lat_B+1, Lon_B+1);
		LatLng C = new LatLng(Lat_C+1, Lon_C+1);
		LatLng D = new LatLng(Lat_D+1, Lon_D+1);
		
		//用一个数组来存放纹理
		List<BitmapDescriptor> texTuresList = new ArrayList<BitmapDescriptor>();
		texTuresList.add(BitmapDescriptorFactory.fromResource(R.drawable.map_alr));
		texTuresList.add(BitmapDescriptorFactory.fromResource(R.drawable.custtexture));
		texTuresList.add(BitmapDescriptorFactory.fromResource(R.drawable.map_alr_night));
		
		//指定某一段用某个纹理，对应texTuresList的index即可, 四个点对应三段颜色
		List<Integer> texIndexList = new ArrayList<Integer>();
		texIndexList.add(0);//对应上面的第0个纹理
		texIndexList.add(2);
		texIndexList.add(1);
		
		
		PolylineOptions options = new PolylineOptions();
		options.width(20);//设置宽度
		
		//加入四个点
		options.add(A,B,C,D);
		
		//加入对应的颜色,使用setCustomTextureList 即表示使用多纹理；
		options.setCustomTextureList(texTuresList);
		
		//设置纹理对应的Index
		options.setCustomTextureIndex(texIndexList);
		
		aMap.addPolyline(options);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
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
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	/**
	 * Polyline中对填充颜色，透明度，画笔宽度设置响应事件
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (polyline == null) {
			return;
		}
		if (seekBar == mColorBar) {
			polyline.setColor(Color.argb(progress, 1, 1, 1));
		} else if (seekBar == mAlphaBar) {
			float[] prevHSV = new float[3];
			Color.colorToHSV(polyline.getColor(), prevHSV);
			polyline.setColor(Color.HSVToColor(progress, prevHSV));
		} else if (seekBar == mWidthBar) {
			polyline.setWidth(progress);
		}
	}



    /**
     * 监听Fragment的显示和跳转
     *
     * @author Administrator
     */
    class onFragmentBackListener implements OnBackStackChangedListener {

        @Override
        public void onBackStackChanged() {
            Log.i(TAG, "onBackStackChanged");
            try {
                if (fraM != null) {
                    List<Fragment> fragments = fraM.getFragments();
                    int backStackEntryCount = fraM.getBackStackEntryCount();
                    /*
                    IFragment fragment2 = (IFragment) fraM
                            .findFragmentById(R.id.ll_marker_data);
                    if (fragment2 == null || backStackEntryCount == 0) {
                        ScrollDrawerLayout scrool = (ScrollDrawerLayout) findViewById(R.id.drawer_scrool);// Marker显示的View
                        scrool.removeAllView();
                        if (scrool.getState() != MoveLocation.hide) {
                            scrool.moveLocation(MoveLocation.hide);
                        }
                    }
                    if (backStackEntryCount > 0) {
                        BackStackEntry backStackEntryAt = fraM.getBackStackEntryAt(backStackEntryCount - 1);
                        String name = backStackEntryAt.getName();
                        Fragment fragment = fraM.findFragmentByTag(name);
                        if (fragment != null && fragment instanceof IFragment) {
                            IFragment iFragment = (IFragment) fragment;
                            iFragment.onBackPressed(iFragment.isVisible(),
                                    iFragment.isAdded(), false);
                        }
                    }
                    */
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

   

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.d(TAG, "物理返回键   " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK
                && fraM != null && fraM.getBackStackEntryCount() > 0) {
            BackStackEntry backStackEntryAt = fraM.getBackStackEntryAt(fraM
                    .getBackStackEntryCount() - 1);
            String name = backStackEntryAt.getName();
            Fragment fragment = fraM.findFragmentByTag(name);
            if (fragment != null && fragment instanceof IFragment) {
                IFragment iFragment = (IFragment) fragment;
                if (iFragment.onBackPressed(iFragment.isVisible(),
                        iFragment.isAdded(), true)
                        && iFragment.isVisible()) {
                    return false;// 拦截
                } else {
                    return super.onKeyDown(keyCode, event);
                }
            } else {
                return super.onKeyDown(keyCode, event);
            }

        }
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return quit();
//        }
        return super.onKeyDown(keyCode, event);
    }




}
