package com.dgm.fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dgm.bean.UpdataInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.LoginActivity;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;
import com.dgm.util.DownLoadManager;
import com.dgm.util.UpdataInfoParser;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SystemSettingFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private LinearLayout switchp, data, version, logout;
	private String localVersion;
	private InputStream is;
	private String FILE = "saveUserNamePwd";// 用于保存SharedPreferences的文件
	private UpdataInfo info;
	private final int UPDATA_NONEED = 0;
	private final int UPDATA_CLIENT = 1;
	private final int GET_UNDATAINFO_ERROR = 2;
	private final int SDCARD_NOMOUNTED = 3;
	private final int DOWN_ERROR = 4;
	private DBManager dbMgr;

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
		mBaseView = inflater.inflate(R.layout.system_setting, null);
		mContext = getActivity();
		switchp = (LinearLayout) mBaseView.findViewById(R.id.switchp);
		data = (LinearLayout) mBaseView.findViewById(R.id.datainterface);
		version = (LinearLayout) mBaseView.findViewById(R.id.update);
		localVersion = getVersionName();// 获取app版本号
		logout = (LinearLayout) mBaseView.findViewById(R.id.logout);
		switchp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getApplicationContext(),
						getActivity().getResources().getString(R.string.sys_loading), Toast.LENGTH_LONG).show();//正在读取动环字典表，请稍后..
				new Thread() {
					@Override
					public void run() {
						String IP = "http://";
						dbMgr = new DBManager(getActivity());
						String IPPORT = dbMgr.queryInterface();
						String url = IP + IPPORT
								+ "/DlyAppSever/DeviceParamServlet";
						List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
						list.add(new BasicNameValuePair("", ""));

						try {
							String result = (String) HttpReq.topostData(url,
									list);
							JSONObject jsonObject;
							jsonObject = new JSONObject(result);
							String backMsg = jsonObject.getString("msg");
							String code = jsonObject.getString("code");
							if (code.equals("3")) {

							} else {
								if (backMsg.compareTo("success") == 0) {
									JSONArray jsonArray = jsonObject
											.getJSONArray("data");
									dbMgr.deleteDeviceParam();
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										String paramNum = jsonObjectSon
												.getString("paramNum");
										String paramName = jsonObjectSon
												.getString("paramName");
										String paramUseflag = jsonObjectSon
												.getString("paramUseFlag");
										String equipemntID = jsonObjectSon
												.getString("equipmentID");
										String paramType = jsonObjectSon
												.getString("paramType");
										String paramUnit = jsonObjectSon
												.getString("paramUnit");
										String paramDispored = jsonObjectSon
												.getString("paramDisporder");
						
										dbMgr.addDeviceParam(paramNum,
												paramName, equipemntID,
												paramUseflag, paramType,
												paramUnit, paramDispored);
									}

									Looper.prepare();
									Toast.makeText(
											getActivity()
													.getApplicationContext(),
											getActivity().getResources().getString(R.string.sys_success), Toast.LENGTH_LONG)//动环字典表更新成功！
											.show();
									Looper.loop();
								} else {
									Looper.prepare();
									Toast.makeText(
											getActivity()
													.getApplicationContext(),
											getActivity().getResources().getString(R.string.sys_fail), Toast.LENGTH_LONG)//动环字典表更新失败！
											.show();
									Looper.loop();
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				new Thread() {
					@Override
					public void run() {
						String IP = "http://";
						dbMgr = new DBManager(
								getActivity());
						String IPPORT = dbMgr
								.queryInterface();
						String url = IP
								+ IPPORT
								+ "/DlyAppSever/ParameterCheckServlet";
						List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
						list.add(new BasicNameValuePair(
								"", ""));

						try {
							String result = (String) HttpReq
									.topostData(
											url,
											list);
							JSONObject jsonObject;
							jsonObject = new JSONObject(
									result);
							String backMsg = jsonObject
									.getString("msg");
							String code = jsonObject
									.getString("code");
							if (code.equals("3")) {

							} else {
								if (backMsg
										.compareTo("success") == 0) {
									JSONArray jsonArray = jsonObject
											.getJSONArray("data");

										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(0);
										String Num = jsonObjectSon
												.getString("clumCount");
										
										String Time = jsonObjectSon
												.getString("updateTime");
										
										SharedPreferences.Editor edit = getActivity().getSharedPreferences(FILE, getActivity().MODE_PRIVATE)
												.edit();
										edit.putInt("count",Integer.parseInt(Num));
										edit.putString("updatetime", Time);
										edit.commit();
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				

			}

			
		});
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setMessage(getResources().getString(R.string.quitCommit));////确定要退出吗?
				builder.setPositiveButton(getResources().getString(R.string.yes),//确认
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								Intent intent = new Intent();
								intent.setClass(getActivity(),
										LoginActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 注意本行的FLAG设置
								startActivity(intent);
							}
						});

				builder.setNegativeButton(getResources().getString(R.string.cancel),//取消
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				builder.create().show();
			}
		});
		data.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Fragment newContent = new DataInterfaceFragment();
				String title = "datainterface";
				switchFragment(newContent, title);
			}
		});
		version.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CheckVersionTask cv = new CheckVersionTask();
				new Thread(cv).start();
			}

		});
		return mBaseView;
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

	public class CheckVersionTask implements Runnable {
		InputStream is;

		@Override
		public void run() {

			try {
				String IP = "http://";
				dbMgr = new DBManager(getActivity());
				String IPPORT = dbMgr.queryInterface();
				String ip = IP + IPPORT + "/DlyAppSever/version.xml";
				System.out.println("cccip" + ip);
				URL url = new URL(ip);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					// 从服务器获得一个输入流
					is = conn.getInputStream();
				}
				info = UpdataInfoParser.getUpdataInfo(is);
				if (info.getVersion().compareTo(localVersion) == 0) {
					Message msg = new Message();
					msg.what = UPDATA_NONEED;
					handler.sendMessage(msg);
					// LoginMain();
				} else {
					Message msg = new Message();
					msg.what = UPDATA_CLIENT;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				Message msg = new Message();
				msg.what = GET_UNDATAINFO_ERROR;
				handler.sendMessage(msg);
				e.printStackTrace();
			}

		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// super.handleMessage(msg);
			if (isNeedUpdate()) {
				showUpdataDialog();
			}
		}
	};

	/**
	 * 获取本地版本
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getVersionName() {
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageManager packageManager = getActivity().getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getActivity()
					.getPackageName(), 0);
			//Log.e("版本号", packInfo.versionName);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return getResources().getString(R.string.sys_verionunknow);//版本号未知
		}
	}

	private boolean isNeedUpdate() {
		if (info != null) {
			String v = info.getVersion(); // 最新版本的版本号
			if (v.equals(getVersionName())) {
				Toast toast = Toast.makeText(mContext, getResources().getString(R.string.sys_new),//已是最新版本！
						Toast.LENGTH_SHORT);
				// 显示toast信息
				toast.show();
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/*
	 * 
	 * 弹出对话框通知用户更新程序
	 * 
	 * 弹出对话框的步骤： 1.创建alertDialog的builder. 2.要给builder设置属性, 对话框的内容,样式,按钮
	 * 3.通过builder 创建一个对话框 4.对话框show()出来
	 */
	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new Builder(getActivity());
		builer.setTitle(getActivity().getResources().getString(R.string.sys_versionupdate));//版本升级
		builer.setMessage(info.getDescription());
		// 当点确定按钮时从服务器上下载 新的apk 然后安装 װ
		builer.setPositiveButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//确定
			public void onClick(DialogInterface dialog, int which) {
				downLoadApk();
			}
		});
		builer.setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// do sth
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
	}

	/*
	 * 从服务器中下载APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(getActivity());
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(getActivity().getResources().getString(R.string.sys_now));//正在下载更新
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(
							info.getUrl(), pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}.start();
	}

	// 安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		getFocus();
	}

	private void getFocus() {
		getView().setFocusable(true);
		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_BACK) {
					// 监听到返回按钮点击事件
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					startActivity(intent);

					return true;// 未处理
				}
				return false;
			}
		});
	}
}
