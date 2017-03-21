package www.formssi.goodtaste.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;

public class UpdatePayPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_update_pay_password);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle.setText("修改支付密码");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ivReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
        }
    }
}
