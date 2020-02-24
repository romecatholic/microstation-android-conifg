package com.dgm.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dgm.bean.ValuesChatInfo;
import com.dgm.db.DBManager;
import com.dgm.dlyapp.MainActivity;
import com.dgm.dlyapp.R;
import com.dgm.http.HttpReq;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;

public class ValuesChartFragment extends Fragment {
	private View mBaseView;
	private Context mContext;
	private LineChart mChart;
	private DBManager dbMgr;
	private String deviceId = "";
	private String typeM = "";
	private String eqName = "";
	private String unit = "";
	private static ProgressDialog pd;
	private TextView tv_unit;
	private List<ValuesChatInfo> oneTotal = new ArrayList<ValuesChatInfo>();// 用来存放一页数据

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		dbMgr = new DBManager(getActivity());
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.values_chart_main, null);
		mChart = (LineChart) mBaseView.findViewById(R.id.chart1);
		tv_unit = (TextView) mBaseView.findViewById(R.id.unit);
		deviceId = EquipmentDetaileFragment.s_deviceId;
		if (getArguments() != null) {

			typeM = getArguments().getString("typeM");
			unit = getArguments().getString("unit");
			eqName = getArguments().getString("eqName");

		}

		new LoadDataAsyncTask(ValuesChartFragment.this).execute();
		// 刷新图表
		pd = new ProgressDialog(mContext);
		pd.setIndeterminate(true);
		pd.setMessage(getResources().getString(R.string.loading));//数据正在加载中...
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		return mBaseView;
	}

	private class LoadDataAsyncTask extends AsyncTask<String, Void, String> {

		private ValuesChartFragment valueFragment;

		public LoadDataAsyncTask(ValuesChartFragment valueFragment) {
			this.valueFragment = valueFragment;
		}

		protected String doInBackground(String... arg0) {

			String IP = "http://";
			dbMgr = new DBManager(getActivity());
			String IPPORT = dbMgr.queryInterface();
			String url = IP + IPPORT + "/DlyAppSever/GraphDataServlet";
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();

			list.add(new BasicNameValuePair("deviceId", deviceId));// 搜索告警基站区域
			list.add(new BasicNameValuePair("typeM", typeM));// 搜索告警等级
			String backString = (String) HttpReq.topostData(url, list);
			System.out.println(backString);
			return backString;
		}

		/**
		 * 完成时的方法
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.e("@@@@@@@@", "" + result);
			if (result != null) {
				// 如果获取的result数据不为空，那么对其进行JSON解析。并显示在手机屏幕上。
				List<ValuesChatInfo> list = JSONAnalysis(result);

				if (list == null) {
					Toast.makeText(mContext,
							getResources().getString(R.string.vs_Toast), Toast.LENGTH_LONG)//获取数据失败！
							.show();
					Fragment newContent = new EquipmentDetaileFragment();
					String title = "equipmentdetail";
					switchFragment(newContent, title);
				} else if (list.size() != 0) {

					// 设置在Y轴上是否是从0开始显示
					mChart.setStartAtZero(true);
					// 是否在Y轴显示数据，就是曲线上的数据
					mChart.setDrawYValues(true);
					// 设置网格
					mChart.setDrawBorder(true);
					mChart.setBorderPositions(new BorderPosition[] { BorderPosition.BOTTOM });
					// 在chart上的右下角加描述
					mChart.setDescription(getResources().getString(R.string.vs_des));//两小时内数据
					// 设置Y轴上的单位
					// mChart.setUnit(unit);
					tv_unit.setText(getResources().getString(R.string.vs_unit) + unit);//单位:
					tv_unit.setTextColor(Color.parseColor("#2F4F4F"));
					// 设置透明度
					mChart.setAlpha(0.8f);
					// 设置网格底下的那条线的颜色
					mChart.setBorderColor(Color.rgb(0, 0, 225));
					// 设置Y轴前后倒置
					mChart.setInvertYAxisEnabled(false);
					// 设置高亮显示
					mChart.setHighlightEnabled(true);
					// 设置是否可以触摸，如为false，则不能拖动，缩放等
					mChart.setTouchEnabled(true);
					// 设置是否可以拖拽，缩放
					mChart.setDragEnabled(true);
					mChart.setScaleEnabled(true);
					// 设置是否能扩大扩小
					mChart.setPinchZoom(true);
					Typeface tf = Typeface.createFromAsset(getActivity()
							.getAssets(), "OpenSans-Regular.ttf");
					mChart.setValueTypeface(tf);
					// 设置背景颜色

					XLabels xl = mChart.getXLabels();
					// xl.setAvoidFirstLastClipping(true);
					// xl.setAdjustXLabels(true);
					xl.setPosition(XLabelPosition.BOTTOM); // 设置X轴的数据在底部显示
					xl.setTypeface(tf); // 设置字体
					xl.setTextSize(10f); // 设置字体大小
					xl.setSpaceBetweenLabels(2); // 设置数据之间的间距

					YLabels yl = mChart.getYLabels();
					// yl.setPosition(YLabelPosition.LEFT_INSIDE); // set the
					// position
					yl.setTypeface(tf); // 设置字体
					yl.setTextSize(10f); // s设置字体大小
					yl.setLabelCount(10); // 设置Y轴最多显示的数据个数
					// 加载数据

					ArrayList<String> xVals = new ArrayList<String>();
					ArrayList<Entry> yVals = new ArrayList<Entry>();
					for (int i = 0; i < list.size(); i++) {
						xVals.add(list.get(i).getTime());
						yVals.add(new Entry(Float.parseFloat(list.get(i)
								.getData()), i));

					}
					// create a dataset and give it a type
					LineDataSet set1 = new LineDataSet(yVals, eqName);

					set1.setDrawCubic(false); // 设置曲线为圆滑的线
					set1.setCubicIntensity(0.2f);
					set1.setDrawFilled(false); // 设置包括的范围区域填充颜色
					set1.setDrawCircles(true); // 设置有圆点
					set1.setLineWidth(3f); // 设置线的宽度
					set1.setCircleSize(5f); // 设置小圆的大小
					set1.setHighLightColor(Color.rgb(244, 117, 117));
					set1.setColor(Color.rgb(135, 206, 250)); // 设置曲线的颜色

					// create a data object with the datasets
					LineData data = new LineData(xVals, set1);

					// set data
					mChart.setData(data);
					// 从X轴进入的动画
					mChart.animateX(0);
					mChart.animateY(0); // 从Y轴进入的动画
				//	mChart.animateXY(2000, 2000); // 从XY轴一起进入的动画

					// 设置最小的缩放
					mChart.setScaleMinima(0.5f, 1f);
					// 设置视口
					// mChart.centerViewPort(10, 50);

					// get the legend (only possible after setting data)
					Legend l = mChart.getLegend();
					l.setForm(LegendForm.LINE); // 设置图最下面显示的类型
					l.setTypeface(tf);
					l.setTextSize(15);
					l.setTextColor(Color.rgb(135, 206, 250));
					l.setFormSize(30f); // set the size of the legend
										// forms/shapes

					// 刷新图表
					mChart.invalidate();
					ValuesChartFragment.pd.dismiss();
				} else {
					ValuesChartFragment.pd.dismiss();
					Toast.makeText(mContext,
							getResources().getString(R.string.vs_Toast2), Toast.LENGTH_LONG)//该设备最近两小时内没有上传数据！
							.show();
					Fragment newContent = new EquipmentDetaileFragment();
					String title = "equipmentdetail";
					switchFragment(newContent, title);
				}

			} else {
				ValuesChartFragment.pd.dismiss();
				Toast.makeText(mContext,
						getResources().getString(R.string.vs_Toast), Toast.LENGTH_LONG)//获取数据失败！
						.show();
				Fragment newContent = new EquipmentDetaileFragment();
				String title = "equipmentdetail";
				switchFragment(newContent, title);
			}
		}

	}

	public List<ValuesChatInfo> JSONAnalysis(String result) {
		oneTotal.clear();// 解析之前 先清理一下，以保证只存储该页的数据，避免重复。
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String backMsg = jsonObject.getString("msg");
			String code = jsonObject.getString("code");
			if (code.equals("3")) {
				oneTotal.isEmpty();
				return oneTotal;

			} else {
				if (backMsg.compareTo("success") == 0) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObjectSon = (JSONObject) jsonArray
								.opt(i);
						String data = jsonObjectSon.getString("dataValue");
						String time = jsonObjectSon.getString("dataTime");
						if (data.equals("")) {
							data = "0.0";
						}
						ValuesChatInfo ai = new ValuesChatInfo();
						ai.setData(data);
						ai.setTime(time);

						oneTotal.add(ai);
					}
					return oneTotal;// 只返回该页的数据

				} else {
					return null;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
					Fragment newContent = new EquipmentDetaileFragment();
					String title = "equipmentdetail";
					switchFragment(newContent, title);

					return true;// 未处理
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
}
