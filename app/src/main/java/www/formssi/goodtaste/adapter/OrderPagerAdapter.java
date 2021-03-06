package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.utils.ContextUtil;

/**
 * Created by GTs on 2017-03-18.
 * <p>
 * orderStateActivity 需要的pageradapter
 */

public class OrderPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private Context context = ContextUtil.getInstance();
    //tablayout控件显示的文字
    private String[] mTitles = new String[]{
            context.getString(R.string.order_state_all),
            context.getString(R.string.order_state_not_pay),
            context.getString(R.string.order_state_not_delivery),
            context.getString(R.string.order_state_delivery_ing),
            context.getString(R.string.order_state_not_comment),
            context.getString(R.string.order_state_finish)};

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
