package www.formssi.goodtaste.activity.order;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.activity.pay.OnlinePaymentActivity;
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.bean.EventBean;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.ClickUtil;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DateUtil;
import www.formssi.goodtaste.utils.OrderUtil;
import www.formssi.goodtaste.widget.NoScrollListView;

import static www.formssi.goodtaste.constant.ConstantConfig.CALL_PHONE_REQUEST_CODE;
import static www.formssi.goodtaste.utils.ToastUtil.showToast;

/**
 * 订单详情Activity类
 * 说明：
 * 1.根据订单号查询订单详情显示
 * 2.根据订单号查询食品列表
 *
 * @author qq724418408
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvOrderBackText; // 订单状详情
    private TextView tvOrderStatus; // 订单状态，根据状态来显示不同按钮
    private ImageView ivOrderShopImg; // 店铺图像
    private TextView tvOrderShopName; // 店铺名称
    private TextView tvOrderPackFee; // 配送费
    private TextView tvOrderDiscount; // 优惠金额
    private TextView tvOrderActualPay; // 实付金额
    private TextView tvOrderNumber; // 订单号
    private TextView tvOrderTime; // 下单时间
    private TextView tvOrderPayTime; // 支付时间
    private TextView tvOrderArrivalTime; // 到达时间
    private TextView tvOrderAddress; // 送餐地址
    private TextView tvOrderRemarks; // 送餐地址
    private NoScrollListView lvFoodList; // 食品listView
    private ImageView btnBack; // 返回按钮
    private Button btnOK; // 确认按钮：根据状态显示不同文字：去支付、评价、再来一单
    private Button btnCancel; // 取消按钮：取消订单
    private Button btnContactBusiness; // 联系商家按钮：拨打商家电话
    private List<FoodBean> listFoodBean; // 食品列表
    private OrderBean orderBean; // 订单实体
    private FoodListAdapter adapter; // 适配器
    private Intent intent; // 获取上一个intent
    private String rmbSign; // 人民币符号
    private String rmbUnit; // 人民币单位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        EventBus.getDefault().register(this); // 注册EventBus
        initView(); // 初始化控件
        initData(); // 初始化数据
        initListener(); // 初始化监听
    }

    @Override
    protected void initView() {
        lvFoodList = (NoScrollListView) findViewById(R.id.lv_order_food_list);
        ivOrderShopImg = (ImageView) findViewById(R.id.iv_order_shop_image);
        tvOrderBackText = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        tvOrderShopName = (TextView) findViewById(R.id.tv_order_shop_name);
        tvOrderPackFee = (TextView) findViewById(R.id.tv_order_pack_fee);
        tvOrderDiscount = (TextView) findViewById(R.id.tv_order_discount_fee);
        tvOrderActualPay = (TextView) findViewById(R.id.tv_order_actual_pay);
        tvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        tvOrderPayTime = (TextView) findViewById(R.id.tv_order_pay_time);
        tvOrderArrivalTime = (TextView) findViewById(R.id.tv_order_arrival_time);
        tvOrderAddress = (TextView) findViewById(R.id.tv_order_address);
        tvOrderRemarks = (TextView) findViewById(R.id.tv_order_remarks);
        btnBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        btnOK = (Button) findViewById(R.id.btn_order_ok);
        btnCancel = (Button) findViewById(R.id.btn_order_cancel);
        btnContactBusiness = (Button) findViewById(R.id.btn_order_contact_business);
    }

    @Override
    protected void initData() {
        rmbSign = getString(R.string.common_rmb_sign); // 人民币符号
        rmbUnit = getString(R.string.common_rmb_unit); // 人民币单位
        tvOrderBackText.setText(getString(R.string.activity_order_detail_title));
        listFoodBean = new ArrayList<>(); // 食品列表根据订单详情里面的订单号查询出来
        intent = getIntent(); // 通过intent获取订单id
        if (null != intent) {
            String orderId = intent.getStringExtra(ConstantConfig.INTENT_ORDER_ID);
            if (null != orderId && !"".equals(orderId)) {
                orderBean = DataBaseSQLiteUtil.getOrderBeansById(orderId).get(0); // 根据id查询订单详情数据
                setOrderDetail(orderBean); // 展示订单详情信息
            }
            if (null != orderBean) {
                listFoodBean.addAll(orderBean.getFoodBeanList()); // 展示食物列表信息
                adapter = new FoodListAdapter();
                lvFoodList.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void initListener() {
        tvOrderShopName.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnContactBusiness.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_shop_name: // 点击商家名称
                //startActivity(new Intent(this, GoodsDetailActivity.class)); // 跳转到商家店铺
                break;
            case R.id.iv_backTitlebar_back: // 返回按钮
                finish(); // 结束当前
                break;
            case R.id.btn_order_ok: // 根据状态改变按钮处理的业务
                switch (Integer.parseInt(orderBean.getStatus())) {
                    case OrderState.NOT_PAY: // 未支付
                        Intent intent = new Intent(this, OnlinePaymentActivity.class); // 去支付
                        intent.putExtra(ConstantConfig.INTENT_ORDER_ID, orderBean.getOrderId());
                        intent.putExtra(ConstantConfig.INTENT_STORE_NAME, orderBean.getShopName());
                        intent.putExtra(ConstantConfig.INTENT_ACTUAL_PAYMENT, orderBean.getActualPayment());
                        startActivity(intent); // 跳转到支付页面
                        break;
                    case OrderState.NOT_DELIVERY: // 未配送
                        showToast(getString(R.string.toast_order_remind)); // 提醒商家
                        if (ClickUtil.isFastClick(3000)) { // 三秒内重复点击也只执行一次
                            OrderUtil.reminderOrder(orderBean.getOrderId()); // 催单操作
                        }
                        break;
                    case OrderState.DELIVERY_ING: // 配送中，需要确认收货，确认之后生成到达时间
                        showToast(getString(R.string.activity_order_arrival_time_plan)); // 预计送达时间
                        // 选择收货时间
                        orderBean.setArrivalTime(DateUtil.getCurrentTime()); // 送达时间
                        OrderUtil.confirmReceipt(orderBean); // 确认收货
                        break;
                    case OrderState.NOT_COMMENT: // 未评论，跳转到评论页面
                        //showToast(getString(R.string.activity_order_arrival_time)); // 送达时间
                        break;
                    case OrderState.FINISH: // 已完成，跳转到购物车结算页面
                        //showToast(getString(R.string.activity_order_arrival_time)); // 送达时间
                        break;
                }
                break;
            case R.id.btn_order_cancel: // 根据状态改变按钮处理的业务
                break;
            case R.id.btn_order_contact_business: // 联系商家按钮：拨打商家电话
                // 调用系统拨号Action
                if (null != orderBean) {
                    ShopBean shopBean = orderBean.getShopBean(); // 商店实体对象
                    if (null != shopBean) { // 如果数据库查到商店对象
                        String phone = shopBean.getShopPhone(); // 获得商家电话
                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)); // 拨打商家电话的意图
                    } else {
                        String phone = orderBean.getShopPhone(); // 如果获取不到商家信息
                        if (null != phone || "".equals(phone)) { // 就从订单里面获取商家电话，如果有
                            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)); // 拨打商家电话的意图
                        } else { // 如果没有电话
                            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.common_shop_default_phone)));
                        }
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 设置意图新任务标志
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        showToast(getString(R.string.common_call_phone_not_granted));
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
                        return;
                    }
                    startActivity(intent);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenBusMain(EventBean eventBean) { // EventBus接收器，运行在主线程
        initData(); // 更新数据
        switch (eventBean.getAction()){
            case ConstantConfig.REMIND_ORDER:

                break;
        }
    }

    /**
     * 展示订单详情信息
     *
     * @param orderBean
     */
    public void setOrderDetail(OrderBean orderBean) {
        if (null != orderBean) {
            ivOrderShopImg.setImageResource(orderBean.getShopPicture()); // 显示商家图像
            tvOrderShopName.setText(orderBean.getShopName()); // 显示商家名称
            tvOrderNumber.setText(orderBean.getOrderNum()); // 显示订单号
            tvOrderDiscount.setText(rmbSign + orderBean.getDiscountMoney() + rmbUnit); // 显示优惠金额
            tvOrderActualPay.setText(rmbSign + orderBean.getActualPayment() + rmbUnit); // 显示实付金额
            tvOrderPackFee.setText(rmbSign + orderBean.getDistributingFee() + rmbUnit); // 显示配送费
            tvOrderTime.setText(orderBean.getOrderTime()); // 显示下单时间
            tvOrderPayTime.setText(orderBean.getPayTime()); // 显示支付时间，如果已经支付
            Drawable leftDrawable = getResources().getDrawable(orderBean.getShopPicture()); // 商家名称左图标
            Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_right_arrow_normal); // 商家名称右图标
            int w = (int) getResources().getDimension(R.dimen.icon_small_width); // 商家名称左图标宽度
            int h = (int) getResources().getDimension(R.dimen.icon_small_hight); // 商家名称左图标高度
            int padding = (int) getResources().getDimension(R.dimen.common_padding_normal); // 商家名称左图标padding
            leftDrawable.setBounds(0, 0, w, h); // 设置商家名称左图标宽高
            // 设置商家名称右图标宽高
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            tvOrderShopName.setCompoundDrawablePadding(padding); // 设置商家名称左图标padding
            tvOrderShopName.setCompoundDrawables(leftDrawable, null, rightDrawable, null); // 设置商家名称左、右图标
            int addressId = orderBean.getAddressId(); // 获取地址id
            AddressBean bean = DataBaseSQLiteUtil.getAddressById(String.valueOf(addressId)); // 数据库查询地址信息
            tvOrderAddress.setText(bean.toAddressString()); // 显示地址信息
            tvOrderRemarks.setText(orderBean.getRemarks()); // 显示备注信息
            switch (Integer.parseInt(orderBean.getStatus())) { // 根据订单状态分配任务
                case OrderState.NOT_PAY: // 未支付
                    tvOrderStatus.setText(getString(R.string.activity_order_waiting_for_payment)); // 显示支付状态：未支付
                    btnOK.setText(getString(R.string.activity_order_goto_pay)); // 支付按钮
                    tvOrderPayTime.setText(getString(R.string.order_state_not_pay)); // 显示支付时间，如果未支付，显示未支付
                    tvOrderArrivalTime.setText(getString(R.string.order_state_not_pay)); // 显示到达时间，如果未支付
                    break;
                case OrderState.NOT_DELIVERY: // 未配送
                    tvOrderStatus.setText(getString(R.string.order_state_btn_not_delivery)); // 显示订单状态：未配送
                    btnOK.setText(getString(R.string.order_state_btn_not_delivery)); // 催单按钮
                    tvOrderArrivalTime.setText(getString(R.string.order_state_not_delivery)); // 显示到达时间，如果未配送，显示未配送
                    break;
                case OrderState.DELIVERY_ING: // 正在配送，等待确认收货，确认之后生成到达时间
                    tvOrderStatus.setText(getString(R.string.order_state_delivery_ing)); // 显示订单状态：已配送
                    btnOK.setText(getString(R.string.activity_order_waiting_for_confirm_receipt)); // 查看进度
                    tvOrderArrivalTime.setText(getString(R.string.activity_order_arrival_time_plan)); // 显示到达时间，如果未到达，显示预计送达时间
                    break;
                case OrderState.NOT_COMMENT: // 未评论
                    tvOrderStatus.setText(getString(R.string.activity_order_waiting_for_comment)); // 显示订单状态：等待评价
                    btnOK.setText(R.string.activity_order_goto_comment); // 去评价
                    tvOrderArrivalTime.setText(orderBean.getArrivalTime()); // 显示到达时间
                    break;
                case OrderState.FINISH: // 已完成
                    tvOrderStatus.setText(getString(R.string.activity_order_complete)); // 显示订单状态：已完成
                    btnOK.setText(getString(R.string.activity_order_again)); // 再来一单
                    break;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 根据用户授权情况判断哪些权限已经获得
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 用户授予了该权限
            showToast(getString(R.string.common_user_already_granted));
        } else { // 用户拒绝授予该权限
            showToast(getString(R.string.common_user_refuse_granted));
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            FoodViewHolder holder;
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
            holder.tvFoodCount.setText(getString(R.string.common_multiple_sign) + bean.getGoodsBuynumber());
            holder.tvFoodPrice.setText(rmbSign + OrderUtil.getFoodTotalMoney(bean) + rmbUnit);
            return convertView;
        }
    }

    class FoodViewHolder {
        TextView tvFoodName; // 食品名称
        TextView tvFoodCount; // 食品数量
        TextView tvFoodPrice; // 食品单价
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // 反注册EventBus
    }
}
