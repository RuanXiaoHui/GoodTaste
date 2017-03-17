package www.formssi.goodtaste.utils;

import static www.formssi.goodtaste.utils.DateUtil.YYYYMMDDHHMMSS;
import static www.formssi.goodtaste.utils.DateUtil.getCurrentDate;

/**
 * 订单工具类
 *
 * @author qq724418408
 */
public final class OrderUtil {

    public static void main(String[] a) {
        System.out.print("订单号：" + getOrderNumber("17607842058"));
    }

    /**
     * 订单号：当前时间+用户手机号后四位
     *
     * @return orderNumber
     */
    public static String getOrderNumber(String phone) {
        StringBuffer sb = new StringBuffer();
        sb.append(getCurrentDate(YYYYMMDDHHMMSS));
        sb.append(getPhoneAfter4(phone, 4));
        return sb.toString();
    }

    /**
     * 获取str后n位
     *
     * @param str
     * @param n
     * @return
     */
    public static String getPhoneAfter4(String str, int n) {
        String result = null;
        if (!"".equals(str)) {
            if (str.length() > n) {
                result = str.substring(str.length() - n, str.length());
            }
        }
        return result;
    }

    /**
     * 0000 - 9999
     *
     * @param num
     * @return
     */
    public static String getNumber(int num) {
        num++;
        String result = "";
        switch ((num + "").length()) {
            case 1:
                result = "000" + num;
                break;
            case 2:
                result = "0" + num;
                break;
            case 3:
                result = "0" + num;
                break;
            case 4:
                result = "" + num;
                break;
            default: // 此处代表编号已经超过了9999，从0重新开始
                result = "0000";
                break;
        }
        return result;
    }

}
