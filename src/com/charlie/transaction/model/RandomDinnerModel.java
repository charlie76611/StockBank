package com.charlie.transaction.model;

import java.util.Arrays;

import net.sourceforge.stripes.action.FileBean;

import com.charlie.resource.commons.BaseModel;
import com.charlie.resource.enums.FoodType;
import com.charlie.resource.enums.StoreType;

public class RandomDinnerModel extends BaseModel {

	public static final String TABLENAME = "dinner_main";
	
	private String name;
	private String category;
	private String introduction;
	private Integer randomTimes = 0;
	private byte[] storePicture;
	private FileBean picAttachment;
	private FoodType foodType;
	private StoreType storeType;
	private String closeTime;
	private String reserveFlag = "N";
	private String region;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Integer getRandomtimes() {
		return randomTimes;
	}
	public void setRandomtimes(Integer randomtimes) {
		this.randomTimes = randomtimes;
	}
	public byte[] getStorepicture() {
		return storePicture;
	}
	public void setStorepicture(byte[] storepicture) {
		this.storePicture = storepicture;
	}
	public FileBean getPicAttachment() {
		return picAttachment;
	}
	public void setPicAttachment(FileBean picAttachment) {
		this.picAttachment = picAttachment;
	}
	public Integer getRandomTimes() {
		return randomTimes;
	}
	public void setRandomTimes(Integer randomTimes) {
		this.randomTimes = randomTimes;
	}
	public byte[] getStorePicture() {
		return storePicture;
	}
	public void setStorePicture(byte[] storePicture) {
		this.storePicture = storePicture;
	}
	public FoodType getFoodType() {
		return foodType;
	}
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}
	public StoreType getStoreType() {
		return storeType;
	}
	public void setStoreType(StoreType storeType) {
		this.storeType = storeType;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getReserveFlag() {
		return reserveFlag;
	}
	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "RandomDinnerModel [name=" + name + ", category=" + category + ", introduction=" + introduction + ", randomTimes="
				+ randomTimes + ", storePicture=" + Arrays.toString(storePicture) + ", picAttachment=" + picAttachment + ", foodType="
				+ foodType + ", storeType=" + storeType + ", closeTime=" + closeTime + ", reserveFlag=" + reserveFlag + ", region="
				+ region + "]";
	}
	
}
