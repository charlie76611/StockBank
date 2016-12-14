package com.charlie.resource.enums;

public enum FoodType {
	A("火鍋"),
	B("燒烤"),
	C("簡餐"),
	D("排餐"),
	E("小吃"),
	F("速食"),
	O("其他")
	;

	private final String localName;

	private FoodType(String localName) {
		this.localName = localName;
	}

	public String getLocalName() {
		return localName;
	}
	
	public static FoodType[] getFoodTypes() {
		return FoodType.values();
	}

}
