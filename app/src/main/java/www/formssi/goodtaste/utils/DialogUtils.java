package www.formssi.goodtaste.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by qkldev003 on 2017/3/21.
 */

public class DialogUtils {

    /**
     *
     * @param context
     * @param listener
     * @param title
     * @param item1
     * @param item2
     */
    public static void showChoiceHeadPicDialog(final Context context, DialogInterface.OnClickListener listener, String title, String item1, String item2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(new String[]{item1, item2},listener);
        builder.show();
    }
}
