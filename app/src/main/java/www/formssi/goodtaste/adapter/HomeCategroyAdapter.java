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
import www.formssi.goodtaste.bean.HomeGoodsCategroyBean;

/**
 * Created by Administrator on 2017/3/15.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class HomeCategroyAdapter extends BaseAdapter {
    private List<HomeGoodsCategroyBean> mDatas;
    private LayoutInflater mInflater;


    public HomeCategroyAdapter(List<HomeGoodsCategroyBean> datas, Context context) {
        mDatas = datas;
        mInflater=LayoutInflater.from(context);
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

        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.home_goods_gridview_item,null);
            holder.ivHomeCategroy= (ImageView) convertView.findViewById(R.id.ivHomeCategroy);
            holder.tvHomeCategroy= (TextView) convertView.findViewById(R.id.tvHomeCategroy);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.ivHomeCategroy.setImageResource(mDatas.get(position).getIcon());
        holder.tvHomeCategroy.setText(mDatas.get(position).getIconName());

        return convertView;
    }

    static class ViewHolder{
        TextView tvHomeCategroy;
        ImageView ivHomeCategroy;
    }
}
