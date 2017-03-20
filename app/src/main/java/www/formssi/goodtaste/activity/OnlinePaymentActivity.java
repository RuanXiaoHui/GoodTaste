package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;

/**
 * 在线支付页面
 * Created by john on 2017/3/20.
 */

public class OnlinePaymentActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivBack;// 返回
    private TextView tvTitle; //标题
    private TextView tvStoreName;//店名
    private TextView tvPrice;//店名
    private Button btnComfirmPayment; //确认支付
    private Button btnCanclePayment; //取消支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        bindViews();
        tvTitle.setText(R.string.activity_onlinePayment_title);
    }

    /**
     * 初始化、绑定控件
     */
    private void bindViews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvStoreName = (TextView) findViewById(R.id.tv_OnlinePayment_storeName);
        tvPrice = (TextView) findViewById(R.id.tv_OnlinePayment_price);
        btnComfirmPayment = (Button) findViewById(R.id.btn_OnlinePayment_comfirmPayment);
        btnCanclePayment = (Button) findViewById(R.id.btn_OnlinePayment_canclePayment);

        ivBack.setOnClickListener(this);
        btnComfirmPayment.setOnClickListener(this);
        btnCanclePayment.setOnClickListener(this);
    }

    /**
     * 点击事件监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_backTitlebar_back: //返回
                this.finish();
                break;

            case R.id.btn_OnlinePayment_comfirmPayment: //确认支付
//                if(){
                    intent = new Intent(OnlinePaymentActivity.this,PaySuccessActivity.class); //支付成功
//                }else if(){
//                    intent = new Intent(OnlinePaymentActivity.this,PayFailureActivity.class); //支付失败
//                }
                startActivity(intent);
                break;

            case R.id.btn_OnlinePayment_canclePayment: //取消支付
                this.finish();
                break;

            default:
                break;
        }

    }
}
