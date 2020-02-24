package com.dgm.dlyapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgm.fragment.AlarmFragment;
import com.dgm.fragment.AlarmNoConfirmFragment;
import com.dgm.fragment.AlarmNoConfirmSearchFragment;
import com.dgm.fragment.AlarmSearchFragment;
import com.dgm.fragment.DispatchFragment;
import com.dgm.fragment.DispatchSearchFragment;
import com.dgm.fragment.EquipmentDetaileFragment;
import com.dgm.fragment.HomeFragment;
import com.dgm.fragment.LeftFragment;
import com.dgm.fragment.StationDetailFragment;
import com.dgm.fragment.StationFragment;
import com.dgm.fragment.StationSearchFragment;
import com.dgm.fragment.SystemSettingFragment;
import com.dgm.view.TitleBarView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description 主界面
 */
public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {

	private ImageView topButton;
	private Fragment mContent;
	private TextView topTextView;
	/** 主界面 */
	private HomeFragment mHomeFragment;

	private TitleBarView mTitleBarView;
	
	public String userId;//登录用户

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSlidingMenu(savedInstanceState);
		topTextView = (TextView) findViewById(R.id.title_txt);
	}

	/**
	 * 初始化侧边栏
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}
		if (savedInstanceState == null) {
			mHomeFragment = new HomeFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, mHomeFragment, "Home")
					.commit();
			mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			mTitleBarView.setTitleText(getResources().getString(R.string.mainTitle));//动力源环境监控
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					toggle();
				}

			});
		}
		if (mContent == null) {
			mContent = new HomeFragment();
		}

		// 设置左侧滑动菜单
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new LeftFragment()).commit();

		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动菜单阴影的图像资源
		sm.setShadowDrawable(null);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式,这里设置为全屏
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.0f);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState); 
		if (mContent.isAdded()){
			getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	    }
	//getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	/**
	 * 切换Fragment
	 * 
	 * @param fragment
	 */
	public void switchConent(Fragment fragment, String title) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().setCustomAnimations(R.layout.slide_right_in, R.layout.slide_left_out)
				.replace(R.id.content_frame, fragment).commit();

		getSlidingMenu().showContent();

		if (title.compareTo("home") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.mainTitle));//动力源环境监控
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
		} else if (title.compareTo("alarmYes") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.VISIBLE,
					View.VISIBLE);
			mTitleBarView.setTitleLeft(R.string.alarm_quren);
			mTitleBarView.setTitleRight(R.string.alarm_weiquren);
			// 顶部左侧侧滑栏菜单按钮
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					toggle();
				}
			});
			// 顶部最右侧查询按钮
			mTitleBarView.setBtnRight(R.drawable.ic_top_bar_search);
			// 告警界面顶部左侧已确认告警按钮监听
			mTitleBarView.getTitleLeft().setOnClickListener(
					new OnClickListener() {
						@SuppressLint("NewApi")
						@Override
						public void onClick(View v) {
							if (mTitleBarView.getTitleLeft().isEnabled()) {
								Log.e("getTitleLeft@@@", "@@@@@@@"
										+ mTitleBarView.getTitleLeft()
												.isEnabled());
								mTitleBarView.getTitleLeft().setEnabled(false);
								mTitleBarView.getTitleRight().setEnabled(true);
								AlarmFragment afragment = new AlarmFragment();
								mContent = afragment;
								getSupportFragmentManager()
										.beginTransaction()
										.replace(R.id.content_frame, afragment,
												"").commit();
							}
						}
					});
			// 告警界面顶部右侧侧未确认告警按钮监听
			mTitleBarView.getTitleRight().setOnClickListener(
					new OnClickListener() {
						@SuppressLint("NewApi")
						@Override
						public void onClick(View v) {
							if (mTitleBarView.getTitleRight().isEnabled()) {
								Log.e("getTitleRight@@@", "@@@@@@@"
										+ mTitleBarView.getTitleLeft()
												.isEnabled());
								mTitleBarView.getTitleLeft().setEnabled(true);
								mTitleBarView.getTitleRight().setEnabled(false);
								AlarmNoConfirmFragment afragment = new AlarmNoConfirmFragment();
								mContent = afragment;
								getSupportFragmentManager()
										.beginTransaction()
										.replace(R.id.content_frame, afragment,
												"").commit();
							}
						}
					});
			mTitleBarView.getTitleLeft().performClick();// 默认顶部左侧按钮选择
			mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (mTitleBarView.getTitleLeft().isEnabled()) {
						Log.e("getTitleRight@@@", "@@@@@@@alarmNoConfirmSearch");
						alarmNoConfirmSearch();
					} else {
						Log.e("getTitleRight@@@", "@@@@@@@alarmSearch");
						alarmSearch();
					}
				}
			});

		} else if (title.compareTo("alarmNo") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.VISIBLE,
					View.VISIBLE);
			mTitleBarView.setTitleLeft(R.string.alarm_quren);
			mTitleBarView.setTitleRight(R.string.alarm_weiquren);
			// 顶部左侧侧滑栏菜单按钮
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					toggle();
				}
			});
			// 顶部最右侧查询按钮
			mTitleBarView.setBtnRight(R.drawable.ic_top_bar_search);
			// 告警界面顶部左侧已确认告警按钮监听
			mTitleBarView.getTitleLeft().setOnClickListener(
					new OnClickListener() {
						@SuppressLint("NewApi")
						@Override
						public void onClick(View v) {
							if (mTitleBarView.getTitleLeft().isEnabled()) {
								mTitleBarView.getTitleLeft().setEnabled(false);
								mTitleBarView.getTitleRight().setEnabled(true);
								AlarmFragment afragment = new AlarmFragment();
								mContent = afragment;
								getSupportFragmentManager()
										.beginTransaction()
										.replace(R.id.content_frame, afragment,
												"").commit();
							}
						}
					});
			// 告警界面顶部右侧侧未确认告警按钮监听
			mTitleBarView.getTitleRight().setOnClickListener(
					new OnClickListener() {
						@SuppressLint("NewApi")
						@Override
						public void onClick(View v) {
							if (mTitleBarView.getTitleRight().isEnabled()) {
								mTitleBarView.getTitleLeft().setEnabled(true);
								mTitleBarView.getTitleRight().setEnabled(false);
								AlarmNoConfirmFragment afragment = new AlarmNoConfirmFragment();
								mContent = afragment;
								getSupportFragmentManager()
										.beginTransaction()
										.replace(R.id.content_frame, afragment,
												"").commit();
							}

						}
					});
			mTitleBarView.getTitleRight().performClick();// 默认顶部左侧按钮选择
			mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (mTitleBarView.getTitleLeft().isEnabled()) {
						alarmNoConfirmSearch();
					} else {
						alarmSearch();
					}

				}
			});
		} else if (title.compareTo("alarmsearch") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.alarmQuery));//告警查询
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backAlarm();
				}
			});
		} else if (title.compareTo("alarmNosearch") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.alarmQuery));//告警查询
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backAlarmNoConfirm();
				}
			});
		} else if (title.compareTo("station") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.VISIBLE);
			topTextView.setText(getResources().getString(R.string.stationlist));//站址列表
			mTitleBarView.setBtnRight(R.drawable.ic_top_bar_search);
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					toggle();
				}
			});
			mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					stationSearch();
				}
			});
		} else if (title.compareTo("stationsearch") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.stationQuery));//站址查询
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backStation();
				}
			});
		} else if (title.compareTo("stationdetail") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.stationDetail));//基站详情
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backStation();
				}
			});
		} else if (title.compareTo("equipmentdetail") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.realData));//实时数据
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backStationDetail();
				}
			});
		}else if (title.compareTo("dispatch") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.VISIBLE);
			topTextView.setText(getResources().getString(R.string.dispatchManage));//派单管理
			mTitleBarView.setBtnRight(R.drawable.ic_top_bar_search);
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					toggle();
				}
			});
			mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dispatchSearch();
				}
			});
		} else if (title.compareTo("dispatchsearch") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.dispatchQuery));//派单查询
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backDispatch();
				}
			});
		}else if (title.compareTo("setting") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.systemSet));//系统设置
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_category);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					toggle();
				}
			});
		} else if (title.compareTo("datainterface") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.dataUpdate));//数据接口升级
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backSetting();
				}
			});
		} else if (title.compareTo("users") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.personCenter));//个人中心
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					toggle();
				}
			});
		}else if (title.compareTo("alarminfo") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.alarmDetail));//告警详情
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backAlarmNoConfirm();
				}
			});
		}else if (title.compareTo("alarmdetail") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.alarmDetail));//告警详情
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backAlarm();
				}
			});
		}else if (title.compareTo("chart") == 0) {
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.realdataDetail));//详细数据
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backModata();
				}
			});
		}if (title.compareTo("qrcode") == 0){
			mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
					View.GONE);
			topTextView.setText(getResources().getString(R.string.cap_scan));//二维码扫描
			mTitleBarView.setBtnLeft(R.drawable.ic_top_bar_back);
			mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					backHome();
				}
			});
		}
	}

	private void backSetting() {
		Fragment newContent = new SystemSettingFragment();
		String title = "setting";
		switchConent(newContent, title);
	}

	private void alarmNoConfirmSearch() {
		Fragment newContent = new AlarmNoConfirmSearchFragment();
		String title = "alarmNosearch";
		switchConent(newContent, title);
	}

	private void alarmSearch() {
		Fragment newContent = new AlarmSearchFragment();
		String title = "alarmsearch";
		switchConent(newContent, title);
	}
	
	private void dispatchSearch() {
		Fragment newContent = new DispatchSearchFragment();
		String title = "dispatchsearch";
		switchConent(newContent, title);
	}
	
	private void backDispatch() {
		Fragment newContent = new DispatchFragment();
		String title = "dispatch";
		switchConent(newContent, title);
	}
	

	private void stationSearch() {
		Fragment newContent = new StationSearchFragment();
		String title = "stationsearch";
		switchConent(newContent, title);
	}

	private void backAlarm() {
		Fragment newContent = new AlarmFragment();
		String title = "alarmYes";
		switchConent(newContent, title);
	}

	private void backAlarmNoConfirm() {
		Fragment newContent = new AlarmNoConfirmFragment();
		String title = "alarmNo";
		switchConent(newContent, title);
	}
	private void backModata() {
		Fragment newContent = new EquipmentDetaileFragment();
		String title = "equipmentdetail";
		switchConent(newContent, title);
	}

	private void backStation() {
		Fragment newContent = new StationFragment();
		String title = "station";
		switchConent(newContent, title);
	}

	private void backStationDetail() {
		Fragment newContent = new StationDetailFragment();
		Bundle args = new Bundle();
		args.putString("stationId", StationDetailFragment.stationId);
		args.putString("backButton", "backButton");
		newContent.setArguments(args);
		String title = "stationdetail";
		switchConent(newContent, title);
	}
	private void backHome(){
		Fragment newContent = new HomeFragment();
		String title = "home";
		switchConent(newContent, title);
	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setTitle(getResources().getString(R.string.quitCommit))//确定要退出吗?
				.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {//确定

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 注意本行的FLAG设置
						startActivity(intent);
					}
				})
				.setNegativeButton(getResources().getString(R.string.cap_back), new DialogInterface.OnClickListener() {//返回

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					}
				}).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topButton:
			toggle();
			break;
		default:
			break;
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
