package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ToastUtil;

public class UpdatePayPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private EditText etPayPwd;
    private EditText etUpdatePwd;
    private UserBean mUserBean;

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
        tvTitle.setText("修改支付密码");
    }

    @Override
    protected void initData() {
        mUserBean = (UserBean) getIntent().getSerializableExtra("user");
        if (TextUtils.isEmpty(mUserBean.getPayPassword())) {
            etPayPwd.setVisibility(View.GONE);
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
        String oldPayPwd = etPayPwd.getText().toString();
        String newPayPwd = etUpdatePwd.getText().toString();
        if (!TextUtils.isEmpty(mUserBean.getPayPassword())) {
            if (TextUtils.isEmpty(oldPayPwd)) {
                ToastUtil.showToast("请输入旧密码！");
                return;
            }
            if (!TextUtils.equals(oldPayPwd, mUserBean.getPayPassword())) {
                ToastUtil.showToast("旧密码不正确！");
                return;
            }
            update(newPayPwd);
        } else {
            update(newPayPwd);
        }
    }

    private void update(String newPayPwd) {
        if (TextUtils.isEmpty(newPayPwd)) {
            ToastUtil.showToast("请输入新密码");
            return;
        }
        boolean result = DataBaseSQLiteUtil.updatePayPassword(mUserBean.getPhoneNumber(), newPayPwd);
        if (result) {
            sendBroadcast(new Intent(MineFragment.MY_ACTION));
            setResult(RESULT_OK, new Intent().putExtra("result", newPayPwd));
            finish();
        }
    }
}
