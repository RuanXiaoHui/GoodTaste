package www.formssi.goodtaste.activity;

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
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        etUpdateLoginPassword = (EditText) findViewById(R.id.et_update_login_password);
        btnUpdateLoginPassword = (Button) findViewById(R.id.btn_update_login_password);
        tvTitle.setText("修改登录密码");
    }

    @Override
    protected void initData() {
        user = (UserBean) getIntent().getSerializableExtra("user");
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
        String oldPwd = etLoginPassword.getText().toString();
        String newPwd = etUpdateLoginPassword.getText().toString();
        if (TextUtils.equals(oldPwd, user.getLoginPassword())) {
            if (TextUtils.isEmpty(newPwd)) {
                ToastUtil.showToast("请输入新密码！");
                return;
            }
            boolean updateResult = DataBaseSQLiteUtil.updateLoginPassword(user.getPhoneNumber(), newPwd);
            if (updateResult) {
                sendBroadcast(new Intent(MineFragment.MY_ACTION));
                setResult(RESULT_OK, new Intent().putExtra("result", newPwd));
                finish();
            }
        } else {
            ToastUtil.showToast("旧密码不正确");
        }
    }
}
