package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.fragment.MineFragment;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回
    private LinearLayout llService; //客服電話
    private LinearLayout llSuggestion; //意見反饋
    private LinearLayout llCheck; //檢查更新
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        tvTitle.setText(R.string.fragment_personal_setting);

    }

    private void initView() {

        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        llService = (LinearLayout) findViewById(R.id.ll_setting_servicetel);
        llSuggestion = (LinearLayout) findViewById(R.id.ll_setting_suggestion);
        llCheck = (LinearLayout) findViewById(R.id.ll_setting_check);
        btnExit = (Button) findViewById(R.id.btn_exit);

        ivReturn.setOnClickListener(this);
        llService.setOnClickListener(this);
        llSuggestion.setOnClickListener(this);
        llCheck.setOnClickListener(this);
        btnExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.ll_setting_servicetel:
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "10086");
                intent1.setData(data);
                startActivity(intent1);
                break;
            case R.id.ll_setting_suggestion:
                Intent intent2 = new Intent(SettingActivity.this, SettingSuggestionActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_setting_check:
                break;
            case R.id.btn_exit:
                SharedPreferences sharedPreferences = getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login",false);
                editor.commit();
                Intent intent = new Intent(MineFragment.MY_ACTION)
                        .putExtra("login", false)
                        .putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_LOGIN_STATE);
                sendBroadcast(intent);
                finish();
                break;
        }
    }
}
