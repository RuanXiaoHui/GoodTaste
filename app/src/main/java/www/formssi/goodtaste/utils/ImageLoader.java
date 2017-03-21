package www.formssi.goodtaste.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by qkldev003 on 2017/3/21.
 */

public class ImageLoader {
    public static void display(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load(url).into(imageView);
        }
    }
}
