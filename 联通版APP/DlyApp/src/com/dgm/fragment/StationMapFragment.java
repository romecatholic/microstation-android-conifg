package com.dgm.fragment;

import java.io.File;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

public class StationMapFragment extends Fragment {
	private TextureMapView mMapView;
	BaiduMap mBaidumap;
	private double lng, lat, lng_now, lat_now;
	private SensorManager mSensorManager;
	private LocationMode mCurrentMode;
	LocationClient mLocClient;
	private int mCurrentDirection = 0;
	private double mCurrentLat = 0.0;
	private double mCurrentLon = 0.0;
	private float mCurrentAccracy;

	public MyLocationListenner myListener = new MyLocationListenner();
	boolean isFirstLoc = true; // 是否首次定位
	private MyLocationData locData;
	private float direction;
	private Button button;
	public static String fragflag="";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragflag = StationDetailFragment.fragflag;
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getActivity().getApplicationContext());
		View view = inflater.inflate(R.layout.frag_map, container, false);
		mMapView = (TextureMapView) view.findViewById(R.id.bmapView);
		mMapView.setVisibility(View.INVISIBLE);
		button = (Button) view.findViewById(R.id.navigation);
		button.setText(getResources().getString(R.string.guide));//导航
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Boolean BaiduMapAPP = new File("/data/data/"
						+ "com.baidu.BaiduMap").exists();
				if (BaiduMapAPP == true) {
					startNavi();
				} else {
					startWebNavi();
				}
			}
		});
		mBaidumap = mMapView.getMap();
		lng = Double.parseDouble(StationDetailInfoFragment.lng);
		lat = Double.parseDouble(StationDetailInfoFragment.lat);
		if (lng == 0.0) {
			Toast toast = Toast.makeText(getActivity().getApplicationContext(),
					getResources().getString(R.string.sm_Toast), Toast.LENGTH_LONG);//定位解析出错,请核准基站经纬度！
			// 显示toast信息
			toast.show();

		}
		mSensorManager = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);// 获取传感器管理服务
		mCurrentMode = LocationMode.NORMAL;
		mBaidumap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		LatLng cenpt = new LatLng(lat, lng);
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.marker);
		MarkerOptions markerOptions = new MarkerOptions().icon(bitmap)
				.position(cenpt);
		// 获取添加的 marker 这样便于后续的操作
		Marker marker = (Marker) mBaidumap.addOverlay(markerOptions);
		MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(16)
				.build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaidumap.setMapStatus(mMapStatusUpdate);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
				mMapView.setVisibility(View.VISIBLE);

	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}
			mCurrentLat = location.getLatitude();
			mCurrentLon = location.getLongitude();
			mCurrentAccracy = location.getRadius();
			locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(mCurrentDirection)
					.latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaidumap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				lng_now = location.getLongitude();
				lat_now = location.getLatitude();

			}
		}

		@Override
		public void onConnectHotSpotMessage(String arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * 启动百度地图导航(Native)
	 */
	public void startNavi() {
		LatLng pt1 = new LatLng(lat_now, lng_now);
		LatLng pt2 = new LatLng(lat, lng);

		// 构建 导航参数
		NaviParaOption para = new NaviParaOption().startPoint(pt1)
				.endPoint(pt2).startName(getResources().getString(R.string.startpoint)).endName(getResources().getString(R.string.endpoint));//起点	终点	

		try {
			BaiduMapNavigation.openBaiduMapNavi(para, getActivity());
		} catch (BaiduMapAppNotSupportNaviException e) {
			e.printStackTrace();
			// showDialog();
		}

	}

	/**
	 * 启动百度地图导航(Web)
	 */
	public void startWebNavi() {
		LatLng pt1 = new LatLng(lat_now, lng_now);
		LatLng pt2 = new LatLng(lat, lng);
		// 构建 导航参数
		NaviParaOption para = new NaviParaOption().startPoint(pt1)
				.endPoint(pt2);

		BaiduMapNavigation.openWebBaiduMapNavi(para, getActivity());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();

	}

	@Override
	public void onResume() {
		super.onResume();
		getFocus();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
	//	mMapView.setVisibility(View.INVISIBLE);    
		mMapView.onPause();

	}
	private void getFocus() {
		getView().setFocusable(true);
		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_BACK&&fragflag==null) {
					System.out.println("ddd"+fragflag);
					// 监听到返回按钮点击事件
					Fragment newContent = new StationFragment();
					String title = "station";
					switchFragment(newContent, title);

					return true;// 未处理
				}else if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_BACK&&fragflag.equals("alarm")) {
					// 监听到返回按钮点击事件
					Fragment newContent = new AlarmFragment();
					String title = "alarmYes";
					Bundle args = new Bundle();
					if (getArguments() != null) {
						String station_Name=getArguments().getString("stationName");
						String areaName=getArguments().getString("areaName");
						String alarmType=getArguments().getString("alarmType");
						String alarmStartTime=getArguments().getString("alarmStartTime");
						String alarmNamee=getArguments().getString("alarmName");
						String alarmEndTime=getArguments().getString("alarmEndTime");
						args.putString("stationName", station_Name);  
						args.putString("areaName", areaName);
						args.putString("alarmType", alarmType);
						args.putString("alartTime", alarmStartTime);
						args.putString("alarmName", alarmNamee);
						args.putString("alarTimeEnd", alarmEndTime);
						args.putString("areaType", "1");
						
					}
					newContent.setArguments(args);
					switchFragment(newContent, title);

					return true;// 未处理
				}
				return false;
			}
		});
	}
	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchConent(fragment, title);
		}
	}
}
