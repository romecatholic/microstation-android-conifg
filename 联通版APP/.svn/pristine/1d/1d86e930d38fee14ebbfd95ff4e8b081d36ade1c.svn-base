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
	private static int pageNo = 1;// 设置pageNo的初始化值为1，即默认获取的是第一页的数据。
	private List<AlarmInfo> total = new ArrayList<AlarmInfo>();// 用来存放获取的所有数据
	private List<AlarmInfo> oneTotal = new ArrayList<AlarmInfo>();// 用来存放一页数据
	// private static final String IP =
	// "http://192.168.10.218:8080/DlyAppSever/AlarmServlet";
	private DBManager dbMgr;
	private String searchStationName = "";
	private String searchAreaName = "";
	private String searchAlarmType = "";
	private String searchAlarmName = "";
	private String searchAlarmTime = "";
	private String searchAlarmEndTime = "";
	private String areaType = "";// 接口区域查询类型，如果为1，接口区域采用like，如果为0，接口区域采用 in(……)
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
					// 监听到返回按钮点击事件
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					startActivity(intent);

					return true;// 未处理
				}
				return false;
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		alarm_items=new String[]{ getResources().getString(R.string.alarmDetail), getResources().getString(R.string.realData) };//告警详情	实时数据
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
		// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
		String userid = shared.getString("name", "");// 同上，若没找到就让它为空""
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
		pd.setMessage(getResources().getString(R.string.loading));//"数据正在加载中..."
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		String IP = "http://";

		String IPPORT = dbMgr.queryInterface();
		String url = IP + IPPORT + "/DlyAppSever/AlarmServlet";
		new LoadDataAsyncTask(AlarmFragment.this).execute(url + pageNo);
		adapter = new DataAdapter(mContext, list);
		refresh_lv.setAdapter(adapter);

		// 设置可上拉刷新和下拉刷新
		refresh_lv.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);

		// 设置刷新时显示的文本
		ILoadingLayout startLayout = refresh_lv.getLoadingLayoutProxy(true,
				false);
		startLayout.setPullLabel(getResources().getString(R.string.refreshing));//正在下拉刷新...
		startLayout.setRefreshingLabel(getResources().getString(R.string.moreloading));//正在玩命加载中...
		startLayout.setReleaseLabel(getResources().getString(R.string.releasing));//放开以刷新

		ILoadingLayout endLayout = refresh_lv
				.getLoadingLayoutProxy(false, true);
		endLayout.setPullLabel(getResources().getString(R.string.loadingmore));//加载更多数据...
		endLayout.setRefreshingLabel(getResources().getString(R.string.moreloading));//正在玩命加载中...
		endLayout.setReleaseLabel(getResources().getString(R.string.releasing));//放开以刷新

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
						pd.setMessage(getResources().getString(R.string.loading));//"数据正在加载中..."
						pd.setCanceledOnTouchOutside(false);
						pd.show();
					}

				});
		refresh_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent, View v,
					final int len, long id) {
				// 隐藏软键盘
				InputMethodManager manager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				new AlertDialog.Builder(getActivity())
						.setTitle(getResources().getString(R.string.sele))//请选择
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
			list.add(new BasicNameValuePair("stationname", searchStationName));// 搜索告警基站名称，模糊查询
			list.add(new BasicNameValuePair("areaname", searchAreaName));// 搜索告警基站区域
			list.add(new BasicNameValuePair("alarmtype", searchAlarmType));// 搜索告警等级
			list.add(new BasicNameValuePair("operateState", "2"));// 未确认告警状态位
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
		 * 完成时的方法
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.e("@@@@@@@@", "" + result);
			if (result != null) {
				// 如果获取的result数据不为空，那么对其进行JSON解析。并显示在手机屏幕上。
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
					Toast toast = Toast.makeText(mContext, getResources().getString(R.string.nomore),//无更多数据
							Toast.LENGTH_SHORT);
					// 显示toast信息
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
	 * JSON解析
	 * 
	 * @param result
	 * @return
	 */
	public List<AlarmInfo> JSONAnalysis(String result) {
		oneTotal.clear();// 解析之前 先清理一下，以保证只存储该页的数据，避免重复。
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
					return oneTotal;// 只返回该页的数据

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
	 * 自定义适配器
	 */
	private static class DataAdapter extends BaseAdapter {
		private Context context;
		private List<AlarmInfo> list;

		/**
		 * bindData用来传递数据给适配器。
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
			if (alarmLevel.compareTo(convertView.getResources().getString(R.string.urgent)) == 0) {//严重告警
				vh.tv_type.setTextColor(0xffFF0000);
			} else if (alarmLevel.compareTo(convertView.getResources().getString(R.string.important)) == 0) {//重要告警
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
