package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.OrderState;
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
    private TextView tvAddressNull; //送餐地址为空
    private TextView tvStroeName; //店铺名
    private TextView tvDistributionCost; //配送费
    private TextView tvRemarks; //订单备注
    private TextView tvToBePay; //待支付
    private Button btnCommitOrder; //提交订单
    private ListView lvFood; //食物列表
    private LinearLayout lltAddress; //送餐地址栏
    private LinearLayout lltOrderRemarks; //订单备注栏
    private LinearLayout lltHeadView; //食物列表的头部
    private LinearLayout lltFootView; //食物列表的尾部

    private View headView; //列表头部
    private View footView; //列表尾部
    private OrderBean orderBean; //订单实体类
    private List<FoodBean> foodBeanList;  //食物列表
    private Intent intent;
    private ShopBean shopBean; //商店对象
    private int money;
    private AddressBean addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        DataBaseSQLiteUtil.openDataBase();
        bindViews();
        tvTitle.setText(R.string.activity_confirmOrder_title);

        //获取数据并显示
        operateListView();
        ivStore.setImageResource(shopBean.getShopPic()); //商店图片
        tvStroeName.setText(shopBean.getShopName()); //店名
        tvDistributionCost.setText(shopBean.getShopMoney()); //配送费
        tvToBePay.setText("¥" + (money + Integer.parseInt(shopBean.getShopMoney()))); //待支付
        //创建订单
        createOrder();

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

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

        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvToBePay = (TextView) findViewById(R.id.tv_ConfirmOrederActtivity_toBePay);
        btnCommitOrder = (Button) findViewById(R.id.btn_ConfirmOrederActtivity_commitOrder);
        lvFood = (ListView) findViewById(R.id.lv_ConfirmOrederActtivity_food);
        headView = getLayoutInflater().inflate(R.layout.layout_comfir_order_head_view, null);
        footView = getLayoutInflater().inflate(R.layout.layout_comfir_order_foot_view, null);
        ivStore = (ImageView) headView.findViewById(R.id.iv_ConfirmOrederActtivity_store);
        tvAddressNull = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_addressNull);
        tvName = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_name);
        tvGender = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_gender);
        tvPhone = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_phone);
        tvAddress = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_address);
        lltAddress = (LinearLayout) headView.findViewById(R.id.llt_ConfirmOrederActtivity_address);
        tvStroeName = (TextView) headView.findViewById(R.id.tv_ConfirmOrederActtivity_stroeName);
        tvDistributionCost = (TextView) footView.findViewById(R.id.tv_ConfirmOrederActtivity_distributionCost);
        tvRemarks = (TextView) footView.findViewById(R.id.tv_ConfirmOrederActtivity_remarks);
        lltOrderRemarks = (LinearLayout) footView.findViewById(R.id.llt_ConfirmOrederActtivity_orderRemarks);

        ivBack.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;

            case R.id.llt_ConfirmOrederActtivity_address: //点击地址栏
                intent = new Intent(ConfirmOrderActivity.this, ReceiveAddressActivity.class);
                startActivityForResult(intent, OREDER_REDDRESS_REQUEST);
                break;

            case R.id.llt_ConfirmOrederActtivity_orderRemarks: //点击订单备注栏
                intent = new Intent(ConfirmOrderActivity.this, RemarkOrderActivity.class);
                startActivityForResult(intent, ORDER_REMARK_REQUEST);
                break;

            case R.id.btn_ConfirmOrederActtivity_commitOrder: //提交订单
                if (addressBean != null) {
                    long orderId = DataBaseSQLiteUtil.addOrder(orderBean, foodBeanList);
                    intent = new Intent(ConfirmOrderActivity.this, OnlinePaymentActivity.class);
                    intent.putExtra("orderId", orderId + "");
                    intent.putExtra("storeName", shopBean.getShopName());
                    intent.putExtra("totalPay", (money + Integer.parseInt(shopBean.getShopMoney())) + "");
                    startActivity(intent);
                } else {
                    // 提示用户输入地址
                    Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
                }
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
            } else if (requestCode == OREDER_REDDRESS_REQUEST && resultCode == OREDER_REDDRESS_RESULT) { //选择收货地址
                tvAddressNull.setVisibility(View.GONE);
                tvName.setText(data.getStringExtra("name"));
                tvGender.setText(data.getStringExtra("gender"));
                tvPhone.setText(data.getStringExtra("phone"));
                tvAddress.setText(data.getStringExtra("address"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 对ListView的操作
     */
    private void operateListView() {

        intent = getIntent();
        shopBean = (ShopBean) intent.getSerializableExtra("ShopBeans");
        foodBeanList = new ArrayList<>();
        foodBeanList.clear();
        foodBeanList = (List<FoodBean>) intent.getSerializableExtra("foodBeans");
        money = intent.getIntExtra("CountMoney", 0);

        //添加头部和weibu
        lvFood.addHeaderView(headView);
        lvFood.addFooterView(footView);

        UserBean userBean = new UserBean();
        userBean.setAddressId("1");
        // 根据地址id获取用户保存的地址列表
        addressBean = DataBaseSQLiteUtil.getAddressById(userBean.getAddressId());
        if (addressBean != null) {
            tvAddressNull.setVisibility(View.GONE);
            tvName.setText(addressBean.getName());
            tvGender.setText(addressBean.getGender());
            tvPhone.setText(addressBean.getPhone());
            tvAddress.setText(addressBean.getAddress());
        } else {
            tvAddressNull.setVisibility(View.VISIBLE);
        }

        //添加适配器
        lvFood.setAdapter(new FoodListAdapter(foodBeanList));
    }

    /**
     * 创建订单
     */
    private void createOrder() {

        //用户对象
        UserBean userBean = new UserBean();
        userBean.setUserId("1");
        userBean.setPhoneNumber("18376611446");

        orderBean = new OrderBean();
        orderBean.setShopBean(shopBean); //商店实体类
        orderBean.setOrderNum(OrderUtil.getOrderNumber(userBean.getPhoneNumber()));//订单号
        orderBean.setStatus(OrderState.NOT_PAY + "");//订单状态
        orderBean.setOrderTotalMoney(money + "");//订单总金额
        orderBean.setDiscountMoney("0"); //优惠金额
        orderBean.setDistributingFee(shopBean.getShopMoney());//配送费
        orderBean.setActualPayment((money + Integer.parseInt(shopBean.getShopMoney())) + "");//实付金额
        orderBean.setOrderTime(DateUtil.getCurrentTime()); //下单时间
        orderBean.setAddressId(1);  //送餐地址Id
    }

    public class FoodListAdapter extends BaseAdapter {

        protected List<FoodBean> list;

        public FoodListAdapter(List<FoodBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public FoodBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
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
            FoodBean foodBean = getItem(position);
            holder.tvfoodName.setText(foodBean.getGoodsName());
            holder.tvfoodNumber.setText("x" + foodBean.getGoodsBuynumber());
            holder.tvfoodPrice.setText("¥" + Integer.valueOf(foodBean.getGoodsMoney()) * foodBean.getGoodsBuynumber());
            return convertView;
        }
    }

    public class FoodViewHolder {
        TextView tvfoodName; //食物名称
        TextView tvfoodNumber; //购买数量
        TextView tvfoodPrice; //食物价格
    }
}
