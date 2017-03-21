package www.formssi.goodtaste.bean;

import java.io.Serializable;

/**
 * 收货地址实体类
 * Created by sn on 2017/3/16.
 */
public class AddressBean implements Serializable {

    private String userId; //用户id
    private String addressId; //地址id
    private String name; //姓名
    private String gender; //性别
    private String phone; //电话
    private String address; //地址

    public AddressBean() {
    }

    public String toAddressString() {
        return name + ' ' + gender + '\n' + phone + '\n' + address;
    }

    public AddressBean(String userId, String name, String gender, String phone, String address) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
