package com.dgm.dlyapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.db.DBManager;
import com.dgm.http.HttpReq;
import com.dgm.util.DataAssembleUtil;

public class LoginActivity extends Activity {

	static String YES = "yes";
	static String NO = "no";
	private String FILE = "saveUserNamePwd";// ���ڱ���SharedPreferences���ļ�
	private View settingView;
	private CheckBox cbRememberPsw;
	private Context mContext;
	private RelativeLayout rl_user;
	private LinearLayout settingLinearLayout;
	private Button mLogin;
	private Thread thread;
	private EditText etLoginName;
	private EditText etPassWord;
	private EditText ip;
	private EditText port;
	private TextView setting,ver, equip;//2019
	private LayoutInflater mLayoutInflater;
	private Spinner spinner;
	private List<String> itemlists=new ArrayList<String>();
	String Username = "";
	String password = "";
	String IP = "http://";
	private static final String IP2 = "http://192.168.10.218:8080/DlyAppSever/DeviceParamServlet";

	private DBManager dbMgr;
    private ArrayAdapter<String> spinnerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		dbMgr = new DBManager(this);
		findView();
		init();
		isFirstLogin();
		IsFirstApp();
		
	}

	private void findView() {
		rl_user = (RelativeLayout) findViewById(R.id.rl_user);
		etLoginName = (EditText) findViewById(R.id.account);
		etPassWord = (EditText) findViewById(R.id.password);
		mLogin = (Button) findViewById(R.id.login);
		setting = (TextView) findViewById(R.id.setting_interface);
		ver=(TextView)findViewById(R.id.v_code);
		spinner=(Spinner)findViewById(R.id.sp_protocol);//Э��汾��
        itemlists.add("2.0");//˫�Ǻͺ��Ĵ�
        itemlists.add("1.0");
		spinnerAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,itemlists);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner.setAdapter(spinnerAdapter);
		//2019
		equip = (TextView) findViewById(R.id.equip_debug);
		cbRememberPsw = (CheckBox) findViewById(R.id.rememberPw);
		output();// �ս������ȡһ�Σ�������ǰ״̬
		
		try {
			PackageInfo info=this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
			ver.setText(info.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	private void isFirstLogin(){
		int count = dbMgr.queryInterfaceCount();
		if(count!=0){
			new Thread() {
				@Override
				public void run() {
					String IP = "http://";
					dbMgr = new DBManager(
							getApplicationContext());
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
									
//									Time=Time.substring(0, Time.length()-3);
									
									SharedPreferences shared = getSharedPreferences(FILE, MODE_PRIVATE);
									// ��һ���������ǹؼ��֣��ڶ�������ΪĬ��ֵ����˼��˵���û�ҵ�ֵ����Ĭ��ֵ����
									String sysNum = ""+shared.getInt("count", 0);// ͬ�ϣ���û�ҵ�������Ϊ��""
									String sysTime = shared.getString("updatetime", "");// ͬ�ϣ���û�ҵ�������Ϊ��""
									System.out.println(Time);
									if(Num.equals(sysNum)&&Time.equals(sysTime)){
										
									}else{
										SharedPreferences.Editor edit = getSharedPreferences(FILE, MODE_PRIVATE)
												.edit();
										edit.putInt("count",Integer.parseInt(Num));
										edit.putString("updatetime", Time);
										edit.commit();
										new Thread() {
											@Override
											public void run() {
												String IP = "http://";
												dbMgr = new DBManager(getApplicationContext());
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
																	getApplicationContext(),
																	getResources().getString(R.string.updateDoneToast), Toast.LENGTH_LONG)//��⵽�����ֵ�����,�Ѹ�����ɣ�
																	.show();
															Looper.loop();
														} else {
															Looper.prepare();
															Toast.makeText(getApplicationContext(),
																	getResources().getString(R.string.updateUnDoneToast), Toast.LENGTH_LONG)//��⵽�����ֵ�����,������ʧ��,���ֶ����ã�
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
									}
									

								
							} else {
								
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated
						// catch block
						e.printStackTrace();
					}
				}
			}.start();
		}else {
			System.out.println("diyici");
		}
	}

	private void init() {
		Animation anim = AnimationUtils.loadAnimation(mContext,
				R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
		// Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		// startActivity(intent);
		mLogin.setOnClickListener(loginOnClickListener);
		setting.setOnClickListener(settingOnclickListener);
		//2019
		equip.setOnClickListener(equipdebugOnclickListener);
	}
	//2019
	private OnClickListener equipdebugOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//2019
			DataAssembleUtil.setPriviledge=true;
			Intent intent = new Intent(LoginActivity.this,
					EquipDebugActivity.class);
			//Intent intent=new Intent(LoginActivity.this,FileSelectorActivity.class);
			if(spinner.getSelectedItemId()==0){
				DataAssembleUtil.VERSION[0]=0x32;
				DataAssembleUtil.VERSION[1]=0x30;
			}else if(spinner.getSelectedItemId()==1){
				DataAssembleUtil.VERSION[0]=0x31;
				DataAssembleUtil.VERSION[1]=0x30;
			}
			startActivity(intent);
		}
	};
	private OnClickListener settingOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			settingView = (LinearLayout) getLayoutInflater().inflate(
					R.layout.setting_data_interface, null);
			ip = (EditText) settingView.findViewById(R.id.setting_ip);
			port = (EditText) settingView.findViewById(R.id.setting_port);
			int count = dbMgr.queryInterfaceCount();
			if (count != 0) {
				String url = dbMgr.queryInterface();
				String[] url2 = url.split(":");
				if (url2.length != 0) {
					ip.setText(url2[0]);
					port.setText(url2[1]);
				}
			}
			new AlertDialog.Builder(LoginActivity.this)
					.setView(settingView)
					.setPositiveButton(getResources().getString(R.string.ac_set),//����
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {

									String ipchar = ip.getText().toString();
									String portchar = port.getText().toString();
									int count = dbMgr.queryInterfaceCount();
									System.out.println("cc" + ipchar);
									if (count == 0) {
										if (ipchar.length() > 7
												&& portchar.length() > 0) {
											dbMgr.settingInterfaceUrl(ipchar,
													portchar);
											Toast toast = Toast.makeText(
													mContext, getResources().getString(R.string.dataInterfaceOK),//�������ݽӿڳɹ���
													Toast.LENGTH_LONG);
											toast.show();
											new Thread() {
												@Override
												public void run() {
													String IP = "http://";
													dbMgr = new DBManager(
															getApplicationContext());
													String IPPORT = dbMgr
															.queryInterface();
													String url = IP
															+ IPPORT
															+ "/DlyAppSever/DeviceParamServlet";
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
																dbMgr.deleteDeviceParam();
																SharedPreferences.Editor edit = getSharedPreferences(FILE, MODE_PRIVATE)
																		.edit();
																SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
																edit.putInt("count", jsonArray
																		.length());
																edit.putString("updatetime", df.format(new Date()));
																edit.commit();
																for (int i = 0; i < jsonArray
																		.length(); i++) {
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

																	dbMgr.addDeviceParam(
																			paramNum,
																			paramName,
																			equipemntID,
																			paramUseflag,
																			paramType,
																			paramUnit,
																			paramDispored);
																}

																Looper.prepare();
																Toast.makeText(
																		getApplicationContext()
																				.getApplicationContext(),
																		getResources().getString(R.string.dictionaryOk),//���ö����ֵ��ɹ���
																		Toast.LENGTH_LONG)
																		.show();
																Looper.loop();
															} else {
																Looper.prepare();
																Toast.makeText(
																		getApplicationContext()
																				.getApplicationContext(),
																		getResources().getString(R.string.dictionaryError),//���ö����ֵ��ʧ�ܣ�
																		Toast.LENGTH_LONG)
																		.show();
																Looper.loop();
															}
														}
													} catch (JSONException e) {
														// TODO Auto-generated
														// catch block
														e.printStackTrace();
													}
												}
											}.start();

										} else {
											Toast toast = Toast.makeText(
													mContext,
													getResources().getString(R.string.checkIPPORT),//��������ȷ��IP��ַ���˿ںţ�
													Toast.LENGTH_LONG);
											toast.show();
										}
									} else {
										if (ipchar.length() > 7
												&& portchar.length() > 0) {
											dbMgr.updateInterface(ipchar,
													portchar);
											Toast toast = Toast.makeText(
													mContext, getResources().getString(R.string.interfaceOK),//�������ݽӿڳɹ���
													Toast.LENGTH_LONG);
											toast.show();
										} else {
											Toast toast = Toast.makeText(
													mContext,
													getResources().getString(R.string.corrIPPORT),//��������ȷ��IP��ַ���˿ںţ�
													Toast.LENGTH_LONG);
											toast.show();
										}
									}
								}
							}).setNegativeButton(getResources().getString(R.string.cancel), null)//ȡ��
					.setCancelable(false).show();

		}
	};
	private OnClickListener loginOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int count = dbMgr.queryInterfaceCount();
			if (count != 0) {
				remenberPsd();
				thread = new Thread(new loginRunnable());
				thread.start();
			} else {
				Toast.makeText(mContext, getResources().getString(R.string.firstsetInterface), Toast.LENGTH_SHORT)//�״ε�½���������ݽӿڣ�
						.show();
			}

		}
	};
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Object object = msg.obj;
			// �������json���� �� ʵ�� activity ��ת ���ݴ��䵽��һ��ҳ��
			if (object.toString().compareTo("404") == 0) {
				Toast.makeText(mContext, getResources().getString(R.string.checkdataRequestError), Toast.LENGTH_SHORT)//�ӿ����������쳣��
						.show();
			} else {
				try {
					JSONObject jsonObject = new JSONObject(object.toString());
					String backMsg = jsonObject.getString("msg");
					if (backMsg.compareTo("success") == 0) {
						String userJson = jsonObject.getString("data");
						JSONObject jsonObject2 = new JSONObject(userJson);
						// ����json���ݽ�������ת����ҳ
						String userid = jsonObject2.getString("name");
						String purviewId = jsonObject2.getString("purviewId");
						String areaId = jsonObject2.getString("areaId");
						String userName = jsonObject2.getString("userName");
						String companyid = jsonObject2.getString("companyId");
						Log.e("", userid + areaId);
						dbMgr.addorUpdateUser(userid, purviewId, areaId,
								userName, companyid);
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						if(true){//����Ȩ�ޣ���Ȩ����ɿ�
							DataAssembleUtil.setPriviledge=true;
						}
						Bundle bundle = new Bundle();
						bundle.putString("userId", userid);
						intent.putExtras(bundle);
						startActivity(intent);
					} else {
						// �û��������¼ʧ��
						// �û��������ڣ������������ʱ������failed
						Toast.makeText(mContext, getResources().getString(R.string.permission),//��¼ʧ��,�����û������룡
								Toast.LENGTH_SHORT).show();
						etPassWord.setText("");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

	};

	class loginRunnable implements Runnable {

		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			int count = dbMgr.queryInterfaceCount();

			IP = "http://";
			if (count != 0) {

				String IPPORT = dbMgr.queryInterface();
				IP += IPPORT;
			}
			String url = IP + "/DlyAppSever/LoginServlet";
			String name = etLoginName.getText().toString();
			String pasd = etPassWord.getText().toString();
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			list.add(new BasicNameValuePair("account", name));
			list.add(new BasicNameValuePair("password", pasd));
			try {
				Object object = HttpReq.topostData(url, list);

				Message message = handler.obtainMessage();
				message.obj = object;
				message.what = 1;
				handler.sendMessage(message);
			} catch (Exception e) {
				// TODO: handle exception
				Looper.prepare();
				Toast.makeText(mContext, getResources().getString(R.string.dataInterfaceError), Toast.LENGTH_LONG)//���ݽӿ�ͨѶ�쳣������ʧ�ܣ�
						.show();
				Looper.loop();
			}

		}
	}

	/**
	 * ȡ��ס���뱣�������
	 */
	private void output() {
		// ��һ���������ļ������ڶ���������ģʽ�������׿���ȥ��ϰһ��SharedPreferences��֪ʶ��
		SharedPreferences shared = getSharedPreferences(FILE, MODE_PRIVATE);
		// ��һ���������ǹؼ��֣��ڶ�������ΪĬ��ֵ����˼��˵���û�ҵ�ֵ����Ĭ��ֵ����
		String name1 = shared.getString("name", "");// ͬ�ϣ���û�ҵ�������Ϊ��""
		String psd1 = shared.getString("psd", "");
		boolean ischecked1 = shared.getBoolean("isChecked", false);
		etLoginName.setText(name1);
		etPassWord.setText(psd1);
		cbRememberPsw.setChecked(ischecked1);
	}

	/**
	 * ѡ���ס����󣬱����û�����������Ϣ
	 */
	private void remenberPsd() {
		// ��һ���������ļ������ڶ���������ģʽ�������׿���ȥ��ϰһ��SharedPreferences��֪ʶ��
		SharedPreferences.Editor edit = getSharedPreferences(FILE, MODE_PRIVATE)
				.edit();
		// �ж�ѡ����״̬ ��ѡ��isChecked�򡭡�
		if (cbRememberPsw.isChecked()) {
			edit.putString("name", etLoginName.getText().toString());
			edit.putString("psd", etPassWord.getText().toString());
			edit.putBoolean("isChecked", true);
		} else {
			// edit.clear(); //��ѡ��ȫ������ͱ������д��룬ע����������
			edit.putString("name", etLoginName.getText().toString());// ֻ���û���
			edit.putString("psd", "");
			edit.putBoolean("isChecked", false);
		}
		edit.commit();
	}

	public void IsFirstApp() {

		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo("com.dgm.dlyapp", 0);
			int currentVersion = info.versionCode;
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			int lastVersion = prefs.getInt("com.dgm.dlyapp", 0);
			if (currentVersion > lastVersion) {
				Log.e("", getResources().getString(R.string.firstLogin) + currentVersion + "@" + lastVersion + "#");//��һ������APP
				// �����ǰ�汾�����ϴΰ汾���ð汾���ڵ�һ������
				// ����ǰ�汾д��preference�У����´�������ʱ�򣬾ݴ��жϣ�����Ϊ�״�����
				prefs.edit().putInt("com.dgm.dlyapp", currentVersion).commit();
				dbMgr.deleteDeviceParam();
				new LoadDataAsyncTask().execute();
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... arg0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			list.add(new BasicNameValuePair("", ""));
			String backString = (String) HttpReq.topostData(IP2, list);
			return backString;
		}

		/**
		 * ���ʱ�ķ���
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				JSONAnalysis(result);
			}
		}

	}

	public void JSONAnalysis(String result) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String backMsg = jsonObject.getString("msg");
			String code = jsonObject.getString("code");
			if (code.equals("3")) {
				Toast.makeText(LoginActivity.this, getResources().getString(R.string.loadError),//���ص�Դ����ʧ�ܣ�
						Toast.LENGTH_LONG).show();
			} else {
				if (backMsg.compareTo("success") == 0) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObjectSon = (JSONObject) jsonArray
								.opt(i);
						String paramNum = jsonObjectSon.getString("paramNum");
						String paramName = jsonObjectSon.getString("paramName");
						String paramUseflag = jsonObjectSon
								.getString("paramUseFlag");
						String equipemntID = jsonObjectSon
								.getString("equipmentID");
						String paramType = jsonObjectSon.getString("paramType");
						String paramUnit = jsonObjectSon.getString("paramUnit");
						String paramDispored = jsonObjectSon
								.getString("paramDisporder");
						dbMgr.addDeviceParam(paramNum, paramName, equipemntID,
								paramUseflag, paramType, paramUnit,
								paramDispored);

					}
				} else {
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.loadError),//���ص�Դ����ʧ�ܣ�
							Toast.LENGTH_LONG).show();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
