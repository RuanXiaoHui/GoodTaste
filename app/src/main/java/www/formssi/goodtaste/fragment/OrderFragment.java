package www.formssi.goodtaste.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.order.OrderStateActivity;
import www.formssi.goodtaste.adapter.OrderAdapter;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

/**
 * 订单页面
 */
public class OrderFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvOrderList;//显示所有订单的recycle人view
    private LinearLayout lltNoOrder;//没有订单时显示的布局
    private Button btnGoSingle;//没有订单时显示的按钮
    private LinearLayout lltNotPay;//未支付的父布局
    private LinearLayout lltNotDelivery;//未配送的父布局
    private LinearLayout lltDeliveryIng;//送餐中的父布局
    private LinearLayout lltNotComment;//未评论的父布局
    private LinearLayout lltFinish;//已完成的父布局


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        initView(v);
        initListener();
        initData();
        return v;
    }

    /**
     * 初始化控件
     *
     * @param v 视图
     */
    private void initView(View v) {
        rvOrderList = (RecyclerView) v.findViewById(R.id.rvOrderList);
        lltNoOrder = (LinearLayout) v.findViewById(R.id.lltNoOrder);
        btnGoSingle = (Button) v.findViewById(R.id.btnGoSingle);
        lltNotPay = (LinearLayout) v.findViewById(R.id.lltNotPay);
        lltNotDelivery = (LinearLayout) v.findViewById(R.id.lltNotDelivery);
        lltDeliveryIng = (LinearLayout) v.findViewById(R.id.lltDeliveryIng);
        lltNotComment = (LinearLayout) v.findViewById(R.id.lltNotComment);
        lltFinish = (LinearLayout) v.findViewById(R.id.lltFinish);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 为控件添加监听事件
     */
    private void initListener() {
        lltNotPay.setOnClickListener(this);
        lltNotDelivery.setOnClickListener(this);
        lltDeliveryIng.setOnClickListener(this);
        lltNotComment.setOnClickListener(this);
        lltFinish.setOnClickListener(this);
        btnGoSingle.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        List<OrderBean> orders = DataBaseSQLiteUtil.queryOrder(OrderState.ALL);
        if (orders.size() == 0) {//没有订单,则显示的布局
            rvOrderList.setVisibility(View.GONE);
            lltNoOrder.setVisibility(View.VISIBLE);
        } else {//有订单时显示的布局
            OrderAdapter orderAdapter = new OrderAdapter(orders, getContext());
            rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvOrderList.setAdapter(orderAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), OrderStateActivity.class);
        switch (v.getId()) {
            case R.id.btnGoSingle://设置接口,实现去下单的监听事件
                if (getActivity() instanceof MeOnClickListener) {
                    ((MeOnClickListener) getActivity()).onBtnGoSingleClick();
                }
                break;
            case R.id.lltNotPay://未支付
                intent.putExtra("stateNum", OrderState.NOT_PAY);
                startActivity(intent);
                break;
            case R.id.lltNotDelivery://未发货 
                intent.putExtra("stateNum", OrderState.NOT_DELIVERY);
                startActivity(intent);
                break;
            case R.id.lltDeliveryIng://送餐中 
                intent.putExtra("stateNum", OrderState.DELIVERY_ING);
                startActivity(intent);
                break;
            case R.id.lltNotComment://未评论 
                intent.putExtra("stateNum", OrderState.NOT_COMMENT);
                startActivity(intent);
                break;
            case R.id.lltFinish://已完成 
                intent.putExtra("stateNum", OrderState.FINISH);
                startActivity(intent);
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
