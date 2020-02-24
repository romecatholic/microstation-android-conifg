package com.dgm.fragment;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgm.dlyapp.EquipDebugActivity;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.view.MyGridLayout;
import com.dgm.view.MyGridLayout.GridAdatper;
import com.dgm.view.MyGridLayout.OnItemClickListener;
import com.dgm.view.TitleBarView;

public class HomeFragment extends Fragment {
	private MyGridLayout grid;
	int[] srcs = { R.drawable.actions_bluetooth, R.drawable.actions_comment,
			R.drawable.actions_order, R.drawable.actions_account,
			R.drawable.actions_cent, R.drawable.actions_weibo,
			R.drawable.actions_scans,R.drawable.actions_cucc};
	String titles[];

	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	
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
		mBaseView = inflater.inflate(R.layout.frag_home_page, null);
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(getResources().getString(R.string.hm_home));//首页
		mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
		mTitleBarView.setBtnRight(R.drawable.ic_top_bar_category);
		grid = (MyGridLayout) mBaseView.findViewById(R.id.list);
		titles=new String[]{ getResources().getString(R.string.hm_device),getResources().getString(R.string.hm_alarmmanage),getResources().getString(R.string.hm_station),getResources().getString(R.string.hm_dispatch),getResources().getString(R.string.hm_personal),getResources().getString(R.string.hm_system),getResources().getString(R.string.cap_scan),getResources().getString(R.string.home_page) };//	设备调试	告警管理	站址管理	派单管理	个人中心	系统设置	二维码扫描	主页
		grid.setGridAdapter(new GridAdatper() {

			@Override
			public View getView(int index) {
				View view = getActivity().getLayoutInflater().inflate(R.layout.actions_item,
						null);
				ImageView iv = (ImageView) view.findViewById(R.id.iv);
				TextView tv = (TextView) view.findViewById(R.id.tv);
				iv.setImageResource(srcs[index]);
				tv.setText(titles[index]);
				return view;
			}

			@Override
			public int getCount() {
				return titles.length;
			}
		});
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View v, int index) {
				if(index==0){
//					Fragment newContent = new HomeFragment();
//					String title = "home";
//					switchFragment(newContent, title);
					
					Intent intent = new Intent(mBaseView.getContext(),
							EquipDebugActivity.class);
					startActivity(intent);					
				}
				if(index==1){
					Fragment newContent = new AlarmFragment();
					String title = "alarmYes";
					switchFragment(newContent, title);
				}
				if(index==2){
					Fragment newContent = new StationFragment();
					String title = "station";
					switchFragment(newContent, title);
				}
				if(index==3){
					Fragment newContent = new DispatchFragment();
					String title = "dispatch";
					switchFragment(newContent, title);
				}
				if(index==4){
					Fragment newContent = new UsersFragment();
					String title = "users";
					switchFragment(newContent, title);
				}
				if(index==5){
					Fragment newContent = new SystemSettingFragment();
					String title = "setting";
					switchFragment(newContent, title);
				}
				if(index==6){//二维码扫描
					Fragment newContent=new QRCodeFragment();
					String title="qrcode";
					switchFragment(newContent,title);
				}
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


	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& keyCode == KeyEvent.KEYCODE_BACK) {
			// 监听到返回按钮点击事件
			//Intent intent = new Intent(this,LoginActivity.class);
			//startActivity(intent);
		   dialog_Exit(getActivity());
			return true;// 未处理
		}
		return false;
	}
	
	
	public static void dialog_Exit(Context context) {
        AlertDialog.Builder builder = new Builder(context);
        builder.setMessage(context.getResources().getString(R.string.quitCommit));//确定要退出吗?
        builder.setTitle(context.getResources().getString(R.string.hm_tip));//提示
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(context.getResources().getString(R.string.yes),//确定
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        android.os.Process.killProcess(android.os.Process
                                .myPid());
                    }
                });
       
        builder.setNegativeButton(context.getResources().getString(R.string.cancel),//取消
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(0);//正常退出App 
                    }
                });
       
        builder.create().show();
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
