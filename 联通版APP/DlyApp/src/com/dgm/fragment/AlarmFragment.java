package com.dgm.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.bean.AlarmInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class AlarmFragment extends Fragment {

	private View mBaseView;
	private Context mContext;
	private ProgressDialog pd;
	private TextView text;
	private static PullToRefreshListView refresh_lv;
	private List<AlarmInfo> list;
	private DataAdapter adapter;
	private Button refresh;
	private static int pageNo = 1;// ����pageNo�ĳ�ʼ��ֵΪ1����Ĭ�ϻ�ȡ���ǵ�һҳ�����ݡ�
	private List<AlarmInfo> total = new ArrayList<AlarmInfo>();// ������Ż�ȡ����������
	private List<AlarmInfo> oneTotal = new ArrayList<AlarmInfo>();// �������һҳ����
	// private static final String IP =
	// "http://192.168.10.218:8080/DlyAppSever/AlarmServlet";
	private DBManager dbMgr;
	private String searchStationName = "";
	private String searchAreaName = "";
	private String searchAlarmType = "";
	private String searchAlarmName = "";
	private String searchAlarmTime = "";
	private String searchAlarmEndTime = "";
	private String areaType = "";// �ӿ������ѯ���ͣ����Ϊ1���ӿ��������like�����Ϊ0���ӿ�������� in(����)
	private String userArea = "";
	private String userCompany = "";
	private String[] alarm_items = null;

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
					// ���������ذ�ť����¼�
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					startActivity(intent);

					return true;// δ����
				}
				return false;
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		alarm_items=new String[]{ getResources().getString(R.string.alarmDetail), getResources().getString(R.string.realData) };//�澯����	ʵʱ����
		if (getArguments() != null) {
			searchStationName = getArguments().getString("stationName");
			searchAreaName = getArguments().getString("areaName");
			searchAlarmType = getArguments().getString("alarmType");
			areaType = getArguments().getString("areaType");
			searchAlarmTime = getArguments().getString("alartTime");
			searchAlarmName = getArguments().getString("alarmName");
			searchAlarmEndTime = getArguments().getString("alarTimeEnd");
		} else {
			searchStationName = "";
			searchAlarmType = "";
			searchAlarmTime = "";
			searchAlarmName = "";
			searchAlarmEndTime = "";
			areaType = "0";

		}

		pageNo = 1;

		dbMgr = new DBManager(getActivity());
		SharedPreferences shared = getActivity().getSharedPreferences(
				"saveUserNamePwd", getActivity().MODE_PRIVATE);
		// ��һ���������ǹؼ��֣��ڶ�������ΪĬ��ֵ����˼��˵���û�ҵ�ֵ����Ĭ��ֵ����
		String userid = shared.getString("name", "");// ͬ�ϣ���û�ҵ�������Ϊ��""
		searchAreaName = dbMgr.queryUserArea(userid);
		searchAreaName = stringReplace(searchAreaName);
		userArea = dbMgr.queryUserArea(userid);
		userArea = stringReplace(userArea);
		userCompany = dbMgr.queryUserCompany(userid);
		userCompany = stringReplace(userCompany);

		mBaseView = inflater.inflate(R.layout.activity_alarm_main, null);
		text = (TextView) mBaseView.findViewById(R.id.listnull);
		refresh = (Button) mBaseView.findViewById(R.id.alarm_search);
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Fragment newContent = new AlarmFragment();
				String title = "alarmYes";
				switchFragment(newContent, title);
			}
		});

		mContext = getActivity();
		refresh_lv = (PullToRefreshListView) mBaseView
				.findViewById(R.id.main_pull_refresh_lv);
		if (getArguments() != null) {
			searchStationName = getArguments().getString("stationName");
			searchAreaName = getArguments().getString("areaName");
			searchAlarmType = getArguments().getString("alarmType");
			areaType = getArguments().getString("areaType");
			searchAlarmTime = getArguments().getString("alartTime");
			searchAlarmName = getArguments().getString("alarmName");
			searchAlarmEndTime = getArguments().getString("alarTimeEnd");
		}
		list = new ArrayList<AlarmInfo>();
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//"�������ڼ�����..."
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		String IP = "http://";

		String IPPORT = dbMgr.queryInterface();
		String url = IP + IPPORT + "/DlyAppSever/AlarmServlet";
		new LoadDataAsyncTask(AlarmFragment.this).execute(url + pageNo);
		adapter = new DataAdapter(mContext, list);
		refresh_lv.setAdapter(adapter);

		// ���ÿ�����ˢ�º�����ˢ��
		refresh_lv.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);

		// ����ˢ��ʱ��ʾ���ı�
		ILoadingLayout startLayout = refresh_lv.getLoadingLayoutProxy(true,
				false);
		startLayout.setPullLabel(getResources().getString(R.string.refreshing));//��������ˢ��...
		startLayout.setRefreshingLabel(getResources().getString(R.string.moreloading));//��������������...
		startLayout.setReleaseLabel(getResources().getString(R.string.releasing));//�ſ���ˢ��

		ILoadingLayout endLayout = refresh_lv
				.getLoadingLayoutProxy(false, true);
		endLayout.setPullLabel(getResources().getString(R.string.loadingmore));//���ظ�������...
		endLayout.setRefreshingLabel(getResources().getString(R.string.moreloading));//��������������...
		endLayout.setReleaseLabel(getResources().getString(R.string.releasing));//�ſ���ˢ��

		refresh_lv
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// new LoadDataAsyncTask().execute();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						Log.e("@@@@@@@@", "" + pageNo);
						new LoadDataAsyncTask(AlarmFragment.this).execute();

						pd.setIndeterminate(true);
						pd.setMessage(getResources().getString(R.string.loading));//"�������ڼ�����..."
						pd.setCanceledOnTouchOutside(false);
						pd.show();
					}

				});
		refresh_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent, View v,
					final int len, long id) {
				// ���������
				InputMethodManager manager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				new AlertDialog.Builder(getActivity())
						.setTitle(getResources().getString(R.string.sele))//��ѡ��
						.setItems(alarm_items,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int i) {
										if (i == 0) {

											Adapter adapter = parent
													.getAdapter();
											AlarmInfo item = (AlarmInfo) adapter
													.getItem(len);
											String alarmid = item.getAlarmId();
											Fragment newContent = new AlarmDetailFragment();
											Bundle args = new Bundle();
											args.putString("alarmid", alarmid);
											if (getArguments() != null) {
												String station_Name = getArguments()
														.getString(
																"stationName");
												String areaName = getArguments()
														.getString("areaName");
												String alarmType = getArguments()
														.getString("alarmType");
												String alarmStartTime = getArguments()
														.getString(
																"alarmStartTime");
												String alarmNamee = getArguments()
														.getString("alarmName");
												String alarmEndTime = getArguments()
														.getString(
																"alarmEndTime");
												args.putString("stationName",
														station_Name);
												args.putString("areaName",
														areaName);
												args.putString("alarmType",
														alarmType);
												args.putString("alartTime",
														alarmStartTime);
												args.putString("alarmName",
														alarmNamee);
												args.putString("alarTimeEnd",
														alarmEndTime);
												args.putString("areaType", "1");

											}
											newContent.setArguments(args);
											String title = "alarmdetail";
											switchFragment(newContent, title);
										} else {
											Adapter adapter = parent
													.getAdapter();
											AlarmInfo item = (AlarmInfo) adapter
													.getItem(len);
											String stationid = item
													.getStationID();
											Fragment newContent = new StationDetailFragment();
											Bundle args = new Bundle();
											args.putString("stationId",
													stationid);
											args.putString("fragflag", "alarm");
											args.putString("backButton", "");
											if (getArguments() != null) {
												String station_Name = getArguments()
														.getString(
																"stationName");
												String areaName = getArguments()
														.getString("areaName");
												String alarmType = getArguments()
														.getString("alarmType");
												String alarmStartTime = getArguments()
														.getString(
																"alarmStartTime");
												String alarmNamee = getArguments()
														.getString("alarmName");
												String alarmEndTime = getArguments()
														.getString(
																"alarmEndTime");
												args.putString("stationName",
														station_Name);
												args.putString("areaName",
														areaName);
												args.putString("alarmType",
														alarmType);
												args.putString("alartTime",
														alarmStartTime);
												args.putString("alarmName",
														alarmNamee);
												args.putString("alarTimeEnd",
														alarmEndTime);
												args.putString("areaType", "1");

											}
											newContent.setArguments(args);
											String title = "stationdetail";
											switchFragment(newContent, title);

										}

									}
								}).show();
			}

		});

		return mBaseView;
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

	private class LoadDataAsyncTask extends AsyncTask<String, Void, String> {

		private AlarmFragment alarmFragment;

		public LoadDataAsyncTask(AlarmFragment alarmFragment) {
			this.alarmFragment = alarmFragment;
		}

		@Override
		protected String doInBackground(String... arg0) {
			String IP = "http://";
			String IPPORT = dbMgr.queryInterface();
			String url = IP + IPPORT + "/DlyAppSever/AlarmServlet";
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			/*
			 * try { URLEncoder.encode(searchStationName, "UTF-8");
			 * URLEncoder.encode(searchAreaName, "UTF-8");
			 * URLEncoder.encode(searchAlarmType, "UTF-8"); } catch
			 * (UnsupportedEncodingException e) { e.printStackTrace(); }
			 */
			list.add(new BasicNameValuePair("pageCount", "" + pageNo));
			list.add(new BasicNameValuePair("stationname", searchStationName));// �����澯��վ���ƣ�ģ����ѯ
			list.add(new BasicNameValuePair("areaname", searchAreaName));// �����澯��վ����
			list.add(new BasicNameValuePair("alarmtype", searchAlarmType));// �����澯�ȼ�
			list.add(new BasicNameValuePair("operateState", "2"));// δȷ�ϸ澯״̬λ
			list.add(new BasicNameValuePair("areaType", areaType));//
			list.add(new BasicNameValuePair("userArea", userArea));//
			list.add(new BasicNameValuePair("userCompany", userCompany));//
			list.add(new BasicNameValuePair("startTime", searchAlarmTime));// =
																			// "";
			list.add(new BasicNameValuePair("endTime", searchAlarmEndTime));//
			list.add(new BasicNameValuePair("alarmName", searchAlarmName));//
			String backString = (String) HttpReq.topostData(url, list);
			return backString;
		}

		/**
		 * ���ʱ�ķ���
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.e("@@@@@@@@", "" + result);
			if (result != null) {
				// �����ȡ��result���ݲ�Ϊ�գ���ô�������JSON����������ʾ���ֻ���Ļ�ϡ�
				List<AlarmInfo> list = JSONAnalysis(result);

				if (list == null) {
					refresh_lv.setVisibility(View.INVISIBLE);
					text.setVisibility(View.VISIBLE);
					refresh.setVisibility(View.VISIBLE);
				} else if (list.size() != 0) {
					total.addAll(list);
					adapter.bindData(total);
					adapter.notifyDataSetChanged();
					pageNo++;
				} else {
					Toast toast = Toast.makeText(mContext, getResources().getString(R.string.nomore),//�޸�������
							Toast.LENGTH_SHORT);
					// ��ʾtoast��Ϣ
					toast.show();
				}
				alarmFragment.pd.dismiss();
				alarmFragment.refresh_lv.onRefreshComplete();
			} else {
				alarmFragment.refresh_lv.onRefreshComplete();
			}
		}
	}

	/**
	 * JSON����
	 * 
	 * @param result
	 * @return
	 */
	public List<AlarmInfo> JSONAnalysis(String result) {
		oneTotal.clear();// ����֮ǰ ������һ�£��Ա�ֻ֤�洢��ҳ�����ݣ������ظ���
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String backMsg = jsonObject.getString("msg");
			String code = jsonObject.getString("code");
			if (code.equals("3")) {
				oneTotal.isEmpty();
				return oneTotal;

			} else {
				if (backMsg.compareTo("success") == 0) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObjectSon = (JSONObject) jsonArray
								.opt(i);
						String alarmName = jsonObjectSon.getString("alarmName");
						String stationName = jsonObjectSon
								.getString("stationName");
						String deviceName = jsonObjectSon
								.getString("deviceName");
						String sdate = jsonObjectSon.getString("sdate");
						String alarmType = jsonObjectSon.getString("alarmType");
						String alarmId = jsonObjectSon.getString("alarmID");
						String stationID = jsonObjectSon.getString("stationID");
						// String area = jsonObjectSon.getString("alarmID");
						AlarmInfo ai = new AlarmInfo();
						ai.setAlarmName(alarmName);
						ai.setStationName(stationName);
						ai.setAlarmTime(sdate);
						ai.setEquipmentName(deviceName);
						ai.setAlarmType(alarmType);
						ai.setAlarmId(alarmId);
						ai.setStationID(stationID);
						oneTotal.add(ai);
					}
					return oneTotal;// ֻ���ظ�ҳ������

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

	/**
	 * �Զ���������
	 */
	private static class DataAdapter extends BaseAdapter {
		private Context context;
		private List<AlarmInfo> list;

		/**
		 * bindData�����������ݸ���������
		 * 
		 * @param list
		 */
		public void bindData(List<AlarmInfo> list) {
			this.list = list;
		}

		public DataAdapter(Context context, List<AlarmInfo> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.activity_alarm_main_item, parent, false);
				vh = new ViewHolder();
				vh.tv_alarm = (TextView) convertView
						.findViewById(R.id.alarmName);
				vh.tv_alarm1 = (TextView) convertView
						.findViewById(R.id.alarmname1);
				vh.tv_station = (TextView) convertView
						.findViewById(R.id.stationName);
				vh.tv_device = (TextView) convertView
						.findViewById(R.id.deviceName);
				vh.tv_time = (TextView) convertView
						.findViewById(R.id.alarmtime);
				vh.tv_type = (TextView) convertView
						.findViewById(R.id.alarmtype);
				vh.tv_alarmid = (TextView) convertView
						.findViewById(R.id.alarmid);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			AlarmInfo music = (AlarmInfo) getItem(position);
			vh.tv_alarm1.setText(music.getAlarmName());
			vh.tv_alarm.setText(music.getAlarmName());
			vh.tv_station.setText(music.getStationName());
			vh.tv_station.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					System.out.println("111");

				}
			});
			vh.tv_device.setText(music.getEquipmentName());
			vh.tv_time.setText(music.getAlarmTime());
			String alarmLevel = music.getAlarmType();
			if (alarmLevel.compareTo(convertView.getResources().getString(R.string.urgent)) == 0) {//���ظ澯
				vh.tv_type.setTextColor(0xffFF0000);
			} else if (alarmLevel.compareTo(convertView.getResources().getString(R.string.important)) == 0) {//��Ҫ�澯
				vh.tv_type.setTextColor(0xff0000CD);
			} else {
				vh.tv_type.setTextColor(0xffFFFF00);
			}
			vh.tv_type.setText(music.getAlarmType());
			vh.tv_alarmid.setText(music.getAlarmId());
			return convertView;
		}

		class ViewHolder {
			TextView tv_alarm1;
			TextView tv_alarm;
			TextView tv_station;
			TextView tv_device;
			TextView tv_type;
			TextView tv_time;
			TextView tv_alarmid;
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

	public String stringReplace(String s) {
		String[] ss = s.split(";");
		String newString = "";
		for (int i = 0; i < ss.length; i++) {
			newString = newString + "'" + ss[i] + "',";
		}
		newString = newString.substring(0, newString.length() - 1);
		return newString;
	}
}
