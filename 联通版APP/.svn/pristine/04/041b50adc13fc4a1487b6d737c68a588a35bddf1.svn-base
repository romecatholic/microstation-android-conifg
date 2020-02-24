package com.dgm.fragment;

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
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

public class EquipmentDetaileFragment extends Fragment {
	
	private View mBaseView;
	private TextView tv_detail_m,tv_detail_s,tv_detail_c;
	public static String s_stationId;
	public static String s_deviceId;
	public static String s_equipmentId;
	
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
		mBaseView = inflater.inflate(R.layout.frag_equipment_detail_page, null);
		tv_detail_m= (TextView) mBaseView.findViewById(R.id.detail_m);
		tv_detail_s= (TextView) mBaseView.findViewById(R.id.detail_s);
		tv_detail_c= (TextView) mBaseView.findViewById(R.id.detail_c);
		
		if (getArguments() != null) {  
			s_stationId = getArguments().getString("stationId"); 
			s_deviceId = getArguments().getString("deviceId"); 
			s_equipmentId = getArguments().getString("equipmentId"); 
	    }
		Log.e("EquipmentDetaileFragment", s_stationId);
		tv_detail_m.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tv_detail_m.setTextColor(Color.parseColor("#FFFFFF"));
				tv_detail_m.setBackgroundColor(Color.parseColor("#00BBFF"));
				tv_detail_s.setTextColor(Color.parseColor("#000000"));
				tv_detail_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
				tv_detail_c.setTextColor(Color.parseColor("#000000"));
				tv_detail_c.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				EquipmentMoDataFragment detailFragment = new EquipmentMoDataFragment();
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});
		tv_detail_m.performClick();
		tv_detail_s.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tv_detail_s.setTextColor(Color.parseColor("#FFFFFF"));
				tv_detail_s.setBackgroundColor(Color.parseColor("#00BBFF"));
				tv_detail_m.setTextColor(Color.parseColor("#000000"));
				tv_detail_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
				tv_detail_c.setTextColor(Color.parseColor("#000000"));
				tv_detail_c.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				EquipmentSwitchDataFragment detailFragment = new EquipmentSwitchDataFragment();
				ft.replace(R.id.fl_content, detailFragment, StationDetailFragment.TAG);
				ft.commit();
			}
		});
		
		tv_detail_c.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tv_detail_c.setTextColor(Color.parseColor("#FFFFFF"));
				tv_detail_c.setBackgroundColor(Color.parseColor("#00BBFF"));
				tv_detail_s.setTextColor(Color.parseColor("#000000"));
				tv_detail_s.setBackgroundColor(Color.parseColor("#FFFFFF"));
				tv_detail_m.setTextColor(Color.parseColor("#000000"));
				tv_detail_m.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				EquipmentControlDataFragment detailFragment = new EquipmentControlDataFragment();
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
						&& keyCode == KeyEvent.KEYCODE_BACK) {
					// 监听到返回按钮点击事件
					Fragment newContent = new StationDetailFragment();
					Bundle args = new Bundle();
					args.putString("stationId", s_stationId);
					args.putString("backButton", "backButton");
					newContent.setArguments(args);
					String title = "stationdetail";
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
