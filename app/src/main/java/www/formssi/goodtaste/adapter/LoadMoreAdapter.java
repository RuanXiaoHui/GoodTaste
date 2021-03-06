package www.formssi.goodtaste.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.fragment.OrderStateFragment;

/**
 * Created by GTs on 2017-03-28.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter {

    private OrderAdapter orderAdapter;
    private OrderStateFragment orderStateFragment;


    public LoadMoreAdapter(OrderAdapter orderAdapter, OrderStateFragment orderStateFragment) {
        this.orderAdapter = orderAdapter;
        this.orderStateFragment = orderStateFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == R.layout.item_load_more) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return new LoadingItemHolder(v);
        } else {
            return orderAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadingItemHolder) {
            final LoadingItemHolder loadHold = (LoadingItemHolder) holder;
            loadHold.lltLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadHold.pbLoad.setVisibility(View.VISIBLE);
                    loadHold.tvLoad.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadHold.pbLoad.setVisibility(View.GONE);
                            loadHold.tvLoad.setVisibility(View.VISIBLE);
                            if (orderStateFragment.onLoadMoreClickListener() == 0) {//如果查到订单数量等于0，表示没有数据了
                                loadHold.tvLoad.setText(R.string.text_load_no_more);
                            }
                        }
                    }, 2000);
                }
            });
        } else {
            orderAdapter.onBindViewHolder((OrderAdapter.OrderHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (orderAdapter.getItemCount() >= 3) {
            if ((getItemCount() - 1) == position) {
                return R.layout.item_load_more;
            } else {
                return orderAdapter.getItemViewType(position);
            }
//        }
//        return orderAdapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
//        if (orderAdapter.getItemCount() >= 3) {
            return orderAdapter.getItemCount() + 1;
//        } else {
//            return orderAdapter.getItemCount();
//        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class LoadingItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout lltLoadMore;
        private TextView tvLoad;
        private ProgressBar pbLoad;
        public LoadingItemHolder(View itemView) {
            super(itemView);
            lltLoadMore = (LinearLayout) itemView.findViewById(R.id.lltLoadMore);
            tvLoad = (TextView) itemView.findViewById(R.id.tvLoad);
            pbLoad = (ProgressBar) itemView.findViewById(R.id.pbLoad);
        }
    }

    public interface OnLoadMoreClickListener {
        int onLoadMoreClickListener();
    }
}
