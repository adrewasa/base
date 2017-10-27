package com.asa.base.utils.data.date;

import com.asa.base.utils.data.GeneralUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by andrew_asa on 2017/7/28.
 * 时间操作相关
 */
public class DateUtils {

    public static boolean isSameDay(final Date date1, final Date date2) {

        if (GeneralUtils.containNull(date1, date2)) {
            return false;
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * 相同的年日
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {

        if (GeneralUtils.containNull(cal1, cal2)) {
            return false;
        }
        // 公元前还是公元后
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 年份加n
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(final Date date, final int amount) {

        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 月份加n
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(final Date date, final int amount) {

        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 周数加n
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addWeeks(final Date date, final int amount) {

        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date addDays(final Date date, final int amount) {

        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 小时加n
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(final Date date, final int amount) {

        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 分钟加n
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(final Date date, final int amount) {

        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 秒数加n
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(final Date date, final int amount) {

        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 毫秒数加n
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMilliseconds(final Date date, final int amount) {

        return add(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 日期对应字段加1
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    private static Date add(final Date date, final int calendarField, final int amount) {

        if (GeneralUtils.allNotNull(date)) {
            final Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
        return null;
    }

    /**
     * 设置年份
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date setYears(final Date date, final int amount) {

        return set(date, Calendar.YEAR, amount);
    }

    /**
     * 设置月份
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date setMonths(final Date date, final int amount) {

        return set(date, Calendar.MONTH, amount);
    }

    /**
     * 设置日
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date setDays(final Date date, final int amount) {

        return set(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 设置小时
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date setHours(final Date date, final int amount) {

        return set(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 设置分钟
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date setMinutes(final Date date, final int amount) {

        return set(date, Calendar.MINUTE, amount);
    }

    /**
     * 设置秒
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date setSeconds(final Date date, final int amount) {

        return set(date, Calendar.SECOND, amount);
    }

    /**
     * 设置毫秒
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date setMilliseconds(final Date date, final int amount) {

        return set(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 设置日期相应字段
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    private static Date set(final Date date, final int calendarField, final int amount) {

        if (GeneralUtils.allNotNull(date)) {
            final Calendar c = Calendar.getInstance();
            c.setLenient(false);
            c.setTime(date);
            c.set(calendarField, amount);
            return c.getTime();
        }
        return null;
    }

    /**
     * 日期转换
     *
     * @param date
     * @return
     */
    public static Calendar toCalendar(final Date date) {

        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 设置日期
     *
     * @param date
     * @param tz
     * @return
     */
    public static Calendar toCalendar(final Date date, final TimeZone tz) {

        final Calendar c = Calendar.getInstance(tz);
        c.setTime(date);
        return c;
    }

}
