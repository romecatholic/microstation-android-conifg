package com.dgm.bean;

import android.content.Context;

import com.dgm.dlyapp.R;

/**
 * �½��ڲ��࣬�洢ģ��n��״̬
 */
public class ModelBean{
	private int openOrClose;	//���ػ�״̬
	private int currentlimit;	//����״̬
	private int charge;		//���״̬
	private int modeError;	//ģ��״̬
	private int fengshan;	//����״̬
	private int acOver;	//ac��ѹ
	private int dcOver; //dc��ѹ
	private int alarmModeOver;	//ģ�����״̬
	private int alarmCommunication; //ͨ��״̬
	private String tmp;//�¶�
	private String mkTmp;//ģ���¶�
	
	private String inputV;//�����ѹ
	private String inputA;//�������
	private String outputA;//�������
	private Context context;
	public ModelBean(Context context){
		this.context=context;
	}
	
	public String getAcOver() {
		if (acOver == 00) {
			return context.getString(R.string.normal);//����
		} else{
			return context.getString(R.string.fault);//����
		}
	}

	public void setAcOver(int acOver) {
		this.acOver = acOver;
	}

	public String getDcOver() {
		if (dcOver == 00) {
			return context.getString(R.string.normal);//����
		} else{
			return context.getString(R.string.fault);//����
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
			return context.getString(R.string.normal);//����
		} else{
			return context.getString(R.string.fault);//����
		}
	}
	
	public String getFengshan(){
		if (fengshan == 00) {
			return context.getString(R.string.normal);//����
		} else{
			return context.getString(R.string.fault);//����
		}
	}
	
	public String getAlarmModeOver(){
		if (alarmModeOver == 00) {
			return context.getString(R.string.normal);//����
		} else{
			return context.getString(R.string.fault);//����
		}
	}

	public String getAlarmCommunication(){
		if (alarmCommunication == 00) {
			return context.getString(R.string.normal);//����
		} else{
			return context.getString(R.string.fault);//����
		}
	}
	
	public String getOpenOrClose() {
		if(openOrClose==00){
			return context.getString(R.string.powerOn);//����
		}else if(openOrClose==01){
			return context.getString(R.string.powerOff);//�ػ�
		}
		return "";
	}
	public void setOpenOrClose(int openOrClose) {
		this.openOrClose = openOrClose;
	}
	public String getCurrentlimit() {
		if (currentlimit == 00) {
			return  context.getString(R.string.limited);//����
		} else if (currentlimit == 01) {
			return  context.getString(R.string.unlimited);//������
		}
		return "";
	}
	public void setCurrentlimit(int currentlimit) {
		this.currentlimit = currentlimit;
	}
	public String getCharge() {
		if (charge == 00) {
			return context.getString(R.string.floating);//����
		} else if (charge == 01) {
			return context.getString(R.string.equalizing);//����
		} else if (charge == 02) {
			return context.getString(R.string.test);//����
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