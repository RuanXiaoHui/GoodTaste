package www.formssi.goodtaste.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.adapter.OrderAdapter;
import www.formssi.goodtaste.bean.OrderBean;

public class OrderFragment extends Fragment {

    RecyclerView rvOrderList;
    List<OrderBean> orders;
    OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, null);
        rvOrderList = (RecyclerView) v.findViewById(R.id.rvOrderList);

        orders = new ArrayList<>();

//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));

        orderAdapter = new OrderAdapter(orders,getContext());
        rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOrderList.setAdapter(orderAdapter);


        return v;
    }
}
