package www.formssi.goodtaste.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author qq724418408
 */
public final class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 获取当前自定义格式的日期
     *
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
    public static String getCurrentDate() {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
        String time = format.format(System.currentTimeMillis()).replace("-", "").substring(0, 8);
        return time;
    }

    /**
     * 获取当前日期：2016-07-09 yyyy-MM-dd
     *
     * @return 2016-07-09
     */
    public static String getToday() {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
        String time = format.format(System.currentTimeMillis()).substring(0, 10);
        return time;
    }

}
