package com.dgm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Dly_Data.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		// CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		//登录账户信息保存本地数据库 USERID用户ID，PURVIEWID权限类别0超级管理员，1管理员、2普通用户，AREAID 用户管理区域，USERNAME用户名，companyid用户厂家信息
		db.execSQL("CREATE TABLE IF NOT EXISTS UserInfo"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,USERID VARCHAR,PURVIEWID VARCHAR,AREAID VARCHAR,USERNAME VARCHAR,companyid VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS DeviceParamInfo"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,paramNum VARCHAR,paramName VARCHAR,equipemntID VARCHAR,paramUseflag VARCHAR,paramType VARCHAR,paramUnit VARCHAR,paramDispored VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS InterfaceURLInfo"
				+ "(IP VARCHAR,PORT VARCHAR)");
	}

	// 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 if (oldVersion != newVersion) {  
			   db.execSQL("DROP TABLE IF EXISTS UserInfo");
			   db.execSQL("DROP TABLE IF EXISTS DeviceParamInfo");
		 }
		onCreate(db);
	}

}
