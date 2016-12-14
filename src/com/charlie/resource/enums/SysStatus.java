package com.charlie.resource.enums;

public enum SysStatus {
	/** Active(有效資料) **/
	Y("有效資料"),

	/** Cancel(無效資料) **/
	N("無效資料")
	;

	public static SysStatus[] valuesOfSystem() {
		return new SysStatus[] { Y, N };
	}

	private final String localName;

	private SysStatus(String localName) {
		this.localName = localName;
	}

	public String getLocalName() {
		return localName;
	}

}
