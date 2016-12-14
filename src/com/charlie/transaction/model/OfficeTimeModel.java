package com.charlie.transaction.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

public class OfficeTimeModel {

	private String date;		// 上班日期
	
	private String officialPart;		// 班別
	
	private Timestamp offWorkTimeA;		// 下班時間(早)
	
	private Timestamp offWorkTimeP;		// 下班時間(午)
	
	private Timestamp offWorkTimeN;		// 下班時間(晚)
	
	private Double overWorkTimeA = 0.0;	// 加班時數(早)
	
	private Double overWorkTimeP = 0.0;	// 加班時數(午)
	
	private Double overWorkTimeN = 0.0;	// 加班時數(晚)
		
	private boolean isOverTime = false;	// 是否加班
	
	private boolean isLackTime = false;	// 是否欠班
	
	private Double totalWorkTime = 0.0;	// 總工作時數
	
	private Double overWorkTimePerDay = 0.0;	// 每日加班時數
	
	private Double lackWorkTimePerDay = 0.0;	// 每日欠班時數
	
	// 頁面班時設定 ----------------------------------
	
	private String typeAA_on;
	private String typeAA_off;
	private String typeAA_duty;
	private String typeAP_on;
	private String typeAP_off;
	private String typeAP_duty;
	private String typeBA_on;
	private String typeBA_off;
	private String typeBA_duty;
	private String typeBN_on;
	private String typeBN_off;
	private String typeBN_duty;
	private String typeCN_on;
	private String typeCN_off;
	private String typeCN_duty;
	private String typeDA_on;
	private String typeDA_off;
	private String typeDA_duty;	
	
	@Override
	public String toString() {
		return "OfficeTimeModel [date=" + date + ", officialPart=" + officialPart + ", offWorkTimeA=" + offWorkTimeA + ", offWorkTimeP=" + offWorkTimeP
				+ ", offWorkTimeN=" + offWorkTimeN + ", overWorkTimeA=" + overWorkTimeA + ", overWorkTimeP=" + overWorkTimeP + ", overWorkTimeN="
				+ overWorkTimeN + ", totalWorkTime=" + totalWorkTime + ", isOverTime=" + isOverTime + ", isLackTime=" + isLackTime + ", overWorkTimePerDay="
				+ overWorkTimePerDay + ", lackWorkTimePerDay=" + lackWorkTimePerDay + "]";
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOfficialPart() {
		return officialPart;
	}

	public void setOfficialPart(String officialPart) {
		this.officialPart = officialPart;
	}

	public Timestamp getOffWorkTimeA() {
		return offWorkTimeA;
	}

	public void setOffWorkTimeA(Timestamp offWorkTimeA) {
		this.offWorkTimeA = offWorkTimeA;
	}

	public Timestamp getOffWorkTimeP() {
		return offWorkTimeP;
	}

	public void setOffWorkTimeP(Timestamp offWorkTimeP) {
		this.offWorkTimeP = offWorkTimeP;
	}

	public Timestamp getOffWorkTimeN() {
		return offWorkTimeN;
	}

	public void setOffWorkTimeN(Timestamp offWorkTimeN) {
		this.offWorkTimeN = offWorkTimeN;
	}

	public Double getOverWorkTimeA() {
		return overWorkTimeA;
	}

	public void setOverWorkTimeA(Double overWorkTimeA) {
		this.overWorkTimeA = overWorkTimeA;
	}

	public Double getOverWorkTimeP() {
		return overWorkTimeP;
	}

	public void setOverWorkTimeP(Double overWorkTimeP) {
		this.overWorkTimeP = overWorkTimeP;
	}

	public Double getOverWorkTimeN() {
		return overWorkTimeN;
	}

	public void setOverWorkTimeN(Double overWorkTimeN) {
		this.overWorkTimeN = overWorkTimeN;
	}

	public boolean isOverTime() {
		return isOverTime;
	}

	public void setOverTime(boolean isOverTime) {
		this.isOverTime = isOverTime;
	}

	public boolean isLackTime() {
		return isLackTime;
	}

	public void setLackTime(boolean isLackTime) {
		this.isLackTime = isLackTime;
	}

	public Double getTotalWorkTime() {
		return totalWorkTime;
	}

	public void setTotalWorkTime(Double totalWorkTime) {
		this.totalWorkTime = totalWorkTime;
	}

	public Double getOverWorkTimePerDay() {
		return overWorkTimePerDay;
	}

	public void setOverWorkTimePerDay(Double overWorkTimePerDay) {
		this.overWorkTimePerDay = overWorkTimePerDay;
	}

	public Double getLackWorkTimePerDay() {
		return lackWorkTimePerDay;
	}

	public void setLackWorkTimePerDay(Double lackWorkTimePerDay) {
		this.lackWorkTimePerDay = lackWorkTimePerDay;
	}

	public String getTypeAA_on() {
		return typeAA_on;
	}

	public void setTypeAA_on(String typeAA_on) {
		this.typeAA_on = StringUtils.replace(typeAA_on, ":", "");
	}

	public String getTypeAA_off() {
		return typeAA_off;
	}

	public void setTypeAA_off(String typeAA_off) {
		this.typeAA_off = StringUtils.replace(typeAA_off, ":", "");
	}

	public String getTypeAA_duty() {
		return typeAA_duty;
	}

	public void setTypeAA_duty(String typeAA_duty) {
		this.typeAA_duty = StringUtils.replace(typeAA_duty, ":", "");
	}

	public String getTypeAP_on() {
		return typeAP_on;
	}

	public void setTypeAP_on(String typeAP_on) {
		this.typeAP_on = StringUtils.replace(typeAP_on, ":", "");
	}

	public String getTypeAP_off() {
		return typeAP_off;
	}

	public void setTypeAP_off(String typeAP_off) {
		this.typeAP_off = StringUtils.replace(typeAP_off, ":", "");
	}

	public String getTypeAP_duty() {
		return typeAP_duty;
	}

	public void setTypeAP_duty(String typeAP_duty) {
		this.typeAP_duty = StringUtils.replace(typeAP_duty, ":", "");
	}

	public String getTypeBA_on() {
		return typeBA_on;
	}

	public void setTypeBA_on(String typeBA_on) {
		this.typeBA_on = StringUtils.replace(typeBA_on, ":", "");
	}

	public String getTypeBA_off() {
		return typeBA_off;
	}

	public void setTypeBA_off(String typeBA_off) {
		this.typeBA_off = StringUtils.replace(typeBA_off, ":", "");
	}

	public String getTypeBA_duty() {
		return typeBA_duty;
	}

	public void setTypeBA_duty(String typeBA_duty) {
		this.typeBA_duty = StringUtils.replace(typeBA_duty, ":", "");
	}

	public String getTypeBN_on() {
		return typeBN_on;
	}

	public void setTypeBN_on(String typeBN_on) {
		this.typeBN_on = StringUtils.replace(typeBN_on, ":", "");
	}

	public String getTypeBN_off() {
		return typeBN_off;
	}

	public void setTypeBN_off(String typeBN_off) {
		this.typeBN_off = StringUtils.replace(typeBN_off, ":", "");
	}

	public String getTypeBN_duty() {
		return typeBN_duty;
	}

	public void setTypeBN_duty(String typeBN_duty) {
		this.typeBN_duty = StringUtils.replace(typeBN_duty, ":", "");
	}

	public String getTypeCN_on() {
		return typeCN_on;
	}

	public void setTypeCN_on(String typeCN_on) {
		this.typeCN_on = StringUtils.replace(typeCN_on, ":", "");
	}

	public String getTypeCN_off() {
		return typeCN_off;
	}

	public void setTypeCN_off(String typeCN_off) {
		this.typeCN_off = StringUtils.replace(typeCN_off, ":", "");
	}

	public String getTypeCN_duty() {
		return typeCN_duty;
	}

	public void setTypeCN_duty(String typeCN_duty) {
		this.typeCN_duty = StringUtils.replace(typeCN_duty, ":", "");
	}

	public String getTypeDA_on() {
		return typeDA_on;
	}

	public void setTypeDA_on(String typeDA_on) {
		this.typeDA_on = StringUtils.replace(typeDA_on, ":", "");
	}

	public String getTypeDA_off() {
		return typeDA_off;
	}

	public void setTypeDA_off(String typeDA_off) {
		this.typeDA_off = StringUtils.replace(typeDA_off, ":", "");
	}

	public String getTypeDA_duty() {
		return typeDA_duty;
	}

	public void setTypeDA_duty(String typeDA_duty) {
		this.typeDA_duty = StringUtils.replace(typeDA_duty, ":", "");
	}

	
}
