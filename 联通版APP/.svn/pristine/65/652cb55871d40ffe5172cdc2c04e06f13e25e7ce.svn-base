package com.dgm.bean;

import android.content.Context;

import com.dgm.dlyapp.R;

/**
 * 新建内部类，存储模块n的状态
 */
public class ModelBean{
	private int openOrClose;	//开关机状态
	private int currentlimit;	//限流状态
	private int charge;		//充电状态
	private int modeError;	//模块状态
	private int fengshan;	//风扇状态
	private int acOver;	//ac过压
	private int dcOver; //dc过压
	private int alarmModeOver;	//模块过温状态
	private int alarmCommunication; //通信状态
	private String tmp;//温度
	private String mkTmp;//模块温度
	
	private String inputV;//输入电压
	private String inputA;//输入电流
	private String outputA;//输出电流
	private Context context;
	public ModelBean(Context context){
		this.context=context;
	}
	
	public String getAcOver() {
		if (acOver == 00) {
			return context.getString(R.string.normal);//正常
		} else{
			return context.getString(R.string.fault);//故障
		}
	}

	public void setAcOver(int acOver) {
		this.acOver = acOver;
	}

	public String getDcOver() {
		if (dcOver == 00) {
			return context.getString(R.string.normal);//正常
		} else{
			return context.getString(R.string.fault);//故障
		}
	}

	public void setDcOver(int dcOver) {
		this.dcOver = dcOver;
	}

	public void setModeError(int modeError){
		this.modeError=modeError;
	}
	public void setFengshan(int fengshan){
		this.fengshan=fengshan;
	}
	public void setAlarmModeOver(int alarmModeOver){
		this.alarmModeOver=alarmModeOver;
	}
	public void setAlarmCommnunication(int alarmCommunication){
		this.alarmCommunication=alarmCommunication;
	}
	
	public String getModeError(){
		if (modeError == 00) {
			return context.getString(R.string.normal);//正常
		} else{
			return context.getString(R.string.fault);//故障
		}
	}
	
	public String getFengshan(){
		if (fengshan == 00) {
			return context.getString(R.string.normal);//正常
		} else{
			return context.getString(R.string.fault);//故障
		}
	}
	
	public String getAlarmModeOver(){
		if (alarmModeOver == 00) {
			return context.getString(R.string.normal);//正常
		} else{
			return context.getString(R.string.fault);//故障
		}
	}

	public String getAlarmCommunication(){
		if (alarmCommunication == 00) {
			return context.getString(R.string.normal);//正常
		} else{
			return context.getString(R.string.fault);//故障
		}
	}
	
	public String getOpenOrClose() {
		if(openOrClose==00){
			return context.getString(R.string.powerOn);//开机
		}else if(openOrClose==01){
			return context.getString(R.string.powerOff);//关机
		}
		return "";
	}
	public void setOpenOrClose(int openOrClose) {
		this.openOrClose = openOrClose;
	}
	public String getCurrentlimit() {
		if (currentlimit == 00) {
			return  context.getString(R.string.limited);//限流
		} else if (currentlimit == 01) {
			return  context.getString(R.string.unlimited);//不限流
		}
		return "";
	}
	public void setCurrentlimit(int currentlimit) {
		this.currentlimit = currentlimit;
	}
	public String getCharge() {
		if (charge == 00) {
			return context.getString(R.string.floating);//浮充
		} else if (charge == 01) {
			return context.getString(R.string.equalizing);//均充
		} else if (charge == 02) {
			return context.getString(R.string.test);//测试
		}
		return "";
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public void setTmp(String tmp){
		this.tmp=tmp;
	}
	public String getTmp(){
		return this.tmp;
	}
	
	public String getMkTmp() {
		return mkTmp;
	}

	public void setMkTmp(String mkTmp) {
		this.mkTmp = mkTmp;
	}

	public String getInputV() {
		return inputV;
	}
	public void setInputV(String inputV) {
		this.inputV = inputV;
	}
	public String getInputA() {
		return inputA;
	}
	public void setInputA(String inputA) {
		this.inputA = inputA;
	}
	public String getOutputA() {
		return outputA;
	}
	public void setOutputA(String outputA) {
		this.outputA = outputA;
	}
	
}