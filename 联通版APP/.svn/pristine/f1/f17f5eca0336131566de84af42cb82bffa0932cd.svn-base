package com.dgm.fragment;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgm.bean.UserInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;

public class UsersFragment extends Fragment {

	private View mBaseView;
	private Context mContext;
	private TextView username,usertype,userarea,usercompany,userop;
	private DBManager dbMgr;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		dbMgr = new DBManager(getActivity());
		SharedPreferences shared = getActivity().getSharedPreferences("saveUserNamePwd", getActivity().MODE_PRIVATE);
		// 第一个参数就是关键字，第二个参数为默认值，意思是说如果没找到值就用默认值代替
		String userid = shared.getString("name", "");// 同上，若没找到就让它为空""
		List<UserInfo> userlist =  dbMgr.queryUser(userid);
		
		mBaseView = inflater.inflate(R.layout.user_fragment, null);
		username = (TextView) mBaseView.findViewById(R.id.username);
		username.setText(userlist.get(0).getUsename());
		usertype= (TextView) mBaseView.findViewById(R.id.usertype);
		usertype.setText(userlist.get(0).getPurviewId());
		userarea= (TextView) mBaseView.findViewById(R.id.userarea);
		userarea.setText(userlist.get(0).getAreaId());
		usercompany= (TextView) mBaseView.findViewById(R.id.usercompany);
		usercompany.setText(userlist.get(0).getCompanyId());
		userop= (TextView) mBaseView.findViewById(R.id.useroperator);
	//	userop.setText(userlist.get(0).getCompanyId());
		return mBaseView;
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
