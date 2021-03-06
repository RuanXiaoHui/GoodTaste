package www.formssi.goodtaste.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ToastUtil;

public class UpdatePayPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private EditText etPayPwd;
    private EditText etUpdatePwd;
    private UserBean mUserBean;
    private LinearLayout llPayPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pay_password);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        etPayPwd = (EditText) findViewById(R.id.et_pay_password);
        etUpdatePwd = (EditText) findViewById(R.id.et_update_pay_password);
        llPayPassword = (LinearLayout) findViewById(R.id.ll_pay_password);
        tvTitle.setText(R.string.update_pay_pwd);
    }

    @Override
    protected void initData() {
        mUserBean = (UserBean) getIntent().getSerializableExtra(ConstantConfig.USER);
        if (TextUtils.isEmpty(mUserBean.getPayPassword())) {
            llPayPassword.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_backTitlebar_back).setOnClickListener(this);
        findViewById(R.id.btn_update_pay_password).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.btn_update_pay_password:
                updatePayPwd();
                break;
        }
    }

    private void updatePayPwd() {
        String oldPayPwd = etPayPwd.getText().toString().trim();
        String newPayPwd = etUpdatePwd.getText().toString().trim();
        if (!TextUtils.isEmpty(mUserBean.getPayPassword())) {
            if (TextUtils.isEmpty(oldPayPwd)) {
                ToastUtil.showToast(getString(R.string.toast_enter_old_pwd));
                return;
            }
            if (!TextUtils.equals(oldPayPwd, mUserBean.getPayPassword())) {
                ToastUtil.showToast(getString(R.string.toast_old_pay_error));
                return;
            }
            update(newPayPwd);
        } else {
            update(newPayPwd);
        }
    }

    private void update(String newPayPwd) {
        if (TextUtils.isEmpty(newPayPwd)) {
            ToastUtil.showToast(getString(R.string.toast_enter_new_pwd));
            return;
        }
        boolean result = DataBaseSQLiteUtil.updatePayPassword(mUserBean.getPhoneNumber(), newPayPwd);
        mUserBean.setPayPassword(newPayPwd);
        if (result) {
            sendBroadcast(new Intent(MineFragment.MY_ACTION));
            setResult(RESULT_OK, new Intent().putExtra(ConstantConfig.RESULT, mUserBean));
            finish();
        }
    }
}
