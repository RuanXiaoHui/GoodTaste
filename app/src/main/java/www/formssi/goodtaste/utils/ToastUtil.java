package www.formssi.goodtaste.utils;

import android.widget.Toast;

/**
 * Created by qkldev003 on 2017/3/20.
 */

public class ToastUtil {

    public static void showToast(String msg) {
        Toast.makeText(ContextUtil.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
}
