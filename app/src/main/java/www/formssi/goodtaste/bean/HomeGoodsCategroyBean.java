package www.formssi.goodtaste.bean;

/**
 * Created by Administrator on 2017/3/15.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class HomeGoodsCategroyBean {

   private  String iconName;
   private  int icon;

    public HomeGoodsCategroyBean(String iconName, int icon) {
        this.iconName = iconName;
        this.icon = icon;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
