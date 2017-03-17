package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;

/**
 * 订单备注页面
 * Created by john on 2017/3/16.
 */

public class RemarkOrderActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivBack;//返回
    private TextView tvTitle; //标题
    private EditText etRemarkOrder; //填写备注
    private Button btnOk; //确定按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_order);
        bindViiews();
        tvTitle.setText(R.string.activity_remarkOrder_title);
    }

    private void bindViiews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_Title);
        etRemarkOrder = (EditText) findViewById(R.id.et_RemarkOrderActivity_remark);
        btnOk = (Button) findViewById(R.id.btn_RemarkOrderActivity_ok);

        ivBack.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_backTitlebar_back:
                finish();
                break;

            case R.id.btn_RemarkOrderActivity_ok:
                Intent intent = new Intent();
                String remarks = etRemarkOrder.getText().toString();
                intent.putExtra("remarks",remarks);
                setResult(RESULT_OK,intent);
                finish();
                break;

            default:
                break;
        }
    }
}
