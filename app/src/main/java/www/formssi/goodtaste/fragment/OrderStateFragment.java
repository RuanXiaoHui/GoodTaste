package www.formssi.goodtaste.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.OrderStateActivity;
import www.formssi.goodtaste.adapter.OrderAdapter;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;


public class OrderStateFragment extends Fragment {


    RecyclerView rlvOrderState;
    OrderAdapter orderAdapter;
    List<OrderBean> orders;
    int state = 0;



    public OrderStateFragment() {
        super();
    }

    public OrderStateFragment(int state) {
        super();
        this.state = state;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_state, container, false);
        rlvOrderState = (RecyclerView) v.findViewById(R.id.rlvOrderState);
        orders = DataBaseSQLiteUtil.queryOrder(state);

        orderAdapter = new OrderAdapter(orders, getContext());
        rlvOrderState.setLayoutManager(new LinearLayoutManager(getContext()));
        rlvOrderState.setAdapter(orderAdapter);


        return v;
    }

}
