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
import android.widget.LinearLayout;
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
/**
 * 整流
 * @author Administrator
 *
 */

public class RectifierDistributionFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	boolean connect_status_bit = false;
	public String sendCMD = "";//上次发送指令CID2标示
	public int countNum=0;
	private Button getSysMoni, getSysSwitch, getAlarm,setJunOrFu,setOpenOrClose,setInstallorNO,/*txtJunOrFu,txtOpenOrClose,*/txtInsOrNo,
	btn_kg,kaiguanbutton,setdischarge,setdissave,setTestBtn,getTestBtn,testflag,backupsetbtn
	,setconsisBtn,backsaveBtn,getbackStatus;
	private EditText sysOutputV, sysModeNumer, sysOutputA1, sysOutputA2, sysOutputA3,sysOutputA4,/*switchModeNumber,*/
			switchStatus, switchCurrentlimit, switchCharge, rdf_dischargeEdit,rdf_dissaveEdit,/*alarmModeNumber,*/
			alarmModeError,/*alarmFan*/acOver,dcOver,alarmModeOverTemperature,alarmCommunicationError,rdf_testtimeEdit
			,testCompleteText,testCircleText,sysMkTmp,backconsisEdit,backsaveEdit,batterystatusEdit;
	private RadioButton junButton,fuButton,openButton,closeButton,installButton,noInstallBut,cesButton,kaiEQ;
	private LinearLayout rdf_layout;
	private DataAssembleUtil dau;
	
	private int m[]={0,0,0};//获取整流模块数量
	
	private ModelBean[] arr;//记录每个模块下的回数；
	
	private Spinner sp=null;
	private TextView tv;//模块1输出电流(A):
	private TextView txt_rdswitchstatusText;//开关机状态
	private TextView txt_rdswitchcurrentlimitText;//限流状态
	private TextView txt_rdswitchchargeText;//充电状态
	private TextView txt_rdalarmmodeerrorText;//整流模块故障
	//private TextView txt_rdalarmfanerrorText;//风扇故障z
	private TextView txt_rdalarmacOverText;//ac过压故障z
	private TextView txt_rdalarmdcOverText;//dc过压故障z
	private TextView txt_rdalarmmodeovertemperatureText;//整流器模块过温
	private TextView txt_rdalarmcommunicationerrorText;//通信故障
	private TextView txt_rdMktmpText;//模块1温度
	
	byte[][] sel={{0x30,0x31},{0x30,0x31},{0x30,0x31}};
	
	/*private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==0){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						gettestParams();
					}
				}).start();
				//rdf_layout.setVisibility(View.VISIBLE);
            }
        };
        
	};*/

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
			final boolean alarm = DevicesInfo2Activity.mBluetoothLeService
					.connect(DevicesInfo2Activity.mDeviceAddress);
		}
		dau = new DataAssembleUtil();
		mBaseView = inflater.inflate(R.layout.rectifier_distribute_fragment,
				null);
		mContext = getActivity();
		getSysMoni = (Button) mBaseView.findViewById(R.id.getrdsysmoni);// 获取系统模拟量
		getSysSwitch = (Button) mBaseView.findViewById(R.id.getrdsysswitch);// 获取系统开关输入状态
		getAlarm = (Button) mBaseView.findViewById(R.id.getrdalarm);// 获取告警状态
		setJunOrFu = (Button) mBaseView.findViewById(R.id.fuorjunbutton);// 设置均充浮充
		setOpenOrClose = (Button) mBaseView.findViewById(R.id.openorclosebutton);// 设置开机关机
		setInstallorNO = (Button) mBaseView.findViewById(R.id.installornobutton);// 设置安装状态
		btn_kg=(Button)mBaseView.findViewById(R.id.btn_kg);//模块的开关选择按钮
		kaiguanbutton=(Button)mBaseView.findViewById(R.id.kaiguanbutton);//模块的开关
		setdischarge=(Button)mBaseView.findViewById(R.id.setdischarge);//设置终止容量
		setdissave=(Button)mBaseView.findViewById(R.id.setdissave);//设置终止电压
		setTestBtn=(Button)mBaseView.findViewById(R.id.setTestBtn);//设置终止时间
		getTestBtn=(Button)mBaseView.findViewById(R.id.getTestBtn);//获取校准
		testflag=(Button)mBaseView.findViewById(R.id.testflag);//获取电池标记
		backupsetbtn=(Button)mBaseView.findViewById(R.id.backupsetbtn);//设置后备运行模式
		setconsisBtn=(Button)mBaseView.findViewById(R.id.setconsisBtn);//设置后备时长
		backsaveBtn=(Button)mBaseView.findViewById(R.id.backsaveBtn);//设置安全容量
		getbackStatus=(Button)mBaseView.findViewById(R.id.getbackStatus);//获取电池状态
		// 获取系统模拟量
		sysOutputV = (EditText) mBaseView.findViewById(R.id.rdsysoutputVText);// 输出电压
		sysModeNumer = (EditText) mBaseView
				.findViewById(R.id.rdsysmodenumberText);// 整流模块数量
		sysOutputA1 = (EditText) mBaseView.findViewById(R.id.rdsysoutputA1Text);// 模块1输出电流
		sysMkTmp = (EditText) mBaseView.findViewById(R.id.rdsysMKTmp1Text);// 模块1温度
		tv=(TextView)mBaseView.findViewById(R.id.txt_rdsysoutputA1Text);//模块1输出电流(A):
		txt_rdMktmpText=(TextView)mBaseView.findViewById(R.id.txt_rdMktmpText);//模块1温度:
		txt_rdswitchstatusText=(TextView)mBaseView.findViewById(R.id.txt_rdswitchstatusText);//开关机状态
		txt_rdswitchcurrentlimitText=(TextView)mBaseView.findViewById(R.id.txt_rdswitchcurrentlimitText);//限流状态
		txt_rdswitchchargeText=(TextView)mBaseView.findViewById(R.id.txt_rdswitchchargeText);//充电状态
		txt_rdalarmmodeerrorText=(TextView)mBaseView.findViewById(R.id.txt_rdalarmmodeerrorText);//整流模块故障
		//txt_rdalarmfanerrorText=(TextView)mBaseView.findViewById(R.id.txt_rdalarmfanerrorText);//风扇故障
		txt_rdalarmacOverText=(TextView)mBaseView.findViewById(R.id.txt_rdalarmacOverText);//ac过压故障
		txt_rdalarmdcOverText=(TextView)mBaseView.findViewById(R.id.txt_rdalarmdcOverText);//dc过压故障
		txt_rdalarmmodeovertemperatureText=(TextView)mBaseView.findViewById(R.id.txt_rdalarmmodeovertemperatureText);//整流器模块过温
		txt_rdalarmcommunicationerrorText=(TextView)mBaseView.findViewById(R.id.txt_rdalarmcommunicationerrorText);//通信故障
		testCompleteText=(EditText)mBaseView.findViewById(R.id.testCompleteText);//测试放电完成标记位
		testCircleText=(EditText)mBaseView.findViewById(R.id.testCircleText);//测试周期完成标记位
		backconsisEdit=(EditText)mBaseView.findViewById(R.id.backconsisEdit);//后备时长
		backsaveEdit=(EditText)mBaseView.findViewById(R.id.backsaveEdit);//安全容量
		batterystatusEdit=(EditText)mBaseView.findViewById(R.id.batterystatusEdit);//电池状态
		
		kaiEQ=(RadioButton)mBaseView.findViewById(R.id.kaiEQ);
		rdf_testtimeEdit=(EditText)mBaseView.findViewById(R.id.rdf_testtimeEdit);
		rdf_dissaveEdit=(EditText)mBaseView.findViewById(R.id.rdf_dissaveEdit);
		rdf_dischargeEdit=(EditText)mBaseView.findViewById(R.id.rdf_dischargeEdit);
		rdf_layout=(LinearLayout)mBaseView.findViewById(R.id.rdf_layout);
		//txtJunOrFu=(Button)mBaseView.findViewById(R.id.btn_jfc);//模块均浮充状态
		/*txtJunOrFu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openSelectDialog(4);
			}
		});
		txtOpenOrClose=(Button)mBaseView.findViewById(R.id.btn_kgj);//模块开关机状态
		txtOpenOrClose.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openSelectDialog(5);
			}
		});*/
		txtInsOrNo=(Button)mBaseView.findViewById(R.id.btn_az);//模块安装状态
		txtInsOrNo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openSelectDialog(6);
			}
		});
		sysOutputA1.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if(m[0]!=0){
					openSelectDialog(1);
				}
				return false;
			}
		});
		// 获取系统开关输入状态
		/*switchModeNumber = (EditText) mBaseView
				.findViewById(R.id.rdswitchmodeText);*/// 整流模块数量
		switchStatus = (EditText) mBaseView
				.findViewById(R.id.rdswitchstatusText);// 开关机状态
		switchStatus.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				if(m[1]!=0){
					openSelectDialog(2);
				}
				return false;
			}
		});
		switchCurrentlimit = (EditText) mBaseView
				.findViewById(R.id.rdswitchcurrentlimitText);// 限流状态
		switchCharge = (EditText) mBaseView
				.findViewById(R.id.rdswitchchargeText);// 充电状态

		// 获取告警状态
		/*alarmModeNumber = (EditText) mBaseView
				.findViewById(R.id.rdalarmmodenumberText);*/// 整流模块数量
		alarmModeError = (EditText) mBaseView
				.findViewById(R.id.rdalarmmodeerrorText);// 整流模块故障
		alarmModeError.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if(m[2]!=0){
					openSelectDialog(3);
				}
				return false;
			}
		});
		/*alarmFan = (EditText) mBaseView
				.findViewById(R.id.rdalarmfanerrorText);*/// 风扇故障
		acOver = (EditText) mBaseView
				.findViewById(R.id.rdalarmacOverText);// ac过压故障
		dcOver = (EditText) mBaseView
				.findViewById(R.id.rdalarmdcOverText);// dc过压故障
		alarmModeOverTemperature = (EditText) mBaseView
				.findViewById(R.id.rdalarmmodeovertemperatureText);// 整流模块过温
		alarmCommunicationError = (EditText) mBaseView
				.findViewById(R.id.rdalarmcommunicationerrorText);// 通信故障
		//DO设置部分
		junButton=(RadioButton) mBaseView.findViewById(R.id.junEQ);
		fuButton=(RadioButton) mBaseView.findViewById(R.id.fuEQ);
		openButton=(RadioButton) mBaseView.findViewById(R.id.openEQ);
		closeButton=(RadioButton) mBaseView.findViewById(R.id.closeEQ);
		installButton=(RadioButton) mBaseView.findViewById(R.id.anzhuangEQ);
		noInstallBut=(RadioButton) mBaseView.findViewById(R.id.noEQ);
		cesButton=(RadioButton)mBaseView.findViewById(R.id.cesEQ);
		rdf_layout=(LinearLayout)mBaseView.findViewById(R.id.rdf_layout);
		/*if(DataAssembleUtil.setPriviledge){
			rdf.setVisibility(View.VISIBLE);
		}else{
			rdf.setVisibility(View.GONE);
		}*/
		
		getSysMoni.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				tv.setText(getActivity().getResources().getString(R.string.rdf_mk1));//模块1输出电流(A):
				if (sendCMD.length() > 0 && sendCMD.compareTo("42") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };//41 42
					byte[] b = dau.AssembleReadData(recByte, "42");
					sendCMD="42";
					countNum=0;
					sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		getSysSwitch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				txt_rdswitchstatusText.setText(getActivity().getResources().getString(R.string.rdf_mk1kgj));//模块1开关机状态:
        		txt_rdswitchcurrentlimitText.setText(getActivity().getResources().getString(R.string.rdf_mk1xl));//模块1限流状态:
        		txt_rdswitchchargeText.setText(getActivity().getResources().getString(R.string.rdf_mk1cd));//模块1充电状态:

				if (sendCMD.length() > 0 && sendCMD.compareTo("43") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {					
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };//41 43
					byte[] b = dau.AssembleReadData(recByte, "43");
					sendCMD="43";
					countNum=0;
					sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
				
			}
		});
		getAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				txt_rdalarmmodeerrorText.setText(getActivity().getResources().getString(R.string.rdf_mk1mkgz));//模块1整流模块故障:
        		//txt_rdalarmfanerrorText.setText(getActivity().getResources().getString(R.string.rdf_mk1fsgz));//模块1风扇故障:
				txt_rdalarmacOverText.setText(getActivity().getResources().getString(R.string.rdf_mk1acgygz));//模块1ac过压故障:
				txt_rdalarmdcOverText.setText(getActivity().getResources().getString(R.string.rdf_mk1dcgygz));//模块1dc过压故障:
        		txt_rdalarmmodeovertemperatureText.setText(getActivity().getResources().getString(R.string.rdf_mk1zlq));//模块1整流器模块过温:
        		txt_rdalarmcommunicationerrorText.setText(getActivity().getResources().getString(R.string.rdf_mk1tx));//模块1通信故障:
				if (sendCMD.length() > 0 && sendCMD.compareTo("44") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };//41 44
					byte[] b = dau.AssembleReadData(recByte, "44");
					sendCMD="44";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}

			}
		});
		//设置均充或浮充状态
		setJunOrFu.setOnClickListener(new OnClickListener() {
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

					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };//41 45
					int commandType=0;
					if(junButton.isChecked()){
						commandType=16;
					}else if(fuButton.isChecked()){
						commandType=31;
					}else if(cesButton.isChecked()){
						commandType=17;
					}
					else{
						commandType=17;
					}

					byte []b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
//					byte [] bb={b1[0],b1[1],sel[0][0],sel[0][1]};
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
						//sendCommand(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
					}
					sendCMD="";
					/*if(cesButton.isChecked()){
						Message message = handler.obtainMessage();
						message.what = 0;
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage();
						message.what = 1;
						handler.sendMessage(message);						
					}*/
				
				}

			}
		});
		//设置模块开关机
		setOpenOrClose.setOnClickListener(new OnClickListener() {
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

					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };
					int commandType=0;
					if(openButton.isChecked()){
						commandType=48;
					}else if(closeButton.isChecked()){
						commandType=63;
					}else{
						commandType=48;
					}

					byte []b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x31};
//					byte [] bb={b1[0],b1[1],sel[1][0],sel[1][1]};
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
						//sendCommand(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
					}
					sendCMD="";
				}
			}
		});
		//设置安装或未安装
		setInstallorNO.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };
					int commandType=0;
					if(installButton.isChecked()){
						commandType=16;
					}else if(noInstallBut.isChecked()){
						commandType=31;
					}else{
						commandType=16;
					}

					byte []b1=dau.intToAscByte2(commandType);
//					byte [] bb={b1[0],b1[1],0x30,0x30};
					byte [] bb={b1[0],b1[1],sel[2][0],sel[2][1]};
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
						//sendCommand(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
					}
					sendCMD="";
				}
			}
		});
		kaiguanbutton.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };
					int commandType=0;
					if(kaiEQ.isChecked()){
						commandType=32;
					}else if(noInstallBut.isChecked()){
						commandType=47;
					}else{
						commandType=32;
					}

					byte []b1=dau.intToAscByte2(commandType);
//					byte [] bb={b1[0],b1[1],0x30,0x30};
					byte [] bb={b1[0],b1[1],sel[2][0],sel[2][1]};
					byte[] b = dau.sendSetCmd(recByte, "80",bb);
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
						//sendCommand(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
					}
					sendCMD="";
				}
			}
		});
		btn_kg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openSelectDialog(7);
			}
		});
		setdischarge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String a8V=rdf_dischargeEdit.getText().toString();//终止容量A8
					if((a8V.length()==0)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x32 };//42 49
						a8V=rdf_dischargeEdit.getText().toString();//终止容量A8
						int v1=Integer.parseInt(a8V);
								
						byte[] b1=dau.intToAscByte4(v1);
								
						byte [] bb={0x41,0x38,b1[0],b1[1],b1[2],b1[3]};//a8
						byte[] b = dau.sendSetCmd(recByte, "49",bb);
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
							//sendCommand(b10,1);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						}
					}
				sendCMD="";
				}
			}
		});
		setdissave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String V98=rdf_dissaveEdit.getText().toString();//终止电压98
					if((V98.length()==0)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						//发第一条
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x32 };//42 49
						V98=rdf_dissaveEdit.getText().toString();//终止电压98
								
						String s =String.valueOf(Float.valueOf(V98)*100);
						int v2=Integer.parseInt(s.substring(0,s.indexOf(".")));
						
						byte[] b2=dau.intToAscByte4(v2);
						
								
						//发第二条
						byte[] bb=new byte[]{0x39,0x38,b2[0],b2[1],b2[2],b2[3]};//98
						byte[] b = dau.sendSetCmd(recByte, "49",bb);
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
							//sendCommand(b10,2);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						}
								
						sendCMD="";
					}
				}
			}
		});
		setTestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String V99=rdf_dischargeEdit.getText().toString();//终止时间99
					if(V99.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						//发第一条
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x32 };//42 49
						V99=rdf_testtimeEdit.getText().toString();//终止时间99
						int v3=Integer.parseInt(V99);
						byte[] b3=dau.intToAscByte4(v3);
						//发第三条
						byte[] bb=new byte[]{0x39,0x39,b3[0],b3[1],b3[2],b3[3]};//99
						byte[] b = dau.sendSetCmd(recByte, "49",bb);
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
							//sendCommand(b10,1);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						}
						sendCMD="";
					}
				}
			}
		});
		getTestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("47") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x32 };//42 47
					byte[] b = dau.AssembleReadData(recByte, "47");
					sendCMD="47";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		
		});
		testflag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("81") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x45, 0x30 };//E0 81
					byte[] b = dau.AssembleReadData(recByte, "81");
					sendCMD="81";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		
		});
		//设置后备运行模式
		backupsetbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x31 };//41 45
				int commandType=18;
				

				byte []b1=dau.intToAscByte2(commandType);
				byte [] bb={b1[0],b1[1],0x30,0x30};
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
					//sendCommand(b10);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
				}
				sendCMD="";
			}
		
		});
		//设置后备时长
		setconsisBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String V99=backconsisEdit.getText().toString();//终止时间99
					if(V99.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						//发第一条
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x32 };//42 49
						V99=backconsisEdit.getText().toString();//终止时间99
						int v3=Integer.parseInt(V99);
						byte[] b3=dau.intToAscByte2(v3);
						//发第三条
						byte[] bb=new byte[]{0x41,0x32,b3[0],b3[1]};//A2
						byte[] b = dau.sendSetCmd(recByte, "49",bb);
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
							//sendCommand(b10,1);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						}
						sendCMD="";
					}
				}
			}
		});
		//设置安全容量
		backsaveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String V99=backsaveEdit.getText().toString();//终止时间99
					if(V99.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						//发第一条
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x32 };//42 49
						V99=backsaveEdit.getText().toString();//终止时间99
						int v3=Integer.parseInt(V99);
						byte[] b3=dau.intToAscByte2(v3);
						//发第三条
						byte[] bb=new byte[]{0x41,0x33,b3[0],b3[1]};//A3
						byte[] b = dau.sendSetCmd(recByte, "49",bb);
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
							//sendCommand(b10,1);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
						}
						sendCMD="";
					}
				}
			}
		});
		//获取电池状态
		getbackStatus.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("D043") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x30 };//D0 43
					byte[] b = dau.AssembleReadData(recByte, "43");
					sendCMD="D043";
					countNum=0;
					sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		return mBaseView;
	}
	/*private void gettestParams(){
		if (sendCMD.length() > 0 && sendCMD.compareTo("47") != 0) {// 判断当前是否有命令正在发送
			Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
					1000).show();// toast显示1000ms
			countNum++;
			if(countNum>2){
				sendCMD="";
			}
		} else {
			byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x34, 0x32 };//42 47
			byte[] b = dau.AssembleReadData(recByte, "47");
			sendCMD="47";
			countNum=0;
			//sendCommand(b);
			DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
			//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
		}
	}*/
	private void openSelectDialog(final int w){
		//打开一个dialog在里面选择模块几，然后更新sysOutputA1的值和txt_rdsysoutputA1Text中
		//arr=new String[m];///////////2018-08-17
		if(w<4){
			if(arr.length<=0){
				Toast.makeText(mContext,getResources().getString(R.string.rdf_Toast),1).show();//请先获取整流模块数量
				return;
			}
		}
		sp=new Spinner(mContext);
		String mk=getResources().getString(R.string.rdf_mk);//模块
		String mkend=getResources().getString(R.string.rdf_scdl);//输出电流(A):
		switch(w){
		case 2:
			mkend="";
			break;
		case 3:
			//mkend="整流模块故障:";
			mkend="";
			break;
		case 4:
			mkend=getResources().getString(R.string.rdf_jfc);//均浮充:
			arr=null;
			arr=new ModelBean[]{new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
								,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
								,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
								,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
								,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
								,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())};
			break;
		case 5:
			mkend=getResources().getString(R.string.rdf_kgj);//开关机:
			arr=null;
			arr=new ModelBean[]{new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())};
			break;
		case 6:
			mkend=getResources().getString(R.string.rdf_az);//安装;
			arr=null;
			arr=new ModelBean[]{new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())};
			break;
		case 7:
			mkend=getResources().getString(R.string.rdf_kg);//开关:
			arr=null;
			arr=new ModelBean[]{new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())
			,new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity()),new ModelBean(getActivity())};
			break;
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
                    	int position=0;
                    	switch(w){
                    	case 1:
                    		position=sp.getSelectedItemPosition();
                    		tv.setText(sp.getSelectedItem().toString());
                    		txt_rdMktmpText.setText(getResources().getString(R.string.rdf_mk)+(position+1)+getResources().getString(R.string.enviroTemp));
                    		//Toast.makeText(MainActivity.this, sp.getSelectedItem().toString(), 1).show();
                    		sysOutputA1.setText(arr[position].getTmp());
                    		if(arr[position].getMkTmp().contains("-0.10")){
								sysMkTmp.setText("--");
							}else{
								sysMkTmp.setText(arr[position].getMkTmp());
							}
                    		sp=null;
                    		dialog.dismiss();
                    	break;
                    	case 2:
                    		position=sp.getSelectedItemPosition();
                    		txt_rdswitchstatusText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_kgjzt));//开关机状态:
                    		txt_rdswitchcurrentlimitText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_xlzt));//限流状态:
                    		txt_rdswitchchargeText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_cdzt));//充电状态:
                    		//Toast.makeText(MainActivity.this, sp.getSelectedItem().toString(), 1).show();
                    		switchStatus.setText(arr[position].getOpenOrClose());
                    		switchCurrentlimit.setText(arr[position].getCurrentlimit());
                    		switchCharge.setText(arr[position].getCharge());
                    		sp=null;
                    		dialog.dismiss();
                    		break;
                    	case 3:
                    		position=sp.getSelectedItemPosition();
                    		txt_rdalarmmodeerrorText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_zlmkgz));//整流模块故障:
                    		//txt_rdalarmfanerrorText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_fsgz));//风扇故障:
                    		txt_rdalarmacOverText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_acgygz));//ac过压故障:
                    		txt_rdalarmdcOverText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_dcgygz));//dc过压故障:
                    		txt_rdalarmmodeovertemperatureText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_zlqmkgw));//整流器模块过温:
                    		txt_rdalarmcommunicationerrorText.setText(sp.getSelectedItem().toString()+getResources().getString(R.string.rdf_txgz));//通信故障:
                    		//Toast.makeText(MainActivity.this, sp.getSelectedItem().toString(), 1).show();
                    		alarmModeError.setText(arr[position].getModeError());
                    		//alarmFan.setText(arr[position].getFengshan());
                    		acOver.setText(arr[position].getAcOver());
                    		dcOver.setText(arr[position].getDcOver());
                    		alarmModeOverTemperature.setText(arr[position].getAlarmModeOver());
                    		alarmCommunicationError.setText(arr[position].getAlarmCommunication());
                    		sp=null;
                    		dialog.dismiss();
                    		break;
                    	case 4:
                    		position=sp.getSelectedItemPosition();
                    		//txtJunOrFu.setText(sp.getSelectedItem().toString());
                    		sel[0]=dau.intToAscByte2(position+1);
                    		sp=null;
                    		arr=null;
                    		dialog.dismiss();
                    		break;
                    	case 5:
                    		position=sp.getSelectedItemPosition();
                    		//txtOpenOrClose.setText(sp.getSelectedItem().toString());
                    		sel[1]=dau.intToAscByte2(position+1);
                    		sp=null;
                    		arr=null;
                    		dialog.dismiss();
                    		break;
                    	case 6:
                    		position=sp.getSelectedItemPosition();
                    		txtInsOrNo.setText(sp.getSelectedItem().toString());
                    		sel[2]=dau.intToAscByte2(position+1);
                    		sp=null;
                    		arr=null;
                    		dialog.dismiss();
                    		break;
                    	case 7:
                    		position=sp.getSelectedItemPosition();
                    		txtInsOrNo.setText(sp.getSelectedItem().toString());
                    		sel[1]=dau.intToAscByte2(position+1);
                    		sp=null;
                    		arr=null;
                    		dialog.dismiss();
                    		break;
                    	}
                    }
                })
		.create();
		dialog.show();
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

	/*private void writeFile(String data){
		File picDir=this.getView().getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		Log.e("TAG", picDir.getPath());
		File file=new File(picDir,"file01.txt");
		OutputStream out=null;
		try {
			out=new FileOutputStream(file,true);
			out.write((this.getClass().toString()+":"+data+"\r\n").getBytes());
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
	}
	*/
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
					//writeFile(recString);
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
							if (sendCMD.compareTo("42") == 0) {
								arr=null;
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								DecimalFormat df=new DecimalFormat("0.00");
								int V = DataAssembleUtil.parseInt(bb[2], bb[1]);// 整流器输出电压
								String aVf=df.format((float)V/100);
								m[0] = bb[3];//整流模块数量
								int m1A=DataAssembleUtil.parseInt(bb[5], bb[4]);
								String m1Af=df.format((float)m1A/100);
								//隔一个p
								int m1B=DataAssembleUtil.parseInt(bb[8], bb[7]);
								String m1Bf=df.format((float)m1B/10);
								sysOutputV.setText(aVf);// 输出电压
								sysModeNumer.setText(m[0]+"");// 整流模块数量
								sysOutputA1.setText(m1Af);// 模块1输出电流
								if(m1Bf.contains("-0.10")){
									sysMkTmp.setText("--");
								}else{
									sysMkTmp.setText(m1Bf);//模块1温度
								}
								//循环回数，放入arr里
								arr=new ModelBean[m[0]];  ///////2018-08-17
								for(int i=0;i<m[0];i++){
									Log.e("TAG", "bb[5+i]:"+bb[5+i]+";bb[4+i]:"+bb[4+i]);
									int mA=DataAssembleUtil.parseInt(bb[5+i], bb[4+i]);
									arr[i]=new ModelBean(getActivity());
									//隔一个p
									int mB=DataAssembleUtil.parseInt(bb[8+i], bb[7+i]);
									arr[i].setTmp(df.format((float)mA/100));//模块n输出电流
									arr[i].setMkTmp(df.format((float)mB/10));//模块n温度
									//结束
								}
							}else if(sendCMD.compareTo("43") == 0){
								arr=null;///////2018-08-17
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								m[1]=bb[1];//整流模块数量
								int openOrClose=bb[2];//开关机状态
								int currentlimit=bb[3];//限流状态
								int charge=bb[4];//充电状态
								//switchModeNumber.setText(""+m);
								if (openOrClose == 00) {
									switchStatus.setText(getResources().getString(R.string.powerOn));//开机
								} else if (openOrClose == 01) {
									switchStatus.setText(getResources().getString(R.string.powerOff));//关机
								}
								if (currentlimit == 00) {
									switchCurrentlimit.setText(getResources().getString(R.string.limited));//限流
								} else if (currentlimit == 01) {
									switchCurrentlimit.setText(getResources().getString(R.string.unlimited));//不限流
								}
								if (charge == 00) {
									switchCharge.setText(getResources().getString(R.string.floating));//浮充
								} else if (charge == 01) {
									switchCharge.setText(getResources().getString(R.string.equalizing));//均充
								} else if (charge == 02) {
									switchCharge.setText(getResources().getString(R.string.test));//测试
								}
								
								//循环回数，放入arr里
								arr=new ModelBean[m[1]];  ///////2018-08-17
								for(int i=0;i<m[1];i++){
									int openOrClose1=bb[2+i*5];//开关机状态
									int currentlimit1=bb[3+i*5];//限流状态
									int charge1=bb[4+i*5];//充电状态
									arr[i]=new ModelBean(getActivity());
									arr[i].setCharge(charge1);
									arr[i].setCurrentlimit(currentlimit1);
									arr[i].setOpenOrClose(openOrClose1);
								}
								
							}else if(sendCMD.compareTo("44") == 0){
								arr=null;///////2018-08-17
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length>=10){
									m[2]=bb[1];//整流模块数量
									int modeError=bb[2];//模块状态
									int p=bb[3];
									//int fengShan=bb[6];//风扇状态
									int acOverInt=bb[5];//ac过压状态
									int dcOverInt=bb[4];//dc过压状态
									int alarmModeOver=bb[7];//模块过温状态
									int alarmCommunication=bb[9];//通信状态
									//alarmModeNumber.setText(""+m);
									
									if (modeError == 00) {
										alarmModeError.setText(getResources().getString(R.string.normal));//正常
									} else{
										alarmModeError.setText(getResources().getString(R.string.fault));//故障
									}
									
									/*if (fengShan == 00) {
										alarmFan.setText(getResources().getString(R.string.normal));//正常
									} else{
										alarmFan.setText(getResources().getString(R.string.fault));//故障
									}*/
									
									if (acOverInt == 00) {
										acOver.setText(getResources().getString(R.string.normal));//正常
									} else{
										acOver.setText(getResources().getString(R.string.fault));//故障
									}
									if (dcOverInt == 00) {
										dcOver.setText(getResources().getString(R.string.normal));//正常
									} else{
										dcOver.setText(getResources().getString(R.string.fault));//故障
									}
									
									if (alarmModeOver == 00) {
										alarmModeOverTemperature.setText(getResources().getString(R.string.normal));//正常
									} else{
										alarmModeOverTemperature.setText(getResources().getString(R.string.fault));//故障
									}
									
									if (alarmCommunication == 00) {
										alarmCommunicationError.setText(getResources().getString(R.string.normal));//正常
									} else{
										alarmCommunicationError.setText(getResources().getString(R.string.fault));//故障
									}
									
									//循环回数，放入arr里
									arr=new ModelBean[m[2]];  ///////2018-08-17
									for(int i=0;i<m[2];i++){
										int modeError1=bb[2+i*p];//模块状态
										//int fengShan1=bb[6+i*p];//风扇状态
										int ac1=bb[5+i*p];//ac过压
										int dc1=bb[4+i*p];//dc过压
										int alarmModeOver1=bb[7+i*p];//模块过温状态
										int alarmCommunication1=bb[9+i*p];//通信状态
										arr[i]=new ModelBean(getActivity());
										arr[i].setModeError(modeError1);
										//arr[i].setFengshan(fengShan1);
										arr[i].setAcOver(ac1);
										arr[i].setDcOver(dc1);
										arr[i].setAlarmModeOver(alarmModeOver1);
										arr[i].setAlarmCommnunication(alarmCommunication1);
									}
								}else{
									Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 500).show();// toast显示1000ms		收到数据长度异常！
								}
							}else if(sendCMD.compareTo("47") == 0){
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length<27){
									Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 500).show();// toast显示1000ms	收到数据长度异常！
								}else{
									int testV = DataAssembleUtil.parseInt(bb[48], bb[47]);//((bb[1]) & 0xFF | (bb[0] & 0xFF) << 8);测试终止电压
									int testT = DataAssembleUtil.parseInt(bb[50], bb[49]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);测试终止时间
									int bandNum=DataAssembleUtil.parseInt(bb[72], bb[71]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);分路传感器数目
									int dbdypos=73+2*bandNum;//电保电压位置
									//int dbdy=DataAssembleUtil.parseInt(bb[dbdypos+1], bb[dbdypos]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);电保电压
									/*int xdcspos=dbdypos+2;//下电次数位置
									int xdcs=DataAssembleUtil.parseInt(bb[xdcspos+1], bb[xdcspos]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);下电次数
									int dcszpos=(xdcspos+2)+2*xdcs;//电池组数位置		
									int dcsz=DataAssembleUtil.parseInt(bb[dcszpos+1], bb[dcszpos]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);电池组数容量*/
									
									int cszzpos=dbdypos+2;//测试终止容量位置
									int cszz=DataAssembleUtil.parseInt(bb[cszzpos+1], bb[cszzpos]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);测试终止容量
									DecimalFormat df=new DecimalFormat("0.00");
									rdf_dischargeEdit.setText(String.valueOf(cszz));
									rdf_dissaveEdit.setText(String.valueOf(df.format((float)testV/100)));
									rdf_testtimeEdit.setText(String.valueOf(testT));
									int hbscpos=cszzpos+2+2+1;//后备时长位置
									int hbsc=DataAssembleUtil.parseInt(bb[hbscpos+1], bb[hbscpos]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);后备时长
									backconsisEdit.setText(String.valueOf(hbsc));
									
									int aqrl=DataAssembleUtil.parseInt(bb[hbscpos+3], bb[hbscpos+2]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);安全容量
									backsaveEdit.setText(String.valueOf(aqrl));
								}
								
							}else if(sendCMD.compareTo("81") == 0){
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int testwc=bb[8];//电池测试放电完成标志位
								int testzq=bb[9];//电池测试周期完成标志位
								if (Math.abs(testwc) == 128) {//80H
									testCompleteText.setText(getResources().getString(R.string.complete));//完成
								} else{
									testCompleteText.setText(getResources().getString(R.string.operating));//未完成
								}
								if (Math.abs(testzq) == 128) {//80H
									testCircleText.setText(getResources().getString(R.string.complete));//完成
								} else {
									testCircleText.setText(getResources().getString(R.string.operating));//未完成
								}
								
							}else if(sendCMD.compareTo("D043") == 0){
								byte[] bb = dau.Data_Close(b2, b2.length);
								int status=bb[4];//电池状态
								if (status == 00) {
									batterystatusEdit.setText(getResources().getString(R.string.equalizing));//均充
								} else if (status == 1) {
									batterystatusEdit.setText(getResources().getString(R.string.floating));//浮充
								} else if (status == 2) {
									batterystatusEdit.setText(getResources().getString(R.string.discharge));//放电
								} else if (status == 3) {
									batterystatusEdit.setText(getResources().getString(R.string.test));//测试
								} else if (status == 4) {
									batterystatusEdit.setText(getResources().getString(R.string.noexist));//不存在
								} else if (status == 5) {
									batterystatusEdit.setText(getResources().getString(R.string.standby));//待机
								} else if (status == 6) {
									batterystatusEdit.setText(getResources().getString(R.string.protect));//保护
								} else if (status == 7) {
									batterystatusEdit.setText(getResources().getString(R.string.charging));//充电
								} else if (status == 8) {
									batterystatusEdit.setText(getResources().getString(R.string.backup));//后备								}
								}
								
							}
							
						} else {
							Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 1000).show();// toast显示1000ms		收到数据长度异常！
						}
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.acToast5), 1000).show();// toast显示1000ms		收到数据校验失败！
					}
					recString = "";
					sendCMD = "";
				}
			}
		}catch(Exception e){
			Toast.makeText(getActivity(), "回数有问题", 1000).show();// toast显示1000ms		收到数据校验失败！
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
		//writeFile("cmd_"+j+":"+sb.toString());
	}
	
}