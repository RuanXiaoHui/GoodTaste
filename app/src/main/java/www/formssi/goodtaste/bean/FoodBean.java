package www.formssi.goodtaste.bean;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class FoodBean {

    private String goodsId;        //商品id
    private String goodsName;      //商品
    private int goodsCount;        //商品销量
    private float goodsSrart;      //商品评分

    public FoodBean(String goodsId, String goodsName, int goodsCount, float goodsSrart) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsCount = goodsCount;
        this.goodsSrart = goodsSrart;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public float getGoodsSrart() {
        return goodsSrart;
    }

    public void setGoodsSrart(float goodsSrart) {
        this.goodsSrart = goodsSrart;
    }
}
