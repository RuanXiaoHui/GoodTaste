package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.adapter.OrderPagerAdapter;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.fragment.OrderStateFragment;

/**
 * 订单分类的activity
 */
public class OrderStateActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private OrderPagerAdapter orderPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_state);
        init();

        Intent intent = getIntent();
        int stateNum = intent.getIntExtra("stateNum",0) - 1;
        viewPager.setCurrentItem(stateNum);


    }



    private void init() {
        initView();
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }
    public void initView(){
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vpOrderState);
    }

    public void initViewPager(){
        fragments = new ArrayList<>();
        fragments.add(new OrderStateFragment(OrderState.NOT_PAY));
        fragments.add(new OrderStateFragment(OrderState.NOT_DELIVERY));
        fragments.add(new OrderStateFragment(OrderState.DELIVERY_ING));
        fragments.add(new OrderStateFragment(OrderState.NOT_COMMENT));
        fragments.add(new OrderStateFragment(OrderState.FINISH));
        FragmentManager fm = getSupportFragmentManager();
        orderPagerAdapter = new OrderPagerAdapter(fm, fragments);
        viewPager.setAdapter(orderPagerAdapter);
    }


    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
