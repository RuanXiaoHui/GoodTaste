package www.formssi.goodtaste.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/16.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 * 商店详情左边菜单实体类
 */
public class GoodsMenu implements Serializable {

    private int id;        //菜单ID
    private String Name;   //菜单名字

    public GoodsMenu(int id, String name) {
        this.id = id;
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
