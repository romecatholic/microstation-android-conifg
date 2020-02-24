package com.dgm.bean;

import java.util.HashMap;
import java.util.Map;

public class BusPlate {

	private Map<Integer,Optimizer> optimizerMap=new HashMap<Integer,Optimizer>();
	private long generationPower;//发电量
	private int generationPowerMinutes;//发电时间
	private long totalGenerationPower;//累计发电量
	private long totalGenerationMinutes;//累计发电时间
	private long totalOptimizerPower=0;
	public long getTotalOptimizerPower() {
		return totalOptimizerPower;
	}
	public void setTotalOptimizerPower(long totalOptimizerPower) {
		this.totalOptimizerPower = totalOptimizerPower;
	}
	public long getGenerationPower() {
		return generationPower;
	}
	public void setGenerationPower(long generationPower) {
		this.generationPower = generationPower;
	}
	public int getGenerationPowerMinutes() {
		return generationPowerMinutes;
	}
	public void setGenerationPowerMinutes(int generationPowerMinutes) {
		this.generationPowerMinutes = generationPowerMinutes;
	}
	public long getTotalGenerationPower() {
		return totalGenerationPower;
	}
	public void setTotalGenerationPower(long totalGenerationPower) {
		this.totalGenerationPower = totalGenerationPower;
	}
	public long getTotalGenerationMinutes() {
		return totalGenerationMinutes;
	}
	public void setTotalGenerationMinutes(long totalGenerationMinutes) {
		this.totalGenerationMinutes = totalGenerationMinutes;
	}
	public void putOptimizer(int i,Optimizer o){
		optimizerMap.put(i, o);
	}
	public Optimizer getOptimizer(int i){
		return optimizerMap.get(i);
	}
	public void clearMap(){
		optimizerMap.clear();
	}
}
