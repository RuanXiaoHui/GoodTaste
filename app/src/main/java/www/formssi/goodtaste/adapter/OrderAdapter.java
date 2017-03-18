package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.OrderDetailActivity;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.ContextUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by GTs on 2017-03-16.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> implements View.OnClickListener {

    List<OrderBean> list;
    Context context;

    public OrderAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        OrderHolder holder = new OrderHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final OrderHolder holder, int position) {
        holder.tvShopName.setText(list.get(position).getShopName());
        holder.tvPrice.setText(list.get(position).getPrice());
        holder.imgShop.setImageResource(list.get(position).getShopPicture());
        holder.tvOrderTime.setText(list.get(position).getOrderTime());
        holder.tvOrderContent.setText(list.get(position).getOrderContent());
        holder.lltOrderItem.setOnClickListener(this);
        int status = Integer.valueOf(list.get(position).getStatus());
        initStatusBtn(holder, status);
    }

    private void initStatusBtn(OrderHolder holder, int status) {
        switch (status) {
            case OrderState.NOT_PAY://未支付
                holder.tvTransactionStatus.setText("未支付");
                holder.btnStatusLogic.setText("去支付");
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
            case OrderState.NOT_COMMENT://未评论
                holder.tvTransactionStatus.setText("未评论");
                holder.btnStatusLogic.setText("去评论");
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
            case OrderState.NOT_DELIVERY://未发货
                holder.tvTransactionStatus.setText("未发货");
                holder.btnStatusLogic.setText("去催单");
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ContextUtil.getInstance(), R.string.toast_order_remind, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case OrderState.FINISH://订单完成
                holder.btnStatusLogic.setText("再来一单");
                holder.tvTransactionStatus.setText("交易完成");
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
            case OrderState.DELIVERY_ING://送餐中
                holder.tvTransactionStatus.setText("送餐中");
                holder.btnStatusLogic.setText("查看进度");
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lltOrderItem:
                context.startActivity(new Intent(context,OrderDetailActivity.class));
                break;

        }
    }


    class OrderHolder extends RecyclerView.ViewHolder {

        LinearLayout lltOrderItem;
        TextView tvShopName;//商店名字
        TextView tvOrderTime;//交易时间
        TextView tvTransactionStatus;//交易状态
        TextView tvOrderContent;//商品详情
        TextView tvPrice;//商品单价

        ImageView imgShop;//商店图片

        Button btnStatusLogic;//状态逻辑按钮

        public OrderHolder(View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            lltOrderItem = (LinearLayout) itemView.findViewById(R.id.lltOrderItem);
            tvShopName = (TextView) itemView.findViewById(R.id.tvShopName);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tvOrderTime);
            tvTransactionStatus = (TextView) itemView.findViewById(R.id.tvTransactionStatus);
            tvOrderContent = (TextView) itemView.findViewById(R.id.tvCommodityName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            imgShop = (ImageView) itemView.findViewById(R.id.imgShop);
            btnStatusLogic = (Button) itemView.findViewById(R.id.btnStatusLogic);
        }

    }



}
