package www.formssi.goodtaste.bean;

import java.util.List;

/**
 * 订单实体类
 *
 * @author qq724418408
 */
public class OrderBean {

    private String orderId; // 订单id
    private String userId; // 用户id
    private String storeId; // 店铺id
    private String shopName; // 商店名称
    private String shopImgPath; // 商店图像
    private int shopPicture; // 商店图像
    private String orderNum; // 订单号（参考）：店铺id（后三位） + 用户id（后三位） + 用户手机号（后四位） + 流水号（0000-9999）
    private String status; // 订单状态：未支付、未配送、送餐中、已完成、已评价
    private String orderContent; // 订单内容
    private List<FoodBean> foodBeanList; // 食品列表
    private String distributingFee; // 配送费
    private String orderTotalMoney; // 总金额
    private String discountMoney; // 优惠金额
    private String actualPayment; // 实付金额
    private String orderTime; // 下单时间
    private String payTime; // 支付时间
    private String address; // 送餐地址

    public OrderBean() {
    }

    public OrderBean(String shopName, String orderTime, String orderContent, String status) {
        this.shopName = shopName;
        this.orderTime = orderTime;
        this.orderContent = orderContent;
        this.status = status;
    }

    public int getShopPicture() {
        return shopPicture;
    }

    public void setShopPicture(int shopPicture) {
        this.shopPicture = shopPicture;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getShopImgPath() {
        return shopImgPath;
    }

    public void setShopImgPath(String shopImgPath) {
        this.shopImgPath = shopImgPath;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List<FoodBean> getFoodBeanList() {
        return foodBeanList;
    }

    public void setFoodBeanList(List<FoodBean> foodBeanList) {
        this.foodBeanList = foodBeanList;
    }

    public String getDistributingFee() {
        return distributingFee;
    }

    public void setDistributingFee(String distributingFee) {
        this.distributingFee = distributingFee;
    }

    public String getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(String orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public String getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(String actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransactionStatus() {
        return status;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.status = transactionStatus;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String commodityName) {
        this.orderContent = commodityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
