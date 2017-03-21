package www.formssi.goodtaste.utils;

/**
 * Created by qkldev003 on 2017/3/21.
 */

public class StringUtils {
    public static String hideTelephone(String oldTel) {
        String s = oldTel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return s;
    }
}
