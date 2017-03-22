package www.formssi.goodtaste.bean;

/**
 * Created by Administrator on 2017/3/15.
 * 邮箱：769006026@qq.com
 * project: GoodTaste
 */
public class Tab {

    private int TabIcon;
    private int TabName;
    private Class Fragments;

    public Tab(int tabIcon, int tabName, Class fragments) {
        TabIcon = tabIcon;
        TabName = tabName;
        Fragments = fragments;
    }

    public int getTabIcon() {
        return TabIcon;
    }

    public void setTabIcon(int tabIcon) {
        TabIcon = tabIcon;
    }

    public int getTabName() {
        return TabName;
    }

    public void setTabName(int tabName) {
        TabName = tabName;
    }

    public Class getFragments() {
        return Fragments;
    }

    public void setFragments(Class fragments) {
        Fragments = fragments;
    }
}
