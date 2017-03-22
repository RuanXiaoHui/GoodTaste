package www.formssi.goodtaste.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_ORDER_ID;

/**
 * 支付失败页面
 * Created by john on 2017/3/20.
 */

public class PayFailureActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;// 返回
    private TextView tvTitle; //标题
    private Button btnPayFailure; //确定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_failure);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        btnPayFailure = (Button) findViewById(R.id.btn_payFailure);
    }

    @Override
    protected void initData() {
        //设置标题
        tvTitle.setText(R.string.activity_payFailure_title);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btnPayFailure.setOnClickListener(this);
    }

    /**
     * 点击事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:  //返回
                this.finish();
                break;

            case R.id.btn_paySuccess:  //确定按钮
                this.finish();
                break;

            default:
                break;
        }
    }
}
