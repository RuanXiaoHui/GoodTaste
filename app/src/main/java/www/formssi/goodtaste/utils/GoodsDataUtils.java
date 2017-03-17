package www.formssi.goodtaste.utils;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.GoodsMenu;
import www.formssi.goodtaste.bean.ShopBean;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class GoodsDataUtils {

    public static List<ShopBean> GetShop(){
         int shopIcon[]={R.mipmap.food,R.mipmap.food2,R.mipmap.food3,R.mipmap.food2,
                 R.mipmap.food3,R.mipmap.food,R.mipmap.food1,R.mipmap.food,
                 R.mipmap.food2,R.mipmap.food1,R.mipmap.food3,R.mipmap.food2,};
         String shopName[]={"小龙虾","老和火锅","高天回锅肉","俊流早餐",
                 "苏妮小菜","王永珠自助餐","小林子咖啡","老王餐馆",
                 "星巴克","肯德基","麦当劳","沙县小吃",};

        List<ShopBean> mData=new ArrayList<>();
        for (int i = 0; i <12 ; i++) {

            List<FoodBean> foods=new ArrayList<>();
            for (int j = 0; j <7 ; j++) {
                foods.add(new FoodBean("1","老母鸡煲汤",R.mipmap.ic_launcher,33,4,"18"));
            }

            List<GoodsMenu> shopMenu=new ArrayList<>();

            shopMenu.add(new GoodsMenu(1,"热销榜"));
            shopMenu.add(new GoodsMenu(2,"冷菜"));
            shopMenu.add(new GoodsMenu(3,"招牌菜"));
            shopMenu.add(new GoodsMenu(4,"热菜"));
            shopMenu.add(new GoodsMenu(5,"主食"));
            shopMenu.add(new GoodsMenu(6,"汤类"));

            mData.add(new ShopBean("110",shopName[i],foods, shopIcon[i],"4","深圳联谊大厦121","味道很不错，你值得拥有","4","18376611549",shopMenu,100+i,"35分钟","早10:00-晚21:00"));

            System.out.println(mData.get(i).getShopName()+mData.get(i).getShopDesc());

        }


        return mData;
    }

}
