package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.formssi.goodtaste.R;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class HomeGoodsBaseAdapter extends BaseAdapter{
    private List<String> mDatas;
    private LayoutInflater mInflater;


    public HomeGoodsBaseAdapter(List<String> datas, Context mContext) {
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
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_home_listview_goods,null);

            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else{

            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mDatas.get(position).toString());

        return convertView;
    }
    static class ViewHolder{
        TextView tv;
    }
}
