package com.dgm.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class BatteryFragmentAlarm extends Fragment {

	private Context mContext;
	private View mBaseView;
	boolean connect_status_bit = false;
	public String sendCMD = "";//上次发送指令CID2标示
	public int countNum=0;
	private Button btnbetalarm;
	private EditText txtbetcutOff1,txtbetcutOff2,txtbetkttmpzt1,txtbetkttmpzt2;
	private ListView gjzt1ListView,gjzt2ListView,bhzt1ListView,bhzt2ListView;
	private List<String> gj1list=new ArrayList<String>();
	private List<String> gj2list=new ArrayList<String>();
	private List<String> bh1list=new ArrayList<String>();
	private List<String> bh2list=new ArrayList<String>();

	private ArrayAdapter<String> bh1Adapter,bh2Adapter,gj1Adapter,gj2Adapter;
	
	private DataAssembleUtil dau;
	
	public BatteryFragmentAlarm() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		getActivity().registerReceiver(mGattUpdateReceiver,
				makeGattUpdateIntentFilter());
		if (DevicesInfo2Activity.mBluetoothLeService != null) {
			final boolean alarm = DevicesInfo2Activity.mBluetoothLeService
					.connect(DevicesInfo2Activity.mDeviceAddress);
		}
		setHasOptionsMenu(true);
		dau = new DataAssembleUtil();
		mBaseView = inflater.inflate(R.layout.fragment_battery_fragment_alarm,
				null);
		mContext = getActivity();
		btnbetalarm = (Button) mBaseView.findViewById(R.id.btnbetalarm);// 获取电池信息

		txtbetcutOff1 = (EditText) mBaseView.findViewById(R.id.txtbetcutOff1);//第一组通信故障
		txtbetcutOff2 = (EditText) mBaseView.findViewById(R.id.txtbetcutOff2);//第二组通信故障

		txtbetkttmpzt1 = (EditText) mBaseView.findViewById(R.id.txtbetkttmpzt1);//第一组壳体温度状态
		txtbetkttmpzt2 = (EditText) mBaseView.findViewById(R.id.txtbetkttmpzt2);//第二组壳体温度状态

		gjzt1ListView=(ListView)mBaseView.findViewById(R.id.gjzt1ListView);//告警状态第一组lv
		gjzt2ListView=(ListView)mBaseView.findViewById(R.id.gjzt2ListView);//告警状态第二组lv
		bhzt1ListView=(ListView)mBaseView.findViewById(R.id.bhzt1ListView);//保护状态第一组lv
		bhzt2ListView=(ListView)mBaseView.findViewById(R.id.bhzt2ListView);//保护状态第二组lv
		
		gj1Adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,gj1list);
		gj2Adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,gj2list);
		
		gjzt1ListView.setAdapter(gj1Adapter);
		gjzt2ListView.setAdapter(gj2Adapter);

		bh1Adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,bh1list);
		bh2Adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,bh2list);
		
		bhzt1ListView.setAdapter(bh1Adapter);
		bhzt2ListView.setAdapter(bh2Adapter);
		btnbetalarm.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("44") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x37 };//D7 44
					byte[] b = dau.AssembleReadData3(recByte, "44");
					sendCMD="44";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
					//displayData("7E3130303144373030423035303030303131303030303030303030303030303030303030303030303030303030303030303030303630303030303030303030303230303030303030303030303030303030303030303030303030303030454538320D");
					//测试用
					//gj1list.clear();
					//gj1list.add(getResources().getString(R.string.fb_dtgygj)+getResources().getString(R.string.alarm));// 单体过压告警
					//gj1Adapter.notifyDataSetChanged();
					//结束
					
				}
			}
		});
		return mBaseView;
	}
	
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(mGattUpdateReceiver);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.battery_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	@Override  
	  public boolean onOptionsItemSelected(MenuItem item) {  
		switch(item.getItemId()){ 
        case R.id.menu_aiInfo: 
        	FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			BatteryFragment umFragment = new BatteryFragment();
			ft.replace(R.id.fl_content2, umFragment, DevicesScanFragment.TAG);
			ft.commit();
            break; 
        } 
		return super.onOptionsItemSelected(item);  
	  }  
	/*private void sendCommand(byte[] bb) {
		StringBuffer sb=new StringBuffer(bb.length);
		String tmp;
		for(int i=0;i<bb.length;i++){
			tmp=Integer.toHexString(0xFF & bb[i]);
			if(tmp.length()<2){
				sb.append(0);
			}
			sb.append(tmp.toUpperCase());
		}
		Log.e("TAG", sb.toString());
		//writeFile("cmd_"+sb.toString());
	}*/
	
	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				// mConnected = true;
				connect_status_bit = true;
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				connect_status_bit = false;
				//2019年9月24日删除密码样式
				DevicesInfo2Activity.titles="first";
				try{
					getActivity().onBackPressed();
				}catch(Exception e){
					
				}
			} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
					.equals(action)) {
					try{
						displayGattServices(DevicesInfo2Activity.mBluetoothLeService
								.getSupportedGattServices());
					}catch(NullPointerException e){
						
					}catch(ConcurrentModificationException e){
						
					}
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				displayData(intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA));
			}
		}
	};
	/*private void writeFile(String data){
		OutputStream out=null;
		try {
			File picDir=this.getView().getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			Log.e("TAG", picDir.getPath());
			File file=new File(picDir,"file01.txt");
			out=new FileOutputStream(file,true);
			out.write((this.getClass().toString()+":"+data+"\r\n").getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/
	public String recString = "";
	private void displayData(String data1) {
		try{
			//data1=new String(AES.decrypt(data1.getBytes(),AES.AES_KEY));
			if (data1 != null && data1.length() > 0) {
				Log.e("@@@@@@@@@@@@@@@@@@", "data1:" + data1);
				recString = recString + data1;
				//writeFile(recString);
				int index7E = recString.indexOf("7E");
				int index0D = recString.indexOf("0D");
				if (index7E != -1 && index0D != -1) {
					Log.e("@@@@@@@@@@", recString);
					String cmd = recString.substring(index7E + 2, index0D);
					byte[] b = dau.hex2byte(cmd);// 去掉7E,0D后转byte数组
					boolean ifTrue = dau.ifCheckSum(cmd);// 判断接收到数据校验位与所有数据计算得到的校验位是否一致
					if (ifTrue) {
						byte[] byteData = { b[9], b[10], b[11] };
						String dataLength = new String(byteData);
						int intLength = Integer.parseInt(dataLength, 16);
						byte[] b2 = new byte[intLength];
						if (b.length > 12 + intLength) {
							for (int j = 0; j < intLength; j++) {
								b2[j] = b[j + 12];
							}
							if (sendCMD.compareTo("44") == 0) {
								DecimalFormat df=new DecimalFormat("0.00");
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int n0=bb[1];//蓄电池组数
								if(n0<=0) return;
								int n1=bb[2];//第一组蓄电池数量
								//32 33 00 00 00 33 00 00 00
								//n个蓄电池单体电压位置2*(n1-1)+3
								//标识温度数量位置
								int pn2=n1+3;
								int n2=bb[pn2];//标识温度数量
								//n个标识温度位置
								int pt=n2+pn2;
								//蓄电池电流告警位置
								int cfdpt=pt+1;
								//保护状态1位置
								int phztpt=cfdpt+3;
								
								//告警状态1位置
								cfdpt=cfdpt+10;
								gj1list.clear();
								bh1list.clear();
								
								if((bb[cfdpt] >>0 & 1)!=0){
									gj1list.add(getResources().getString(R.string.fb_dtgygj)+getResources().getString(R.string.alarm));// 单体过压告警
								}
								if((bb[cfdpt] >>1 & 1)!=0){
									gj1list.add(getResources().getString(R.string.fb_dtdygj)+getResources().getString(R.string.alarm));// 单体低压告警
								}
								if((bb[cfdpt] >>2 & 1)!=0){
									gj1list.add(getResources().getString(R.string.fb_zygygj)+getResources().getString(R.string.alarm));// 总压过压告警
								}
								if((bb[cfdpt] >>3 & 1)!=0){
									gj1list.add(getResources().getString(R.string.fb_zydygj)+getResources().getString(R.string.alarm));// 总压低压告警
								}
								
								if((bb[cfdpt] >>4 & 1)!=0){
									gj1list.add(getResources().getString(R.string.fb_chargeAlarm)+getResources().getString(R.string.alarm));// 充电过流告警
								}
								
								if((bb[cfdpt] >>5 & 1)!=0){
									gj1list.add(getResources().getString(R.string.fb_releaseAlarm)+getResources().getString(R.string.alarm));// 放电过流告警
								}
								
								//获取保护状态
								if((bb[phztpt] >>0 & 1)!=0){
									bh1list.add(getResources().getString(R.string.fb_dtgybh));// 单体过压保护
								}
								if((bb[phztpt] >>1 & 1)!=0){
									bh1list.add(getResources().getString(R.string.fb_dtdybh));// 单体低压保护
								}
								if((bb[phztpt] >>2 & 1)!=0){
									bh1list.add(getResources().getString(R.string.fb_zygybh));// 总压过压保护
								}
								if((bb[phztpt] >>3 & 1)!=0){
									bh1list.add(getResources().getString(R.string.fb_zydybh));// 总压低压保护
								}
								
								if((bb[phztpt] >>4 & 1)!=0){
									bh1list.add(getResources().getString(R.string.fb_cdglbh));// 充电过流保护
								}
								
								if((bb[phztpt] >>5 & 1)!=0){
									bh1list.add(getResources().getString(R.string.fb_fdglbh));// 放电过流保护
								}
								gj1Adapter.notifyDataSetChanged();
								bh1Adapter.notifyDataSetChanged();
								
								if(n2>0){
									if(bb[pn2+6]==0x00){//壳体温度状态
										txtbetkttmpzt1.setText(getResources().getString(R.string.normal));//	正常
									}else if(bb[pn2+6]==0x01){
										txtbetkttmpzt1.setText(getResources().getString(R.string.belowTemp));//低温告警
									}else{
										txtbetkttmpzt1.setText(getResources().getString(R.string.overTemp));//高温告警
										
									}
								}
								
								if(bb[cfdpt+3]==0x00){//通信故障
									txtbetcutOff1.setText(getResources().getString(R.string.normal));//	正常
								}else{
									txtbetcutOff1.setText(getResources().getString(R.string.alarm));//	告警
								}
								
								if(n0>1){
									cfdpt=cfdpt+4;
									
									n1=bb[cfdpt];//第二组蓄电池数量
									//	n个标识温度位置
									pn2=n1+3;
									n2=bb[pn2];//标识温度数量
									//n个标识温度位置
									pt=n2+pn2;
									//蓄电池电流告警位置
									cfdpt=pt+1;
									
									//保护状态1位置
									phztpt=cfdpt+3;
									
									//告警状态1位置
									cfdpt=cfdpt+10;
									
									gj2list.clear();
									bh2list.clear();
									if((bb[cfdpt] >>0 & 1)!=0){
										gj2list.add(getResources().getString(R.string.fb_dtgygj)+getResources().getString(R.string.alarm));// 单体过压告警
									}
									if((bb[cfdpt] >>1 & 1)!=0){
										gj2list.add(getResources().getString(R.string.fb_dtdygj)+getResources().getString(R.string.alarm));// 单体低压告警
									}
									if((bb[cfdpt] >>2 & 1)!=0){
										gj2list.add(getResources().getString(R.string.fb_zygygj)+getResources().getString(R.string.alarm));// 总压过压告警
									}
									if((bb[cfdpt] >>3 & 1)!=0){
										gj2list.add(getResources().getString(R.string.fb_zydygj)+getResources().getString(R.string.alarm));// 总压低压告警
									}
									
									if((bb[cfdpt] >>4 & 1)!=0){
										gj2list.add(getResources().getString(R.string.fb_chargeAlarm)+getResources().getString(R.string.alarm));// 充电过流告警
									}
									
									if((bb[cfdpt] >>5 & 1)!=0){
										gj2list.add(getResources().getString(R.string.fb_releaseAlarm)+getResources().getString(R.string.alarm));// 放电过流告警
									}

									//获取保护状态
									if((bb[phztpt] >>0 & 1)!=0){
										bh2list.add(getResources().getString(R.string.fb_dtgybh));// 单体过压保护
									}
									if((bb[phztpt] >>1 & 1)!=0){
										bh2list.add(getResources().getString(R.string.fb_dtdybh));// 单体低压保护
									}
									if((bb[phztpt] >>2 & 1)!=0){
										bh2list.add(getResources().getString(R.string.fb_zygybh));// 总压过压保护
									}
									if((bb[phztpt] >>3 & 1)!=0){
										bh2list.add(getResources().getString(R.string.fb_zydybh));// 总压低压保护
									}
									
									if((bb[phztpt] >>4 & 1)!=0){
										bh2list.add(getResources().getString(R.string.fb_cdglbh));// 充电过流保护
									}
									
									if((bb[phztpt] >>5 & 1)!=0){
										bh2list.add(getResources().getString(R.string.fb_fdglbh));// 放电过流保护
									}
									gj2Adapter.notifyDataSetChanged();
									bh2Adapter.notifyDataSetChanged();
									
									if(n2>0){
										if(bb[pn2+6]==0x00){//壳体温度状态
											txtbetkttmpzt1.setText(getResources().getString(R.string.normal));//	正常
										}else if(bb[pn2+6]==0x01){
											txtbetkttmpzt1.setText(getResources().getString(R.string.belowTemp));//低温告警
										}else{
											txtbetkttmpzt1.setText(getResources().getString(R.string.overTemp));//高温告警
											
										}
									}
									
									if(bb[cfdpt+3]==0x00){//通信故障
										txtbetcutOff2.setText(getResources().getString(R.string.normal));//	正常
									}else{
										txtbetcutOff2.setText(getResources().getString(R.string.alarm));//	告警
									}
								}
								
							}
						} else {
							if(getActivity()==null) return;
							Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 1000).show();// toast显示1000ms	收到数据长度异常！
						}
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.acToast5), 1000).show();// toast显示1000ms	收到数据校验失败！
					}
					recString = "";
					sendCMD = "";
				}
			}
		}catch(Exception e){
			
		}
	}
		
	private static IntentFilter makeGattUpdateIntentFilter(){
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	// Demonstrates how to iterate through the supported GATT
	// Services/Characteristics.
	// In this sample, we populate the data structure that is bound to the
	// ExpandableListView
	// on the UI.
	private void displayGattServices(List<BluetoothGattService> gattServices) {
		if (gattServices == null)
			return;

		if (gattServices.size() > 0
				&& DevicesInfo2Activity.mBluetoothLeService
						.get_connected_status(gattServices) >= 4) {
			if (connect_status_bit) {
				DevicesInfo2Activity.mBluetoothLeService.enable_JDY_ble(true);
				try {
					Thread.currentThread();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				DevicesInfo2Activity.mBluetoothLeService.enable_JDY_ble(true);
			} else {
				Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.Exception),//异常错误
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
}
