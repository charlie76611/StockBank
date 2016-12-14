package com.charlie.resource;

public class AppConstants {
	
	/**
     * 所有的 datetime pattern 都必須 "reference" 到這個變數, 避免 datetime 字串的格式太多種而失去控制.<br>
     * 注意不要只在程式打"yyyy-MM-dd HH:mm:ss", 因為那樣也是不受控制, 應該用 reference 的方式.
     */
    public static final String STANDARD_FULL_DATETIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
    
    /**
     * 當欄位的 display type 為 DATE 的時候允許 user 的顯示方式為 yyyy-MM-dd.<br>
     * 由於 統一日期字串格式規定在 STANDARD_DATETIME_PATTERN, 所以這個 ALTERNATIVE 的格式只能使用在 user 顯示與輸入的時候, <br>
     * 一回到 server 就必須盡快轉換成日期型態或 STANDARD_DATETIME_PATTERN 所規定的 pattern.<br>
     * 當使用 yyyy-MM-dd 的時候要注意必須 reference 到這個變數, 避免直接打字串在程式裡.
     */
    public static final String ALTERNATIVE_DATETIME_PATTERN = "yyyy/MM/dd";
    public static final String ALTERNATIVE_MINUTE_PATTERN = "HHmm"; 

	public static String version = "0.05";
	
	public static String webRealPathRoot = "";
	
	public static final String INSERT_SUCCESS = "新增成功！";
	
	public static final String UPDATE_SUCCESS = "更新成功！";
	
	public static final String DELETE_SUCCESS = "刪除成功！";
	
	public static final String SAVED_FAILED = "儲存失敗！";
}
