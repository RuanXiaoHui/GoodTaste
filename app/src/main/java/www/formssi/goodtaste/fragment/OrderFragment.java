package www.formssi.goodtaste.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.adapter.OrderAdapter;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static android.content.ContentValues.TAG;

public class OrderFragment extends Fragment implements View.OnClickListener {

    RecyclerView rvOrderList;
    List<OrderBean> orders;
    OrderAdapter orderAdapter;
    LinearLayout lltNoOrder;
    Button btnGoSingle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, null);
        rvOrderList = (RecyclerView) v.findViewById(R.id.rvOrderList);
        lltNoOrder = (LinearLayout) v.findViewById(R.id.lltNoOrder);
        btnGoSingle = (Button) v.findViewById(R.id.btnGoSingle);
        btnGoSingle.setOnClickListener(this);
        DataBaseSQLiteUtil.insertOrder();
        DataBaseSQLiteUtil.insertOrder();
        DataBaseSQLiteUtil.insertOrder();
        DataBaseSQLiteUtil.insertOrder();
        orders = DataBaseSQLiteUtil.queryOrder();
        Log.e(TAG, "onCreateView: "+orders.size() );
//                new ArrayList<>();
//
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));

//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));
//        orders.add(new OrderBean("好味道","2017-03-11 22:30","宫保鸡丁","23","交易完成"));

        if (orders==null) {
            rvOrderList.setVisibility(View.GONE);
            lltNoOrder.setVisibility(View.VISIBLE);

        }else {
            orderAdapter = new OrderAdapter(orders, getContext());
            rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvOrderList.setAdapter(orderAdapter);
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoSingle:
                if (getActivity() instanceof MeOnClickListener) {
                    ((MeOnClickListener) getActivity()).onBtnGoSingleClick();
                }
                break;
        }
    }

    public interface MeOnClickListener {
        void onBtnGoSingleClick();

    }
}
