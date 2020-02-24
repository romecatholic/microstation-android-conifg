package com.dgm.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.aes.AES;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;
/**
 * 交流配电参数界面
 * @author Administrator
 *
 */
public class AcDistributionFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private Button getSysMoni, /*getSysSwitch,*/ getAlarm, 
	getConfig, setConfig,setVDown,setAUp,setPUp,setPDown,
	setvoltageClear,setcurrentClear,setpowerClear;//,ac_queryEngsetBtn;
	boolean connect_status_bit = false;
	private DataAssembleUtil dau;
	public String sendCMD = "";//上次发送指令CID2标示
	public int countNum=0;
	//private TextView ac_startTimeEdit,ac_endTimeEdit;
	public EditText /*sysScreen,*/ sysInputVA, sysInputVB, sysInputVC,
			sysFrequency, sysOutputAA, sysOutputAB, sysOutputAC, switchScreen,
			/*switchStatus, alarmScreen,*/ alarmInputVA, alarmInputVB,
			alarmInputVC, alarmFrequency, /*alarmFuse,*/ alarmOutputAA,
			alarmOutputAB, alarmOutputAC, alarmThunderError, alarmPowerCut,
			conVupperlimit, conVlowerlimit, conOutputAupperlimit,
			conFrequencyUpperlimit, conFrequencyLowerlimit,sysPa,sysFpa,
			sysZdn,sysZxygdn,sysFxygdn,voltageClearEdit,currentClearEdit;
			//,ac_queryEngEdit;
	//CountDownLatch count=new CountDownLatch(1);
	List<Map<String, String>> ulist3 = new ArrayList<Map<String, String>>();
	private SimpleAdapter engListAdapter;
	public AlertDialog engDialog;
	public ListView engListView=null;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==0){
            	Object object = msg.obj;
            	try {
            		engListView.setAdapter(engListAdapter);
    				if(engDialog==null){
    					engDialog=new AlertDialog.Builder(mContext)
    				.setIcon(android.R.drawable.alert_dark_frame)
    				.setView(engListView)
    				.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
    		                    @Override
    		                    public void onClick(DialogInterface dialog, int which) {
    		                    	engDialog.dismiss();
    		                    }
    		                }).create();
    				
    				}
    				engDialog.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
            }/*else if(msg.what==1){
            	try {
					count.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	getSysMoni.callOnClick();
            }*/
        };
        
	};


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
		dau = new DataAssembleUtil();
		mBaseView = inflater.inflate(R.layout.ac_distribute_fragment, null);
		mContext = getActivity();
		getSysMoni = (Button) mBaseView.findViewById(R.id.getacsysmoni);// 获取系统模拟量
		//getSysSwitch = (Button) mBaseView.findViewById(R.id.getacsysswitch);// 获取系统开关输入状态
		getAlarm = (Button) mBaseView.findViewById(R.id.getacalarm);// 获取告警状态
		getConfig = (Button) mBaseView.findViewById(R.id.getacconfig);// 获取参数
		setConfig = (Button) mBaseView.findViewById(R.id.setacconfig);// 设置参数
		setVDown = (Button) mBaseView.findViewById(R.id.setacconfig_down);// 设置电压下限参数
		setAUp = (Button) mBaseView.findViewById(R.id.setacA_up);// 设置参数
		setPUp = (Button) mBaseView.findViewById(R.id.setacP_up);// 设置参数
		setPDown = (Button) mBaseView.findViewById(R.id.setacP_down);// 设置参数
		setvoltageClear = (Button) mBaseView.findViewById(R.id.setvoltageClear);// 设置参数
		setcurrentClear = (Button) mBaseView.findViewById(R.id.setcurrentClear);// 设置参数
		setpowerClear = (Button) mBaseView.findViewById(R.id.setpowerClear);// 设置参数
		//ac_queryEngsetBtn = (Button) mBaseView.findViewById(R.id.ac_queryEngsetBtn);

		// 获取系统模拟量
		//sysScreen = (EditText) mBaseView.findViewById(R.id.acsysscreenText);// 交流屏数量
		sysInputVA = (EditText) mBaseView.findViewById(R.id.acsysinputVAText);// 输入相电压A
		sysInputVB = (EditText) mBaseView.findViewById(R.id.acsysinputVBText);// 输入相电压B
		sysInputVC = (EditText) mBaseView.findViewById(R.id.acsysinputVCText);// 输入相电压C
		sysFrequency = (EditText) mBaseView.findViewById(R.id.acsysfrequencyText);// 输入频率
		sysOutputAA = (EditText) mBaseView.findViewById(R.id.acsysoutputAAText);// 输出电流A
		sysOutputAB = (EditText) mBaseView.findViewById(R.id.acsysoutputABText);// 输出电流B
		sysOutputAC = (EditText) mBaseView.findViewById(R.id.acsysoutputACText);// 输出电流C

		sysPa = (EditText) mBaseView.findViewById(R.id.sysPa);// A相有功功率Pa(单相)
		sysFpa = (EditText) mBaseView.findViewById(R.id.sysFpa);// A相功率因数PFA(单相)
		sysZdn = (EditText) mBaseView.findViewById(R.id.sysZdn);// 当前有功总电能（单相）
		sysZxygdn = (EditText) mBaseView.findViewById(R.id.sysZxygdn);// 当前正向有功总电能（单相）
		sysFxygdn = (EditText) mBaseView.findViewById(R.id.sysFxygdn);// 当前反向有功总电能（单相）
		currentClearEdit = (EditText) mBaseView.findViewById(R.id.currentClearEdit);// 交流电表电流零漂清除（单位：A）
		voltageClearEdit = (EditText) mBaseView.findViewById(R.id.voltageClearEdit);// 交流电表电压零漂清除（单位：V）

		// 获取系统开关输入状态
		/*switchScreen = (EditText) mBaseView
				.findViewById(R.id.acswitchscreenText);*/// 交流屏数量
		/*switchStatus = (EditText) mBaseView
				.findViewById(R.id.acswitchstatusText);*/// 输出开关状态

		// 获取告警状态
		//alarmScreen = (EditText) mBaseView.findViewById(R.id.acalarmscreenText);// 交流屏数量
		alarmInputVA = (EditText) mBaseView
				.findViewById(R.id.acalarminputVAText);// 输出相电压A
		alarmInputVB = (EditText) mBaseView
				.findViewById(R.id.acalarminputVBText);// 输出相电压B
		alarmInputVC = (EditText) mBaseView
				.findViewById(R.id.acalarminputVCText);// 输出相电压C
		alarmFrequency = (EditText) mBaseView
				.findViewById(R.id.acalarmfrequencyText);// 频率
		//alarmFuse = (EditText) mBaseView.findViewById(R.id.acalarmfuseText);// 熔丝开关状态
		alarmOutputAA = (EditText) mBaseView
				.findViewById(R.id.acalarmoutputAAText);// 输出电流A
		alarmOutputAB = (EditText) mBaseView
				.findViewById(R.id.acalarmoutputABText);// 输出电流B
		alarmOutputAC = (EditText) mBaseView
				.findViewById(R.id.acalarmoutputACText);// 输出电流C
		alarmThunderError = (EditText) mBaseView
				.findViewById(R.id.acalarmthundererrorText);// 防雷器故障告警
		alarmPowerCut = (EditText) mBaseView
				.findViewById(R.id.acalarmpowercutText);// 停电告警

		// 获取和设置参数
		conVupperlimit = (EditText) mBaseView
				.findViewById(R.id.acconfigVupperlimitText);// 交流相电压上限
		conVlowerlimit = (EditText) mBaseView
				.findViewById(R.id.acconfigVlowerlimitText);// 交流相电压下限
		conOutputAupperlimit = (EditText) mBaseView
				.findViewById(R.id.acconfigoutputAupperlimitText);// 交流输出电流上限
		conFrequencyUpperlimit = (EditText) mBaseView
				.findViewById(R.id.acconfigfrequencyupperlimitText);// 频率上限
		conFrequencyLowerlimit = (EditText) mBaseView
				.findViewById(R.id.acconfigfrequencylowerlimitText);// 频率下限
		//ac_queryEngEdit = (EditText) mBaseView.findViewById(R.id.ac_queryEngEdit);
		//ac_startTimeEdit=(TextView) mBaseView.findViewById(R.id.ac_startTimeEdit);
		//ac_endTimeEdit=(TextView) mBaseView.findViewById(R.id.ac_endTimeEdit);
		engListView=new ListView(mContext);

		engListAdapter=new SimpleAdapter(mContext, ulist3,
                R.layout.list_item_2, new String[] { "xh","sj","dn","rq" }
		, new int[] { R.id.xh,R.id.sj,R.id.dn,R.id.rq });
		
		if(DataAssembleUtil.setPriviledge){
			setConfig.setVisibility(View.VISIBLE);
			setAUp.setVisibility(View.VISIBLE);
			setPDown.setVisibility(View.VISIBLE);
			setPUp.setVisibility(View.VISIBLE);
			setVDown.setVisibility(View.VISIBLE);
			
		}else{
			setConfig.setVisibility(View.INVISIBLE);
			setAUp.setVisibility(View.INVISIBLE);
			setPDown.setVisibility(View.INVISIBLE);
			setPUp.setVisibility(View.INVISIBLE);
			setVDown.setVisibility(View.INVISIBLE);
		}
		getSysMoni.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("42") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 42
					byte[] b2 = { 0x46, 0x46 };
					byte[] bb = dau.sendSetCmd(recByte, "42", b2);
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "42";
					countNum=0;
				}
			}
		});
		/*getSysSwitch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("43") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), "上条指令尚未收到数据，CID2：" + sendCMD,
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };
					byte [] b2={0x46,0x46};
					byte[] bb = dau.sendSetCmd(recByte, "43", b2);
					sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD="43";
					countNum=0;
				}
			}
		});*/
		getAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("44") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 44
					byte [] b2={0x46,0x46};
					byte[] bb = dau.sendSetCmd(recByte, "44", b2);
					sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD="44";
					countNum=0;
				}
			}
		});
		getConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("47") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 47
					byte[] b = dau.AssembleReadData(recByte, "47");
					sendCMD = "47";
					countNum=0;
					//sendCommand(b);					
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		setConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					String vUplimit=conVupperlimit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
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
							//sendCommand(b10);							
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						}
						sendCMD="";
					}
					
				}
			}
		});
		
		setVDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					String vUplimit=conVlowerlimit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
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
						}
						sendCMD="";
					}
					
				}
			}
		});
		
		setAUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
									1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					String vUplimit=conOutputAupperlimit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x36,b1[0],b1[1],b1[2],b1[3]};
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
						}
						sendCMD="";
					}
					
				}
			}
		});
		setPUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					String vUplimit=conFrequencyUpperlimit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x33,b1[0],b1[1],b1[2],b1[3]};
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
						}
						sendCMD="";
					}
					
				}
			}
		});
		
		setPDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					String vUplimit=conFrequencyLowerlimit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x34,b1[0],b1[1],b1[2],b1[3]};
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
						}
						sendCMD="";
					}
					
				}
			}
		});
		setvoltageClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					String vUplimit=voltageClearEdit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*10);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x37,b1[0],b1[1],b1[2],b1[3]};
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
						}
						sendCMD="";
					}
					
				}
			}
		});

		setcurrentClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					String vUplimit=currentClearEdit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*10);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x38,b1[0],b1[1],b1[2],b1[3]};
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
						}
						sendCMD="";
					}
					
				}
			}
		});

		setpowerClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 49
					int v= 224;
					byte []b1=dau.intToAscByte4(v);
					byte [] bb={0x38,0x39,b1[0],b1[1],b1[2],b1[3]};
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
					}
					sendCMD="";					
				}
			}
		});
		/*ac_queryEngsetBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("80") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					try{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//D2H
						String vUplimit=ac_queryEngEdit.getText().toString();
						if(vUplimit.length()==0){
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
									1500).show();// toast显示1000ms
						}else{
							int vUp=Integer.parseInt(vUplimit);
							if(vUp<0 || vUp>90){
								Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valueOverRange),//值超范围
										1500).show();// toast显示1000ms
								return;
							}
							byte [] b1=dau.intToAscByte2(vUp);
							byte [] bb={b1[0],b1[1]};
							byte[] b = dau.sendSetCmd(recByte, "80",bb);
							sendCMD = "80";
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
								sendCommand(b10);							
								DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
								//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
							}
						}
					}catch(Exception e){
						return ;
					}
				}
			}
		});	*/
		/*ac_startTimeEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				DatePickerDialog dialog = new DatePickerDialog(mBaseView.getContext(), new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						ac_startTimeEdit.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
					}
				}, 1980, 0, 1);  
	            DatePicker datePicker = dialog.getDatePicker();  
	            try {
					datePicker.setMinDate(format1.parse("1970-01-01").getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	            try {
					datePicker.setMaxDate(format1.parse("2013-01-07").getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	            dialog.show();  
   			}
		});
		ac_endTimeEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				DatePickerDialog dialog = new DatePickerDialog(mBaseView.getContext(), new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						ac_endTimeEdit.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
					}
				}, 1980, 0, 1);  
	            DatePicker datePicker = dialog.getDatePicker();  
	            try {
					datePicker.setMinDate(format1.parse("1970-01-01").getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	            try {
					datePicker.setMaxDate(format1.parse("2013-01-07").getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	            dialog.show();  
   			}
		});
    	count.countDown();*/
		return mBaseView;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e("@@@@@@@@@@@@@@@@@@", "AcDistributionFragment:onResume@@@@@@@");
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
		Log.e("@@@@@@@@@@@@@@@@@@",
				"AcDistributionFragment%%%%%%onPause@@@@@@@");
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
//				Toast toast = Toast.makeText(getActivity(), "123123",
//						Toast.LENGTH_SHORT);
//				toast.show();
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
				/*	Message message = handler.obtainMessage();
					message.what = 1;
					handler.sendMessage(message);*/
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
		File picDir=this.getView().getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		Log.e("TAG", picDir.getPath());
		File file=new File(picDir,"file01.txt");
		OutputStream out=null;
		try {
			out=new FileOutputStream(file,true);
			out.write((this.getClass().toString()+":"+data+"\r\n").getBytes());
			Toast.makeText(this.getActivity(), "缓存OK!", 1).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
				//recString="7E323030313430303037303336303030313032353443343030303030303030303030303030303030303030303030303030303030303030303030303030303030303030463336360D";
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
							if (sendCMD.compareTo("42") == 0) {//
								//writeFile(recString);
								DecimalFormat df=new DecimalFormat("0.00");
								DecimalFormat df2=new DecimalFormat("0.000");
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								//int mString = bb[1];
								//int mmString = bb[0];
								int aV = DataAssembleUtil.parseInt(bb[4], bb[3]);//((bb[4]) & 0xFF | (bb[3] & 0xFF) << 8);// 输入相电压 AB/A
								int bV = DataAssembleUtil.parseInt(bb[6], bb[5]);//((bb[6]) & 0xFF | (bb[5] & 0xFF) << 8);// 输入相电压 BC/B
								int cV = DataAssembleUtil.parseInt(bb[8], bb[7]);//((bb[8]) & 0xFF | (bb[7] & 0xFF) << 8);// 输入相电压CA/C
								String aVf=df.format((float)aV/100);
								String bVf=df.format((float)bV/100);
								String cVf=df.format((float)cV/100);
								int pingLv = DataAssembleUtil.parseUnsignedInt(bb[10], bb[9]);//((bb[10]) & 0xFF | (bb[9] & 0xFF) << 8);// 输入频率
								int p = bb[11];//
								//int mmLength = (9 + p * 2) * mmString;
								/*if(bb.length>=mmLength+8){
									int aA = ((bb[mmLength + 4]) & 0xFF | (bb[mmLength + 3] & 0xFF) << 8);// 交流屏输出电流 A
									int bA = ((bb[mmLength + 6]) & 0xFF | (bb[mmLength + 5] & 0xFF) << 8);// 交流屏输出电流B																								// B
									int cA = ((bb[mmLength + 8]) & 0xFF | (bb[mmLength + 7] & 0xFF) << 8);// 交流屏输出电流C
									String aAf=df.format((float)aA/100);
									String bAf=df.format((float)bA/100);
									String cAf=df.format((float)cA/100);
									sysOutputAA.setText(aAf);
									sysOutputAB.setText(bAf);
									sysOutputAC.setText(cAf);
								}*/
								int aA = DataAssembleUtil.parseInt(bb[13], bb[12]);//((bb[13]) & 0xFF | (bb[12] & 0xFF) << 8);// 交流输入电流 A
								int bA = DataAssembleUtil.parseInt(bb[15], bb[14]);//((bb[15]) & 0xFF | (bb[14] & 0xFF) << 8);// 交流输入电流B																								// B
								int cA = DataAssembleUtil.parseInt(bb[17], bb[16]);//((bb[17]) & 0xFF | (bb[16] & 0xFF) << 8);// 交流输入电流C								
								long pa = DataAssembleUtil.parseInt(bb[18], bb[19],bb[20], bb[21]);//A相有功功率Pa(单相)
								long fpa= DataAssembleUtil.parseInt(bb[22], bb[23],bb[24], bb[25]);//A相功率因数PFA(单相)
								long zdn= DataAssembleUtil.parseUnsignedInt(bb[26], bb[27],bb[28], bb[29]);//当前有功总电能（单相）
								long zxygdn= DataAssembleUtil.parseUnsignedInt(bb[30], bb[31],bb[32], bb[33]);//当前正向有功总电能（单相）
								long fxygdn= DataAssembleUtil.parseUnsignedInt(bb[34], bb[35],bb[36], bb[37]);//当前反向有功总电能（单相）

								String aAf=df.format((float)aA/100);
								String bAf=df.format((float)bA/100);
								String cAf=df.format((float)cA/100);
								String paf = df.format((float)pa/100);
								String fpaf= df.format((float)fpa/1000);
								String zdnf= df2.format((float)zdn/1000);
								String zxygdnf= df2.format((float)zxygdn/1000);
								String fxygdnf= df2.format((float)fxygdn/1000);

								sysOutputAA.setText(aAf);
								sysOutputAB.setText(bAf);
								sysOutputAC.setText(cAf);
								//sysScreen.setText(""+mString);
								sysInputVA.setText(aVf);
								sysInputVB.setText(bVf);
								sysInputVC.setText(cVf);
								sysFrequency.setText(""+pingLv/1000);
								sysPa.setText(paf);
								sysFpa.setText(fpaf);
								sysZdn.setText(zdnf);
								sysZxygdn.setText(zxygdnf);
								sysFxygdn.setText(fxygdnf);
														
							/*}else if (sendCMD.compareTo("44") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int mString = bb[1];//M
								int mString2 = bb[2];//检测的输出开关数量 m
								int switch1 = bb[3];//检测的输出开关数量 m
							
								switchScreen.setText(mString);
								/*if(switch1==0){
									switchStatus.setText("闭合");
								}else if(switch1==1){
									switchStatus.setText("断开");
								}*/
							}else if (sendCMD.compareTo("44") == 0) {//
								//writeFile(recString);
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int mString = bb[1];//M
								int mmString = bb[2];
								int aV = bb[3];//
								int bV = bb[4];
								int cV = bb[5];
								int pingLv = bb[6];//频率
								int switchM = bb[7];
								int p=bb[8+switchM];
								int powerCut=bb[9+switchM]& 0xFF;
								int ThunderError = bb[10+switchM] & 0xFF;
								String hv = Integer.toHexString(ThunderError);
					        	String hv2 = Integer.toHexString(powerCut);
								int length=(5+switchM+1+p)*mmString;
								int aA=bb[3+length];//电流
								//int aA=bb[length+1];//电流
								int bA=bb[4+length];
								//int bA=bb[length+2];
								int cA=bb[5+length];
								//int cA=bb[length+3];
								//alarmScreen.setText(""+mString);
							
								if(hv.compareTo("81")==0){
									alarmThunderError.setText(getResources().getString(R.string.lightningError));//系统防雷器故障
								}else{
									alarmThunderError.setText(getResources().getString(R.string.normal));//正常
								}
								if(hv2.compareTo("80")==0){
									alarmPowerCut.setText(getResources().getString(R.string.sysPowerOff));//系统停电
								}else{
									alarmPowerCut.setText(getResources().getString(R.string.normal));//正常
								}
								//输出相电压A
								if(aV==0){
									alarmInputVA.setText(getResources().getString(R.string.normal));//正常
								}else if(aV==1){
									alarmInputVA.setText(getResources().getString(R.string.lowerlimit));//低于下限
								}else if(aV==2){
									alarmInputVA.setText(getResources().getString(R.string.upperlimit));//高于上限
								}else if(aV==3){
									alarmInputVA.setText(getResources().getString(R.string.lackphase));//缺相
								}
								//输出相电压B
								if(bV==0){
									alarmInputVB.setText(getResources().getString(R.string.normal));//正常
								}else if(bV==1){
									alarmInputVB.setText(getResources().getString(R.string.lowerlimit));//低于下限
								}else if(bV==2){
									alarmInputVB.setText(getResources().getString(R.string.upperlimit));//高于上限
								}else if(bV==3){
									alarmInputVB.setText(getResources().getString(R.string.lackphase));//缺相
								}
								//输出相电压C
								if(cV==0){
									alarmInputVC.setText(getResources().getString(R.string.normal));//正常
								}else if(cV==1){
									alarmInputVC.setText(getResources().getString(R.string.lowerlimit));//低于下限
								}else if(cV==2){
									alarmInputVC.setText(getResources().getString(R.string.upperlimit));//高于上限
								}else if(cV==3){
									alarmInputVC.setText(getResources().getString(R.string.lackphase));//缺相
								}
								//频率
								if(pingLv==0){
									alarmFrequency.setText(getResources().getString(R.string.normal));//正常
								}else if(pingLv==1){
									alarmFrequency.setText(getResources().getString(R.string.lowerlimit));//低于下限
								}else if(pingLv==2){
									alarmFrequency.setText(getResources().getString(R.string.upperlimit));//高于上限
								}
								//熔丝开关状态
								//alarmFuse.setText("未知");
								//输出电流A
								if(aA==0){
									alarmOutputAA.setText(getResources().getString(R.string.normal));//正常
								}else if(aA==1){
									alarmOutputAA.setText(getResources().getString(R.string.lowerlimit));//低于下限
								}else if(aA==2){
									alarmOutputAA.setText(getResources().getString(R.string.upperlimit));//高于上限
								}
								//输出电流B
								if(bA==0){
									alarmOutputAB.setText(getResources().getString(R.string.normal));//正常
								}else if(bA==1){
									alarmOutputAB.setText(getResources().getString(R.string.lowerlimit));//低于下限
								}else if(bA==2){
									alarmOutputAB.setText(getResources().getString(R.string.upperlimit));//高于上限
								}
								//输出电流C
								if(cA==0){
									alarmOutputAC.setText(getResources().getString(R.string.normal));//正常
								}else if(cA==1){
									alarmOutputAC.setText(getResources().getString(R.string.lowerlimit));//低于下限
								}else if(bA==2){
									alarmOutputAC.setText(getResources().getString(R.string.upperlimit));//高于上限
								}
							}else if (sendCMD.compareTo("47") == 0) {
								//writeFile(recString);
								DecimalFormat df=new DecimalFormat("0.00");
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length>=10){
									int vUp = DataAssembleUtil.parseInt(bb[1], bb[0]);//((bb[1]) & 0xFF | (bb[0] & 0xFF) << 8);// 
									int vLower = DataAssembleUtil.parseInt(bb[3], bb[2]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);// 
									int aUp = DataAssembleUtil.parseInt(bb[14], bb[13]);//((bb[14]) & 0xFF | (bb[13] & 0xFF) << 8);//
									int pingLvUp = DataAssembleUtil.parseInt(bb[7], bb[6]);//((bb[7]) & 0xFF | (bb[6] & 0xFF) << 8);// 
									int pingLvLower = DataAssembleUtil.parseInt(bb[9], bb[8]);//((bb[9]) & 0xFF | (bb[8] & 0xFF) << 8);// 
									//int p=bb[15];
									//16,17,18,19
									long vol=DataAssembleUtil.parseInt(bb[16], bb[15]);
									long cur=DataAssembleUtil.parseInt(bb[18], bb[17]);
									
									String vUf=df.format((float)vUp/100);
									String vLf=df.format((float)vLower/100);
									String aUf=df.format((float)aUp/100);
									String pUf=df.format((float)pingLvUp/100);
									String pLf=df.format((float)pingLvLower/100);
									String pvol=df.format((float)vol/10);
									String pcur=df.format((float)cur/10);
									conVupperlimit.setText(vUf);
									conVlowerlimit.setText(vLf);
									conOutputAupperlimit.setText(aUf);
									conFrequencyUpperlimit.setText(pUf);
									conFrequencyLowerlimit.setText(pLf);
									voltageClearEdit.setText(pvol);
									currentClearEdit.setText(pcur);
								}else{
									Toast.makeText(getActivity(), getResources().getString(R.string.acToast3), 500).show();//收到数据异常！
								}
							}else if(sendCMD.compareTo("80") == 0){
								dau.AscToHex2(b2, b2.length);
								final byte[] bb = dau.Data_Close(b2, b2.length);
								new Thread(new Runnable() {
									@Override
									public void run() {
										try {
											DecimalFormat df=new DecimalFormat("0.00");
											ulist3.clear();
											int totalcount = DataAssembleUtil.parseInt(bb[1], bb[0]);// 电能记录总条数
											int count=bb[2];//一次响应总电能历史记录条数
											
											int base=3;
											if(count>0){
												Map<String, String> keyValuePair = new HashMap<String, String>();
												keyValuePair.put("xh",getResources().getString(R.string.pv_xh));
												keyValuePair.put("sj", getResources().getString(R.string.pv_sj));//查询出来的信息
												keyValuePair.put("dn", getResources().getString(R.string.pv_dn));//查询出来的信息
												//keyValuePair.put("rq", getResources().getString(R.string.pv_rq));//查询出来的信息
												ulist3.add(keyValuePair);
											}
											for(int i = 0;i<count;i++){
												String sj=bb[base]+"/"+bb[base+1]+"/"+bb[base+2];
												long dn=DataAssembleUtil.parseUnsignedInt(bb[base+3],bb[base+4],bb[base+5],bb[base+6]);
												//int rq=DataAssembleUtil.parseInt(bb[base+8],bb[base+7]);
												Map<String, String> keyValuePair = new HashMap<String, String>();
												keyValuePair.put("xh",String.valueOf(i+1));
												keyValuePair.put("sj", String.valueOf(sj));
												keyValuePair.put("dn", String.valueOf(df.format((float)dn/100)));
												//keyValuePair.put("rq", String.valueOf(rq));
												ulist3.add(keyValuePair);
												base=base+9;
											}
											engListAdapter.notifyDataSetChanged();

											Message message = handler.obtainMessage();
		            						message.what = 0;
		            						handler.sendMessage(message);
					            		} catch (Exception e) {
					    				
					            		}
					            	}
								}).start();
							}
						} else {
							Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 1000).show();// toast显示1000ms	收到数据长度异常！
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
		System.out.println(sb.toString());
		//writeFile("cmd_"+sb.toString());
	}
}