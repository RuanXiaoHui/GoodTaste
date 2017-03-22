package www.formssi.goodtaste.activity.pay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import www.formssi.goodtaste.activity.mine.LoginActivity;
import www.formssi.goodtaste.activity.mine.ReceiveAddressActivity;
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.ContextUtil;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DateUtil;
import www.formssi.goodtaste.utils.OrderUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_ID;
import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_USER_ID;
import static www.formssi.goodtaste.constant.ConstantConfig.ORDER_REMARK_REQUEST;
import static www.formssi.goodtaste.constant.ConstantConfig.ORDER_REMARK_RESULT;
import static www.formssi.goodtaste.constant.ConstantConfig.OREDER_REDDRESS_REQUEST;
import static www.formssi.goodtaste.constant.ConstantConfig.OREDER_REDDRESS_RESULT;

/**
 * 确认订单页面
 * 说明：包含送餐地址、食品列表、配送费、订单备注、待支付费用
 * Created by sn on 2017/3/16.
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack; //返回
    private ImageView ivStore; //店铺图片
    private TextView tvTitle; //标题
    private TextView tvName; //姓名
    private TextView tvGender; //性别
    private TextView tvPhone; //电话
    private TextView tvAddress; //送餐地址
    private TextView tvAddressNull; //送餐地址为空
    private TextView tvStoreName; //店铺名
    private TextView tvDistributionCost; //配送费
    private TextView tvRemarks; //订单备注
    private TextView tvToBePay; //待支付
    private Button btnCommitOrder; //提交订单
    private ListView lvFood; //食物列表
    private LinearLayout lltAddress; //送餐地址栏
    private LinearLayout lltOrderRemarks; //订单备注栏
    private View headView; //列表头部
    private View footView; //列表尾部
    private OrderBean orderBean; //订单实体类
    private List<FoodBean> foodBeanList;  //食物列表
    private Intent intent;
    private ShopBean shopBean; //商店对象
    private int money; //食品总金额
    private UserBean userBean; //用户对象
    private boolean loginStatus;  //登录状态
    private AddressBean addressBean; //地址对象
    private boolean isAddressNull = true; //地址是否为空

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tvToBePay = (TextView) findViewById(R.id.tv_ConfirmOrderActivity_toBePay);
        btnCommitOrder = (Button) findViewById(R.id.btn_ConfirmOrderActivity_commitOrder);
        lvFood = (ListView) findViewById(R.id.lv_ConfirmOrderActivity_food);
        headView = getLayoutInflater().inflate(R.layout.layout_comfir_order_head_view, null);
        footView = getLayoutInflater().inflate(R.layout.layout_comfir_order_foot_view, null);
        ivStore = (ImageView) headView.findViewById(R.id.iv_ConfirmOrderActivity_store);
        tvAddressNull = (TextView) headView.findViewById(R.id.tv_ConfirmOrderActivity_addressNull);
        tvName = (TextView) headView.findViewById(R.id.tv_ConfirmOrderActivity_name);
        tvGender = (TextView) headView.findViewById(R.id.tv_ConfirmOrderActivity_gender);
        tvPhone = (TextView) headView.findViewById(R.id.tv_ConfirmOrderActivity_phone);
        tvAddress = (TextView) headView.findViewById(R.id.tv_ConfirmOrderActivity_address);
        lltAddress = (LinearLayout) headView.findViewById(R.id.llt_ConfirmOrderActivity_address);
        tvStoreName = (TextView) headView.findViewById(R.id.tv_ConfirmOrderActivity_storeName);
        tvDistributionCost = (TextView) footView.findViewById(R.id.tv_ConfirmOrderActivity_distributionCost);
        tvRemarks = (TextView) footView.findViewById(R.id.tv_ConfirmOrderActivity_remarks);
        lltOrderRemarks = (LinearLayout) footView.findViewById(R.id.llt_ConfirmOrderActivity_orderRemarks);
    }

    @Override
    protected void initData() {
        //用户对象
        userBean = new UserBean();
        //获取当前登录用户的登录状态、id和电话
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
        loginStatus = sharedPreferences.getBoolean("login", false);
        if (!loginStatus) { //用户未登录
            Toast.makeText(this, R.string.activity_confirmOrder_please_login, Toast.LENGTH_SHORT).show();
        } else {//用户已登录
            String userId = sharedPreferences.getString(INTENT_USER_ID, "-1");
            String telephone = sharedPreferences.getString("telephone", "");
            userBean.setUserId(userId);
            userBean.setPhoneNumber(telephone);
        }
        // 根据用户id显示默认收货地址
        showDefaultAddress();
        //获取商品详情页传递过来的数据
        getIntentData();
        //设置标题
        tvTitle.setText(R.string.activity_confirmOrder_title);
        //创建订单
        createOrder();
        //获取食物列表数据并显示
        operateListView();
        //商店图片
        ivStore.setImageResource(shopBean.getShopPic());
        //店名
        tvStoreName.setText(shopBean.getShopName());
        //配送费
        tvDistributionCost.setText(shopBean.getShopMoney());
        //待支付
        tvToBePay.setText("¥" + (money + Integer.parseInt(shopBean.getShopMoney())));
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        lltAddress.setOnClickListener(this);
        lltOrderRemarks.setOnClickListener(this);
        btnCommitOrder.setOnClickListener(this);
    }

    /**
     * 获取Intent传递的值
     */
    public void getIntentData() {
        intent = getIntent();
        shopBean = (ShopBean) intent.getSerializableExtra("ShopBeans");
        foodBeanList = new ArrayList<>();
        foodBeanList.clear();
        foodBeanList = (List<FoodBean>) intent.getSerializableExtra("foodBeans");
        money = intent.getIntExtra("CountMoney", 0);
    }

    /**
     * 根据用户id显示默认收货地址
     */
    private void showDefaultAddress() {
        if (userBean != null) {
            addressBean = DataBaseSQLiteUtil.getUserDefaultAddress(Integer.parseInt(userBean.getUserId()));
            if (addressBean.getAddress() != null) { //如果默认地址不为空
                isAddressNull = false;
                tvAddressNull.setVisibility(View.GONE);
                showAddressDetail(addressBean);
            } else {//如果默认地址为空
                List<AddressBean> addressBeanList = DataBaseSQLiteUtil.queryAddressByUserId(Integer.parseInt(userBean.getUserId()));
                if (addressBeanList.size() <= 0) {
                    isAddressNull = true;
                    tvAddressNull.setVisibility(View.VISIBLE);
                } else {
                    isAddressNull = false;
                    tvAddressNull.setVisibility(View.GONE);
                    showAddressDetail(addressBeanList.get(0));
                }
            }
        }
    }

    private void showAddressDetail(AddressBean addressBean) {
        userBean.setAddressId(addressBean.getAddressId());
        tvName.setText(addressBean.getName());
        tvGender.setText(addressBean.getGender());
        tvPhone.setText(addressBean.getPhone());
        tvAddress.setText(addressBean.getAddress());
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;

            case R.id.llt_ConfirmOrderActivity_address: //点击地址栏
                intent = new Intent(ConfirmOrderActivity.this, ReceiveAddressActivity.class);
                startActivityForResult(intent, OREDER_REDDRESS_REQUEST);
                break;

            case R.id.llt_ConfirmOrderActivity_orderRemarks: //点击订单备注栏
                intent = new Intent(ConfirmOrderActivity.this, RemarkOrderActivity.class);
                startActivityForResult(intent, ORDER_REMARK_REQUEST);
                break;

            case R.id.btn_ConfirmOrderActivity_commitOrder: //提交订单
                if (!loginStatus) { //用户未登录
                    intent = new Intent(ConfirmOrderActivity.this, LoginActivity.class);
                } else {//用户已登录
                    if (!isAddressNull) {//地址不为空
                        long orderId = DataBaseSQLiteUtil.addOrder(orderBean, foodBeanList);
                        //提交订单成功
                        if (orderId > 0) {
                            // 同时设置用户的默认送餐地址为当前选中的地址
                            DataBaseSQLiteUtil.setUserDefaultAddress(userBean);
                            DataBaseSQLiteUtil.closeDataBase();
                            ContextUtil.getInstance().setFinishGoodsActivity(true);
                            intent = new Intent(ConfirmOrderActivity.this, OnlinePaymentActivity.class);
                            intent.putExtra(INTENT_ORDER_ID, orderId + "");
                            intent.putExtra("storeName", shopBean.getShopName());
                            intent.putExtra("totalPay", (money + Integer.parseInt(shopBean.getShopMoney())) + "");
                            startActivity(intent);
                            finish();
                        }
                    } else {//地址为空
                        // 提示用户输入地址
                        Toast.makeText(this, R.string.activity_confirmOrder_toast, Toast.LENGTH_SHORT).show();
                    }
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
            } else if (requestCode == OREDER_REDDRESS_REQUEST && resultCode == OREDER_REDDRESS_RESULT) { //选择收货地址,返回选中的地址
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
        //添加头部和尾部
        lvFood.addHeaderView(headView);
        lvFood.addFooterView(footView);
        //添加适配器
        lvFood.setAdapter(new FoodListAdapter(foodBeanList));
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        //创建一个订单对象
        orderBean = new OrderBean();
        orderBean.setShopBean(shopBean); //商店实体类
        orderBean.setOrderNum(OrderUtil.getOrderNumber(userBean.getPhoneNumber()));//订单号
        orderBean.setStatus(OrderState.NOT_PAY + "");//订单状态
        orderBean.setOrderTotalMoney(money + "");//订单总金额
        orderBean.setOrderContent(OrderUtil.createOrderContent(foodBeanList));//订单内容
        orderBean.setShopPhone(shopBean.getShopPhone());
        orderBean.setDiscountMoney("0"); //优惠金额
        orderBean.setDistributingFee(shopBean.getShopMoney());//配送费
        orderBean.setActualPayment((money + Integer.parseInt(shopBean.getShopMoney())) + "");//实付金额
        orderBean.setOrderTime(DateUtil.getCurrentTime()); //下单时间
        orderBean.setAddressId(Integer.parseInt(userBean.getAddressId()));  //送餐地址Id
    }

    /**
     * 适配器
     */
    public class FoodListAdapter extends BaseAdapter {

        private List<FoodBean> list;

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
                holder.tvFoodName = (TextView) convertView.findViewById(R.id.tv_ConfirmOrderActivity_foodName);
                holder.tvFoodNumber = (TextView) convertView.findViewById(R.id.tv_ConfirmOrderActivity_foodNumber);
                holder.tvFoodPrice = (TextView) convertView.findViewById(R.id.tv_ConfirmOrderActivity_foodPrice);
                convertView.setTag(holder);
            } else {
                holder = (FoodViewHolder) convertView.getTag();
            }
            //赋值
            FoodBean foodBean = getItem(position);
            holder.tvFoodName.setText(foodBean.getGoodsName());
            holder.tvFoodNumber.setText(getString(R.string.common_rmb_sign) + foodBean.getGoodsBuynumber());
            holder.tvFoodPrice.setText(getString(R.string.common_rmb_sign) + Integer.valueOf(foodBean.getGoodsMoney()) * foodBean.getGoodsBuynumber());
            return convertView;
        }
    }

    public class FoodViewHolder {
        TextView tvFoodName; //食物名称
        TextView tvFoodNumber; //购买数量
        TextView tvFoodPrice; //食物价格
    }
}
