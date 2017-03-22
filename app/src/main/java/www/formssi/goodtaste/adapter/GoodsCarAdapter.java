package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.FoodBean;

/**
 * Created by zhuchenxu on 2017/3/22.
 */

public class GoodsCarAdapter extends BaseAdapter {

    private List<FoodBean> mDatas;
    private LayoutInflater mInflater;

    public GoodsCarAdapter(List<FoodBean> mDatas, Context context) {
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_listview_goods_car, null);
            holder.tvCarGoodsName = (TextView) view.findViewById(R.id.tvCarGoodsName);
            holder.tvCarGoodsMoney = (TextView) view.findViewById(R.id.tvCarGoodsMoney);
            holder.tvCarGoodsNumber = (TextView) view.findViewById(R.id.tvCarGoodsNumber);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvCarGoodsName.setText(mDatas.get(i).getGoodsName());
        holder.tvCarGoodsMoney.setText(mDatas.get(i).getGoodsMoney() + "元");
        holder.tvCarGoodsNumber.setText("x" + mDatas.get(i).getGoodsBuynumber());
        return view;
    }

    static class ViewHolder {
        TextView tvCarGoodsName;
        TextView tvCarGoodsMoney;
        TextView tvCarGoodsNumber;

    }
}
