package www.formssi.goodtaste.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.OrderDetailActivity;
import www.formssi.goodtaste.activity.OrderStateActivity;
import www.formssi.goodtaste.adapter.OrderAdapter;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static android.content.ContentValues.TAG;

public class OrderFragment extends Fragment implements View.OnClickListener {

    RecyclerView rvOrderList;
    List<OrderBean> orders;
    OrderAdapter orderAdapter;
    LinearLayout lltNoOrder;
    Button btnGoSingle;
    LinearLayout lltNotPay;
    LinearLayout lltNotDelivery;
    LinearLayout lltDeliveryIng;
    LinearLayout lltNotComment;
    LinearLayout lltFinish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, null);
        initView(v);
        setOnClick();
//        DataBaseSQLiteUtil.insertOrder();//测试
        orders = DataBaseSQLiteUtil.queryOrder(OrderState.ALL);
        if (orders.size() == 0) {
            rvOrderList.setVisibility(View.GONE);
            lltNoOrder.setVisibility(View.VISIBLE);

        } else {
            orderAdapter = new OrderAdapter(orders, getContext());
            rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvOrderList.setAdapter(orderAdapter);
        }

        return v;
    }

    private void setOnClick() {
        lltNotPay.setOnClickListener(this);
        lltNotDelivery.setOnClickListener(this);
        lltDeliveryIng.setOnClickListener(this);
        lltNotComment.setOnClickListener(this);
        lltFinish.setOnClickListener(this);
        btnGoSingle.setOnClickListener(this);
    }

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


    public interface MeOnClickListener {
        void onBtnGoSingleClick();
    }
}
