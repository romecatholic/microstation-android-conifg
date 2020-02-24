package com.dgm.fragment;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

public class AlarmSearchFragment extends Fragment {

	private View mBaseView;
	private EditText area;
	private String[] items;
	private String[] alarm_items = {getResources().getString(R.string.normalalarm),getResources().getString(R.string.important),getResources().getString(R.string.urgent)};//一般告警,重要告警,严重告警
	private DBManager dbMgr;
	private Context mContext;
	private String areaName = "";
	private EditText alartTime,alarTimeEnd;
	private EditText stationName;
	private EditText alarmName;
	private EditText et_alarm_type;
	private Button searchButton;
	private Calendar calendar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.frag_alarm_search_page, null);
		dbMgr = new DBManager(mContext);
		SharedPreferences shared = getActivity().getSharedPreferences(
				"saveUserNamePwd", getActivity().MODE_PRIVATE);
		// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
		String userid = shared.getString("name", "");// 同上，若没找到就让它为空""
		areaName = dbMgr.queryUserArea(userid);
		stationName = (EditText) mBaseView
				.findViewById(R.id.alarm_search_staiton_name);
		alarmName = (EditText) mBaseView
				.findViewById(R.id.alarm_search_alarm_name);
		area = (EditText) mBaseView.findViewById(R.id.areaselect);
		alartTime = (EditText) mBaseView.findViewById(R.id.inputtime);
		alarTimeEnd = (EditText) mBaseView.findViewById(R.id.inputtimeend);
		et_alarm_type = (EditText) mBaseView.findViewById(R.id.alarm_type);
		searchButton = (Button) mBaseView.findViewById(R.id.search);
		et_alarm_type.setInputType(InputType.TYPE_NULL);
		area.setInputType(InputType.TYPE_NULL);
		et_alarm_type.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(stationName.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});

		alartTime.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(alartTime.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});

		area.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(area.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});

		stationName.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(stationName.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});
		

		alarmName.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// 关闭软键盘
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(alarmName.getWindowToken(), 0);
					// 使得根View重新获取焦点，以监听返回键
					regetFocus();
					return true;
				}
				return false;
			}
		});
		
		et_alarm_type.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 隐藏软键盘
				InputMethodManager manager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				new AlertDialog.Builder(getActivity())
						.setTitle(getResources().getString(R.string.seleAlarmLevel))//选择告警等级
						.setItems(alarm_items,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int i) {
										et_alarm_type.setText(alarm_items[i]);
									}
								}).show();
			}
		});
		area.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 隐藏软键盘
				InputMethodManager manager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.seleReg))//选择区域  
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int i) {
								area.setText(items[i]);
							}
						}).show();
			}
		});
		addlist();
		alartTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 隐藏软键盘
				 //通过自定义控件AlertDialog实现
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(mContext, R.layout.time_dialog, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
                //设置日期简略显示 否则详细显示 包括:星期\周
                datePicker.setCalendarViewShown(false);
                //初始化当前日期
                Calendar calendar = Calendar.getInstance(); 
                calendar.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                //设置date布局
                builder.setView(view);
                builder.setTitle(getResources().getString(R.string.setDate));//设置日期信息
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//确定
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	 Integer mHour = timePicker.getCurrentHour();
                    	 Integer mMinute = timePicker.getCurrentMinute();
                         //时间小于10的数字 前面补0 如01:12:00
                    	 StringBuffer time = new StringBuffer();
                         time.append(mHour < 10 ? "0" + mHour : mHour).append(":")
                                 .append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00");
                        //日期格式
                        StringBuffer date = new StringBuffer();
                        date.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        String allstr = date+" "+time;

                        alartTime.setText(allstr);
                     
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
			}
		});
		alarTimeEnd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 隐藏软键盘
				 //通过自定义控件AlertDialog实现
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(mContext, R.layout.time_dialog, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
                //设置日期简略显示 否则详细显示 包括:星期\周
                datePicker.setCalendarViewShown(false);
                //初始化当前日期
                Calendar calendar = Calendar.getInstance(); 
                calendar.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                //设置date布局
                builder.setView(view);
                builder.setTitle(getResources().getString(R.string.setDate));//设置日期信息
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//确定
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	 Integer mHour = timePicker.getCurrentHour();
                    	 Integer mMinute = timePicker.getCurrentMinute();
                         //时间小于10的数字 前面补0 如01:12:00
                    	 StringBuffer time = new StringBuffer();
                         time.append(mHour < 10 ? "0" + mHour : mHour).append(":")
                                 .append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00");
                        //日期格式
                        StringBuffer date = new StringBuffer();
                        date.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        String allstr = date+" "+time;

                        alarTimeEnd.setText(allstr);
                     
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//取消
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
			}
		});
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String station_Name = stationName.getText().toString();
				String areaName = area.getText().toString();
				String alarmType = et_alarm_type.getText().toString();
				String alarmStartTime=alartTime.getText().toString();
				String alarmEndTime=alarTimeEnd.getText().toString();
				String alarmNamee=alarmName.getText().toString();
				Fragment newContent = new AlarmFragment();
				Bundle args = new Bundle();
				args.putString("stationName", station_Name);
				args.putString("areaName", areaName);
				args.putString("alarmType", alarmType);
				args.putString("alartTime", alarmStartTime);
				args.putString("alarTimeEnd", alarmEndTime);
				args.putString("alarmName", alarmNamee);
				args.putString("areaType", "1");
				newContent.setArguments(args);
				String title = "alarmYes";
				switchFragment(newContent, title);
			}
		});
		return mBaseView;
	}

	@Override
	public void onResume() {
		super.onResume();
		getFocus();
	}

	private void regetFocus() {
		Fragment newContent = new AlarmFragment();
		String title = "alarmYes";
		switchFragment(newContent, title);

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
					Fragment newContent = new AlarmFragment();
					String title = "alarmYes";
					switchFragment(newContent, title);

					return true;// 未处理
				}
				return false;
			}
		});
	}

	private void addlist() {
		System.out.println("dd" + areaName);
		items = areaName.split(";");
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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
