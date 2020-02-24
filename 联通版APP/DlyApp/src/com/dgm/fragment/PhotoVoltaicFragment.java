package com.dgm.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dgm.aes.AES;
import com.dgm.bean.BusPlate;
import com.dgm.bean.Optimizer;
import com.dgm.dlyapp.BluetoothLeService;
import com.dgm.dlyapp.DevicesInfo2Activity;
import com.dgm.dlyapp.R;
import com.dgm.util.DataAssembleUtil;
/**
 * 交流配电参数界面
 * @author Administrator
 *
 */
public class PhotoVoltaicFragment extends Fragment {
	public String sendCMD = "";//上次发送指令CID2标示
	public EditText pv_gerEdit,pv_powerEdit,pv_singlebusPowerEdit,pv_daygerEdit,pv_totalgerEdit,
	pv_daygertimeEdit,pv_totalgertimeEdit,pv_dayGerEdit,clickmemo,clickmemo2,pv_queryEngEdit,pv_busplateNumEdit;
	public Spinner spinner;
	//2020年1月7日修改，原来是选择汇流板1,2,3,4时对应选择31,32,33,34，现在改成直接选择汇流箱，操作汇流板1时其他汇流板自动操作
	//,sp;
	//结束
	public ListView optimizerListView=null,optimizerAlarmListView=null,engListView=null;
	public int countNum=0;
	boolean connect_status_bit = false;
	public Map<Integer,BusPlate> plateMap=new HashMap<Integer,BusPlate>();
	List<Map<String, String>> ulist = new ArrayList<Map<String, String>>();
	List<Map<String, String>> ulist2 = new ArrayList<Map<String, String>>();
	List<Map<String, String>> ulist3 = new ArrayList<Map<String, String>>();
	
	private Context mContext;
	private View mBaseView;
	private DataAssembleUtil dau;
	private Button getPvAIBtn,getPvDIBtn,setBtn,
	//2020年1月7日修改，原来是选择汇流板1,2,3,4时对应选择31,32,33,34，现在改成直接选择汇流箱，操作汇流板1时其他汇流板自动操作
	//pv_btnSel,
	//结束
	pv_queryEngsetBtn,kgSet,pv_busplateNumBtn,getPvParamBtn;
	private ArrayAdapter<String> spinnerAdapter;
	private SimpleAdapter optimizerAdapter;
	private SimpleAdapter optimizerAlarmAdapter;
	private SimpleAdapter engListAdapter;
	private List<String> itemlists;
	DecimalFormat df=new DecimalFormat("0.00");
	//2020年1月7日修改，原来是选择汇流板1,2,3,4时对应选择31,32,33,34，现在改成直接选择汇流箱，操作汇流板1时其他汇流板自动操作
	//byte[] sl={0x30,0x31};
	//结束
	public LinearLayout pf;
	public AlertDialog optimizerDialog,optimizerAlarmDialog,engDialog;
	private RadioButton kai,guan;
	//private LinearLayout ll;
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
		mBaseView = inflater.inflate(R.layout.photovoltaic_fragment, null);
		mContext = getActivity();
		optimizerListView=new ListView(mContext);
		optimizerAlarmListView=new ListView(mContext);
		engListView=new ListView(mContext);
		getPvAIBtn = (Button) mBaseView.findViewById(R.id.getPvAIBtn);// 获取系统模拟量
		getPvDIBtn = (Button) mBaseView.findViewById(R.id.getPvDIBtn);// 获取系统告警量
		setBtn =(Button)mBaseView.findViewById(R.id.setBtn);// 清空命令
		//2020年1月7日修改，原来是选择汇流板1,2,3,4时对应选择31,32,33,34，现在改成直接选择汇流箱，操作汇流板1时其他汇流板自动操作
		//pv_btnSel =(Button)mBaseView.findViewById(R.id.pv_btnSel);// 选择汇流板
		//结束
		pv_queryEngsetBtn=(Button)mBaseView.findViewById(R.id.pv_queryEngsetBtn);//查询电能
		kgSet=(Button)mBaseView.findViewById(R.id.pv_kgsetbtn);//优化器开关设置
		getPvParamBtn=(Button)mBaseView.findViewById(R.id.getPvParamBtn);//获取参数		
		pv_busplateNumBtn=(Button)mBaseView.findViewById(R.id.pv_busplateNumBtn);//汇流板个数设置
		pv_powerEdit = (EditText) mBaseView.findViewById(R.id.pv_powerEdit);// 汇流箱总功率
		pv_gerEdit = (EditText) mBaseView.findViewById(R.id.pv_gerEdit);// 累计发电量
		pv_singlebusPowerEdit=(EditText)mBaseView.findViewById(R.id.pv_singlebusPowerEdit);//单汇流板功率
		pv_daygerEdit=(EditText)mBaseView.findViewById(R.id.pv_daygerEdit);//单汇流板日发电量
		pv_totalgerEdit=(EditText)mBaseView.findViewById(R.id.pv_totalgerEdit);//单汇流板累计发电量
		pv_daygertimeEdit=(EditText)mBaseView.findViewById(R.id.pv_daygertimeEdit);//单汇流板日发电时间
		pv_totalgertimeEdit=(EditText)mBaseView.findViewById(R.id.pv_totalgertimeEdit);//单汇流板累计发电时间
		pv_dayGerEdit=(EditText)mBaseView.findViewById(R.id.pv_dayGerEdit);//汇流箱日发电量
		clickmemo=(EditText)mBaseView.findViewById(R.id.pv_clickEdit);//优化器信息选择
		clickmemo2=(EditText)mBaseView.findViewById(R.id.pv_click2Edit);//优化器告警信息选择
		pv_queryEngEdit=(EditText)mBaseView.findViewById(R.id.pv_queryEngEdit);//查询电能条数
		pv_busplateNumEdit=(EditText)mBaseView.findViewById(R.id.pv_busplateNumEdit);//汇流板个数
		pf=(LinearLayout)mBaseView.findViewById(R.id.pf);
		kai=(RadioButton)mBaseView.findViewById(R.id.pv_k);//选项开
		guan=(RadioButton)mBaseView.findViewById(R.id.pv_g);//选项关
		//ll=(LinearLayout)mBaseView.findViewById(R.id.ll);//参数面板
		if(DataAssembleUtil.setPriviledge){
			pf.setVisibility(View.VISIBLE);
		}else{
			pf.setVisibility(View.GONE);
		}
		
		spinner=(Spinner)mBaseView.findViewById(R.id.spinner);//汇流板选择
		itemlists = new ArrayList<String>();
        itemlists.add(getResources().getString(R.string.pv_select));//请先获取汇流板个数
		spinnerAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,itemlists);
		/**设置Item的样式**/
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view,
		            int position, long id) {
		    	String se=spinner.getSelectedItem().toString();
		    	if(se.equals(getResources().getString(R.string.pv_select))){
		    		return;
		    	}else{
		    		String index=se.substring(se.length()-1, se.length());
		    		int in=Integer.valueOf(index)-1;
		    		BusPlate bsp=plateMap.get(in);
		    		
		    		pv_singlebusPowerEdit.setText(String.valueOf(df.format((float)bsp.getTotalOptimizerPower()/10)));//单汇流板功率
		    		pv_daygerEdit.setText(df.format((float)bsp.getGenerationPower()/100));//单汇流板日发电量
		    		pv_totalgerEdit.setText(df.format((float)bsp.getTotalGenerationPower()/100));//单汇流板累计发电量
		    		pv_daygertimeEdit.setText(String.valueOf(bsp.getGenerationPowerMinutes()));//单汇流板日发电时间
		    		pv_totalgertimeEdit.setText(String.valueOf(bsp.getTotalGenerationMinutes()));//单汇流板累计发电时间

		    		ulist.clear();
		    		for(int i=0;i<=7;i++){
		    			Optimizer o=bsp.getOptimizer(i);
		    			Map<String, String> keyValuePair = new HashMap<String, String>();
		    			keyValuePair.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_inputV));//优化器i输入电压
		    			keyValuePair.put("value", String.valueOf(o.getInputV()));//优化器i输入电压
		    			ulist.add(keyValuePair);
		    			
		    			Map<String, String> keyValuePair1 = new HashMap<String, String>();
		    			keyValuePair1.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_outputV));//优化器i输出电压
		    			keyValuePair1.put("value", String.valueOf(o.getOutputV()));//优化器i输出电压
		    			ulist.add(keyValuePair1);
		    			
		    			Map<String, String> keyValuePair3 = new HashMap<String, String>();
		    			keyValuePair3.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_power));//优化器i输出功率
		    			keyValuePair3.put("value", df.format((float)o.getPower()/10));//优化器i输出功率
		    			ulist.add(keyValuePair3);
		    			
		    			Map<String, String> keyValuePair4 = new HashMap<String, String>();
		    			keyValuePair4.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_curr));//优化器i电流
		    			keyValuePair4.put("value", df.format((float)o.getCurr()/10));//优化器i电流
		    			ulist.add(keyValuePair4);
		    		}
		    		optimizerAdapter.notifyDataSetChanged();
		    	}
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // TODO Auto-generated method stub
		    }
			
		});
		
		
		optimizerAdapter=new SimpleAdapter(mContext, ulist,
                R.layout.list_item_1, new String[] { "name",
        "value" }, new int[] { R.id.li_name,
        R.id.li_value });
		
		optimizerAlarmAdapter=new SimpleAdapter(mContext, ulist2,
                R.layout.list_item_1, new String[] { "name",
        "value" }, new int[] { R.id.li_name,
        R.id.li_value });
		
		engListAdapter=new SimpleAdapter(mContext, ulist3,
                R.layout.list_item_2, new String[] { "xh","sj","dn","rq" }
		, new int[] { R.id.xh,R.id.sj,R.id.dn,R.id.rq });
		
		getPvParamBtn.setOnClickListener(new OnClickListener() {

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x32 };//D2 47
					byte[] b = dau.AssembleReadData(recByte, "47");
					sendCMD = "47";
					countNum=0;
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);					
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);				
				}
			}
		});
				
		getPvAIBtn.setOnClickListener(new OnClickListener() {
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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x32 };//D2 42
					byte[] b = dau.AssembleReadData(recByte, "42");
					sendCMD="42";
					countNum=0;
					//getPvAIBtn.setEnabled(false);
					/*new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Message message = handler.obtainMessage();
        					message.what = 1;
        					handler.sendMessage(message);
						}
					}).start();*/
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
						//sendCommand(b10);
						//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10);
						DevicesInfo2Activity.mBluetoothLeService.sendCmd(b10,AES.AES_KEY);
					}
					*/
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);
					//displayData("7E0D");
					
				}
			}
		});
		getPvDIBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sendCMD.length() > 0 && sendCMD.compareTo("44") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x32 };//D2 44
					byte[] b = dau.AssembleReadData(recByte, "44");
					sendCMD = "44";
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
					}*/
					//sendCommand(b);
					DevicesInfo2Activity.mBluetoothLeService.sendCmd(b);					
					//DevicesInfo2Activity.mBluetoothLeService.sendCmd(b,AES.AES_KEY);					
				}
			}
		});
		pv_busplateNumBtn.setOnClickListener(new OnClickListener() {
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
					try{
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x32 };//D2 49
						int commandType=130;//82H
						int value=Integer.valueOf(pv_busplateNumEdit.getText().toString());
						byte []b1=dau.intToAscByte2(commandType);
						byte []b2=dau.intToAscByte2(value);
						byte [] bb={b1[0],b1[1],b2[0],b2[1]};
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
					}catch(Exception e){
						return;
					}
				}
			}
		});
		setBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//2020
				//sendCMD="42";
				//displayData("7E3230303144323030463132453031423730303030303030343032303030303030303430303433303030303030303430303030303030464132413331413335303031313337303035323030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030304132413330303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030433431390D");
				if (sendCMD.length() > 0 && sendCMD.compareTo("49") != 0) {// 判断当前是否有命令正在发送
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.acToast) + sendCMD,//上条指令尚未收到数据，CID2：
							1000).show();// toast显示1000ms
					countNum++;
					if(countNum>2){
						sendCMD="";
					}
				} else {
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x32 };//D2 49
					int commandType=129;//81H

					byte []b1=dau.intToAscByte2(commandType);
					//2020年1月7日修改，原来是选择汇流板1,2,3,4时对应选择31,32,33,34，现在改成直接选择汇流箱，操作汇流板1时其他汇流板自动操作
					//byte [] bb={b1[0],b1[1],sl[0],sl[1]};
					byte [] bb={b1[0],b1[1],0x30,0x31};
					//结束
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
		pv_queryEngsetBtn.setOnClickListener(new OnClickListener() {

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
						byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1], 0x44, 0x32 };//D2H
						String vUplimit=pv_queryEngEdit.getText().toString();
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
		});
		//2020年1月7日修改，原来是选择汇流板1,2,3,4时对应选择31,32,33,34，现在改成直接选择汇流箱，操作汇流板1时其他汇流板自动操作
		/*
		pv_btnSel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openSelectDialog();
			}
			
		});*/
		//结束
		clickmemo.setOnLongClickListener(new OnLongClickListener(){
			
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
		
		clickmemo2.setOnLongClickListener(new OnLongClickListener(){
			
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
				optimizerAlarmDialog.show();
				return true;
			}
		});
		kgSet.setOnClickListener(new OnClickListener(){

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
					byte[] recByte = { DataAssembleUtil.VERSION[0], DataAssembleUtil.VERSION[1], DataAssembleUtil.ADDR[0], DataAssembleUtil.ADDR[1],  0x44, 0x32 };//D2 49
					int commandType=128;//80H
					byte [] b1=dau.intToAscByte2(commandType);
					byte [] bb={b1[0],b1[1],0x30,0x30};
					if(kai.isChecked()){
						bb[3]=0x31;
					}else{
						bb[3]=0x30;
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
		
		return mBaseView;
	}
	
	//2020年1月7日修改，原来是选择汇流板1,2,3,4时对应选择31,32,33,34，现在改成直接选择汇流箱，操作汇流板1时其他汇流板自动操作
	/*
	private void openSelectDialog(){
		sp=new Spinner(mContext);
				
		String s[]=new String[]{getResources().getString(R.string.pv_busplate1),
				getResources().getString(R.string.pv_busplate2),
				getResources().getString(R.string.pv_busplate3),
				getResources().getString(R.string.pv_busplate4)};
		
		ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(mContext,
				android.R.layout.simple_dropdown_item_1line,
				s);
		
		
		sp.setAdapter(arrayAdapter);
		AlertDialog dialog=new AlertDialog.Builder(mContext)
		.setIcon(android.R.drawable.alert_dark_frame)
		.setView(sp)
		.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	sp=null;
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//确定
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获得
                    	int position=sp.getSelectedItemPosition();
                    	pv_btnSel.setText(sp.getSelectedItem().toString());
                        sl=dau.intToAscByte2(position+1);
                        sp=null;
                        dialog.dismiss();
                    }
                })
		.create();
		
		dialog.show();
	}
*/
	//结束

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
//				Toast toast = Toast.makeText(getActivity(), "123123",
//						Toast.LENGTH_SHORT);
//				toast.show();
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
				}catch(NullPointerException e){
					
				}catch(ConcurrentModificationException e){
					
				}
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

	private void displayData(String data1) {
		try{
			//data1=new String(AES.decrypt(data1.getBytes(),AES.AES_KEY));
			if (data1 != null && data1.length() > 0) {
				Log.e("@@@@@@@@@@@@@@@@@@", "data1:" + data1);
				recString = recString + data1;
				int index7E = recString.indexOf("7E");
				int index0D = recString.indexOf("0D");
				if (index7E != -1 && index0D != -1) {
					Log.e("@@@@@@@@@@", recString);
					//writeFile(recString);
					if(recString==null)return;
					String cmd = recString.substring(index7E + 2, index0D);
					//String cmd="323030314432303043323445303045443030303030303033303430303030303030303030303230303030303030333030303030303030413241333030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030000000003030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030413241333030303030303030303030303030303030303030303030303030303030303030303030303030303030303330333530303039344230303243303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030000000003030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303038463937";
					byte[] b = dau.hex2byte(cmd);// 去掉7E,0D后转byte数组
					boolean ifTrue = dau.ifCheckSum(cmd);// 判断接收到数据校验位与所有数据计算得到的校验位是否一致
					if (ifTrue) {
						byte[] byteData = { b[9], b[10], b[11] };
						String dataLength = new String(byteData);
						int intLength = Integer.parseInt(dataLength, 16);
						if(intLength==0) {
							recString="";
							sendCMD="";
							return;
						}
						byte[] b2 = new byte[intLength];
						if (b.length > 12 + intLength) {
							for (int j = 0; j < intLength; j++) {
								b2[j] = b[j + 12];
							}
							if (sendCMD.compareTo("42") == 0) {
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								for(int i=0;i<plateMap.size();i++){
									plateMap.get(i).clearMap();
								}
								plateMap.clear();
								//bb[0];追加的flag
								int zgl = DataAssembleUtil.parseInt(bb[2], bb[1]);// 汇流箱总功率
								pv_powerEdit.setText(String.valueOf(zgl));
							
								long ljfdl = DataAssembleUtil.parseUnsignedInt(bb[3], bb[4],bb[5], bb[6]);// 汇流箱累计发电量
								String a=df.format((float)ljfdl/100);
								pv_gerEdit.setText(a);
							
								long total=0;//	汇流箱总日发电量
								//long total=DataAssembleUtil.parseUnsignedInt(bb[7], bb[8],bb[9], bb[10]);
								int hlbgs=bb[7];
								if(hlbgs>0){
									spinnerAdapter.clear();
								}
								int base=8;
								for(int i = 0;i<hlbgs;i++){
									spinnerAdapter.add(getResources().getString(R.string.pv_busplate)+" "+(i+1));//汇流板
					            	BusPlate bp=new BusPlate();
					            	long rfdl = DataAssembleUtil.parseUnsignedInt(bb[base], bb[base+1],bb[base+2], bb[base+3]);
					            	//8 9 10 11
					            	//total=total+rfdl;
					            	bp.setGenerationPower(rfdl);
					            	int rfdsj=DataAssembleUtil.parseInt(bb[base+5], bb[base+4]);
					            	bp.setGenerationPowerMinutes(rfdsj);
					            
					            	long ljrfdl = DataAssembleUtil.parseUnsignedInt(bb[base+6], bb[base+7],bb[base+8], bb[base+9]);
					            	bp.setTotalGenerationPower(ljrfdl);
					            
					            	long ljrfdsj = DataAssembleUtil.parseUnsignedInt(bb[base+10], bb[base+11],bb[base+12], bb[base+13]);
					            	bp.setTotalGenerationMinutes(ljrfdsj);
					            	int start=base+15;
					            	for(int j=0;j<=7;j++){
					            		Optimizer o=new Optimizer();
					            		o.setInputV(DataAssembleUtil.parseUnsignedInt(bb[start+1]));
					            		o.setOutputV(DataAssembleUtil.parseUnsignedInt(bb[start+2]));
					            		o.setSetV(DataAssembleUtil.parseUnsignedInt(bb[start+3]));
					            		o.setPower(DataAssembleUtil.parseUnsignedInt(bb[start+5], bb[start+4]));
					            		o.setCurr(DataAssembleUtil.parseUnsignedInt(bb[start+7], bb[start+6]));
					            		bp.setTotalOptimizerPower(bp.getTotalOptimizerPower()+o.getPower());
					            		bp.putOptimizer(j, o);
					            		start=start+7;
					            	}
					            	base=start+1;
					            	plateMap.put(i, bp);
								}
								total=DataAssembleUtil.parseUnsignedInt(bb[base], bb[base+1],bb[base+2], bb[base+3]);
								pv_dayGerEdit.setText(df.format((float)total/100));//汇流箱日发电量:
								if(plateMap.size()>=0){
							
									BusPlate bsp=plateMap.get(0);
									pv_singlebusPowerEdit.setText(String.valueOf(df.format((float)bsp.getTotalOptimizerPower()/10)));//单汇流板功率
									pv_daygerEdit.setText(df.format((float)bsp.getGenerationPower()/100));//单汇流板日发电量
									pv_totalgerEdit.setText(df.format((float)bsp.getTotalGenerationPower()/100));//单汇流板累计发电量
									pv_daygertimeEdit.setText(String.valueOf(bsp.getGenerationPowerMinutes()));//单汇流板日发电时间
					    			pv_totalgertimeEdit.setText(String.valueOf(bsp.getTotalGenerationMinutes()));//单汇流板累计发电时间

									ulist.clear();
									for(int i=0;i<=7;i++){
										Optimizer o=bsp.getOptimizer(i);
										Map<String, String> keyValuePair = new HashMap<String, String>();
										keyValuePair.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_inputV));//优化器i输入电压
										keyValuePair.put("value", String.valueOf(o.getInputV()));//优化器i输入电压
										ulist.add(keyValuePair);
									
										Map<String, String> keyValuePair1 = new HashMap<String, String>();
										keyValuePair1.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_outputV));//优化器i输出电压
										keyValuePair1.put("value", String.valueOf(o.getOutputV()));//优化器i输出电压
										ulist.add(keyValuePair1);
									
										/*Map<String, String> keyValuePair2 = new HashMap<String, String>();
										keyValuePair2.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_setV));//优化器i设置电压
										keyValuePair2.put("value", String.valueOf(o.getSetV()));//优化器i设置电压
										ulist.add(keyValuePair2);*/
									
										Map<String, String> keyValuePair3 = new HashMap<String, String>();
										keyValuePair3.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_power));//优化器i输出功率
										keyValuePair3.put("value", df.format((float)o.getPower()/10));//优化器i输出功率
					    				ulist.add(keyValuePair3);
				    			
										Map<String, String> keyValuePair4 = new HashMap<String, String>();
										keyValuePair4.put("name", getResources().getString(R.string.pv_optimizer)+" "+(i+1)+getResources().getString(R.string.pv_curr));//优化器i电流
										keyValuePair4.put("value", df.format((float)o.getCurr()/10));//优化器i电流
										ulist.add(keyValuePair4);
									}
									optimizerAdapter.notifyDataSetChanged();
									spinner.setSelection(0);
								}
								getPvAIBtn.setEnabled(true);
								//writeFile(recString);
							}else if(sendCMD.compareTo("44") == 0){
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
								ulist2.clear();
								//bb[0];追加的flag
								if((bb[1] & 0x01)==0x00){// 系统状态第四位第一个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 1"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{// 系统状态第四位第一个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 1"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
								if((bb[1] & 0x02)==0x00){// 系统状态第四位第二个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 2"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{// 系统状态第四位第二个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 2"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
								if((bb[1] & 0x04)==0x00){// 系统状态第四位第三个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 3"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{// 系统状态第四位第三个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 3"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
								if((bb[1] & 0x08)==0x00){// 系统状态第四位第四个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 4"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{// 系统状态第四位第四个字节
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 4"+getResources().getString(R.string.pv_light));//汇流板i防雷
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
							
								if(bb[2]==0x00){// 汇流板1通信故障
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 1"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 1"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
								if(bb[3]==0x00){// 汇流板2通信故障
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 2"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 2"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
								if(bb[4]==0x00){// 汇流板3通信故障
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 3"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 3"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
								if(bb[5]==0x00){// 汇流板4通信故障
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 4"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.normal));//正常
									ulist2.add(keyValuePair);
								}else{
									Map<String, String> keyValuePair = new HashMap<String, String>();
									keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" 4"+getResources().getString(R.string.rdf_txgz));//汇流板i通信故障
									keyValuePair.put("value", getResources().getString(R.string.fault));//故障
									ulist2.add(keyValuePair);
								}
							
								int z=6;byte x=0x01;
								for(int i=0;i<=3;i++){
									for(int j=0;j<=7;j++){
										switch(j){
										case 0:
											x=0x01;
											break;
										case 1:
											x=0x02;
											break;
										case 2:
											x=0x04;
											break;
										case 3:
											x=0x08;
											break;
										case 4:
											x=0x10;
											break;
										case 5:
											x=0x20;
											break;
										case 6:
											x=0x40;
											break;
										case 7:
											x=(byte) 0x80;
											break;
										}
										if((bb[z] & x)==0x00){// 汇流板1优化器1通信故障
											Map<String, String> keyValuePair = new HashMap<String, String>();
											keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" "+(i+1)+getResources().getString(R.string.pv_optimizer)+" "+(j+1)+getResources().getString(R.string.rdf_txgz));//汇流板i优化器j通信故障
											keyValuePair.put("value", getResources().getString(R.string.normal));//正常
											ulist2.add(keyValuePair);
										}else{
											Map<String, String> keyValuePair = new HashMap<String, String>();
											keyValuePair.put("name", getResources().getString(R.string.pv_busplate)+" "+(i+1)+getResources().getString(R.string.pv_optimizer)+" "+(j+1)+getResources().getString(R.string.rdf_txgz));//汇流板i优化器j通信故障
											keyValuePair.put("value", getResources().getString(R.string.fault));//故障
											ulist2.add(keyValuePair);
										}
									}
									z++;//第n块汇流板
								}
								
								optimizerAlarmAdapter.notifyDataSetChanged();
								//writeFile(recString);
							}else if(sendCMD.compareTo("47") == 0){
								dau.AscToHex2(b2, b2.length);
								byte[] bb = dau.Data_Close(b2, b2.length);
							
								int n= DataAssembleUtil.parseInt(bb[1]);
								if(bb[0]==1){// 优化器开关
									kai.setChecked(true);
								}else{
									guan.setChecked(true);
								}
								pv_busplateNumEdit.setText(String.valueOf(n));
								//writeFile(recString);
							
							}else if(sendCMD.compareTo("80") == 0){
								dau.AscToHex2(b2, b2.length);
								final byte[] bb = dau.Data_Close(b2, b2.length);
								new Thread(new Runnable() {
									@Override
									public void run() {
										try {
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
							}
							//writeFile(recString);
						} else {
							if(getActivity()==null) return;
							Toast.makeText(getActivity(), getResources().getString(R.string.acToast4), 1000).show();// toast显示1000ms	收到数据长度异常！
						}
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.acToast5), 1000).show();// toast显示1000ms	收到数据校验失败！
					}
					recString = "";
					sendCMD = "";
				}
			}
		}catch(Exception e){
			
		}
	}

	/*private void sendCommand(byte[] bb) {
		StringBuffer sb=new StringBuffer(bb.length);
		String tmp;
		for(int i=0;i<bb.length;i++){
			tmp=Integer.toHexString(0xFF & bb[i]);
			if(tmp.length()<2){
				sb.append(0);
			}
			sb.append(tmp.toUpperCase());
		}
		writeFile("cmd_"+sb.toString());
	}*/
}