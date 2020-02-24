package com.dgm.fragment;

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
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dgm.aes.AES;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

public class EnvironmentProtocolFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private Button getSysMoni, getAlarm, getConfig, setConfig, enviSend;
	private EditText sysTemperatureNumber, sysTemperature, sysHumidityNumber,
			sysHumidity, alarmTemperature, alarmHumidity, alarmSmoke,
			alarMimmersion, alarmInfrared, alarmDoor, alarmGlass,
			alarmSensorNumber, alarmSensor, envicontrol, conTemperatureNumber,
			conTemperatureUpperlimit, conTemperatureLowerlimit,
			conHumidityNumber, conHumidityUpperlimit, conHumidityLowerlimit;
	private String[] items = { "门1", "门2", getResources().getString(R.string.dc_all) };//所有
	boolean connect_status_bit = false;
	private DataAssembleUtil dau;
	public String sendCMD = "";//上次发送指令CID2标示
	public int countNum=0;

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
		mBaseView = inflater.inflate(R.layout.enviroment_protocol_fragment,
				null);
		mContext = getActivity();
		dau = new DataAssembleUtil();
		getSysMoni = (Button) mBaseView.findViewById(R.id.getenvisysmoni);// 获取系统模拟量
		getAlarm = (Button) mBaseView.findViewById(R.id.getenvialarm);// 获取环境告警状态
		enviSend = (Button) mBaseView.findViewById(R.id.envisend);// 发送遥控指令
		getConfig = (Button) mBaseView.findViewById(R.id.getenviconfig);// 获取参数
		setConfig = (Button) mBaseView.findViewById(R.id.setenviconfig);// 设置参数

		// 获取系统模拟量
		sysTemperatureNumber = (EditText) mBaseView
				.findViewById(R.id.envisystemperaturesensorText);// 温度传感器数量
		sysTemperature = (EditText) mBaseView
				.findViewById(R.id.envisystemperatureText);// 温度
		sysHumidityNumber = (EditText) mBaseView
				.findViewById(R.id.envisyshumiditysensorText);// 湿度传感器
		sysHumidity = (EditText) mBaseView
				.findViewById(R.id.envisyshumidityText);// 湿度

		// 获取环境告警状态
		alarmTemperature = (EditText) mBaseView
				.findViewById(R.id.envialarmtemperatureText);// 温度传感器告警
		alarmHumidity = (EditText) mBaseView
				.findViewById(R.id.envialarmhumidityText);// 湿度传感器告警
		alarmSmoke = (EditText) mBaseView.findViewById(R.id.envialarmsmokeText);// 烟雾传感器告警
		alarMimmersion = (EditText) mBaseView
				.findViewById(R.id.envialarmimmersionText);// 水浸传感告警
		alarmInfrared = (EditText) mBaseView
				.findViewById(R.id.envialarmInfraredText);// 红外传感器告警
		alarmDoor = (EditText) mBaseView.findViewById(R.id.envialarmdoorText);// 门窗传感器告警
		alarmGlass = (EditText) mBaseView.findViewById(R.id.envialarmglassText);// 玻璃破碎传感器告警
		alarmSensorNumber = (EditText) mBaseView
				.findViewById(R.id.envialarmsensornumberText);// 传感器数量
		alarmSensor = (EditText) mBaseView
				.findViewById(R.id.envialarmsensorText);// 传感器

		// 遥控
		envicontrol = (EditText) mBaseView.findViewById(R.id.envicontrol);// 遥控

		// 获取和设置参数
		conTemperatureNumber = (EditText) mBaseView
				.findViewById(R.id.enviconfigtemperaturenumberText);// 温度传感器数量
		conTemperatureUpperlimit = (EditText) mBaseView
				.findViewById(R.id.enviconfigtemperatureupperlimitText);// 温度传感器上限
		conTemperatureLowerlimit = (EditText) mBaseView
				.findViewById(R.id.enviconfigtemperaturelowerlimitText);// 温度传感器下限
		conHumidityNumber = (EditText) mBaseView
				.findViewById(R.id.enviconfighumiditynumberText);// 湿度传感器数量
		conHumidityUpperlimit = (EditText) mBaseView
				.findViewById(R.id.enviconfighumidityupperlimitText);// 湿度传感器上限
		conHumidityLowerlimit = (EditText) mBaseView
				.findViewById(R.id.enviconfighumiditylowerlimitText);// 湿度传感器下限

		envicontrol.setInputType(InputType.TYPE_NULL);
		getSysMoni.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x38, 0x30 };
				byte[] b = dau.AssembleReadData(recByte, "42");
				//sendCommand(b);
				DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
				//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
			}
		});
		getAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x38, 0x30 };
				byte[] b = dau.AssembleReadData(recByte, "44");
				DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
				//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
			}
		});
		enviSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
		getConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x38, 0x30 };
				byte[] b = dau.AssembleReadData(recByte, "47");
				//sendCommand(b);
				DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
				//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
			}
		});

		setConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		envicontrol.setOnTouchListener(new OnTouchListener() {

			// 按住和松开的标识
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// 自己业务
					// 隐藏软键盘
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(envicontrol.getWindowToken(), 0);
					new AlertDialog.Builder(getActivity())
							.setTitle(getResources().getString(R.string.sele))//请选择
							.setItems(items,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int i) {
											envicontrol.setText(items[i]);
										}
									}).show();

					return true;

				}
				return false;
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
	}
*/
	public String recString = "";
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
				//	writeFile(recString);
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
							dau.AscToHex2(b2, b2.length);
							byte[] bb = dau.Data_Close(b2, b2.length);
							if (sendCMD.compareTo("42") == 0) {
								int wenDuM=bb[1];//温度传感器数量
								int temperature1 = DataAssembleUtil.parseInt(bb[3], bb[2]);//((bb[3]) & 0xFF | (bb[2] & 0xFF) << 8);//温度值
								int x=2+wenDuM*2;
								int shiDuM=bb[x];//湿度传感器数量
								int shiDu1=DataAssembleUtil.parseInt(bb[x+2], bb[x+1]);//((bb[x+2]) & 0xFF | (bb[x+1] & 0xFF) << 8);//湿度
								sysTemperatureNumber.setText(wenDuM);
								sysTemperature.setText(temperature1);
								sysHumidityNumber.setText(shiDuM);
								sysHumidity.setText(shiDu1);
								
							}else if (sendCMD.compareTo("44") == 0) {
								int m1=bb[1];//温度传感器数量
								int wenDuAlarm=bb[2];//温度传感器告警状态
								int m2 =bb[2+m1];
								int shiDuAlarm=bb[3+m1];//湿度传感器告警内容
								int m3=bb[3+m1+m2];
								int yanWuAlarm=bb[4+m1+m2];//烟雾传感器告警内容 1 
								int m4 = bb[4+m1+m2+m3];
								int shuiQinAlarm=bb[5+m1+m2+m3];//水浸传感器告警内容 
								int m5 =bb[5+m1+m2+m3+m4];
								int hongWaiAlarm=bb[6+m1+m2+m3+m4];//红外传感器告警内容
								int m6=bb[6+m1+m2+m3+m4+m5];
								int menChuangAlarm=bb[7+m1+m2+m3+m4+m5];//门窗传感器告警内容 1 
								int m7=bb[7+m1+m2+m3+m4+m5+m6];
								int boLiAlarm=bb[8+m1+m2+m3+m4+m5+m6];//玻璃破碎传感器内容 1 
								
								alarmSensorNumber.setText(m1);
								
								if (wenDuAlarm == 00) {
									alarmTemperature.setText(getResources().getString(R.string.normal));//正常
								} else if (wenDuAlarm == 01) {
									alarmTemperature.setText(getResources().getString(R.string.lowerlimit));//低于下限
								} else if (wenDuAlarm == 02) {
									alarmTemperature.setText(getResources().getString(R.string.upperlimit));//高于上限
								} else if (wenDuAlarm == 04) {
									alarmTemperature.setText(getResources().getString(R.string.alarm));//告警
								} else if (wenDuAlarm == 05) {
									alarmTemperature.setText(getResources().getString(R.string.dc_bournout));//传感器断线
								}
								
								if (shiDuAlarm == 00) {
									alarmHumidity.setText(getResources().getString(R.string.normal));//正常
								} else if (shiDuAlarm == 01) {
									alarmHumidity.setText(getResources().getString(R.string.lowerlimit));//低于下限
								} else if (shiDuAlarm == 02) {
									alarmHumidity.setText(getResources().getString(R.string.upperlimit));//高于上限
								} else if (shiDuAlarm == 04) {
									alarmHumidity.setText(getResources().getString(R.string.alarm));//告警
								} else if (shiDuAlarm == 05) {
									alarmHumidity.setText(getResources().getString(R.string.dc_bournout));//传感器断线
								}
								
								if (yanWuAlarm == 00) {
									alarmSmoke.setText(getResources().getString(R.string.normal));//正常
								} else if (yanWuAlarm == 01) {
									alarmSmoke.setText(getResources().getString(R.string.lowerlimit));//低于下限
								} else if (yanWuAlarm == 02) {
									alarmSmoke.setText(getResources().getString(R.string.upperlimit));//高于上限
								} else if (yanWuAlarm == 04) {
									alarmSmoke.setText(getResources().getString(R.string.alarm));//告警
								} else if (yanWuAlarm == 05) {
									alarmSmoke.setText(getResources().getString(R.string.dc_bournout));//传感器断线
								}
								
								if (shuiQinAlarm == 00) {
									alarMimmersion.setText(getResources().getString(R.string.normal));//正常
								} else if (shuiQinAlarm == 01) {
									alarMimmersion.setText(getResources().getString(R.string.lowerlimit));//低于下限
								} else if (shuiQinAlarm == 02) {
									alarMimmersion.setText(getResources().getString(R.string.upperlimit));//高于上限
								} else if (shuiQinAlarm == 04) {
									alarMimmersion.setText(getResources().getString(R.string.alarm));//告警
								} else if (shuiQinAlarm == 05) {
									alarMimmersion.setText(getResources().getString(R.string.dc_bournout));//传感器断线
								}
								
								if (hongWaiAlarm == 00) {
									alarmInfrared.setText(getResources().getString(R.string.normal));//正常
								} else if (hongWaiAlarm == 01) {
									alarmInfrared.setText(getResources().getString(R.string.lowerlimit));//低于下限
								} else if (hongWaiAlarm == 02) {
									alarmInfrared.setText(getResources().getString(R.string.upperlimit));//高于上限
								} else if (hongWaiAlarm == 04) {
									alarmInfrared.setText(getResources().getString(R.string.alarm));//告警
								} else if (hongWaiAlarm == 05) {
									alarmInfrared.setText(getResources().getString(R.string.dc_bournout));//传感器断线
								}
								
								if (menChuangAlarm == 00) {
									alarmDoor.setText(getResources().getString(R.string.normal));//正常
								} else if (menChuangAlarm == 01) {
									alarmDoor.setText(getResources().getString(R.string.lowerlimit));//低于下限
								} else if (menChuangAlarm == 02) {
									alarmDoor.setText(getResources().getString(R.string.upperlimit));//高于上限
								} else if (menChuangAlarm == 04) {
									alarmDoor.setText(getResources().getString(R.string.alarm));//告警
								} else if (menChuangAlarm == 05) {
									alarmDoor.setText(getResources().getString(R.string.dc_bournout));//传感器断线
								}
								
								if (boLiAlarm == 00) {
									alarmGlass.setText(getResources().getString(R.string.normal));//正常
								} else if (boLiAlarm == 01) {
									alarmGlass.setText(getResources().getString(R.string.lowerlimit));//低于下限
								} else if (boLiAlarm == 02) {
									alarmGlass.setText(getResources().getString(R.string.upperlimit));//高于上限
								} else if (boLiAlarm == 04) {
									alarmGlass.setText(getResources().getString(R.string.alarm));//告警
								} else if (boLiAlarm == 05) {
									alarmGlass.setText(getResources().getString(R.string.dc_bournout));//传感器断线
								}
								
							}else if(sendCMD.compareTo("47") == 0){
								
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
		//writeFile("cmd_"+sb.toString());
	}
}