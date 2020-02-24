package com.dgm.dlyapp;

import java.util.List;

import com.dgm.fragment.AcDistributionFragment;
import com.dgm.fragment.BatteryFragment;
import com.dgm.fragment.DcDistributionFragment;
import com.dgm.fragment.DigitalSwitchFragment;
import com.dgm.fragment.PhotoVoltaicFragment;
//import com.dgm.fragment.PhotoVoltaicFragment;
import com.dgm.fragment.VDCDCFragment;
//import com.dgm.fragment.EnvironmentProtocolFragment;
import com.dgm.fragment.RectifierDistributionFragment;
import com.dgm.fragment.UniversalCommandFragment;
import com.dgm.fragment.MicroStationFragment;
import com.dgm.util.DataAssembleUtil;

import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class DevicesInfoActivity extends FragmentActivity {
	protected static final String TAG = "DevicesInfoActivity";
	private TextView commandTv,acTv,dcTv,rectifierTv,protocoTv,micro_cfgTv,vdcdcTv,digi_cfgTv,photoTv;
    private String mDeviceName;
    public static String mDeviceAddress;
    public static BluetoothLeService mBluetoothLeService;
    boolean connect_status_bit=false;
    
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
           // UniversalCommandFragment com=new UniversalCommandFragment();
            //com.sendMessageToBlueTooth(0);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

	@Override
	protected void onDestroy() {
	    try{
	    	DataAssembleUtil.FIRSTSET=0;
		    mBluetoothLeService.close();
		    unbindService(mServiceConnection);
		    super.onDestroy();
	    }catch(Exception e){
	    	
	    }
	}
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devices_scan_shiyan);
		Intent intent =getIntent();
		mDeviceName= intent.getStringExtra("name");
		mDeviceAddress= intent.getStringExtra("address");
		
		
		commandTv = (TextView) findViewById(R.id.universal_command);
		acTv = (TextView) findViewById(R.id.ac_distribution);
		dcTv = (TextView) findViewById(R.id.dc_distribution);
		rectifierTv = (TextView) findViewById(R.id.rectifier_distribution);
		protocoTv = (TextView) findViewById(R.id.environment_protocol);
		micro_cfgTv=(TextView) findViewById(R.id.micro_cfg);
		vdcdcTv=(TextView) findViewById(R.id.vdc_dc);
		photoTv=(TextView) findViewById(R.id.photoTv);
		digi_cfgTv=(TextView)findViewById(R.id.digi_cfg);

		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		boolean sg = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
		commandTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				commandTv.setTextColor(Color.parseColor("#FFFFFF"));
				commandTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setTextColor(Color.parseColor("#000000"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setTextColor(Color.parseColor("#000000"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				photoTv.setTextColor(Color.parseColor("#000000"));
				photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				UniversalCommandFragment umFragment = new UniversalCommandFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});
		commandTv.performClick();
		
		acTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataAssembleUtil.ISZORC==1){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
					return;
				}
				acTv.setTextColor(Color.parseColor("#FFFFFF"));
				acTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setTextColor(Color.parseColor("#000000"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setTextColor(Color.parseColor("#000000"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				photoTv.setTextColor(Color.parseColor("#000000"));
				photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				AcDistributionFragment umFragment = new AcDistributionFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});
		dcTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataAssembleUtil.ISZORC==1){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
					return;
				}
				dcTv.setTextColor(Color.parseColor("#FFFFFF"));
				dcTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setTextColor(Color.parseColor("#000000"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setTextColor(Color.parseColor("#000000"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				photoTv.setTextColor(Color.parseColor("#000000"));
				photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				DcDistributionFragment umFragment = new DcDistributionFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});

		rectifierTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataAssembleUtil.ISZORC==1){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
					return;
				}
				rectifierTv.setTextColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setTextColor(Color.parseColor("#000000"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setTextColor(Color.parseColor("#000000"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				photoTv.setTextColor(Color.parseColor("#000000"));
				photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				RectifierDistributionFragment umFragment = new RectifierDistributionFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});
		
		protocoTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataAssembleUtil.ISZORC==1){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
					return;
				}
				protocoTv.setTextColor(Color.parseColor("#FFFFFF"));
				protocoTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setTextColor(Color.parseColor("#000000"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setTextColor(Color.parseColor("#000000"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				photoTv.setTextColor(Color.parseColor("#000000"));
				photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				//EnvironmentProtocolFragment umFragment = new EnvironmentProtocolFragment();
				//ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				//2018-8-27 追加电池信息BatteryFragment
				BatteryFragment umFragment=new BatteryFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});
		micro_cfgTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataAssembleUtil.ISZORC==1){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
					return;
				}
				micro_cfgTv.setTextColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setTextColor(Color.parseColor("#000000"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				photoTv.setTextColor(Color.parseColor("#000000"));
				photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				MicroStationFragment umFragment = new MicroStationFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});
		vdcdcTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataAssembleUtil.ISZORC==1){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
					return;
				}
				vdcdcTv.setTextColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setTextColor(Color.parseColor("#000000"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				photoTv.setTextColor(Color.parseColor("#000000"));
				photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				VDCDCFragment umFragment = new VDCDCFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});
		photoTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataAssembleUtil.ISZORC==1){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
					return;
				}
				photoTv.setTextColor(Color.parseColor("#FFFFFF"));
				photoTv.setBackgroundColor(Color.parseColor("#00BBFF"));
				dcTv.setTextColor(Color.parseColor("#000000"));
				dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				acTv.setTextColor(Color.parseColor("#000000"));
				acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				commandTv.setTextColor(Color.parseColor("#000000"));
				commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				rectifierTv.setTextColor(Color.parseColor("#000000"));
				rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				protocoTv.setTextColor(Color.parseColor("#000000"));
				protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				micro_cfgTv.setTextColor(Color.parseColor("#000000"));
				micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				vdcdcTv.setTextColor(Color.parseColor("#000000"));
				vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				digi_cfgTv.setTextColor(Color.parseColor("#000000"));
				digi_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
				PhotoVoltaicFragment umFragment = new PhotoVoltaicFragment();
				ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
				ft.commit();
			}
		});
		digi_cfgTv.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(DataAssembleUtil.ISZORC==1){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.devicesToast), 1).show();//现在设置的是从机，不可使用此功能！
				return;
			}
			digi_cfgTv.setTextColor(Color.parseColor("#FFFFFF"));
			digi_cfgTv.setBackgroundColor(Color.parseColor("#00BBFF"));
			dcTv.setTextColor(Color.parseColor("#000000"));
			dcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			acTv.setTextColor(Color.parseColor("#000000"));
			acTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			commandTv.setTextColor(Color.parseColor("#000000"));
			commandTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			rectifierTv.setTextColor(Color.parseColor("#000000"));
			rectifierTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			protocoTv.setTextColor(Color.parseColor("#000000"));
			protocoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			micro_cfgTv.setTextColor(Color.parseColor("#000000"));
			micro_cfgTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			vdcdcTv.setTextColor(Color.parseColor("#000000"));
			vdcdcTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			photoTv.setTextColor(Color.parseColor("#000000"));
			photoTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			DigitalSwitchFragment umFragment = new DigitalSwitchFragment();
			ft.replace(R.id.fl_content, umFragment, DevicesInfoActivity.TAG);
			ft.commit();
		}
	});
	}

}
