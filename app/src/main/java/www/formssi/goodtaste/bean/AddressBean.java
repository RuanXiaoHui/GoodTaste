package www.formssi.goodtaste.bean;

/**
 * 收货地址实体类
 * Created by sn on 2017/3/17.
 */
public class AddressBean {

    private String userId; //用户id
    private String addressId; //地址id
    private String name; //姓名
    private String gender; //性别
    private String phone; //电话
    private String address; //地址
    private boolean isCheckboxFlag; //复选框是否选中状态

    public AddressBean() {
    }

    public AddressBean(String userId, String addressId, String name, String gender, String phone, String address, boolean isCheckboxFlag) {
        this.userId = userId;
        this.addressId = addressId;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.isCheckboxFlag = isCheckboxFlag;
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

    public boolean isCheckboxFlag() {
        return isCheckboxFlag;
    }

    public void setCheckboxFlag(boolean checkboxFlag) {
        isCheckboxFlag = checkboxFlag;
    }
}
