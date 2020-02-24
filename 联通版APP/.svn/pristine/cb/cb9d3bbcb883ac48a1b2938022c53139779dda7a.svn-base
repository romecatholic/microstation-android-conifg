package com.dgm.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dgm.bean.AlarmInfo;
import com.dgm.bean.DispatchInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.dlyapp.R.id;
import com.dgm.http.HttpReq;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DispatchFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private ProgressDialog pd;
	private TextView text;
	private static PullToRefreshListView refresh_lv;
	private List<DispatchInfo> list;
	private DataAdapter adapter;
	private Button refresh;
	private static int pageNo = 1;// 设置pageNo的初始化值为1，即默认获取的是第一页的数据。
	private List<DispatchInfo> total = new ArrayList<DispatchInfo>();// 用来存放获取的所有数据
	private List<DispatchInfo> oneTotal = new ArrayList<DispatchInfo>();// 用来存放一页数据
	//private static final String IP = "http://192.168.10.218:8080/DlyAppSever/AlarmServlet";
	private DBManager dbMgr;
	private TextView alarmname,alarmtime,areaname,stationname,devicename;
	private String userArea="";
	
	private String searchStationName = "";
	private String searchAlarmTime = "";
	private String searchAlarmEndTime = "";
	private String dateType = "0";

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
		searchStationName = "";
		searchAlarmTime = "";
		searchAlarmEndTime = "";
		dateType="0";
		dbMgr = new DBManager(getActivity());
		SharedPreferences shared = getActivity().getSharedPreferences("saveUserNamePwd", getActivity().MODE_PRIVATE);
		// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
		String userid = shared.getString("name", "");// 同上，若没找到就让它为空""
		pageNo = 1;
		userArea=dbMgr.queryUserArea(userid);
		userArea=stringReplace(userArea);
		mBaseView = inflater.inflate(R.layout.dispatch_main, null);
		mContext = getActivity();
		text = (TextView) mBaseView.findViewById(R.id.dispatchnull);
		refresh = (Button) mBaseView.findViewById(R.id.dispatch_refresh);
		refresh_lv = (PullToRefreshListView) mBaseView
				.findViewById(R.id.dispatch_pull_refresh_lv);
	
		if (getArguments() != null) {
			searchStationName = getArguments().getString("stationName");
			dateType=getArguments().getString("dateType");
			searchAlarmTime=getArguments().getString("alarmTime");
			searchAlarmEndTime=getArguments().getString("alarmTimeEnd");
		}
		
		
		list = new ArrayList<DispatchInfo>();
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//数据正在加载中...
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		String IP = "http://";

		String IPPORT = dbMgr.queryInterface();
		String url = IP+ IPPORT+"/DlyAppSever/WorkOrderServlet";
		new LoadDataAsyncTask(DispatchFragment.this).execute(url + pageNo);
		adapter = new DataAdapter(mContext, list);
		refresh_lv.setAdapter(adapter);
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Fragment newContent = new DispatchFragment();
				String title = "dispatch";
				switchFragment(newContent, title);
			}
		});
		
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
						new LoadDataAsyncTask(DispatchFragment.this).execute();

						pd.setIndeterminate(true);
						pd.setMessage(getResources().getString(R.string.loading));//数据正在加载中...
						pd.setCanceledOnTouchOutside(false);
						pd.show();
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

		private DispatchFragment dispatchFragment;

		public LoadDataAsyncTask(DispatchFragment dispatchFragment) {
			this.dispatchFragment = dispatchFragment;
		}

		@Override
		protected String doInBackground(String... arg0) {
			String IP = "http://";
			String IPPORT = dbMgr.queryInterface();
			String url = IP+ IPPORT+"/DlyAppSever/WorkOrderServlet";
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
/*			try {
				URLEncoder.encode(searchStationName, "UTF-8");
				URLEncoder.encode(searchAreaName, "UTF-8");
				URLEncoder.encode(searchAlarmType, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			list.add(new BasicNameValuePair("pageCount", "" + pageNo));
			list.add(new BasicNameValuePair("userArea", userArea));// 
			list.add(new BasicNameValuePair("startTime", searchAlarmTime));// = "";
			list.add(new BasicNameValuePair("endTime", searchAlarmEndTime));//
			list.add(new BasicNameValuePair("stationname", searchStationName));//搜索告警基站名称，模糊查询
			list.add(new BasicNameValuePair("dateType", dateType));//
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
				List<DispatchInfo> list = JSONAnalysis(result);

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
				dispatchFragment.pd.dismiss();
				dispatchFragment.refresh_lv.onRefreshComplete();
			} else {
				dispatchFragment.refresh_lv.onRefreshComplete();
			}
		}
	}

	/**
	 * JSON解析
	 * 
	 * @param result
	 * @return
	 */
	public List<DispatchInfo> JSONAnalysis(String result) {
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
						String alarmTime = jsonObjectSon
								.getString("alarmTime");
						String deviceName = jsonObjectSon
								.getString("deviceName");
						String continuedTime = jsonObjectSon.getString("continuedTime");
						String stationName = jsonObjectSon.getString("stationName");
						String areaName = jsonObjectSon.getString("areaName"); 
						String orderTime = jsonObjectSon.getString("orderTime"); 
						DispatchInfo ai = new DispatchInfo();
						ai.setAlarmName(alarmName);
						ai.setAlarmTime(alarmTime);
						ai.setAreaName(areaName);
						ai.setContinuedTime(continuedTime);
						ai.setDeviceName(deviceName);
						ai.setStationName(stationName);
						ai.setOdertime(orderTime);
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
		private List<DispatchInfo> list;

		/**
		 * bindData用来传递数据给适配器。
		 * 
		 * @param list
		 */
		public void bindData(List<DispatchInfo> list) {
			this.list = list;
		}

		public DataAdapter(Context context, List<DispatchInfo> list) {
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
						R.layout.dispatch_item, parent, false);
				vh = new ViewHolder();
				vh.alarmname = (TextView) convertView
						.findViewById(R.id.dispatch_alarmname);
				vh.alarmtime = (TextView) convertView
						.findViewById(R.id.dispatch_alarmtime);
				vh.stationname = (TextView) convertView
						.findViewById(R.id.dispatch_stationname);
				vh.areaname = (TextView) convertView
						.findViewById(R.id.dispatch_areaname);
				vh.devicename = (TextView) convertView
						.findViewById(R.id.dispatch_devicename);
				vh.continuedtime = (TextView) convertView
						.findViewById(R.id.dispatch_continuedtime);
				vh.ordetime = (TextView) convertView
						.findViewById(R.id.dispatch_ordertime);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			DispatchInfo music = (DispatchInfo) getItem(position);
			vh.alarmname.setText(music.getAlarmName());
			vh.alarmtime.setText(music.getAlarmTime());
			vh.areaname.setText(music.getAreaName());
			vh.stationname.setText(music.getStationName());
			vh.continuedtime.setText(music.getContinuedTime());
			vh.devicename.setText(music.getDeviceName());
			vh.ordetime.setText(music.getOdertime());

			return convertView;
		}

		class ViewHolder {
			TextView alarmname;
			TextView alarmtime;
			TextView areaname;
			TextView stationname;
			TextView continuedtime;
			TextView devicename;
			TextView ordetime;
	

			
		}
	}

	
	public String stringReplace(String s){
		String [] ss=s.split(";");
		String newString ="";
		for (int i = 0; i < ss.length; i++) {
			newString=newString+"'"+ss[i]+"',";
		}
		newString=newString.substring(0, newString.length()-1);
		return newString;
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
	
}
