package com.dgm.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dgm.bean.StationInfo;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.view.TitleBarView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class StationDetailFragment extends Fragment {
	protected static final String TAG = "MainActivity";
	private View mBaseView;
	private Context mContext;
	private PopupWindow mPopupWindow;
	private View mPopView;
	private TitleBarView mTitleBarView;
	private TextView detailTv,equipTv,alarmTv,mapTv;
	private ProgressDialog pd;
	private static PullToRefreshListView refresh_lv;
	private List<StationInfo> list;
	private TextView text;
	
	public static String stationId="";
	public static String fragflag="";

	public static String equipmentBackString="";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		stationId="";
		mBaseView = inflater.inflate(R.layout.frag_station_detail_page, null);
		mContext = getActivity();
		detailTv = (TextView) mBaseView.findViewById(R.id.detail);
		equipTv = (TextView) mBaseView.findViewById(R.id.equip_info);
		alarmTv = (TextView) mBaseView.findViewById(R.id.alarm_info);
		mapTv = (TextView) mBaseView.findViewById(R.id.map);
		if (getArguments() != null) {  
			stationId = getArguments().getString("stationId");
			fragflag = getArguments().getString("fragflag");
			equipmentBackString= getArguments().getString("backButton");
	    }
		
		Log.e("@@@@@@@@@@@@@@", stationId);
		detailTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				detailTv.setTextColor(Color.parseColor("#FFFFFF"));
				detailTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				equipTv.setTextColor(Color.parseColor("#000000"));
				equipTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				alarmTv.setTextColor(Color.parseColor("#000000"));
				alarmTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				mapTv.setTextColor(Color.parseColor("#000000"));
				mapTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				StationDetailInfoFragment detailFragment = new StationDetailInfoFragment();
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
					args.putString("alarmName", alarmNamee);
					args.putString("alartTime", alarmStartTime);
					args.putString("alarTimeEnd", alarmEndTime);
					args.putString("areaType", "1");
				}
				detailFragment.setArguments(args);
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});
	
		equipTv = (TextView) mBaseView.findViewById(R.id.equip_info);
		equipTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				equipTv.setTextColor(Color.parseColor("#FFFFFF"));
				equipTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				detailTv.setTextColor(Color.parseColor("#000000"));
				detailTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				alarmTv.setTextColor(Color.parseColor("#000000"));
				alarmTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				mapTv.setTextColor(Color.parseColor("#000000"));
				mapTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				StationEquipInfoFragment detailFragment = new StationEquipInfoFragment();
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
					args.putString("alarmName", alarmNamee);
					args.putString("alartTime", alarmStartTime);
					args.putString("alarTimeEnd", alarmEndTime);
					args.putString("areaType", "1");
				}
				detailFragment.setArguments(args);
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.commit();
			}
		});
		if(equipmentBackString.length()>0){
			equipTv.performClick();
		}else{
			detailTv.performClick();
		}
		alarmTv = (TextView) mBaseView.findViewById(R.id.alarm_info);
		alarmTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alarmTv.setTextColor(Color.parseColor("#FFFFFF"));
				alarmTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				detailTv.setTextColor(Color.parseColor("#000000"));
				detailTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				equipTv.setTextColor(Color.parseColor("#000000"));
				equipTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				mapTv.setTextColor(Color.parseColor("#000000"));
				mapTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				StationAlarmInfoFragment detailFragment = new StationAlarmInfoFragment();
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
					args.putString("alarmName", alarmNamee);
					args.putString("alartTime", alarmStartTime);
					args.putString("alarTimeEnd", alarmEndTime);
					args.putString("areaType", "1");
				}
				detailFragment.setArguments(args);
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});
		mapTv = (TextView) mBaseView.findViewById(R.id.map);
		mapTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mapTv.setTextColor(Color.parseColor("#FFFFFF"));
				mapTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				detailTv.setTextColor(Color.parseColor("#000000"));
				detailTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				equipTv.setTextColor(Color.parseColor("#000000"));
				equipTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				alarmTv.setTextColor(Color.parseColor("#000000"));
				alarmTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				StationMapFragment detailFragment = new StationMapFragment();
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
					args.putString("alarmName", alarmNamee);
					args.putString("alartTime", alarmStartTime);
					args.putString("alarTimeEnd", alarmEndTime);
					args.putString("areaType", "1");
				}
				detailFragment.setArguments(args);
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});
		return mBaseView;
	}
	@Override
	public void onResume() {
		super.onResume();
		getFocus();
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
