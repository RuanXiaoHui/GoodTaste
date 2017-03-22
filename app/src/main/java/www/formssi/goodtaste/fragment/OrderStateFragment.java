package www.formssi.goodtaste.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.adapter.OrderAdapter;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

/**
 * 订单分类的fragment
 */
public class OrderStateFragment extends Fragment {
    private RecyclerView rlvOrderState;//分类订单显示的列表
    private LinearLayout lltNoOrder;//没有订单时的父布局
    private int state = 0;//进入视图时显示的fragment

    public OrderStateFragment(int state) {
        super();
        this.state = state;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_state, container, false);
        initView(v);
        initData();
        return v;
    }

    private void initView(View v) {
        rlvOrderState = (RecyclerView) v.findViewById(R.id.rlvOrderState);
        lltNoOrder = (LinearLayout) v.findViewById(R.id.lltNoOrder);
    }

    private void initData() {
        List<OrderBean> orders = DataBaseSQLiteUtil.queryOrder(state);
        if (orders.size() == 0) {
            rlvOrderState.setVisibility(View.GONE);
            lltNoOrder.setVisibility(View.VISIBLE);
        } else {
            OrderAdapter orderAdapter = new OrderAdapter(orders, getContext());
            rlvOrderState.setLayoutManager(new LinearLayoutManager(getContext()));
            rlvOrderState.setAdapter(orderAdapter);
        }
    }

}
