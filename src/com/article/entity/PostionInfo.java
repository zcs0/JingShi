package com.article.entity;

import java.io.Serializable;


/**
 * @ClassName:     PostionInfo.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月16日 上午11:39:02 
 * @Description:   
 */
public class PostionInfo implements Serializable{
	private String devId;//设备标识
	private double lng;//纬度
	private double lat;//经度
	private double temp;//温度
	private double rh;//相对湿度
	private double spd;//车速
	private double vibrate;//震动
	private double impact;//冲击
	private String ts;//时间
	public String getDevId() {
		return devId;
	}
	public String getDevIdTitle() {
		return "设备标识";
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	public double getLng() {
		return lng;
	}
	public String getLngTitle() {
		return "纬度";
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public String getLatTitle() {
		return "经度";
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getTemp() {
		return temp;
	}
	public String getTempTitle() {
		return "温度";
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public double getRh() {
		return rh;
	}
	public String getRhTitle() {
		return "相对湿度";
	}
	public void setRh(double rh) {
		this.rh = rh;
	}
	public double getSpd() {
		return spd;
	}
	public String getSpdTitle() {
		return "车速";
	}
	public void setSpd(double spd) {
		this.spd = spd;
	}
	public double getVibrate() {
		return vibrate;
	}
	public String getVibrateTitle() {
		return "震动";
	}
	public void setVibrate(double vibrate) {
		this.vibrate = vibrate;
	}
	public double getImpact() {
		return impact;
	}
	public String getImpactTitle() {
		return "冲击";
	}
	public void setImpact(double impact) {
		this.impact = impact;
	}
	public String getTs() {
		return ts;
	}
	public String getTsTitle() {
		return "时间";
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	
	
	
	

}
