package www.formssi.goodtaste.bean;

/**
 * Created by GTs on 2017-03-17.
 */

public class OrderBean {
    String id;//订单id
    String shopName;//商店名字
    String orderTime;//下单时间
    String orderContent;//订单内容
    String price;//价格
    String status;//状态
    String orderNumber;//订单号
    int shopPicture ;//商店图片的资源文件

    public int getShopPicture() {
        return shopPicture;
    }

    public void setShopPicture(int shopPicture) {
        this.shopPicture = shopPicture;
    }

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
