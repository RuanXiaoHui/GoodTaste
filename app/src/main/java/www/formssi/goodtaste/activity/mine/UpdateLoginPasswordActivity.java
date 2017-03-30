package www.formssi.goodtaste.activity.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ToastUtil;

public class UpdateLoginPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回
    private Button btnUpdateLoginPassword; //修改登录密码
    private EditText etLoginPassword; //旧密码
    private EditText etUpdateLoginPassword; //新密码
    private UserBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_login_password);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        etUpdateLoginPassword = (EditText) findViewById(R.id.et_update_login_password);
        btnUpdateLoginPassword = (Button) findViewById(R.id.btn_update_login_password);
        tvTitle.setText(R.string.update_login_pwd);
    }

    @Override
    protected void initData() {
        user = (UserBean) getIntent().getSerializableExtra(ConstantConfig.USER);
    }

    @Override
    protected void initListener() {
        ivReturn.setOnClickListener(this);
        btnUpdateLoginPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.btn_update_login_password:
                updateLoginPwd();
                break;
        }
    }

    private void updateLoginPwd() {
        String oldPwd = etLoginPassword.getText().toString().trim();
        String newPwd = etUpdateLoginPassword.getText().toString().trim();
        if (TextUtils.equals(oldPwd, user.getLoginPassword())) {
            if (TextUtils.isEmpty(newPwd)) {
                ToastUtil.showToast(getString(R.string.toast_enter_new_pwd));
                return;
            }
            boolean updateResult = DataBaseSQLiteUtil.updateLoginPassword(user.getPhoneNumber(), newPwd);
            user.setLoginPassword(newPwd);
            if (updateResult) {
                sendBroadcast(new Intent(MineFragment.MY_ACTION));
                setResult(RESULT_OK, new Intent().putExtra(ConstantConfig.RESULT, user));
                finish();
            }
        } else {
            ToastUtil.showToast(getString(R.string.toast_old_pay_error));
        }
    }
}
