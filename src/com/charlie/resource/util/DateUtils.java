package com.charlie.resource.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/8/8</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 * 
 * @author Charlie Woo
 * 
 */
public class DateUtils {

	public static Date ULIMITEDDATE;
	public static final String DAYS = "days";
	public static final String HOURS = "hours";
	public static final String MINUTES = "minutes";
	public static final String SECONDS = "seconds";

	static {

		ULIMITEDDATE = parseDate("9999/12/31", "yyyy/mm/dd");

	}

	private DateUtils() {
	}

	public static Timestamp nowTimestamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String currentTime() {
		return formatDate(nowTimestamp().getTime(), "yyyy/MM/dd HH:mm:ss");
	}

	public static String currentTimeNoSplit() {
		return formatDate(nowTimestamp().getTime(), "yyyyMMddHHmmss");
	}
	
	public static String formatDate(String format) {
		return formatDate(nowTimestamp().getTime(), format);
	}

	public static String formatDate(long date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = new Date(date);
		return sdf.format(d);
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatDateUS(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		if (date == null)
			return "";
		else
			return sdf.format(date);
	}

	public static Date parseDateObject(Object dateObject) {
		Date date = null;
		if (dateObject == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		try {
			date = sdf.parse(dateObject.toString());
		} catch (ParseException e) {
			// nothing
		}
		return date;
	}

	public static Date parseDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date rtnDate = null;
		try {
			if (StringUtils.isBlank(date)) {
				return null;
			} else {
				rtnDate = sdf.parse(StringUtils.trim(date));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtnDate;
	}

	/**
	 * 是否為閏年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}

	/**
	 * 取得最大的一天, 通常用在預設date
	 * 
	 * @return
	 */
	public static Date day99991231() {
		return time(9999, 12, 31, 23, 59, 59);
	}

	public static Date time(int year, int month, int day, int hour, int minute, int second) {
		return time(year, month, day, hour, minute, second, Locale.TAIWAN);
	}

	public static Date time(int year, int month, int day, int hour, int minute, int second, Locale aLocale) {
		Calendar calendar = Calendar.getInstance(aLocale);
		calendar.set(year, month - 1, day, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/** 日期時間增減 **/
	public static Date add(Date date, int field, int amount) {
		if (date == null)
			return null;

		date = new Timestamp(date.getTime());
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(field, amount);
		date.setTime(c.getTimeInMillis());

		return date;
	}

	/**
	 * 取得現在時間
	 * 
	 * @return
	 */
	public static Date today() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 取得今日0分0時0秒
	 * 
	 * @return
	 */
	public static Date todayStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取得日期0時0分0秒
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date dayStart(int year, int month, int day) {
		return time(year, month, day, 0, 0, 0);
	}

	public static Date dayStart(Date date) {
		return time(getYear(date), getMonthOfYear(date), getDayOfMonth(date), 0, 0, 0);
	}

	/**
	 * 取得日期23時59分59秒
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date dayStop(int year, int month, int day) {
		return time(year, month, day, 23, 59, 59);
	}

	public static Date dayStop(Date date) {
		return date == null ? null : time(getYear(date), getMonthOfYear(date), getDayOfMonth(date), 23, 59, 59);
	}

	/**
	 * A是否大於B
	 * 
	 * @return true if a > b
	 */
	public static boolean isBeforeDate(long a, long b) {
		int adate = Integer.parseInt(formatDate(a, "yyyyMMdd"));
		int bdate = Integer.parseInt(formatDate(b, "yyyyMMdd"));
		return adate > bdate;
	}

	/**
	 * 兩日期年月日是否相等
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean sameDay(long time1, long time2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTimeInMillis(time1);
		c2.setTimeInMillis(time2);
		int c1Year = c1.get(Calendar.YEAR);
		int c1Month = c1.get(Calendar.MONTH);
		int c1Day = c1.get(Calendar.DAY_OF_MONTH);
		int c2Year = c2.get(Calendar.YEAR);
		int c2Month = c2.get(Calendar.MONTH);
		int c2Day = c2.get(Calendar.DAY_OF_MONTH);
		return c1Year == c2Year && c1Month == c2Month && c1Day == c2Day;
	}

	/**
	 * 判斷是否大於等於今天
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isGreaterThanOrEqualsNowDate(Date targetDate) {
		return isGreaterThanOrEqualsDate(new Date(), targetDate);
	}

	/**
	 * 判斷是否小於等於今天
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isLessThanOrEqualsNowDate(Date targetDate) {
		return isLessThanOrEqualsDate(new Date(), targetDate);
	}

	/**
	 * 判斷date2是否大於等於date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isGreaterThanOrEqualsDate(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		return c2.compareTo(c1) >= 0;
	}

	/**
	 * 判斷date2是否大於date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isGreaterThanDate(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		return c2.compareTo(c1) > 0;
	}

	/**
	 * 判斷date2是否小於等於date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isLessThanOrEqualsDate(Date date1, Date date2) {
		return !isGreaterThanDate(date1, date2);
	}

	/**
	 * 判斷date2是否小於date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isLessThanDate(Date date1, Date date2) {
		return !isGreaterThanOrEqualsDate(date1, date2);
	}

	/**
	 * ignore hour,min
	 * 
	 * @param date
	 * @return
	 */
	public static final Date format(Date date) {
		return format(date, "yyyyMMdd");
	}

	/**
	 * ignore hour,min
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static final Date format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(sdf.format(date));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 比對兩日期相距大於或小於month
	 * 
	 * @param a
	 * @param b
	 * @param month
	 * @param opt
	 * @return
	 */
	public static boolean dateDiffMonthsCheck(Date date1, Date date2, int month, String opt) {
		int diffMonths = dateDiffMonths(date1, date2);
		if (">".equals(opt)) {
			return (diffMonths > month);
		} else if ("<".equals(opt)) {
			return (diffMonths < month);
		}
		return false;
	}

	/**
	 * 取得Calendar
	 * 
	 * @param time
	 * @return
	 */
	public static Calendar calendar(Timestamp time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());
		return cal;
	}

	public static Calendar calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return cal;
	}

	/**
	 * parse string To Timestamp
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Timestamp parse(String source, String pattern) {
		Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date d = sdf.parse(source);
			cal.setTime(d);
		} catch (ParseException e) {
			return nowTimestamp();
		}
		return new Timestamp(cal.getTimeInMillis());
	}

	public static int dateDiffMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}
	
	/**
	 * 兩日期時間差
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param dateBeanType
	 * @return
	 */
	public static Double dateTimeDiff(String startTime, String endTime, String format, String dateBeanType) {  
		
		if (StringUtils.isBlank(dateBeanType)) return null;		
        SimpleDateFormat sd = new SimpleDateFormat(format);  
        double diff = 0; 
        try {  
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();  
            
            if (StringUtils.equals(dateBeanType, DAYS)) {
            	return diff / 1000 / 60 / 60 / 24;
            } else if (StringUtils.equals(dateBeanType, HOURS)) {
            	return diff / 1000 / 60 / 60;
            } else if (StringUtils.equals(dateBeanType, MINUTES)) {
            	return diff / 1000 / 60;
            } else if (StringUtils.equals(dateBeanType, SECONDS)) {
            	return diff / 1000;
            }
        } catch (ParseException e) {  
            e.printStackTrace();  
        }          
        return null;
    }
	
	/**
	 * 兩日期時間差
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param dateBeanType
	 * @return
	 */
	public static Double dateTimeDiff(Timestamp startTime, Timestamp endTime, String dateBeanType) {  
		
		if (StringUtils.isBlank(dateBeanType)) return null;		
        double diff = 0; 
        diff = endTime.getTime() - startTime.getTime();  
		
		if (StringUtils.equals(dateBeanType, DAYS)) {
			return diff / 1000 / 60 / 60 / 24;
		} else if (StringUtils.equals(dateBeanType, HOURS)) {
			return diff / 1000 / 60 / 60;
		} else if (StringUtils.equals(dateBeanType, MINUTES)) {
			return diff / 1000 / 60;
		} else if (StringUtils.equals(dateBeanType, SECONDS)) {
			return diff / 1000;
		}          
        return null;
    }
	
	/**
	 * 轉換差異分鐘數至幾時幾分
	 * @param diffTime
	 * @return
	 */
	public static String diffMinutesForString(Double diffTime) {
		Double overflowMinute = 0.0;
		Integer overflowHour = 0;
		Boolean isOverflowHour = false;
		overflowMinute = diffTime;
		do {
			if (overflowMinute < 60) break;
			overflowMinute = overflowMinute - 60;
			overflowHour++;
			isOverflowHour = true;
			
		} while (overflowMinute >= 60);
		return isOverflowHour ? overflowHour + "時 " + overflowMinute.intValue() + "分" : diffTime.intValue() + "分";
	}

	/** 判斷日期是否在起迄時間內 */
	public static boolean isValidPreiod(Timestamp bdate, Timestamp edate, Timestamp date) {
		boolean flag = false;
		long btime = bdate.getTime(); // begin
		long etime = edate.getTime(); // end
		long time = date.getTime();
		if (btime <= time && time <= etime) {
			flag = true;
		}
		return flag;
	}

	public static boolean isValidPreiod(Date bdate, Date edate, Date date) {
		Timestamp bts = new Timestamp(bdate.getTime());
		Timestamp ets = new Timestamp(edate.getTime());
		Timestamp cdate = new Timestamp(date.getTime());
		return isValidPreiod(bts, ets, cdate);
	}

	public static String getROCYear(Date date) {
		if (date == null)
			return "";
		Calendar calendar = calendar(date);
		return String.valueOf(calendar.get(Calendar.YEAR) - 1911);
	}

	public static int getYear(Date date) {
		if (date == null)
			return 0;
		Calendar calendar = calendar(date);
		return calendar.get(Calendar.YEAR);
	}

	public static String getMonth(Date date) {
		if (date == null)
			return "";
		int month = getMonthOfYear(date);
		return month < 10 ? "0" + month : String.valueOf(month);
	}

	public static String getDay(Date date) {
		if (date == null)
			return "";
		int day = getDayOfMonth(date);
		return day < 10 ? "0" + day : String.valueOf(day);
	}

	public static int getMonthOfYear(Date date) {
		if (date == null)
			return 0;
		Calendar calendar = calendar(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getDayOfMonth(Date date) {
		if (date == null)
			return 0;
		Calendar calendar = calendar(date);
		return calendar.get(Calendar.DATE);
	}

}