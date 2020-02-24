package com.dgm.fragment;

import java.util.ConcurrentModificationException;
import java.util.List;

import com.dgm.bean.ModelBean;
import android.app.AlertDialog;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;

import android.os.Handler;
import android.os.Message;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ShiyanFragment extends Fragment{
    protected static final String TAG = "ShiyanFragment";
    private FrameLayout fl;
    private LinearLayout dsf_ll,ll_univer,ll_ac,ll_dc,ll_rec,ll_dcdc,ll_bat,ll_photo,ll_micro,ll_dig,ll_fiveg;
    private DevicesInfo2Activity dia;
	private Context mContext;
	private View mBaseView;
	boolean connect_status_bit = false;
	private DataAssembleUtil dau;
	//2019年9月24日删除密码样式
	boolean notovertime=true;
	
	public DevicesInfo2Activity getDia() {
		return dia;
	}

	public void setDia(DevicesInfo2Activity dia) {
		this.dia = dia;
	}

	//2019年9月24日删除密码样式
	
	private AlertDialog inputDialog;
	private View dialogview;//自定义一个dialog
	private EditText inputEditText;
	private Button btn_comfirm,btn_cancel,btn_replay;
	long n=0;
	
	//放开
    private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			Object object = msg.obj;
            if(msg.what==1){
           		btn_cancel=null;
           		btn_comfirm=null;
           		btn_replay=null;
           		inputDialog.dismiss();
           		DevicesInfo2Activity.titles="first";
           		try{
           			if(!DataAssembleUtil.isSendPass){
           				getActivity().onBackPressed();
           			}
           		}catch(Exception e){
           			//Log.e("exception",e.getMessage());
           		}
            }else if(msg.what==2){
            	btn_replay=(Button)dialogview.findViewById(R.id.btn_reply);//重发按钮
				btn_replay.setEnabled(false);
				btn_replay.setText("10");
            	if(btn_replay!=null){
            		if((""+object).equals("0")){
            			btn_cancel=null;
                		btn_comfirm=null;
                		btn_replay=null;
                		inputDialog.dismiss();
                		//DevicesInfo2Activity.titles="first";
                		try{
                			getActivity().onBackPressed();
                		}catch(Exception ex){
                			//Log.e("exception",ex.getMessage());
                		}
            		}else{
            			btn_replay.setText(""+object);
            		}
            		
            	}
            }else if(msg.what==3){
            	btn_comfirm.callOnClick();
            }
        };

	
	};    
	//放开结束
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
		mBaseView = inflater.inflate(R.layout.shiyan_fragment,
				null);
		mContext = getActivity();

		dsf_ll=(LinearLayout)mBaseView.findViewById(R.id.dsf_ll);
		int linearCount=0;
		WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
		for (int j = 0; j < dsf_ll.getChildCount(); j++) {  
			View ll=dsf_ll.getChildAt(j);  
			if (ll instanceof LinearLayout){  
				linearCount++;
			}
		}
		if(linearCount>5){
			linearCount=5;
		}
		for (int j = 0; j < dsf_ll.getChildCount(); j++) {  
			View ll=dsf_ll.getChildAt(j);  
			if (ll instanceof LinearLayout){  
				LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) ll.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20  
				linearParams.height = height/linearCount;// 控件的宽强制设成30   
				ll.setLayoutParams(linearParams); 
			}
		}
        ll_univer=(LinearLayout)mBaseView.findViewById(R.id.ll_univer);//通用命令
        ll_ac=(LinearLayout)mBaseView.findViewById(R.id.ll_ac);//交流分配器
        ll_dc=(LinearLayout)mBaseView.findViewById(R.id.ll_dc);//直流分配器
        ll_rec=(LinearLayout)mBaseView.findViewById(R.id.ll_rec);//整流配电
        ll_bat=(LinearLayout)mBaseView.findViewById(R.id.ll_bat);//锂电信息
        ll_dcdc=(LinearLayout)mBaseView.findViewById(R.id.ll_dcdc);//dcdc
        ll_photo=(LinearLayout)mBaseView.findViewById(R.id.ll_photo);//光伏汇流板
        ll_micro=(LinearLayout)mBaseView.findViewById(R.id.ll_micro);//微站设置
        ll_dig=(LinearLayout)mBaseView.findViewById(R.id.ll_dig);//电子开关
        ll_fiveg=(LinearLayout)mBaseView.findViewById(R.id.ll_fiveg);//5G设置
        ll_univer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("universal");
			}
		});
        ll_ac.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("acdistribution");
			}
		});
        ll_dc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("dcdistribution");
			}
		});
        ll_rec.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("rectifier");
			}
		});
        ll_bat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("batteryinfo");
			}
		});
        ll_dcdc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("dcdc");
			}
		});
        ll_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("photo");
			}
		});
        ll_micro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("micro");
			}
		});
        ll_dig.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia.switchFragment("digitalfragment");
			}
		});
        ll_fiveg.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		// TODO Auto-generated method stub
        		dia.switchFragment("fivefragment");
        	}
        });
    	//2019年9月24日删除密码样式
        //放开
        
        dialogview= View.inflate(getActivity(), R.layout.pass_dialog, null);
		inputEditText=(EditText)dialogview.findViewById(R.id.dialog_edit);
		inputDialog = new AlertDialog.Builder(getActivity()).create();
		inputDialog.setView(dialogview);
		inputDialog.setCancelable(false);
		try {
			btn_comfirm=(Button)dialogview.findViewById(R.id.btn_comfirm);//确定按钮
			btn_comfirm.setOnClickListener(new View.OnClickListener() {
			   	 @Override
			   	 public void onClick(View v) {
			   		String check;
			   		if(DataAssembleUtil.VERSION[0]==0x32){
			   			check="123456";
			   		}else{
			   			check=inputEditText.getText().toString();
			   		}
			   		if(check.equals("")||check.equals(null)){
			   			try{
			   				inputDialog.dismiss();
	            			getActivity().onBackPressed();
	            		}catch(Exception e){
	            			//Log.e("exception",e.getMessage());
	            		}
	            	}else if(check.length()!=6){
	            		try{
	            			inputDialog.dismiss();
	            			getActivity().onBackPressed();
	            		}catch(Exception e){
	            			//Log.e("exception",e.getMessage());
	            		}
	            	}else{
	            		//改用BJJA密码加密时用20191024
	            		byte[] cmd;
	            		if(DataAssembleUtil.isJAUUID==0){
	            			cmd=new byte["DPC_BJJA=".getBytes().length+check.getBytes().length];
	            			System.arraycopy("DPC_BJJA=".getBytes(), 0, cmd, 0, "DPC_BJJA=".getBytes().length);
	            			System.arraycopy( check.getBytes(), 0, cmd, "DPC_BJJA=".getBytes().length, check.getBytes().length);
	            		}else{
	            			cmd=check.getBytes();
	            		}
	            		//结束
	            		UniversalCommandFragment com=new UniversalCommandFragment();
	            		DataAssembleUtil.isSendPass=true;
	            		notovertime=false;
	            		com.sendPass(cmd);
	            		inputDialog.dismiss();
	            		new Thread(){
	            			public void run() {
	            				try {
									Thread.sleep(1000*3);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	            				UniversalCommandFragment com2=new UniversalCommandFragment();
	            				com2.sendMessageToBlueTooth(0);
	            				//inputDialog.dismiss();
	            			};
	            		}.start();
					 }
			   	 }
			   });
				btn_cancel=(Button)dialogview.findViewById(R.id.btn_cancel);//取消按钮
				btn_cancel.setOnClickListener(new View.OnClickListener() {
					@Override
				   	public void onClick(View v) {
						inputDialog.dismiss();
						try{
							getActivity().onBackPressed();
						}catch(Exception e){
							
						}
				    }
			    });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!DataAssembleUtil.isSendPass){
			if(DataAssembleUtil.VERSION[0]==0x31){
				inputDialog.show();	
		
				n=System.currentTimeMillis();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							long n2=System.currentTimeMillis()-n;
            		
							int count=10;
							while(notovertime){
								if((n2>9*1000)){
									Message message = handler.obtainMessage();
									message.what = 1;
									handler.sendMessage(message);
									notovertime=false;
								}else{
									Message message = handler.obtainMessage();
									message.what = 2;
									message.obj=count--;
									handler.sendMessage(message);
								}
								n2=System.currentTimeMillis()-n;
								Thread.sleep(1000);
							}
						}catch(Exception e){
							
						}
					}
				}).start();
			}else if(DataAssembleUtil.VERSION[0]==0x32){
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
						btn_comfirm.callOnClick();
					}
				}).start();
				
			}
		}
		//结束放开
		return mBaseView;
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
				
			}
		}
	};
}
