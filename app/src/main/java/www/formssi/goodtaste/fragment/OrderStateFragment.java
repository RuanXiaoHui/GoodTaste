package www.formssi.goodtaste.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

/**
 * 订单分类的fragment
 */
public class OrderStateFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rlvOrderState;//分类订单显示的列表
    private LinearLayout lltNoOrder;//没有订单时的父布局
    private int state = 0;//进入视图时显示的fragment
    private Button btnGoSingle;//没有订单时显示的按钮
    private LoadMoreAdapter orderAdapter;
    private SwipeRefreshLayout swipeOrderState;
    private CountDownTimer downTimer; // 倒计时器
    private OrderAdapter adapter;
    private int page = 0;

    public List<OrderBean> getOrders() {
        return orders;
    }

    private List<OrderBean> orders;

    public OrderStateFragment(int state) {
        super();
        this.state = state;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_state, container, false);
        Log.e("state", "onCreateView:    " + state);
        initView(v);
        initListener();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();//再次回到页面刷新数据
        EventBus.getDefault().register(this);
    }

    private void initView(View v) {
        swipeOrderState = (SwipeRefreshLayout) v.findViewById(R.id.swipeOrderState);
        rlvOrderState = (RecyclerView) v.findViewById(R.id.rlvOrderState);
        lltNoOrder = (LinearLayout) v.findViewById(R.id.lltNoOrder);
        btnGoSingle = (Button) v.findViewById(R.id.btnGoSingle);
        swipeOrderState.setColorSchemeResources(R.color.appColor);
    }

    private void initData() {
        page = 0;
        orders = DataBaseSQLiteUtil.queryOrder(state, 0, 5);
        if (orders.size() == 0) {//如果没有该类型订单,显示去下单按钮
            rlvOrderState.setVisibility(View.GONE);
            lltNoOrder.setVisibility(View.VISIBLE);
        } else {//有订单时显示订单列表
            adapter = new OrderAdapter(orders, getContext(), downTimer);
            orderAdapter = new LoadMoreAdapter(adapter);//加载更多...
            rlvOrderState.setLayoutManager(new LinearLayoutManager(getContext()));
            rlvOrderState.setAdapter(orderAdapter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(null != adapter){
            adapter.cancelTimer();
        }
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(EventBean eventBean) {//EventBus接收器，运行在主线程
        switch (eventBean.getAction()) {
            case ConstantConfig.REMIND_ORDER:
                initData();
                break;
        }
    }

    private void initListener() {
        btnGoSingle.setOnClickListener(this);
        swipeOrderState.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeOrderState.setRefreshing(false);
                        initData();
                    }
                }, 2000);
            }
        });
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
     * 实现LoadMoreAdapter里面的接口
     */
    @Override
    public int onLoadMoreClickListener() {
        page = page + 5;
        List loadMoreList = DataBaseSQLiteUtil.queryOrder(state, page, 5);
        orders.addAll(loadMoreList);
        orderAdapter.notifyDataSetChanged();
        return loadMoreList.size();
    }

    /**
     * 去下单按钮回调接口
     */
    public interface MeOnClickListener {
        void onBtnGoSingleClick();
    }
}
