package www.formssi.goodtaste.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;

public class UpdateTelephoneActivity extends AppCompatActivity {

    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_telephone);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvTitle.setText("修改手机号码");
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
