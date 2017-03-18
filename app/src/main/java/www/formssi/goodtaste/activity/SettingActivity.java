package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.fragment.MineFragment;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        tvTitle.setText(R.string.fragment_personal_setting);

    }

    private void initView() {

//        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_Title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
        }
    }
}
