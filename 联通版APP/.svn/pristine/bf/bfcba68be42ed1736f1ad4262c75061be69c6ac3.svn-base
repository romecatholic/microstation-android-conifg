package com.dgm.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

public class StationSearchFragment extends Fragment {
	private View mBaseView;

	private DBManager dbMgr;
	private Context mContext;
	private Spinner spinner;
	private EditText et_stationName;
	private EditText area;
	private EditText et_stationStatus;
	private Button searchButton;

	private String areaName = "";
	private String[] items;
	private String[] items2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	boolean isSpinnerFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.frag_station_search_page, null);
		items2=new String[]{mContext.getResources().getString(R.string.normal),mContext.getResources().getString(R.string.cutoff),mContext.getResources().getString(R.string.alarm),mContext.getResources().getString(R.string.uninstall)};//"正常","掉线","告警","未安装"
		dbMgr = new DBManager(mContext);
		SharedPreferences shared = getActivity().getSharedPreferences("saveUserNamePwd", getActivity().MODE_PRIVATE);
		// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
		String userid = shared.getString("name", "");// 同上，若没找到就让它为空""
		areaName = dbMgr.queryUserArea(userid);
		area = (EditText) mBaseView.findViewById(R.id.selectarea);
		et_stationStatus = (EditText) mBaseView.findViewById(R.id.search_station_status);
		et_stationStatus.setInputType(InputType.TYPE_NULL); 
		area.setInputType(InputType.TYPE_NULL);
		area.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(area.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});
		et_stationStatus.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_stationStatus.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});
		et_stationStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//隐藏软键盘
				 InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				 manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				new AlertDialog.Builder(getActivity()).setTitle(mContext.getResources().getString(R.string.sear_stationstate))//选择基站状态
						.setItems(items2, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int i) {
								et_stationStatus.setText(items2[i]);
							}
						}).show();
			}
		});
		addlist();
		area.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//隐藏软键盘
				 InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				 manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				
				 new AlertDialog.Builder(getActivity()).setTitle(mContext.getResources().getString(R.string.sear_areasel))//选择区域
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int i) {
								area.setText(items[i]);
							}
						}).show();
			}
		});
		et_stationName = (EditText) mBaseView
				.findViewById(R.id.search_staiton_name);
		et_stationName.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_stationName.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});
		searchButton = (Button) mBaseView.findViewById(R.id.search_station);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String station_Name = et_stationName.getText().toString();
				String areaName=area.getText().toString();
				String stationStatus=et_stationStatus.getText().toString();
				Fragment newContent = new StationFragment();
				Bundle args = new Bundle();
				args.putString("stationName", station_Name);
				args.putString("areaName", areaName);
				args.putString("stationStatus", stationStatus);
				args.putString("areaType", "1");
				newContent.setArguments(args);
				String title = "station";
				switchFragment(newContent, title);
			}
		});

		return mBaseView;
	}

	@Override
	public void onResume() {
		super.onResume();
		getFocus();
	}
	private void regetFocus() {
		Fragment newContent = new StationFragment();
		String title = "station";
		switchFragment(newContent, title);
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
					Fragment newContent = new StationFragment();
					String title = "station";
					switchFragment(newContent, title);
					return true;// 未处理
				}
				return false;
			}
		});
	}

	private void addlist() {
		items = areaName.split(";");
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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
