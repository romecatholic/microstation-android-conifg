package com.dgm.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dgm.bean.DeviceMuBanInfo;
import com.dgm.bean.UserInfo;
import com.dgm.dlyapp.R;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	private Context context;

	public DBManager(Context context) {
		this.context=context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	/**
	 * 
	 * @param userid
	 * @return
	 */
	public int queryUserCount(String userid) {
		Cursor c = db.rawQuery("SELECT * FROM UserInfo WHERE  USERID=?",
				new String[] { userid });
		int count = 0;
		while (c.moveToNext()) {
			count = c.getCount();
		}
		c.close();
		return count;
	}

	public int queryInterfaceCount() {
		Cursor c = db.rawQuery("SELECT * FROM InterfaceURLInfo ", null);
		int count = 0;
		while (c.moveToNext()) {
			count = c.getCount();
		}
		c.close();
		return count;
	}

	/**
	 * 
	 * @param userid
	 * @param purviewId
	 * @param areaId
	 * @param userName
	 * @param companyid
	 */
	public void addorUpdateUser(String userid, String purviewId, String areaId,
			String userName, String companyid) {
		int count = queryUserCount(userid);
		if (count == 0) {
			addUserInfo(userid, purviewId, areaId, userName, companyid);
		} else {
			ContentValues cv = new ContentValues();
			cv.put("AREAID", areaId);
			cv.put("PURVIEWID", purviewId);
			cv.put("USERNAME", userName);
			cv.put("companyid", companyid);
			db.update("UserInfo", cv, "USERID = ?", new String[] { userid });
		}
	}

	public void settingInterfaceUrl(String Ip, String Port) {
		String insert = "insert into InterfaceURLInfo (IP,PORT) VALUES('" + Ip
				+ "','" + Port + "')";
		db.execSQL(insert);
	}

	public void updateInterface(String Ip, String Port) {
		ContentValues cv = new ContentValues();
		cv.put("IP", Ip);
		cv.put("PORT", Port);
		db.update("InterfaceURLInfo", cv, null, null);
	}

	public String queryUserArea(String userid) {
		Cursor c = db.rawQuery("SELECT * FROM UserInfo where USERID=?",
				new String[] { userid });
		while (c.moveToNext()) {
			String areaName = c.getString(c.getColumnIndex("AREAID"));
			return areaName;
		}
		return "";
	}

	public String queryUserCompany(String userid) {
		Cursor c = db.rawQuery("SELECT * FROM UserInfo where USERID=?",
				new String[] { userid });
		while (c.moveToNext()) {
			String companyid = c.getString(c.getColumnIndex("companyid"));
			return companyid;
		}
		return "";
	}

	public String queryInterface() {
		System.out.println("111");
		Cursor c = db.rawQuery("SELECT * FROM InterfaceURLInfo", null);
		while (c.moveToNext()) {
			String url = c.getString(c.getColumnIndex("IP"));
			url = url + ":" + c.getString(c.getColumnIndex("PORT"));
			// System.out.println("qqq"+url);
			return url;
		}
		return "";
	}

	public void addUserInfo(String userid, String purviewId, String areaId,
			String userName, String companyid) {
		db.beginTransaction();
		try {
			String insert = "insert into UserInfo (USERID,PURVIEWID,AREAID,USERNAME,companyid) VALUES ('"
					+ userid
					+ "','"
					+ purviewId
					+ "','"
					+ areaId
					+ "','"
					+ userName + "','" + companyid + "')";
			db.execSQL(insert);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * 
	 * @param paramNum
	 * @param paramName
	 * @param equipemntID
	 * @param paramUseflag
	 * @param paramType
	 * @param paramUnit
	 * @param paramDispored
	 */
	public void addDeviceParam(String paramNum, String paramName,
			String equipemntID, String paramUseflag, String paramType,
			String paramUnit, String paramDispored) {
		db.beginTransaction();
		try {
			String insert = "insert into DeviceParamInfo (paramNum,paramName,equipemntID,paramUseflag,paramType,paramUnit,paramDispored) VALUES('"
					+ paramNum
					+ "','"
					+ paramName
					+ "','"
					+ equipemntID
					+ "','"
					+ paramUseflag
					+ "','"
					+ paramType
					+ "','"
					+ paramUnit + "','" + paramDispored + "')";
			Log.e("", insert);
			db.execSQL(insert);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

	}

	public List<DeviceMuBanInfo> queryMuBan(String equipmentId, String type) {
		List<DeviceMuBanInfo> deviceArray = new ArrayList<DeviceMuBanInfo>();
		Cursor c = queryMuban(equipmentId, type);
		while (c.moveToNext()) {
			DeviceMuBanInfo dmbi = new DeviceMuBanInfo();
			dmbi.setEquipemntID(c.getString(c.getColumnIndex("equipemntID")));
			dmbi.setParamName(c.getString(c.getColumnIndex("paramName")));
			dmbi.setParamNum(c.getString(c.getColumnIndex("paramNum")));
			dmbi.setParamType(c.getString(c.getColumnIndex("paramType")));
			dmbi.setParamUnit(c.getString(c.getColumnIndex("paramUnit")));
			deviceArray.add(dmbi);
		}
		c.close();
		return deviceArray;

	}

	public Cursor queryMuban(String equipmentId, String type) {
		Cursor c = db
				.rawQuery(
						"SELECT * FROM DeviceParamInfo where equipemntID=? and paramType=?",
						new String[] { equipmentId, type });
		return c;
	}

	public List<UserInfo> queryUser(String userid) {
		List<UserInfo> deviceArray = new ArrayList<UserInfo>();
		Cursor c = queryUserC(userid);
		while (c.moveToNext()) {
			UserInfo dmbi = new UserInfo();
			dmbi.setAreaId(c.getString(c.getColumnIndex("AREAID")));
			dmbi.setCompanyId(c.getString(c.getColumnIndex("companyid")));
			//dmbi.setPurviewId(c.getString(c.getColumnIndex("PURVIEWID")));
			dmbi.setUsename(c.getString(c.getColumnIndex("USERNAME")));
			if (c.getString(c.getColumnIndex("PURVIEWID")).equals("0")) {
				//dmbi.setPurviewId("超级管理员");
				dmbi.setPurviewId(context.getString(R.string.supermanager));
			} else if (c.getString(c.getColumnIndex("PURVIEWID")).equals("1")) {
				//dmbi.setPurviewId("管理员");
				dmbi.setPurviewId(context.getString(R.string.manager));
			} else if (c.getString(c.getColumnIndex("PURVIEWID")).equals("2")) {
				//dmbi.setPurviewId("普通用户");
				dmbi.setPurviewId(context.getString(R.string.normaluser));
			}else{
				//dmbi.setPurviewId("未知用户");
				dmbi.setPurviewId(context.getString(R.string.unknownuser));
				
			}
			dmbi.setUserId(c.getString(c.getColumnIndex("USERID")));
			deviceArray.add(dmbi);
		}
		c.close();
		return deviceArray;

	}

	public Cursor queryUserC(String userid) {
		Cursor c = db.rawQuery("SELECT * FROM UserInfo where USERID=?",
				new String[] { userid });
		return c;
	}

	public void deleteDeviceParam() {
		db.delete("DeviceParamInfo", "", null);
	}

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}

}
