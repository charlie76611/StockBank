package com.charlie.resource.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

	public static final String DAYS = "days";
	public static final String HOURS = "hours";
	public static final String MINUTES = "minutes";
	public static final String SECONDS = "seconds";
	
	public DateUtil() {
		
	}

	public static Timestamp nowTimestamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String currentTime() {
		return formatDate(nowTimestamp().getTime(), "yyyy/MM/dd HH:mm:ss");
	}

	public static String formatDate(String format) {
		return formatDate(nowTimestamp().getTime(), format);
	}

	public static String formatDate(long date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = new Date(date);
		return sdf.format(d);
	}

	public static String formatDateUS(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		if ( date == null )
			return "";
		else
			return sdf.format(date);
	}

	public static Date paserDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date rtnDate = null;
		try {
			if ( date == null || date.trim().equals("") )
				return null;
			else
				rtnDate = sdf.parse(date);
		} catch (ParseException pse) {
			pse.printStackTrace();
		}
		return rtnDate;
	}

	/**
	 * �O�_���|�~
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}

	/**
	 * ��o�̤j���@��, �q�`�Φb�w�]date
	 * 
	 * @return
	 */
	public static Date day99991231() {
		return time(9999, 12, 31, 23, 59, 59);
	}
	
	public static Timestamp time99991231() {
		return time2(9999, 12, 31, 23, 59, 59);
	}

	public static Date time(int year, int month, int day, int hour, int minute, int second) {
		return time(year, month, day, hour, minute, second, Locale.TAIWAN);
	}
	
	public static Timestamp time2(int year, int month, int day, int hour, int minute, int second) {
		return time2(year, month, day, hour, minute, second, Locale.TAIWAN);
	}

	public static Date time(int year, int month, int day, int hour, int minute, int second, Locale aLocale) {
		Calendar calendar = Calendar.getInstance(aLocale);
		calendar.set(year, month - 1, day, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Timestamp time2(int year, int month, int day, int hour, int minute, int second, Locale aLocale) {
		Calendar calendar = Calendar.getInstance(aLocale);
		calendar.set(year, month - 1, day, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * ��o����0��0��0��
	 * 
	 * @return
	 */
	public static Date today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * ��o���0��0��0��
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date dayStart(int year, int month, int day) {
		return time(year, month, day, 0, 0, 0);
	}

	/**
	 * ��o���23��59��59��
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date dayStop(int year, int month, int day) {
		return time(year, month, day, 23, 59, 59);
	}

	/**
	 * A�O�_�j��B
	 * 
	 * @return true if a > b
	 */
	public static boolean isBeforeDate(long a, long b) {
		int adate = Integer.parseInt(formatDate(a, "yyyyMMdd"));
		int bdate = Integer.parseInt(formatDate(b, "yyyyMMdd"));
		return adate > bdate;
	}

    /**
     * �����~���O�_�۵�
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
	 * ignore hour,min
	 * @param date
	 * @return
	 */
	public static final Date format(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(sdf.format(date));
		} catch (Exception e) {
			return null;
		}	
	}
	
	
	/**
	 * �������۶Z�j��Τp��month
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
		}
		else if ("<".equals(opt)) {
			return (diffMonths < month);
		}
		return false;
	}
	/**
	 * ��oCalendar
	 * 
	 * @param time
	 * @return
	 */
	public static Calendar calendar(Timestamp time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());
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
		}
		catch (ParseException e) {
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

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/** add by Charlie */
	public static String getROCDate(String westDate) {
		int roc_year = Integer.parseInt(westDate.substring(0, 4)) - 1911; 
		return roc_year + westDate.substring(4);
	}
	
	/** �P�_����O�_�b�_���ɶ��� */
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
	
	/**
	 * �[�WN��
	 * 
	 * @param time
	 * @param days
	 * @return
	 */
	public static Timestamp addDays(Timestamp time, int days) {
		return new Timestamp(DateUtils.addDays(time, days).getTime());
	}
	
	/**
	 * �[�WN��
	 * 
	 * @param time
	 * @param minutes
	 * @return
	 */
	public static Timestamp addMinutes(Timestamp time, int minutes) {
		return new Timestamp(DateUtils.addMinutes(time, minutes).getTime());
	}
	
	/**
	 * ��o�ɶ��_���I(�C30����)
	 * ��method�ثe�ȾA�Φ��e��w�p�ɮĳ]�w�@�~
	 * @param start
	 * @param subtract
	 * @return
	 */
	public static Set<String> getTimePer30Mins(int start, int subtract) {
		Set<String> list = new TreeSet<String>();
		int plusMinutes = 0;
		
		while(start < 2400) {
			list.add(StringUtils.leftPad(String.valueOf(start), 4, "0"));
			if (plusMinutes == 0 || plusMinutes == 70) plusMinutes = 30;
			else if (plusMinutes == 30) plusMinutes = 70;
			start += plusMinutes;
		}
		list.add(StringUtils.leftPad(String.valueOf(start - subtract), 4, "0"));
		return list;
	}
	
	/**
	 * �p������ɶ��t�A�æ^�ǻݭn���ɶ��q
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param dateBeanType
	 * @return
	 */
	public static Double dateDiff(String startTime, String endTime, String format, String dateBeanType) {  
		
		if (StringUtils.isBlank(dateBeanType)) return null;		
        SimpleDateFormat sd = new SimpleDateFormat(format);  
        double diff = 0; 
        // ��o��Ӯɶ����@��t  
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
	
	public static void main(String[] a) {
		System.err.println(DateUtil.getTimePer30Mins(29, 70));
		System.err.println(DateUtil.parse("2012-08-07", "yyyy-MM-dd"));
		System.err.println(DateUtil.dateDiff("20130501020300", "20130501030400", "yyyyMMddHHmmss", DateUtil.MINUTES));
	}
}
