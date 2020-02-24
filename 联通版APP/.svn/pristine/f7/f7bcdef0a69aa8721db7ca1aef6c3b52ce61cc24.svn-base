package com.dgm.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class BatteryFragment extends Fragment {

	private Context mContext;
	private View mBaseView;
	boolean connect_status_bit = false;
	public String sendCMD = "";//上次发送指令CID2标示
	public int countNum=0;
	private Button btnbetinfo,getIcon,setIcon;
	private EditText txtbetvol1,txtbetvol2,txtbetcur1,txtbetcur2,txtbetcap1,txtbetcap2,txtbettmp1,txtbettmp2,txtbetloop1,txtbetloop2,txtbetkttmp1,txtbetkttmp2;
	private Spinner spinner;
	private DataAssembleUtil dau;
	private ArrayAdapter<String> spinnerAdapter;
	private List<String> itemlists= new ArrayList<String>();
	
	public BatteryFragment() {
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
		mBaseView = inflater.inflate(R.layout.fragment_battery,
				null);
		mContext = getActivity();
		btnbetinfo = (Button) mBaseView.findViewById(R.id.btnbetinfo);// 获取电池信息
		getIcon= (Button) mBaseView.findViewById(R.id.getIcon);// 获取电池信息
		setIcon = (Button) mBaseView.findViewById(R.id.setIcon);// 获取电池信息

		txtbetvol1 = (EditText) mBaseView.findViewById(R.id.txtbetvol1);// 第一组电池电压
		txtbetvol2 = (EditText) mBaseView.findViewById(R.id.txtbetvol2);// 第二组电池电压
		txtbetcur1 = (EditText) mBaseView.findViewById(R.id.txtbetcur1);// 第一组电池电流
		txtbetcur2 = (EditText) mBaseView.findViewById(R.id.txtbetcur2);// 第二组电池电流
		txtbetcap1 = (EditText) mBaseView.findViewById(R.id.txtbetcap1);// 第一组剩余容量
		txtbetcap2 = (EditText) mBaseView.findViewById(R.id.txtbetcap2);// 第二组剩余容量
		txtbettmp1 = (EditText) mBaseView.findViewById(R.id.txtbettmp1);// 第一组电池温度
		txtbettmp2 = (EditText) mBaseView.findViewById(R.id.txtbettmp2);// 第二组电池温度
		
		txtbetkttmp1 = (EditText) mBaseView.findViewById(R.id.txtbetkttmp1);// 第一组电池壳体温度
		txtbetkttmp2 = (EditText) mBaseView.findViewById(R.id.txtbetkttmp2);// 第二组电池壳体温度
		
		txtbetloop1 = (EditText) mBaseView.findViewById(R.id.txtbetxuhuan1);//第一组电池循环次数
		txtbetloop2 = (EditText) mBaseView.findViewById(R.id.txtbetxunhuan2);//第二组电池循环次数
		spinner=(Spinner)mBaseView.findViewById(R.id.icon_sel);//电源品牌 
        itemlists.add(getResources().getString(R.string.sdhsd));//双登和海四达
        itemlists.add(getResources().getString(R.string.nandu));//南都
		spinnerAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,itemlists);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner.setAdapter(spinnerAdapter);
		
		btnbetinfo.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("42") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x37 };//D7 42
					byte[] b = dau.AssembleReadData3(recByte, "42");
					sendCMD="42";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
					//displayData("7E323030313432303045304138303030303030303030303031303030303134303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030433030303137393138303030313535414130303031303030303535414135354141353541413535414130303031303030303030303430303030303030303030303030303030444346330D");
				}
			}
		});
		getIcon.setOnClickListener(new OnClickListener() {

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

					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 80
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

		setIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("81") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if (countNum > 2) {
						sendCMD = "";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };
					byte[] value={0x30,0x30};
					int se=spinner.getSelectedItemPosition();
					switch(se){
						case 0:
							value[1]=0x31;
							break;
						case 1:
							value[1]=0x32;
							break;
					}


					byte [] bb={0x38,0x33,value[0],value[1]};
					byte[] b = dau.sendSetCmd(recByte, "81",bb);
					sendCMD = "81";
					countNum=0;
					byte[]b10=new byte[20];
					for (int i = 0; i <2; i++) {
						for (int j = 0; j < b10.length; j++) {
							if(b.length>(i*20+j)){
								b10[j]=b[i*20+j];
							}else {
								b10[j] = 0;
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
		return mBaseView;
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
        case R.id.menu_alarmInfo: 
        	FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			BatteryFragmentAlarm umFragment = new BatteryFragmentAlarm();
			//ft.replace(R.id.fl_content, umFragment, DevicesScanFragment.TAG);
			ft.replace(R.id.fl_content2, umFragment, DevicesScanFragment.TAG);
			ft.commit();
            break; 
        } 
		return super.onOptionsItemSelected(item);  
	  }  
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(mGattUpdateReceiver);
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
			File file=new File(picDir,"file123123.txt");
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
						if(intLength==0) return;
						byte[] b2 = new byte[intLength];
						if (b.length > 12 + intLength) {
							for (int j = 0; j < intLength; j++) {
								b2[j] = b[j + 12];
							}
							if (sendCMD.compareTo("42") == 0) {
								DecimalFormat df=new DecimalFormat("0.00");
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								//bb={4,2,16,13,120,13,22,13,27,13,48,13,64,13,37,13,48,13,48,13,42,13,94,13,40,13,115,13,31,13,-2,13,-22,0,0,6,11,-74,11,-75,11,-70,11,-71,11,-48,0,0,0,0,-43,19,19,-45,3,19,-44,0,8,19,-120,16,13,120,13,22,13,27,13,48,13,64,13,37,13,48,13,48,13,42,13,94,13,40,13,115,13,31,13,-2,13,-22,0,0,6,11,-74,11,-75,11,-70,11,-71,11,-48,0,0,0,0,-43,19,19,-45,3,19,-44,0,8,19,-120};
								int n0=bb[1];//蓄电池组数
								if(n0<=0) return;
								int n1=bb[2];//第一组蓄电池数量
								//32 32 00 00 00 00 32 00 00 00 00
								//n个蓄电池单体电压位置2*(n1-1)+3
								//标识温度数量位置
								int pn2=2*(n1-1)+5;
								int n2=DataAssembleUtil.parseUnsignedInt(bb[pn2]);//标识温度数量
								//n个标识温度位置
								int pt=2*(n2-1)+pn2+1;
								//蓄电池充防电位置
								int cfdpt=pt+2;
							
								int cfd = DataAssembleUtil.parseInt(bb[cfdpt+1], bb[cfdpt]);// 充放电电流
								String a=df.format((float)cfd/100);
								txtbetcur1.setText(a);//电池电流1
							
								if(n2>0){
									cfd=DataAssembleUtil.parseInt(bb[pn2+2], bb[pn2+1]);
									a=df.format((float)cfd/10);
									txtbettmp1.setText(a);// 电池1温度
									
									cfd=DataAssembleUtil.parseInt(bb[pn2+12], bb[pn2+11]);
									a=df.format((float)cfd/10);
									txtbetkttmp1.setText(a);// 电池6温度(电池壳体温度)
								}
							
								cfd=DataAssembleUtil.parseUnsignedInt(bb[cfdpt+3], bb[cfdpt+2]);// 蓄电池总电压
								a=df.format((float)cfd/100);
								txtbetvol1.setText(a);
							
								/*NumberFormat numberFormat = NumberFormat.getInstance();  
								numberFormat.setMaximumFractionDigits(0);*/  
								DecimalFormat f=new DecimalFormat("0");
								if(DataAssembleUtil.parseInt(bb[cfdpt+8], bb[cfdpt+7])!=0){
									txtbetcap1.setText(f.format((float) (DataAssembleUtil.parseInt(bb[cfdpt+5], bb[cfdpt+4])) 
											/ (float) (DataAssembleUtil.parseInt(bb[cfdpt+8], bb[cfdpt+7])) * 100));	//剩余容量百分比
								}
							
								cfd=DataAssembleUtil.parseInt(bb[cfdpt+10], bb[cfdpt+9]);
								txtbetloop1.setText(String.valueOf(cfd));//循环次数
							
								if(n0>1){
									cfdpt=cfdpt+13;
								
									n1=bb[cfdpt];//第二组蓄电池数量
									pn2=2*n1+cfdpt+1;
									n2=DataAssembleUtil.parseUnsignedInt(bb[pn2]);//标识温度数量
									//n个标识温度位置
									pt=2*(n2-1)+pn2;
									//蓄电池充防电位置
									cfdpt=pt+3;
								
									cfd = DataAssembleUtil.parseInt(bb[cfdpt+1], bb[cfdpt]);// 充放电电流
									a=df.format((float)cfd/100);
									txtbetcur2.setText(a);//电池电流1
									if(n2>0){
										cfd=DataAssembleUtil.parseInt(bb[pn2+2], bb[pn2+1]);
										a=df.format((float)cfd/10);
										txtbettmp2.setText(a);// 电池1温度
										
										cfd=DataAssembleUtil.parseInt(bb[pn2+12], bb[pn2+11]);
										a=df.format((float)cfd/10);
										txtbetkttmp2.setText(a);// 电池6温度(电池壳体温度)
									}
								
									cfd=DataAssembleUtil.parseUnsignedInt(bb[cfdpt+3], bb[cfdpt+2]);// 蓄电池总电压
									a=df.format((float)cfd/100);
									txtbetvol2.setText(a);
								
									/*numberFormat = NumberFormat.getInstance();  
									numberFormat.setMaximumFractionDigits(2);*/  

									if(DataAssembleUtil.parseInt(bb[cfdpt+8], bb[cfdpt+7])!=0){
										txtbetcap2.setText(f.format((float) (DataAssembleUtil.parseInt(bb[cfdpt+5], bb[cfdpt+4])) 
												/ (float) (DataAssembleUtil.parseInt(bb[cfdpt+8], bb[cfdpt+7])) * 100));	//剩余容量百分比
									}
								
									cfd=DataAssembleUtil.parseInt(bb[cfdpt+10], bb[cfdpt+9]);
									txtbetloop2.setText(String.valueOf(cfd));//循环次数
								}
							
							}else if(sendCMD.compareTo("80") == 0) {
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
							
								switch(bb[22]){
								case 0x01:
									spinner.setSelection(0);
									break;
								case 0x02:
									spinner.setSelection(1);
									break;
								}
								
								sendCMD = "";
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
