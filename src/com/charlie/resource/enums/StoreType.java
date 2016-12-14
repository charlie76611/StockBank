package com.charlie.resource.enums;

public enum StoreType {
	A("中式"),
	B("美式"),
	C("日式"),
	D("義式"),
	E("韓式"),
	F("泰式"),
	G("法式"),
	
	O("夜市"),
	
	;

	private final String localName;

	private StoreType(String localName) {
		this.localName = localName;
	}

	public String getLocalName() {
		return localName;
	}
	
	public static StoreType[] getStoreTypes() {
		return StoreType.values();
	}

}
