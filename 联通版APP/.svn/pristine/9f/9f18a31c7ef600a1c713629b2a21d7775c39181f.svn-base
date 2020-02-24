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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

public class MicroStationCUCCFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	
	boolean connect_status_bit = false;
	private DataAssembleUtil dau;
	public String sendCMD = "";// �ϴη���ָ��CID2��ʾ
	//��ͨ5G��
	private TextView zcjip1,dkh1,zcjip2,dkh2,vpn1,vpnuser1,vpnpass1,vpn2,
	vpnuser2,vpnpass2,fsuip,fsuid,dpcid,apn,apnyhm,apnmm,xykip,xykdk,zcjip3,
	dkh3,vpn3,vpnuser3,vpnpass3,fsuvpnip,gwip,swmkxh,mac,sjch,simkxhqd;
	private Spinner simkyys,wlzs,ptjrzt,sgbljzt,wlms;
	private List<String> wlmslists=new ArrayList<String>();
	private List<String> sgbljztlists=new ArrayList<String>();
	private List<String> ptjrztlists=new ArrayList<String>();
	private List<String> wlzslists=new ArrayList<String>();
	private List<String> simkyyslists=new ArrayList<String>();
	private ArrayAdapter<String> wlmsAdapter;
	private ArrayAdapter<String> sgbljztAdapter;
	private ArrayAdapter<String> ptjrztAdapter;
	private ArrayAdapter<String> wlzsAdapter;
	private ArrayAdapter<String> simkyysAdapter;
	private Button btnzcjip1,btndkh1,btnzcjip2,btndkh2,btnvpn1,btnvpnuser1,
	btnvpnpass1,btnvpn2,btnvpnuser2,btnvpnpass2,btnfsuip,btnfsuid,btndpcid,
	btnwlms,btnapn,btnapnyhm,btnapnmm,btnxykip,btnxykdk,btnzcjip3,btndkh3,
	btnvpn3,btnvpnuser3,btnvpnpass3,btnfsuvpnip,btngwip,btnswmkxh,btnmac,
	btnsjch,btnsimkyys,btnsimkxhqd,btnwlzs,btnpjrzt,btnsgbljzt,fivegsys;
	
	//����
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
		mBaseView = inflater.inflate(R.layout.micro_station_cucc_fragment, null);
		mContext = getActivity();
		dau = new DataAssembleUtil();		
		init5G();
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
				//2019��9��24��ɾ��������ʽ
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

	// 14λ IDУ�鷽��
	public static boolean isID(String str) {
		boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
		boolean isLength = false;
		int strLenth = str.length();
		if (strLenth == 14) {
			isLength = true;
		}
		return isInt && isLength;
	}
	
	// 16λ �û���У�鷽��
	public static boolean isUserAndPass(String str) {
		boolean isInt = Pattern.compile("^-?[0-9]\\d*$").matcher(str).find();
		boolean isLength = false;
		int strLenth = str.length();
		if (strLenth == 16) {
			isLength = true;
		}
		return isInt && isLength;
	}

	// PORT�˿�У�鷽��
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
	 * �յ�������ݽ���
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
					byte[] b = dau.hex2byte(cmd);// ȥ��7E,0D��תbyte����
					boolean ifTrue = dau.ifCheckSum(cmd);// �жϽ��յ����У��λ��������ݼ���õ���У��λ�Ƿ�һ��
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
							
							if (sendCMD.compareTo("88") == 0) {//
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int a1,a2,a3,a4,a5;
								//ip1
								a1=dau.parseUnsignedInt(bb[0]);//bb12 bb13
								a2=dau.parseUnsignedInt(bb[1]);//bb14 bb15
								a3=dau.parseUnsignedInt(bb[2]);
								a4=dau.parseUnsignedInt(bb[3]);
								a5=dau.parseUnsignedInt(bb[5], bb[4]);
//								int PortB = ((bb[11]) & 0xFF | (bb[10] & 0xFF) << 8);// �˿�
//								
//								updateIPA1.setText("" + IPA1);
//								updateIPA2.setText("" + IPA2);
//								updateIPA3.setText("" + IPA3);
//								updateIPA4.setText("" + IPA4);
//								updatePortA.setText("" + PortA);
//								updateIPB1.setText("" + IPB1);
//								updateIPB2.setText("" + IPB2);
//								updateIPB3.setText("" + IPB3);
//								updateIPB4.setText("" + IPB4);
//								updatePortB.setText("" + PortB);
								zcjip1.setText(a1+"."+a2+"."+a3+"."+a4);
								//�˿�1
								dkh1.setText(""+a5);
								//ip2
								a1=dau.parseUnsignedInt(bb[6]);
								a2=dau.parseUnsignedInt(bb[7]);
								a3=dau.parseUnsignedInt(bb[8]);
								a4=dau.parseUnsignedInt(bb[9]);
								a5=dau.parseUnsignedInt(bb[11], bb[10]);
								zcjip2.setText(a1+"."+a2+"."+a3+"."+a4);
								//�˿�2
								dkh2.setText(""+a5);
								//vpn 1
								a1=dau.parseUnsignedInt(bb[12]);
								a2=dau.parseUnsignedInt(bb[13]);
								a3=dau.parseUnsignedInt(bb[14]);
								a4=dau.parseUnsignedInt(bb[15]);
								vpn1.setText(a1+"."+a2+"."+a3+"."+a4);
								//vpnuser1
								/*String u=""+dau.parseUnsignedInt(bb[17],bb[16])+dau.parseUnsignedInt(bb[19],bb[18])
										+dau.parseUnsignedInt(bb[21],bb[20])+dau.parseUnsignedInt(bb[23],bb[22])
										+dau.parseUnsignedInt(bb[25],bb[24])+dau.parseUnsignedInt(bb[27],bb[26])
										+dau.parseUnsignedInt(bb[29],bb[28])+dau.parseUnsignedInt(bb[31],bb[30])
										+dau.parseUnsignedInt(bb[33],bb[32])+dau.parseUnsignedInt(bb[35],bb[34]);*/
								int startb=16;
								int tmpstartb=startb*2+12;
								String u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								vpnuser1.setText(u.replaceAll("^(0+)", ""));
								//vpnpass1
								/*String p=""+dau.parseUnsignedInt(bb[37],bb[36])+dau.parseUnsignedInt(bb[39],bb[38])
										+dau.parseUnsignedInt(bb[41],bb[40])+dau.parseUnsignedInt(bb[43],bb[42])
										+dau.parseUnsignedInt(bb[45],bb[44])+dau.parseUnsignedInt(bb[47],bb[46])
										+dau.parseUnsignedInt(bb[49],bb[48])+dau.parseUnsignedInt(bb[51],bb[50])
										+dau.parseUnsignedInt(bb[53],bb[52])+dau.parseUnsignedInt(bb[55],bb[54]);*/
								startb=36;
								tmpstartb=startb*2+12;
								String p=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								vpnpass1.setText(p.replaceAll("^(0+)", ""));
								//vpn 2
								a1=dau.parseUnsignedInt(bb[56]);
								a2=dau.parseUnsignedInt(bb[57]);
								a3=dau.parseUnsignedInt(bb[58]);
								a4=dau.parseUnsignedInt(bb[59]);
								vpn2.setText(a1+"."+a2+"."+a3+"."+a4);
								//vpnuser2
								/*u=""+dau.parseUnsignedInt(bb[61],bb[60])+dau.parseUnsignedInt(bb[63],bb[62])
										+dau.parseUnsignedInt(bb[65],bb[64])+dau.parseUnsignedInt(bb[67],bb[66])
										+dau.parseUnsignedInt(bb[69],bb[68])+dau.parseUnsignedInt(bb[71],bb[70])
										+dau.parseUnsignedInt(bb[73],bb[72])+dau.parseUnsignedInt(bb[75],bb[74])
										+dau.parseUnsignedInt(bb[77],bb[76])+dau.parseUnsignedInt(bb[79],bb[78]);*/
								startb=60;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								vpnuser2.setText(u.replaceAll("^(0+)", ""));
								//vpnpass2
								/*p=""+dau.parseUnsignedInt(bb[81],bb[80])+dau.parseUnsignedInt(bb[83],bb[82])
										+dau.parseUnsignedInt(bb[85],bb[84])+dau.parseUnsignedInt(bb[87],bb[86])
										+dau.parseUnsignedInt(bb[89],bb[88])+dau.parseUnsignedInt(bb[91],bb[90])
										+dau.parseUnsignedInt(bb[93],bb[92])+dau.parseUnsignedInt(bb[95],bb[94])
										+dau.parseUnsignedInt(bb[97],bb[96])+dau.parseUnsignedInt(bb[99],bb[98]);*/
								startb=80;
								tmpstartb=startb*2+12;
								p=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								vpnpass2.setText(p.replaceAll("^(0+)", ""));
								//fsu ip
								a1=dau.parseUnsignedInt(bb[100]);
								a2=dau.parseUnsignedInt(bb[101]);
								a3=dau.parseUnsignedInt(bb[102]);
								a4=dau.parseUnsignedInt(bb[103]);
								fsuip.setText(a1+"."+a2+"."+a3+"."+a4);
								//fsu id
								/*u=""+dau.parseUnsignedInt(bb[105],bb[104])+dau.parseUnsignedInt(bb[107],bb[106])
										+dau.parseUnsignedInt(bb[109],bb[108])+dau.parseUnsignedInt(bb[111],bb[110])
										+dau.parseUnsignedInt(bb[113],bb[112])+dau.parseUnsignedInt(bb[115],bb[114])
										+dau.parseUnsignedInt(bb[117],bb[116]);*/
								 u=""+dau.parseUnsignedInt(bb[104])+dau.parseUnsignedInt(bb[105])
											+dau.parseUnsignedInt(bb[106])+dau.parseUnsignedInt(bb[107])
											+dau.parseUnsignedInt(bb[108])+dau.parseUnsignedInt(bb[109])
											+dau.parseUnsignedInt(bb[110])+dau.parseUnsignedInt(bb[111])
											+dau.parseUnsignedInt(bb[112])+dau.parseUnsignedInt(bb[113])
											+dau.parseUnsignedInt(bb[114])+dau.parseUnsignedInt(bb[115])
											+dau.parseUnsignedInt(bb[116])+dau.parseUnsignedInt(bb[117]);
								fsuid.setText(u.replaceAll("^(0+)", ""));
								//dpc id
								/*u=""+dau.parseUnsignedInt(bb[119],bb[118])+dau.parseUnsignedInt(bb[121],bb[120])
										+dau.parseUnsignedInt(bb[123],bb[122])+dau.parseUnsignedInt(bb[125],bb[124])
										+dau.parseUnsignedInt(bb[127],bb[126])+dau.parseUnsignedInt(bb[129],bb[128])
										+dau.parseUnsignedInt(bb[131],bb[130]);*/
								startb=118;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								dpcid.setText(u.replaceAll("^(0+)", ""));
								//apn/vpn
								a1=dau.parseUnsignedInt(bb[132]);
								if(a1==10){
									wlms.setSelection(1);
								}else if(a1==11){
									wlms.setSelection(2);
								}else{
									wlms.setSelection(0);
								}
								//apn
								/*u=""+dau.parseUnsignedInt(bb[134],bb[133])+dau.parseUnsignedInt(bb[136],bb[135])
										+dau.parseUnsignedInt(bb[138],bb[137])+dau.parseUnsignedInt(bb[140],bb[139])
										+dau.parseUnsignedInt(bb[142],bb[141])+dau.parseUnsignedInt(bb[144],bb[143])
										+dau.parseUnsignedInt(bb[146],bb[145])+dau.parseUnsignedInt(bb[148],bb[147])
										+dau.parseUnsignedInt(bb[150],bb[149])+dau.parseUnsignedInt(bb[152],bb[151]);*/
								startb=133;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								apnyhm.setText(u.replaceAll("^(0+)", ""));
								apn.setText(a1+"."+a2+"."+a3+"."+a4);
								//apn���
								/*u=""+dau.parseUnsignedInt(bb[154],bb[153])+dau.parseUnsignedInt(bb[156],bb[155])
										+dau.parseUnsignedInt(bb[158],bb[157])+dau.parseUnsignedInt(bb[160],bb[159])
										+dau.parseUnsignedInt(bb[162],bb[161])+dau.parseUnsignedInt(bb[164],bb[163])
										+dau.parseUnsignedInt(bb[166],bb[165])+dau.parseUnsignedInt(bb[168],bb[167])
										+dau.parseUnsignedInt(bb[170],bb[169])+dau.parseUnsignedInt(bb[172],bb[171]);*/
								startb=153;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								apnyhm.setText(u.replaceAll("^(0+)", ""));
								//apn����
								/*u=""+dau.parseUnsignedInt(bb[174],bb[173])+dau.parseUnsignedInt(bb[176],bb[175])
										+dau.parseUnsignedInt(bb[178],bb[177])+dau.parseUnsignedInt(bb[180],bb[179])
										+dau.parseUnsignedInt(bb[182],bb[181])+dau.parseUnsignedInt(bb[184],bb[183])
										+dau.parseUnsignedInt(bb[186],bb[185])+dau.parseUnsignedInt(bb[188],bb[187])
										+dau.parseUnsignedInt(bb[190],bb[189])+dau.parseUnsignedInt(bb[192],bb[191]);*/
								startb=173;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								apnmm.setText(u.replaceAll("^(0+)", ""));
								//Э���ip
								a1=dau.parseUnsignedInt(bb[193]);
								a2=dau.parseUnsignedInt(bb[194]);
								a3=dau.parseUnsignedInt(bb[195]);
								a4=dau.parseUnsignedInt(bb[196]);
								a5=dau.parseUnsignedInt(bb[198], bb[197]);
								xykip.setText(a1+"."+a2+"."+a3+"."+a4);
								//Э���˿�
								xykdk.setText(""+a5);
								//ip3
								a1=dau.parseUnsignedInt(bb[199]);
								a2=dau.parseUnsignedInt(bb[200]);
								a3=dau.parseUnsignedInt(bb[201]);
								a4=dau.parseUnsignedInt(bb[202]);
								a5=dau.parseUnsignedInt(bb[204], bb[203]);
								zcjip3.setText(a1+"."+a2+"."+a3+"."+a4);
								//�˿�3
								dkh3.setText(""+a5);
								//vpn 3
								a1=dau.parseUnsignedInt(bb[205]);
								a2=dau.parseUnsignedInt(bb[206]);
								a3=dau.parseUnsignedInt(bb[207]);
								a4=dau.parseUnsignedInt(bb[208]);
								vpn3.setText(a1+"."+a2+"."+a3+"."+a4);
								//vpnuser3
								/*u=""+dau.parseUnsignedInt(bb[210],bb[209])+dau.parseUnsignedInt(bb[212],bb[211])
										+dau.parseUnsignedInt(bb[214],bb[213])+dau.parseUnsignedInt(bb[216],bb[215])
										+dau.parseUnsignedInt(bb[218],bb[217])+dau.parseUnsignedInt(bb[220],bb[219])
										+dau.parseUnsignedInt(bb[222],bb[221])+dau.parseUnsignedInt(bb[224],bb[223])
										+dau.parseUnsignedInt(bb[226],bb[225])+dau.parseUnsignedInt(bb[228],bb[227]);*/
								startb=209;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								vpnuser3.setText(u.replaceAll("^(0+)", ""));
								//vpnpass3
								/*p=""+dau.parseUnsignedInt(bb[230],bb[229])+dau.parseUnsignedInt(bb[232],bb[231])
										+dau.parseUnsignedInt(bb[234],bb[233])+dau.parseUnsignedInt(bb[236],bb[235])
										+dau.parseUnsignedInt(bb[238],bb[237])+dau.parseUnsignedInt(bb[240],bb[239])
										+dau.parseUnsignedInt(bb[242],bb[241])+dau.parseUnsignedInt(bb[244],bb[243])
										+dau.parseUnsignedInt(bb[246],bb[245])+dau.parseUnsignedInt(bb[248],bb[247]);*/
								startb=229;
								tmpstartb=startb*2+12;
								p=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								vpnpass3.setText(p.replaceAll("^(0+)", ""));
								//fsp-vpnip
								a1=dau.parseUnsignedInt(bb[249]);
								a2=dau.parseUnsignedInt(bb[250]);
								a3=dau.parseUnsignedInt(bb[251]);
								a4=dau.parseUnsignedInt(bb[252]);
								fsuvpnip.setText(a1+"."+a2+"."+a3+"."+a4);
								//����ip
								a1=dau.parseUnsignedInt(bb[253]);
								a2=dau.parseUnsignedInt(bb[254]);
								a3=dau.parseUnsignedInt(bb[255]);
								a4=dau.parseUnsignedInt(bb[256]);
								gwip.setText(a1+"."+a2+"."+a3+"."+a4);
								//����ģ���ͺ�
//								u=""+dau.parseUnsignedInt(bb[257])+dau.parseUnsignedInt(bb[258])
//										+dau.parseUnsignedInt(bb[259])+dau.parseUnsignedInt(bb[260])
//										+dau.parseUnsignedInt(bb[261])+dau.parseUnsignedInt(bb[262])
//										+dau.parseUnsignedInt(bb[263])+dau.parseUnsignedInt(bb[264])
//										+dau.parseUnsignedInt(bb[265])+dau.parseUnsignedInt(bb[266])
//										
//										+dau.parseUnsignedInt(bb[267])+dau.parseUnsignedInt(bb[268])
//										+dau.parseUnsignedInt(bb[269])+dau.parseUnsignedInt(bb[270])
//										+dau.parseUnsignedInt(bb[271])+dau.parseUnsignedInt(bb[272])
//										+dau.parseUnsignedInt(bb[273])
//										+dau.parseUnsignedInt(bb[274])+dau.parseUnsignedInt(bb[275])
//										+dau.parseUnsignedInt(bb[276])+dau.parseUnsignedInt(bb[277])
//										+dau.parseUnsignedInt(bb[278])+dau.parseUnsignedInt(bb[279])
//										+dau.parseUnsignedInt(bb[280])+dau.parseUnsignedInt(bb[281])
//										
//										+dau.parseUnsignedInt(bb[282])+dau.parseUnsignedInt(bb[283])
//										+dau.parseUnsignedInt(bb[284])+dau.parseUnsignedInt(bb[285])
//										+dau.parseUnsignedInt(bb[286]);
								startb=257;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								swmkxh.setText(u.replaceAll("^(0+)", ""));
								//mac
								startb=287;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								mac.setText(u.replaceAll("^(0+)", ""));
								//sim������
//								u=""+dau.parseUnsignedInt(bb[307])+dau.parseUnsignedInt(bb[308])
//										+dau.parseUnsignedInt(bb[309])+dau.parseUnsignedInt(bb[310])
//										+dau.parseUnsignedInt(bb[311])+dau.parseUnsignedInt(bb[312])
//										+dau.parseUnsignedInt(bb[313])+dau.parseUnsignedInt(bb[314])
//										+dau.parseUnsignedInt(bb[315])+dau.parseUnsignedInt(bb[316])
//										+dau.parseUnsignedInt(bb[317])+dau.parseUnsignedInt(bb[318])
//										+dau.parseUnsignedInt(bb[319])+dau.parseUnsignedInt(bb[320])
//										+dau.parseUnsignedInt(bb[321])+dau.parseUnsignedInt(bb[322])
//										+dau.parseUnsignedInt(bb[323])+dau.parseUnsignedInt(bb[324])
//										+dau.parseUnsignedInt(bb[325])+dau.parseUnsignedInt(bb[326]);
								startb=307;
								tmpstartb=startb*2+12;
								u=""+(char)dau.parseUnsignedInt(b[tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb])
										+(char)dau.parseUnsignedInt(b[++tmpstartb])+(char)dau.parseUnsignedInt(b[++tmpstartb]);
								sjch.setText(u.replaceAll("^(0+)", ""));
								//��Ӫ��
								a1=dau.parseUnsignedInt(bb[327]);
								if(a1==1){
									simkyys.setSelection(1);
								}else if(a1==2){
									simkyys.setSelection(2);
								}else if(a1==3){
									simkyys.setSelection(3);
								}else {
									simkyys.setSelection(4);
								}
								//�ź�ǿ��
								a1=dau.parseInt(bb[328]);
								simkxhqd.setText(""+a1);
								//������ʽ
								a1=dau.parseUnsignedInt(bb[329]);
								if(a1==2){
									wlzs.setSelection(1);
								}else if(a1==3){
									wlzs.setSelection(2);
								}else if(a1==4){
									wlzs.setSelection(3);
								}else if(a1==5){
									wlzs.setSelection(4);
								}else{
									wlzs.setSelection(5);
								}
								//ƽ̨����״̬
								a1=dau.parseUnsignedInt(bb[330]);
								if(a1==1){
									ptjrzt.setSelection(1);
								}else{
									ptjrzt.setSelection(2);
								}
								//4G��״̬
								a1=dau.parseUnsignedInt(bb[331]);
								if(a1==1){
									sgbljzt.setSelection(1);
								}else{
									sgbljzt.setSelection(2);
								}
								///////////////ooooooooooooooooooooooo//////////////
								recString = "";
								sendCMD = "";
								//setD.setText("");
							}
							
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
	
	private void init5G(){
		wlms = (Spinner) mBaseView.findViewById(R.id.wlms);	//����ģʽ
		wlmslists.add("");
		wlmslists.add("APN");
		wlmslists.add("VPN");
		wlmsAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,wlmslists);
		wlmsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		wlms.setAdapter(wlmsAdapter);

		simkyys = (Spinner) mBaseView.findViewById(R.id.simkyys);	//SIM����Ӫ��
		simkyyslists.add("");
		simkyyslists.add(getResources().getString(R.string.cmcc));//�ƶ�
		simkyyslists.add(getResources().getString(R.string.cucc));//��ͨ
		simkyyslists.add(getResources().getString(R.string.ctcc));//����
		simkyyslists.add(getResources().getString(R.string.ad_unknow));//δ֪
		simkyysAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,simkyyslists);
		simkyysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		simkyys.setAdapter(simkyysAdapter);
		
		wlzs = (Spinner) mBaseView.findViewById(R.id.wlzs);	//������ʽ
		wlzslists.add("");
		wlzslists.add("2G");
		wlzslists.add("3G");
		wlzslists.add("4G");
		wlzslists.add("5G");
		wlzslists.add(getResources().getString(R.string.ad_unknow));//δ֪
		wlzsAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,wlzslists);
		wlzsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		wlzs.setAdapter(wlzsAdapter);
		
		ptjrzt = (Spinner) mBaseView.findViewById(R.id.ptjrzt);	//ƽ̨����״̬
		ptjrztlists.add("");//ע��
		ptjrztlists.add(getResources().getString(R.string.register));//ע��
		ptjrztlists.add(getResources().getString(R.string.noregister));//δע��
		ptjrztAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,ptjrztlists);
		ptjrztAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		ptjrzt.setAdapter(ptjrztAdapter);
		
		sgbljzt = (Spinner) mBaseView.findViewById(R.id.sgbljzt);	//4G������״̬
		sgbljztlists.add("");
		sgbljztlists.add(getResources().getString(R.string.normal));//��
		sgbljztlists.add(getResources().getString(R.string.disconnected));//�Ͽ�����
		sgbljztAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,sgbljztlists);
		sgbljztAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		sgbljzt.setAdapter(sgbljztAdapter);
		
		
		zcjip1 = (EditText) mBaseView.findViewById(R.id.zcjip1);//ע���IP1	
		dkh1 = (EditText) mBaseView.findViewById(R.id.dkh1);	//ע���˿ں�1
		zcjip2 = (EditText) mBaseView.findViewById(R.id.zcjip2);//ע���IP2
		dkh2 = (EditText) mBaseView.findViewById(R.id.dkh2);	//ע���˿�2
		vpn1 = (EditText) mBaseView.findViewById(R.id.vpn1);	//vpnIP1
		vpnuser1 = (EditText) mBaseView.findViewById(R.id.vpnuser1);//vpn�û���1
		vpnpass1 = (EditText) mBaseView.findViewById(R.id.vpnpass1);//vpn����1
		vpn2 = (EditText) mBaseView.findViewById(R.id.vpn2);	//vpnIP2
		vpnuser2 = (EditText) mBaseView.findViewById(R.id.vpnuser2);//vpn�û���2
		vpnpass2 = (EditText) mBaseView.findViewById(R.id.vpnpass2);//VPN����2
		fsuip = (EditText) mBaseView.findViewById(R.id.fsuip);	//fsuip
		fsuid = (EditText) mBaseView.findViewById(R.id.fsuid);	//fsuid
		dpcid = (EditText) mBaseView.findViewById(R.id.dpcid);	//dpcid
		apn = (EditText) mBaseView.findViewById(R.id.apn);	//apn���
		apnyhm = (EditText) mBaseView.findViewById(R.id.apnyhm);//apn�û���
		apnmm = (EditText) mBaseView.findViewById(R.id.apnmm);	//apn����
		xykip = (EditText) mBaseView.findViewById(R.id.xykip);	//fsuЭ���IP
		xykdk = (EditText) mBaseView.findViewById(R.id.xykdk);	//fsuЭ���˿�
		zcjip3 = (EditText) mBaseView.findViewById(R.id.zcjip3);//ע���IP3
		dkh3 = (EditText) mBaseView.findViewById(R.id.dkh3);	//ע���˿ں�3
		vpn3 = (EditText) mBaseView.findViewById(R.id.vpn3);	//vpnIP3
		vpnuser3 = (EditText) mBaseView.findViewById(R.id.vpnuser3);//vpn�û���3
		vpnpass3 = (EditText) mBaseView.findViewById(R.id.vpnpass3);//vpn����3
		fsuvpnip = (EditText) mBaseView.findViewById(R.id.fsuvpnip);//fsu_IP vpn_IP
		gwip = (EditText) mBaseView.findViewById(R.id.gwip);	//fsu����IP
		swmkxh = (EditText) mBaseView.findViewById(R.id.swmkxh);//����ģ���ͺ�
		mac = (EditText) mBaseView.findViewById(R.id.mac);	//mac
		sjch = (EditText) mBaseView.findViewById(R.id.sjch);	//�ֻ���
		simkxhqd = (EditText) mBaseView.findViewById(R.id.simkxhqd);	//sim���ź�ǿ��

	
		btnzcjip1 = (Button) mBaseView.findViewById(R.id.btnzcjip1);	//����ע���IP1��ַ
		btndkh1 = (Button) mBaseView.findViewById(R.id.btndkh1);	//����ע���˿ں�1
		btnzcjip2 = (Button) mBaseView.findViewById(R.id.btnzcjip2);	//����ע���IP2��ַ
		btndkh2 = (Button) mBaseView.findViewById(R.id.btndkh2);	//����ע���˿ں�2
		btnvpn1 = (Button) mBaseView.findViewById(R.id.btnvpn1);	//����VPN1Ip��ַ1
		btnvpnuser1 = (Button) mBaseView.findViewById(R.id.btnvpnuser1);	//����VPN�û���1
		btnvpnpass1 = (Button) mBaseView.findViewById(R.id.btnvpnpass1);	//����VPN����1
		btnvpn2 = (Button) mBaseView.findViewById(R.id.btnvpn2);	//����VPNIP��ַ2
		btnvpnuser2 = (Button) mBaseView.findViewById(R.id.btnvpnuser2);	//����VPN�û���2
		btnvpnpass2 = (Button) mBaseView.findViewById(R.id.btnvpnpass2);	//����VPN����2
		btnfsuip = (Button) mBaseView.findViewById(R.id.btnfsuip);	//����FSUIP
		btnfsuid = (Button) mBaseView.findViewById(R.id.btnfsuid);	//����FSUID
		btndpcid = (Button) mBaseView.findViewById(R.id.btndpcid);	//����DPCID
		btnwlms = (Button) mBaseView.findViewById(R.id.btnwlms);	//��������ģʽ
		btnapn = (Button) mBaseView.findViewById(R.id.btnapn);	//����APN���
		btnapnyhm = (Button) mBaseView.findViewById(R.id.btnapnyhm);	//����APN�û���
		btnapnmm = (Button) mBaseView.findViewById(R.id.btnapnmm);	//����APN����
		btnxykip = (Button) mBaseView.findViewById(R.id.btnxykip);	//����FSUЭ������IP
		btnxykdk = (Button) mBaseView.findViewById(R.id.btnxykdk);	//����FSUЭ�����¶˿�
		btnzcjip3 = (Button) mBaseView.findViewById(R.id.btnzcjip3);	//����ע���IP3
		btndkh3 = (Button) mBaseView.findViewById(R.id.btndkh3);	//����ע���˿ں�3
		btnvpn3 = (Button) mBaseView.findViewById(R.id.btnvpn3);	//����VPNIP��ַ3
		btnvpnuser3 = (Button) mBaseView.findViewById(R.id.btnvpnuser3);	//����VPN�û���3
		btnvpnpass3 = (Button) mBaseView.findViewById(R.id.btnvpnpass3);	//����VPN����3
		btnfsuvpnip = (Button) mBaseView.findViewById(R.id.btnfsuvpnip);	//����FSUIP_VPNIP
		btngwip = (Button) mBaseView.findViewById(R.id.btngwip);	//����FSU����
		btnswmkxh = (Button) mBaseView.findViewById(R.id.btnswmkxh);	//��������ģ���ͺ�
		btnmac = (Button) mBaseView.findViewById(R.id.btnmac);	//����MAC
		btnsjch = (Button) mBaseView.findViewById(R.id.btnsjch);	//����sim������
		btnsimkyys = (Button) mBaseView.findViewById(R.id.btnsimkyys);	//����sim����Ӫ��
		btnsimkxhqd = (Button) mBaseView.findViewById(R.id.btnsimkxhqd);	//����sim���ź�ǿ��
		btnwlzs = (Button) mBaseView.findViewById(R.id.btnwlzs);	//������ַģʽ
		btnpjrzt = (Button) mBaseView.findViewById(R.id.btnptjrzt);	//����ƽ̨����״̬
		btnsgbljzt = (Button) mBaseView.findViewById(R.id.btnsgbljzt);	//����4G������״̬
		fivegsys = (Button) mBaseView.findViewById(R.id.fivegsys);	//��ȡ����
		
		btnzcjip1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String ip=zcjip1.getText().toString();
					if(!DataAssembleUtil.verifyIP(ip)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						String[] addr=ip.split("\\.");
						int a1,a2,a3,a4;
						byte[] b1,b2,b3,b4;
						a1=Integer.valueOf(addr[0]);
						a2=Integer.valueOf(addr[1]);
						a3=Integer.valueOf(addr[2]);
						a4=Integer.valueOf(addr[3]);
						b1=dau.intToAscByte2(a1);
						b2=dau.intToAscByte2(a2);
						b3=dau.intToAscByte2(a3);
						b4=dau.intToAscByte2(a4);
						byte[] b = { 0x38, 0x30, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//80H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btndkh1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String port=dkh1.getText().toString();
					if(!isPORT(port)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast2),//������Ϸ���ֵ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						int a=Integer.valueOf(port);
						byte[] b1=dau.intToAscByte4(a);
						byte[] b = { 0x38, 0x31, b1[0],b1[1],b1[2],b1[3]};		//81H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btnzcjip2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String ip=zcjip2.getText().toString();
					if(!DataAssembleUtil.verifyIP(ip)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						String[] addr=ip.split("\\.");
						int a1,a2,a3,a4;
						byte[] b1,b2,b3,b4;
						a1=Integer.valueOf(addr[0]);
						a2=Integer.valueOf(addr[1]);
						a3=Integer.valueOf(addr[2]);
						a4=Integer.valueOf(addr[3]);
						b1=dau.intToAscByte2(a1);
						b2=dau.intToAscByte2(a2);
						b3=dau.intToAscByte2(a3);
						b4=dau.intToAscByte2(a4);
						byte[] b = { 0x38, 0x32, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//82H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btndkh2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String port=dkh2.getText().toString();
					if(!isPORT(port)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast2),//������Ϸ���ֵ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						int a=Integer.valueOf(port);
						byte[] b1=dau.intToAscByte4(a);
						byte[] b = { 0x38, 0x33, b1[0],b1[1],b1[2],b1[3]};		//83H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btnvpn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String ip=vpn1.getText().toString();
					if(!DataAssembleUtil.verifyIP(ip)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						String[] addr=ip.split("\\.");
						int a1,a2,a3,a4;
						byte[] b1,b2,b3,b4;
						a1=Integer.valueOf(addr[0]);
						a2=Integer.valueOf(addr[1]);
						a3=Integer.valueOf(addr[2]);
						a4=Integer.valueOf(addr[3]);
						b1=dau.intToAscByte2(a1);
						b2=dau.intToAscByte2(a2);
						b3=dau.intToAscByte2(a3);
						b4=dau.intToAscByte2(a4);
						byte[] b = { 0x38, 0x34, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//84H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btnvpnuser1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(vpnuser1.getText().length()<0){
						return;
					}
					String p=vpnuser1.getText().toString();
					StringBuilder tmp=new StringBuilder();;
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x38;
					b[1]=0x35;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//85H
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//sendCommand(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnvpnpass1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(vpnpass1.getText().length()<0){
						return;
					}
					String p=vpnpass1.getText().toString();
					StringBuilder tmp=new StringBuilder();;
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x38;
					b[1]=0x36;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//86H
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnvpn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String ip=vpn2.getText().toString();
					if(!DataAssembleUtil.verifyIP(ip)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						String[] addr=ip.split("\\.");
						int a1,a2,a3,a4;
						byte[] b1,b2,b3,b4;
						a1=Integer.valueOf(addr[0]);
						a2=Integer.valueOf(addr[1]);
						a3=Integer.valueOf(addr[2]);
						a4=Integer.valueOf(addr[3]);
						b1=dau.intToAscByte2(a1);
						b2=dau.intToAscByte2(a2);
						b3=dau.intToAscByte2(a3);
						b4=dau.intToAscByte2(a4);
						byte[] b = { 0x38, 0x37, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//87H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btnvpnuser2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(vpnuser2.getText().length()<0){
						return;
					}
					String p=vpnuser2.getText().toString();
					StringBuilder tmp=new StringBuilder();;
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x38;
					b[1]=0x38;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//88H
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnvpnpass2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(vpnpass2.getText().length()<0){
						return;
					}
					String p=vpnpass2.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x38;
					b[1]=0x39;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//89H
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnfsuip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
								1000).show();// toast��ʾ1000ms
						countNum++;
						if(countNum>2){
							sendCMD="";
						}
					} else {
						String ip=fsuip.getText().toString();
						if(!DataAssembleUtil.verifyIP(ip)){
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
									1500).show();// toast��ʾ1000ms
						}else{
							byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
							String[] addr=ip.split("\\.");
							int a1,a2,a3,a4;
							byte[] b1,b2,b3,b4;
							a1=Integer.valueOf(addr[0]);
							a2=Integer.valueOf(addr[1]);
							a3=Integer.valueOf(addr[2]);
							a4=Integer.valueOf(addr[3]);
							b1=dau.intToAscByte2(a1);
							b2=dau.intToAscByte2(a2);
							b3=dau.intToAscByte2(a3);
							b4=dau.intToAscByte2(a4);
							byte[] b = { 0x38, 0x41, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//8AH
							byte[] bb = dau.sendSetCmd(recByte, "89", b);
							//sendCommand(bb);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
							sendCMD = "89";
							countNum=0;
							
						}
					}
				}
			}
		});

		btnfsuid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(fsuid.getText().length()<0){
						return;
					}
					String p=fsuid.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<28){
						for(int i=0;i<28-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30};//30������
					b[0]=0x38;
					b[1]=0x42;
					int j=2;
					for(int i=1;i<29;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//8BH
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btndpcid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(dpcid.getText().length()<0){
						return;
					}
					String p=dpcid.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<28){
						for(int i=0;i<28-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30};//30������
					b[0]=0x38;
					b[1]=0x43;
					int j=2;
					for(int i=1;i<29;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//8CH
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnwlms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					long sel=wlms.getSelectedItemId();
					if(sel==0){
						return;
					}else{
						byte[] b={0x30,0x30};
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						if(sel==1){//ѡ��apn
							b[0]=30;
							b[1]=31;
						}else{
							b[0]=31;
							b[1]=31;
							
						}
						byte[] b2 = { 0x38, 0x44 ,b[1],b[0]}; 		//8DH
						byte[] bb = dau.sendSetCmd(recByte, "89", b2);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
					}
				}
			}
		});

		btnapn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(apnyhm.getText().length()<0){
						return;
					}
					String p=apn.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x38;
					b[1]=0x45;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//8EH
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnapnyhm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(apnyhm.getText().length()<0){
						return;
					}
					String p=apnyhm.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x38;
					b[1]=0x46;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//8FH
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnapnmm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(apnmm.getText().length()<0){
						return;
					}
					String p=apnmm.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x39;
					b[1]=0x30;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//90H
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnxykip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
								1000).show();// toast��ʾ1000ms
						countNum++;
						if(countNum>2){
							sendCMD="";
						}
					} else {
						String ip=xykip.getText().toString();
						if(!DataAssembleUtil.verifyIP(ip)){
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
									1500).show();// toast��ʾ1000ms
						}else{
							byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
							String[] addr=ip.split("\\.");
							int a1,a2,a3,a4;
							byte[] b1,b2,b3,b4;
							a1=Integer.valueOf(addr[0]);
							a2=Integer.valueOf(addr[1]);
							a3=Integer.valueOf(addr[2]);
							a4=Integer.valueOf(addr[3]);
							b1=dau.intToAscByte2(a1);
							b2=dau.intToAscByte2(a2);
							b3=dau.intToAscByte2(a3);
							b4=dau.intToAscByte2(a4);
							byte[] b = { 0x39, 0x31, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//91H
							byte[] bb = dau.sendSetCmd(recByte, "89", b);
							//sendCommand(bb);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
							sendCMD = "89";
							countNum=0;
							
						}
					}
				}
			}
		});

		btnxykdk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String port=xykdk.getText().toString();
					if(!isPORT(port)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast2),//������Ϸ���ֵ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						int a=Integer.valueOf(port);
						byte[] b1=dau.intToAscByte4(a);
						byte[] b = { 0x39, 0x32, b1[0],b1[1],b1[2],b1[3]};		//92H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btnzcjip3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
								1000).show();// toast��ʾ1000ms
						countNum++;
						if(countNum>2){
							sendCMD="";
						}
					} else {
						String ip=zcjip3.getText().toString();
						if(!DataAssembleUtil.verifyIP(ip)){
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
									1500).show();// toast��ʾ1000ms
						}else{
							byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
							String[] addr=ip.split("\\.");
							int a1,a2,a3,a4;
							byte[] b1,b2,b3,b4;
							a1=Integer.valueOf(addr[0]);
							a2=Integer.valueOf(addr[1]);
							a3=Integer.valueOf(addr[2]);
							a4=Integer.valueOf(addr[3]);
							b1=dau.intToAscByte2(a1);
							b2=dau.intToAscByte2(a2);
							b3=dau.intToAscByte2(a3);
							b4=dau.intToAscByte2(a4);
							byte[] b = { 0x39, 0x33, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//93H
							byte[] bb = dau.sendSetCmd(recByte, "89", b);
							//sendCommand(bb);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
							sendCMD = "89";
							countNum=0;
							
						}
					}
				}
			}
		});

		btndkh3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String port=dkh3.getText().toString();
					if(!isPORT(port)){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast2),//������Ϸ���ֵ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						int a=Integer.valueOf(port);
						byte[] b1=dau.intToAscByte4(a);
						byte[] b = { 0x39, 0x34, b1[0],b1[1],b1[2],b1[3]};		//94H
						byte[] bb = dau.sendSetCmd(recByte, "89", b);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
						
					}
				}
			}
		});

		btnvpn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
								1000).show();// toast��ʾ1000ms
						countNum++;
						if(countNum>2){
							sendCMD="";
						}
					} else {
						String ip=vpn3.getText().toString();
						if(!DataAssembleUtil.verifyIP(ip)){
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
									1500).show();// toast��ʾ1000ms
						}else{
							byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
							String[] addr=ip.split("\\.");
							int a1,a2,a3,a4;
							byte[] b1,b2,b3,b4;
							a1=Integer.valueOf(addr[0]);
							a2=Integer.valueOf(addr[1]);
							a3=Integer.valueOf(addr[2]);
							a4=Integer.valueOf(addr[3]);
							b1=dau.intToAscByte2(a1);
							b2=dau.intToAscByte2(a2);
							b3=dau.intToAscByte2(a3);
							b4=dau.intToAscByte2(a4);
							byte[] b = { 0x39, 0x35, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//95H
							byte[] bb = dau.sendSetCmd(recByte, "89", b);
							//sendCommand(bb);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
							sendCMD = "89";
							countNum=0;
							
						}
					}
				}
			}
		});

		btnvpnuser3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(vpnuser3.getText().length()<0){
						return;
					}
					String p=vpnuser3.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x39;
					b[1]=0x36;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//96H
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnvpnpass3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(vpnpass3.getText().length()<0){
						return;
					}
					String p=vpnpass3.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x39;
					b[1]=0x37;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//97H
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnfsuvpnip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
								1000).show();// toast��ʾ1000ms
						countNum++;
						if(countNum>2){
							sendCMD="";
						}
					} else {
						String ip=fsuvpnip.getText().toString();
						if(!DataAssembleUtil.verifyIP(ip)){
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
									1500).show();// toast��ʾ1000ms
						}else{
							byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
							String[] addr=ip.split("\\.");
							int a1,a2,a3,a4;
							byte[] b1,b2,b3,b4;
							a1=Integer.valueOf(addr[0]);
							a2=Integer.valueOf(addr[1]);
							a3=Integer.valueOf(addr[2]);
							a4=Integer.valueOf(addr[3]);
							b1=dau.intToAscByte2(a1);
							b2=dau.intToAscByte2(a2);
							b3=dau.intToAscByte2(a3);
							b4=dau.intToAscByte2(a4);
							byte[] b = { 0x39, 0x38, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//98H
							byte[] bb = dau.sendSetCmd(recByte, "89", b);
							//sendCommand(bb);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
							sendCMD = "89";
							countNum=0;
							
						}
					}
				}
			}
		});

		btngwip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
								1000).show();// toast��ʾ1000ms
						countNum++;
						if(countNum>2){
							sendCMD="";
						}
					} else {
						String ip=gwip.getText().toString();
						if(!DataAssembleUtil.verifyIP(ip)){
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msf_Toast4),//������Ϸ���IP��ַ
									1500).show();// toast��ʾ1000ms
						}else{
							byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
							String[] addr=ip.split("\\.");
							int a1,a2,a3,a4;
							byte[] b1,b2,b3,b4;
							a1=Integer.valueOf(addr[0]);
							a2=Integer.valueOf(addr[1]);
							a3=Integer.valueOf(addr[2]);
							a4=Integer.valueOf(addr[3]);
							b1=dau.intToAscByte2(a1);
							b2=dau.intToAscByte2(a2);
							b3=dau.intToAscByte2(a3);
							b4=dau.intToAscByte2(a4);
							byte[] b = { 0x39, 0x39, b1[0],b1[1],b2[0],b2[1],b3[0],b3[1],b4[0],b4[1]};		//99H
							byte[] bb = dau.sendSetCmd(recByte, "89", b);
							//sendCommand(bb);
							DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
//							//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
							sendCMD = "89";
							countNum=0;
							
						}
					}
				}
			}
		});
		btnswmkxh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(swmkxh.getText().length()<0){
						return;
					}
					String p=swmkxh.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<60){
						for(int i=0;i<60-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30};//62������
					b[0]=0x39;
					b[1]=0x41;
					int j=2;
					for(int i=1;i<61;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//9AH
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});
		btnmac.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(mac.getText().length()<0){
						return;
					}
					String p=mac.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x39;
					b[1]=0x42;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//9BH
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		mac.addTextChangedListener(new TextWatcher() {
			private boolean mWasEdited = false;
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable text) {
				// TODO Auto-generated method stub
				if(mWasEdited){
	                mWasEdited = false;
	                return;
	            }
				mWasEdited=true;
				String mac = text.toString();
	            mac = mac.toUpperCase().replace("-","").replace(":","");
	            StringBuilder builder = new StringBuilder();
	            for(int i=0;i<mac.length()&&i<12;i++){
	                builder.append(mac.charAt(i));
	                if(i%2!=0&&i!=mac.length()-1&&i!=11){
	                    builder.append(":");
	                }
	            }
	            text.replace(0,text.length(),builder.toString());

			}
		});

		btnsjch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if(sjch.getText().length()<0){
						return;
					}
					String p=sjch.getText().toString();
					StringBuilder tmp=new StringBuilder();
					if(p.length()<40){
						for(int i=0;i<40-p.length();i++){
							tmp.append("0");
						}
						p=tmp.toString()+p;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					String[] a=p.split("");
					byte[] b=new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
							0x30,0x30,0x30};//42������
					b[0]=0x39;
					b[1]=0x43;
					int j=2;
					for(int i=1;i<41;i++){
						byte[] t=a[i].getBytes();
						b[j]=t[0];
						//b[j+1]=t[0];
						j++;
					}
					byte[] bb = dau.sendSetCmd(recByte, "89", b);	//9CH
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});
//////////////////////kkkkkkkkkkkkkkk/////////////////////////////////////////////////////////////////////////////////
		btnsimkyys.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					long sel=simkyys.getSelectedItemId();
					if(sel==0){
						return;
					}else{
						byte[] b={0x30,0x30};
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						if(sel==1){//�ƶ�
							b[0]=30;
							b[1]=31;
						}else if(sel==2){//��ͨ
							b[0]=30;
							b[1]=32;
						}else if(sel==3){//����
							b[0]=30;
							b[1]=33;
						}else{//����
							b[0]=30;
							b[1]=30;
						}
						byte[] b2 = { 0x39, 0x44 ,b[1],b[0]}; 		//9DH
						byte[] bb = dau.sendSetCmd(recByte, "89", b2);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
					}
				}
			}
		});

		btnsimkxhqd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					int v=Integer.valueOf(simkxhqd.getText().toString());
					if(v<-128 || v>127){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valueOverRange) + sendCMD,//ֵ����Χ
								1000).show();// toast��ʾ1000ms
						return;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
					byte[] b = dau.intToAscByte2(v);
					byte[] b2 = { 0x39, 0x45, b[0],b[1]};		//9EH
					byte[] bb = dau.sendSetCmd(recByte, "89", b2);
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "89";
					countNum=0;
				}
			}
		});

		btnwlzs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					long sel=wlzs.getSelectedItemId();
					if(sel==0){
						return;
					}else{
						byte[] b={0x30,0x30};
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						if(sel==1){//2G
							b[0]=30;
							b[1]=32;
						}else if(sel==2){//3G
							b[0]=30;
							b[1]=33;
						}else if(sel==3){//4G
							b[0]=30;
							b[1]=34;
						}else if(sel==4){//5G
							b[0]=30;
							b[1]=35;
						}else{//����
							b[0]=30;
							b[1]=30;
						}
						byte[] b2 = { 0x39, 0x46 ,b[1],b[0]}; 		//9FH
						byte[] bb = dau.sendSetCmd(recByte, "89", b2);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
					}
				}
			}
		});

		btnpjrzt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					long sel=ptjrzt.getSelectedItemId();
					if(sel==0){
						return;
					}else{
						byte[] b={0x30,0x30};
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						if(sel==1){//����
							b[0]=30;
							b[1]=31;
						}else{//δ����
							b[0]=30;
							b[1]=30;
						}
						byte[] b2 = { 0x42, 0x30 ,b[1],b[0]}; 		//B0H
						byte[] bb = dau.sendSetCmd(recByte, "89", b2);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
					}
				}
			}
		});

		btnsgbljzt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("89") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					long sel=sgbljzt.getSelectedItemId();
					if(sel==0){
						return;
					}else{
						byte[] b={0x30,0x30};
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 89
						if(sel==1){//��ͨ
							b[0]=30;
							b[1]=31;
						}else{//δ��ͨ
							b[0]=30;
							b[1]=30;
						}
						byte[] b2 = { 0x42, 0x31 ,b[1],b[0]}; 		//B1H
						byte[] bb = dau.sendSetCmd(recByte, "89", b2);
						//sendCommand(bb);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
						sendCMD = "89";
						countNum=0;
					}
				}
			}
		});

		fivegsys.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("88") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x35 };//D5 88
					byte[] bb = dau.AssembleReadData(recByte, "88");
					//sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "88";
					countNum=0;
					///ppppppppppppppppppppppppppp////
					/*displayData("7E3230303144353030303030304646464546444643464646464646464546444643" +
							"4646464646464645464446433030303030303030303030303030303030303030676767" +
							"6666666666666767676767666666666767303030303030303030433030303030303030" +
							"3030303030303030303030303030303030303030303146464645464446433030303030" +
							"3030303030303030303030303030303030303030303030303030303030307430303032" +
							"3030303030303030303030303030303030303030303033303030303030303030303030" +
							"3030303032464646454644464330303030313032303839713230493030303030303030" +
							"3030303031303030303030303030303030656667686970303030303030303030313130" +
							"4246464645464446446464645464446446464645464446446464645464464646454644" +
							"4646464645463030303030303030303030303030303030303030303030303030303030" +
							"3030303030303030313031303030303030303030303030303030303030303030303030" +
							"3030303030303030303030303031303246464645464446434646464664646454644464" +
							"3346464646464646454644464330303030303030303030303030303030303030303030" +
							"3030303030303030303030303030303230313030303030303030303030303030303030" +
							"303030303030303030303030303030303030303033303143304138303A303230313031" +
							"3031303130303031303230333034303530363037303830393030303130323033303430" +
							"3530363037303830393030303130323033303430353036303730383039303030303030" +
							"303030303030303030303030303030303044463A30313A45303A37373A33463A453930" +
							"3030303030303030303030303030303030303030303030303030303030303030303031" +
							"3037303130323830303530313030364433440D");*/
				}
			}
		});

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
