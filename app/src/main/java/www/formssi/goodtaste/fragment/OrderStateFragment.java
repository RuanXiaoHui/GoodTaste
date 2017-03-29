package www.formssi.goodtaste.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.adapter.LoadMoreAdapter;
import www.formssi.goodtaste.adapter.OrderAdapter;
import www.formssi.goodtaste.bean.EventBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.REMIND_ORDER;

/**
 * 订单分类的fragment
 */
public class OrderStateFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rlvOrderState;//分类订单显示的列表
    private LinearLayout lltNoOrder;//没有订单时的父布局
    private int state = 0;//进入视图时显示的fragment
    private Button btnGoSingle;//没有订单时显示的按钮
    LoadMoreAdapter orderAdapter;

    public OrderStateFragment(int state) {
        super();
        this.state = state;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_state, container, false);
        EventBus.getDefault().register(this);
        initView(v);
        initData();
        initListener();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();//再次回到页面刷新数据
    }

    private void initView(View v) {
        rlvOrderState = (RecyclerView) v.findViewById(R.id.rlvOrderState);
        lltNoOrder = (LinearLayout) v.findViewById(R.id.lltNoOrder);
        btnGoSingle = (Button) v.findViewById(R.id.btnGoSingle);
    }

    private void initData() {
        List<OrderBean> orders = DataBaseSQLiteUtil.queryOrder(state);
        if (orders.size() == 0) {//如果没有该类型订单,显示去下单按钮
            rlvOrderState.setVisibility(View.GONE);
            lltNoOrder.setVisibility(View.VISIBLE);
        } else {//有订单时显示订单列表
            orderAdapter = new LoadMoreAdapter(new OrderAdapter(orders, getContext()));//加载更多...
            rlvOrderState.setLayoutManager(new LinearLayoutManager(getContext()));
            rlvOrderState.setAdapter(orderAdapter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(EventBean eventBean) {//EventBus接收器，运行在主线程
        if (REMIND_ORDER.equals(eventBean.getAction())) {
            initData();
        }
    }

    private void initListener() {
        btnGoSingle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoSingle:
                if (getActivity() instanceof OrderStateFragment.MeOnClickListener) {
                    ((OrderStateFragment.MeOnClickListener) getActivity()).onBtnGoSingleClick();
                }
                break;
        }
    }

    /**
     * 去下单按钮回调接口
     */
    public interface MeOnClickListener {
        void onBtnGoSingleClick();
    }
}
