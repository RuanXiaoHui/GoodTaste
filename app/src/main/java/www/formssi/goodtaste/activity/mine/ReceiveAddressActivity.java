package www.formssi.goodtaste.activity.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.adapter.AddressAdapter;
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;

import static www.formssi.goodtaste.constant.ConstantConfig.ADD_NEW_ADREES_REQUEST;
import static www.formssi.goodtaste.constant.ConstantConfig.INTENT_USER_ID;
import static www.formssi.goodtaste.constant.ConstantConfig.OREDER_REDDRESS_RESULT;

/**
 * 收货地址列表页面
 * 说明：收货地址列表显示、新增、删除、修改地址
 * Created by sn on 2017/3/16.
 */

public class ReceiveAddressActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private ImageView ivBack; //返回
    private TextView tvTitle; //标题
    private ListView lvAddress; //地址列表
    private LinearLayout llt_ReceiveAddressActivity_addAddress; //新增地址栏
    private List<AddressBean> addressBeanList = new ArrayList<>();  //对象列表
    private AddressAdapter addressAdapter; //适配器
    private String userId; //用户id
    private Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_address);
        initView();     //初始化控件
        initData();     //初始化数据
        initListener(); //初始化监听事件
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        lvAddress = (ListView) findViewById(R.id.lv_ReceiveAddressActivity_address);
        llt_ReceiveAddressActivity_addAddress = (LinearLayout) findViewById(R.id.llt_ReceiveAddressActivity_addAddress);
    }

    @Override
    protected void initData() {
        //获取当前登录的用户id
        sharedPreferences = getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(INTENT_USER_ID, "-1");
        //设置标题
        tvTitle.setText(R.string.activity_receiveAddress_title);
        //ListView操作
        operateListView();
    }

    @Override
    protected void initListener() {
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
            case R.id.iv_backTitlebar_back: //返回按钮
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
        // 根据地址id获取用户保存的地址列表
        addressBeanList = DataBaseSQLiteUtil.queryAddressByUserId(Integer.parseInt(userId));
        addressAdapter = new AddressAdapter(addressBeanList, this);
        lvAddress.setAdapter(addressAdapter);
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
        intent.putExtra("addressId", addressBean.getAddressId()); //地址id
        intent.putExtra("name", addressBean.getName());           //收货人
        intent.putExtra("gender", addressBean.getGender());       //性别
        intent.putExtra("phone", addressBean.getPhone());         //电话
        intent.putExtra("address", addressBean.getAddress());     //地址
        setResult(OREDER_REDDRESS_RESULT, intent);
        this.finish();
    }

    /**
     * 创建删除地址的对话框
     *
     * @param position
     */
    private void createDialog(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReceiveAddressActivity.this);
        dialog.setTitle(R.string.activity_receiveAddress_deleteAddress);
        dialog.setMessage(R.string.activity_receiveAddress_are_you_sure_delete_the_address);
        //确定按钮
        dialog.setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddressBean addressBean = addressBeanList.remove(position);
                DataBaseSQLiteUtil.userDeleteAddressById(addressBean.getAddressId());
                addressAdapter.notifyDataSetChanged();
            }
        });
        //取消按钮
        dialog.setNegativeButton(R.string.common_cancel, new DialogInterface.OnClickListener() {
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
        //清空地址列表
        addressBeanList.clear();
        //通过用户Id查询数据库,获得地址列表，并显示地址列表信息
        List<AddressBean> list = DataBaseSQLiteUtil.queryAddressByUserId(Integer.parseInt(userId));
        addressBeanList.addAll(list);
        addressAdapter.notifyDataSetChanged();
        lvAddress.setSelection(addressBeanList.size() - 1);
    }
}
