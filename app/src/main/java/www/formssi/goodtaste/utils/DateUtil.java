package www.formssi.goodtaste.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author qq724418408
 */
public final class DateUtil {

    public static void main(String[] a) {
        System.out.println(getCurrentDate("ss秒"));
        System.out.println(getDateMillis("2017-03-30 09:22:00"));
        System.out.println(System.currentTimeMillis());
    }

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 获取当前自定义格式的日期
     * <p>
     * yyyy-MM-dd HH:mm:ss
     *
     * @param template
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDate(String template) {
        DateFormat format = new SimpleDateFormat(template, Locale.CHINA);
        String time = format.format(System.currentTimeMillis());
        return time;
    }

    /**
     * 获取时间差
     *
     * @param orderTimeMillis
     * @return
     */
    public static long getTimeChange(long orderTimeMillis,long currentTimeMillis) {
        long changeTimeMillis =currentTimeMillis - orderTimeMillis;
        return changeTimeMillis;
    }

    /**
     * 获取当前时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
        String time = format.format(System.currentTimeMillis());
        return time;

    }

    /**
     * 获取当前日期： 20160709 yyyyMMdd
     *
     * @return 20160709
     */
    public static long getDateMillis(String data) {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
        long time = 0;
        try {
            time = format.parse(data).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取当前日期： 20160709 yyyyMMdd
     *
     * @return 20160709
     */
    public static String getCurrentDate() {
        DateFormat format = new SimpleDateFormat(YYYYMMDD, Locale.CHINA);
        String time = format.format(System.currentTimeMillis());
        return time;
    }

    /**
     * 获取当前日期：2016-07-09 yyyy-MM-dd
     *
     * @return 2016-07-09
     */
    public static String getToday() {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD, Locale.CHINA);
        String time = format.format(System.currentTimeMillis());
        return time;
    }

}
