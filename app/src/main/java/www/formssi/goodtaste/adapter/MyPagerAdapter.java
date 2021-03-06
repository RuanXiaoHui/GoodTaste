package www.formssi.goodtaste.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> mViews;

    public MyPagerAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
        return mViews.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
