package www.formssi.goodtaste.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

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
        tvOrderBackText = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tvOrderTime = (TextView) findViewById(R.id.tvOrderTime);
        tvOrderPayTime = (TextView) findViewById(R.id.tvOrderPayTime);
        tvOrderDeliveryTime = (TextView) findViewById(R.id.tvOrderDeliveryTime);
        tvOrderArrivalTime = (TextView) findViewById(R.id.tvOrderArrivalTime);
    }

    @Override
    protected void initData() {
        tvOrderBackText.setText("订单跟踪");
        intent = getIntent(); // 通过intent获取订单id
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
        tvOrderTime.setText(orderBean.getOrderTime());
        tvOrderPayTime.setText(orderBean.getPayTime());
        tvOrderDeliveryTime.setText(orderBean.getPayTime());
        tvOrderArrivalTime.setText(orderBean.getPayTime());
    }

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back: // 返回按钮
                finish(); // 结束当前
                break;
        }
    }
}
