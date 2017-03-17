package www.formssi.goodtaste.bean;

/**
 * Created by GTs on 2017-03-17.
 */

public class OrderBean {
    String id;
    String shopName;
    String orderTime;
    String orderContent;
    String price;
    String status;
    String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderBean() {
    }

    public OrderBean(String shopName, String orderTime, String orderContent, String price, String status) {
        this.shopName = shopName;
        this.orderTime = orderTime;
        this.orderContent = orderContent;
        this.price = price;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
