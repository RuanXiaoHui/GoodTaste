package www.formssi.goodtaste.activity.pay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.EventBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DateUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_ID;
import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_NUM;
import static www.formssi.goodtaste.constant.ConstantConfig.PAY_COUNT_DOWN_TIME;

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
    private Intent intent;
    private long currentTimeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        initView();
        initData();
        initListener();
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
        orderId = intent.getStringExtra(INTENT_ORDER_ID);
        orderNum = intent.getStringExtra(INTENT_ORDER_NUM);

        SharedPreferences sharedPreferences = OnlinePaymentActivity.this.getSharedPreferences("countTime", MODE_PRIVATE);
        long orderTimeMillis = sharedPreferences.getLong("orderTimeMillis", 0);
        Log.i("订单时间 long   ", orderTimeMillis + "");
        //计算时间差
        currentTimeMillis = System.currentTimeMillis();
        long orderMillisUntilFinished = 900*1000 - DateUtil.getTimeChange(orderNum, orderTimeMillis, currentTimeMillis);
        getCountDownTime(orderMillisUntilFinished);
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

    private CountDownTimer timer;


    /**
     * 点击事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back: //返回
                this.finish();
                break;

            case R.id.btn_OnlinePaymentActivity_confirmPayment: //确认支付按钮
                int payOrder = DataBaseSQLiteUtil.payOrder(orderId);
                if (payOrder > 0) {
                    intent = new Intent(OnlinePaymentActivity.this, PaySuccessActivity.class); //支付成功
                    intent.putExtra(INTENT_ORDER_ID, orderId);
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

    /**
     * 获取倒计时
     *
     */
    private void getCountDownTime(long orderMillisUntilFinished) {
        timer = new CountDownTimer(orderMillisUntilFinished, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int minute = (int) (millisUntilFinished / 1000 / 60); //分
                int second = 0;  //秒
                String strTime = null;
                if (minute < 60) {
                    second = (int) (millisUntilFinished / 1000 % 60);
                    strTime = unitFormat(minute) + ":" + unitFormat(second);
                    Log.i("在线支付倒计时", strTime);

                }
                if(TextUtils.isEmpty(strTime)){
                    tvCountDown.setText("订单超时");
                    btnConfirmPayment.setText("支付超时");
                    btnConfirmPayment.setEnabled(false);
                } else {
                    tvCountDown.setText(strTime);
                    btnConfirmPayment.setEnabled(true);
                }

            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    /**
     * 倒计时显示格式
     *
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String result = null;
        if (i >= 0 && i < 10)
            result = "0" + Integer.toString(i);
        else
            result = "" + i;
        return result;
    }

}
