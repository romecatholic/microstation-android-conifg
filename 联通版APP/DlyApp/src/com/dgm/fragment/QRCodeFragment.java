package com.dgm.fragment;

import com.dgm.dlyapp.CaptureActivity;
import com.dgm.dlyapp.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class QRCodeFragment  extends Fragment{
	private Context mContext;
	private View mBaseView;
	private EditText txtqrres;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mBaseView = inflater.inflate(R.layout.activity_qrcode, null);
		
		mContext = getActivity();
		Intent it = new Intent(mContext,CaptureActivity.class);
		startActivityForResult(it, 10);
		
		txtqrres=(EditText)mBaseView.findViewById(R.id.txt_codeInfo);
				
		return mBaseView;
	}
	

}
