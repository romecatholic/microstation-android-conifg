package com.dgm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Dly_Data.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		// CursorFactory����Ϊnull,ʹ��Ĭ��ֵ
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		//��¼�˻���Ϣ���汾�����ݿ� USERID�û�ID��PURVIEWIDȨ�����0��������Ա��1����Ա��2��ͨ�û���AREAID �û���������USERNAME�û�����companyid�û�������Ϣ
		db.execSQL("CREATE TABLE IF NOT EXISTS UserInfo"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,USERID VARCHAR,PURVIEWID VARCHAR,AREAID VARCHAR,USERNAME VARCHAR,companyid VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS DeviceParamInfo"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,paramNum VARCHAR,paramName VARCHAR,equipemntID VARCHAR,paramUseflag VARCHAR,paramType VARCHAR,paramUnit VARCHAR,paramDispored VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS InterfaceURLInfo"
				+ "(IP VARCHAR,PORT VARCHAR)");
	}

	// ���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 if (oldVersion != newVersion) {  
			   db.execSQL("DROP TABLE IF EXISTS UserInfo");
			   db.execSQL("DROP TABLE IF EXISTS DeviceParamInfo");
		 }
		onCreate(db);
	}

}
