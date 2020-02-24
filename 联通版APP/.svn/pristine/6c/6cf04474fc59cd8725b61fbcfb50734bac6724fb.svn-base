package com.dgm.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.aes.AES;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

/**
 * 蓝牙直流界面
 * @author Administrator
 *
 */
public class DcDistributionFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private Button getSysMoni, getAlarm, getConfig, setConfig,setVdown,setTime,setV_jun,setV_fu,junpesistTimeBtn;
	boolean connect_status_bit = false;
	private DataAssembleUtil dau;
	public String sendCMD = "";//上次发送指令CID2标示
	public int countNum=0;
	private Spinner sp=null;
	private String[] stateArr;//记录每个模块下的回数；
	private EditText /*sysScreenNumber,*/ sysOutputV, sysCurrent,totalCurrent,
			/*sysShuntA1,sysShuntA2,*//*sysBatteryTemperature01,sysBatteryTemperature02, alarmScreenNumber,*/ alarmDCV,
			alarmFuse1, alarmFuse2, alarmElectric1, alarmElectric2,
			conVupperlimit, conVlowerlimit, conEqualizingCycle, conEqualizingV,
			conFloatingV,junpesistTimeEdit,dctotpowerText,KetiText,KeTiState,KetiTextupperlimit,KetiTextlowerlimit;
	//2018-12-06 追加按钮获取熔丝状态 getFuseStateText
	private Button setChongdianA,setZhuanhuanA,setJunTime,setCankaoTem,setBattery1,setBattery2,KetiTextupperlimitBtn,KetiTextlowerlimitBtn,getSurfaceTempState,getPowerInfo,getSurfaceTemp,getSurfaceTemplimit;
	private EditText editChongdianA,editZhuanhuanA,editJunTime,editCankaoTem,editBattery1,editBattery2,editFuse;
	
	private TextView txtFuseStateText;//开关机状态
	
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
		mBaseView = inflater.inflate(R.layout.dc_distribute_fragment, null);
		dau = new DataAssembleUtil();
		mContext = getActivity();
		txtFuseStateText=(TextView)mBaseView.findViewById(R.id.txtFuseStateText);//直流熔丝/开关
		getSysMoni = (Button) mBaseView.findViewById(R.id.getdcsysmoni);// 获取系统模拟量
		getAlarm = (Button) mBaseView.findViewById(R.id.getdcalarm);// 获取告警状态
		getConfig = (Button) mBaseView.findViewById(R.id.getDcConfig);// 获取参数
		setConfig = (Button) mBaseView.findViewById(R.id.setdcconfig);// 设置参数
		setVdown = (Button) mBaseView.findViewById(R.id.setdcV_down);// 设置参数
		setTime = (Button) mBaseView.findViewById(R.id.setdc_Time);// 设置参数
		setV_jun = (Button) mBaseView.findViewById(R.id.setdcV_jun);// 设置参数
		setV_fu = (Button) mBaseView.findViewById(R.id.setdcV_fu);// 设置参数
		setChongdianA = (Button) mBaseView.findViewById(R.id.setchongdianA);
		setZhuanhuanA = (Button) mBaseView.findViewById(R.id.setzhuanhuanA);
		setJunTime = (Button) mBaseView.findViewById(R.id.setjunTime);
		setCankaoTem = (Button) mBaseView.findViewById(R.id.setcankaoTem);
		setBattery1 = (Button) mBaseView.findViewById(R.id.setbattery1C);
		setBattery2 = (Button) mBaseView.findViewById(R.id.setbattery2C);
		junpesistTimeBtn=(Button) mBaseView.findViewById(R.id.junpesistTimeBtn);//均充保持时间
		KetiTextupperlimitBtn=(Button) mBaseView.findViewById(R.id.KetiTextupperlimitBtn);//设置机壳温度上限
		KetiTextlowerlimitBtn=(Button) mBaseView.findViewById(R.id.KetiTextlowerlimitBtn);//设置机壳温度下限
		getPowerInfo=(Button) mBaseView.findViewById(R.id.getPowerInfo);//获取电能板信息
		getSurfaceTemp=(Button) mBaseView.findViewById(R.id.getSurfaceTemp);//获取壳体温度
		getSurfaceTempState=(Button) mBaseView.findViewById(R.id.getSurfaceTempState);//获取壳体温度状态
		getSurfaceTemplimit=(Button) mBaseView.findViewById(R.id.getSurfaceTemplimit);//获取壳体温度上下限
		//获取系统模拟量
		//sysScreenNumber =  (EditText) mBaseView.findViewById(R.id.dcsysscreenText);//交流屏数量
		sysOutputV =  (EditText) mBaseView.findViewById(R.id.dcsysoutputVText);//直流输出电压
		sysCurrent =  (EditText) mBaseView.findViewById(R.id.dcsyscurrentText);//总负载电流
		totalCurrent =  (EditText) mBaseView.findViewById(R.id.totalcurrentText);//电池总电流
		//sysShuntA1 =  (EditText) mBaseView.findViewById(R.id.dcsysshuntA1Text);//分路1电流
		//sysShuntA2 =  (EditText) mBaseView.findViewById(R.id.dcsysshuntA2Text);//分路2电流
		//sysBatteryTemperature01 =  (EditText) mBaseView.findViewById(R.id.dcsysbatterytemText01);//蓄电池组温度
		//sysBatteryTemperature02 =  (EditText) mBaseView.findViewById(R.id.dcsysbatterytemText02);//蓄电池组温度
		
		//获取告警状态
		//alarmScreenNumber = (EditText) mBaseView.findViewById(R.id.dcalarmscreenText);//交流屏数量
		alarmDCV = (EditText) mBaseView.findViewById(R.id.dcalarmDCVText);//直流电压
		alarmFuse1 = (EditText) mBaseView.findViewById(R.id.dcalarmfuse1Text);//电池1熔丝
		alarmFuse2 = (EditText) mBaseView.findViewById(R.id.dcalarmfuse2Text);//电池1熔丝
		alarmElectric1 = (EditText) mBaseView.findViewById(R.id.dcalarmelectric1Text);//一次下电
		alarmElectric2 = (EditText) mBaseView.findViewById(R.id.dcalarmelectric2Text);//二次下电
		
		//获取和设置参数
		conVupperlimit = (EditText) mBaseView.findViewById(R.id.dcconfigVupperlimitText);//直流电压上限
		conVlowerlimit = (EditText) mBaseView.findViewById(R.id.dcconfigVlowerlimitText);//直流电压下限
		conEqualizingCycle = (EditText) mBaseView.findViewById(R.id.dcconfigequalizingcycleText);//均充周期
		conEqualizingV = (EditText) mBaseView.findViewById(R.id.dcconfigequalizingVText);//均充电压
		conFloatingV = (EditText) mBaseView.findViewById(R.id.dcconfigfloatingVText);//浮充电压
		
		editChongdianA = (EditText) mBaseView.findViewById(R.id.chongdianA);
		editZhuanhuanA = (EditText) mBaseView.findViewById(R.id.zhuanhuanA);
		editJunTime = (EditText) mBaseView.findViewById(R.id.junTime);
		editCankaoTem = (EditText) mBaseView.findViewById(R.id.cankaoTem);
		editBattery1 = (EditText) mBaseView.findViewById(R.id.battery1C);
		editBattery2 = (EditText) mBaseView.findViewById(R.id.battery2C);
		editFuse=(EditText)mBaseView.findViewById(R.id.getFuseStateText);
		dctotpowerText=(EditText)mBaseView.findViewById(R.id.dctotpowerText);
		junpesistTimeEdit=(EditText)mBaseView.findViewById(R.id.junpesistTimeEdit);
		KetiText=(EditText)(EditText)mBaseView.findViewById(R.id.KetiText);//壳体温度
		KeTiState=(EditText)(EditText)mBaseView.findViewById(R.id.KeTiState);//壳体温度状态
		KetiTextupperlimit=(EditText)mBaseView.findViewById(R.id.KetiTextupperlimit);//壳体温度上限
		KetiTextlowerlimit=(EditText)mBaseView.findViewById(R.id.KetiTextlowerlimit);//壳体温度下限
		if(DataAssembleUtil.setPriviledge){
			setBattery1.setVisibility(View.VISIBLE);
			setBattery2.setVisibility(View.VISIBLE);
			setCankaoTem.setVisibility(View.VISIBLE);
			setChongdianA.setVisibility(View.VISIBLE);
			setConfig.setVisibility(View.VISIBLE);
			setJunTime.setVisibility(View.VISIBLE);
			setTime.setVisibility(View.VISIBLE);
			setV_fu.setVisibility(View.VISIBLE);
			setV_jun.setVisibility(View.VISIBLE);
			setVdown.setVisibility(View.VISIBLE);
			setZhuanhuanA.setVisibility(View.VISIBLE);
		}else{
			setBattery1.setVisibility(View.INVISIBLE);
			setBattery2.setVisibility(View.INVISIBLE);
			setCankaoTem.setVisibility(View.INVISIBLE);
			setChongdianA.setVisibility(View.INVISIBLE);
			setConfig.setVisibility(View.INVISIBLE);
			setJunTime.setVisibility(View.INVISIBLE);
			setTime.setVisibility(View.INVISIBLE);
			setV_fu.setVisibility(View.INVISIBLE);
			setV_jun.setVisibility(View.INVISIBLE);
			setVdown.setVisibility(View.INVISIBLE);
			setZhuanhuanA.setVisibility(View.INVISIBLE);
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };//42 42
					byte [] b2={0x46,0x46};
					byte[] bb = dau.sendSetCmd(recByte, "42", b2);
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD="42";
					countNum=0;
				}
				
			}
		});
		getPowerInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("94") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };//42 94
					byte [] b2={0x30,0x30};
					byte[] bb = dau.sendSetCmd(recByte, "94", b2);
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD="94";
					countNum=0;
				}
				
			}
		});
		getSurfaceTemp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("8042") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x38, 0x30 };//80 42
					byte [] b2={0x30,0x30};
					byte[] bb = dau.sendSetCmd(recByte, "42", b2);
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD="8042";
					countNum=0;
				}
				
			}
		});
		getSurfaceTempState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("8044") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x38, 0x30 };//80 44
					byte [] b2={0x30,0x30};
					byte[] bb = dau.sendSetCmd(recByte, "44", b2);
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD="8044";
					countNum=0;
				}
				
			}
		});
		getSurfaceTemplimit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("8047") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x38, 0x30 };//80 44
					byte [] b2={0x30,0x30};
					byte[] bb = dau.sendSetCmd(recByte, "47", b2);
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD="8047";
					countNum=0;
				}
				
			}
		});
		getAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				txtFuseStateText.setText(getActivity().getResources().getString(R.string.dc_fuse1));//直流熔丝/开关1:
				if (sendCMD.length() > 0 && sendCMD.compareTo("44") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };//42 44
					byte [] b2={0x46,0x46};
					byte[] bb = dau.sendSetCmd(recByte, "44", b2);
					//sendCommand(bb);
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };//42 47
					byte[] b = dau.AssembleReadData(recByte, "47");
					sendCMD="47";
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
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };//42 49
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
		setVdown.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
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
		
		setTime.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=conEqualizingCycle.getText().toString();
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
		
		setV_jun.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=conEqualizingV.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x35,b1[0],b1[1],b1[2],b1[3]};
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
		
		setV_fu.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=conFloatingV.getText().toString();
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
		//设置充电限流值
		setChongdianA.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=editChongdianA.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);//20180911倍率乘以100
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x32,b1[0],b1[1],b1[2],b1[3]};
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
		//均浮转换(电流)
		setZhuanhuanA.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=editZhuanhuanA.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*1000);//20180911倍率乘以1000
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
		//均充时间
		setJunTime.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=editJunTime.getText().toString();
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
		//参考温度
		setCankaoTem.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=editCankaoTem.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x41,b1[0],b1[1],b1[2],b1[3]};
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
						}
						sendCMD="";
					}
				}
			}
		});
		//第一组蓄电池标称容量
		setBattery1.setOnClickListener(new OnClickListener() {
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
							byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
							String vUplimit=editBattery1.getText().toString();
							if(vUplimit.length()==0){
								Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
										1500).show();// toast显示1000ms
							}else{
								float vUp=Float.parseFloat(vUplimit);
								int v=(int) (vUp*100);
								byte []b1=dau.intToAscByte4(v);
								byte [] bb={0x38,0x42,b1[0],b1[1],b1[2],b1[3]};
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
		//第一组蓄电池标称容量
				setBattery2.setOnClickListener(new OnClickListener() {
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
									byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
									String vUplimit=editBattery2.getText().toString();
									if(vUplimit.length()==0){
										Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
												1500).show();// toast显示1000ms
									}else{
										float vUp=Float.parseFloat(vUplimit);
										int v=(int) (vUp*100);
										byte []b1=dau.intToAscByte4(v);
										byte [] bb={0x38,0x43,b1[0],b1[1],b1[2],b1[3]};
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
				
		editFuse.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				openSelectDialog();
				return false;
			}
		});
		//设置均充保持时间
		junpesistTimeBtn.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x32 };
					String vUplimit=junpesistTimeEdit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						float vUp=Float.parseFloat(vUplimit);
						int v=(int) (vUp*100);
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
		
		KetiTextupperlimitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("8049") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x38, 0x30 };//80 49
					String vUplimit=KetiTextupperlimit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						//float vUp=Float.parseFloat(vUplimit);
						//int v=(int) (vUp*100);
						int v=Integer.valueOf(vUplimit);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x30,b1[0],b1[1],b1[2],b1[3]};//a9
						byte[] b = dau.sendSetCmd(recByte, "49",bb);
						sendCMD = "8049";
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
		KetiTextlowerlimitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("8049") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x38, 0x30 };//80 49
					String vUplimit=KetiTextlowerlimit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						//float vUp=Float.parseFloat(vUplimit);
						//int v=(int) (vUp*10);
						int v=Integer.valueOf(vUplimit);
						byte []b1=dau.intToAscByte4(v);
						byte [] bb={0x38,0x31,b1[0],b1[1],b1[2],b1[3]};
						byte[] b = dau.sendSetCmd(recByte, "49",bb);//aa
						sendCMD = "8049";
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

	/**
	 * 收到蓝牙数据解析
	 * @param data1
	 */
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
							
							dau.AscToHex2(b2, b2.length);
							byte[] bb = dau.Data_Close(b2, b2.length);
							if (sendCMD.compareTo("42") == 0) {
								//int m=bb[1];
								int zhiLiuV = DataAssembleUtil.parseInt(bb[3], bb[2]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);
								int fuzaiA = DataAssembleUtil.parseInt(bb[5], bb[4]);//((bb[5]) & 0xFF | (bb[4] & 0xFF) << 8);
								//long dianliang=DataAssembleUtil.parseInt(bb[6], bb[7],bb[8], bb[9]);
								//int ketiT = DataAssembleUtil.parseInt(bb[11], bb[10]);
								int fuzaiA2 = DataAssembleUtil.parseInt(bb[8], bb[7]);//((bb[8]) & 0xFF | (bb[7] & 0xFF) << 8);
								//int m2 =bb[6];
								DecimalFormat df=new DecimalFormat("0.00");
								/*if(bb.length>=10+m2*2){
									int fenLu1A=((bb[9+m2*2]) & 0xFF | (bb[8+m2*2] & 0xFF) << 8);
									int fenLu2A=((bb[11+m2*2]) & 0xFF | (bb[10+m2*2] & 0xFF) << 8);
									String sShunt1=df.format((float)fenLu1A/100);
									String sShunt2=df.format((float)fenLu2A/100);

									//sysShuntA1.setText(sShunt1);
									//sysShuntA2.setText(sShunt2);
								}*/
								String aVf=df.format((float)zhiLiuV/100);
								String Af=df.format((float)fuzaiA/100);
								String Af2=df.format((float)fuzaiA2/100);
								//String dianl=df.format((float)dianliang/100);
								//String ketiTS=df.format((float)ketiT/10);
								//sysScreenNumber.setText(""+m);
								sysOutputV.setText(aVf);
								sysCurrent.setText(Af);
								totalCurrent.setText(Af2);
								//dctotpowerText.setText(dianl);
								//KetiText.setText(ketiTS);
							}else if(sendCMD.compareTo("44") == 0){
								stateArr=null;
								if(bb.length<8){
									Toast.makeText(getActivity(), getResources().getString(R.string.acToast3), 500).show();//收到数据异常！
								}else{
									int m=bb[1];//直流屏数量 M（
									int Valarm=bb[2];//直流电压 
									int m2=bb[3];
									int rongSi1=bb[4];//电池 1 熔丝 
									int rongSi2=bb[5];//电池 2 熔丝 
									int xiaDian1=bb[5+m2];//一次下电 
									int xiaDian2=bb[6+m2];//二次下电
									int pos=8+m2;//电池数量位置
									
									//alarmScreenNumber.setText(""+m);
									
									stateArr=new String[m2];
								
									for(int i=0;i<m2;i++){
										int v2=bb[6+i];
										if (v2 == 00) {
											stateArr[i]=getResources().getString(R.string.normal);//正常
										} else if (v2 == 01) {
											stateArr[i]=getResources().getString(R.string.lowerlimit);//低于下限
										} else if (v2 == 02) {
											stateArr[i]=getResources().getString(R.string.upperlimit);//高于上限
										} else if (v2 == 03) {
											stateArr[i]=getResources().getString(R.string.dc_fuseOff);//熔丝断
										} else if (v2 == 04) {
											stateArr[i]=getResources().getString(R.string.dc_switchOn);//开关打开
										}
									}
									editFuse.setText(stateArr[0]);
								
									if (Valarm == 00) {
										alarmDCV.setText(getResources().getString(R.string.normal));//正常
									} else if (Valarm == 01) {
										alarmDCV.setText(getResources().getString(R.string.lowerlimit));//低于下限
									} else if (Valarm == 02) {
										alarmDCV.setText(getResources().getString(R.string.upperlimit));//高于上限
									} else if (Valarm == 03) {
										alarmDCV.setText(getResources().getString(R.string.dc_fuseOff));//熔丝断
									} else if (Valarm == 04) {
										alarmDCV.setText(getResources().getString(R.string.dc_switchOn));//开关打开
									}

									if (rongSi1 == 00) {
										alarmFuse1.setText(getResources().getString(R.string.normal));//正常
									} else if (rongSi1 == 01) {
										alarmFuse1.setText(getResources().getString(R.string.lowerlimit));//低于下限
									} else if (rongSi1 == 02) {
										alarmFuse1.setText(getResources().getString(R.string.upperlimit));//高于上限
									} else if (rongSi1 == 03) {
										alarmFuse1.setText(getResources().getString(R.string.dc_fuseOff));//熔丝断
									} else if (rongSi1 == 04) {
										alarmFuse1.setText(getResources().getString(R.string.dc_switchOn));//开关打开
									}
								
									if (rongSi2 == 00) {
										alarmFuse2.setText(getResources().getString(R.string.normal));//正常
									} else if (rongSi2 == 01) {
										alarmFuse2.setText(getResources().getString(R.string.lowerlimit));//低于下限
									} else if (rongSi2 == 02) {
										alarmFuse2.setText(getResources().getString(R.string.upperlimit));//高于上限
									} else if (rongSi2 == 03) {
										alarmFuse2.setText(getResources().getString(R.string.dc_fuseOff));//熔丝断
									} else if (rongSi2 == 04) {
										alarmFuse2.setText(getResources().getString(R.string.dc_switchOn));//开关打开
									}
								
									if (xiaDian1 == -128) {
										alarmElectric1.setText(getResources().getString(R.string.dc_level1));//一次下电报警
									} else {
										alarmElectric1.setText(getResources().getString(R.string.normal));//正常
									} 
								
									if (xiaDian2 == -127) {
										alarmElectric2.setText(getResources().getString(R.string.dc_level2));//二次下电报警
									} else{
										alarmElectric2.setText(getResources().getString(R.string.normal));//正常
									} 
									
									
									/*int dcsl=bb[pos];//电池数量
									for(int i=0;i<dcsl;i++){
										pos++;
									}//充电过流
									for(int i=0;i<dcsl;i++){
										pos++;
									}//放电告警
									for(int i=0;i<dcsl;i++){
										pos++;
									}//熔丝告警*/
									//pos++;//壳体温度状态位置
									/*int ktwdzt=bb[pos];
									if (ktwdzt == 0) {
										KeTiState.setText(getResources().getString(R.string.normal));//正常
									} else if(ktwdzt==1){
										KeTiState.setText(getResources().getString(R.string.belowTemp));//低温告警
									} else if(ktwdzt==2){
										KeTiState.setText(getResources().getString(R.string.overTemp));//高温告警
									}*/
								}
							
							}else if(sendCMD.compareTo("47") == 0){
								if(bb.length<27){
									Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 500).show();// toast显示1000ms	收到数据长度异常！
								}else{
									int Vup = DataAssembleUtil.parseInt(bb[1], bb[0]);//((bb[1]) & 0xFF | (bb[0] & 0xFF) << 8);
									int Vdown = DataAssembleUtil.parseInt(bb[3], bb[2]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);
									int p= bb[4];
									int junchongA=DataAssembleUtil.parseInt(bb[6], bb[5]);//((bb[6]) & 0xFF | (bb[5] & 0xFF) << 8);//设置充电限流值   （单位：A）
									int junzhuanA=DataAssembleUtil.parseUnsignedInt(bb[8], bb[7]);//((bb[8]) & 0xFF | (bb[7] & 0xFF) << 8);//均浮转换电流   100（单位：A）
									int fuchongV = DataAssembleUtil.parseInt(bb[10], bb[9]);//((bb[10]) & 0xFF | (bb[9] & 0xFF) << 8);//
									int junChongV = DataAssembleUtil.parseInt(bb[12], bb[11]);//((bb[12]) & 0xFF | (bb[11] & 0xFF) << 8);
								
									int junChongTime = DataAssembleUtil.parseInt(bb[14], bb[13]);//((bb[14]) & 0xFF | (bb[13] & 0xFF) << 8);
									int junChongT = DataAssembleUtil.parseInt(bb[16], bb[15]);//((bb[16]) & 0xFF | (bb[15] & 0xFF) << 8);//设均充周期 （单位：Day）
									int junPersist = DataAssembleUtil.parseInt(bb[18], bb[17]);//((bb[18]) & 0xFF | (bb[17] & 0xFF) << 8);//均充保持时间     （单位：Min）
									int wenduCan = DataAssembleUtil.parseInt(bb[22], bb[21]);//((bb[22]) & 0xFF | (bb[21] & 0xFF) << 8);//参考温度         （单位：℃）
									int battery1 = DataAssembleUtil.parseInt(bb[24], bb[23]);//((bb[24]) & 0xFF | (bb[23] & 0xFF) << 8);//参考温度         （单位：℃）
									int battery2 = DataAssembleUtil.parseInt(bb[26], bb[25]);//((bb[26]) & 0xFF | (bb[25] & 0xFF) << 8);//参考温度         （单位：℃）
								
								
									DecimalFormat df=new DecimalFormat("0.00");
									String junxianA=df.format((float)junchongA/100);//设置充电限流值   （单位：A）20180926 倍率为100
									String junzhunaAs=df.format((float)junzhuanA/1000);//均浮转换电流   1000（单位：A）
									String junchongTimeS=df.format((float)junChongTime/100);//设均充时间     100  （单位：H）
									String junPersistS=df.format((float)junPersist/100);//设均充时间     100  （单位：H）
									String cankaoTem=df.format((float)wenduCan/100);
									String battery1S=df.format((float)battery1/100);
									String battery2S=df.format((float)battery2/100);
									
									String Vup2=df.format((float)Vup/100);//直流电压上限 （单位：V）
									String Vdown2=df.format((float)Vdown/100);//直流电压下限 （单位：V）
									String fuchongV2=df.format((float)fuchongV/100);//设浮充电压 （单位：V）
									String junChongV2=df.format((float)junChongV/100);//均充电压 （单位：V） 
									
									conVupperlimit.setText(Vup2);
									conVlowerlimit.setText(Vdown2);
									conEqualizingCycle.setText(""+junChongT/10);
									conEqualizingV.setText(junChongV2);
									conFloatingV.setText(fuchongV2);
									editChongdianA.setText(junxianA);
									editZhuanhuanA.setText(junzhunaAs);
									editJunTime.setText(junchongTimeS);
									editCankaoTem.setText(cankaoTem);
									editBattery1.setText(battery1S);
									editBattery2.setText(battery2S);
									junpesistTimeEdit.setText(junPersistS);
									int flcgqsm= DataAssembleUtil.parseInt(bb[72], bb[71]);;//71 72是分路传感器数目
									int pos=73;
									for(int i=0;i<flcgqsm;i++){
										pos=pos+2;
									}
									pos=pos+2;//测试容量
									pos=pos+2;//
									
									//int  jkwdsx=DataAssembleUtil.parseInt(bb[pos+1], bb[pos]);
									//int  jkwdxx=DataAssembleUtil.parseInt(bb[pos+3], bb[pos+2]);
									
									//String jkwdsxS=df.format((float)jkwdsx/10);//机壳温度上限
									//String jkwdxxS=df.format((float)jkwdxx/10);//机壳温度下限
									
									//String jkwdsxS=String.valueOf(jkwdsx);//机壳温度上限
									//String jkwdxxS=String.valueOf(jkwdxx);//机壳温度下限
									//KetiTextupperlimit.setText(jkwdsxS);
									//KetiTextlowerlimit.setText(jkwdxxS);
								}
								
							}else if(sendCMD.compareTo("94") == 0){
								int m=bb[0];
								int pos=1;
								for(int i=0;i<m;i++){
									pos=pos+2;
								}
								int n=bb[pos];
								long dianliang=DataAssembleUtil.parseInt(bb[pos+1], bb[pos+2], bb[pos+3], bb[pos+4]);//电能板客户1电能高位
								DecimalFormat df=new DecimalFormat("0.00");
								String dianl=df.format((float)dianliang/100);
								dctotpowerText.setText(dianl);
							}else if(sendCMD.compareTo("8042") == 0){
								int wenDuM=bb[1];//温度传感器数量
								int ketiT = DataAssembleUtil.parseInt(bb[3], bb[2]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);//温度值
								DecimalFormat df=new DecimalFormat("0.00");
								String ketiTS="";
								if(ketiT==21930){
									ketiTS="--";
								}else{
									ketiTS=df.format((float)ketiT/10);
								}
								KetiText.setText(ketiTS);
							}else if(sendCMD.compareTo("8044") == 0){
								int wenDuAlarm=bb[1];//温度传感器告警状态
								
								if (wenDuAlarm == 00) {
									KeTiState.setText(getResources().getString(R.string.normal));//正常
								} else if(wenDuAlarm==01){
									KeTiState.setText(getResources().getString(R.string.belowTemp));//低温告警
								} else if(wenDuAlarm==02){
									KeTiState.setText(getResources().getString(R.string.overTemp));//高温告警
								}
							}else if(sendCMD.compareTo("8047") == 0){
								int Vup = DataAssembleUtil.parseInt(bb[1], bb[0]);//((bb[1]) & 0xFF | (bb[0] & 0xFF) << 8);
								int Vdown = DataAssembleUtil.parseInt(bb[3], bb[2]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);
								//DecimalFormat df=new DecimalFormat("0.00");
								//String Vup2=df.format((float)Vup/100);//温度上限 
								//String Vdown2=df.format((float)Vdown/100);//温度下限
								String Vup2=String.valueOf(Vup);//温度上限 
								String Vdown2=String.valueOf(Vdown);//温度下限
								KetiTextupperlimit.setText(Vup2);
								KetiTextlowerlimit.setText(Vdown2);
							}
						} else {
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
			recString = "";
			sendCMD = "";
		}
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
		writeFile("cmd_"+sb.toString());
	}*/
	
	private void openSelectDialog(){
		if(stateArr==null || stateArr.length==0){
			Toast.makeText(mContext,getResources().getString(R.string.dcToast1),1).show();//请先获取告警信息
			return;
		}
		sp=new Spinner(mContext);
		String mk=getResources().getString(R.string.dc_fuse);//直流熔丝/开关
		String s[]=new String[stateArr.length];
		for(int i=0;i<stateArr.length;i++){
			s[i]=mk+(i+1);
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
                    	txtFuseStateText.setText(sp.getSelectedItem().toString()+":");
                    	editFuse.setText(stateArr[position]);
                    	sp=null;
                    	dialog.dismiss();	
                    }
                })
		.create();
		
		dialog.show();
	}
}