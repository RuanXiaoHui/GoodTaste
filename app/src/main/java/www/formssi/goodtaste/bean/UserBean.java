package www.formssi.goodtaste.bean;

import java.io.Serializable;

/**
 * Created by qkldev003 on 2017/3/17.
 */

public class UserBean implements Serializable{
    private String userId; //用户ID
    private String userName; //用户名
    private String headProtrait; //头像
    private String phoneNimeber; //电话号码
    private String loginPassword; //登录密码
    private String payPassword; //支付密码
    public UserBean() {
    }

    public UserBean(String userId, String userName, String headProtrait, String phoneNimeber, String loginPassword, String payPassword) {
        this.userId = userId;
        this.userName = userName;
        this.headProtrait = headProtrait;
        this.phoneNimeber = phoneNimeber;
        this.loginPassword = loginPassword;
        this.payPassword = payPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadProtrait() {
        return headProtrait;
    }

    public void setHeadProtrait(String headProtrait) {
        this.headProtrait = headProtrait;
    }

    public String getPhoneNimeber() {
        return phoneNimeber;
    }

    public void setPhoneNimeber(String phoneNimeber) {
        this.phoneNimeber = phoneNimeber;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

}
