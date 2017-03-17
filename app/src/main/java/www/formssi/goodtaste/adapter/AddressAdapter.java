package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by john on 2017/3/17.
 */

public class AddressAdapter extends XAdapter implements CompoundButton.OnCheckedChangeListener {

    private  int currentPosition;//当前的item项位置
    private AddressBean addressBean;
//
//    //接口回调
//    private ViewOnClickListenr viewOnClickListner;
//
//    public interface ViewOnClickListenr{
//        void OnClickImageButton(int position); //图片按钮监听事件
//    }
//
//    //对外提供一个构造方法
//    public void setViewOnClickLister(ViewOnClickListenr viewOnClickListner){
//        this.viewOnClickListner = viewOnClickListner;
//    }

    public AddressAdapter(List<Object> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AddressViewHolder holder = null;
        if (convertView == null){
            holder = new AddressViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receive_oreder_address,null);
            holder.tvReceiverName = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_name);
            holder.tvReceiverGender = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_gender);
            holder.tvReceiverPhone = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_phone);
            holder.tvReceiverAddress = (TextView) convertView.findViewById(R.id.tv_ReceiveAddressActivity_address);
            holder.cbSelected = (CheckBox) convertView.findViewById(R.id.cb_ReceiveAddressActivity_selected);
            holder.ibEdit = (ImageButton) convertView.findViewById(R.id.ib_ReceiveAddressActivity_edit);

            holder.ibEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditAddressActivity.class);
                    Bundle bundle = new Bundle();
                    Object object = list.get(position);
                    AddressBean addressBean = (AddressBean) object;
                    bundle.putSerializable("editAddress",addressBean);
                    bundle.putInt("sendEditPosition",position);
                    intent.putExtra("editBundle",bundle);
                    ((ReceiveAddressActivity)context).startActivityForResult(intent,EDIT_ADREES_REQUEST);
                }
            });
            convertView.setTag(holder);
        }else {
            holder = (AddressViewHolder) convertView.getTag();
        }

        //赋值
        Object object = list.get(position);
        addressBean = (AddressBean) object;
        holder.tvReceiverName.setText(addressBean.getName());
        holder.tvReceiverGender.setText(addressBean.getGender());
        holder.tvReceiverPhone.setText(addressBean.getPhone());
        holder.tvReceiverAddress.setText(addressBean.getAddress());
        holder.cbSelected.setOnCheckedChangeListener(this);
        holder.cbSelected.setChecked(addressBean.isCheckboxFlag());

        return convertView;
    }

    public class AddressViewHolder{
        TextView tvReceiverName; //姓名
        TextView tvReceiverGender; //性别
        TextView tvReceiverPhone; //电话
        TextView tvReceiverAddress;//地址
        CheckBox cbSelected; //复选框
        ImageButton ibEdit; //编辑
    }

    /**
     * 复选按钮监听事件
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        addressBean.setCheckboxFlag(isChecked);
    }

}
