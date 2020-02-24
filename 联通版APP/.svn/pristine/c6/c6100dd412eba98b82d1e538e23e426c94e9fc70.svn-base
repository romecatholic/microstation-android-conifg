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

import com.dgm.bean.StationDetaileInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;

public class StationDetailInfoFragment extends Fragment {

	protected static final String TAG = "MainActivity";
	private View mBaseView;
	private Context mContext;
	private ProgressDialog pd;
	private List<StationDetaileInfo> oneTotal = new ArrayList<StationDetaileInfo>();
//	private static final String IP = "http://192.168.10.218:8080/DlyAppSever/StationDetaileServlet";
	private DBManager dbMgr;
	private TextView tv_stationName;
	private TextView tv_stationCode;
	private TextView tv_stationSite;
	private TextView tv_stationArea;
	private TextView tv_stationCompany;
	private TextView tv_stationLng;
	private TextView tv_stationLat;
	private TextView tv_stationType;
	private TextView tv_stationStatus;
	private TextView tv_stationTime;
	public static String lng = "";
	public static String lat = "";
	public static String fragflag="";
	public static String stationId = "";

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
		stationId = StationDetailFragment.stationId;
		fragflag = StationDetailFragment.fragflag;
		mBaseView = inflater.inflate(R.layout.station_detail_page, null);
		mContext = getActivity();
		init();
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//"数据正在加载中..."
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		new LoadDataAsyncTask(StationDetailInfoFragment.this).execute();
		return mBaseView;
	}

	/**
	 * 
	 */

	public void init() {
		tv_stationName = (TextView) mBaseView
				.findViewById(R.id.staitonname_detail);
		tv_stationCode = (TextView) mBaseView
				.findViewById(R.id.staitoncode_detail);
		tv_stationSite = (TextView) mBaseView
				.findViewById(R.id.staitonadress_detail);
		tv_stationArea = (TextView) mBaseView
				.findViewById(R.id.staitonarea_detail);
		tv_stationCompany = (TextView) mBaseView
				.findViewById(R.id.staitoncompany_detail);
		tv_stationLng = (TextView) mBaseView
				.findViewById(R.id.staitonlang_detail);
		tv_stationLat = (TextView) mBaseView
				.findViewById(R.id.staitonlei_detail);
		tv_stationStatus = (TextView) mBaseView
				.findViewById(R.id.staitonrun_detail);
		tv_stationTime = (TextView) mBaseView
				.findViewById(R.id.staitontime_detail);
		tv_stationType = (TextView) mBaseView
				.findViewById(R.id.staitonclass_detail);
	}

	/**
	 * 
	 * @author Administrator
	 * 
	 */
	private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {
		private StationDetailInfoFragment SDIfragment;

		public LoadDataAsyncTask(StationDetailInfoFragment sdif) {
			this.SDIfragment = sdif;
		}

		@Override
		protected String doInBackground(Void... arg0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			list.add(new BasicNameValuePair("stationID", stationId));
			Log.e("StationDetailInfoFragment", stationId);
			String IP = "http://";
			dbMgr = new DBManager(getActivity());
			String IPPORT = dbMgr.queryInterface();
			String url = IP+ IPPORT+"/DlyAppSever/StationDetaileServlet";
			String backString = (String) HttpReq.topostData(url, list);
			Log.e("StationDetailInfoFragment", backString);
			return backString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				List<StationDetaileInfo> list = JSONAnalysis(result);
				pd.dismiss();
				if(list!=null){
					if (list.size() != 0) {
						StationDetaileInfo sdi = list.get(0);
						tv_stationName.setText(sdi.getStationName());
						tv_stationCode.setText(sdi.getStationCode());
						tv_stationSite.setText(sdi.getStationAddress());
						tv_stationArea.setText(sdi.getStationArea());
						tv_stationCompany.setText(sdi.getStationCompany());
						tv_stationLng.setText(sdi.getStationLng());
						tv_stationLat.setText(sdi.getStationLat());
						if (sdi.getStationLng().length()!=0
								&& sdi.getStationLat().length()!=0) {
							lng = sdi.getStationLng();
							lat = sdi.getStationLat();
						} else {
							lng = "0.0";
							lat = "0.0";
						}
						tv_stationStatus.setText(sdi.getStationStatus());
						if (sdi.getStationStatus().compareTo(getResources().getString(R.string.cutoff)) == 0) {//掉线
							tv_stationStatus.setTextColor(0xffFF0000);
						} else if (sdi.getStationStatus().compareTo(getResources().getString(R.string.normal)) == 0) {//正常
							tv_stationStatus.setTextColor(0xff01B468);
						} else {
							tv_stationStatus.setTextColor(0xffFF0000);
						}

						tv_stationTime.setText(sdi.getStationCreatTime());
						tv_stationType.setText(sdi.getStationType());
					}
				}

			}
		}
	}

	public List<StationDetaileInfo> JSONAnalysis(String result) {
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
						String stationArea = jsonObjectSon
								.getString("stationArea");
						String stationName = jsonObjectSon
								.getString("stationName");
						String stationCode = jsonObjectSon
								.getString("stationCode");
						String stationAddress = jsonObjectSon
								.getString("stationAddress");
						String stationCompany = jsonObjectSon
								.getString("stationCompany");
						String stationCreatTime = jsonObjectSon
								.getString("stationCreatTime");
						String stationLat = jsonObjectSon
								.getString("stationLat");
						String stationLng = jsonObjectSon
								.getString("stationLng");
						String stationStatus = jsonObjectSon
								.getString("stationStatus");
						String stationType = jsonObjectSon
								.getString("stationType");
						StationDetaileInfo sdi = new StationDetaileInfo();
						sdi.setStationArea(stationArea);
						sdi.setStationCode(stationCode);
						sdi.setStationName(stationName);
						sdi.setStationAddress(stationAddress);
						sdi.setStationCreatTime(stationCreatTime);
						sdi.setStationLng(stationLng);
						sdi.setStationLat(stationLat);
						sdi.setStationCompany(stationCompany);
						sdi.setStationStatus(stationStatus);
						sdi.setStationType(getResources().getString(R.string.normalstation));//普通基站
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
