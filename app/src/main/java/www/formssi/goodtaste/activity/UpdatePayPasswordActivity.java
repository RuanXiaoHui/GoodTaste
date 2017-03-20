package www.formssi.goodtaste.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;

public class UpdatePayPasswordActivity extends AppCompatActivity {

    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pay_password);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvTitle.setText("修改支付密码");
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
