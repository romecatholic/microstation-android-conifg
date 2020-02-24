package com.dgm.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.bean.StationEquip;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class StationEquipInfoFragment  extends Fragment{

	private View mBaseView;
	private Context mContext;
	private ProgressDialog pd;
	private TextView text;
	private static PullToRefreshListView refresh_lv;
	private List<StationEquip> list;
    private DataAdapter adapter;
    private Button refresh;
	private static int pageNo = 1;//设置pageNo的初始化值为1，即默认获取的是第一页的数据。
	private List<StationEquip> total = new ArrayList<StationEquip>();//用来存放获取的所有数据
	private List<StationEquip> oneTotal = new ArrayList<StationEquip>();// 用来存放一页数据
	//private static final String IP="http://192.168.10.218:8080/DlyAppSever/EquipmentServlet";
	private DBManager dbMgr;
	private String stationId="";
	public static String fragflag="";

	@Override
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
		stationId="";
		pageNo=1;
		mBaseView = inflater.inflate(R.layout.station_equip_page, null);
		text = (TextView) mBaseView.findViewById(R.id.listnull);
	
		mContext = getActivity();
		refresh_lv = (PullToRefreshListView) mBaseView.findViewById(R.id.main_equip_refresh_lv);
		stationId=StationDetailFragment.stationId;
		fragflag = StationDetailFragment.fragflag;

        list = new ArrayList<StationEquip>();
        pd = new ProgressDialog(mContext); 
        pd.setIndeterminate(true);  
        pd.setMessage(getResources().getString(R.string.loading));//"数据正在加载中..."
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String IP = "http://";
		dbMgr = new DBManager(getActivity());
		String IPPORT = dbMgr.queryInterface();
		String url = IP+ IPPORT+"/DlyAppSever/EquipmentServlet";
        new LoadDataAsyncTask(StationEquipInfoFragment.this).execute(url+pageNo);
        adapter = new DataAdapter(mContext,list);
        refresh_lv.setAdapter(adapter);

        //设置可上拉刷新和下拉刷新
        refresh_lv.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);
  
        //设置刷新时显示的文本
        ILoadingLayout startLayout = refresh_lv.getLoadingLayoutProxy(true,false);
        startLayout.setPullLabel(getResources().getString(R.string.refreshing));//正在下拉刷新...
		startLayout.setRefreshingLabel(getResources().getString(R.string.moreloading));//正在玩命加载中...
		startLayout.setReleaseLabel(getResources().getString(R.string.releasing));//放开以刷新

		ILoadingLayout endLayout = refresh_lv
				.getLoadingLayoutProxy(false, true);
		endLayout.setPullLabel(getResources().getString(R.string.loadingmore));//加载更多数据...
		endLayout.setRefreshingLabel(getResources().getString(R.string.moreloading));//正在玩命加载中...
		endLayout.setReleaseLabel(getResources().getString(R.string.releasing));//放开以刷新
        
//		
//        refresh_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//               // new LoadDataAsyncTask().execute();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//            	Log.e("@@@@@@@@", ""+pageNo);
//                new LoadDataAsyncTask(StationEquipInfoFragment.this).execute();
//                
//                pd.setIndeterminate(true);  
//                pd.setMessage("数据正在加载中...");  
//                pd.setCanceledOnTouchOutside(false);
//                pd.show();  
//            }
//        	 
//        });

		refresh_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int len,
					long id) {
				Adapter adapter = parent.getAdapter();
				StationEquip item = (StationEquip) adapter.getItem(len);
				String deviceId = item.getDeviceId();
				String equipmentID = item.getEquipment_id();
				Fragment newContent = new EquipmentDetaileFragment();
				Bundle args = new Bundle();
				args.putString("deviceId", deviceId);
				args.putString("stationId", stationId);
				args.putString("equipmentId", equipmentID);
				newContent.setArguments(args);
				String title = "equipmentdetail";
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
	 private  class LoadDataAsyncTask extends AsyncTask<String,Void,String>{

		 private StationEquipInfoFragment equipFragment;
		 public LoadDataAsyncTask(StationEquipInfoFragment equipFragment){
			 this.equipFragment=equipFragment;
		 }
		@Override
		protected String doInBackground(String... arg0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			try {
				URLEncoder.encode(stationId, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			list.add(new BasicNameValuePair("stationID", stationId));
			String IP = "http://";
			dbMgr = new DBManager(getActivity());
			String IPPORT = dbMgr.queryInterface();
			String url = IP+ IPPORT+"/DlyAppSever/EquipmentServlet";
			String backString=(String) HttpReq.topostData(url,list);
			//String backString=(String)HttpReq.toGetData("http://192.168.10.218:8080/DlyAppSever/AlarmServlet?pageCount=1&stationname=测试&areaname=&alarmtype=");
			return backString;
		}
	 
	 /**
      * 完成时的方法
      */
		  @Override
	    protected void onPostExecute(String result) {
			  super.onPostExecute(result);
			  Log.e("@@@@@@@@", ""+result);
			  if (result != null) {
					// 如果获取的result数据不为空，那么对其进行JSON解析。并显示在手机屏幕上。
					List<StationEquip> list = JSONAnalysis(result);
					
					if(list==null){
						refresh_lv.setVisibility(View.INVISIBLE);
					}else if(list.size()!=0){
						total.addAll(list);
						adapter.bindData(total);
						adapter.notifyDataSetChanged();
						pageNo++;
					}else{

						Toast toast = Toast.makeText(mContext, getResources().getString(R.string.nomore),//无更多数据
								Toast.LENGTH_SHORT); 
						//显示toast信息 
						toast.show(); 
					}
					equipFragment.pd.dismiss();  
					equipFragment.refresh_lv.onRefreshComplete();
			  }else{
				  equipFragment.refresh_lv.onRefreshComplete();
			  }
		}
	 }

		/**
		 * JSON解析
		 * @param result
		 * @return
		 */
		public List<StationEquip> JSONAnalysis(String result) {
			oneTotal.clear();//解析之前 先清理一下，以保证只存储该页的数据，避免重复。
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String backMsg=jsonObject.getString("msg");
				String code=jsonObject.getString("code");
				if(code.equals("3")){
					oneTotal.isEmpty();
					return oneTotal;
					
				}else {
				if(backMsg.compareTo("success")==0){
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					 for (int i=0;i<jsonArray.length();i++)
			         {
						 JSONObject jsonObjectSon= (JSONObject)jsonArray.opt(i);
						 String equipName=jsonObjectSon.getString("equipmentName");
						 String stationName=jsonObjectSon.getString("stationName");
						 String areaName=jsonObjectSon.getString("areaName");
						 String deviceId=jsonObjectSon.getString("deviceID");
						 String equipmentId=jsonObjectSon.getString("equipmentID");
						 
						 StationEquip ai=new StationEquip();
						 ai.setEquipName(equipName);
						 ai.setEquipArea(areaName);
						 ai.setEquipStationName(stationName);
						 ai.setDeviceId(deviceId);
						 ai.setEquipment_id(equipmentId);

						 oneTotal.add(ai);
			         }
					return oneTotal;//只返回该页的数据
					
				}else{
					return null;
				}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	 
	 private static class DataAdapter extends BaseAdapter{
		  private Context context;
		  private List<StationEquip> list;
		  
			/**
			 * bindData用来传递数据给适配器。
			 * @param list
			 */
			public void bindData(List<StationEquip> list) {
				this.list = list;
			}
		  
		  public DataAdapter(Context context, List<StationEquip> list) {
		      this.context = context;
		      this.list = list;
		  }
			@Override
			public int getCount() {
	            if (list != null){
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
	            if (convertView == null){
	                convertView = LayoutInflater.from(context).inflate(R.layout.station_equip_item,parent,false);
	                vh = new ViewHolder();
	                vh.tv_euqipname = (TextView) convertView.findViewById(R.id.euqipname);
	                vh.tv_equiparea = (TextView) convertView.findViewById(R.id.equiparea);
	                vh.tv_equipstationname = (TextView) convertView.findViewById(R.id.equipstationname);
	                vh.tv_device_id=(TextView) convertView.findViewById(R.id.device_id);
	                vh.tv_euqipment_id=(TextView) convertView.findViewById(R.id.equipment_id);
	                
	                convertView.setTag(vh);
	            }else{
	                vh = (ViewHolder) convertView.getTag();
	            }
	            StationEquip music = (StationEquip) getItem(position);
	            vh.tv_euqipname.setText(music.getEquipName());
	            vh.tv_equiparea.setText(music.getEquipArea());
	            vh.tv_equipstationname.setText(music.getEquipStationName());
	            vh.tv_device_id.setText(music.getDeviceId());
	            vh.tv_euqipment_id.setText(music.getEquipment_id());
	            return convertView;
	        }

	        class ViewHolder{
	        	TextView tv_euqipname;
	            TextView tv_equiparea;
	            TextView tv_equipstationname;
	            TextView tv_euqipment_id;
	            TextView tv_device_id;

	        }
	    }
	
		public void onDestroyView() {
			super.onDestroyView();
		}
		
		
		@Override
		public void onDestroy() {
			super.onDestroy();
		}

}
