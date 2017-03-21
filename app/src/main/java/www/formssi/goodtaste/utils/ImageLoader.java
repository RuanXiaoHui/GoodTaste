package www.formssi.goodtaste.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by qkldev003 on 2017/3/21.
 */

public class ImageLoader {
    /**
     *
     * @param context
     * @param url 图片url
     * @param imageView 目标控件
     */
    public static void display(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }
}
