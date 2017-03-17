package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.adapter.XAdapter;

/**
 * 确认订单页面
 * 说明：包含送餐地址、食品列表、配送费、订单备注、待支付费用
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "ConfirmOrderActivity";
    private ImageView ivBack; //返回
    private ImageView ivStore; //店铺图片
    private TextView tvTitle; //标题
    private TextView tvName; //姓名
    private TextView tvGender; //性别
    private TextView tvPhone; //电话
    private TextView tvAddress; //送餐地址
    private TextView tvStroeName; //店铺名
    private TextView tvDistributionCost; //配送费
    private TextView tvRemarks; //配送费
    private TextView tvToBePay; //待支付
    private Button btnCommitOrder; //提交订单
    private ListView lvFood; //食物列表
    private LinearLayout lltAddress; //送餐地址栏
    private LinearLayout lltOrderRemarks; //订单备注栏
    private LinearLayout lltHeadView; //食物列表的头部
    private LinearLayout lltFootView; //食物列表的尾部

    private List<Object> list;
    private View headView;
    private View footView;

    private  int ORDER_REMARK = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        bindViews();
        tvTitle.setText(R.string.activity_confirmOrder_title);
        operateListView();

    }

    /**
     * 初始化、绑定控件
     */
    private void bindViews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivStore = (ImageView) findViewById(R.id.iv_ConfirmOrederActtivity_store);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvName = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_name);
        tvGender = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_gender);
        tvPhone = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_phone);
        tvAddress = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_address);
        tvStroeName = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_stroeName);

        tvToBePay = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_toBePay);
        btnCommitOrder  = (Button) findViewById(R.id.btn_ConfirmOrederActtivity_commitOrder);
        lvFood = (ListView) findViewById(R.id.lv_ConfirmOrederActtivity_food);
        headView = getLayoutInflater().inflate(R.layout.layout_comfir_order_head_view, null);
        footView = getLayoutInflater().inflate(R.layout.layout_comfir_order_foot_view, null);
        tvDistributionCost = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_distributionCost);
        tvRemarks = (TextView) footView.findViewById(R.id.tv_ConfirmOrederActtivity_remarks);
        lltAddress = (LinearLayout)headView.findViewById(R.id.llt_ConfirmOrederActtivity_address);
        lltOrderRemarks = (LinearLayout) footView.findViewById(R.id.llt_ConfirmOrederActtivity_orderRemarks);

        lltAddress.setOnClickListener(this);
        lltOrderRemarks.setOnClickListener(this);
        btnCommitOrder.setOnClickListener(this);
    }

    /**
     * 监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.llt_ConfirmOrederActtivity_address: //点击地址栏
                intent = new Intent(ConfirmOrderActivity.this,ReceiveAddressActivity.class);
                startActivity(intent);
                break;

            case R.id.llt_ConfirmOrederActtivity_orderRemarks: //点击订单备注栏
                intent =  new Intent(ConfirmOrderActivity.this,RemarkOrderActivity.class);
                startActivityForResult(intent,ORDER_REMARK);
                break;

            case R.id.btn_ConfirmOrederActtivity_commitOrder: //提交订单
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ORDER_REMARK && resultCode == RESULT_OK){
            tvRemarks.setText(data.getStringExtra("remarks"));
        }
    }

    /**
     * 对ListView的操作
     */
    private void operateListView() {
        //添加数据
        list = new ArrayList<Object>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        //添加头部和weibu
        lvFood.addHeaderView(headView);
        lvFood.addFooterView(footView);

        //添加适配器
        lvFood.setAdapter(new XAdapter(list, ConfirmOrderActivity.this) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Log.e(TAG, "getView: ");
                FoodViewHolder holder = null;
                if (convertView == null) {
                    holder = new FoodViewHolder();
                    convertView = LayoutInflater.from(ConfirmOrderActivity.this).inflate(R.layout.item_confirm_order_food, parent,false);
                    holder.tvfoodName = (TextView) convertView.findViewById(R.id.tv_ConfirmOrederActtivity_foodName);
                    holder.tvfoodNumber = (TextView) convertView.findViewById(R.id.tv_ConfirmOrederActtivity_foodNumber);
                    holder.tvfoodPrice = (TextView) convertView.findViewById(R.id.tv_ConfirmOrederActtivity_foodPrice);
                    convertView.setTag(holder);
                } else {
                    holder = (FoodViewHolder) convertView.getTag();
                }

                //赋值
//                holder.tvfoodName.setText(list.get(position));
                return convertView;
            }
        });
    }

    public class FoodViewHolder {
        TextView tvfoodName; //食物名称
        TextView tvfoodNumber; //购买数量
        TextView tvfoodPrice; //食物价格
    }
}
