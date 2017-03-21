package www.formssi.goodtaste.activity;

import android.content.Intent;
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

public class UpdateUserNameActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private EditText etUpdateUsername;
    private Button btnUpdate;
    private ImageView ivReturn; //返回
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_name);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        etUpdateUsername = (EditText) findViewById(R.id.et_update_username);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        tvTitle.setText("修改用户名");
    }

    @Override
    protected void initData() {
        userBean = (UserBean) getIntent().getSerializableExtra("user");
        etUpdateUsername.setText(userBean.getUserName());
    }

    @Override
    protected void initListener() {
        ivReturn.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.btn_update:
                String contents = etUpdateUsername.getText().toString();
                if (!TextUtils.isEmpty(contents)) {
                    DataBaseSQLiteUtil.updateUserName(userBean.getPhoneNumber(), contents);
                    Intent intent = new Intent(MineFragment.MY_ACTION);
                    intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_USERNAME);
                    intent.putExtra(MineFragment.MyReceiver.RESULT, contents);
                    sendBroadcast(intent);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }
}
