package www.formssi.goodtaste.utils;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.GoodsMenu;
import www.formssi.goodtaste.bean.ShopBean;

import static www.formssi.goodtaste.R.mipmap.food;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class GoodsDataUtils {

    public static List<ShopBean> GetShop(){
         int shopIcon[]={food,R.mipmap.food2,R.mipmap.food3,R.mipmap.food2,
                 R.mipmap.food3, food,R.mipmap.food1, food,
                 R.mipmap.food2,R.mipmap.food1,R.mipmap.food3,R.mipmap.food2,};
         String shopName[]={"小龙虾","老和火锅","高天回锅肉","俊流早餐",
                 "苏妮小菜","王永珠自助餐","小林子咖啡","老王餐馆",
                 "星巴克","肯德基","麦当劳","沙县小吃",};

        List<ShopBean> mData=new ArrayList<>();
        for (int i = 1; i <12 ; i++) {

            List<FoodBean> foods=new ArrayList<>();
            for (int j = 1; j <7 ; j++) {

                //生成食品ID   这里面进行拼接数，防止商品的Id数进行重复
                foods.add(new FoodBean(j+"00"+i,""+j,"老母鸡煲汤"+j,R.mipmap.ic_launcher,33,4,"18",13,0));
                foods.add(new FoodBean(j+"00"+(i+1),""+j,"好味道姜汤"+j,R.mipmap.ic_launcher,33,4,"11",432,0));
                foods.add(new FoodBean(j+"00"+(i+2),""+j,"小鸡炖蘑菇"+j,R.mipmap.ic_launcher,33,4,"22",43,0));
                foods.add(new FoodBean(j+"00"+(i+3),""+j,"芥末小鸡"+j,R.mipmap.ic_launcher,33,4,"45",32,0));
                foods.add(new FoodBean(j+"00"+(i+4),""+j,"鲫鱼炖汤"+j,R.mipmap.ic_launcher,33,4,"65",22,0));
                foods.add(new FoodBean(j+"00"+(i+5),""+j,"宫保鸡丁"+j,R.mipmap.ic_launcher,33,4,"11",12,0));
                foods.add(new FoodBean(j+"00"+(i+6),""+j,"萝卜牛腩"+j,R.mipmap.ic_launcher,33,4,"8",212,0));
                foods.add(new FoodBean(j+"00"+(i+7),""+j,"小黄人鸡汤"+j,R.mipmap.ic_launcher,33,4,"28",412,0));
                foods.add(new FoodBean(j+"00"+(i+8),""+j,"火腿炒青椒"+j,R.mipmap.ic_launcher,33,4,"18",43,0));
                foods.add(new FoodBean(j+"00"+(i+9),""+j,"老母鸡煲汤"+j,R.mipmap.ic_launcher,33,4,"18",6,0));
                foods.add(new FoodBean(j+"00"+(i+10),""+j,"小鸡炖蘑菇"+j,R.mipmap.ic_launcher,33,4,"38",98,0));
                foods.add(new FoodBean(j+"00"+(i+11),""+j,"番茄炒蛋"+j,R.mipmap.ic_launcher,33,4,"78",48,0));
                foods.add(new FoodBean(j+"00"+(i+12),""+j,"猪扒"+j,R.mipmap.ic_launcher,33,4,"18",40,0));
                foods.add(new FoodBean(j+"00"+(i+13),""+j,"金针菇炒米饭"+j,R.mipmap.ic_launcher,33,4,"28",41,0));

            }

            List<GoodsMenu> shopMenu=new ArrayList<>();

            shopMenu.add(new GoodsMenu(1,"热销榜"));
            shopMenu.add(new GoodsMenu(2,"冷菜"));
            shopMenu.add(new GoodsMenu(3,"招牌菜"));
            shopMenu.add(new GoodsMenu(4,"热菜"));
            shopMenu.add(new GoodsMenu(5,"主食"));
            shopMenu.add(new GoodsMenu(6,"汤类"));

            mData.add(new ShopBean("110",shopName[i],foods, shopIcon[i],"4","深圳联谊大厦121","味道很不错，你值得拥有","4","18376611549",shopMenu,100+i,"35分钟","早10:00-晚21:00"));


        }


        return mData;
    }

}
