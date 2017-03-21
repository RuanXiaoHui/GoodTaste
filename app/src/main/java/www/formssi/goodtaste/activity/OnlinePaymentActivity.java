package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_ID;

/**
 * 在线支付页面
 * Created by john on 2017/3/20.
 */

public class OnlinePaymentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;// 返回
    private TextView tvTitle; //标题
    private TextView tvStoreName;//店名
    private TextView tvPrice;//总金额
    private Button btnConfirmPayment; //确认支付
    private Button btnCancelPayment; //取消支付
    private Intent intent;
    private String orderId; //订单id

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
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvStoreName = (TextView) findViewById(R.id.tv_OnlinePayment_storeName);
        tvPrice = (TextView) findViewById(R.id.tv_OnlinePayment_price);
        btnConfirmPayment = (Button) findViewById(R.id.btn_OnlinePayment_confirmPayment);
        btnCancelPayment = (Button) findViewById(R.id.btn_OnlinePayment_cancelPayment);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.activity_onlinePayment_title);
        intent = getIntent();
        //订单id
        orderId = intent.getStringExtra("orderId");
        //店名
        String storeName = intent.getStringExtra("storeName");
        tvStoreName.setText(storeName);
        //订单金额
        String totalPay = intent.getStringExtra("totalPay");
        tvPrice.setText("¥" + totalPay);
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

            case R.id.btn_OnlinePayment_confirmPayment: //确认支付按钮
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

            case R.id.btn_OnlinePayment_cancelPayment: //取消支付按钮
                this.finish();
                break;

            default:
                break;
        }
    }
}
