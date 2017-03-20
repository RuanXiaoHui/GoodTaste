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

public class ReceiveAddressActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener{

    private static final String TAG = "ReceiveAddressActivity";
    private ImageView ivBack; //返回
    private TextView tvTitle; //标题
    private ListView lvAddress; //地址列表
    private LinearLayout llt_ReceiveAddressActivity_addAddress; //新增地址栏

    private List<Object> list;  //对象列表
    private AddressBean addressBean; // 地址实体类
    private  int addressId; //当前选中得item项位置
    private AddressAdapter addressAdapter; //适配器
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_address);
        bindViews();
        tvTitle.setText(R.string.activity_receiveAddress_title);
        operateListView();
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
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_backTitlebar_back: //返回
                this.finish();
                break;
            case R.id.llt_ReceiveAddressActivity_addAddress: //新增地址
                intent = new Intent(ReceiveAddressActivity.this,AddNewAddressActivity.class);
                startActivityForResult(intent,ADD_NEW_ADREES_REQUEST);
                break;
            default:
                break;
        }
    }

    /**
     * 创建唯一id，可用来标识每个地址项
     * @return
     */
    private String createAddressID() {
        int hashCode = UUID.randomUUID().hashCode();
        return Integer.toHexString(hashCode);
    }

    /**
     * 对ListView的操作
     */
    private void operateListView() {
        //添加数据
        list = new ArrayList<Object>();
        list.add(new AddressBean("",createAddressID(),"微微","女士","18376614562","深圳市罗湖XXXXX",false));
        list.add(new AddressBean("",createAddressID(),"哈哈","女士","18376614562","深圳市罗湖XXXXX",false));
        list.add(new AddressBean("",createAddressID(),"呵呵","女士","18376614562","深圳市罗湖XXXXX",false));
        list.add(new AddressBean("",createAddressID(),"微微","女士","18376614562","深圳市罗湖XXXXX",false));
        list.add(new AddressBean("",createAddressID(),"哈哈","女士","18376614562","深圳市罗湖XXXXX",false));
        list.add(new AddressBean("",createAddressID(),"呵呵","女士","18376614562","深圳市罗湖XXXXX",false));
        //添加适配器
        addressAdapter = new AddressAdapter(list, ReceiveAddressActivity.this);
        lvAddress.setAdapter(addressAdapter);
    }

    /**
     * 长按列表项，删除地址
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
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent = new Intent();
        AddressBean addressBean = (AddressBean) list.get(position);
        intent.putExtra("name",addressBean.getName());
        intent.putExtra("gender",addressBean.getGender());
        intent.putExtra("phone",addressBean.getPhone());
        intent.putExtra("address",addressBean.getAddress());
        setResult(OREDER_REDDRESS_RESULT,intent);
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
                list.remove(position);
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
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NEW_ADREES_REQUEST && resultCode == ADD_NEW_ADREES_RESULT){

            String name = data.getStringExtra("name");
            String gender = data.getStringExtra("gender");
            String phone = data.getStringExtra("phone");
            String address = data.getStringExtra("address");
            Log.e(TAG, name+gender+phone+address);

            //把填写的地址添加到列表中
            list.add(new AddressBean("",createAddressID(),name,gender,phone,address,false));
            addressAdapter.notifyDataSetChanged();
            lvAddress.setSelection(list.size() - 1);

        }else if((requestCode == EDIT_ADREES_REQUEST && resultCode == EDIT_ADREES_RESULT)){

            Bundle bundle = data.getBundleExtra("returnEditAddressBeanBunlde");
            AddressBean addressBean = (AddressBean) bundle.getSerializable("returnEditAddressBean");
            int returnEditPosition = bundle.getInt("returnEditPosition");
//            int addressId = Integer.parseInt(addressBean.getAddressId());
            list.set(returnEditPosition,addressBean);
            addressAdapter.notifyDataSetChanged();

        }
    }
}
