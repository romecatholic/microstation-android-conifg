package com.dgm.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.view.TitleBarView;

public class DevicesScanFragment extends Fragment{
 protected static final String TAG = "DevicesInfo2Activity";
	private View mBaseView;
	private Context mContext;
	private PopupWindow mPopupWindow;
	private View mPopView;
	private TitleBarView mTitleBarView;
	private TextView commandTv,acTv,dcTv,rectifierTv,protocoTv;
	private ProgressDialog pd;
	private TextView text;
	private String devicesName;
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
		if (getArguments() != null) {
			devicesName = getArguments().getString("devicesName");
		}
		mBaseView = inflater.inflate(R.layout.devices_scan_shiyan, null);
		mContext = getActivity();
		commandTv = (TextView) mBaseView.findViewById(R.id.universal_command);
		acTv = (TextView) mBaseView.findViewById(R.id.ac_distribution);
		dcTv = (TextView) mBaseView.findViewById(R.id.dc_distribution);
		rectifierTv = (TextView) mBaseView.findViewById(R.id.rectifier_distribution);
		protocoTv = (TextView) mBaseView.findViewById(R.id.environment_protocol);

		commandTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				commandTv.setTextColor(Color.parseColor("#FFFFFF"));
				commandTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				UniversalCommandFragment umFragment = new UniversalCommandFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesScanFragment.TAG);
				ft.commit();
			}
		});
	
		acTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				acTv.setTextColor(Color.parseColor("#FFFFFF"));
				acTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				StationEquipInfoFragment detailFragment = new StationEquipInfoFragment();
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.commit();
			}
		});
		dcTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dcTv.setTextColor(Color.parseColor("#FFFFFF"));
				dcTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				StationAlarmInfoFragment detailFragment = new StationAlarmInfoFragment();
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});

		rectifierTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				rectifierTv.setTextColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				StationMapFragment detailFragment = new StationMapFragment();
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});
		
		protocoTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				protocoTv.setTextColor(Color.parseColor("#FFFFFF"));
				protocoTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				StationMapFragment detailFragment = new StationMapFragment();
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});
		return mBaseView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	//	getFocus();
	}

//	private void getFocus() {
//		getView().setFocusable(true);
//		getView().setFocusableInTouchMode(true);
//		getView().requestFocus();
//		getView().setOnKeyListener(new View.OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				if (event.getAction() == KeyEvent.ACTION_DOWN
//						&& keyCode == KeyEvent.KEYCODE_BACK) {
//					// 监听到返回按钮点击事件
//					Fragment newContent = new StationFragment();
//					String title = "station";
//					switchFragment(newContent, title);
//
//					return true;// 未处理
//				}
//				return false;
//			}
//		});
//	}
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
