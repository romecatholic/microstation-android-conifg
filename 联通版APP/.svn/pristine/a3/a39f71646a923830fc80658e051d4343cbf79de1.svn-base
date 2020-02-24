package com.dgm.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dgm.aes.AES;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.FileSelectorActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;
import com.dgm.util.DataAssembleUtil;

public class UniversalCommandFragment extends Fragment {

	private View mBaseView;
	@SuppressWarnings("unused")
	private LinearLayout switchp, data, version, logout;
	boolean connect_status_bit = false;
	private TextView mConnectionState;
	private Button readTime, setTime,  getArea, getEquip,setArea,getSysState,setzcbtn,getzcbtn,
	btn_comfirm,btn_cancel,btn_replay,ucf_pos,getposfromrec,setposfromrec;//,selectfile,sendtoblue;//2018-12-07 ׷��getSysState
	private EditText timeText, addressText,companyText,sysBatStateText,inputEditText
	,ucf_longitude,ucf_latitude;//,filename;//2018-12-07 ׷��sysBatStateText
	private LinearLayout ucf;
	private RadioButton zr,cr;
	private View dialogview;//�Զ���һ��dialog
	public String sendCMD = "";
	public int countNum=0;
	private long n=0;
	private DataAssembleUtil dau;
	private AlertDialog inputDialog;
	
	private double latitude,longitude;
	LocationClient mLocClient;
	public MyLocationListener myListener = new MyLocationListener();
	
	/*private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
            if(msg.what==0){
            	Object object = msg.obj;
            	try {
					JSONObject jsonObject = new JSONObject(object.toString());
					String backMsg = jsonObject.getString("msg");
					if (backMsg.compareTo("success") == 0) {
						inputDialog.show();
						btn_comfirm=(Button)dialogview.findViewById(R.id.btn_comfirm);//ȷ����ť
						btn_comfirm.setOnClickListener(new View.OnClickListener() {
						   	 @Override
						   	 public void onClick(View v) {
						   		 String check=inputEditText.getText().toString();
						   		 if(check.compareTo("111111")==0){
						   			 DataAssembleUtil.FIRSTSET=2;
						             sendMessageToBlueTooth(0);
									 Toast.makeText(mBaseView.getContext(), getResources().getString(R.string.checksuc), 1).show();//��֤�ɹ�
									 inputDialog.dismiss();
						         }else{
						           	Toast.makeText(mBaseView.getContext(), getResources().getString(R.string.checkfal), 1).show();//������֤��������������
						           	//inputDialog
						         }
						   	 }
					    });
						btn_cancel=(Button)dialogview.findViewById(R.id.btn_cancel);//ȡ��ť
						btn_cancel.setOnClickListener(new View.OnClickListener() {
							@Override
						   	public void onClick(View v) {
								inputDialog.cancel();
						    }
					    });
						btn_replay=(Button)dialogview.findViewById(R.id.btn_reply);//�ط���ť
						btn_replay.setOnClickListener(new View.OnClickListener() {
							@Override
						   	public void onClick(View v) {
								replaycheck();
						    }
					    });
						btn_replay.setEnabled(false);
						btn_replay.setText(getResources().getString(R.string.replay)+"(60)");
					} else {
						DataAssembleUtil.FIRSTSET=0;//����
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }else if(msg.what==1){
            	try{
            		Toast.makeText(getActivity(), getResources().getString(R.string.overtime), 1).show();//����������Ӧ
//            		btn_cancel=null;
//            		btn_comfirm=null;
//            		btn_replay=null;
//            		inputDialog.dismiss();
            		btn_replay.setEnabled(true);
            		btn_replay.setText(getResources().getString(R.string.replay));
            	}catch(IllegalStateException e){
            		btn_cancel=null;
            		btn_comfirm=null;
            		btn_replay=null;
            		inputDialog.dismiss();	
            	}
            }else if(msg.what==2){
            	if(btn_replay!=null){
            		btn_replay.setText(getResources().getString(R.string.replay)+"("+msg.obj+")");
            	}
            }
        };

	};*/
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
				connect_status_bit = true;// 
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
					getDeviceID();
				}catch(NullPointerException e){
					
				}catch(ConcurrentModificationException e){
					
				}
				//getDeviceID();
				//getISZORC();
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				displayData(intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA));
			}
		}
	};

	
	/*private void writeFile(String data){
		File picDir=this.getView().getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		Log.e("TAG", picDir.getPath());
		File file=new File(picDir,"file01.txt");
		OutputStream out=null;
		try {
			out=new FileOutputStream(file,true);
			out.write((this.getClass().toString()+":"+data+"\r\n").getBytes());
			Toast.makeText(this.getActivity(), "����OK!", 1).show();
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

	/**
	 * �����������
	 * 
	 * @param data1
	 */
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
					String cmd = recString.substring(index7E + 2, index0D);
					byte[] b = dau.hex2byte(cmd);// ȥ��7E,0D��תbyte����
					boolean ifTrue = dau.ifCheckSum(cmd);// �жϽ��յ����У��λ��������ݼ���õ���У��λ�Ƿ�һ��
					if (ifTrue) {
						byte[] byteData = { b[9], b[10], b[11] };
						String dataLength = new String(byteData);
						int intLength = Integer.parseInt(dataLength, 16);
						byte[] b2 = new byte[intLength];
						if (b.length > 12 + intLength) {
							for (int j = 0; j < intLength; j++) {
								b2[j] = b[j + 12];
							}
							if (sendCMD.compareTo("4D") == 0) {// �յ���ȡʱ��ָ��ظ�
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								byte[] yearByte = new byte[] { bb[0], bb[1] };
								int yearString = ((yearByte[1]) & 0xFF | (yearByte[0] << 8) & 0xFF00);
								int mString = bb[2];
								int dString = bb[3];
								int hString = bb[4];
								int miString = bb[5];
								int sString = bb[6];
								String timeString = yearString + "-" + mString
										+ "-" + dString + " " + hString + ":"
										+ miString + ":" + sString;
								timeText.setText(timeString);
							} else if (sendCMD.compareTo("50") == 0) {// �յ���ȡ�豸��ַָ��
								byte []add={b[2],b[3]};
								DataAssembleUtil.ADDR[0]=add[0];
								DataAssembleUtil.ADDR[1]=add[1];
								String address = new String(add);
								int intAddress = Integer.parseInt(address, 16);
								if(addressText!=null){
									addressText.setText(intAddress+"");
								}
							} else if (sendCMD.compareTo("51") == 0) {// �յ���ȡ�豸������Ϣָ��
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length>30){
									byte [] name=new byte[10];
									for (int i = 0; i < 10; i++) {
										name[i]=bb[i];
									}
									String nameString = new String(name);
									byte[] companyVersion={bb[10],bb[11]};
									String VersionString = new String(companyVersion);
									byte [] ompanyName=new byte [20];
									for (int i = 0; i < 20; i++) {
										ompanyName[i]=bb[i+12];
									}
									String companyString = new String(ompanyName);
								}
							}else if(sendCMD.compareTo("81")==0){
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
							
								int batstate=bb[6];//���״̬
								if (batstate == 00) {
									sysBatStateText.setText(getResources().getString(R.string.equalizing));//���
								} else if (batstate == 01) {
									sysBatStateText.setText(getResources().getString(R.string.floating));//����
								} else if (batstate == 02) {
									sysBatStateText.setText(getResources().getString(R.string.discharge));//�ŵ�
								} else if (batstate == 03) {
									sysBatStateText.setText(getResources().getString(R.string.test));//����
								} else if (batstate == 04) {
									sysBatStateText.setText(getResources().getString(R.string.uninstall));//δ��
								} else if (batstate == 06) {
									sysBatStateText.setText(getResources().getString(R.string.protect));//����
								} else{
									sysBatStateText.setText(getResources().getString(R.string.undefine));//δ����
								}
								
							}else if(sendCMD.compareTo("92")==0){
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								if(bb.length<=0){
									zr.setChecked(true);
									DataAssembleUtil.ISZORC=0;//����Ϊ��
									return;
								}
								int batstate=bb[30];//����
								if (batstate == 00) {
									zr.setChecked(true);
									DataAssembleUtil.ISZORC=0;//����Ϊ��
								} else{
									cr.setChecked(true);
									DataAssembleUtil.ISZORC=1;//����Ϊ��
								}
							}else if(sendCMD.compareTo("47")==0){
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								int num=bb[0];//��·����
								int start=1;
				            	for(int j=0;j<num;j++){
				            		start=start+1;
				            	}

				            	for(int j=0;j<num;j++){
				            		start=start+4;
				            	}
				            	//byte[] labyte={bb[start+4],bb[start+5],bb[start+6],bb[start+7],bb[start+8]};
				            	if(bb[start]==0x01){
				            		ucf_latitude.setText("-"+bb[start+1]+"."+bb[start+2]+bb[start+3]+bb[start+4]);
				            	}else{
				            		ucf_latitude.setText(bb[start+1]+"."+bb[start+2]+bb[start+3]+bb[start+4]);
				            	}
				            	if(bb[start+5]==0x01){
				            		ucf_longitude.setText("-"+bb[start+6]+"."+bb[start+7]+bb[start+8]+bb[start+9]);
				            	}else{
				            		ucf_longitude.setText(bb[start+6]+"."+bb[start+7]+bb[start+8]+bb[start+9]);
				            	}
				            	
							}
						} else {
							Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 1000).show();// toast��ʾ1000ms	�յ���ݳ����쳣
						}
					} else {
						Toast.makeText(getActivity(),getResources().getString(R.string.acToast5), 1000).show();// toast��ʾ1000ms	�յ����У��ʧ�ܣ�
					}
					recString = "";
					sendCMD = "";
					/*
					 * }else if(sendCMD.length()<=0){ recString=""; sendCMD="";
				 	* return; }
				 	*/
					
				}
			}
		}catch(Exception e){
			
		}

	}

	@Override
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
		dau = new DataAssembleUtil();
		setHasOptionsMenu(true);
		mBaseView = inflater.inflate(R.layout.universal_command_fragment, null);
		readTime = (Button) mBaseView.findViewById(R.id.getunicommondtime);
		setTime = (Button) mBaseView.findViewById(R.id.setunicommondtime);
		getArea = (Button) mBaseView.findViewById(R.id.getuniarea);
		setArea = (Button) mBaseView.findViewById(R.id.getuniareaset);
		getEquip = (Button) mBaseView.findViewById(R.id.getequipcompany);
		getSysState=(Button) mBaseView.findViewById(R.id.getsysState);
		timeText = (EditText) mBaseView.findViewById(R.id.timeText);
		addressText = (EditText) mBaseView.findViewById(R.id.unequipareaText);
		companyText=(EditText) mBaseView.findViewById(R.id.uncompanynameText);
		sysBatStateText=(EditText) mBaseView.findViewById(R.id.sysBatStateText);
		//filename=(EditText)mBaseView.findViewById(R.id.fileName);
		setzcbtn=(Button)mBaseView.findViewById(R.id.vdcdc_zcset);
		zr=(RadioButton)mBaseView.findViewById(R.id.zEQ);
		cr=(RadioButton)mBaseView.findViewById(R.id.cEQ);
		getzcbtn=(Button)mBaseView.findViewById(R.id.vdcdc_zcget);
		//selectfile=(Button)mBaseView.findViewById(R.id.selectfile);
		//sendtoblue=(Button)mBaseView.findViewById(R.id.sendtoblue);
		dialogview= View.inflate(getActivity(), R.layout.countdown_dialog, null);
		inputEditText=(EditText)dialogview.findViewById(R.id.dialog_edit);
		inputDialog = new AlertDialog.Builder(getActivity()).create();
		inputDialog.setView(dialogview);
		inputDialog.setCancelable(false);
		ucf=(LinearLayout)mBaseView.findViewById(R.id.ucf);
		//��λ���
		ucf_pos=(Button)mBaseView.findViewById(R.id.ucf_pos);
		ucf_latitude=(EditText)mBaseView.findViewById(R.id.ucf_latitude);
		ucf_longitude=(EditText)mBaseView.findViewById(R.id.ucf_longitude);
		mLocClient = new LocationClient(mBaseView.getContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��gps
		option.setCoorType("bd09ll"); // �����������
		option.setScanSpan(1000);
		//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setIsNeedAddress(true);
		//��ѡ�������Ƿ���Ҫ��ַ����
		option.setIsNeedLocationDescribe(true);
		//��ѡ�������Ƿ���Ҫ�豸������
		option.setNeedDeviceDirect(false);
		//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setLocationNotify(true);
		//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶�����̣������Ƿ���stop��ʱ��ɱ�������̣�Ĭ�ϲ�ɱ��
		option.setIgnoreKillProcess(true); 
		//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯��������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ���
		option.setIsNeedLocationDescribe(true);
		//��ѡ��Ĭ��false�������Ƿ���ҪPOI��������BDLocation.getPoiList��õ�
		option.setIsNeedLocationPoiList(true);
		//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.SetIgnoreCacheException(false); 
		//��ѡ��Ĭ��false�������Ƿ���Gps��λ
		option.setOpenGps(true); 
		//��ѡ��Ĭ��false�����ö�λʱ�Ƿ���Ҫ������Ϣ��Ĭ�ϲ���Ҫ�����λ�汾������
		option.setIsNeedAltitude(false);
		//���ô��Զ��ص�λ��ģʽ���ÿ��ش򿪺��ڼ�ֻҪ��λSDK��⵽λ�ñ仯�ͻ������ص����ߣ���ģʽ�¿����������ٹ��Ķ�λ����Ƕ��٣���λSDK���?��λ�ñ仯�ͻἰʱ�ص�����
		option.setOpenAutoNotifyMode();
		//���ô��Զ��ص�λ��ģʽ���ÿ��ش򿪺��ڼ�ֻҪ��λSDK��⵽λ�ñ仯�ͻ������ص�����
		option.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT); 
		//�轫���úõ�LocationClientOption����ͨ��setLocOption�������ݸ�LocationClient����ʹ��
		mLocClient.setLocOption(option);
		mLocClient.start();
		ucf_pos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pinPoint(arg0);
			}
		});
		getposfromrec=(Button)mBaseView.findViewById(R.id.getposfromrec);//�ӿ��������
		setposfromrec=(Button)mBaseView.findViewById(R.id.setposfromrec);//д�������
		//����
		if(DataAssembleUtil.setPriviledge){
			setTime.setVisibility(View.VISIBLE);
			setArea.setVisibility(View.VISIBLE);
			ucf.setVisibility(View.VISIBLE);//���õ�ַ
			
		}else{
			setTime.setVisibility(View.INVISIBLE);//����ʱ��
			setArea.setVisibility(View.INVISIBLE);//���õ�ַ
			ucf.setVisibility(View.GONE);//���õ�ַ
			
		}
		getzcbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("92") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x30 };	//D0 92
					byte[] b = dau.AssembleReadData(recByte, "92");
					sendCMD="92";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});

		setzcbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("93") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x30 };//D0 93
					int commandType=0;
					if(zr.isChecked()){
						commandType=0;
					}else{
						commandType=1;
					}

					byte []b1=dau.intToAscByte2(commandType);
//					byte [] bb={b1[0],b1[1],0x30,0x30};
					byte [] bb={0x38,0x39,b1[0],b1[1]};
					byte[] b = dau.sendSetCmd(recByte, "93",bb);
					sendCMD = "93";
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
					//	sendCommand(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
					}
					sendCMD="";
				}
			}
		});
		readTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("4D") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>3){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 4D
					byte[] b = dau.AssembleReadData(recByte, "4D");
					sendCMD = "4D";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
					displayData("7E3230303134303030323030453037453430323138304132453244464139390D");
				}
			}
		});
		setTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//2019
				if (sendCMD.length() > 0 && sendCMD.compareTo("4E") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>3){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x38, 0x30 };//80 4E
					Calendar now = Calendar.getInstance();
					long number = (long) now.get(Calendar.YEAR);
					byte[] b_year = dau.LongToAscByte4(number);// ��ȡ��ASCII����
					String s_month = "" + (now.get(Calendar.MONTH) + 1);// ��ȡ�·�
					if (s_month.length() < 2) {
						s_month = "0" + s_month;
					}
					long l_month = Long.parseLong(s_month);
					byte[] b_month = dau.longToAscByte2(l_month);// ��ȡ��ASCII����
					String s_day = now.get(Calendar.DAY_OF_MONTH) + "";// ��ȡ����
					if (s_day.length() < 2) {
						s_day = "0" + s_day;
					}
					long l_day = Long.parseLong(s_day);
					byte[] b_day = dau.longToAscByte2(l_day);

					String s_hour = now.get(Calendar.HOUR_OF_DAY) + "";// ��ȡ��ǰСʱ
					if (s_hour.length() < 2) {
						s_hour = "0" + s_hour;
					}
					long l_hour = Long.parseLong(s_hour);
					byte[] b_hour = dau.longToAscByte2(l_hour);

					String s_mm = now.get(Calendar.MINUTE) + "";// ��ȡ��ǰʱ�����
					if (s_mm.length() < 2) {
						s_mm = "0" + s_mm;
					}
					long l_mm = Long.parseLong(s_mm);
					byte[] b_mm = dau.longToAscByte2(l_mm);

					String s_s = now.get(Calendar.SECOND) + "";
					if (s_s.length() < 2) {
						s_s = "0" + s_month;
					}
					long l_ss = Long.parseLong(s_s);
					byte[] b_ss = dau.longToAscByte2(l_ss);

					byte[] b = new byte[14];// Ҫ���͵�ʱ������
					for (int j = 0; j < b_year.length; j++) {
						b[j] = b_year[j];
					}
					b[4] = b_month[0];
					b[5] = b_month[1];
					b[6] = b_day[0];
					b[7] = b_day[1];
					b[8] = b_hour[0];
					b[9] = b_hour[1];
					b[10] = b_mm[0];
					b[11] = b_mm[1];
					b[12] = b_ss[0];
					b[13] = b_ss[1];
					byte[] bb = dau.sendSetCmd(recByte, "4E", b);
					sendCMD = "4E";
					countNum=0;
					//sendCommand(bb);
					byte[]b10=new byte[20];
					for (int i = 0; i <2; i++) {
						for (int j = 0; j < b10.length; j++) {
							if(bb.length>(i*20+j)){
								b10[j]=bb[i*20+j];
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
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);	
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);	
				}			
				//2019
				/*if(DataAssembleUtil.FIRSTSET==0){
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.setNotify) ,//��ʾ����һ��������Ҫ��֤����
							1000).show();// toast��ʾ1000ms
					DataAssembleUtil.FIRSTSET=1;//׼���ȴ���������
					n=System.currentTimeMillis();
					replaycheck();
					new Thread(new Runnable() {
			            @Override
			            public void run() {
			            	try {
			            		long n2=System.currentTimeMillis()-n;
			            		boolean notovertime=true;
			            		int count=60;
			            		while(notovertime){
			            			if((n2>60*1000)){
			            				if(DataAssembleUtil.FIRSTSET!=2){
			            					DataAssembleUtil.FIRSTSET=0;
			            					Message message = handler.obtainMessage();
			            					message.what = 1;
			            					handler.sendMessage(message);
			            				}
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
			            	} catch (Exception e) {
			    				
			    			}
			            }
					}).start();
					
				}else if(DataAssembleUtil.FIRSTSET==2){
					sendMessageToBlueTooth(0);
				}else{
					long n2=System.currentTimeMillis()-n;
					if(n2>60*1000){
						//Toast.makeText(getActivity(), getResources().getString(R.string.overtime), 1).show();//����������Ӧ
						//DataAssembleUtil.FIRSTSET=0;
						//inputDialog.dismiss();
						//inputDialog=null;
					}else{
						Toast.makeText(getActivity(), getResources().getString(R.string.waiting), 1).show();//δ��ʱ��ȴ�
						inputDialog.show();
						
					}
				}*/
			}
		});
		getArea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("50") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>3){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };//40 50
					byte[] b = dau.AssembleReadData(recByte, "50");
					sendCMD="50";
					countNum=0;
					sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		
		setArea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("93") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					String add=addressText.getText().toString();
					if(add.length()==0){
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast2),//����д��Ҫ���õ�ֵ
								1500).show();// toast��ʾ1000ms
					}else{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x30 };//D0 93
						int commandType=128;
						
						byte []b1=dau.intToAscByte2(commandType);
						int v=Integer.valueOf(add);
						byte []b2=dau.intToAscByte2(v);
						DataAssembleUtil.ADDR[0]=b2[0];
						DataAssembleUtil.ADDR[1]=b2[1];
						byte [] bb={b1[0],b1[1],b2[0],b2[1]};
						byte[] b = dau.sendSetCmd(recByte, "93",bb);
						sendCMD = "93";
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
		
		getEquip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("51") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>3){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };
					byte[] b = dau.AssembleReadData(recByte, "51");
					sendCMD="51";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		getSysState.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sendCMD.length() > 0 && sendCMD.compareTo("81") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>3){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x45, 0x30 };//e0 81
					byte[] b = dau.AssembleReadData(recByte, "81");
					sendCMD="81";
					countNum=0;
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
				}
			}
		});
		getposfromrec.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("47") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
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
		setposfromrec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
							1000).show();// toast��ʾ1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					if("".equals(ucf_latitude.getText().toString().trim()) || "".equals(ucf_longitude.getText().toString().trim())){
						return;
					}
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x38 };	//D8 49
					DecimalFormat d = new DecimalFormat("#000.000000");
					String la=ucf_latitude.getText().toString();
					String lg=ucf_longitude.getText().toString();
					byte lafh=0x30,lgfh=0x30;
					int laInt = 0,lgInt=0,laWInt1=0,lgWInt1=0,laWInt2=0,lgWInt2=0,laWInt3=0,lgWInt3=0;
					if(DataAssembleUtil.checkDouble(la) && DataAssembleUtil.checkDouble(lg)){
						la=d.format(Double.valueOf(la));
						lg=d.format(Double.valueOf(lg));
						String[] laStr=la.split("\\.");
						String[] lgStr=lg.split("\\.");
						Double laD=Double.valueOf(la);
						Double lgD=Double.valueOf(lg);
						if(laD<0){
							lafh=0x31;
						}
						if(lgD<0){
							lgfh=0x31;
						}
						laInt=Math.abs(Integer.valueOf(laStr[0]));
						lgInt=Math.abs(Integer.valueOf(lgStr[0]));
						laWInt1=Integer.valueOf(laStr[1].substring(0, 2));
						lgWInt1=Integer.valueOf(lgStr[1].substring(0, 2));
						laWInt2=Integer.valueOf(laStr[1].substring(2, 4));
						lgWInt2=Integer.valueOf(lgStr[1].substring(2, 4));
						laWInt3=Integer.valueOf(laStr[1].substring(4));
						lgWInt3=Integer.valueOf(lgStr[1].substring(4));
					}
					
					byte [] labyte=dau.intToAscByte2(laInt);
					byte [] laWbyte1=dau.intToAscByte2(laWInt1);
					byte [] laWbyte2=dau.intToAscByte2(laWInt2);
					byte [] laWbyte3=dau.intToAscByte2(laWInt3);
					byte [] lgbyte=dau.intToAscByte2(lgInt);
					byte [] lgWbyte1=dau.intToAscByte2(lgWInt1);
					byte [] lgWbyte2=dau.intToAscByte2(lgWInt2);
					byte [] lgWbyte3=dau.intToAscByte2(lgWInt3);
					byte [] bb={0x39,0x39,0x30,lafh,labyte[0],labyte[1],laWbyte1[0],laWbyte1[1],laWbyte2[0],laWbyte2[1],laWbyte3[0],laWbyte3[1]
							,0x30,lgfh,lgbyte[0],lgbyte[1],lgWbyte1[0],lgWbyte1[1],lgWbyte2[0],lgWbyte2[1],lgWbyte3[0],lgWbyte3[1]};
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
		
		/*selectfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*View view=new FileSelectorView(mBaseView.getContext());
				AlertDialog.Builder builder = new Builder(mBaseView.getContext());
				builder.setMessage("ȷ���Ƴ������ͼƬ��");
				builder.setView(view);
				builder.setTitle("��ʾ");
				builder.create().show();*/
				/*Intent intent=new Intent(mBaseView.getContext(),FileSelectorActivity.class);
				
				startActivityForResult(intent,FileSelectorActivity.FileNameResult);
			}
		});*/
		return mBaseView;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bundle bundle=data.getExtras();  
        String Code=bundle.getString("filename");
        if(Code == null || Code.equals("") || Code.equals("null")){
        	//filename.setText("");
        }else{
        	//Toast.makeText(MainActivity.this, resultCode, Toast.LENGTH_LONG).show();
        	String file= Code.substring(Code.lastIndexOf("\\")+1);
        	//filename.setText(file);
        }
	}
	private void getDeviceID(){
		byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x34, 0x30 };
		sendCMD="50";
		if(dau==null){
			dau = new DataAssembleUtil();
		}
		byte[] b = dau.AssembleReadData(recByte, "50");
		DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
		//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
	}
	private void getISZORC(){
		byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x30 };	//D6 42
		byte[] b = dau.AssembleReadData(recByte, "92");
		sendCMD="92";
		DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
		//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
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
	
	/*@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		//2019
		inflater.inflate(R.menu.qrcode, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}*/
	
	/*@Override  
	public boolean onOptionsItemSelected(MenuItem item) {  
		switch(item.getItemId()){ 
			case R.id.menu_qrcode: 
				Intent intent = new Intent(this.getActivity(),
						QRCodeActivity.class);
				startActivity(intent);
			break; 
		} 
		return super.onOptionsItemSelected(item);  
	}*/
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
	
	/*private void replaycheck(){
		new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		//��������ȴ���ŵȲ���
            		String url = "http://192.168.80.99:8988/DlyAppSever/LoginServlet";
            		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        			list.add(new BasicNameValuePair("account", "hzy"));
        			list.add(new BasicNameValuePair("password", "123"));
    				Object object = HttpReq.topostData(url, list);

    				//Thread.sleep(50*1000);
    				Message message = handler.obtainMessage();
    				message.obj = object;
    				message.what = 0;
    				handler.sendMessage(message);
    			} catch (Exception e) {
    				Looper.prepare();
    				Looper.loop();
    			}
            }
		}).start();
	}*/
	public void pinPoint(View v){
		if(mLocClient.isStarted()){
			ucf_latitude.setText(""+DataAssembleUtil.latitude);
			ucf_longitude.setText(""+DataAssembleUtil.longitude);
		}
	}
	
	public void sendMessageToBlueTooth(int clickbutton){
		switch (clickbutton){
		case 0://����ʱ��
			getDeviceID();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (sendCMD.length() > 0 && sendCMD.compareTo("4E") != 0) {// �жϵ�ǰ�Ƿ����������ڷ���
				return;
				/*Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//����ָ����δ�յ���ݣ�CID2��
						1000).show();// toast��ʾ1000ms
				countNum++;
				if(countNum>3){
					sendCMD="";
				}*/
			} else {
				byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x38, 0x30 };
				Calendar now = Calendar.getInstance();
				long number = (long) now.get(Calendar.YEAR);
				if(dau==null){
					dau = new DataAssembleUtil();
				}
				byte[] b_year = dau.LongToAscByte4(number);// ��ȡ��ASCII����
				String s_month = "" + (now.get(Calendar.MONTH) + 1);// ��ȡ�·�
				if (s_month.length() < 2) {
					s_month = "0" + s_month;
				}
				long l_month = Long.parseLong(s_month);
				byte[] b_month = dau.longToAscByte2(l_month);// ��ȡ��ASCII����
				String s_day = now.get(Calendar.DAY_OF_MONTH) + "";// ��ȡ����
				if (s_day.length() < 2) {
					s_day = "0" + s_day;
				}
				long l_day = Long.parseLong(s_day);
				byte[] b_day = dau.longToAscByte2(l_day);

				String s_hour = now.get(Calendar.HOUR_OF_DAY) + "";// ��ȡ��ǰСʱ
				if (s_hour.length() < 2) {
					s_hour = "0" + s_hour;
				}
				long l_hour = Long.parseLong(s_hour);
				byte[] b_hour = dau.longToAscByte2(l_hour);

				String s_mm = now.get(Calendar.MINUTE) + "";// ��ȡ��ǰʱ�����
				if (s_mm.length() < 2) {
					s_mm = "0" + s_mm;
				}
				long l_mm = Long.parseLong(s_mm);
				byte[] b_mm = dau.longToAscByte2(l_mm);

				String s_s = now.get(Calendar.SECOND) + "";
				if (s_s.length() < 2) {
					s_s = "0" + s_month;
				}
				long l_ss = Long.parseLong(s_s);
				byte[] b_ss = dau.longToAscByte2(l_ss);

				byte[] b = new byte[14];// Ҫ���͵�ʱ������
				for (int j = 0; j < b_year.length; j++) {
					b[j] = b_year[j];
				}
				b[4] = b_month[0];
				b[5] = b_month[1];
				b[6] = b_day[0];
				b[7] = b_day[1];
				b[8] = b_hour[0];
				b[9] = b_hour[1];
				b[10] = b_mm[0];
				b[11] = b_mm[1];
				b[12] = b_ss[0];
				b[13] = b_ss[1];
				byte[] bb = dau.sendSetCmd(recByte, "4E", b);
				sendCMD = "4E";
				countNum=0;
				//sendCommand(bb);
				DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb);	
				//DevicesInfo2Activity.mBluetoothLeService.sendCmd(bb,AES.AES_KEY);
			}			
			break;
		}
	}
	public void sendPass(byte[] pass){
		//sendCommand(pass);
		DevicesInfo2Activity.mBluetoothLeService.sendCmd(pass);
		AES.AES_KEY[10]=pass[0];
		AES.AES_KEY[11]=pass[1];
		AES.AES_KEY[12]=pass[2];
		AES.AES_KEY[13]=pass[3];
		AES.AES_KEY[14]=pass[4];
		AES.AES_KEY[15]=pass[5];
	}


	class MyLocationListener implements BDLocationListener {
	    @Override
	    public void onReceiveLocation(BDLocation location){
	        //�˴���BDLocationΪ��λ�����Ϣ�࣬ͨ����ĸ���get�����ɻ�ȡ��λ��ص�ȫ�����
	        //����ֻ�оٲ��ֻ�ȡ��γ����أ����ã��Ľ����Ϣ
	        //�������Ϣ��ȡ˵�����������ο���BDLocation���е�˵��

	    	//��ȡγ����Ϣ
        	latitude = location.getLatitude();
        	
	        if((latitude!=DataAssembleUtil.latitude) && (latitude!=4.9E-324) && (latitude!=0.0)){
	        	DataAssembleUtil.latitude=latitude;
	        }
	    	//��ȡ������Ϣ
        	longitude = location.getLongitude();
        
	        if((longitude!=DataAssembleUtil.longitude)&& (longitude!=4.9E-324) && (longitude!=0.0)){
	        	DataAssembleUtil.longitude=longitude;
	        }
	        //��ȡ��λ���ȣ�Ĭ��ֵΪ0.0f
	        float radius = location.getRadius();
	        //��ȡ��γ��������ͣ���LocationClientOption�����ù���������Ϊ׼
	        String coorType = location.getCoorType();
	        //��ȡ��λ���͡���λ���󷵻��룬������Ϣ�ɲ�����ο���BDLocation���е�˵��
	        int errorCode = location.getLocType();
	       
	    }

		@Override
		public void onConnectHotSpotMessage(String arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	}
}
