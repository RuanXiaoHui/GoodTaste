package www.formssi.goodtaste.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ToastUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_USER_ID;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Context mContext;
    private ImageView ivReturn; //返回
    private TextView tvTitle; //标题
    private Button btnLogin; //登录
    private EditText etTelephone; //
    private EditText etLoginPassword; //登录密码
    private SharedPreferences mContextSharedPreferences;
    private TextView mTvRegister;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        etTelephone = (EditText) findViewById(R.id.et_login_telephone);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        tvTitle.setText(R.string.activity_login);
    }

    @Override
    protected void initData() {
        mContext = this;
        mContextSharedPreferences = mContext.getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
        final String telephone = mContextSharedPreferences.getString("telephone", "");
        final String password = mContextSharedPreferences.getString("password", "");
        etTelephone.setText(telephone);
        etLoginPassword.setText(password);
    }

    @Override
    protected void initListener() {
        mTvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ivReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist:
                RegisterActivity.start(mContext);
                break;
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String telephone1 = etTelephone.getText().toString();
        String pass = etLoginPassword.getText().toString();
        if (telephone1.length() != 11) {
            ToastUtil.showToast(getString(R.string.telephone_error));
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtil.showToast(getString(R.string.enter_pwd));
            return;
        }
        UserBean userBean = DataBaseSQLiteUtil.userLogin(telephone1, pass);
        if (userBean != null) {
            //登录设置
            SharedPreferences.Editor editor = mContextSharedPreferences.edit();
            editor.putString(INTENT_USER_ID, userBean.getUserId());
            editor.putString("telephone", telephone1);
            editor.putString("password", pass);
            editor.putBoolean("login", true);
            editor.commit();
            //将登录的手机号码显示在MineFragment
            Intent intent = new Intent(MineFragment.MY_ACTION);
            intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_LOGIN);
            intent.putExtra(MineFragment.MyReceiver.RESULT, telephone1);
            intent.putExtra("login", true);
            sendBroadcast(intent);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            ToastUtil.showToast(getString(R.string.toast_login_error));
        }
    }
}
