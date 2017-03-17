package www.formssi.goodtaste.bean;

/**
 * Created by GTs on 2017-03-17.
 */

public class OrderBean {
    String shopName;
    String orderTime;
    String commodityName;
    String price;
    String transactionStatus;


    public OrderBean(String shopName, String orderTime, String commodityName, String price, String transactionStatus) {
        this.shopName = shopName;
        this.orderTime = orderTime;
        this.commodityName = commodityName;
        this.price = price;
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    int type;

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

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



}
