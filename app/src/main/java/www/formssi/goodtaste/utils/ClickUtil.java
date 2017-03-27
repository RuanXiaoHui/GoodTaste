package www.formssi.goodtaste.utils;

/**
 * Created by GTs on 2017-03-27.
 */

public class ClickUtil {


    private static long lastClickTime;

    /**
     * 间隔时间
     * @param minTime 间隔的最短时间
     * @return 超过最短时间后返回true
     */
    public static boolean isFastClick(int minTime) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= minTime) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
