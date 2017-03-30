package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.order.OrderDetailActivity;
import www.formssi.goodtaste.activity.pay.OnlinePaymentActivity;
import www.formssi.goodtaste.bean.EventBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.ClickUtil;
import www.formssi.goodtaste.utils.OrderUtil;
import www.formssi.goodtaste.utils.ToastUtil;

/**
 * 订单列表的adapter
 * Created by GTs on 2017-03-16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private List<OrderBean> list;
    private Context context;

    public OrderAdapter(List<OrderBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_order;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.OrderHolder holder, int position) {
        holder.tvShopName.setText(list.get(position).getShopName());
        holder.tvPrice.setText("¥ " + list.get(position).getActualPayment());
        holder.imgShop.setImageResource(list.get(position).getShopPicture());
        holder.tvOrderTime.setText(list.get(position).getOrderTime());
        holder.tvOrderContent.setText(list.get(position).getOrderContent());
        initListener(holder, position);
    }


    private void initListener(OrderHolder holder, final int position) {
        int status = Integer.valueOf(list.get(position).getStatus());//订单状态码
        holder.lltOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//item的点击事件
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra(ConstantConfig.INTENT_ORDER_ID, list.get(position).getOrderId());
                context.startActivity(intent);
            }
        });
        switch (status) {//状态按钮
            case OrderState.NOT_PAY://未支付
                holder.tvTransactionStatus.setText(R.string.order_state_not_pay);
                holder.btnStatusLogic.setText(R.string.order_state_btn_not_pay);
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, OnlinePaymentActivity.class);
                        intent.putExtra(ConstantConfig.INTENT_ORDER_ID, list.get(position).getOrderId());
                        intent.putExtra(ConstantConfig.INTENT_ORDER_NUM, list.get(position).getOrderNum());
                        intent.putExtra(ConstantConfig.INTENT_STORE_NAME, list.get(position).getShopName());
                        intent.putExtra(ConstantConfig.INTENT_ACTUAL_PAYMENT, list.get(position).getActualPayment());
                        EventBean eventBean = new EventBean();
                        eventBean.setAction(list.get(position).getOrderNum());
                        EventBus.getDefault().post(eventBean);
                        context.startActivity(intent);
                    }
                });
                break;
            case OrderState.NOT_COMMENT://未评论
                holder.tvTransactionStatus.setText(R.string.order_state_not_comment);
                holder.btnStatusLogic.setText(R.string.order_state_btn_not_comment);
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
            case OrderState.NOT_DELIVERY://未发货
                holder.tvTransactionStatus.setText(R.string.order_state_not_delivery);
                holder.btnStatusLogic.setText(R.string.order_state_btn_not_delivery);

                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast(R.string.toast_order_remind);
                        if (ClickUtil.isFastClick(3000)) { // 三秒内重复点击也只执行一次
                            OrderUtil.reminderOrder(list.get(position).getOrderId()); // 催单操作
                        }
                    }
                });
                break;
            case OrderState.FINISH://订单完成
                holder.btnStatusLogic.setText(R.string.order_state_finish);
                holder.tvTransactionStatus.setText(R.string.order_state_btn_finish);
                holder.btnStatusLogic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
            case OrderState.DELIVERY_ING://送餐中
                holder.tvTransactionStatus.setText(R.string.order_state_delivery_ing);
                holder.btnStatusLogic.setText(R.string.order_state_btn_delivery_ing);
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


    class OrderHolder extends RecyclerView.ViewHolder {
        private LinearLayout lltOrderItem;
        private TextView tvShopName;//商店名字
        private TextView tvOrderTime;//交易时间
        private TextView tvTransactionStatus;//交易状态
        private TextView tvOrderContent;//商品详情
        private TextView tvPrice;//商品单价
        private ImageView imgShop;//商店图片
        private Button btnStatusLogic;//状态逻辑按钮

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
