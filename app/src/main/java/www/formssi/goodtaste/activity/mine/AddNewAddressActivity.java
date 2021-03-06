package www.formssi.goodtaste.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.ADD_NEW_ADREES_RESULT;
import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_USER_ID;
import static www.formssi.goodtaste.utils.StringUtils.checkPhoneIsEleven;

/**
 * 新增收货地址页面
 * 说明：直接输入姓名、性别、电话、地址
 * Created by sn on 2017/3/17.
 */
public class AddNewAddressActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView ivBack;    //返回
    private TextView tvTitle;    //标题
    private EditText etName;     //姓名
    private EditText etPhone;    //电话
    private EditText etAddress;  //地址
    private RadioGroup rgGender; //性别
    private Button btnOk;        //确定
    private String gender;       //获取性别

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();      //初始化控件
        initData();      // 初始化数据
        initListener();  //初始化监听事件
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        etName = (EditText) findViewById(R.id.et_AddNewAddressActivity_name);
        etPhone = (EditText) findViewById(R.id.et_AddNewAddressActivity_phone);
        etAddress = (EditText) findViewById(R.id.et_AddNewAddressActivity_address);
        rgGender = (RadioGroup) findViewById(R.id.rg_AddNewAddressActivity_gender);
        btnOk = (Button) findViewById(R.id.btn_AddNewAddressActivity_ok);
    }

    @Override
    protected void initData() {
        //设置标题
        tvTitle.setText(R.string.activity_addNewAddress_title);
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
            case R.id.btn_AddNewAddressActivity_ok: // 确定
                Intent intent = new Intent();
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                boolean nameEmpty = TextUtils.isEmpty(name);
                boolean phoneIsEleven = checkPhoneIsEleven(phone);
                boolean genderEmpty = TextUtils.isEmpty(gender);
                boolean addressEmpty = TextUtils.isEmpty(address);
                if (nameEmpty || genderEmpty || addressEmpty) {
                    Toast.makeText(this, R.string.activity_address_please_enter_a_complete_message, Toast.LENGTH_SHORT).show();
                } else if (!phoneIsEleven) {
                    Toast.makeText(this, R.string.activity_address_please_enter_11_phone_number, Toast.LENGTH_SHORT).show();
                } else {
                    //获取当前登录用户id
                    SharedPreferences sharedPreferences = getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
                    String userId = sharedPreferences.getString(INTENT_USER_ID, "");
                    AddressBean addressBean = new AddressBean(userId, name, gender, phone, address);
                    long addressId = DataBaseSQLiteUtil.userInsertAddress(addressBean);
                    //用户对象
                    UserBean userBean = new UserBean();
                    userBean.setUserId(userId);
                    userBean.setAddressId(addressId + "");
                    //设置默认地址
                    DataBaseSQLiteUtil.setUserDefaultAddress(userBean);
                    setResult(ADD_NEW_ADREES_RESULT, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 监听性别单选按钮
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton rbGender = (RadioButton) findViewById(checkedId);
        gender = rbGender.getText().toString();
    }
}
