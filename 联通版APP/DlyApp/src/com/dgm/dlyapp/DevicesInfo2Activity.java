package com.dgm.dlyapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dgm.fragment.AcDistributionFragment;
import com.dgm.fragment.BatteryFragment;
import com.dgm.fragment.DcDistributionFragment;
import com.dgm.fragment.DigitalSwitchFragment;
import com.dgm.fragment.MicroStationCUCCFragment;
import com.dgm.fragment.MicroStationFragment;
import com.dgm.fragment.PhotoVoltaicFragment;
import com.dgm.fragment.RectifierDistributionFragment;
import com.dgm.fragment.ShiyanFragment;
import com.dgm.fragment.UniversalCommandFragment;
import com.dgm.fragment.VDCDCFragment;
import com.dgm.util.DataAssembleUtil;

public class DevicesInfo2Activity extends FragmentActivity {
    protected static final String TAG = "DevicesInfo2Activity";
	private String mDeviceName;
    public static String titles;
    public static String mDeviceAddress;
    public static BluetoothLeService mBluetoothLeService;
    boolean connect_status_bit=false;
    private LinearLayout dsf_ll;
    private TextView ll_univer;
    
	/*private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
            if(msg.what==0){
            	Fragment newContent = new UniversalCommandFragment();
				String title = getResources().getString(R.string.universal);//通用命令
				switchFragment(newContent, title);
            }
        };

	};*/
    
 // references to our images
   /* private Integer[] mThumbIds = {
            R.drawable.actions_universal, R.drawable.actions_acdis,
            R.drawable.actions_dcdis, R.drawable.actions_rectifier,
            R.drawable.actions_bat, R.drawable.actions_dcdc,
            R.drawable.actions_sun, R.drawable.actions_micro,
            R.drawable.actions_about,R.drawable.actions_booktag
    };*/
    
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
    		//2019年9月24日删除密码样式
            //UniversalCommandFragment com=new UniversalCommandFragment();
            //com.sendMessageToBlueTooth(0);
            //删掉这两行
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devices_scan_shiyan);
		Intent intent =getIntent();
		mDeviceName= intent.getStringExtra("name");
		mDeviceAddress= intent.getStringExtra("address");
		
		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		boolean sg = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

		//2019年9月24日删除密码样式
		DataAssembleUtil.isSendPass=false;
		switchFragment("first");
	}

	public void switchFragment(String title) {
		// TODO Auto-generated method stub
		titles=title;
		if(title.equals("first")){
			ShiyanFragment s=new ShiyanFragment();
			s.setDia(this);
			Fragment newContent = s;
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
			
		}else if(title.equals("universal")){
			
			Fragment newContent = new UniversalCommandFragment();
			title = getResources().getString(R.string.universal);//通用命令
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("acdistribution")){
			
			Fragment newContent = new AcDistributionFragment();
			title = getResources().getString(R.string.acdistribution);//交流分配器
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("dcdistribution")){
			
			Fragment newContent = new DcDistributionFragment();
			title = getResources().getString(R.string.dcdistribution);//直流分配器
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("rectifier")){
			
			Fragment newContent = new RectifierDistributionFragment();
			title = getResources().getString(R.string.rectifier);//整流配电
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("batteryinfo")){
			
			Fragment newContent = new BatteryFragment();
			title = getResources().getString(R.string.batteryinfo);//锂电信息
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("dcdc")){
			
			Fragment newContent = new VDCDCFragment();
			title = getResources().getString(R.string.dcdc);//dcdc
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("photo")){
			
			Fragment newContent = new PhotoVoltaicFragment();
			title = getResources().getString(R.string.photo);//光伏汇流板
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("micro")){
			
			Fragment newContent = new MicroStationFragment();
			title = getResources().getString(R.string.micro);//微站设置
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("digitalfragment")){
			
			Fragment newContent = new DigitalSwitchFragment();
			title = getResources().getString(R.string.digitalfragment);//电子开关
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}else if(title.equals("fivefragment")){
			
			Fragment newContent = new MicroStationCUCCFragment();
			title = getResources().getString(R.string.fiveg);//5G配置
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out);
			ft.replace(R.id.fl_content2, newContent, DevicesInfo2Activity.TAG);
			ft.commit();
		}
	}
	
	@Override
	protected void onDestroy() {
	    DataAssembleUtil.FIRSTSET=0;
	    mBluetoothLeService.disconnect();
	    mBluetoothLeService.close();
	    unbindService(mServiceConnection);
	    super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(!titles.equals("first")){
			switchFragment("first");
		}else{
			super.onBackPressed();
		}
	}
	
}
