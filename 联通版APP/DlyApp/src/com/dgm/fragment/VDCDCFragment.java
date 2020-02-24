package com.dgm.fragment;


import java.text.DecimalFormat;
import java.util.ConcurrentModificationException;
import java.util.List;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.aes.AES;
import com.dgm.bean.ModelBean;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

public class VDCDCFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	boolean connect_status_bit = false;
	private DataAssembleUtil dau;
	public String sendCMD = "";// 上次发送指令CID2标示
	public int countNum = 0;
	
	private int[] m={0,0,0};//获取整流模块数量
	
	private ModelBean[] arr;//记录每个模块下的回数；
	private EditText vdcdc_scdyEdit,vdcdc_mkslEdit,vdcdc_srdyEdit,/*vdcdc_srdlEdit,*/vdcdc_scdlEdit,/*vdcdc_wdEdit,*/vdcdc_kgjEdit,vdcdc_xlEdit,vdcdc_mkgzEdit,vdcdc_jyztEdit,vdcdc_outputVEdit;
	private TextView vdcdc_srdyText,/*vdcdc_srdlText,*/vdcdc_scdlText,/*vdcdc_wdText,*/vdcdc_kgjText,vdcdc_xlText,vdcdc_mkgzText;
	private Button getaibtn,getdibtn,getalarmbtn,vdcdc_kgbtn,vdcdc_kgset,vdcdc_hqcsbtn,vdcdc_setOutputVset,vdcdc_kg2set;
	private RadioButton kgt,kgt2,kgf2;
	
	private Spinner sp=null;
	
	byte[] sl={0x30,0x31};

	// private LinearLayout switchp, data, version, logout;
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
		getActivity().registerReceiver(mGattUpdateReceiver,
				makeGattUpdateIntentFilter());
		if (DevicesInfo2Activity.mBluetoothLeService != null) {
			final boolean result = DevicesInfo2Activity.mBluetoothLeService
					.connect(DevicesInfo2Activity.mDeviceAddress);
		}
		mBaseView = inflater.inflate(R.layout.vdc_dc_fragment, null);
		mContext = getActivity();
		dau = new DataAssembleUtil();
		
		vdcdc_scdyEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_scdyEdit);
		vdcdc_mkslEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_mkslEdit);
		
		vdcdc_srdyEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_srdyEdit);
		//vdcdc_srdlEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_srdlEdit);
		vdcdc_scdlEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_scdlEdit);
		//vdcdc_wdEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_wdEdit);
		
		vdcdc_kgjEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_kgjEdit);
		vdcdc_xlEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_xlEdit);
		vdcdc_mkgzEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_mkgzEdit);
		vdcdc_outputVEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_outputVEdit);
		vdcdc_jyztEdit=(EditText)mBaseView.findViewById(R.id.vdcdc_jyztEdit);
		
		vdcdc_srdyText=(TextView)mBaseView.findViewById(R.id.vdcdc_srdyText);
		//vdcdc_srdlText=(TextView)mBaseView.findViewById(R.id.vdcdc_srdlText);
		vdcdc_scdlText=(TextView)mBaseView.findViewById(R.id.vdcdc_scdlText);
		//vdcdc_wdText=(TextView)mBaseView.findViewById(R.id.vdcdc_wdText);
		
		vdcdc_kgjText=(TextView)mBaseView.findViewById(R.id.vdcdc_kgjText);
		vdcdc_xlText=(TextView)mBaseView.findViewById(R.id.vdcdc_xlText);
		vdcdc_mkgzText=(TextView)mBaseView.findViewById(R.id.vdcdc_mkgzText);
		
		
		getaibtn=(Button)mBaseView.findViewById(R.id.vdcdc_getAIbtn);
		getdibtn=(Button)mBaseView.findViewById(R.id.vdcdc_getDIbtn);
		getalarmbtn=(Button)mBaseView.findViewById(R.id.vdcdc_getAlarmbtn);

		vdcdc_kgbtn=(Button)mBaseView.findViewById(R.id.vdcdc_kgbtn);
		vdcdc_kgset=(Button)mBaseView.findViewById(R.id.vdcdc_kgset);
		
		vdcdc_hqcsbtn=(Button)mBaseView.findViewById(R.id.vdcdc_hqcsbtn);
		vdcdc_setOutputVset=(Button)mBaseView.findViewById(R.id.vdcdc_setOutputVset);
		vdcdc_kg2set=(Button)mBaseView.findViewById(R.id.vdcdc_kg2set);
		
		kgt=(RadioButton) mBaseView.findViewById(R.id.kgt);
		kgt2=(RadioButton) mBaseView.findViewById(R.id.kgt2);
		kgf2=(RadioButton) mBaseView.findViewById(R.id.kgf2);
		
		if(DataAssembleUtil.setPriviledge){
			vdcdc_kgset.setVisibility(View.VISIBLE);
			vdcdc_setOutputVset.setVisibility(View.VISIBLE);
			vdcdc_kg2set.setVisibility(View.VISIBLE);
		}else{
			vdcdc_kgset.setVisibility(View.INVISIBLE);
			vdcdc_setOutputVset.setVisibility(View.INVISIBLE);
			vdcdc_kg2set.setVisibility(View.INVISIBLE);
		}
		
		getaibtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				vdcdc_srdyText.setText(getResources().getString(R.string.dc_mk1srdy));//模块1输入电压(V):
				//vdcdc_srdlText.setText("模块1输入电流(A):");
				vdcdc_scdlText.setText(getResources().getString(R.string.dc_mk1scdl));//模块1输出电流(A):
				//vdcdc_wdText.setText("模块1温度(℃):");
				
				if (sendCMD.length() > 0 && sendCMD.compareTo("42") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x36 };	//D6 42
					byte[] b = dau.AssembleReadData(recByte, "42");
					sendCMD="42";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		
		getdibtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				vdcdc_kgjText.setText(getResources().getString(R.string.dc_mk1kgjzt));//模块1开关机状态:
				vdcdc_xlText.setText(getResources().getString(R.string.dc_mk1xlzt));//模块1限流状态:
				
				if (sendCMD.length() > 0 && sendCMD.compareTo("43") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x36 };	//D6 42
					byte[] b = dau.AssembleReadData(recByte, "43");
					sendCMD="43";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		
		getalarmbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				vdcdc_mkgzText.setText(getActivity().getResources().getString(R.string.dc_mk1gz));//模块1故障:
				
				if (sendCMD.length() > 0 && sendCMD.compareTo("44") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x36 };	//D6 42
					byte[] b = dau.AssembleReadData(recByte, "44");
					sendCMD="44";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		
		vdcdc_hqcsbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("47") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x36 };	//D6 42
					byte[] b = dau.AssembleReadData(recByte, "47");
					sendCMD="47";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		
		vdcdc_setOutputVset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x36 };	//D6 42
					String vUplimit=vdcdc_outputVEdit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);//20180911倍率乘以100
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x30,b1[0],b1[1],b1[2],b1[3]};
						byte[] b = dau.sendSetCmd(recByte, "49",bb);
						sendCMD = "49";
						countNum=0;
						byte[]b10=new byte[20];
						for (int i = 0; i <2; i++) {
							for (int j = 0; j < b10.length; j++) {
								if(b.length>(i*20+j)){
									b10[j]=b[i*20+j];
								}
							}
							try {
								//Thread.sleep(100);
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						}
						sendCMD="";
					}
				}
			}
		});
		
		vdcdc_kg2set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x36 };	//D6 42
					int v=0;
					if(kgt2.isChecked()){
						v=3855;//f0f
					}else{
						v=61680;//f0f0
					}
					byte []b1=dau.intToAscByte4(v);
					byte [] bb={0x38,0x31,b1[0],b1[1],b1[2],b1[3]};
					byte[] b = dau.sendSetCmd(recByte, "49",bb);
					sendCMD = "49";
					countNum=0;
					byte[]b10=new byte[20];
					for (int i = 0; i <2; i++) {
						for (int j = 0; j < b10.length; j++) {
							if(b.length>(i*20+j)){
								b10[j]=b[i*20+j];
							}
						}
						try {
							//Thread.sleep(100);
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//sendCommand(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						sendCMD="";
					}
				}
			}
		});
		
		vdcdc_srdyEdit.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if(m[0]!=0){
					openSelectDialog(0);
				}
				return false;
			}
		});
		
		vdcdc_kgjEdit.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if(m[1]!=0){
					openSelectDialog(1);
				}
				return false;
			}
		});
		vdcdc_mkgzEdit.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if(m[2]!=0){
					openSelectDialog(2);
				}
				return false;
			}
		});
		
		vdcdc_kgbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openSelectDialog(3);
			}
			
		});
		
		//设置模块开或关
		vdcdc_kgset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("45") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x36 };
					int commandType=0;
					if(kgt.isChecked()){
						commandType=15;
					}else{
						commandType=240;
					}

					byte []b1=dau.intToAscByte2(commandType);
//					byte [] bb={b1[0],b1[1],0x30,0x30};
					byte [] bb={b1[0],b1[1],sl[0],sl[1]};
					byte[] b = dau.sendSetCmd(recByte, "45",bb);
					sendCMD = "45";
					countNum=0;
					byte[]b10=new byte[20];
					for (int i = 0; i <2; i++) {
						for (int j = 0; j < b10.length; j++) {
							if(b.length>(i*20+j)){
								b10[j]=b[i*20+j];
							}
						}
						try {
							//Thread.sleep(100);
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//	sendCommand(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
					}
					sendCMD="";
				}
			}
		});
		
		return mBaseView;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().registerReceiver(mGattUpdateReceiver,
				makeGattUpdateIntentFilter());
		if (DevicesInfo2Activity.mBluetoothLeService != null) {
			final boolean result = DevicesInfo2Activity.mBluetoothLeService
					.connect(DevicesInfo2Activity.mDeviceAddress);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(mGattUpdateReceiver);
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
				Toast toast = Toast.makeText(getActivity(), "123123",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	// Handles various events fired by the Service.
	// ACTION_GATT_CONNECTED: connected to a GATT server.
	// ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
	// ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
	// ACTION_DATA_AVAILABLE: received data from the device. This can be a
	// result of read
	// or notification operations.
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

	public String recString = "";


	/**
	 * 收到蓝牙数据解析
	 * 
	 * @param data1
	 */
	private void displayData(String data1) {
		try{
			//data1=new String(AES.decrypt(data1.getBytes(),AES.AES_KEY));
			if (data1 != null && data1.length() > 0) {
				Log.e("@@@@@@@@@@@@@@@@@@", "recString:" + recString);
				recString = recString + data1;
				//	writeFile(recString);
				// recString =
				// "7E323030313430303037303336303030313032353443343030303030303030303030303030303030303030303030303030303030303030303030303030303030303030463336360D";
				int index7E = recString.indexOf("7E");
				int index0D = recString.indexOf("0D");
				if (index7E != -1 && index0D != -1) {
					Log.e("@@@@@@@@@@", recString);
					String cmd = recString.substring(index7E + 2, index0D);
					byte[] b = dau.hex2byte(cmd);// 去掉7E,0D后转byte数组
					boolean ifTrue = dau.ifCheckSum(cmd);// 判断接收到数据校验位与所有数据计算得到的校验位是否一致
					if (ifTrue) {
						//byte[] byteData = { b[9], b[10], b[11] };
						//String dataLength = new String(byteData);
						//int intLength = Integer.parseInt(dataLength, 16);
						//byte[] b2 = new byte[intLength];
						if(b.length<16) return;
						byte[] b2 = new byte[b.length-16];
						//if (b.length > 12 + intLength) {
							//for (int j = 0; j < intLength; j++) {
							for (int j = 0; j < b2.length; j++) {
								b2[j] = b[j + 12];
							}
							// sendCMD = "86";
							
							if (sendCMD.compareTo("42") == 0) {//
								dau.AscToHex2(b2, b2.length);
								arr=null;
								byte[] bb = dau.Data_Close(b2, b2.length);
								DecimalFormat df=new DecimalFormat("0.00");
								if(bb.length<=0){
									return;
								}
								int V = DataAssembleUtil.parseInt(bb[1], bb[0]);// 输出电压
								String aVf=df.format((float)V/100);
								m[0] = bb[2];//模块数量
								
								vdcdc_scdyEdit.setText(aVf);
								vdcdc_mkslEdit.setText(String.valueOf(m[0]));
								
								vdcdc_srdyEdit.setText(df.format((float)(DataAssembleUtil.parseInt(bb[4], bb[3]))/100));// 模块输入电压
								//vdcdc_srdlEdit.setText(df.format((float)(DataAssembleUtil.parseInt(bb[6], bb[5]))/100));// 模块输入电流
								vdcdc_scdlEdit.setText(df.format((float)(DataAssembleUtil.parseInt(bb[8], bb[7]))/100));// 模块输出电流
								//vdcdc_wdEdit.setText(df.format((float)(DataAssembleUtil.parseInt(bb[10], bb[9]))/100));// 模块温度
								
								//循环回数，放入arr里
								arr=new ModelBean[m[0]];  ///////2018-08-17
								for(int i=0;i<m[0];i++){
									int inputV=DataAssembleUtil.parseInt(bb[4+i*9], bb[3+i*9]);
									String iV=df.format((float)inputV/100);// 模块输入电压
									
									int inputA=DataAssembleUtil.parseInt(bb[6+i*9], bb[5+i*9]);
									String iA=df.format((float)inputA/100);// 模块输入电流
									
									int outputA=DataAssembleUtil.parseInt(bb[8+i*9], bb[7+i*9]);
									String oA=df.format((float)outputA/100);// 模块输出电流
									
									int tp=DataAssembleUtil.parseInt(bb[10+i*9], bb[9+i*9]);
									String tp1=df.format((float)tp/100);// 模块温度
									
									arr[i]=new ModelBean(getActivity());
									arr[i].setInputA(iA);
									arr[i].setInputV(iV);
									arr[i].setOutputA(oA);
									arr[i].setTmp(tp1);
								}
								sendCMD = "";
							} else if (sendCMD.compareTo("43") == 0) {//
								dau.AscToHex2(b2, b2.length);
								arr=null;
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length<=0){
									return;
								}
								m[1] = bb[0];//模块数量
								
								ModelBean bean=new ModelBean(getActivity());
								int open=bb[1];
								int curr=bb[2];
								bean.setOpenOrClose(open);
								bean.setCurrentlimit(curr);
								vdcdc_kgjEdit.setText(bean.getOpenOrClose());
								vdcdc_xlEdit.setText(bean.getCurrentlimit());
								
								//循环回数，放入arr里
								arr=new ModelBean[m[1]];  ///////2018-08-17
								for(int i=0;i<m[1];i++){
									open=bb[1+i*3];
									curr=bb[2+i*3];
									
									arr[i]=new ModelBean(getActivity());
									arr[i].setOpenOrClose(open);
									arr[i].setCurrentlimit(curr);
								}
								sendCMD = "";
							} else if (sendCMD.compareTo("44") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length<=0){
									return;
								}
								arr=null;
								m[2] = bb[0];//模块数量
							
								ModelBean bean=new ModelBean(getActivity());
								int open=bb[1];
								bean.setAlarmModeOver(open);
								vdcdc_mkgzEdit.setText(bean.getAlarmModeOver());
								
								//循环回数，放入arr里
								arr=new ModelBean[m[2]];  ///////2018-08-17
								for(int i=0;i<m[2];i++){
									open=bb[1+i*2];
								
									arr[i]=new ModelBean(getActivity());
									arr[i].setAlarmModeOver(open);
								}
								
								open=bb[m[2]*2+1];//模块绝缘故障状态
								if (open == 00) {
									vdcdc_jyztEdit.setText(getResources().getString(R.string.normal));//正常
								} else if(open==01){
									vdcdc_jyztEdit.setText(getResources().getString(R.string.dc_positiveInsulationFault));//正对地绝缘故障
								} else if(open==02){
									vdcdc_jyztEdit.setText(getResources().getString(R.string.dc_nagativeInsulationFault));//负对地绝缘故障
								} else{
									vdcdc_jyztEdit.setText(getResources().getString(R.string.dc_pos_nag_InsulationFault));//正负对地绝缘故障
								}
								sendCMD = "";
								
							} else if (sendCMD.compareTo("47") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length<=0){
									return;
								}
								int scdy = ((bb[1]) & 0xFF | (bb[0] & 0xFF) << 8);
								
								DecimalFormat df=new DecimalFormat("0.00");
								vdcdc_outputVEdit.setText(df.format((float)scdy/100));
								
								int jybh = ((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);
								
								if (jybh == 3855) {//0f0f
									kgt2.setChecked(true);
								} else {
									kgf2.setChecked(true);
								} 
								sendCMD = "";
							}
					} else {
						Toast.makeText(getActivity(),getResources().getString(R.string.acToast5), 1000).show();// toast显示1000ms	收到数据校验失败！
					}
					recString = "";
					sendCMD = "";
				}
			}
		}catch(Exception e){
			
		}
	}

	private String getNumber(byte b) {
		// TODO Auto-generated method stub
		DecimalFormat df=new DecimalFormat("00");
		try{
			return df.format(Integer.parseInt(Integer.toHexString(b&0xff)));
		}catch(Exception e){
			return df.format(Integer.parseInt(Integer.toHexString(b&0xff),16));
		}
		
	}
	
	private void openSelectDialog(final int sel){
		if(sel<3 && arr.length<=0){
			Toast.makeText(mContext,getResources().getString(R.string.rdf_Toast),1).show();//请先获取整流模块数量
			return;
		}
		sp=new Spinner(mContext);
		String mk=getResources().getString(R.string.rdf_mk);//模块
		String mkend="";
		
		if(sel==3){
			arr=null;
			arr=new ModelBean[]{new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())};
		}
				
		String s[]=new String[arr.length];
		for(int i=0;i<arr.length;i++){
			s[i]=mk+(i+1)+mkend;
		}
		ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(mContext,
				android.R.layout.simple_dropdown_item_1line,
				s);
		
		
		sp.setAdapter(arrayAdapter);
		AlertDialog dialog=new AlertDialog.Builder(mContext)
		.setIcon(android.R.drawable.alert_dark_frame)
		.setView(sp)
		.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	sp=null;
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//确定
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获得
                    	int position=sp.getSelectedItemPosition();
                    	switch(sel){
                    		case 0:
                    			vdcdc_srdyText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.dc_srdy));//输入电压(V):
                				//vdcdc_srdlText.setText(sp.getSelectedItem().toString()+"输入电流(A):");
                				vdcdc_scdlText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.dc_scdl));//输出电流(A):
                				//vdcdc_wdText.setText(sp.getSelectedItem().toString()+"温度(℃):");
                            	
                				vdcdc_srdyEdit.setText(arr[position].getInputV());
                				//vdcdc_srdlEdit.setText(arr[position].getInputA());
                				vdcdc_scdlEdit.setText(arr[position].getOutputA());
                				//vdcdc_wdEdit.setText(arr[position].getTmp());
                            	
                    			break;
                    		case 1:
                    			vdcdc_kgjText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.dc_kgjzt));//开关机状态:
                    			vdcdc_xlText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.dc_xlzt));//限流状态:
                    			
                    			vdcdc_kgjEdit.setText(arr[position].getOpenOrClose());//开关机状态
                				vdcdc_xlEdit.setText(arr[position].getCurrentlimit());//限流状态
                				
                    			break;
                    		case 2:
                    			vdcdc_mkgzText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.dc_gz));//故障:
                    			vdcdc_mkgzEdit.setText(arr[position].getAlarmModeOver());//模块故障
                    			break;
                    		case 3:
                        		position=sp.getSelectedItemPosition();
                        		vdcdc_kgbtn.setText(sp.getSelectedItem().toString());
                        		sl=dau.intToAscByte2(position+1);
                        		sp=null;
                        		arr=null;
                        		dialog.dismiss();
                        		break;		
                    	}
                    	
        				sp=null;
                    	dialog.dismiss();
                    }
                })
		.create();
		
		dialog.show();
	}
		
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
	}
	
	
	private void sendCommand(byte[] bb) {
		StringBuffer sb=new StringBuffer(bb.length);
		String tmp;
		for(int i=0;i<bb.length;i++){
			tmp=Integer.toHexString(0xFF & bb[i]);
			if(tmp.length()<2){
				sb.append(0);
			}
			sb.append(tmp.toUpperCase());
		}
		writeFile("cmd_"+sb.toString());
	}*/
}
