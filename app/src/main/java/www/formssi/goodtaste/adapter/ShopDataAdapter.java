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
 * Created by zhuchenxu on 2017/3/17.
 */

public class ShopDataAdapter extends BaseAdapter {

    private List<FoodBean> mdatas;
    private LayoutInflater mInflater;

    public ShopDataAdapter(List<FoodBean> mdatas, Context context) {
        this.mdatas = mdatas;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            holder=new ViewHolder();
            view=mInflater.inflate(R.layout.item_shop_listview,null);
            holder.tv= (TextView) view.findViewById(R.id.tvRight);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tv.setText(mdatas.get(i).getGoodsName());
        return view;
    }

    static class ViewHolder{
        TextView tv;
    }
}
