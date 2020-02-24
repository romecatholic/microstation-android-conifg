package com.dgm.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
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
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dgm.adapter.CurrentProtectAdapter;
import com.dgm.aes.AES;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

public class DigitalSwitchFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private DataAssembleUtil dau;
	public String sendCMD = "";//上次发送指令CID2标示
	public int countNum=0;
	public EditText digVolEdit,dsf_clickEdit,dsf_fstValEdit,dsf_sndValEdit,dsf_csiEdit,setflNumEdit;//,
			//updateIPA1, updateIPA2, updateIPA3,
			//updateIPA4, updateIPB1, updateIPB2, updateIPB3,setD,
			//updateIPB4;//,dsf_queryEngEdit;
	//private TextView cmdD;
	//private String[] itemsD1 ;
	//private String[] itemsD2 = { "80", "81", "82", "83", "85", "86",
	//		"87", "88" };
	public Button getdsfAIBtn,road1_set,road2_set,road3_set,road4_set,road5_set,road6_set
	,road7_set,road8_set,getRoadStateBtn,getParamStateBtn,TimeSet,setflNumbtn,TimeSet2;//,setUpdate,getUpdate;//,dsf_queryEngsetBtn;//,setDsfClear;
	public RadioButton rbconn1,rbdis1,rbconn2,rbdis2,rbconn3,rbdis3,rbconn4,rbdis4,rbconn5,rbdis5
	,rbconn6,rbdis6,rbconn7,rbdis7,rbconn8,rbdis8;
	boolean connect_status_bit = false;
	List<Map<String, String>> ulist = new ArrayList<Map<String, String>>();
	List<Map<String, String>> ulist2 = new ArrayList<Map<String, String>>();
	List<Map<String, String>> ulist3 = new ArrayList<Map<String, String>>();
	private SimpleAdapter engListAdapter, optimizerAdapter;
	private CurrentProtectAdapter optimizerAlarmAdapter;
	public AlertDialog optimizerDialog,optimizerAlarmDialog,engDialog;
	public ListView optimizerListView=null,engListView=null,optimizerAlarmListView=null;
	private TextView ds_startTimeEdit,ds_endTimeEdit,ds_startTimeEdit2,ds_endTimeEdit2;

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
            }
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
		mBaseView = inflater.inflate(R.layout.digital_switch_fragment,
				null);
		mContext = getActivity();
		dau = new DataAssembleUtil();
		
		//digVolEdit,digFstCurrEdit,digFstPwrEdit,digSndCurEdit,digSndPwrEdit
		//,dsf_fstValEdit,dsf_sndValEdit
		digVolEdit = (EditText) mBaseView.findViewById(R.id.digVolEdit);
		dsf_clickEdit = (EditText) mBaseView.findViewById(R.id.dsf_clickEdit);
		dsf_csiEdit=(EditText)mBaseView.findViewById(R.id.dsf_csiEdit);
		setflNumEdit=(EditText)mBaseView.findViewById(R.id.setflNumEdit);
		/*updateIPA1 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPA1);// FSUID

		updateIPA2 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPA2);// FSUID

		updateIPA3 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPA3);// FSUID

		updateIPA4 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPA4);// FSUID

		updateIPB1 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPB1);// FSUID

		updateIPB2 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPB2);// FSUID

		updateIPB3 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPB3);// FSUID

		updateIPB4 = (EditText) mBaseView
				.findViewById(R.id.UpdateIPB4);// FSUID
		cmdD = (TextView) mBaseView.findViewById(R.id.CmdD);// FSUID
		
		setD = (EditText) mBaseView.findViewById(R.id.Update);// FSUID*/
		//dsf_queryEngEdit = (EditText) mBaseView.findViewById(R.id.dsf_queryEngEdit);
		//getdsfAIBtn,road1_set,road2_set,road3_set,road4_set,road5_set,road6_set
		//,road7_set,road8_set,getRoadStateBtn,dsf_firstSet,dsf_secondSet;
		getdsfAIBtn = (Button) mBaseView.findViewById(R.id.getdsfAIBtn);
		
		getRoadStateBtn = (Button) mBaseView.findViewById(R.id.getRoadStateBtn);//获取分路通断状态
		road1_set = (Button) mBaseView.findViewById(R.id.road1_set);//分路1
		road2_set = (Button) mBaseView.findViewById(R.id.road2_set);//分路2
		road3_set = (Button) mBaseView.findViewById(R.id.road3_set);//分路3
		road4_set = (Button) mBaseView.findViewById(R.id.road4_set);//分路4
		road5_set = (Button) mBaseView.findViewById(R.id.road5_set);//分路5
		road6_set = (Button) mBaseView.findViewById(R.id.road6_set);//分路6
		road7_set = (Button) mBaseView.findViewById(R.id.road7_set);//分路7
		road8_set = (Button) mBaseView.findViewById(R.id.road8_set);//分路8
		setflNumbtn=(Button) mBaseView.findViewById(R.id.setflNumbtn);//设置分路数量
		getParamStateBtn=(Button)mBaseView.findViewById(R.id.getParamStateBtn);
		TimeSet=(Button)mBaseView.findViewById(R.id.TimeSet);
		TimeSet2=(Button)mBaseView.findViewById(R.id.TimeSet2);
		/*setUpdate = (Button) mBaseView.findViewById(R.id.UpdateSet);// 设置参数
		getUpdate = (Button) mBaseView.findViewById(R.id.UpdateGet);// 读取参数*/
		//dsf_queryEngsetBtn = (Button) mBaseView.findViewById(R.id.dsf_queryEngsetBtn);
		//setDsfClear = (Button) mBaseView.findViewById(R.id.setDsfClear);
		///rbconn1,rbdis1,rbconn2,rbdis2,rbconn3,rbdis3,rbconn4,rbdis4,rbconn5,rbdis5
		//,rbconn6,rbdis6,rbconn7,rbdis7,rbconn8,rbdis8
		
		rbconn1=(RadioButton)mBaseView.findViewById(R.id.rbconn1);
		rbdis1=(RadioButton)mBaseView.findViewById(R.id.rbdis1);
		rbconn2=(RadioButton)mBaseView.findViewById(R.id.rbconn2);
		rbdis2=(RadioButton)mBaseView.findViewById(R.id.rbdis2);
		rbconn3=(RadioButton)mBaseView.findViewById(R.id.rbconn3);
		rbdis3=(RadioButton)mBaseView.findViewById(R.id.rbdis3);
		rbconn4=(RadioButton)mBaseView.findViewById(R.id.rbconn4);
		rbdis4=(RadioButton)mBaseView.findViewById(R.id.rbdis4);
		rbconn5=(RadioButton)mBaseView.findViewById(R.id.rbconn5);
		rbdis5=(RadioButton)mBaseView.findViewById(R.id.rbdis5);
		rbconn6=(RadioButton)mBaseView.findViewById(R.id.rbconn6);
		rbdis6=(RadioButton)mBaseView.findViewById(R.id.rbdis6);
		rbconn7=(RadioButton)mBaseView.findViewById(R.id.rbconn7);
		rbdis7=(RadioButton)mBaseView.findViewById(R.id.rbdis7);
		rbconn8=(RadioButton)mBaseView.findViewById(R.id.rbconn8);
		rbdis8=(RadioButton)mBaseView.findViewById(R.id.rbdis8);
		
		ds_startTimeEdit=(TextView) mBaseView.findViewById(R.id.ds_startTimeEdit);
		ds_endTimeEdit=(TextView) mBaseView.findViewById(R.id.ds_endTimeEdit);
		ds_startTimeEdit2=(TextView) mBaseView.findViewById(R.id.ds_startTimeEdit2);
		ds_endTimeEdit2=(TextView) mBaseView.findViewById(R.id.ds_endTimeEdit2);
		engListView=new ListView(mContext);
		optimizerListView=new ListView(mContext);
		optimizerAlarmListView=new ListView(mContext);
		optimizerAdapter=new SimpleAdapter(mContext, ulist,
                R.layout.list_item_1, new String[] { "name",
        "value" }, new int[] { R.id.li_name,
        R.id.li_value });
		optimizerAlarmAdapter=new CurrentProtectAdapter(mContext, ulist2);
		optimizerAlarmAdapter.setD(this);
		engListAdapter=new SimpleAdapter(mContext, ulist3,
                R.layout.list_item_2, new String[] { "xh","sj","dn","rq" }
		, new int[] { R.id.xh,R.id.sj,R.id.dn,R.id.rq });
		
		optimizerAlarmDialog=new AlertDialog.Builder(mContext)
		.setIcon(android.R.drawable.alert_dark_frame)
		.setView(optimizerAlarmListView)
		.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	optimizerAlarmDialog.dismiss();
                }
         }).create();
		optimizerAlarmDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		optimizerAlarmDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		optimizerAlarmDialog.show();
		optimizerAlarmDialog.dismiss();
		getdsfAIBtn.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x38 };//D8 42
					byte[] b2 = { 0x46, 0x46 };
					byte[] bb = dau.sendSetCmd(recByte, "42", b2);
					sendCommand(bb);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
					sendCMD = "42";
					countNum=0;
				}
			}
		});
		
		getRoadStateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//sendCMD = "43";
				//displayData("7E32303031443830303630304130343032304630463030464237380D");
				//return;
				if (sendCMD.length() > 0 && sendCMD.compareTo("43") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x38 };//D2 44
					byte[] b = dau.AssembleReadData(recByte, "43");
					sendCMD = "43";
					countNum=0;
					//sendCommand(b);
					/*byte[]b10=new byte[20];
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
					//sendCommand(b);*/
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);					
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);					
				}
			}
		});
		road1_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=129;//81H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn1.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		road2_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=130;//82H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn2.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		road3_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=131;//83H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn3.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		road4_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=132;//84H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn4.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		road5_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=133;//85H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn5.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		road6_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=134;//86H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn6.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		road7_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=135;//87H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn7.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		road8_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 49
					int commandType=136;//88H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(rbconn8.isChecked()){
						bb[2]=(byte) 0x30;
						bb[3]=(byte) 0x46;
						
					}else{
						bb[2]=(byte) 0x46;
						bb[3]=(byte) 0x30;
					}
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
		dsf_csiEdit.setOnLongClickListener(new OnLongClickListener(){
			
			@Override
			public boolean onLongClick(View v){
				optimizerAlarmListView.setAdapter(optimizerAlarmAdapter);
				if(optimizerAlarmDialog==null){
					optimizerAlarmDialog=new AlertDialog.Builder(mContext)
					.setIcon(android.R.drawable.alert_dark_frame)
					.setView(optimizerAlarmListView)
					.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
		                    @Override
		                    public void onClick(DialogInterface dialog, int which) {
		                    	optimizerAlarmDialog.dismiss();
		                    }
		             }).create();
				
				}
				optimizerAlarmDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
	                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				optimizerAlarmDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				optimizerAlarmDialog.show();
				return true;
			}
		});
		getParamStateBtn.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x38 };//D8 47
					byte[] b = dau.AssembleReadData(recByte, "47");
					sendCMD = "47";
					countNum=0;
					
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);					
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);	
				}
			}
		});	
		ds_startTimeEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TimePickerDialog dialog=new TimePickerDialog(mBaseView.getContext(), new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						// TODO Auto-generated method stub
						StringBuilder starttime=new StringBuilder();
		            	starttime.append(h < 10 ? "0" + h : h).append(":")
                        .append(m < 10 ? "0" + m : m);
						ds_startTimeEdit.setText(starttime.toString());
					}
				}, 0, 0, true);
				
	            dialog.show();  
   			}
		});
		ds_endTimeEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TimePickerDialog dialog=new TimePickerDialog(mBaseView.getContext(), new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						// TODO Auto-generated method stub
						StringBuilder starttime=new StringBuilder();
		            	starttime.append(h < 10 ? "0" + h : h).append(":")
                        .append(m < 10 ? "0" + m : m);
		            	ds_endTimeEdit.setText(starttime.toString());
					}
				}, 0, 0, true);
				
	            dialog.show();  
   			}
		});
		TimeSet.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };	//D8 49
					String start=ds_startTimeEdit.getText().toString();
					String end=ds_endTimeEdit.getText().toString();
					
					String startarr[]=start.split(":");
					String endarr[]=end.split(":");
					
					byte [] starth=dau.intToAscByte2(Integer.valueOf(startarr[1]));
					byte [] startm=dau.intToAscByte2(Integer.valueOf(startarr[0]));
					byte [] endh=dau.intToAscByte2(Integer.valueOf(endarr[1]));
					byte [] endm=dau.intToAscByte2(Integer.valueOf(endarr[0]));
					byte [] bb={0x39,0x31,startm[0],startm[1],starth[0],starth[1],endm[0],endm[1],endh[0],endh[1]};
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
		
		setflNumbtn.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],0x44, 0x38 };//D8 49
					String vUplimit=setflNumEdit.getText().toString();
					if(vUplimit.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
								1500).show();// toast显示1000ms
					}else{
						int v=Integer.valueOf(vUplimit);
						byte []b1=dau.intToAscByte2(v);
						byte [] bb={0x38,0x30,b1[0],b1[1]};
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
					}
					
				}
			}
		});
		/*setUpdate.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };

					String id = "";
					Boolean isCorrect = false;
					if (sendType.equals("80")) {
						byte[] type = dau.stringHToAscByte2(sendType);//D8 87
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
							sendCommand(b10);
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };//D8 86
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
		itemsD1=new String[]{ getResources().getString(R.string.ds_ip5), getResources().getString(R.string.ds_ip6), getResources().getString(R.string.ds_ip7),//IP-1	IP-2	IP-3
				getResources().getString(R.string.ds_ip8),  getResources().getString(R.string.ds_ip9), getResources().getString(R.string.ds_ip10), getResources().getString(R.string.ds_ip11),//IP-4	mask-1	mask-2	mask-3
				getResources().getString(R.string.ds_ip12)};//mask-4

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
		});*/
		/*dsf_queryEngsetBtn.setOnClickListener(new OnClickListener() {

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
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x38 };//D2H
						String vUplimit=dsf_queryEngEdit.getText().toString();
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
								//sendCommand(b10);							
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
		/*setDsfClear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x41 };//D8 4A
					int commandType=138;//8AH
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x45,0x30};//0x45 0x30表示是E0
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
		});*/
		dsf_clickEdit.setOnLongClickListener(new OnLongClickListener(){
			
			@Override
			public boolean onLongClick(View v){
				optimizerListView.setAdapter(optimizerAdapter);
				if(optimizerDialog==null){
					optimizerDialog=new AlertDialog.Builder(mContext)
					.setIcon(android.R.drawable.alert_dark_frame)
					.setView(optimizerListView)
					.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
							@Override
							public void onClick(DialogInterface dialog, int which) {
								optimizerDialog.dismiss();
		                    	}
		                	}).create();
				}	
				optimizerDialog.show();
				return true;
			}
		});
		
		ds_startTimeEdit2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TimePickerDialog dialog=new TimePickerDialog(mBaseView.getContext(), new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						// TODO Auto-generated method stub
						StringBuilder starttime=new StringBuilder();
		            	starttime.append(h < 10 ? "0" + h : h).append(":")
                        .append(m < 10 ? "0" + m : m);
						ds_startTimeEdit2.setText(starttime.toString());
					}
				}, 0, 0, true);
				
	            dialog.show();  
   			}
		});
		ds_endTimeEdit2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TimePickerDialog dialog=new TimePickerDialog(mBaseView.getContext(), new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						// TODO Auto-generated method stub
						StringBuilder starttime=new StringBuilder();
		            	starttime.append(h < 10 ? "0" + h : h).append(":")
                        .append(m < 10 ? "0" + m : m);
		            	ds_endTimeEdit2.setText(starttime.toString());
					}
				}, 0, 0, true);
				
	            dialog.show();  
   			}
		});
		TimeSet2.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };	//D8 49
					String start=ds_startTimeEdit2.getText().toString();
					String end=ds_endTimeEdit2.getText().toString();
					
					String startarr[]=start.split(":");
					String endarr[]=end.split(":");
					
					byte [] starth=dau.intToAscByte2(Integer.valueOf(startarr[1]));
					byte [] startm=dau.intToAscByte2(Integer.valueOf(startarr[0]));
					byte [] endh=dau.intToAscByte2(Integer.valueOf(endarr[1]));
					byte [] endm=dau.intToAscByte2(Integer.valueOf(endarr[0]));
					byte [] bb={0x39,0x32,startm[0],startm[1],starth[0],starth[1],endm[0],endm[1],endh[0],endh[1]};
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
					getParamStateBtn.callOnClick();
				}catch(NullPointerException e){
					
				}catch(ConcurrentModificationException e){
					
				}
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				displayData(intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA));
			}
		}
	};

/*	private void writeFile(String data){
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
							final byte[] bb = dau.Data_Close(b2, b2.length);
							if (sendCMD.compareTo("42") == 0) {
								ulist.clear();
								DecimalFormat df=new DecimalFormat("0.00");
								dau.AscToHex2(b2, b2.length);
								int aV = DataAssembleUtil.parseInt(bb[2], bb[1]);//直流电压
								String a=df.format((float)aV/100);//直流电压
								digVolEdit.setText(a);
								int num=bb[3];//分路数量
								setflNumEdit.setText(String.valueOf(num));
								int start=4;//分路电流开始位置
								for(int i=0;i<num;i++){
									int bV = DataAssembleUtil.parseInt(bb[start+1], bb[start]);//第n路电流
									String bv=df.format((float)bV/100);
									
									long cV = DataAssembleUtil.parseUnsignedInt(bb[start+2], bb[start+3],bb[start+4], bb[start+5]);// 第一路电量
									String c=df.format((float)cV/100);// 第一路电量

									Map<String, String> keyValuePair = new HashMap<String, String>();
					    			keyValuePair.put("name", getResources().getString(R.string.dsf_firstcur1)+" "+(i+1)+getResources().getString(R.string.dsf_firstcur));//第i路电流
					    			keyValuePair.put("value", bv);//第i路电流的值
					    			ulist.add(keyValuePair);
					    			
					    			Map<String, String> keyValuePair1 = new HashMap<String, String>();
					    			keyValuePair1.put("name", getResources().getString(R.string.dsf_firstcur1)+" "+(i+1)+getResources().getString(R.string.dsf_firstpower));//第i路电量
					    			keyValuePair1.put("value", c);//第i路电量的值
					    			ulist.add(keyValuePair1);
					    			start=start+7;//间隔一个p
								}
								optimizerAdapter.notifyDataSetChanged();
								
							}else if (sendCMD.compareTo("43") == 0) {
								int num=bb[1];
								LinearLayout sLinerLayout = (LinearLayout)mBaseView.findViewById(R.id.rbgl);
								for (int j = 0; j < sLinerLayout.getChildCount(); j++) {  
									View ll=sLinerLayout.getChildAt(j);  
									if (ll instanceof LinearLayout){  
										LinearLayout mLinearLayout=(LinearLayout)sLinerLayout.getChildAt(j);
										RadioGroup mRadioGroup = (RadioGroup)mLinearLayout.getChildAt(1);  
										if(mRadioGroup!=null){
											mRadioGroup.clearCheck();
										}
									}
								}
								int s=0;
								for(int i=0;i<num;i++){
									int start=2+i;
									for (int j = 0; j < sLinerLayout.getChildCount(); j++) {  
										View v=sLinerLayout.getChildAt(j);  
										//int s=j+1;
										if (v instanceof LinearLayout){  
											LinearLayout mLinearLayout=(LinearLayout)sLinerLayout.getChildAt(j);
											TextView text=(TextView)mLinearLayout.getChildAt(0);
											RadioGroup mRadioGroup = (RadioGroup)mLinearLayout.getChildAt(1);  
											//根据ID获取RadioButton的实例          
											if(mRadioGroup!=null){
												if(bb[start]==0x0f){
													RadioButton rb = (RadioButton)mRadioGroup.getChildAt(0); 
													if(i==s){
														if(text.getText().toString().contains(String.valueOf(s+1))){
															rb.setChecked(true);
														}else{
															continue;
														}
													}
												}else{
													RadioButton rb = (RadioButton)mRadioGroup.getChildAt(1);  
													if(i==s){
														if(text.getText().toString().contains(String.valueOf(s+1))){
															rb.setChecked(true);
														}else{
															continue;
														}
													}
												}
												break;
											}
										}
									}
									s++;
								}
								/*for(int i=0;i<num;i++){
									switch(i){
									case 0:
										if(bb[2]==0x0f){
											rbconn1.setChecked(true);
										}else{
											rbdis1.setChecked(true);
										}
										break;
									case 1:
										if(bb[3]==0x0f){
											rbconn2.setChecked(true);
										}else{
											rbdis2.setChecked(true);
										}
										break;
									case 2:
										if(bb[4]==0x0f){
											rbconn3.setChecked(true);
										}else{
											rbdis3.setChecked(true);
										}
										break;
									case 3:
										if(bb[5]==0x0f){
											rbconn4.setChecked(true);
										}else{
											rbdis4.setChecked(true);
										}
										break;
									case 4:
										if(bb[6]==0x0f){
											rbconn5.setChecked(true);
										}else{
											rbdis5.setChecked(true);
										}
										break;
									case 5:
										if(bb[7]==0x0f){
											rbconn6.setChecked(true);
										}else{
											rbdis6.setChecked(true);
										}
										break;
									case 6:
										if(bb[8]==0x0f){
											rbconn7.setChecked(true);
										}else{
											rbdis7.setChecked(true);
										}
										break;
									case 7:
										if(bb[9]==0x0f){
											rbconn8.setChecked(true);
										}else{
											rbdis8.setChecked(true);
										}
										break;
									}
								}*/
							}else if (sendCMD.compareTo("47") == 0) {
								ulist2.clear();
								int num=bb[0];//分路个数
								int start=1;
				            	for(int j=0;j<num;j++){
				            		int v=bb[start];
				            		Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.dsf_firstcur1)+" "+(j+1)+getResources().getString(R.string.firstroad));//第i路电流保护值
									keyValuePair.put("value", String.valueOf(v));
									ulist2.add(keyValuePair);
				            		start=start+1;
				            	}
				            	optimizerAlarmAdapter.notifyDataSetChanged();
				            	int starth=bb[start];
				            	if(starth==24){
				            		starth=0;
				            	}
				            	int startm=bb[start+1];
				            	int endh=bb[start+2];
				            	if(endh==24){
				            		endh=0;
				            	}
				            	int endm=bb[start+3];
				            	StringBuilder starttime=new StringBuilder();
				            	starttime.append(starth < 10 ? "0" + starth : starth).append(":")
                                .append(startm < 10 ? "0" + startm : startm);
				            	StringBuilder endtime=new StringBuilder();
				            	endtime.append(endh < 10 ? "0" + endh : endh).append(":")
                                .append(endm < 10 ? "0" + endm : endm);
				            	ds_startTimeEdit.setText(starttime.toString());
				            	ds_endTimeEdit.setText(endtime.toString());
				            	
				            	int starth2=bb[start+4];
				            	if(starth2==24){
				            		starth2=0;
				            	}
				            	int startm2=bb[start+5];
				            	int endh2=bb[start+6];
				            	if(endh2==24){
				            		endh=0;
				            	}
				            	int endm2=bb[start+7];
				            	StringBuilder starttime2=new StringBuilder();
				            	starttime2.append(starth2 < 10 ? "0" + starth2 : starth2).append(":")
                                .append(startm2 < 10 ? "0" + startm2 : startm2);
				            	StringBuilder endtime2=new StringBuilder();
				            	endtime2.append(endh2 < 10 ? "0" + endh2 : endh2).append(":")
                                .append(endm2 < 10 ? "0" + endm2 : endm2);
				            	ds_startTimeEdit2.setText(starttime2.toString());
				            	ds_endTimeEdit2.setText(endtime2.toString());
							}else if(sendCMD.compareTo("80") == 0){
								dau.AscToHex2(b2, b2.length);
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
												keyValuePair.put("rq", getResources().getString(R.string.pv_rq));//查询出来的信息
												ulist3.add(keyValuePair);
											}
											for(int i = 0;i<count;i++){
												String sj=bb[base]+"/"+bb[base+1]+"/"+bb[base+2];
												long dn=DataAssembleUtil.parseUnsignedInt(bb[base+3],bb[base+4],bb[base+5],bb[base+6]);
												int rq=DataAssembleUtil.parseInt(bb[base+8],bb[base+7]);
												Map<String, String> keyValuePair = new HashMap<String, String>();
												keyValuePair.put("xh",String.valueOf(i+1));
												keyValuePair.put("sj", String.valueOf(sj));
												keyValuePair.put("dn", String.valueOf(df.format((float)dn/100)));
												keyValuePair.put("rq", String.valueOf(rq));
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
							} /*else if (sendCMD.compareTo("86") == 0) {//
								dau.AscToHex2(b2, b2.length);
								int IPA1 = bb[0]&0xff;
								int IPA2 = bb[1]&0xff;
								int IPA3 = bb[2]&0xff;
								int IPA4 = bb[3]&0xff;
								
								int IPB1 = bb[4]&0xff;
								int IPB2 = bb[5]&0xff;
								int IPB3 = bb[6]&0xff;
								int IPB4 = bb[7]&0xff;
								
								updateIPA1.setText("" + IPA1);
								updateIPA2.setText("" + IPA2);
								updateIPA3.setText("" + IPA3);
								updateIPA4.setText("" + IPA4);
								updateIPB1.setText("" + IPB1);
								updateIPB2.setText("" + IPB2);
								updateIPB3.setText("" + IPB3);
								updateIPB4.setText("" + IPB4);
								
								recString = "";
								sendCMD = "";
								setD.setText("");
							}*/
							
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
	public void sendCurrentProtect(int position,String v){
		int pos=position+137;//从89h开始
		if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
			Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
					1000).show();// toast显示1000ms
			countNum++;
			if(countNum>2){
				sendCMD="";
			}
		} else {
			byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x38 };	//D8 49
			if(v.length()==0){
				Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//请填写需要设置的值
						1500).show();// toast显示1000ms
			}else{
				int vUp=Integer.valueOf(v);
				if((vUp>50) || (vUp<0)){
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valueOverRange),//值超范围
							1500).show();// toast显示1000ms
					return;
				}
				byte [] b0=dau.intToAscByte2(pos);
				byte [] b1=dau.intToAscByte2(vUp);
				byte [] bb={b0[0],b0[1],b1[0],b1[1]};
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
}