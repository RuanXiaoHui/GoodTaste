package www.formssi.goodtaste.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
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
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.EDIT_ADREES_RESULT;
import static www.formssi.goodtaste.utils.StringUtils.checkPhoneIsEleven;

/**
 * 编辑收货地址页面
 * Created by sn on 2017/3/17.
 */
public class EditAddressActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView ivBack;  //返回
    private TextView tvTitle; //标题
    private EditText etName; //姓名
    private EditText etPhone; //电话
    private EditText etAddress; //地址
    private RadioGroup rgGender; //性别
    private RadioButton rbGentleman; //男士
    private RadioButton rbLady; //女士
    private Button btnOk;//确定
    private String mGender; //获取性别
    private AddressBean addressBean;  //地址对象
    private String addressId;  //地址id
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        initView();      //初始化控件
        initData();      //初始化数据
        initListener();  //初始化监听事件
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        etName = (EditText) findViewById(R.id.et_EditAddressActivity_name);
        etPhone = (EditText) findViewById(R.id.et_EditAddressActivity_phone);
        etAddress = (EditText) findViewById(R.id.et_EditAddressActivity_address);
        rgGender = (RadioGroup) findViewById(R.id.rg_EditAddressActivity_gender);
        rbGentleman = (RadioButton) findViewById(R.id.rb_EditAddressActivity_gentleman);
        rbLady = (RadioButton) findViewById(R.id.rb_EditAddressActivity_lady);
        btnOk = (Button) findViewById(R.id.btn_EditAddressActivity_ok);
    }

    @Override
    protected void initData() {
        //设置标题
        tvTitle.setText(R.string.activity_editAddress_title);
        //获取上一页面传递过来的地址id
        intent = getIntent();
        addressId = intent.getStringExtra("addressId");
        //通过订单id获取地址对象
        addressBean = DataBaseSQLiteUtil.getAddressById(addressId);
        //获取性别
        mGender = addressBean.getGender();
        //打开修改地址页面，显示暂未修改的信息
        showOldData(mGender);
    }

    @Override
    protected void initListener() {
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
                String name = etName.getText().toString().trim();        //收货人
                String phone = etPhone.getText().toString().trim();      //电话
                String address = etAddress.getText().toString().trim();  //地址信息
                boolean nameEmpty = TextUtils.isEmpty(name);
                boolean phoneIsEleven = checkPhoneIsEleven(phone);
                boolean genderEmpty = TextUtils.isEmpty(mGender);
                boolean addressEmpty = TextUtils.isEmpty(address);
                if (nameEmpty || genderEmpty || addressEmpty) {
                    Toast.makeText(this, R.string.activity_address_please_enter_a_complete_message, Toast.LENGTH_SHORT).show();
                } else if (!phoneIsEleven) {
                    Toast.makeText(this, R.string.activity_address_please_enter_11_phone_number, Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent();
                    addressBean.setName(name);
                    addressBean.setGender(mGender);
                    addressBean.setPhone(phone);
                    addressBean.setAddress(address);
                    //修改地址信息
                    DataBaseSQLiteUtil.userEditAddress(addressBean);
                    setResult(EDIT_ADREES_RESULT, intent);
                    this.finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 单选按钮
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton rbGender = (RadioButton) findViewById(checkedId);
        mGender = rbGender.getText().toString();  //性别
    }

    /**
     * 显示原来的数据
     */
    private void showOldData(String gender) {
        etName.setText(addressBean.getName()); //显示姓名
        if (gender.equals(getString(R.string.activity_address_gentleman))) {  //如果对象中性别为先生，选中先生单选按钮
            rbGentleman.setChecked(true);
            rbLady.setChecked(false);
        } else if (gender.equals(getString(R.string.activity_address_lady))) {//如果对象中性别为女士，选中男士单选按钮
            rbGentleman.setChecked(false);
            rbLady.setChecked(true);
        }
        addressBean.setGender(gender); //在地址对象中设置性别
        etPhone.setText(addressBean.getPhone()); //显示电话
        etAddress.setText(addressBean.getAddress());  //显示地址
    }
}
