package com.pnj.utils;

import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * <p>Description：日期工具类</p>
 * <p>Copyright：Copyright (c) 2017</p>
 * <p>Company：SenseTime</p>
 * <p>Email：jiayongfei@sensetime.com</p>
 *
 * @author jiayongfei
 * @date 17-3-20:下午2:20
 */
public class DateUtil extends DateUtils {

    /**
     * 格式：年－月－日 小时：分钟：秒
     **/
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式：年－月－日 小时：分钟
     **/
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

    /**
     * 格式：年月日 小时分钟秒
     **/
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

    /**
     * 格式：年月日 小时分钟秒
     **/
    public static final String FORMAT_FOUR = "yyyy_MM_dd_HH_mm_ss";

    /**
     * 格式：年－月－日
     **/
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式：月－日
     **/
    public static final String SHORT_DATE_FORMAT = "MM-dd";
    /**
     * 格式：年－ 月
     **/
    public static final String SHORT_DATE_FORMAT_TWO = "yyyy-MM";
    /**
     * 格式：小时：分钟：秒
     **/
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    /**
     * yyyyMMddHHmmss 紧凑类型
     **/
    public static final String LONG_DATE_COMPACT_FORMAT = "yyyyMMddHHmmss";

    /**
     * yyyyMMdd 紧凑类型
     **/
    public static final String SHORT_DATE_COMPACT_FORMAT = "yyyyMMdd";

    /**
     * 年的加减
     **/
    public static final int SUB_YEAR = Calendar.YEAR;

    /**
     * 月加减
     **/
    public static final int SUB_MONTH = Calendar.MONTH;

    /**
     * 天的加减
     **/
    public static final int SUB_DAY = Calendar.DATE;

    /**
     * 小时的加减
     **/
    public static final int SUB_HOUR = Calendar.HOUR;

    /**
     * 分钟的加减
     **/
    public static final int SUB_MINUTE = Calendar.MINUTE;

    /**
     * 秒的加减
     **/
    public static final int SUB_SECOND = Calendar.SECOND;

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @param pattern
     * @return
     * @throws Exception
     */
    public static Date stringToDate(String dateStr, String pattern) {

        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = DateTime.parse(dateStr, format);
        return dateTime.toDate();
    }

    public static String format(long timestamp, String formatter) {
        DateTime dateTime = new DateTime(timestamp);
        return dateTime.toString(formatter);
    }

    /**
     * 时间按照给定格式转化为字符串
     *
     * @param date
     * @param pattern
     * @return
     * @throws Exception
     */
    public static String dateToString(Date date, String pattern) {

        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }

    /**
     * 获得当前日期字符串
     * 按照pattern的格式返回
     *
     * @return
     */
    public static String getCurrentDate(String pattern) {

        DateTime dateTime = new DateTime();
        return dateTime.toString(pattern);
    }

    /**
     * 获得本周星期日的日期
     *
     * @return
     */
    public static String getCurrentWeekday() {

        DateTime dateTime = new DateTime();
        return dateTime.dayOfWeek().withMaximumValue().toString(LONG_DATE_FORMAT);
    }

    /**
     * 获得上年最后一天的日期
     *
     * @return
     */
    public static String getPreviousYearEnd() {

        DateTime dateTime = new DateTime();
        return dateTime.minusYears(1).monthOfYear().setCopy(12).dayOfMonth().withMaximumValue().toString(LONG_DATE_FORMAT);
    }

    /**
     * 时间戳转指定格式日期
     *
     * @param timestamp
     * @return
     * @throws ParseException
     */
    public static Date unixTimestampToDate(long timestamp) {
        return new Date(timestamp * 1000);
    }

    /**
     * 把时间戳转换为23:59:59秒的时间戳
     *
     * @param timestamp
     * @return
     */
    public static long changeTimestampToEnd(long timestamp) {

        DateTime dateTime = new DateTime(timestamp);
        long changedTimestamp = dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis();
        return toUnixTimestamp(changedTimestamp);
    }

    /**
     * 获取指定格式的当前时间
     *
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {

        return dateToString(new Date(), format);
    }

    /**
     * 为指定日期dateStr在dateKind（年，月，日等）增加amount（为负则为减少）
     *
     * @param dateKind Calendar.YEAR Calendar.MONTH Calendar.DAY_OF_MONTH等
     * @param dateStr
     * @param amount
     * @return
     */
    public static String dateSub(int dateKind, String dateStr, int amount) {

        Date date = stringToDate(dateStr, FORMAT_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateKind, amount);
        return dateToString(calendar.getTime(), FORMAT_ONE);
    }

    /**
     * 两个日期相减
     * 如果secDate > firstDate 返回正数，否则返回负数
     *
     * @param date1
     * @param date2
     * @return 相减得到的秒数
     */
    public static long timeSub(String date1, String date2) {

        DateTime DateTime1 = new DateTime(stringToDate(date1, FORMAT_ONE));
        DateTime DateTime2 = new DateTime(stringToDate(date2, FORMAT_ONE));
        return Seconds.secondsBetween(DateTime1, DateTime2).getSeconds();
    }

    /**
     * 获取两个日期间的总月数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int monthSub(String date1, String date2) {

        DateTime DateTime1 = new DateTime(stringToDate(date1, FORMAT_ONE));
        DateTime DateTime2 = new DateTime(stringToDate(date2, FORMAT_ONE));
        return Months.monthsBetween(DateTime1, DateTime2).getMonths();
    }

    /**
     * 获取某年某月的天数
     *
     * @param year  int
     * @param month int 月份[1-12]
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {

        DateTime dateTime = new DateTime(year, month, Days.ONE.getDays(), Hours.ZERO.getHours(), Minutes.ZERO.getMinutes(), Seconds.ZERO.getSeconds());
        return dateTime.dayOfMonth().getMaximumValue();
    }

    /**
     * 获得当前天
     *
     * @return int
     */
    public static int getCurrentDay() {

        DateTime dateTime = new DateTime();
        return dateTime.getDayOfMonth();
    }

    /**
     * 获得当前月份
     *
     * @return int
     */
    public static int getCurrentMonth() {

        DateTime dateTime = new DateTime();
        return dateTime.getMonthOfYear();
    }

    /**
     * 获得当前年份
     *
     * @return int
     */
    public static int getCurrentYear() {

        DateTime dateTime = new DateTime();
        return dateTime.getYear();
    }

    /**
     * 返回日期的天
     *
     * @param date
     * @return int
     */
    public static int getDay(Date date) {

        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfMonth();
    }

    /**
     * 返回日期的月份
     *
     * @param date
     * @return int
     */
    public static int getMonth(Date date) {

        DateTime dateTime = new DateTime(date);
        return dateTime.getMonthOfYear();
    }

    /**
     * 返回日期的年
     *
     * @param date
     * @return int
     */
    public static int getYear(Date date) {

        DateTime dateTime = new DateTime(date);
        return dateTime.getYear();
    }

    /**
     * 计算两个日期相差的天数
     * 考虑时分秒的影响，如果date2 > date1 返回正数，否则返回负数
     *
     * @param date1
     * @param date2
     * @return long
     */
    public static long dayDiff(Date date1, Date date2) {

        DateTime dateTime1 = new DateTime(date1);
        DateTime dateTime2 = new DateTime(date2);
        return Days.daysBetween(dateTime1, dateTime2).getDays();
    }

    /**
     * 比较两个日期的年差
     * 不考虑日月，时分秒的影响
     *
     * @param date1 yyyy-MM-dd
     * @param date2 yyyy-MM-dd
     * @return
     */
    public static int yearDiff(String date1, String date2) {

        DateTime dateTime1 = new DateTime(stringToDate(date1, LONG_DATE_FORMAT));
        DateTime dateTime2 = new DateTime(stringToDate(date2, LONG_DATE_FORMAT));
        return dateTime2.getYear() - dateTime1.getYear();
    }

    /**
     * 比较指定日期与当前日期的差
     * 不考虑日月，时分秒的影响
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static int yearDiffCurr(String date) {

        DateTime dateTime1 = new DateTime(stringToDate(date, LONG_DATE_FORMAT));
        DateTime dateTime2 = new DateTime();
        return dateTime2.getYear() - dateTime1.getYear();
    }

    /**
     * 取得某年某月第一天是周几
     *
     * @param year
     * @param month
     * @return
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {

        DateTime dateTime = new DateTime(year, month, Days.ONE.getDays(), Hours.ZERO.getHours(), Minutes.ZERO.getMinutes(), Seconds.ZERO.getSeconds());
        return dateTime.getDayOfWeek();
    }

    /**
     * 取得某年某月最后一天是周几
     *
     * @param year
     * @param month
     * @return
     */
    public static int getLastWeekdayOfMonth(int year, int month) {

        DateTime dateTime = new DateTime(year, month, Days.ONE.getDays(), Hours.ZERO.getHours(), Minutes.ZERO.getMinutes(), Seconds.ZERO.getSeconds());
        dateTime = dateTime.withDayOfMonth(dateTime.dayOfMonth().withMaximumValue().dayOfMonth().get());
        return dateTime.getDayOfWeek();
    }

    /**
     * 根据生日获取星座
     *
     * @param birth YYYY-mm-dd
     * @return
     */
    public static String getAstro(String birth) {

        if (!isDate(birth)) {
            birth = "2000" + birth;
        }
        if (!isDate(birth)) {
            return "";
        }
        int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1,
                birth.lastIndexOf("-")));
        int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] arr = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
        int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
        return s.substring(start, start + 2) + "座";
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     *
     * @param date YYYY-mm-dd
     * @return
     */
    public static boolean isDate(String date) {

        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 取得指定日期过 months 月后的日期
     * 当 months 为负数表示指定月之前
     *
     * @param date   日期 为null时表示当天
     * @param months 相加(相减)的月数
     * @return
     */
    public static Date plusMonths(Date date, int months) {

        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 取得指定日期过 days 天后的日期
     * 当 day 为负数表示指定天之前
     *
     * @param date 日期 为null时表示当天
     * @param days 相加(相减)的天数
     * @return
     */
    public static Date plusDays(Date date, int days) {

        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 取得指定日期过 days 天后的日期
     * 当 day 为负数表示指定天之前
     *
     * @param date    日期 为null时表示当天
     * @param days    相加(相减)的天数
     * @param pattern
     * @return
     */
    public static String plusDaysStr(Date date, int days, String pattern) {

        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toString(pattern);
    }

    /**
     * 取得指定日期过 seconds 秒后的日期
     * 当 seconds 为负数表示指定秒之前
     *
     * @param date    日期 为null时表示当天
     * @param seconds 相加(相减)的秒数
     * @return
     */
    public static Date plusSeconds(Date date, int seconds) {

        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 取得指定日期过 weeks 周后的日期
     * 当 weeks 为负数表示指定周之前
     *
     * @param date  日期 为null时表示当天
     * @param weeks 相加(相减)的周数
     * @return
     */
    public static Date plusWeeks(Date date, int weeks) {

        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     *
     * @return
     */
    public static int getDayNum() {

        int daynum = 0;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
        Date dt1 = gd1.getTime();
        daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
        return daynum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     *
     * @param days
     * @return
     */
    public static Date getDateByNum(int days) {

        GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
        Date date = gd.getTime();
        date = plusDays(date, days);
        return date;
    }

    /**
     * 根据一个日期，返回是星期几的英文字符串
     *
     * @param date
     * @return
     */
    public static String getWeek(String date) {

        DateTime dateTime = new DateTime(DateUtil.stringToDate(date, DateUtil.LONG_DATE_FORMAT));
        DateTimeFormatter format = DateTimeFormat.forPattern("EEEE");
        return dateTime.toString(format);
    }

    /**
     * 计算当月最后一天,返回字符串
     *
     * @return
     */
    public static String getLastDayOfCurrentMonth() {

        DateTime dateTime = new DateTime();
        dateTime = dateTime.withDayOfMonth(dateTime.dayOfMonth().withMaximumValue().dayOfMonth().get());
        return dateTime.toString(DateUtil.LONG_DATE_FORMAT);
    }

    /**
     * 上月第一天
     *
     * @return
     */
    public static String getFirstDayOfLastMonth() {

        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusMonths(-1).withDayOfMonth(1);
        return dateTime.toString(LONG_DATE_FORMAT);
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static String getFirstDayOfMonth() {

        DateTime dateTime = new DateTime();
        dateTime = dateTime.withDayOfMonth(1);
        return dateTime.toString(LONG_DATE_FORMAT);
    }

    /**
     * 时间戳转日期
     *
     * @param unix
     * @return
     */
    public static Date unixTimestampToDate(int unix) {

        Long timestamp = (long) unix * 1000;
        return new Date(timestamp);
    }

    /**
     * 日期转时间戳
     *
     * @param date
     * @return
     */
    public static long dateToUnixTimestamp(Date date) {

        return date.getTime() / 1000;
    }

    /**
     * 返回时间戳（13位->10位）
     *
     * @param timestamp
     * @return
     */
    public static long toUnixTimestamp(Long timestamp) {

        return timestamp / 1000;
    }

    /**
     * 返回当前时间下一天0点的时间戳
     *
     * @return
     */
    public static long getNextDayZeroTimestamp() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 返回当前时间下num天0点的时间戳
     *
     * @return
     */
    public static Date getNextNumDayDate(Integer num) {

        if (num == null) num = 0;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, num);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 返回过去几天的时间戳数值
     *
     * @param num
     * @return
     */
    public static long[] getLastDaysTimestampArray(Integer num) {
        DateTime dateTime = DateTime.now().withTime(0, 0, 0, 0);
        int dayOfMonth = dateTime.getDayOfMonth();
        long[] time = new long[num];
        for (int i = num - 1, j = 0; i >= 0; i--) {
            System.out.println(dateTime.withDayOfMonth(dayOfMonth - i - 1));
            time[i] = dateTime.withDayOfMonth(i + 1).getMillis();
        }
        return time;
    }

    /**
     * 获取当月某天的时间戳
     *
     * @param day    天
     * @param hour   时
     * @param minute 分
     * @param sec    秒
     * @return 13位时间戳
     */
    public static long getTimestamp(int day, int hour, int minute, int sec) {
        DateTime dateTime = DateTime.now();
        return getTimestamp(dateTime.getYear(), dateTime.getMonthOfYear(), day, hour, minute, sec);
    }

    /**
     * 获取指定年月日时分秒的时间戳
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @param sec    秒
     * @return 13位时间戳
     */
    public static long getTimestamp(int year, int month, int day, int hour, int minute, int sec) {
        DateTime dateTime = new DateTime().withDate(year, month, day).withTime(hour, minute, sec, 0);
        return dateTime.getMillis();
    }

    /**
     * @return 去年昨天的0点0分0秒的13位时间戳
     */
    public static Timestamp lastYearYesterday() {

        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, -1);
        now.add(Calendar.DATE, -1);
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return new Timestamp(now.getTime().getTime());
    }

    /**
     * 昨天的 23点59分59秒
     *
     * @return 昨天的23.59.59的13位时间戳
     */
    public static long yesterdayEnd() {
        int day = DateTime.now().getDayOfMonth();
        return getTimestamp(day - 1, 23, 59, 59);
    }

    /**
     * 返回当天23:59:59的时间戳
     *
     * @return 今天23.59.59的13位时间戳
     */
    public static long getTodayEndTimestamp() {
        return getTodayTimestamp(23, 59, 59);
    }

    /**
     * 获取指定小时、分钟、秒的的当天时间戳
     *
     * @param hour 小时
     * @param min  分钟
     * @param sec  秒
     * @return 指定的13位时间戳（毫秒）
     */
    public static long getTodayTimestamp(int hour, int min, int sec) {
        return DateTime.now().withTime(hour, min, sec, 0).getMillis();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getLastDaysTimestampArray(5)));
        System.out.println(new DateTime(yesterdayEnd()));
    }
}
