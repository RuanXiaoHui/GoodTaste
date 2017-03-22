package www.formssi.goodtaste.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ToastUtil;

public class SettingSuggestionActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivReturn;
    private EditText etSuggestion;
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
        etSuggestion = (EditText) findViewById(R.id.et_suggestion_submit);
        tvTitle.setText(R.string.activity_suggestion_title);
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

    private void commit() {
        String contents = etSuggestion.getText().toString();
        if (TextUtils.isEmpty(contents)) {
            ToastUtil.showToast(getString(R.string.toast_suggestion_contents));
            return;
        }
        String userId = mUser.getUserId();
        DataBaseSQLiteUtil.feedback(userId, contents);
        ToastUtil.showToast(getString(R.string.toast_suggestion_commit));
        finish();
    }
}
