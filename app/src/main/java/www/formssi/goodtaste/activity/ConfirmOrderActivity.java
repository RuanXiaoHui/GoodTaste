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
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DateUtil;
import www.formssi.goodtaste.utils.OrderUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.ORDER_REMARK_REQUEST;
import static www.formssi.goodtaste.constant.ConstantConfig.ORDER_REMARK_RESULT;
import static www.formssi.goodtaste.constant.ConstantConfig.OREDER_REDDRESS_REQUEST;
import static www.formssi.goodtaste.constant.ConstantConfig.OREDER_REDDRESS_RESULT;

/**
 * 确认订单页面
 * 说明：包含送餐地址、食品列表、配送费、订单备注、待支付费用
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

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
    private View headView; //列表头部
    private View footView; //列表尾部
    private OrderBean orderBean; //订单实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        DataBaseSQLiteUtil.openDataBase();
        bindViews();
        tvTitle.setText(R.string.activity_confirmOrder_title);
        createOrder();
        operateListView();
    }



    @Override
    protected void onDestroy() {
        DataBaseSQLiteUtil.closeDataBase();
        super.onDestroy();
    }

    /**
     * 初始化、绑定控件
     */
    private void bindViews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivStore = (ImageView) findViewById(R.id.iv_ConfirmOrederActtivity_store);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvToBePay = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_toBePay);
        btnCommitOrder = (Button) findViewById(R.id.btn_ConfirmOrederActtivity_commitOrder);
        lvFood = (ListView) findViewById(R.id.lv_ConfirmOrederActtivity_food);
        headView = getLayoutInflater().inflate(R.layout.layout_comfir_order_head_view, null);
        footView = getLayoutInflater().inflate(R.layout.layout_comfir_order_foot_view, null);
        tvName = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_name);
        tvGender = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_gender);
        tvPhone = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_phone);
        tvAddress = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_address);
        lltAddress = (LinearLayout) headView.findViewById(R.id.llt_ConfirmOrederActtivity_address);
        tvStroeName = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_stroeName);
        tvDistributionCost = (TextView) footView.findViewById(R.id.tv_ConfirmOrederActtivity_distributionCost);
        tvRemarks = (TextView) footView.findViewById(R.id.tv_ConfirmOrederActtivity_remarks);
        lltOrderRemarks = (LinearLayout) footView.findViewById(R.id.llt_ConfirmOrederActtivity_orderRemarks);

        lltAddress.setOnClickListener(this);
        lltOrderRemarks.setOnClickListener(this);
        btnCommitOrder.setOnClickListener(this);
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        Intent intent;
        switch (v.getId()) {
            case R.id.llt_ConfirmOrederActtivity_address: //点击地址栏
                intent = new Intent(ConfirmOrderActivity.this, ReceiveAddressActivity.class);
                startActivityForResult(intent, OREDER_REDDRESS_REQUEST);
                break;

            case R.id.llt_ConfirmOrederActtivity_orderRemarks: //点击订单备注栏
                intent = new Intent(ConfirmOrderActivity.this, RemarkOrderActivity.class);
                startActivityForResult(intent, ORDER_REMARK_REQUEST);
                break;

            case R.id.btn_ConfirmOrederActtivity_commitOrder: //提交订单
                Log.i(TAG, "onClick: 点击了确认订单按钮");
//                DataBaseSQLiteUtil.addOrder()
                intent = new Intent(ConfirmOrderActivity.this, OnlinePaymentActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == ORDER_REMARK_REQUEST && resultCode == ORDER_REMARK_RESULT) { //订单备注返回数据
                tvRemarks.setText(data.getStringExtra("remarks"));
            } else if (requestCode == OREDER_REDDRESS_REQUEST && resultCode == OREDER_REDDRESS_RESULT) { //收货地址
                tvName.setText(data.getStringExtra("name"));
                tvGender.setText(data.getStringExtra("gender"));
                tvPhone.setText(data.getStringExtra("phone"));
                tvAddress.setText(data.getStringExtra("address"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 创建订单
     */
    private void createOrder() {

//        //用户对象
//        UserBean userBean= new UserBean();
//
//        //商店对象
//        ShopBean shopBean = new ShopBean();
//        shopBean.setShopId();  //商店Id
//        shopBean.setShopName(); //商店名称
//        shopBean.setShopPic(); //商店tuxiang
//
//        //订单对象
//        OrderBean orderBean = new OrderBean();
//        orderBean.setShopBean(shopBean); //商店实体类
//        orderBean.setOrderNum(OrderUtil.getOrderNumber(userBean.getPhoneNimeber()));//订单号
//        orderBean.setStatus();//订单状态
//        orderBean.setOrderTotalMoney();//订单总金额
//        orderBean.setDiscountMoney(); //优惠金额
//        orderBean.setDistributingFee();//配送费
//        orderBean.setActualPayment();//实付金额
//        orderBean.setOrderTime(DateUtil.getCurrentTime()); //下单时间
//        orderBean.setAddressId();  //送餐地址Id

//        List<FoodBean> foodBeanList =

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
                    convertView = LayoutInflater.from(ConfirmOrderActivity.this).inflate(R.layout.item_confirm_order_food, parent, false);
                    holder.tvfoodName = (TextView) convertView.findViewById(R.id.tv_ConfirmOrederActtivity_foodName);
                    holder.tvfoodNumber = (TextView) convertView.findViewById(R.id.tv_ConfirmOrederActtivity_foodNumber);
                    holder.tvfoodPrice = (TextView) convertView.findViewById(R.id.tv_ConfirmOrederActtivity_foodPrice);
                    convertView.setTag(holder);
                } else {
                    holder = (FoodViewHolder) convertView.getTag();
                }

                //赋值
                FoodBean foodBean = (FoodBean) list.get(position);
                holder.tvfoodName.setText(foodBean.getGoodsName());
                holder.tvfoodNumber.setText(foodBean.getGoodsBuynumber());
                holder.tvfoodPrice.setText(Integer.valueOf(foodBean.getGoodsMoney()) * foodBean.getGoodsBuynumber());
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
