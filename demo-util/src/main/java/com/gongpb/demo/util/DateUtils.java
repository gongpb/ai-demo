
package com.gongpb.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 日期工具类
 * 提供线程安全的时间转换方法
 * @author gongpb
 * @since 2013-12-30
 */
@Slf4j
public class DateUtils {
    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
    public enum DATE_FORMATE_ENUM{
        FORMATE2yyyyMMdd("yyyyMMdd",1),
        FORMATE2yyyy_MM_dd("yyyy-MM-dd",0),
        FORMATE2yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm",0),
        FORMATE2yyyyMMddHH("yyyyMMddHH",1),
        FORMATE2yyyyMMddHHmmss("yyyyMMddHHmmss",2),
        FORMATE2yyyyMM("yyyyMM",1),
        FORMATE2HHmmss("HHmmss",1),
        FORMATE2HH_mm_ss("HH:mm:ss",0),
        FORMATE2yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss",0),
        FORMATE2yyyy_MM_dd_HH_mm_CN("yyyy年MM月dd日 HH:mm",0);

        DATE_FORMATE_ENUM(String formate, int type){
            this.formate = formate;
            this.type = type;
        }
        private String formate;
        private int type; //0:字符串；1:int；2:long

        public Date toDate(String dateString) {
            try {
                return getSdf(this.formate).parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Date toDate(int dateString) {
            try {
                return getSdf(this.formate).parse(dateString+"");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String toString(Date date) {
            return getSdf(this.formate).format(date);
        }

        public int toInt(Date date) throws Exception{
            if (type!=1){
                throw new Exception("字符串类型不能转换int");
            }
            return Integer.valueOf(getSdf(this.formate).format(date));
        }
        public long toLong_YYYYMMDDHHMMSSFromDate(Date date)  throws Exception{
            if (type!=2){
                throw new Exception("字符串类型不能转换long");
            }
            return Long.parseLong(getSdf(this.formate).format(date));
        }
    }

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            tl = sdfMap.get(pattern);
            if (tl == null) {
                // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                tl = new ThreadLocal<SimpleDateFormat>() {
                    @Override
                    protected SimpleDateFormat initialValue() {
                        return new SimpleDateFormat(pattern);
                    }
                };
                sdfMap.put(pattern, tl);
            }
        }
        return tl.get();
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatUnSafe(Date date, String format) {
        SimpleDateFormat sdf = getSdf(format);
        return sdf.format(date);
    }


    public static Date dateAddDay(int days, Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, days);
        return ca.getTime();
    }

    /**
     * <p>
     * 日期增加几个月
     * </p>
     *
     * @param months 几个月
     * @param date
     * @return Date
     */
    public static Date addMonths(int months, Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, months);
        return ca.getTime();
    }

    /**
     * 获得几天之后的日期 传负数  表示几天之前的时间
     *
     * @param days
     * @return
     */
    public static Date addDays(int days, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        Date d = cal.getTime();
        return d;
    }

    /**
     * 获得几小时之后的日期 传负数  表示几天之前的时间
     *
     * @return
     */
    public static Date addHHs(int hour, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        Date d = cal.getTime();
        return d;
    }

    /**
     * 获得几分钟之后的日期 传负数  表示几分钟之前的时间
     *
     * @return
     */
    public static Date dateAddMinutes(int minute, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        Date d = cal.getTime();
        return d;
    }

    /**
     * 获得几分钟之后的秒 传负数  表示几分钟之前的时间
     *
     * @return
     */
    public static Date dateAddSecond(int second, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        Date d = cal.getTime();
        return d;
    }


    /**
     * 几毫秒之后的时间
     * @param millsecond
     * @param date
     * @return
     */
    public static Date addMillsecond(int millsecond, Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MILLISECOND, millsecond);
        return ca.getTime();
    }

    /**
     * <p>计算开始日期 到 结束日期 总共相差多少月，忽略时分秒天</p>
     *
     * @param startDate
     * @param endDate
     * @return int  相差多少月
     */
    public static int monthsBetweenYYYYMM(Date startDate, Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        c.setTime(endDate);
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);

        int result;
        if (year == year1) {
            result = month1 - month;//两个日期相差几个月，即月份差
        } else {
            result = 12 * (year1 - year) + month1 - month;//两个日期相差几个月，即月份差
        }
        return result;
    }


    /**
     * 几个月之后的月份
     *
     * @param days
     * @return
     */
    public static Date dateAddMonths(int days, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, days);
        Date d = cal.getTime();
        return d;
    }

    public static Date getDateBeforeWeek(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();// 获取一周之前的时间点(算上本天)
    }

    /**
     * <p>计算开始日期 到 结束日期 总共有多少天</p>
     *
     * @param startDate
     * @param endDate
     * @return int
     */
    public static int daysBetween(Date startDate, Date endDate) {
        final int dayTime = 86400000;//一天的时间
        if (startDate == null || endDate == null) {
            return 0;
        }
        long ld1 = startDate.getTime();
        long ld2 = endDate.getTime();
        if (ld2 - ld1 < 0) {
            return 0;
        }
        int days = (int) ((ld2 - ld1) / dayTime);
        return days;
    }

}
