package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.AddressBean;

import static www.formssi.goodtaste.constant.ConstantConfig.EDIT_ADREES_RESULT;

/**
 * 编辑收货地址页面
 * Created by john on 2017/3/17.
 */
public class EditAddressActivity extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private ImageView ivBack;  //返回
    private TextView tvTitle; //标题
    private EditText etName; //姓名
    private EditText etPhone; //电话
    private EditText etAddress; //地址
    private RadioGroup rgGender; //性别
    private RadioButton rbGentleman; //男士
    private RadioButton rbLady; //女士
    private Button btnOk;//确定

    private String gender; //获取性别
    private int currentPosition;  //当前修改的地址在列表中的位置
    private AddressBean addressBean;  //当前修改的列表项所对应的AddressBean实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        bindViews();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("editBundle");
        currentPosition = bundle.getInt("sendEditPosition", 0);
        addressBean = (AddressBean) bundle.getSerializable("editAddress");
        gender = addressBean.getGender();
        showOldData();
    }

    /**
     * 初始化、绑定控件
     */
    private void bindViews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        etName = (EditText) findViewById(R.id.et_EditAddressActivity_name);
        etPhone = (EditText) findViewById(R.id.et_EditAddressActivity_phone);
        etAddress = (EditText) findViewById(R.id.et_EditAddressActivity_address);
        rgGender = (RadioGroup) findViewById(R.id.rg_EditAddressActivity_gender);
        rbGentleman = (RadioButton) findViewById(R.id.rb_EditAddressActivity_gentleman);
        rbLady = (RadioButton) findViewById(R.id.rb_EditAddressActivity_lady);
        btnOk = (Button) findViewById(R.id.btn_EditAddressActivity_ok);

        ivBack.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        rgGender.setOnCheckedChangeListener(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back:  //返回
                this.finish();
                break;

            case R.id.btn_EditAddressActivity_ok: // 确定
                Intent intent = new Intent();
                addressBean.setName(etName.getText().toString());
                addressBean.setGender(gender);
                addressBean.setPhone(etPhone.getText().toString());
                addressBean.setAddress(etAddress.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putSerializable("returnEditAddressBean", addressBean);
                bundle.putInt("returnEditPosition", currentPosition);
                intent.putExtra("returnEditAddressBeanBunlde", bundle);
                setResult(EDIT_ADREES_RESULT, intent);
                this.finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton rbGender = (RadioButton) findViewById(checkedId);
        gender = rbGender.getText().toString();

    }

    /**
     * 显示原来的数据
     */
    private void showOldData() {
        tvTitle.setText(R.string.activity_editAddress_title);
        etName.setText(addressBean.getName());
        if(gender.equals("先生")){
            rbGentleman.setChecked(true);
            rbLady.setChecked(false);

        }else if(gender.equals("女士")){
            rbGentleman.setChecked(false);
            rbLady.setChecked(true);
        }
        addressBean.setGender(gender);
        etPhone.setText(addressBean.getPhone());
        etAddress.setText(addressBean.getAddress());
    }
}