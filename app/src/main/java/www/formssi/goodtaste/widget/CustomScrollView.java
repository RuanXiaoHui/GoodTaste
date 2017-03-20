package www.formssi.goodtaste.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/3/20.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 * desc:自定义ScrollView
 */

public class CustomScrollView extends ScrollView {


    /***对外提供接口***/
    public interface ScrollViewListener{
        void OnScrollViewChangeListener(int x, int y, int oldx, int oldy);
    }
    public ScrollViewListener OnScrollView;

    public void setOnScrollView(ScrollViewListener onScrollView) {
        OnScrollView = onScrollView;
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (OnScrollView!=null){
            OnScrollView.OnScrollViewChangeListener(l,t,oldl,oldt);
        }
    }
}
