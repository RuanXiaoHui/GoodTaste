package www.formssi.goodtaste.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.adapter.AddressAdapter;
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.ADD_NEW_ADREES_REQUEST;
import static www.formssi.goodtaste.constant.ConstantConfig.ADD_NEW_ADREES_RESULT;
import static www.formssi.goodtaste.constant.ConstantConfig.EDIT_ADREES_REQUEST;
import static www.formssi.goodtaste.constant.ConstantConfig.EDIT_ADREES_RESULT;
import static www.formssi.goodtaste.constant.ConstantConfig.OREDER_REDDRESS_RESULT;

/**
 * 收货地址列表页面
 * 说明：收货地址列表显示、新增、删除、修改地址
 * Created by john on 2017/3/16.
 */

public class ReceiveAddressActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "ReceiveAddressActivity";
    private ImageView ivBack; //返回
    private TextView tvTitle; //标题
    private ListView lvAddress; //地址列表
    private LinearLayout llt_ReceiveAddressActivity_addAddress; //新增地址栏

    private List<AddressBean> addressBeanList = new ArrayList<>();  //对象列表
    private AddressAdapter addressAdapter; //适配器
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_address);
        DataBaseSQLiteUtil.openDataBase();
        bindViews();
        tvTitle.setText(R.string.activity_receiveAddress_title);
        operateListView();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * 初始化，绑定控件
     */
    private void bindViews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        lvAddress = (ListView) findViewById(R.id.lv_ReceiveAddressActivity_address);
        llt_ReceiveAddressActivity_addAddress = (LinearLayout) findViewById(R.id.llt_ReceiveAddressActivity_addAddress);

        ivBack.setOnClickListener(this);
        llt_ReceiveAddressActivity_addAddress.setOnClickListener(this);
        lvAddress.setOnItemLongClickListener(this);
        lvAddress.setOnItemClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backTitlebar_back: //返回
                this.finish();
                break;
            case R.id.llt_ReceiveAddressActivity_addAddress: //新增地址
                intent = new Intent(ReceiveAddressActivity.this, AddNewAddressActivity.class);
                startActivityForResult(intent, ADD_NEW_ADREES_REQUEST);
                break;
            default:
                break;
        }
    }

    /**
     * 对ListView的操作
     */
    private void operateListView() {
        //添加数据
        UserBean userBean = new UserBean();
        userBean.setUserId("1");
        addressBeanList = DataBaseSQLiteUtil.queryAddressByUserId(Integer.parseInt(userBean.getUserId())); // 根据地址id获取用户保存的地址列表
        addressAdapter = new AddressAdapter(addressBeanList, this);
        lvAddress.setAdapter(addressAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        UserBean userBean = new UserBean();
        userBean.setUserId("1");
        addressBeanList = DataBaseSQLiteUtil.queryAddressByUserId(Integer.parseInt(userBean.getUserId()));
        addressAdapter.notifyDataSetChanged();
    }

    /**
     * 长按列表项，删除地址
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        createDialog(position);
        return true;
    }

    /**
     * 点击列表项，选择收货地址
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent = new Intent();
        AddressBean addressBean = addressBeanList.get(position);
        intent.putExtra("name", addressBean.getName());
        intent.putExtra("gender", addressBean.getGender());
        intent.putExtra("phone", addressBean.getPhone());
        intent.putExtra("address", addressBean.getAddress());
        setResult(OREDER_REDDRESS_RESULT, intent);
        this.finish();
    }

    private void createDialog(final int position) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ReceiveAddressActivity.this);
        dialog.setTitle("删除地址");
        dialog.setMessage("你确定要删除该地址吗？");
        //确定
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddressBean addressBean = addressBeanList.remove(position);
//                DataBaseSQLiteUtil.userDeleteAddressById(addressBean.getAddressId());
                addressAdapter.notifyDataSetChanged();
            }
        });
        //取消
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    /**
     * 新增收货地址的返回结果处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == ADD_NEW_ADREES_REQUEST && resultCode == ADD_NEW_ADREES_RESULT) { //新增地址

                String name = data.getStringExtra("name");
                String gender = data.getStringExtra("gender");
                String phone = data.getStringExtra("phone");
                String address = data.getStringExtra("address");
                Log.e(TAG, name + gender + phone + address);

                //把填写的地址添加到列表中
                AddressBean addressBean = new AddressBean("1", "1", name, gender, phone, address);
                addressBeanList.add(addressBean);
                addressAdapter.notifyDataSetChanged();
                lvAddress.setSelection(addressBeanList.size() - 1);
                DataBaseSQLiteUtil.userInsertAddress(addressBean);
            } else if ((requestCode == EDIT_ADREES_REQUEST && resultCode == EDIT_ADREES_RESULT)) {  //编辑地址
                Bundle bundle = data.getBundleExtra("returnEditAddressBeanBunlde");
                AddressBean addressBean = (AddressBean) bundle.getSerializable("returnEditAddressBean");
//                DataBaseSQLiteUtil.userEditAddress(addressBean);
                addressBeanList = DataBaseSQLiteUtil.queryAddressByUserId(Integer.parseInt(addressBean.getUserId()));
                addressAdapter.notifyDataSetChanged();
            }
        }
    }
}
