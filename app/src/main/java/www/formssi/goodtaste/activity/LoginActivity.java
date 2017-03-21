package www.formssi.goodtaste.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    private ImageView ivReturn; //返回
    private TextView tvTitle; //标题
    private Button btnLogin; //登录
    private EditText etTelephone; //
    private EditText etLoginPassword; //登录密码
    private SharedPreferences mContextSharedPreferences;
    private TextView tvRegist;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvTitle.setText("登录");
        etTelephone = (EditText) findViewById(R.id.et_login_telephone);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegist = (TextView) findViewById(R.id.tv_regist);
        tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.start(mContext);
            }
        });
        mContextSharedPreferences = mContext.getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
        final String telephone = mContextSharedPreferences.getString("telephone", "");
        final String password = mContextSharedPreferences.getString("password", "");
        etTelephone.setText(telephone);
        etLoginPassword.setText(password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telephone1 = etTelephone.getText().toString();
                String pass = etLoginPassword.getText().toString();
                if (telephone1.length() != 11) {
                    Toast.makeText(mContext, "手机号错误", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(mContext, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }

                DataBaseSQLiteUtil.openDataBase();
                UserBean userBean = DataBaseSQLiteUtil.userLogin(telephone1, pass);
                DataBaseSQLiteUtil.closeDataBase();
                if (userBean != null) {
                    //登录设置
                    SharedPreferences.Editor editor = mContextSharedPreferences.edit();
                    editor.putString("telephone", telephone1);
                    editor.putString("password", pass);
                    editor.putBoolean("login", true);
                    editor.commit();
                    //将登录的手机号码显示在MineFragment
                    Intent intent = new Intent(MineFragment.MY_ACTION);
                    intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_TELEPHONE);
                    intent.putExtra(MineFragment.MyReceiver.RESULT, telephone1);
                    intent.putExtra("login", true);
                    sendBroadcast(intent);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.showToast("用户名或密码错误");
                }
            }
        });
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
