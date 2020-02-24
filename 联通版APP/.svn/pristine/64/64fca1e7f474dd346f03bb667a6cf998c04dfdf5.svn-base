package com.dgm.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dgm.dlyapp.R;
import com.dgm.fragment.DigitalSwitchFragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CurrentProtectAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private List<Map<String, String>> ulist = new ArrayList<Map<String, String>>();
	private DigitalSwitchFragment d;
	private int touchItemPosition = -1;

	public CurrentProtectAdapter(Context context,List<Map<String,String>> ulist){
    	this.inflater = LayoutInflater.from(context);
    	this.ulist=ulist;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ulist.size();
	}

	@Override
	public Map<String, String> getItem(int position) {
		// TODO Auto-generated method stub
		return ulist.get(position);
	}

	@Override
	 public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
	}

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
    	ViewHolder holder = null;
    	if(convertView == null){
    		holder = new ViewHolder();
    		convertView = inflater.inflate(R.layout.list_item_cursaveinfo, null);
    		holder.textView= (TextView) convertView.findViewById(R.id.lics_text1);
    		holder.editText = (EditText) convertView.findViewById(R.id.dsf_sndValEdit);
    		holder.button = (Button) convertView.findViewById(R.id.dsf_secondSet);
    		holder.editText.setOnTouchListener(new OnTouchListener() {
    			@Override
    			public boolean onTouch(View view, MotionEvent motionEvent) {
    				touchItemPosition = (Integer) view.getTag();
    				return false;
    			}

    		});
    		holder.myTextWatcher = new MyTextWatcher();
    		holder.editText.addTextChangedListener(holder.myTextWatcher);
    		holder.updatePosition(position);
    		Map<String, String> keyValuePair = ulist.get(position);
    		holder.textView.setText(keyValuePair.get("name"));
    		holder.editText.setText(keyValuePair.get("value"));
    		holder.editText.setTag(position);
    		// 把查找的view缓存起来方便多次重用
    		convertView.setTag(holder);
    	}else{
    		holder = (ViewHolder) convertView.getTag();
    	}
    	
		// 默认是－1，肯定不等于，无需有焦点，当点击过输入框时，会进行赋值说明用户点击的是第几个，这时候会调用清除焦点，
		// 然后唤起键盘，页面刷新时，每一个Item都刷新，就会重新进来，而touchItemPosition有值，就会执行下面的请求焦点的代码
		if (touchItemPosition == position) {
			holder.editText.requestFocus();
			holder.editText.setSelection(holder.editText.getText()
					.length());
		} else {
			holder.editText.clearFocus();
		}

		holder.button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(d!=null){
					Map<String, String> k=ulist.get(position);
					String value=k.get("value");
					d.sendCurrentProtect(position,value);
				}
			}
		});
		return convertView;
	}
    
	static final class ViewHolder {
		public TextView textView;
		public EditText editText;
		public Button button;
		public MyTextWatcher myTextWatcher;
 
		public void updatePosition(int myPosition) {
			myTextWatcher.updateWatcherPosition(myPosition);
		}
 
	}
 
	class MyTextWatcher implements TextWatcher {
		private int currentPosition;
 
		public void updateWatcherPosition(int myPosition) {
			currentPosition = myPosition;
		}
 
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
 
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
 
		@Override
		public void afterTextChanged(Editable s) {
			Map<String, String> myItem =ulist.get(currentPosition);
			String ss=myItem.get("name");
			myItem.put("value", s.toString());
			//myItem.setEditItem(s.toString());//每次变化，设置对应的类的editItem
		}
	}
    
	public DigitalSwitchFragment getD() {
		return d;
	}
	public void setD(DigitalSwitchFragment d) {
		this.d = d;
	}
}
