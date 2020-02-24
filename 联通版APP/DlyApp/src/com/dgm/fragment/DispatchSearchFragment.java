package com.dgm.fragment;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

public class DispatchSearchFragment extends Fragment {

	private View mBaseView;
	private String[] items;
	private DBManager dbMgr;
	private Context mContext;
	private EditText alartTime,alarTimeEnd;
	private EditText stationName;
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
		mBaseView = inflater.inflate(R.layout.frag_dispatch_search_page, null);
		dbMgr = new DBManager(mContext);
		SharedPreferences shared = getActivity().getSharedPreferences(
				"saveUserNamePwd", getActivity().MODE_PRIVATE);
		// ��һ���������ǹؼ��֣��ڶ�������ΪĬ��ֵ����˼��˵���û�ҵ�ֵ����Ĭ��ֵ����
		String userid = shared.getString("name", "");// ͬ�ϣ���û�ҵ�������Ϊ��""
		stationName = (EditText) mBaseView
				.findViewById(R.id.dispatch_search_staiton_name);
		alartTime = (EditText) mBaseView.findViewById(R.id.dispatch_inputtime);
		alarTimeEnd = (EditText) mBaseView.findViewById(R.id.dispatch_inputtimeend);
		searchButton = (Button) mBaseView.findViewById(R.id.dispatch_search);

		alartTime.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					// �ر������
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(alartTime.getWindowToken(), 0);
					// ʹ�ø�View���»�ȡ���㣬�Լ������ؼ�
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
					// �ر������
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(stationName.getWindowToken(), 0);
					// ʹ�ø�View���»�ȡ���㣬�Լ������ؼ�
					regetFocus();
					return true;
				}
				return false;
			}
		});
		alartTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���������
				 //ͨ���Զ���ؼ�AlertDialogʵ��
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(mContext, R.layout.time_dialog, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
                //�������ڼ�����ʾ ������ϸ��ʾ ����:����\��
                datePicker.setCalendarViewShown(false);
                //��ʼ����ǰ����
                Calendar calendar = Calendar.getInstance(); 
                calendar.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                //����date����
                builder.setView(view);
                builder.setTitle(getResources().getString(R.string.setDate));//����������Ϣ
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//ȷ��
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	 Integer mHour = timePicker.getCurrentHour();
                    	 Integer mMinute = timePicker.getCurrentMinute();
                         //ʱ��С��10������ ǰ�油0 ��01:12:00
                    	 StringBuffer time = new StringBuffer();
                         time.append(mHour < 10 ? "0" + mHour : mHour).append(":")
                                 .append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00");
                        //���ڸ�ʽ
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
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//ȡ��
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
				// ���������
				 //ͨ���Զ���ؼ�AlertDialogʵ��
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(mContext, R.layout.time_dialog, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
                //�������ڼ�����ʾ ������ϸ��ʾ ����:����\��
                datePicker.setCalendarViewShown(false);
                //��ʼ����ǰ����
                Calendar calendar = Calendar.getInstance(); 
                calendar.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                //����date����
                builder.setView(view);
                builder.setTitle(getResources().getString(R.string.setDate));//����������Ϣ
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//ȷ��
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	 Integer mHour = timePicker.getCurrentHour();
                    	 Integer mMinute = timePicker.getCurrentMinute();
                         //ʱ��С��10������ ǰ�油0 ��01:12:00
                    	 StringBuffer time = new StringBuffer();
                         time.append(mHour < 10 ? "0" + mHour : mHour).append(":")
                                 .append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00");
                        //���ڸ�ʽ
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
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {//ȡ��
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
				String alarmStartTime=alartTime.getText().toString();
				String alarmEndTime=alarTimeEnd.getText().toString();
				Fragment newContent = new DispatchFragment();
				Bundle args = new Bundle();
				args.putString("stationName", station_Name);
				args.putString("alarmTime", alarmStartTime);
				args.putString("alarmTimeEnd", alarmEndTime);
				args.putString("dateType", "1");
				newContent.setArguments(args);
				String title = "dispatch";
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
		Fragment newContent = new DispatchFragment();
		String title = "dispatch";
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
					// ���������ذ�ť����¼�
					Fragment newContent = new DispatchFragment();
					String title = "dispatch";
					switchFragment(newContent, title);

					return true;// δ����
				}
				return false;
			}
		});
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
