package www.formssi.goodtaste.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class FoodBean  implements Serializable {

    private String goodsId;        //商品id
    private String goodsType;      //商品的类型 一般店铺进行分类的时候进行使用按照类型进行分类
    private String goodsName;      //商品名字
    private int goodsIcon;         //商品图片
    private int goodsCount;        //商品销量
    private float goodsSrart;      //商品评分
    private String goodsMoney;     //商品单价
    private int goodsNumber;       //商品单次购买数
    private int goodsBuynumber;    //你所要购买的数量

    public FoodBean() {

    }
    public FoodBean(String goodsId, String goodsType, String goodsName, int goodsIcon, int goodsCount, float goodsSrart, String goodsMoney, int goodsNumber, int goodsBuynumber) {
        this.goodsId = goodsId;
        this.goodsType = goodsType;
        this.goodsName = goodsName;
        this.goodsIcon = goodsIcon;
        this.goodsCount = goodsCount;
        this.goodsSrart = goodsSrart;
        this.goodsMoney = goodsMoney;
        this.goodsNumber = goodsNumber;
        this.goodsBuynumber = goodsBuynumber;
    }
    public FoodBean(String goodsId,String goodsType, String goodsName, int goodsIcon,int goodsCount, float goodsSrart, String  goodsMoney) {
        this.goodsId = goodsId;
        this.goodsType=goodsType;
        this.goodsName = goodsName;
        this.goodsIcon=goodsIcon;
        this.goodsCount = goodsCount;
        this.goodsSrart = goodsSrart;
        this.goodsMoney=goodsMoney;
    }

    public FoodBean(String goodsId, String goodsName, int goodsIcon,int goodsCount, float goodsSrart, String  goodsMoney) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsIcon=goodsIcon;
        this.goodsCount = goodsCount;
        this.goodsSrart = goodsSrart;
        this.goodsMoney=goodsMoney;
    }


    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(int goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public float getGoodsSrart() {
        return goodsSrart;
    }

    public void setGoodsSrart(float goodsSrart) {
        this.goodsSrart = goodsSrart;
    }

    public String getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(String goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public int getGoodsBuynumber() {
        return goodsBuynumber;
    }

    public void setGoodsBuynumber(int goodsBuynumber) {
        this.goodsBuynumber = goodsBuynumber;
    }
}

