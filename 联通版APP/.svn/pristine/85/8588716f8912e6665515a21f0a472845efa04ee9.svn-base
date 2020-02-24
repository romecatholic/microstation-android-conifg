package com.dgm.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

public class DataInterfaceFragment extends Fragment {
	private View mBaseView;
	private Context mContext;
	private ProgressDialog pd;
	private TextView text;
	private Button setting;
	private EditText dataurl, dataport;
	private DBManager dbMgr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		getFocus();
	}

	private void getFocus() {
		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_BACK) {
					// 监听到返回按钮点击事件
					Fragment newContent = new SystemSettingFragment();
					String title = "setting";
					switchFragment(newContent, title);

					return true;// 未处理
				}
				return false;
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mBaseView = inflater.inflate(R.layout.data_interface, null);
		dataurl = (EditText) mBaseView.findViewById(R.id.data_ip);
		dataport = (EditText) mBaseView.findViewById(R.id.data_port);
		setting = (Button) mBaseView.findViewById(R.id.update_url);
		dbMgr = new DBManager(getActivity());
		String url = dbMgr.queryInterface();
		String[] url2 = url.split(":");
		if (url2.length != 0) {
			dataurl.setText(url2[0]);
			dataport.setText(url2[1]);
		}
		dataurl.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(dataurl.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});
		dataport.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(dataport.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});
		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String ipchar = dataurl.getText().toString();
				String portchar = dataport.getText().toString();

				if (ipchar.length() > 7 && portchar.length() > 0) {
					dbMgr.updateInterface(ipchar, portchar);
					Toast toast = Toast.makeText(getActivity()
							.getApplicationContext(), getResources().getString(R.string.dataInterfaceOK),//设置数据接口成功！
							Toast.LENGTH_LONG);
					toast.show();
					Fragment newContent = new SystemSettingFragment();
					String title = "setting";
					switchFragment(newContent, title);

				} else {
					Toast toast = Toast.makeText(getActivity()
							.getApplicationContext(), getResources().getString(R.string.checkIPPORT),//请输入正确的IP地址及端口号！
							Toast.LENGTH_LONG);
					toast.show();
				}
			}

		});
		return mBaseView;
	}

	private void regetFocus() {
		Fragment newContent = new SystemSettingFragment();
		String title = "setting";
		switchFragment(newContent, title);

	}

	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchConent(fragment, title);
		}
	}
}
