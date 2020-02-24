package com.dgm.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgm.bean.AlarmInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;

public class AlarmDetailFragment extends Fragment {
	private View mBaseView, loginBiew;
	private Context mContext;
	private ProgressDialog pd;
	private TextView tv_stationName;
	private TextView tv_area;
	private TextView tv_alarmName;
	private TextView tv_alarmClass;
	private TextView tv_alarmEquip;
	private TextView tv_alarmTime;
	public static String alarmID= "";
	private List<AlarmInfo> oneTotal = new ArrayList<AlarmInfo>();
	private static final String IP = "http://192.168.10.218:8080/DlyAppSever/AlarmDetailServlet";
	private DBManager dbMgr;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
					Fragment newContent = new AlarmFragment();
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
						System.out
						.println("ddddddddddddddaaaaaaaaaaaaaaaaa"+station_Name);
					}
					newContent.setArguments(args);
					String title = "alarmYes";
					switchFragment(newContent, title);

					return true;// 未处理
				}
				return false;
			}
		});
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		mBaseView = inflater.inflate(R.layout.alarm_detail_fragment, null);
		dbMgr = new DBManager(getActivity());

		if (getArguments() != null) {
			alarmID = getArguments().getString("alarmid");
		}
		init();
		mContext = getActivity();
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//数据正在加载中...
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		new LoadDataAsyncTask(AlarmDetailFragment.this).execute();
		return mBaseView;
	}

	public void init() {
		tv_stationName = (TextView) mBaseView
				.findViewById(R.id.staitonname_alarm_de);
		tv_area = (TextView) mBaseView.findViewById(R.id.staitonarea_alarm_de);
		tv_alarmName = (TextView) mBaseView.findViewById(R.id.alarm_name_de);
		tv_alarmClass = (TextView) mBaseView.findViewById(R.id.alarm_class_de);
		tv_alarmEquip = (TextView) mBaseView.findViewById(R.id.alarm_equip_de);
		tv_alarmTime = (TextView) mBaseView.findViewById(R.id.alarm_time_de);
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

	/**
	 * 
	 * @author Administrator
	 * 
	 */
	private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {
		private AlarmDetailFragment SDIfragment;

		public LoadDataAsyncTask(AlarmDetailFragment alarmStationFragment) {
			this.SDIfragment = alarmStationFragment;
		}

		@Override
		protected String doInBackground(Void... arg0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			list.add(new BasicNameValuePair("alarmID", alarmID));
			
			String IP = "http://";

			String IPPORT = dbMgr.queryInterface();
			String url = IP+ IPPORT+"/DlyAppSever/AlarmDetailServlet";
			String backString = (String) HttpReq.topostData(url, list);
			Log.e("AlarmInfo", backString);
			return backString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				List<AlarmInfo> list = JSONAnalysis(result);
				pd.dismiss();
				if (list != null) {
					if (list.size() != 0) {
						AlarmInfo sdi = list.get(0);
						tv_stationName.setText(sdi.getStationName());
						tv_area.setText(sdi.getArea());
						tv_alarmName.setText(sdi.getAlarmName());
						tv_alarmClass.setText(sdi.getAlarmType());
						tv_alarmEquip.setText(sdi.getEquipmentName());
						tv_alarmTime.setText(sdi.getAlarmTime());

					}
				}

			}
		}
	}

	public List<AlarmInfo> JSONAnalysis(String result) {
		oneTotal.clear();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String backMsg = jsonObject.getString("msg");
			String code = jsonObject.getString("code");
			if (code.equals("3")) {

			} else {
				if (backMsg.compareTo("success") == 0) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObjectSon = (JSONObject) jsonArray
								.opt(i);
						String stationName = jsonObjectSon
								.getString("stationName");
						String alarmID = jsonObjectSon.getString("alarmID");
						String alarmName = jsonObjectSon.getString("alarmName");
						String alarmType = jsonObjectSon.getString("alarmType");
						String deviceName = jsonObjectSon
								.getString("deviceName");
						String areaName = jsonObjectSon.getString("areaName");
						String sdate = jsonObjectSon.getString("sdate");

						AlarmInfo sdi = new AlarmInfo();
						sdi.setAlarmId(alarmID);
						sdi.setAlarmName(alarmName);
						sdi.setAlarmTime(sdate);
						sdi.setAlarmType(alarmType);
						sdi.setArea(areaName);
						sdi.setEquipmentName(deviceName);
						sdi.setStationName(stationName);
						oneTotal.add(sdi);

					}
					return oneTotal;
				} else {
					return null;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}