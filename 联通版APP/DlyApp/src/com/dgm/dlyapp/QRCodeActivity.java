package com.dgm.dlyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class QRCodeActivity extends Activity {

	private EditText txtqrres;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		
		Intent it = new Intent(this,CaptureActivity.class);
		startActivityForResult(it, 10);
		
		txtqrres=(EditText)findViewById(R.id.txt_codeInfo);
		
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bundle bundle=data.getExtras();  
        String qrCode=bundle.getString("back");
        if(qrCode == null || qrCode.equals("") || qrCode.equals("null")){
        	Toast.makeText(this,getResources().getString(R.string.qrcodeToast), Toast.LENGTH_SHORT).show();//没有扫描到二维码信息
        }else{
        	//Toast.makeText(MainActivity.this, resultCode, Toast.LENGTH_LONG).show();
        	txtqrres.setText(qrCode);
        }
	}

}
