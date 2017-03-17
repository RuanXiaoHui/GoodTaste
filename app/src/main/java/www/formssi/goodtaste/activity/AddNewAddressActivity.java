package www.formssi.goodtaste.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import www.formssi.goodtaste.R;

/**
 * 新增收货地址页面
 * 说明：直接输入姓名、性别、电话、地址
 * Created by john on 2017/3/17.
 */

public class AddNewAddressActivity extends Activity implements View.OnClickListener {

    private ImageView ivBack;  //返回
    private TextView tvTitle; //标题
    private EditText etName; //姓名
    private EditText etPhone; //电话
    private EditText etAddress; //地址
    private RadioGroup rgGender; //性别
    private RadioButton rbGentleman; //男士
    private RadioButton rbLady; //女士
    private Button btnOk;//确定

    private  int ORDER_REMARK = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        bindViews();
    }

    /**
     * 初始化，绑定控件
     */
    private void bindViews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        etName = (EditText) findViewById(R.id.et_AddNewAddressActivity_name);
        etPhone = (EditText) findViewById(R.id.et_AddNewAddressActivity_phone);
        etAddress = (EditText) findViewById(R.id.et_AddNewAddressActivity_address);
        rgGender = (RadioGroup) findViewById(R.id.rg_AddNewAddressActivity_gender);
        rbGentleman = (RadioButton) findViewById(R.id.rb_AddNewAddressActivity_gentleman);
        rbLady = (RadioButton) findViewById(R.id.rb_AddNewAddressActivity_lady);
        btnOk  = (Button) findViewById(R.id.btn_AddNewAddressActivity_ok);

    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_backTitlebar_back:  //返回
                this.finish();
                break;

            case R.id.btn_AddNewAddressActivity_ok: // 确定
                Intent intent =  new Intent();
                intent.putExtra("name",etName.getText().toString());
//                intent.putExtra("gender",.getText().toString());
                intent.putExtra("phone",etPhone.getText().toString());
                intent.putExtra("address",etAddress.getText().toString());
                setResult(RESULT_OK,intent);


                break;

            default:
                break;


        }
    }
}
