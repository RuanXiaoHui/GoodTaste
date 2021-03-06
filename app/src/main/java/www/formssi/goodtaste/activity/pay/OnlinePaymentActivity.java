package www.formssi.goodtaste.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DateUtil;
import www.formssi.goodtaste.utils.OrderUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_ID;
import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_NUM;
import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_TIME_MILLIS;

/**
 * 在线支付页面
 * Created by sn on 2017/3/20.
 */

public class OnlinePaymentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;// 返回
    private TextView tvTitle; //标题
    private TextView tvCountDown; //支付倒计时
    private TextView tvStoreName;//店名
    private TextView tvPrice;//总金额
    private Button btnConfirmPayment; //确认支付
    private Button btnCancelPayment; //取消支付
    private String orderId; //订单id
    private String orderNum; //订单号
    private long orderTimeMillis; //订单提交时间
    private Intent intent;
    private long currentTimeMillis;  //倒计时处理对象
    private CountDownTimer countDownTime;  //倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        initView();      //初始化控件
        initData();      //初始化数据
        initListener();  //初始化监听事件
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tvCountDown = (TextView) findViewById(R.id.tv_OnlinePaymentActivity_countDown);
        tvStoreName = (TextView) findViewById(R.id.tv_OnlinePaymentActivity_storeName);
        tvPrice = (TextView) findViewById(R.id.tv_OnlinePaymentActivity_price);
        btnConfirmPayment = (Button) findViewById(R.id.btn_OnlinePaymentActivity_confirmPayment);
        btnCancelPayment = (Button) findViewById(R.id.btn_OnlinePaymentActivity_cancelPayment);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.activity_onlinePayment_title);
        //获取上一个页面传过来的订单id、订单号
        intent = getIntent();
        orderId = intent.getStringExtra(INTENT_ORDER_ID);   //订单id
        orderNum = intent.getStringExtra(INTENT_ORDER_NUM); //订单号
        orderTimeMillis = intent.getLongExtra(INTENT_ORDER_TIME_MILLIS, 0); //创建订单时间
        //计算时间差
        currentTimeMillis = System.currentTimeMillis();
        //获取订单支付剩余时间
        long orderMillisUntilFinished = DateUtil.getOrderMillisUntilFinished(orderTimeMillis, currentTimeMillis);
        //获取CountdownTimer对象，并启动倒计时
        countDownTime = OrderUtil.setCountDownTime(orderMillisUntilFinished, tvCountDown, null,
                getString(R.string.activity_order_state_time_out), btnConfirmPayment,
                getString(R.string.activity_order_time_out));
        countDownTime.start();
        //店名
        String storeName = intent.getStringExtra("storeName");
        tvStoreName.setText(storeName);
        //订单金额
        String totalPay = intent.getStringExtra("totalPay");
        tvPrice.setText(getString(R.string.common_rmb_sign) + totalPay);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btnConfirmPayment.setOnClickListener(this);
        btnCancelPayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back: //返回
                this.finish();
                break;
            case R.id.btn_OnlinePaymentActivity_confirmPayment: //确认支付按钮
                int payOrder = DataBaseSQLiteUtil.payOrder(orderId);
                if (payOrder > 0) {
                    if (countDownTime != null) {
                        countDownTime.cancel();
                    }
                    intent = new Intent(OnlinePaymentActivity.this, PaySuccessActivity.class); //支付成功
                    intent.putExtra(INTENT_ORDER_ID, orderId); //传递订单号
                } else {
                    intent = new Intent(OnlinePaymentActivity.this, PayFailureActivity.class); //支付失败
                }
                startActivity(intent);
                this.finish();
                break;
            case R.id.btn_OnlinePaymentActivity_cancelPayment: //取消支付按钮
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTime != null) {
            countDownTime.cancel();
        }
    }
}
