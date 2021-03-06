package www.formssi.goodtaste.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.activity.order.OrderDetailActivity;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_ID;

/**
 * 支付成功页面
 * Created by sn on 2017/3/20.
 */

public class PaySuccessActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;// 返回
    private TextView tvTitle; //标题
    private Button btnPaySuccess; //确定
    private String orderId;//订单id
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        initView();     //初始化控件
        initData();     //初始化数据
        initListener(); //初始化监听事件
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        btnPaySuccess = (Button) findViewById(R.id.btn_paySuccess);
    }

    @Override
    protected void initData() {
        //设置标题
        tvTitle.setText(R.string.activity_paySuccess_title);
        //获取订单Id
        intent = getIntent();
        orderId = intent.getStringExtra(INTENT_ORDER_ID);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btnPaySuccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:  //返回
                this.finish();
                break;
            case R.id.btn_paySuccess:  //确定按钮
                Intent intent = new Intent(PaySuccessActivity.this, OrderDetailActivity.class);
                intent.putExtra(INTENT_ORDER_ID, orderId); //订单id
                startActivity(intent);
                this.finish();
                break;
            default:
                break;
        }
    }
}
