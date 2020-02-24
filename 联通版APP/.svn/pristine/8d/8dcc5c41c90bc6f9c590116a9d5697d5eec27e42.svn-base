package com.dgm.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dgm.bean.AlarmInfo;
import com.dgm.bean.StationDetaileInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.LoginActivity;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmStationFragment extends Fragment {
	private View mBaseView, loginBiew;
	private Context mContext;
	private ProgressDialog pd;
	private Button confirm;
	private TextView tv_stationName;
	private TextView tv_area;
	private TextView tv_alarmName;
	private TextView tv_alarmClass;
	private TextView tv_alarmEquip;
	private TextView tv_alarmTime;
	
	private EditText et_alarmUser;
	private EditText et_alarmPassword;
	
	private String user_Name;
	private String user_password;
	
	public static String alarmId = "";
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
					Fragment newContent = new AlarmNoConfirmFragment();
					String title = "alarmNo";
					if(getArguments()!=null){
						Bundle args = new Bundle();  
						Log.e("alarmNo", "alarmNo");
						args.putString("stationName",getArguments().getString("stationName"));  
						args.putString("areaName", getArguments().getString("areaName"));
						args.putString("alarmType", getArguments().getString("alarmType"));
						args.putString("alartTime", getArguments().getString("alarmStartTime"));
						args.putString("alarTimeEnd", getArguments().getString("areaName"));
						args.putString("alarmName", getArguments().getString("alarmName"));
						args.putString("areaType", "1");
						newContent.setArguments(args); 				
					}
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

		mBaseView = inflater.inflate(R.layout.alarm_station, null);
		dbMgr = new DBManager(getActivity());

		if (getArguments() != null) {
			alarmId = getArguments().getString("alarmid");
			System.out.println(alarmId);
		}
		init();
		confirm = (Button) mBaseView.findViewById(R.id.alarmconfirm);
		mContext = getActivity();
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loginBiew = inflater.inflate(R.layout.alarm_login, null);
				et_alarmUser=(EditText)loginBiew.findViewById(R.id.alarm_login_user);
				et_alarmPassword=(EditText)loginBiew.findViewById(R.id.alarm_login_password);
				new AlertDialog.Builder(getActivity())
						.setView(loginBiew)
						.setPositiveButton(getResources().getString(R.string.confirmAlarm),//确认告警
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										user_Name = et_alarmUser.getText().toString();
										user_password = et_alarmPassword.getText().toString();
										// 第一个参数是文件名，第二个参数是模式（不明白可以去补习一下SharedPreferences的知识）
										SharedPreferences shared = getActivity().getSharedPreferences("saveUserNamePwd", Context.MODE_PRIVATE);
										// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
										String name1 = shared.getString("name", "");// 同上，若没找到就让它为空""
										String psd1 = shared.getString("psd", "");
										//Log.e("@@@@@@@@@@@@@2", "用户名："+name1+":"+user_Name+"密码："+psd1+":"+user_password);
										if(user_Name.compareTo(name1)==0 &&user_password.compareTo(psd1)==0 ){
											new Thread() {
												@Override
												public void run() {
													String IP = "http://";
													dbMgr = new DBManager(
															getActivity());
													String IPPORT = dbMgr
															.queryInterface();
													String url = IP
															+ IPPORT
															+ "/DlyAppSever/AlarmConfirmServlet";
													List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
													list.add(new BasicNameValuePair("userName", user_Name));
													list.add(new BasicNameValuePair("userPassword", user_password));
													list.add(new BasicNameValuePair("alarmID", alarmId));
													String result = (String) HttpReq.topostData(url,list);
													Message message = handler.obtainMessage();
													message.obj = result;
													message.what = 1;
													handler.sendMessage(message);
												}
											}.start();
										}else{
											user_Name="";
											user_password="";
											Toast.makeText(getActivity(), getResources().getString(R.string.acToast6),//请确认用户名密码！
													Toast.LENGTH_LONG).show();
										}
									}
								}).setNegativeButton(getResources().getString(R.string.cancel), null)//取消
						.setCancelable(false).show();

			}
		});
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//数据正在加载中...
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		new LoadDataAsyncTask(AlarmStationFragment.this).execute();
		return mBaseView;
	}

	public void init() {
		tv_stationName = (TextView) mBaseView
				.findViewById(R.id.staitonname_alarm);
		tv_area = (TextView) mBaseView.findViewById(R.id.staitonarea_alarm);
		tv_alarmName = (TextView) mBaseView.findViewById(R.id.alarm_name);
		tv_alarmClass = (TextView) mBaseView.findViewById(R.id.alarm_class);
		tv_alarmEquip = (TextView) mBaseView.findViewById(R.id.alarm_equip);
		tv_alarmTime = (TextView) mBaseView.findViewById(R.id.alarm_time);
	}

	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Object object = msg.obj;
			String result=object.toString();
			Log.e("@@@@@@@@@2", result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String backMsg = jsonObject.getString("msg");
				String code = jsonObject.getString("code");
				if (code.equals("1")&&backMsg.compareTo("success") == 0){
					
					Fragment newContent = new AlarmNoConfirmFragment();
					String title = "alarmNo";
					switchFragment(newContent, title);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		};
	};
	
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
		private AlarmStationFragment SDIfragment;

		public LoadDataAsyncTask(AlarmStationFragment alarmStationFragment) {
			this.SDIfragment = alarmStationFragment;
		}

		@Override
		protected String doInBackground(Void... arg0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			list.add(new BasicNameValuePair("alarmID", alarmId));
			
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

					}else{
						Log.e("list", "list.size()=0");
					}
				}else{
					Log.e("list", "null");
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
						String sdate = jsonObjectSon.getString("sdate");
						String areaName = jsonObjectSon.getString("areaName");
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
