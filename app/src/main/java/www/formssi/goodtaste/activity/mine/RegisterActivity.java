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

import java.util.UUID;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.SPUtils;
import www.formssi.goodtaste.utils.ToastUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_USER_ID;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    Context mContext;
    private ImageView ivReturn; //返回
    private TextView tvTitle; //标题
    private Button btnLogin; //登录
    private EditText etTelephone; //
    private EditText etLoginPassword; //登录密码
    private EditText etPayLoginPassword; //支付密码
    private SharedPreferences mContextSharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        etPayLoginPassword = (EditText) findViewById(R.id.et_login_pay_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvTitle.setText(getString(R.string.activity_register));
    }

    @Override
    protected void initData() {
        mContext = this;
        mContextSharedPreferences = mContext.getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
        String telephone = mContextSharedPreferences.getString("telephone", "");
        final String password = mContextSharedPreferences.getString("password", "");
        etTelephone.setText(telephone);
        etLoginPassword.setText(password);
    }

    @Override
    protected void initListener() {
        ivReturn.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.btn_login:
                register();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String telephone1 = etTelephone.getText().toString();
        String pass = etLoginPassword.getText().toString();
        String pPwd = etPayLoginPassword.getText().toString();
        if (telephone1.length() != 11) {
            ToastUtil.showToast(getString(R.string.telephone_error));
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtil.showToast(getString(R.string.enter_pwd));
            return;
        }
        String id = Integer.toHexString(UUID.randomUUID().hashCode());
        String name = "_" + telephone1.substring(telephone1.length() - 4);
        String headUrl = "";
        UserBean user = new UserBean(id, name, headUrl, telephone1, pass, pPwd);
        long uid = DataBaseSQLiteUtil.userRegister(user);
        SPUtils.updateTel(mContext, telephone1);
        SPUtils.putString(mContext, INTENT_USER_ID, uid + "");
        //将注册的用户信息返回MineFragment
        Intent intent = new Intent(MineFragment.MY_ACTION);
        intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_LOGIN);
        intent.putExtra(MineFragment.MyReceiver.RESULT, user);
        intent.putExtra("login", true);
        sendBroadcast(intent);
        setResult(RESULT_OK, intent);
        finish();
    }
}
