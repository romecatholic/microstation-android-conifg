package com.dgm.fragment;

import java.util.List;

import com.dgm.bean.UserInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description ������˵�
 */
public class LeftFragment extends Fragment implements OnClickListener {
	private View homeView;
	private View alarmListView;
	private View discussView;
	private View favoritesView;
	private View commentsView;
	private View settingsView;
	private DBManager dbMgr;
	private TextView username;
	private View image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu, null);
		dbMgr = new DBManager(getActivity());
		username =(TextView) view.findViewById(R.id.leftusername);
		SharedPreferences shared = getActivity().getSharedPreferences("saveUserNamePwd", getActivity().MODE_PRIVATE);
		// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
		String userid = shared.getString("name", "");// 同上，若没找到就让它为空""
		List<UserInfo> userlist =  dbMgr.queryUser(userid);
		username = (TextView) view.findViewById(R.id.leftusername);
		username.setText(userlist.get(0).getUsename());
		image = (com.dgm.view.CircleImageView) view.findViewById(R.id.profile_image);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Fragment newContent = null;
				String title = null;
				newContent = new UsersFragment();
				title = "users";
				switchFragment(newContent, title);
			}
		});
		findViews(view);

		return view;
	}

	public void findViews(View view) {
		homeView = view.findViewById(R.id.homePage);
		alarmListView = view.findViewById(R.id.tvAlarmlist);
		discussView = view.findViewById(R.id.tvDiscussMeeting);
		favoritesView = view.findViewById(R.id.tvMyFavorites);
		commentsView = view.findViewById(R.id.tvMyComments);
		settingsView = view.findViewById(R.id.tvMySettings);
		homeView.setOnClickListener(this);
		alarmListView.setOnClickListener(this);
		discussView.setOnClickListener(this);
		favoritesView.setOnClickListener(this);
		commentsView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		String title = null;
		switch (v.getId()) {
		case R.id.homePage: // ��ҳ
			newContent = new HomeFragment();
			title = "home";
			break;
		case R.id.tvAlarmlist:// �����б�
			newContent = new AlarmFragment();
			title = "alarmYes";
			break;
		case R.id.tvDiscussMeeting: // ��վ�б�
			newContent = new StationFragment();
			title = "station";
			break;
		case R.id.tvMyFavorites: // �ҵ��ղ�
			newContent = new DispatchFragment();
			title = "dispatch";
			break;
		case R.id.tvMyComments: // ��������
			newContent = new UsersFragment();
			title = "users";
			break;
		case R.id.tvMySettings: // ϵͳ����
			newContent = new SystemSettingFragment();
			title = "setting";
			break;
		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}

	/**
	 * �л�fragment
	 * 
	 * @param fragment
	 */
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
