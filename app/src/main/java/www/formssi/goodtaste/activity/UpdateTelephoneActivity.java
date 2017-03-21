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
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.SPUtils;
import www.formssi.goodtaste.utils.ToastUtil;

public class UpdateTelephoneActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回
    private EditText etTelephone; //原手机号码
    private EditText etUpdateTelephone; //新手机号码
    private Button btnUpdate; //确认绑定手机
    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_telephone);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        etTelephone = (EditText) findViewById(R.id.et_telephone);
        etUpdateTelephone = (EditText) findViewById(R.id.et_update_telephone);
        btnUpdate = (Button) findViewById(R.id.btn_update_telephone);
        tvTitle.setText("修改手机号码");
    }

    @Override
    protected void initData() {
        tel = getIntent().getStringExtra("tel");
    }

    @Override
    protected void initListener() {
        ivReturn.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;

            case R.id.btn_update_telephone:
                updateTelephone();
                break;
        }
    }

    private void updateTelephone() {
        String oldTelephone = etTelephone.getText().toString();
        String newTelephone = etUpdateTelephone.getText().toString();
        if (TextUtils.equals(tel, oldTelephone)) {
            if (TextUtils.getTrimmedLength(newTelephone) != 11) {
                ToastUtil.showToast("新手机号格式有误");
                return;
            }
            boolean b = DataBaseSQLiteUtil.updateUserPhone(tel, newTelephone);
            if (b) {
                SPUtils.updateTel(this, newTelephone);
            }
            Intent intent = new Intent(MineFragment.MY_ACTION);
            intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_TELEPHONE);
            intent.putExtra(MineFragment.MyReceiver.RESULT, newTelephone);
            sendBroadcast(intent);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            ToastUtil.showToast("旧手机不匹配");
        }
    }
}
