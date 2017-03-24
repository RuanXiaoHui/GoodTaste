package www.formssi.goodtaste.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.adapter.OrderPagerAdapter;
import www.formssi.goodtaste.constant.OrderState;

/**
 * 订单页面
 */
public class OrderFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        initView(v);
        initData();
        return v;
    }

    private void initView(View v) {
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) v.findViewById(R.id.vpOrderState);
    }

    private void initData() {
        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrderStateFragment(OrderState.ALL));
        fragments.add(new OrderStateFragment(OrderState.NOT_PAY));
        fragments.add(new OrderStateFragment(OrderState.NOT_DELIVERY));
        fragments.add(new OrderStateFragment(OrderState.DELIVERY_ING));
        fragments.add(new OrderStateFragment(OrderState.NOT_COMMENT));
        fragments.add(new OrderStateFragment(OrderState.FINISH));
        OrderPagerAdapter orderPagerAdapter = new OrderPagerAdapter(fm, fragments);
        viewPager.setAdapter(orderPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
