package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.GoodsMenu;

/**
 * Created by zhuchenxu on 2017/3/17.
 */

public class ShopMenuAdapter extends BaseAdapter {

    private List<GoodsMenu> mdatas;
    private LayoutInflater mInflater;

    public ShopMenuAdapter(List<GoodsMenu> mdatas, Context context) {
        this.mdatas = mdatas;
        mInflater=LayoutInflater.from(context);
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
            view=mInflater.inflate(R.layout.item_goods_leftmenu,null);
            holder.tv= (TextView) view.findViewById(R.id.tvMenu);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tv.setText(mdatas.get(i).getName());
        return view;
    }

    static class ViewHolder{
        TextView tv;
    }
}
