package www.formssi.goodtaste.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.widget.NoScrollListView;

/**
 * 订单详情Activity类
 * 说明：订单详情页面信息显示
 *
 * @author qq724418408
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvOrderStatus; // 订单状态
    private NoScrollListView lvFoodList; // 食品listView
    private Button btnOK; // 确认按钮：根据状态显示不同文字：去支付、评价、再来一单
    private Button btnCancel; // 取消按钮：取消订单
    private Button btnContactBusiness; // 联系商家按钮：拨打商家电话
    private List<FoodBean> listFoodBean; // 食品列表
    private FoodListAdapter adapter; // 适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initViews(); // 初始化控件
        initEvents(); // 注册事件
    }

    private void initEvents() {
        DataBaseSQLiteUtil.openDataBase();
    }

    /**
     * 初始化控件
     */
    public void initViews() {
        listFoodBean = new ArrayList<>();
        listFoodBean.add(new FoodBean("", "鱼香肉丝", 0, 0, 0, ""));
        listFoodBean.add(new FoodBean("", "香菜牛肉", 0, 0, 0, ""));
        listFoodBean.add(new FoodBean("", "芹菜炒鸡蛋", 0, 0, 0, ""));
        listFoodBean.add(new FoodBean("", "餐盒", 0, 0, 0, ""));
        adapter = new FoodListAdapter();
        lvFoodList = (NoScrollListView) findViewById(R.id.lv_order_food_list);
        lvFoodList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    class FoodListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listFoodBean.size();
        }

        @Override
        public FoodBean getItem(int position) {
            return listFoodBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FoodViewHolder holder = null;
            if (convertView == null) {
                holder = new FoodViewHolder();
                convertView = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.item_order_food_list_viewl, null);
                holder.tvFoodName = (TextView) convertView.findViewById(R.id.iv_order_item_food_name);
                holder.tvFoodCount = (TextView) convertView.findViewById(R.id.iv_order_item_food_count);
                holder.tvFoodPrice = (TextView) convertView.findViewById(R.id.iv_order_item_food_price);
                convertView.setTag(holder);
            } else {
                holder = (FoodViewHolder) convertView.getTag();
            }
            FoodBean bean = getItem(position);
            holder.tvFoodName.setText(bean.getGoodsName());
            //holder.tvFoodCount.setText(bean.getGoodsName());
            //holder.tvFoodPrice.setText(bean.getGoodsName());
            return convertView;
        }
    }

    class FoodViewHolder {
        TextView tvFoodName; // 食品名称
        TextView tvFoodCount; // 食品数量
        TextView tvFoodPrice; // 食品单价
    }
}
