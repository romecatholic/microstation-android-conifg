package com.dgm.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.bean.StationInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;
import com.dgm.view.TitleBarView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class StationFragment extends Fragment {
	private View mBaseView;
	private Context mContext;
	private TitleBarView mTitleBarView;
	private ProgressDialog pd;
	private static PullToRefreshListView refresh_lv;
	private List<StationInfo> list;
	private TextView text;
	private Button refresh;
	private DataAdapter adapter;
	private static int pageNo = 1;// 设置pageNo的初始化值为1，即默认获取的是第一页的数据。
	private List<StationInfo> total = new ArrayList<StationInfo>();// 用来存放获取的所有数据
	private List<StationInfo> oneTotal = new ArrayList<StationInfo>();// 用来存放一页数据
	//private static final String IP = "http://192.168.10.218:8080/DlyAppSever/StationServlet";
	private DBManager dbMgr;

	public static String stationName = "";
	public static String areaName = "";
	public static String stationStatus = "";
	private String areaType = "";
	private String userArea="";
	private String userCompany="";
	
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
		stationName = "";
		areaName="";
		stationStatus="";
		areaType="0";
		pageNo = 1;
		dbMgr = new DBManager(getActivity());
		SharedPreferences shared = getActivity().getSharedPreferences("saveUserNamePwd", getActivity().MODE_PRIVATE);
		// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
		String userid = shared.getString("name", "");// 同上，若没找到就让它为空""
		areaName=dbMgr.queryUserArea(userid);
		userArea=dbMgr.queryUserArea(userid);
		areaName=stringReplace(areaName);
		userArea=stringReplace(userArea);
		userCompany=dbMgr.queryUserCompany(userid);
		userCompany=stringReplace(userCompany);
		System.out.println("@@@@@@@@@@@@@@@@"+userCompany);
		mBaseView = inflater.inflate(R.layout.activity_station_main, null);
		mContext = getActivity();
		refresh_lv = (PullToRefreshListView) mBaseView
				.findViewById(R.id.station_pull_refresh_lv);
		text = (TextView) mBaseView.findViewById(R.id.stationnull);
		refresh = (Button) mBaseView.findViewById(R.id.station_search);
		list = new ArrayList<StationInfo>();
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//"数据正在加载中..."
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		if (getArguments() != null) {
			stationName = getArguments().getString("stationName");
			areaName = getArguments().getString("areaName");
			stationStatus = getArguments().getString("stationStatus");
			areaType=getArguments().getString("areaType");
		}
		Log.e("mParam1", "" + stationName);
		String IP = "http://";
		
		String IPPORT = dbMgr.queryInterface();
		String url = IP+ IPPORT+"/DlyAppSever/StationServlet";
		new LoadDataAsyncTask(StationFragment.this).execute(url + pageNo);
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
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new LoadDataAsyncTask(StationFragment.this).execute();

						pd.setIndeterminate(true);
						pd.setMessage(getResources().getString(R.string.loading));//"数据正在加载中..."
						pd.setCanceledOnTouchOutside(false);
						pd.show();

					}
				});
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Fragment newContent = new StationFragment();
				String title = "station";
				switchFragment(newContent, title);
			}
		});
		refresh_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int len,
					long id) {
				Adapter adapter = parent.getAdapter();
				StationInfo item = (StationInfo) adapter.getItem(len);
				String stationid = item.getStationID();
				Fragment newContent = new StationDetailFragment();
				Bundle args = new Bundle();
				args.putString("stationId", stationid);
				args.putString("backButton", "");
				newContent.setArguments(args);
				String title = "stationdetail";
				switchFragment(newContent, title);
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

		private StationFragment stationFragment;

		public LoadDataAsyncTask(StationFragment alarmFragment) {
			this.stationFragment = alarmFragment;
		}

		@Override
		protected String doInBackground(String... arg0) {
			// String backString=HttpReq.RequestData(IP+pageNo);
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			list.add(new BasicNameValuePair("pageCount", "" + pageNo));
			list.add(new BasicNameValuePair("stationname", stationName));
			list.add(new BasicNameValuePair("areaName", areaName));
			list.add(new BasicNameValuePair("stationStatus", stationStatus));
			list.add(new BasicNameValuePair("areaType", areaType));// 
			list.add(new BasicNameValuePair("userArea", userArea));// 
			list.add(new BasicNameValuePair("userCompany", userCompany));// 

			String IP = "http://";
			dbMgr = new DBManager(getActivity());
			String IPPORT = dbMgr.queryInterface();
			String url = IP+ IPPORT+"/DlyAppSever/StationServlet";
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
				List<StationInfo> list = JSONAnalysis(result);
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
				stationFragment.pd.dismiss();
				stationFragment.refresh_lv.onRefreshComplete();
			} else {
				stationFragment.refresh_lv.onRefreshComplete();
			}
		}
	}

	/**
	 * JSON解析
	 * 
	 * @param result
	 * @return
	 */
	public List<StationInfo> JSONAnalysis(String result) {
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
						String areaName = jsonObjectSon.getString("areaName");
						String companyName = jsonObjectSon
								.getString("companyName");
						String stationID = jsonObjectSon.getString("stationID");
						String stationLat = jsonObjectSon
								.getString("stationLat");
						String stationLng = jsonObjectSon
								.getString("stationLng");
						String stationName = jsonObjectSon
								.getString("stationName");
						String stationStatus = jsonObjectSon
								.getString("stationStatus");
						String stationCode = jsonObjectSon
								.getString("stationCode");
						StationInfo si = new StationInfo();
						si.setAreaName(areaName);
						si.setCompanyName(companyName);
						si.setStationID(stationID);
						si.setStationLat(stationLat);
						si.setStationLng(stationLng);
						si.setStationName(stationName);
						si.setStationStatus(stationStatus);
						si.setStationCode(stationCode);
						oneTotal.add(si);
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
		private List<StationInfo> list;

		/**
		 * bindData用来传递数据给适配器。
		 * 
		 * @param list
		 */
		public void bindData(List<StationInfo> list) {
			this.list = list;
		}

		public DataAdapter(Context context, List<StationInfo> list) {
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
						R.layout.activity_station_main_item, parent, false);
				vh = new ViewHolder();
				vh.tv_image = (ImageView) convertView
						.findViewById(R.id.station_img);
				vh.tv_areaname = (TextView) convertView.findViewById(R.id.area);
				vh.tv_stationCode = (TextView) convertView
						.findViewById(R.id.stationcode);
				vh.tv_companyName = (TextView) convertView
						.findViewById(R.id.company);
				vh.tv_stationId = (TextView) convertView
						.findViewById(R.id.stationid);
				vh.tv_stationName = (TextView) convertView
						.findViewById(R.id.station_name);
				vh.tv_stationStatus = (TextView) convertView
						.findViewById(R.id.alarm_status);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			StationInfo si = (StationInfo) getItem(position);
			String status = si.getStationStatus();
			if (status.compareTo(convertView.getResources().getString(R.string.cutoff)) == 0) {//掉线
				vh.tv_image.setImageResource(R.drawable.warn);
				vh.tv_stationStatus.setTextColor(0xffFF0000);
			} else if (status.compareTo(convertView.getResources().getString(R.string.alarm)) == 0) {//告警
				vh.tv_image.setImageResource(R.drawable.offline);
				vh.tv_stationStatus.setTextColor(0xffFF0000);
			} else if (status.compareTo(convertView.getResources().getString(R.string.uninstall)) == 0) {//未安装
				vh.tv_image.setImageResource(R.drawable.neterror);
				vh.tv_stationStatus.setTextColor(0Xff2828FF);
			} else {
				vh.tv_image.setImageResource(R.drawable.run);
				vh.tv_stationStatus.setTextColor(0xff01B468);
			}
			vh.tv_areaname.setText(si.getAreaName());
			vh.tv_stationCode.setText(si.getStationCode());
			vh.tv_companyName.setText(si.getCompanyName());
			vh.tv_stationName.setText(si.getStationName());
			vh.tv_stationStatus.setText(si.getStationStatus());
			vh.tv_stationId.setText(si.getStationID());
			return convertView;
		}

		class ViewHolder {
			ImageView tv_image;
			TextView tv_stationName;
			TextView tv_areaname;
			TextView tv_stationId;
			TextView tv_stationCode;
			TextView tv_companyName;
			TextView tv_stationStatus;
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
	
	public String stringReplace(String s){
		String [] ss=s.split(";");
		String newString ="";
		for (int i = 0; i < ss.length; i++) {
			newString=newString+"'"+ss[i]+"',";
		}
		newString=newString.substring(0, newString.length()-1);
		return newString;
	}
	
}
