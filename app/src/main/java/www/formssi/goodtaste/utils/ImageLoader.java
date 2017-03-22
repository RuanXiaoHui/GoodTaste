package www.formssi.goodtaste.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by qkldev003 on 2017/3/21.
 */

public class ImageLoader {

    public static void display(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load(url).into(imageView);
        }
    }

    public static void display(Context context, Uri uri, ImageView imageView) {
        if (uri != null) {
            Picasso.with(context).load(uri).into(imageView);
        }
    }

    public static void display(Context context, File file, ImageView imageView) {
        if (file != null && file.exists()) {
            Picasso.with(context).load(file).into(imageView);
        }
    }

    public static void display(Context context, int resId, ImageView imageView) {
        Picasso.with(context).load(resId).into(imageView);

    }
}
