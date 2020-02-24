package com.dgm.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.aes.AES;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

public class MicroStationFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private Button setSys, getSys, setCfg, getCfg, setVPN, getVPN, setUpdate,
			getUpdate;
	private TextView cmdA, cmdB, cmdC, cmdD;
	private EditText fsuID, switchID, towerID, cfgIP1, cfgIP2, cfgIP3, cfgIP4,
			cfgPort, vpnIPA1, vpnIPB1, vpnIPC1, vpnIPD1, vpnIPA2, vpnIPB2,
			vpnIPC2, vpnIPD2, vpnIPA3, vpnIPB3, vpnIPC3, vpnIPD3, vpnIPA4,
			vpnIPB4, vpnIPC4, vpnIPD4, updateIPA1, updateIPA2, updateIPA3,
			updateIPA4, updatePortA, updateIPB1, updateIPB2, updateIPB3,
			updateIPB4, updatePortB, setA, setB, setC, setD, vpn1user,vpn1pass,
			vpn2user,vpn2pass,vpn3user,vpn3pass,vpn4user,vpn4pass;
	private String[] itemsA1 ;
	private String[] itemsA2 = { "80", "81", "82", "84" };
	private String[] itemsB1 ;
	private String[] itemsB2 = { "80", "81", "82", "83", "84" };
	private String[] itemsC1 ;		
	private String[] itemsC2 = { "80", "81", "82", "83", "90", "91",
			"84", "85", "86","87","92", "93","88", "89", "8A", "8B", 
			"94", "95","8C", "8D", "8E", "8F","96", "97" };
	private String[] itemsD1 ;
	private String[] itemsD2 = { "80", "81", "82", "83", "84", "85", "86",
			"87", "88", "89" };
	private RadioButton rgnbkai,rgnbguan;
	boolean connect_status_bit = false;
	private DataAssembleUtil dau;
	public String sendCMD = "";// 上次发送指令CID2标示
	
	public int countNum = 0;

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
		mBaseView = inflater.inflate(R.layout.micro_station_fragment, null);
		mContext = getActivity();
		itemsA1= new String[]{ "FSUID",getResources().getString(R.string.kgdyId),getResources().getString(R.string.tdId),getResources().getString(R.string.nb) };//"FSUID", "开关电源ID", "铁塔ID","NB版功能"
		itemsB1=new String[]{ getResources().getString(R.string.msf_ip1), getResources().getString(R.string.msf_ip2), getResources().getString(R.string.msf_ip3),	//注册机IP地址-1	注册机IP地址-2	注册机IP地址-3	注册机IP地址-4	端口号
				getResources().getString(R.string.msf_ip4), getResources().getString(R.string.msf_port) };
		itemsC1=new String[]{ getResources().getString(R.string.msf_vpn1), getResources().getString(R.string.msf_vpn2),//VPN服务IP1地址-1	VPN服务IP1地址-2
				getResources().getString(R.string.msf_vpn3), getResources().getString(R.string.msf_vpn4),getResources().getString(R.string.msf_vpn1user),getResources().getString(R.string.msf_vpn1pass), //	VPN服务IP1地址-3	VPN服务IP1地址-4	VPN1用户名		VPN1密码
				getResources().getString(R.string.msf_vpn5),getResources().getString(R.string.msf_vpn6) ,getResources().getString(R.string.msf_vpn7),//VPN服务IP2地址-1	VPN服务IP2地址-2	VPN服务IP2地址-3	 
				getResources().getString(R.string.msf_vpn8),getResources().getString(R.string.msf_vpn2user) ,getResources().getString(R.string.msf_vpn2pass),getResources().getString(R.string.msf_vpn9),//VPN服务IP2地址-4	VPN2用户名		VPN2密码	VPN服务IP3地址-1 
				getResources().getString(R.string.msf_vpn10),getResources().getString(R.string.msf_vpn11) ,getResources().getString(R.string.msf_vpn12) ,//VPN服务IP3地址-2	VPN服务IP3地址-3		VPN服务IP3地址-4 
				getResources().getString(R.string.msf_vpn3user),getResources().getString(R.string.msf_vpn3pass),getResources().getString(R.string.msf_vpn13),getResources().getString(R.string.msf_vpn14) ,//VPN3用户名	VPN3密码		VPN服务IP4地址-1	VPN服务IP4地址-2
				getResources().getString(R.string.msf_vpn15),getResources().getString(R.string.msf_vpn16) ,getResources().getString(R.string.msf_vpn4user),getResources().getString(R.string.msf_vpn4pass) };//VPN服务IP4地址-3	VPN服务IP4地址-4	VPN4用户名		VPN4密码
		itemsD1=new String[]{ getResources().getString(R.string.msf_ip5), getResources().getString(R.string.msf_ip6), getResources().getString(R.string.msf_ip7),//升级IP1地址-1	升级IP1地址-2	升级IP1地址-3
				getResources().getString(R.string.msf_ip8), getResources().getString(R.string.msf_port2), getResources().getString(R.string.msf_ip9), getResources().getString(R.string.msf_ip10), getResources().getString(R.string.msf_ip11),//升级IP1地址-4	升级端口1	升级IP2地址-1	升级IP2地址-2	升级IP2地址-3
				getResources().getString(R.string.msf_ip12), getResources().getString(R.string.msf_port3) };//升级IP2地址-4	升级端口2
		dau = new DataAssembleUtil();
		getSys = (Button) mBaseView.findViewById(R.id.MicroStationSysGet);// 读取参数
		setSys = (Button) mBaseView.findViewById(R.id.MicroStationSysSet);// 设置参数
		getCfg = (Button) mBaseView.findViewById(R.id.MicroStationCfgGet);// 读取参数
		setCfg = (Button) mBaseView.findViewById(R.id.MicroStationCfgSet);// 设置参数
		getVPN = (Button) mBaseView.findViewById(R.id.MicroStationVPNIPGet);// 读取参数
		setVPN = (Button) mBaseView.findViewById(R.id.MicroStationVPNIPSet);// 设置参数
		setUpdate = (Button) mBaseView.findViewById(R.id.MicroStationUpdateSet);// 设置参数
		getUpdate = (Button) mBaseView.findViewById(R.id.MicroStationUpdateGet);// 读取参数

		fsuID = (EditText) mBaseView.findViewById(R.id.MicroStationFsuID);// FSUID

		switchID = (EditText) mBaseView.findViewById(R.id.MicroStationSwitchID);// FSUID

		towerID = (EditText) mBaseView.findViewById(R.id.MicroStationTowerID);// FSUID

		cfgIP1 = (EditText) mBaseView.findViewById(R.id.MicroStationIP1);// FSUID

		cfgIP2 = (EditText) mBaseView.findViewById(R.id.MicroStationIP2);// FSUID

		cfgIP3 = (EditText) mBaseView.findViewById(R.id.MicroStationIP3);// FSUID

		cfgIP4 = (EditText) mBaseView.findViewById(R.id.MicroStationIP4);// FSUID

		cfgPort = (EditText) mBaseView.findViewById(R.id.MicroStationPort);// FSUID

		vpnIPA1 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPA1);// FSUID

		vpnIPA2 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPA2);// FSUID

		vpnIPA3 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPA3);// FSUID

		vpnIPA4 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPA4);// FSUID

		vpnIPB1 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPB1);// FSUID

		vpnIPB2 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPB2);// FSUID

		vpnIPB3 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPB3);// FSUID

		vpnIPB4 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPB4);// FSUID

		vpnIPC1 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPC1);// FSUID

		vpnIPC2 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPC2);// FSUID

		vpnIPC3 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPC3);// FSUID

		vpnIPC4 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPC4);// FSUID

		vpnIPD1 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPD1);// FSUID

		vpnIPD2 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPD2);// FSUID

		vpnIPD3 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPD3);// FSUID

		vpnIPD4 = (EditText) mBaseView.findViewById(R.id.MicroStationVPNIPD4);// FSUID
		
		vpn1user= (EditText) mBaseView.findViewById(R.id.MicroStationVPN1user);// vpn1user
		vpn2user= (EditText) mBaseView.findViewById(R.id.MicroStationVPN2user);// vpn2user
		vpn3user= (EditText) mBaseView.findViewById(R.id.MicroStationVPN3user);// vpn3user
		vpn4user= (EditText) mBaseView.findViewById(R.id.MicroStationVPN4user);// vpn4user
		
		vpn1pass= (EditText) mBaseView.findViewById(R.id.MicroStationVPN1pass);// vpn1pass
		vpn2pass= (EditText) mBaseView.findViewById(R.id.MicroStationVPN2pass);// vpn2pass
		vpn3pass= (EditText) mBaseView.findViewById(R.id.MicroStationVPN3pass);// vpn3pass
		vpn4pass= (EditText) mBaseView.findViewById(R.id.MicroStationVPN4pass);// vpn4pass

		updateIPA1 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPA1);// FSUID

		updateIPA2 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPA2);// FSUID

		updateIPA3 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPA3);// FSUID

		updateIPA4 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPA4);// FSUID

		updatePortA = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdatePort1);// FSUID

		updateIPB1 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPB1);// FSUID

		updateIPB2 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPB2);// FSUID

		updateIPB3 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPB3);// FSUID

		updateIPB4 = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdateIPB4);// FSUID

		updatePortB = (EditText) mBaseView
				.findViewById(R.id.MicroStationUpdatePort2);// FSUID

		setA = (EditText) mBaseView.findViewById(R.id.MicroStationSys);// FSUID

		setB = (EditText) mBaseView.findViewById(R.id.MicroStationCfg);// FSUID

		setC = (EditText) mBaseView.findViewById(R.id.MicroStationVPN);// FSUID

		setD = (EditText) mBaseView.findViewById(R.id.MicroStationUpdate);// FSUID

		cmdA = (TextView) mBaseView.findViewById(R.id.MicroStationCmdA);// FSUID

		cmdB = (TextView) mBaseView.findViewById(R.id.MicroStationCmdB);// FSUID

		cmdC = (TextView) mBaseView.findViewById(R.id.MicroStationCmdC);// FSUID

		cmdD = (TextView) mBaseView.findViewById(R.id.MicroStationCmdD);// FSUID
		rgnbkai=(RadioButton) mBaseView.findViewById(R.id.rgnbkai);//开启nb功能版
		rgnbguan=(RadioButton) mBaseView.findViewById(R.id.rgnbguan);//关闭nb功能版
		if(DataAssembleUtil.setPriviledge){
			setCfg.setVisibility(View.VISIBLE);
			setSys.setVisibility(View.VISIBLE);
			setUpdate.setVisibility(View.VISIBLE);
			setVPN.setVisibility(View.VISIBLE);
		}else{
			setCfg.setVisibility(View.INVISIBLE);
			setSys.setVisibility(View.INVISIBLE);
			setUpdate.setVisibility(View.INVISIBLE);
			setVPN.setVisibility(View.INVISIBLE);
		}
		
		setA.setInputType(InputType.TYPE_NULL);
		setA.setOnTouchListener(new OnTouchListener() {

			// 按住和松开的标识
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// 自己业务
					// 隐藏软键盘
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(setA.getWindowToken(), 0);
					new AlertDialog.Builder(getActivity())
							.setTitle(getResources().getString(R.string.sele))//请选择
							.setItems(itemsA1,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int i) {
											setA.setText(itemsA1[i]);
											cmdA.setText(itemsA2[i]);
											
										}
									}).show();

					return true;
				}
				return false;
			}
		});
		setB.setInputType(InputType.TYPE_NULL);
		setB.setOnTouchListener(new OnTouchListener() {

			// 按住和松开的标识
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// 自己业务
					// 隐藏软键盘
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(setB.getWindowToken(), 0);
					new AlertDialog.Builder(getActivity())
							.setTitle(getResources().getString(R.string.sele))//请选择
							.setItems(itemsB1,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int i) {
											setB.setText(itemsB1[i]);
											cmdB.setText(itemsB2[i]);

										}
									}).show();

					return true;

				}
				return false;
			}
		});
		setC.setInputType(InputType.TYPE_NULL);
		setC.setOnTouchListener(new OnTouchListener() {
			// 按住和松开的标识
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// 自己业务
					// 隐藏软键盘
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(setC.getWindowToken(), 0);
					new AlertDialog.Builder(getActivity())
							.setTitle(getResources().getString(R.string.sele))//请选择
							.setItems(itemsC1,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int i) {
											setC.setText(itemsC1[i]);
											cmdC.setText(itemsC2[i]);

										}
									}).show();

					return true;

				}
				return false;
			}
		});
		setD.setInputType(InputType.TYPE_NULL);
		setD.setOnTouchListener(new OnTouchListener() {

			// 按住和松开的标识
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// 自己业务
					// 隐藏软键盘
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(setD.getWindowToken(), 0);
					new AlertDialog.Builder(getActivity())
							.setTitle(getResources().getString(R.string.sele))//请选择
							.setItems(itemsD1,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int i) {
											setD.setText(itemsD1[i]);
											cmdD.setText(itemsD2[i]);

										}
									}).show();

					return true;

				}
				return false;
			}
		});
		setSys.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String sendType = (String) cmdA.getText();
				int command = 0;
				if (sendCMD.length() > 0 && sendCMD.compareTo("81") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//D5 81

					String id = "";
					if (sendType.equals("80")) {
						id = fsuID.getText().toString();
					} else if (sendType.equals("81")) {
						id = switchID.getText().toString();
					} else if (sendType.equals("82")) {
						id = towerID.getText().toString();
					} else{
						if(rgnbkai.isChecked()){
							command=128;
						}else{
							command=0;
						}
						id="unknow";
					}
					if (id.length() == 0) {
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					} else if ((isID(id) == false)&& !id.equals("unknow")) {
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast1),//请输入14位长度ID,不包含字母及特殊符号!
								1500).show();// toast显示1000ms
					} else if(!sendType.equals("")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						byte[] newA = new byte[16];
						byte[] b;
						newA[0] = type[0];
						newA[1] = type[1];if(!sendType.equals("84")){
							for (int i = 1; i <= id.length() / 2; i++) {
								byte[] b1 = dau.stringHToAscByte2(id.substring(
										(i - 1) * 2, i * 2));
								System.out.println((i - 1) + "#" + (i * 2));
								newA[((i) * 2)] = b1[0];
								newA[((i) * 2 + 1)] = b1[1];
							}
							b = dau.sendSetCmd(recByte, "81", newA);
						}else{
							byte []b1=dau.intToAscByte2(command);
							byte[] n = new byte[4];
							n[0]=newA[0];
							n[1]=newA[1];
							n[2]=b1[0];
							n[3]=b1[1];
							
							b = dau.sendSetCmd(recByte, "81", n);
						}
						String s1 = new String(b);
						System.out.println(s1);
						sendCMD = "81";
						countNum = 0;
						byte[] b10 = new byte[20];
						for (int i = 0; i < 2; i++) {
							for (int j = 0; j < b10.length; j++) {
								if (b.length > (i * 20 + j)) {
									b10[j] = b[i * 20 + j];
								} else {
									b10[j] = 0;
								}
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//sendCommand(b10);
							DevicesInfo2Activity.mBluetoothLeService
									.sendCmd(b10);
						}
						sendCMD = "";
						recString="";
					}

				}

			}
		});
		getSys.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("80") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					// displayData("0");

					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//d5 80
					byte[] b = dau.AssembleReadData(recByte, "80");
					sendCMD = "80";
					countNum = 0;
					recString="";
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		setCfg.setOnClickListener(new OnClickListener() {

			private byte[] newA;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String sendType = (String) cmdB.getText();
				if (sendCMD.length() > 0 && sendCMD.compareTo("83") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//D5 83

					String id = "";
					Boolean isCorrect = false;
					if (sendType.equals("80")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = cfgIP1.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("81")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = cfgIP2.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("82")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = cfgIP3.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("83")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = cfgIP4.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("84")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = cfgPort.getText().toString();
						newA = new byte[6];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isPORT(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte4(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte4(Integer.parseInt(id))[1];
							newA[4] = dau.intToAscByte4(Integer.parseInt(id))[2];
							newA[5] = dau.intToAscByte4(Integer.parseInt(id))[3];
						}
					}
					if (id.length() == 0) {
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					} else if (isCorrect == false) {
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast2),//请输入合法的值
								 1500).show();// toast显示1000ms
					} else {
						byte[] b = dau.sendSetCmd(recByte, "83", newA);
						String s1 = new String(b);
						System.out.println(s1);
						sendCMD = "83";
						countNum = 0;
						byte[] b10 = new byte[20];
						for (int i = 0; i < 2; i++) {
							for (int j = 0; j < b10.length; j++) {
								if (b.length > (i * 20 + j)) {
									b10[j] = b[i * 20 + j];
								} else {
									b10[j] = 0;
								}
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//sendCommand(b10);
							DevicesInfo2Activity.mBluetoothLeService
									.sendCmd(b10);
						}
						sendCMD = "";
						recString="";
					}

				}

			}
		});
		getCfg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (sendCMD.length() > 0 && sendCMD.compareTo("82") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					// displayData("0");

					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//D5 82
					byte[] b = dau.AssembleReadData(recByte, "82");
					sendCMD = "82";
					countNum = 0;
					recString="";
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);

				}

			}
		});

		setVPN.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub

				String sendType = (String) cmdC.getText();
				if (sendCMD.length() > 0 && sendCMD.compareTo("85") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//D5 85

					String id = "";
					if (sendType.equals("80")) {
						id = vpnIPA1.getText().toString();
					} else if (sendType.equals("81")) {
						id = vpnIPA2.getText().toString();
					} else if (sendType.equals("82")) {
						id = vpnIPA3.getText().toString();
					} else if (sendType.equals("83")) {
						id = vpnIPA4.getText().toString();
					} else if (sendType.equals("84")) {
						id = vpnIPB1.getText().toString();
					} else if (sendType.equals("85")) {
						id = vpnIPB2.getText().toString();
					} else if (sendType.equals("86")) {
						id = vpnIPB3.getText().toString();
					} else if (sendType.equals("87")) {
						id = vpnIPB4.getText().toString();
					} else if (sendType.equals("88")) {
						id = vpnIPC1.getText().toString();
					} else if (sendType.equals("89")) {
						id = vpnIPC2.getText().toString();
					} else if (sendType.equals("8A")) {
						id = vpnIPC3.getText().toString();
					} else if (sendType.equals("8B")) {
						id = vpnIPC4.getText().toString();
					} else if (sendType.equals("8C")) {
						id = vpnIPD1.getText().toString();
					} else if (sendType.equals("8D")) {
						id = vpnIPD2.getText().toString();
					} else if (sendType.equals("8E")) {
						id = vpnIPD3.getText().toString();
					} else if (sendType.equals("8F")) {
						id = vpnIPD4.getText().toString();
					}else if (sendType.equals("90")) {
						id = vpn1user.getText().toString();
					}else if (sendType.equals("91")) {
						id = vpn1pass.getText().toString();
					}else if (sendType.equals("92")) {
						id = vpn2user.getText().toString();
					}else if (sendType.equals("93")) {
						id = vpn2pass.getText().toString();
					}else if (sendType.equals("94")) {
						id = vpn3user.getText().toString();
					}else if (sendType.equals("95")) {
						id = vpn3pass.getText().toString();
					}else if (sendType.equals("96")) {
						id = vpn4user.getText().toString();
					}else if (sendType.equals("97")) {
						id = vpn4pass.getText().toString();
					}
					if(sendType.startsWith("8")){
						if (id.length() == 0) {
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
									1500).show();// toast显示1000ms
						} else if (isIP(id) == false) {
							Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.acToast4),//请输入合法的IP地址 
									 1500)
							.show();// toast显示1000ms
						} else {
							byte[] type = dau.stringHToAscByte2(sendType);
							byte[] newA = new byte[4];
							newA[0] = type[0];
							newA[1] = type[1];
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
							
							byte[] b = dau.sendSetCmd(recByte, "85", newA);
							String s1 = new String(b);
							System.out.println(s1);
							sendCMD = "85";
							countNum = 0;
							byte[] b10 = new byte[20];
							for (int i = 0; i < 2; i++) {
								for (int j = 0; j < b10.length; j++) {
									if (b.length > (i * 20 + j)) {
										b10[j] = b[i * 20 + j];
									} else {
										b10[j] = 0;
									}
								}
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//sendCommand(b10);
								DevicesInfo2Activity.mBluetoothLeService
								.sendCmd(b10);
							}
							sendCMD = "";
							recString="";
						}
					}else{
						if (id.length() == 0) {
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
									1500).show();// toast显示1000ms
						}else if (isUserAndPass(id) == false) {
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast3),//请输入16位长度数字,不包含字母及特殊符号!
									1500).show();// toast显示1000ms
						}else {
							byte[] type = dau.stringHToAscByte2(sendType);
							byte[] newA = new byte[18];
							newA[0] = type[0];
							newA[1] = type[1];
							for (int i = 1; i <= id.length() / 2; i++) {
								byte[] b1 = dau.stringHToAscByte2(id.substring(
										(i - 1) * 2, i * 2));
								System.out.println((i - 1) + "#" + (i * 2));
								newA[((i) * 2)] = b1[0];
								newA[((i) * 2 + 1)] = b1[1];
							}
							
							byte[] b = dau.sendSetCmd(recByte, "85", newA);
							String s1 = new String(b);
							System.out.println(s1);
							sendCMD = "85";
							countNum = 0;
							byte[] b10 = new byte[20];
							for (int i = 0; i < 2; i++) {
								for (int j = 0; j < b10.length; j++) {
									if (b.length > (i * 20 + j)) {
										b10[j] = b[i * 20 + j];
									} else {
										b10[j] = 0;
									}
								}
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//sendCommand(b10);
								DevicesInfo2Activity.mBluetoothLeService
								.sendCmd(b10);
							}
							sendCMD = "";
							recString="";
						}
					}

				}

			}
		});
		getVPN.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (sendCMD.length() > 0 && sendCMD.compareTo("84") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					// displayData("0");
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//D5 84
					byte[] b = dau.AssembleReadData(recByte, "84");
					sendCMD = "84";
					countNum = 0;
					recString="";
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}

			}
		});
		setUpdate.setOnClickListener(new OnClickListener() {

			private byte[] newA;

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub

				String sendType = (String) cmdD.getText();
				if (sendCMD.length() > 0 && sendCMD.compareTo("87") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//D5 87

					String id = "";
					Boolean isCorrect = false;
					if (sendType.equals("80")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPA1.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("81")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPA2.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("82")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPA3.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("83")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPA4.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("84")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updatePortA.getText().toString();
						newA = new byte[6];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isPORT(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte4(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte4(Integer.parseInt(id))[1];
							newA[4] = dau.intToAscByte4(Integer.parseInt(id))[2];
							newA[5] = dau.intToAscByte4(Integer.parseInt(id))[3];
						}
					} else if (sendType.equals("85")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPB1.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("86")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPB2.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("87")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPB3.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("88")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updateIPB4.getText().toString();
						newA = new byte[4];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isIP(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte2(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte2(Integer.parseInt(id))[1];
						}
					} else if (sendType.equals("89")) {
						byte[] type = dau.stringHToAscByte2(sendType);
						id = updatePortB.getText().toString();
						newA = new byte[6];
						newA[0] = type[0];
						newA[1] = type[1];

						if (isPORT(id)) {
							isCorrect = true;
							newA[2] = dau.intToAscByte4(Integer.parseInt(id))[0];
							newA[3] = dau.intToAscByte4(Integer.parseInt(id))[1];
							newA[4] = dau.intToAscByte4(Integer.parseInt(id))[2];
							newA[5] = dau.intToAscByte4(Integer.parseInt(id))[3];
						}
					}
					if (id.length() == 0) {
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					} else if (isCorrect == false) {
						Toast.makeText(getActivity(),  getActivity().getResources().getString(R.string.msf_Toast2),//请输入合法的值
								 1500).show();// toast显示1000ms
					}

					else {

						byte[] b = dau.sendSetCmd(recByte, "87", newA);
						String s1 = new String(b);
						System.out.println(s1);
						sendCMD = "87";
						countNum = 0;
						byte[] b10 = new byte[20];
						for (int i = 0; i < 2; i++) {
							for (int j = 0; j < b10.length; j++) {
								if (b.length > (i * 20 + j)) {
									b10[j] = b[i * 20 + j];
								} else {
									b10[j] = 0;
								}
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//sendCommand(b10);
							DevicesInfo2Activity.mBluetoothLeService
									.sendCmd(b10);
						}
						sendCMD = "";
						recString="";
					}

				}

			}
		});
		getUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (sendCMD.length() > 0 && sendCMD.compareTo("86") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					// displayData("0");
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x35 };//D5 86
					byte[] b = dau.AssembleReadData(recByte, "86");
					sendCMD = "86";
					countNum = 0;
					recString="";
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
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

	// 14位 ID校验方法
	public static boolean isID(String str) {
		boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
		boolean isLength = false;
		int strLenth = str.length();
		if (strLenth == 14) {
			isLength = true;
		}
		return isInt && isLength;
	}
	
	// 16位 用户名校验方法
	public static boolean isUserAndPass(String str) {
		boolean isInt = Pattern.compile("^-?[0-9]\\d*$").matcher(str).find();
		boolean isLength = false;
		int strLenth = str.length();
		if (strLenth == 16) {
			isLength = true;
		}
		return isInt && isLength;
	}

	// IP地址校验方法
	public static boolean isIP (String str) {
		boolean isInt = Pattern.compile("^-?[0-9]\\d*$").matcher(str).find();
		boolean isIP = false;
		try {
			if (Integer.parseInt(str) < 256) {
				isIP = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		return isInt && isIP;
	}

	// PORT端口校验方法
	public static boolean isPORT(String str) {
		boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
		boolean isIP = false;
		try {
			if (Integer.parseInt(str) < 65536) {
				isIP = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return isInt && isIP;
	}

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
				//writeFile(recString);
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
						if(b.length<=16) return;
						byte[] b2 = new byte[b.length-16];
						//if (b.length > 12 + intLength) {
							//for (int j = 0; j < intLength; j++) {
							for (int j = 0; j < b2.length; j++) {
								b2[j] = b[j + 12];
							}
							// sendCMD = "86";
							
							if (sendCMD.compareTo("80") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								
								String fsuA = getNumber(bb[0]);
								String fsuB = getNumber(bb[1]);
								String fsuC = getNumber(bb[2]);
								String fsuD = getNumber(bb[3]);
								String fsuE = getNumber(bb[4]);
								String fsuF = getNumber(bb[5]);
								String fsuG = getNumber(bb[6]);
								
								String switchA = getNumber(bb[7]);
								String switchB = getNumber(bb[8]);
								String switchC = getNumber(bb[9]);
								String switchD = getNumber(bb[10]);
								String switchE = getNumber(bb[11]);
								String switchF = getNumber(bb[12]);
								String switchG = getNumber(bb[13]);
								
								String towerA = getNumber(bb[14]);
								String towerB = getNumber(bb[15]);
								String towerC = getNumber(bb[16]);
								String towerD = getNumber(bb[17]);
								String towerE = getNumber(bb[18]);
								String towerF = getNumber(bb[19]);
								String towerG = getNumber(bb[20]);
								
								String MsgfsuID = fsuA + "" + fsuB + "" + fsuC + ""
										+ fsuD + "" + fsuE + "" + fsuF + "" + fsuG;
								String MsgswitchID = switchA + "" + switchB + ""
										+ switchC + "" + switchD + "" + switchE
										+ "" + switchF + "" + switchG;
								String MsgtowerID = towerA + "" + towerB + ""
										+ towerC + "" + towerD + "" + towerE + ""
										+ towerF + "" + towerG;
								fsuID.setText(MsgfsuID);
								switchID.setText(MsgswitchID);
								towerID.setText(MsgtowerID);
								if(bb[23]==0x80){
									rgnbkai.setChecked(true);
								}else{
									rgnbguan.setChecked(true);
								}
								System.out.println(fsuA + "" + fsuB + "" + fsuC
										+ "" + fsuD + "" + fsuE + "" + fsuF + ""
										+ fsuG);
								recString = "";
								sendCMD = "";
								setA.setText("");
							} else if (sendCMD.compareTo("82") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int IPA = bb[0]&0xff;
								int IPB = bb[1]&0xff;
								int IPC = bb[2]&0xff;
								int IPD = bb[3]&0xff;
								int Port = ((bb[5]) & 0xFF | (bb[4] & 0xFF) << 8);// 端口
								
								cfgIP1.setText("" + IPA);
								cfgIP2.setText("" + IPB);
								cfgIP3.setText("" + IPC);
								cfgIP4.setText("" + IPD);
								cfgPort.setText("" + Port);
								
								recString = "";
								sendCMD = "";
								setB.setText("");
							} else if (sendCMD.compareTo("84") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int IPA1 = bb[0]&0xff;
								int IPA2 = bb[1]&0xff;
								int IPA3 = bb[2]&0xff;
								int IPA4 = bb[3]&0xff;
								int IPB1 = bb[4]&0xff;
								int IPB2 = bb[5]&0xff;
								int IPB3 = bb[6]&0xff;
								int IPB4 = bb[7]&0xff;
								int IPC1 = bb[8]&0xff;
								int IPC2 = bb[9]&0xff;
								int IPC3 = bb[10]&0xff;
								int IPC4 = bb[11]&0xff;
								int IPD1 = bb[12]&0xff;
								int IPD2 = bb[13]&0xff;
								int IPD3 = bb[14]&0xff;
								int IPD4 = bb[15]&0xff;
								
								DecimalFormat df=new DecimalFormat("00");
								
								String user1A =df.format(Integer.parseInt(Integer.toHexString(bb[16]&0xff)));
								String user1B = df.format(Integer.parseInt(Integer.toHexString(bb[17]&0xff)));
								String user1C = df.format(Integer.parseInt(Integer.toHexString(bb[18]&0xff)));
								String user1D = df.format(Integer.parseInt(Integer.toHexString(bb[19]&0xff)));
								String user1E = df.format(Integer.parseInt(Integer.toHexString(bb[20]&0xff)));
								String user1F = df.format(Integer.parseInt(Integer.toHexString(bb[21]&0xff)));
								String user1G = df.format(Integer.parseInt(Integer.toHexString(bb[22]&0xff)));
								String user1H = df.format(Integer.parseInt(Integer.toHexString(bb[23]&0xff)));
								
								String pass1A =df.format(Integer.parseInt(Integer.toHexString(bb[24]&0xff)));
								String pass1B = df.format(Integer.parseInt(Integer.toHexString(bb[25]&0xff)));
								String pass1C = df.format(Integer.parseInt(Integer.toHexString(bb[26]&0xff)));
								String pass1D = df.format(Integer.parseInt(Integer.toHexString(bb[27]&0xff)));
								String pass1E = df.format(Integer.parseInt(Integer.toHexString(bb[28]&0xff)));
								String pass1F = df.format(Integer.parseInt(Integer.toHexString(bb[29]&0xff)));
								String pass1G = df.format(Integer.parseInt(Integer.toHexString(bb[30]&0xff)));
								String pass1H = df.format(Integer.parseInt(Integer.toHexString(bb[31]&0xff)));
								
								String user1 = user1A + "" + user1B + "" + user1C + ""
										+ user1D + "" + user1E + "" + user1F + "" + user1G +"" +user1H;
								
								String pass1 = pass1A + "" + pass1B + "" + pass1C + ""
										+ pass1D + "" + pass1E + "" + pass1F + "" + pass1G +"" +pass1H;
								
								String user2A =df.format(Integer.parseInt(Integer.toHexString(bb[32]&0xff)));
								String user2B = df.format(Integer.parseInt(Integer.toHexString(bb[33]&0xff)));
								String user2C = df.format(Integer.parseInt(Integer.toHexString(bb[34]&0xff)));
								String user2D = df.format(Integer.parseInt(Integer.toHexString(bb[35]&0xff)));
								String user2E = df.format(Integer.parseInt(Integer.toHexString(bb[36]&0xff)));
								String user2F = df.format(Integer.parseInt(Integer.toHexString(bb[37]&0xff)));
								String user2G = df.format(Integer.parseInt(Integer.toHexString(bb[38]&0xff)));
								String user2H = df.format(Integer.parseInt(Integer.toHexString(bb[39]&0xff)));
								
								String pass2A =df.format(Integer.parseInt(Integer.toHexString(bb[40]&0xff)));
								String pass2B = df.format(Integer.parseInt(Integer.toHexString(bb[41]&0xff)));
								String pass2C = df.format(Integer.parseInt(Integer.toHexString(bb[42]&0xff)));
								String pass2D = df.format(Integer.parseInt(Integer.toHexString(bb[43]&0xff)));
								String pass2E = df.format(Integer.parseInt(Integer.toHexString(bb[44]&0xff)));
								String pass2F = df.format(Integer.parseInt(Integer.toHexString(bb[45]&0xff)));
								String pass2G = df.format(Integer.parseInt(Integer.toHexString(bb[46]&0xff)));
								String pass2H = df.format(Integer.parseInt(Integer.toHexString(bb[47]&0xff)));
								
								String user2 = user2A + "" + user2B + "" + user2C + ""
										+ user2D + "" + user2E + "" + user2F + "" + user2G +"" +user2H;
								
								String pass2 = pass2A + "" + pass2B + "" + pass2C + ""
										+ pass2D + "" + pass2E + "" + pass2F + "" + pass2G +"" +pass2H;
								
								String user3A =df.format(Integer.parseInt(Integer.toHexString(bb[48]&0xff)));
								String user3B = df.format(Integer.parseInt(Integer.toHexString(bb[49]&0xff)));
								String user3C = df.format(Integer.parseInt(Integer.toHexString(bb[50]&0xff)));
								String user3D = df.format(Integer.parseInt(Integer.toHexString(bb[51]&0xff)));
								String user3E = df.format(Integer.parseInt(Integer.toHexString(bb[52]&0xff)));
								String user3F = df.format(Integer.parseInt(Integer.toHexString(bb[53]&0xff)));
								String user3G = df.format(Integer.parseInt(Integer.toHexString(bb[54]&0xff)));
								String user3H = df.format(Integer.parseInt(Integer.toHexString(bb[55]&0xff)));
								
								String pass3A =df.format(Integer.parseInt(Integer.toHexString(bb[56]&0xff)));
								String pass3B = df.format(Integer.parseInt(Integer.toHexString(bb[57]&0xff)));
								String pass3C = df.format(Integer.parseInt(Integer.toHexString(bb[58]&0xff)));
								String pass3D = df.format(Integer.parseInt(Integer.toHexString(bb[59]&0xff)));
								String pass3E = df.format(Integer.parseInt(Integer.toHexString(bb[60]&0xff)));
								String pass3F = df.format(Integer.parseInt(Integer.toHexString(bb[61]&0xff)));
								String pass3G = df.format(Integer.parseInt(Integer.toHexString(bb[62]&0xff)));
								String pass3H = df.format(Integer.parseInt(Integer.toHexString(bb[63]&0xff)));
								
								String user3 = user3A + "" + user3B + "" + user3C + ""
										+ user3D + "" + user3E + "" + user3F + "" + user3G +"" +user3H;
								
								String pass3 = pass3A + "" + pass3B + "" + pass3C + ""
										+ pass3D + "" + pass3E + "" + pass3F + "" + pass3G +"" +pass3H;
								
								
								String user4A =df.format(Integer.parseInt(Integer.toHexString(bb[64]&0xff)));
								String user4B = df.format(Integer.parseInt(Integer.toHexString(bb[65]&0xff)));
								String user4C = df.format(Integer.parseInt(Integer.toHexString(bb[66]&0xff)));
								String user4D = df.format(Integer.parseInt(Integer.toHexString(bb[67]&0xff)));
								String user4E = df.format(Integer.parseInt(Integer.toHexString(bb[68]&0xff)));
								String user4F = df.format(Integer.parseInt(Integer.toHexString(bb[69]&0xff)));
								String user4G = df.format(Integer.parseInt(Integer.toHexString(bb[70]&0xff)));
								String user4H = df.format(Integer.parseInt(Integer.toHexString(bb[71]&0xff)));
								
								String pass4A =df.format(Integer.parseInt(Integer.toHexString(bb[72]&0xff)));
								String pass4B = df.format(Integer.parseInt(Integer.toHexString(bb[73]&0xff)));
								String pass4C = df.format(Integer.parseInt(Integer.toHexString(bb[74]&0xff)));
								String pass4D = df.format(Integer.parseInt(Integer.toHexString(bb[75]&0xff)));
								String pass4E = df.format(Integer.parseInt(Integer.toHexString(bb[76]&0xff)));
								String pass4F = df.format(Integer.parseInt(Integer.toHexString(bb[77]&0xff)));
								String pass4G = df.format(Integer.parseInt(Integer.toHexString(bb[78]&0xff)));
								String pass4H = df.format(Integer.parseInt(Integer.toHexString(bb[79]&0xff)));
								
								String user4 = user4A + "" + user4B + "" + user4C + ""
										+ user4D + "" + user4E + "" + user4F + "" + user4G +"" +user4H;
								
								String pass4 = pass4A + "" + pass4B + "" + pass4C + ""
										+ pass4D + "" + pass4E + "" + pass4F + "" + pass4G +"" +pass4H;
								
								
								vpnIPA1.setText("" + IPA1);
								vpnIPA2.setText("" + IPA2);
								vpnIPA3.setText("" + IPA3);
								vpnIPA4.setText("" + IPA4);
								vpnIPB1.setText("" + IPB1);
								vpnIPB2.setText("" + IPB2);
								vpnIPB3.setText("" + IPB3);
								vpnIPB4.setText("" + IPB4);
								vpnIPC1.setText("" + IPC1);
								vpnIPC2.setText("" + IPC2);
								vpnIPC3.setText("" + IPC3);
								vpnIPC4.setText("" + IPC4);
								vpnIPD1.setText("" + IPD1);
								vpnIPD2.setText("" + IPD2);
								vpnIPD3.setText("" + IPD3);
								vpnIPD4.setText("" + IPD4);
								
								vpn1user.setText("" + user1);
								vpn2user.setText("" + user2);
								vpn3user.setText("" + user3);
								vpn4user.setText("" + user4);
								vpn1pass.setText("" + pass1);
								vpn2pass.setText("" + pass2);
								vpn3pass.setText("" + pass3);
								vpn4pass.setText("" + pass4);
								
								recString = "";
								sendCMD = "";
								setC.setText("");
							} else if (sendCMD.compareTo("86") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int IPA1 = bb[0]&0xff;
								int IPA2 = bb[1]&0xff;
								int IPA3 = bb[2]&0xff;
								int IPA4 = bb[3]&0xff;
								int PortA = ((bb[5]) & 0xFF | (bb[4] & 0xFF) << 8);// 端口
								
								int IPB1 = bb[6]&0xff;
								int IPB2 = bb[7]&0xff;
								int IPB3 = bb[8]&0xff;
								int IPB4 = bb[9]&0xff;
								int PortB = ((bb[11]) & 0xFF | (bb[10] & 0xFF) << 8);// 端口
								
								updateIPA1.setText("" + IPA1);
								updateIPA2.setText("" + IPA2);
								updateIPA3.setText("" + IPA3);
								updateIPA4.setText("" + IPA4);
								updatePortA.setText("" + PortA);
								updateIPB1.setText("" + IPB1);
								updateIPB2.setText("" + IPB2);
								updateIPB3.setText("" + IPB3);
								updateIPB4.setText("" + IPB4);
								updatePortB.setText("" + PortB);
								
								recString = "";
								sendCMD = "";
								setD.setText("");
							}
							
							//}
				}	
					
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
		System.out.println(sb.toString());
		//writeFile("cmd_"+sb.toString());
	}*/
}
