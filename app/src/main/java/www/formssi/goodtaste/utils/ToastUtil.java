package www.formssi.goodtaste.utils;

import android.widget.Toast;

import www.formssi.goodtaste.R;

/**
 * Created by qkldev003 on 2017/3/20.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null){
            toast = Toast.makeText(ContextUtil.getInstance(),msg,Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 根据string.xml文件显示内容
     * @param stringId
     */
    public static void showToast(int stringId) {
        if (toast == null){
            toast = Toast.makeText(ContextUtil.getInstance(),stringId,Toast.LENGTH_SHORT);
        }else {
            toast.setText(stringId);
        }
        toast.show();
    }
}
