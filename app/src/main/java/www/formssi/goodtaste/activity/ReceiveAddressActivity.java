package www.formssi.goodtaste.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.adapter.XAdapter;

/**
 * 收货地址页面
 * Created by john on 2017/3/16.
 */

public class ReceiveAddressActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "ConfirmOrderActivity";
    private ImageView ivBack; //返回
    private TextView tvTitle; //标题
    private ListView lvAddress; //地址列表
    private LinearLayout llt_ReceiveAddressActivity_addAddress; //新增地址栏

    private List<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_address);

        bindViews();
        tvTitle.setText(R.string.activity_receiveAddress_title);
        operateListView();
    }


    private void bindViews() {
        ivBack = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_Title);
        lvAddress = (ListView) findViewById(R.id.lv_ReceiveAddressActivity_address);
        llt_ReceiveAddressActivity_addAddress = (LinearLayout) findViewById(R.id.llt_ReceiveAddressActivity_addAddress);

        ivBack.setOnClickListener(this);
        llt_ReceiveAddressActivity_addAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.llt_ReceiveAddressActivity_addAddress:
                break;
            default:
                break;
        }
    }
    private void operateListView() {
        list = new ArrayList<Object>();
        //添加数据
        list = new ArrayList<Object>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        //添加适配器
        lvAddress.setAdapter(new XAdapter(list,ReceiveAddressActivity.this) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                AddressViewHolder holder = null;
                if (convertView == null){
                    holder = new AddressViewHolder();
                    convertView = LayoutInflater.from(ReceiveAddressActivity.this).inflate(R.layout.item_receive_oreder_address,null);
                    holder.tvReceiverName = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_name);
                    holder.tvReceiverGender = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_gender);
                    holder.tvReceiverPhone = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_phone);
                    holder.tvReceiverAddress = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_address);
                    holder.cbSelected = (CheckBox) convertView.findViewById(R.id.cb_ReceiveAddressActivity_selected);
                    holder.ivEdit = (ImageView) convertView.findViewById(R.id.iv_ReceiveAddressActivity_edit);
                    convertView.setTag(holder);
                }else {
                    holder = (AddressViewHolder) convertView.getTag();
                }
                //赋值

                return convertView;
            }
        });

    }

    public class AddressViewHolder{
        TextView tvReceiverName; //姓名
        TextView tvReceiverGender; //性别
        TextView tvReceiverPhone; //电话
        TextView tvReceiverAddress;//地址
        CheckBox cbSelected; //复选框
        ImageView ivEdit; //编辑
    }
}
