package www.formssi.goodtaste.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 * 描述：商店的实体类
 */
public class ShopBean  implements Serializable{

    private String shopId;        //商店ID
    private String shopName;      //商店名字
    private List<FoodBean> foods; //商品集合
    private int ShopPic;          //商店图片
    private String shopMoney;     //配送费
    private String shopAddress;   //商店地址
    private String shopDesc;      //商店描述
    private String shopStart;     //商店评分
    private String shopPhone;     //商店电话
    private List<GoodsMenu> shopMenu;  //商店菜单
    private int shopCount;     //商店总销量
    private String  distributionTime;   //平均配送时间
    private String shopBusinessHours;    //商店营业时间


    public ShopBean(String shopId, String shopName, List<FoodBean> foods, int shopPic, String shopMoney, String shopAddress, String shopDesc, String shopStart, String shopPhone,List<GoodsMenu> shopMenu,int shopCount,String  distributionTime,String shopBusinessHours) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.foods = foods;
        ShopPic = shopPic;
        this.shopMoney = shopMoney;
        this.shopAddress = shopAddress;
        this.shopDesc = shopDesc;
        this.shopStart = shopStart;
        this.shopPhone = shopPhone;
        this.shopMenu=shopMenu;
        this.shopCount=shopCount;
        this.distributionTime=distributionTime;
        this.shopBusinessHours=shopBusinessHours;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<FoodBean> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodBean> foods) {
        this.foods = foods;
    }

    public int getShopPic() {
        return ShopPic;
    }

    public void setShopPic(int shopPic) {
        ShopPic = shopPic;
    }

    public String getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(String shopMoney) {
        this.shopMoney = shopMoney;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopStart() {
        return shopStart;
    }

    public void setShopStart(String shopStart) {
        this.shopStart = shopStart;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public List<GoodsMenu> getShopMenu() {
        return shopMenu;
    }

    public void setShopMenu(List<GoodsMenu> shopMenu) {
        this.shopMenu = shopMenu;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public String getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(String distributionTime) {
        this.distributionTime = distributionTime;
    }

    public String getShopBusinessHours() {
        return shopBusinessHours;
    }

    public void setShopBusinessHours(String shopBusinessHours) {
        this.shopBusinessHours = shopBusinessHours;
    }
}
