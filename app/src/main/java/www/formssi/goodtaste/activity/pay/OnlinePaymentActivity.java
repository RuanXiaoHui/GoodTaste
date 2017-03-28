package www.formssi.goodtaste.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_ID;

/**
 * 在线支付页面
 * Created by sn on 2017/3/20.
 */

public class OnlinePaymentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;// 返回
    private TextView tvTitle; //标题
    private TextView tv_CountDown; //支付倒计时
    private TextView tvStoreName;//店名
    private TextView tvPrice;//总金额
    private Button btnConfirmPayment; //确认支付
    private Button btnCancelPayment; //取消支付
    private String orderId; //订单id
    private Intent intent;

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//                int time = (int) msg.obj;
//                int minute = 0; //分
//                int second = 0;  //秒
//                String strTime = null;
//                if (time > 0) {
//                    minute = time / 60;
//                    if (minute < 60) {
//                        second = time % 60;
//                        strTime = unitFormat(minute) + ":" + unitFormat(second);
//                    }
//                    tv_CountDown.setText(strTime);
//                }
//                if (time == 1) {
//                    btnConfirmPayment.setText("支付超时");
//                    btnCancelPayment.setEnabled(false);
//                } else if (time > 1) {
//                    btnCancelPayment.setEnabled(true);
//                }
//            }
//        }
//    };

    public static String unitFormat(int i) {
        String result = null;
        if (i >= 0 && i < 10)
            result = "0" + Integer.toString(i);
        else
            result = "" + i;
        return result;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tv_CountDown = (TextView) findViewById(R.id.tv_OnlinePaymentActivity_countDown);
        tvStoreName = (TextView) findViewById(R.id.tv_OnlinePaymentActivity_storeName);
        tvPrice = (TextView) findViewById(R.id.tv_OnlinePaymentActivity_price);
        btnConfirmPayment = (Button) findViewById(R.id.btn_OnlinePaymentActivity_confirmPayment);
        btnCancelPayment = (Button) findViewById(R.id.btn_OnlinePaymentActivity_cancelPayment);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.activity_onlinePayment_title);
        intent = getIntent();
        //订单id
        orderId = intent.getStringExtra(INTENT_ORDER_ID);
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

    private static final String TAG = "OnlinePaymentActivity";

    /**
     * EventBus处理事件
     *
     * @param time
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMain(Integer time) {
        Log.e(TAG, "onEventBusMain: " + time);
        int minute = 0; //分
        int second = 0;  //秒
        String strTime = null;
        if (time >= 0) {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                strTime = unitFormat(minute) + ":" + unitFormat(second);
                if (strTime.equals("00:00")) {
                    btnConfirmPayment.setText("支付超时");
                    btnConfirmPayment.setEnabled(false);
                }else {
                    btnConfirmPayment.setEnabled(true);
                }
            }
            tv_CountDown.setText(strTime);
        }
    }

}
