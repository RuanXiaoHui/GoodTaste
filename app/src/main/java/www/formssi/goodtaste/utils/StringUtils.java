package www.formssi.goodtaste.utils;

import android.text.TextUtils;

/**
 * Created by qkldev003 on 2017/3/21.
 */

public class StringUtils {
    /**
     * 隐藏手机号码
     *
     * @param tel
     * @return
     */
    public static String hideTelephone(String tel) {
        if (TextUtils.isEmpty(tel) || TextUtils.getTrimmedLength(tel) != 11) {
            return "";
        }
        return tel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 判断是否为11位手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneIsEleven(String phone) {
        return (null != phone && !TextUtils.isEmpty(phone) && TextUtils.getTrimmedLength(phone) == 11);
    }

}
