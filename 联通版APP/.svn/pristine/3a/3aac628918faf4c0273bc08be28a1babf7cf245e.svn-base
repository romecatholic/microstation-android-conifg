package com.dgm.dlyapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.dgm.fragment.AlarmFragment;
import com.dgm.fragment.AlarmNoConfirmFragment;
import com.dgm.fragment.DevicesScanFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class EquipDebugActivity extends FragmentActivity implements
		OnClickListener {
	// private LeDeviceListAdapter mLeDeviceListAdapter;
	private BluetoothAdapter mBluetoothAdapter;
	private boolean mScanning;
	private Handler mHandler;
	private Fragment mContent;
	private static final int REQUEST_ENABLE_BT = 1;
	// Stops scanning after 10 seconds.
	private static final long SCAN_PERIOD = 10000;

	private DeviceListAdapter mDevListAdapter;
	ToggleButton tb_on_off;
	TextView btn_searchDev;
	Button btn_aboutUs;
	ListView lv_bleList;

	Timer timer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equip_debug);
		// getActionBar().setTitle(R.string.title_devices);
		mHandler = new Handler();

		// Use this check to determine whether BLE is supported on the device.
		// Then you can
		// selectively disable BLE-related features.
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
					.show();
			finish();
		}

		// Initializes a Bluetooth adapter. For API level 18 and above, get a
		// reference to
		// BluetoothAdapter through BluetoothManager.
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		// 如果本地蓝牙没有开启，则开启
		if (!mBluetoothAdapter.isEnabled()) {
			//
			// 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，
			// 那么将会收到RESULT_OK的结果，
			// 如果RESULT_CANCELED则代表用户不愿意开启蓝牙
			// mBluetoothAdapter.enable();
//			Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//			startActivityForResult(mIntent, 1);
			//
			// 用enable()方法来开启，无需询问用户(实惠无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限。
			 mBluetoothAdapter.enable();
			// mBluetoothAdapter.disable();//关闭蓝牙
		}

		lv_bleList = (ListView) findViewById(R.id.lv_bleList);

		mDevListAdapter = new DeviceListAdapter();
		lv_bleList.setAdapter(mDevListAdapter);

		lv_bleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mDevListAdapter.getCount() > 0) {
					BluetoothDevice device1 = mDevListAdapter.getItem(position);
					if (device1 == null)
						return;
					Intent intent1 = new Intent(EquipDebugActivity.this,
							//DevicesInfoActivity.class);//20190219修改显示样式成网格状展示
							DevicesInfo2Activity.class);
					intent1.putExtra("name", device1.getName());
					intent1.putExtra("address", device1.getAddress());
					if (mScanning) {
						mBluetoothAdapter.stopLeScan(mLeScanCallback);
						mScanning = false;
					}
					startActivity(intent1);
				}
			}
		});
	}

	public static boolean turnOnBluetooth() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothAdapter != null) {
			return bluetoothAdapter.enable();
		}
		return false;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case 0:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		if (!mScanning) {
			menu.findItem(R.id.menu_stop).setVisible(false);
			menu.findItem(R.id.menu_scan).setVisible(true);
			menu.findItem(R.id.menu_refresh).setActionView(null);
		} else {
			menu.findItem(R.id.menu_stop).setVisible(true);
			menu.findItem(R.id.menu_scan).setVisible(false);
			menu.findItem(R.id.menu_refresh).setActionView(
					R.layout.actionbar_indeterminate_progress);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_scan:
			// mLeDeviceListAdapter.clear();
			scanLeDevice(true);
			// mDevListAdapter.;
			mDevListAdapter.clear();
			mDevListAdapter.notifyDataSetChanged();
			break;
		case R.id.menu_stop:
			scanLeDevice(false);
			break;
		}
		return true;
	}


	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					invalidateOptionsMenu();
				}
			}, SCAN_PERIOD);

			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
		invalidateOptionsMenu();
	}

	/*
	 * // Adapter for holding devices found through scanning. private class
	 * LeDeviceListAdapter extends BaseAdapter { private
	 * ArrayList<BluetoothDevice> mLeDevices; private LayoutInflater mInflator;
	 * 
	 * public LeDeviceListAdapter() { super(); mLeDevices = new
	 * ArrayList<BluetoothDevice>(); mInflator =
	 * DeviceScanActivity.this.getLayoutInflater(); }
	 * 
	 * public void addDevice(BluetoothDevice device) {
	 * if(!mLeDevices.contains(device)) { //String
	 * gg=device.getAddress().toString().trim(); mLeDevices.add(device); } }
	 * 
	 * public BluetoothDevice getDevice(int position) { return
	 * mLeDevices.get(position); }
	 * 
	 * public void clear() { mLeDevices.clear(); }
	 * 
	 * @Override public int getCount() { return mLeDevices.size(); }
	 * 
	 * @Override public Object getItem(int i) { return mLeDevices.get(i); }
	 * 
	 * @Override public long getItemId(int i) { return i; }
	 * 
	 * @Override public View getView(int i, View view, ViewGroup viewGroup) {
	 * ViewHolder viewHolder; // General ListView optimization code. if (view ==
	 * null) { view = mInflator.inflate(R.layout.listitem_device, null);
	 * viewHolder = new ViewHolder(); viewHolder.deviceAddress = (TextView)
	 * view.findViewById(R.id.device_address); viewHolder.deviceName =
	 * (TextView) view.findViewById(R.id.device_name); view.setTag(viewHolder);
	 * } else { viewHolder = (ViewHolder) view.getTag(); }
	 * 
	 * BluetoothDevice device = mLeDevices.get(i); final String deviceName =
	 * device.getName(); if (deviceName != null && deviceName.length() > 0)
	 * viewHolder.deviceName.setText(deviceName); else
	 * viewHolder.deviceName.setText(R.string.unknown_device);
	 * viewHolder.deviceAddress.setText(device.getAddress());
	 * 
	 * return view; } }
	 * 
	 * // Device scan callback. private BluetoothAdapter.LeScanCallback
	 * mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
	 * 
	 * @Override public void onLeScan(final BluetoothDevice device, int rssi,
	 * byte[] scanRecord) { runOnUiThread(new Runnable() {
	 * 
	 * @Override public void run() { //String
	 * gg=device.getAddress().toString().trim(); //Log.i("tag", gg);
	 * mLeDeviceListAdapter.addDevice(device);
	 * mLeDeviceListAdapter.notifyDataSetChanged(); } }); } };
	 * 
	 * static class ViewHolder { TextView deviceName; TextView deviceAddress; }
	 */

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(device.getName()!=null && device.getName().contains("DPC")){
						mDevListAdapter.addDevice(device);
						mDevListAdapter.notifyDataSetChanged();
					}
				}
			});
		}
	};

	@Override
	protected void onResume() {// 打开APP时扫描设备
		super.onResume();
		scanLeDevice(true);
	}

	@Override
	protected void onPause() {// 停止扫描
		super.onPause();
		scanLeDevice(false);
	}

	class DeviceListAdapter extends BaseAdapter {

		private List<BluetoothDevice> mBleArray;
		private ViewHolder viewHolder;

		public DeviceListAdapter() {
			mBleArray = new ArrayList<BluetoothDevice>();
		}

		public void addDevice(BluetoothDevice device) {
			if (!mBleArray.contains(device)) {
				mBleArray.add(device);
			}
		}

		public void clear() {
			mBleArray.clear();
		}

		@Override
		public int getCount() {
			return mBleArray.size();
		}

		@Override
		public BluetoothDevice getItem(int position) {
			return mBleArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(EquipDebugActivity.this)
						.inflate(R.layout.listitem_device, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_devName = (TextView) convertView
						.findViewById(R.id.device_name);
				viewHolder.tv_devAddress = (TextView) convertView
						.findViewById(R.id.device_address);
				convertView.setTag(viewHolder);
			} else {
				convertView.getTag();
			}

			// add-Parameters
			BluetoothDevice device = mBleArray.get(position);
			String devName = device.getName();
			if (devName != null && devName.length() > 0) {
				viewHolder.tv_devName.setText(devName);
			} else {
				viewHolder.tv_devName.setText(getResources().getString(R.string.unknown_device));//未知设备
			}
			viewHolder.tv_devAddress.setText(device.getAddress());

			return convertView;
		}

	}

	class ViewHolder {
		TextView tv_devName, tv_devAddress;
	}

}