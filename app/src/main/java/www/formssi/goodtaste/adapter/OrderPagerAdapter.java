package www.formssi.goodtaste.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by GTs on 2017-03-18.
 */

public class OrderPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String[] mTitles = new String[]{"未支付","未配送","送餐中","未评论","已完成"};

    public OrderPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
