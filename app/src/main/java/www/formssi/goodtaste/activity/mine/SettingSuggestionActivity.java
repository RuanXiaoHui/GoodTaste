package www.formssi.goodtaste.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ToastUtil;

public class SettingSuggestionActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivReturn;
    private EditText etSuggsetion;
    private UserBean mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_suggestion);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        etSuggsetion = (EditText) findViewById(R.id.et_suggestion_submit);
        tvTitle.setText("意见反馈");
    }

    @Override
    protected void initData() {
        mUser = (UserBean) getIntent().getSerializableExtra("user");
    }

    @Override
    protected void initListener() {
        ivReturn.setOnClickListener(this);
        findViewById(R.id.btn_suggestion_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.btn_suggestion_submit:
                commit();
                break;
        }
    }

    private static final String TAG = "SettingSuggestionActivi";
    private void commit() {
        String contents = etSuggsetion.getText().toString();
        if (TextUtils.isEmpty(contents)) {
            ToastUtil.showToast("请输入反馈内容");
            return;
        }
        DataBaseSQLiteUtil.feedback(mUser.getUserId(),contents);
        ToastUtil.showToast("已收到反馈");
        finish();
    }
}
