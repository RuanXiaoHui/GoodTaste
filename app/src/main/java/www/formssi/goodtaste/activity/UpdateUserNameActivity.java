package www.formssi.goodtaste.activity;

import android.content.Context;
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
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

public class UpdateUserNameActivity extends AppCompatActivity {

    private TextView tvTitle; //标题
    private EditText etUpdateUsername;
    private Button btnUpdate;
    private ImageView ivReturn; //返回
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_name);
        userBean = (UserBean) getIntent().getSerializableExtra("user");
        etUpdateUsername = (EditText) findViewById(R.id.et_update_username);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvTitle.setText("修改用户名");
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contents = etUpdateUsername.getText().toString();
                if (!TextUtils.isEmpty(contents)) {

                    DataBaseSQLiteUtil.openDataBase();
                    DataBaseSQLiteUtil.updateUserName(userBean.getPhoneNumber(), contents);
                    DataBaseSQLiteUtil.closeDataBase();

                    Intent intent = new Intent(MineFragment.MY_ACTION);
                    intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_USERNAME);
                    intent.putExtra(MineFragment.MyReceiver.RESULT, contents);
                    sendBroadcast(intent);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}
