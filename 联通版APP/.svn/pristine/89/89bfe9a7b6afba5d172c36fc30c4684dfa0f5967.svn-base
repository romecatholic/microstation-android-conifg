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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dgm.bean.DeviceMuBanInfo;
import com.dgm.bean.EquipmentDataInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class EquipmentControlDataFragment extends Fragment {
	
	private View mBaseView;
	private static PullToRefreshListView refresh_lv;
	private Context mContext;
	private ProgressDialog pd;
	private List<EquipmentDataInfo> list;
	private DataAdapter adapter;
	public String deviceId,equipmentId;
//	private static final String IP = "http://192.168.10.218:8080/DlyAppSever/EquipmentDataServlet";
	private DBManager dbMgr;
	private List<EquipmentDataInfo> oneTotal = new ArrayList<EquipmentDataInfo>();// 用来存放一页数据
	//接口异常界面元素
	private Button refresh;
	private TextView text;
	
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
		deviceId=EquipmentDetaileFragment.s_deviceId;
		equipmentId=EquipmentDetaileFragment.s_equipmentId;
		mBaseView = inflater.inflate(R.layout.equipment_control_data_main, null);
		refresh_lv = (PullToRefreshListView) mBaseView.findViewById(R.id.equipment_control_data_pull_refresh);
		mContext = getActivity();
		dbMgr = new DBManager(mContext);
		refresh = (Button) mBaseView.findViewById(R.id.equipment_control_refresh);
		text = (TextView) mBaseView.findViewById(R.id.listnull);
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//数据正在加载中...
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		new LoadDataAsyncTask(EquipmentControlDataFragment.this).execute();
		adapter = new DataAdapter(mContext, list);
		refresh_lv.setAdapter(adapter);
		
		return mBaseView;
	}
	
	/**
	 * 
	 * @author Administrator
	 * 
	 */
	private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

		public EquipmentControlDataFragment emdFragment;
		
		public LoadDataAsyncTask(EquipmentControlDataFragment emdf){
			this.emdFragment=emdf;
		}
		@Override
		protected String doInBackground(Void... arg0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			Log.e("deviceId@@@@@", deviceId);
			list.add(new BasicNameValuePair("deviceID", deviceId));
			String IP = "http://";
			dbMgr = new DBManager(getActivity());
			String IPPORT = dbMgr.queryInterface();
			String url = IP+ IPPORT+"/DlyAppSever/EquipmentDataControlServlet";
			String backString = (String) HttpReq.topostData(url, list);
			Log.e("", backString);
			return backString;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				List<EquipmentDataInfo> list=JSONAnalysis(result);
				if (list == null) {
					refresh_lv.setVisibility(View.INVISIBLE);
					text.setVisibility(View.VISIBLE);
					refresh.setVisibility(View.VISIBLE);
				} else if (list.size() != 0) {
					adapter.bindData(oneTotal);
					adapter.notifyDataSetChanged();
				}
				pd.dismiss();
				refresh_lv.onRefreshComplete();
			} else {
				refresh_lv.onRefreshComplete();
			}
		}
		
	}
	
	@SuppressWarnings("unused")
	public List<EquipmentDataInfo> JSONAnalysis(String result){
		oneTotal.clear();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String backMsg = jsonObject.getString("msg");
			String code = jsonObject.getString("code");
			if (code.equals("3")) {

			}else{
				if (backMsg.compareTo("success") == 0) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
						String stationArea = jsonObjectSon.getString("areaName");
						String stationName = jsonObjectSon.getString("stationName");
						String deviceName = jsonObjectSon.getString("deviceName");
						String updateTime = jsonObjectSon.getString("updateTime");
						String dataString =jsonObjectSon.getString("dataString");
						Log.e("@@@@@@@", dataString);
						String [] dataArray=dataString.split(";");
						List<DeviceMuBanInfo> moban=dbMgr.queryMuBan(equipmentId,"C");
						for (int j = 0; j < moban.size(); j++) {
							DeviceMuBanInfo dmbi=moban.get(j);
							String paramName=dmbi.getParamName();
							String paramUnit=dmbi.getParamUnit();
							int num=Integer.valueOf(dmbi.getParamNum());
							Log.e("@@@@@@@", ""+num);
							String data=dataArray[num-1];
							EquipmentDataInfo edi= new EquipmentDataInfo();
							edi.setAreaName(stationArea);
							edi.setStationName(stationName);
							edi.setDeviceName(deviceName);
							edi.setUpdateTime(updateTime);
							edi.setDataUnit(paramUnit);
							edi.setDataName(paramName);
							edi.setData(data);
							oneTotal.add(edi);
							
						}
						return oneTotal;
					}
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
		private List<EquipmentDataInfo> list;

		/**
		 * bindData用来传递数据给适配器。
		 * 
		 * @param list
		 */
		public void bindData(List<EquipmentDataInfo> list) {
			this.list = list;
		}
		
		public DataAdapter(Context context, List<EquipmentDataInfo> list) {
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
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.equipment_mo_data_main_item, parent, false);
				vh = new ViewHolder();
				vh.tv_paramName = (TextView) convertView
						.findViewById(R.id.device_param_name);
				vh.tv_deviceData = (TextView) convertView
						.findViewById(R.id.device_data);
				vh.tv_updateTime = (TextView) convertView
						.findViewById(R.id.device_data_time);
				vh.tv_deviceName = (TextView) convertView
						.findViewById(R.id.device_name2);
				vh.tv_stationName = (TextView) convertView
						.findViewById(R.id.device_station_name);
				vh.tv_unit = (TextView) convertView
						.findViewById(R.id.unit_string);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			EquipmentDataInfo music = (EquipmentDataInfo) getItem(position);
			vh.tv_paramName.setText(music.getDataName());
			vh.tv_deviceData.setText(music.getData());
			vh.tv_updateTime.setText(music.getUpdateTime());
			vh.tv_deviceName.setText(music.getDeviceName());
			vh.tv_stationName.setText(music.getStationName());
			vh.tv_unit.setText(music.getDataUnit());
			return convertView;
		}

		class ViewHolder {
			TextView tv_paramName;
			TextView tv_deviceData;
			TextView tv_updateTime;
			TextView tv_deviceName;
			TextView tv_stationName;
			TextView tv_unit;
		}
		
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
	}
}
