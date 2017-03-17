package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.OrderBean;

/**
 * Created by GTs on 2017-03-16.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    List<OrderBean> list ;
    Context context;

    public OrderAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderHolder holder = new OrderHolder(LayoutInflater.from(context).inflate(R.layout.item_order,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
//        holder.tvShopName.setText(list.get(position).getShopName());
//        holder.tvPrice.setText(list.get(position).getPrice());
//        holder.tvOrderTime.setText(list.get(position).getOrderTime());
//        holder.tvTransactionStatus.setText(list.get(position).getTransactionStatus());
//
//        holder.tvCommodityName.setText(list.get(position).getCommodityName());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class OrderHolder extends RecyclerView.ViewHolder{

        TextView tvShopName;//商店名字
        TextView tvOrderTime;//交易时间
        TextView tvTransactionStatus;//交易状态
        TextView tvCommodityName;//商品名称
        TextView tvPrice;//商品单价

        ImageView imgShop;//商店图片

        Button btnStatusLogic;//状态逻辑按钮

        public OrderHolder(View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            tvShopName = (TextView) itemView.findViewById(R.id.tvShopName);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tvOrderTime);
            tvTransactionStatus = (TextView) itemView.findViewById(R.id.tvTransactionStatus);
            tvCommodityName = (TextView) itemView.findViewById(R.id.tvCommodityName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            imgShop = (ImageView) itemView.findViewById(R.id.imgShop);
            btnStatusLogic = (Button) itemView.findViewById(R.id.btnStatusLogic);
        }


    }


}
