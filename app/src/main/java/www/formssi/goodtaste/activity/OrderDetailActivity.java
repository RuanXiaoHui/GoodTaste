package www.formssi.goodtaste.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.widget.NoScrollListView;

import static www.formssi.goodtaste.constant.ConstantConfig.CALL_PHONE_REQUEST_CODE;

/**
 * 订单详情Activity类
 * 说明：
 * 1.根据订单号查询订单详情显示
 * 2.根据订单号查询食品列表
 *
 * @author qq724418408
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvOrderStatus; // 订单状态，根据状态来显示不同按钮
    private ImageView ivOrderShopImg; // 店铺图像
    private TextView tvOrderShopName; // 店铺名称
    private TextView tvOrderPackFee; // 配送费
    private TextView tvOrderDiscount; // 优惠金额
    private TextView tvOrderActualPay; // 实付金额
    private TextView tvOrderNumber; // 订单号
    private TextView tvOrderTime; // 下单时间
    private TextView tvOrderPayTime; // 支付时间
    private TextView tvOrderAddress; // 送餐地址
    private NoScrollListView lvFoodList; // 食品listView
    private Button btnBack; // 返回按钮
    private Button btnOK; // 确认按钮：根据状态显示不同文字：去支付、评价、再来一单
    private Button btnCancel; // 取消按钮：取消订单
    private Button btnContactBusiness; // 联系商家按钮：拨打商家电话
    private List<FoodBean> listFoodBean; // 食品列表
    private OrderBean orderBean; // 订单实体
    private FoodListAdapter adapter; // 适配器
    private Intent intent; // 获取上一个intent
    private Toast toast; // 吐司
    private String rmbSign; // 人民币符号
    private String rmbUnit; // 人民币单位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        lvFoodList = (NoScrollListView) findViewById(R.id.lv_order_food_list);
        ivOrderShopImg = (ImageView) findViewById(R.id.iv_order_shop_image);
        tvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        tvOrderShopName = (TextView) findViewById(R.id.tv_order_shop_name);
        tvOrderPackFee = (TextView) findViewById(R.id.tv_order_pack_fee);
        tvOrderDiscount = (TextView) findViewById(R.id.tv_order_discount_fee);
        tvOrderActualPay = (TextView) findViewById(R.id.tv_order_actual_pay);
        tvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        tvOrderPayTime = (TextView) findViewById(R.id.tv_order_pay_time);
        tvOrderAddress = (TextView) findViewById(R.id.tv_order_address);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnOK = (Button) findViewById(R.id.btn_order_ok);
        btnCancel = (Button) findViewById(R.id.btn_order_cancel);
        btnContactBusiness = (Button) findViewById(R.id.btn_order_contact_business);
    }

    @Override
    protected void initData() {
        rmbSign = getString(R.string.common_rmb_sign); // 人民币符号
        rmbUnit = getString(R.string.common_rmb_unit); // 人民币单位
        listFoodBean = new ArrayList<>(); // 食品列表根据订单详情里面的订单号查询出来
        intent = getIntent(); // 通过intent获取订单id
        if (null != intent) {
            String orderId = intent.getStringExtra(ConstantConfig.INTENT_ORDER_ID);
            if (null != orderId && !"".equals(orderId)) {
                DataBaseSQLiteUtil.openDataBase();
                orderBean = DataBaseSQLiteUtil.getOrderBeansById(orderId).get(0); // 根据id查询订单详情数据
                DataBaseSQLiteUtil.closeDataBase();
                setOrderDetail(orderBean); // 展示订单详情信息
            }
            if (null != orderBean) {
                listFoodBean.addAll(orderBean.getFoodBeanList());
                adapter = new FoodListAdapter();
                lvFoodList.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnContactBusiness.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: // 返回按钮
                finish();
                break;
            case R.id.btn_order_ok: // 根据状态改变按钮处理的业务
                switch (Integer.parseInt(orderBean.getStatus())) {
                    case OrderState.NOT_PAY: // 未支付
                       // 去支付
                        Intent intent = new Intent(this,OnlinePaymentActivity.class);
                        intent.putExtra(ConstantConfig.INTENT_ORDER_ID, orderBean.getOrderId());
                        intent.putExtra(ConstantConfig.INTENT_STORE_NAME, orderBean.getShopName());
                        intent.putExtra(ConstantConfig.INTENT_ACTUAL_PAYMENT, orderBean.getActualPayment());
                        startActivity(intent);
                        break;
                    case OrderState.NOT_DELIVERY: // 未配送
                        showToast(getString(R.string.toast_order_remind));
                        break;
                }
                break;
            case R.id.btn_order_cancel: // 根据状态改变按钮处理的业务
                break;
            case R.id.btn_order_contact_business: // 联系商家按钮：拨打商家电话
                // 调用系统拨号Action
                if (null != orderBean) {
                    ShopBean shopBean = orderBean.getShopBean();
                    if (null != shopBean) {
                        String phone = shopBean.getShopPhone();
                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    } else {
                        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.common_shop_default_phone)));
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    /**
     * 吐司
     *
     * @param tip
     */
    public void showToast(String tip) {
        if (toast == null) {
            toast = Toast.makeText(this, tip, Toast.LENGTH_SHORT);
        } else {
            toast.setText(tip);
        }
        toast.show();
    }

    /**
     * 展示订单详情信息
     *
     * @param orderBean
     */
    public void setOrderDetail(OrderBean orderBean) {
        if (null != orderBean) {
            ivOrderShopImg.setImageResource(orderBean.getShopPicture());
            tvOrderShopName.setText(orderBean.getShopName());
            tvOrderNumber.setText(orderBean.getOrderNum());
            tvOrderDiscount.setText(rmbSign + orderBean.getDiscountMoney() + rmbUnit);
            tvOrderActualPay.setText(rmbSign + orderBean.getActualPayment() + rmbUnit);
            tvOrderPackFee.setText(rmbSign + orderBean.getDistributingFee() + rmbUnit);
            tvOrderTime.setText(orderBean.getOrderTime());
            tvOrderPayTime.setText(orderBean.getPayTime());
            int addressId = orderBean.getAddressId();
            DataBaseSQLiteUtil.openDataBase();
            AddressBean bean = DataBaseSQLiteUtil.getAddressById(String.valueOf(addressId));
            DataBaseSQLiteUtil.closeDataBase();
            tvOrderAddress.setText(bean.toAddressString());
            switch (Integer.parseInt(orderBean.getStatus())) {
                case OrderState.NOT_PAY: // 未支付
                    tvOrderStatus.setText(getString(R.string.order_state_notpay));
                    btnOK.setText(getString(R.string.activity_order_goto_pay));
                    break;
                case OrderState.NOT_DELIVERY: // 未配送
                    tvOrderStatus.setText(getString(R.string.order_state_btn_notdelivery));
                    btnOK.setText(getString(R.string.order_state_btn_notdelivery));
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
            holder.tvFoodCount.setText(getString(R.string.common_multiple_sign) + bean.getGoodsBuynumber());
            holder.tvFoodPrice.setText(rmbSign + (Integer.parseInt(bean.getGoodsMoney()) *
                    bean.getGoodsBuynumber()) + rmbUnit);
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
    }
}
