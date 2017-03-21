package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.EditAddressActivity;
import www.formssi.goodtaste.activity.ReceiveAddressActivity;
import www.formssi.goodtaste.bean.AddressBean;

import static www.formssi.goodtaste.constant.ConstantConfig.EDIT_ADREES_REQUEST;

/**
 * 用户收货地址ListView适配器
 * Created by sn on 2017/3/17.
 */

public class AddressAdapter extends BaseAdapter {

    protected List<AddressBean> list;  //地址对象集合
    protected Context context; //上下文

    public AddressAdapter(List<AddressBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public AddressBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AddressViewHolder holder = null;
        if (convertView == null) {
            holder = new AddressViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receive_oreder_address, null);
            holder.tvReceiverName = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_name);
            holder.tvReceiverGender = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_gender);
            holder.tvReceiverPhone = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_phone);
            holder.tvReceiverAddress = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_address);
            holder.ibEdit = (ImageButton) convertView.findViewById(R.id.ib_ReceiveAddressActivity_edit);
            convertView.setTag(holder);
        } else {
            holder = (AddressViewHolder) convertView.getTag();
        }

        //赋值
        final AddressBean addressBean = getItem(position);
        holder.tvReceiverName.setText(addressBean.getName());
        holder.tvReceiverGender.setText(addressBean.getGender());
        holder.tvReceiverPhone.setText(addressBean.getPhone());
        holder.tvReceiverAddress.setText(addressBean.getAddress());
        //编辑按钮监听事件
        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddressActivity.class);
                intent.putExtra("addressId", addressBean.getAddressId());
                ((ReceiveAddressActivity) context).startActivityForResult(intent, EDIT_ADREES_REQUEST);
            }
        });

        return convertView;
    }

    public class AddressViewHolder {
        TextView tvReceiverName; //姓名
        TextView tvReceiverGender; //性别
        TextView tvReceiverPhone; //电话
        TextView tvReceiverAddress;//地址
        ImageButton ibEdit; //编辑按钮
    }

}
