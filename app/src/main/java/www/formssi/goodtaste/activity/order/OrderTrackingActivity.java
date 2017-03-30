package www.formssi.goodtaste.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DateUtil;
import www.formssi.goodtaste.utils.OrderUtil;

/**
 * 订单跟踪Activity类
 * 说明：
 * 根据订单号查询订单状态
 *
 * @author qq724418408
 */
public class OrderTrackingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack; // 返回按钮
    private TextView tvOrderBackText; // 订单跟踪
    private TextView tvOrderTime; // 下单时间
    private TextView tvOrderPayTime; // 支付时间
    private TextView tvOrderDeliveryTime; // 送餐开始时间
    private TextView tvOrderArrivalTime; // 预计到达时间
    private Button btnOrderOk; // 确认按钮
    private OrderBean orderBean; // 订单实体
    private Intent intent; // 获取上一个intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        initView(); // 初始化控件
        initData(); // 初始化数据
        initListener(); // 初始化监听
    }

    @Override
    protected void initView() {
        btnBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        btnOrderOk = (Button) findViewById(R.id.btnOrderOk);
        tvOrderBackText = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tvOrderTime = (TextView) findViewById(R.id.tvOrderTime);
        tvOrderPayTime = (TextView) findViewById(R.id.tvOrderPayTime);
        tvOrderDeliveryTime = (TextView) findViewById(R.id.tvOrderDeliveryTime);
        tvOrderArrivalTime = (TextView) findViewById(R.id.tvOrderArrivalTime);
    }

    @Override
    protected void initData() {
        tvOrderBackText.setText(R.string.activity_order_tracking);
        intent = getIntent(); // 通过intent获取订单id号
        if (null != intent) {
            String orderId = intent.getStringExtra(ConstantConfig.INTENT_ORDER_ID);
            if (null != orderId && !"".equals(orderId)) {
                orderBean = DataBaseSQLiteUtil.getOrderBeansById(orderId).get(0); // 根据id查询订单详情数据
                setOrderTracking(orderBean); // 展示订单状态情况
            }
        }
    }

    /**
     * 展示订单状态
     *
     * @param orderBean
     */
    public void setOrderTracking(OrderBean orderBean) {
        tvOrderTime.setText(orderBean.getOrderTime()); // 显示下单时间
        tvOrderPayTime.setText(orderBean.getPayTime()); // 显示支付时间
        tvOrderDeliveryTime.setText(orderBean.getPayTime()); // 显示开始送餐时间
        tvOrderArrivalTime.setText(orderBean.getPayTime()); // 显示预计到达时间
    }

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(this);
        btnOrderOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back: // 返回按钮
                finish(); // 结束当前
                break;
            case R.id.btnOrderOk: // 确认收货按钮
                orderBean.setArrivalTime(DateUtil.getCurrentTime()); // 送达时间
                OrderUtil.confirmReceipt(orderBean); // 确认收货
                tvOrderArrivalTime.setText(orderBean.getArrivalTime()); // 显示到达时间
                btnOrderOk.setVisibility(View.GONE);
                break;
        }
    }
}
