package www.formssi.goodtaste.bean;

/**
 * Created by qkldev003 on 2017/3/16.
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
