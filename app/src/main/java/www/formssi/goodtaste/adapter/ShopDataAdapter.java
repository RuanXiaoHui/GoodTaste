package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.widget.NumberSubAdd;

/**
 * Created by zhuchenxu on 2017/3/17.
 */

public class ShopDataAdapter extends BaseAdapter {

    private List<FoodBean> mdatas;
    private LayoutInflater mInflater;
    private int CountNumber=0;
    private Map<String,FoodBean> mBeans;

    public ShopDataAdapter(List<FoodBean> mdatas, Context context) {
        this.mdatas = mdatas;
        this.mInflater=LayoutInflater.from(context);
        mBeans=new HashMap<String,FoodBean>();
    }

    //////////////*对外提供接口//////////////
    public interface  OnExtralClickListener{
        void onClickMoney(int vue,Map<String,FoodBean> beans);
    }
    public OnExtralClickListener onExtralClickListener;

    public void setOnExtralClickListener(OnExtralClickListener onExtralClickListener) {
        this.onExtralClickListener = onExtralClickListener;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            holder=new ViewHolder();
            view=mInflater.inflate(R.layout.item_shop_listview,null);
            holder.ivFoodIcon= (ImageView) view.findViewById(R.id.ivFoodIcon);
            holder.tvFoodName= (TextView) view.findViewById(R.id.tvFoodName);
            holder.tvFoodBuyNumber= (TextView) view.findViewById(R.id.tvFoodBuyNumber);
            holder.tvFoodMoney= (TextView) view.findViewById(R.id.tvFoodMoney);
            holder.numberSubAdd= (NumberSubAdd) view.findViewById(R.id.numberSubAdd);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        FoodBean bean=mdatas.get(i);
        holder.ivFoodIcon.setImageResource(bean.getGoodsIcon());
        holder.tvFoodName.setText(bean.getGoodsName());
        holder.tvFoodBuyNumber.setText("月销量："+bean.getGoodsNumber()+"份");
        holder.tvFoodMoney.setText("￥"+bean.getGoodsMoney()+"元");
        holder.numberSubAdd.setNubNumBerSubAddClick(new NumberSubAdd.NumBerSubAddClick() {
            @Override
            public void AddBtnOnClick(View v, int vue) {
                if (onExtralClickListener!=null&&mdatas.get(i).getGoodsBuynumber()<=9){
                    mdatas.get(i).setGoodsBuynumber(vue);
                    int price= Integer.parseInt(mdatas.get(i).getGoodsMoney());//获得当前的选项的单价
                    CountNumber=CountNumber+price;
                    mBeans.put(mdatas.get(i).getGoodsId(),mdatas.get(i));
                    onExtralClickListener.onClickMoney(CountNumber,mBeans);
                }
            }

            @Override
            public void SubBtnOnclick(View v, int vue) {
                if (onExtralClickListener!=null&&mdatas.get(i).getGoodsBuynumber()>=1){
                    int price= Integer.parseInt(mdatas.get(i).getGoodsMoney());//获得当前的选项的单价
                    mdatas.get(i).setGoodsBuynumber(vue);
                    CountNumber=CountNumber-price;
                    if (vue==0){
                        mBeans.remove(mdatas.get(i).getGoodsId());
                    }else{
                        mBeans.put(mdatas.get(i).getGoodsId(),mdatas.get(i));
                    }
                    onExtralClickListener.onClickMoney(CountNumber,mBeans);
                }
            }
        });
        holder.numberSubAdd.setVue(bean.getGoodsBuynumber());
        return view;
    }

    static class ViewHolder{
        ImageView ivFoodIcon;
        TextView tvFoodName;
        TextView tvFoodBuyNumber;
        TextView tvFoodMoney;
        NumberSubAdd numberSubAdd;
    }

    public void addData(List<FoodBean> data){
        mdatas.clear();
        mdatas.addAll(data);
        this.notifyDataSetChanged();
    }
}
