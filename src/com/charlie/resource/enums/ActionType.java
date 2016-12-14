package com.charlie.resource.enums;

public enum ActionType {
	INSERT("新增"),

	UPDATE("更新"),
	
	DELETE("刪除"),
	
	QUERY("查詢")
	;

	public static ActionType[] valuesOfActionType() {
		return new ActionType[] { INSERT, QUERY, UPDATE, DELETE };
	}

	private final String localName;

	private ActionType(String localName) {
		this.localName = localName;
	}

	public String getLocalName() {
		return localName;
	}

}
