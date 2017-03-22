package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.ShopBean;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class HomeGoodsBaseAdapter extends BaseAdapter {
    private List<ShopBean> mDatas;
    private LayoutInflater mInflater;


    public HomeGoodsBaseAdapter(List<ShopBean> datas, Context mContext) {
        mDatas = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_home_listview_goods, null);

            holder.ivGoodsIcon = (ImageView) convertView.findViewById(R.id.ivGoodsIcon);
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tvShopName);
            holder.tvShopDesc = (TextView) convertView.findViewById(R.id.tvShopDesc);
            holder.tvShopCount = (TextView) convertView.findViewById(R.id.tvShopCount);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        ShopBean bean = mDatas.get(position);
        holder.ivGoodsIcon.setImageResource(bean.getShopPic());
        holder.tvShopName.setText(bean.getShopName());
        holder.tvShopDesc.setText(bean.getShopDesc());
        holder.tvShopCount.setText("月销量：" + bean.getShopCount() + "份");

        return convertView;
    }

    static class ViewHolder {
        ImageView ivGoodsIcon;
        TextView tvShopName;
        TextView tvShopDesc;
        TextView tvShopCount;

    }
}
