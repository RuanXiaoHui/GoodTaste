package www.formssi.goodtaste.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.Tab;
import www.formssi.goodtaste.fragment.HomeFragment;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.fragment.OrderFragment;

public class MainActivity extends FragmentActivity implements OrderFragment.MeOnClickListener{

    private FragmentTabHost mTabhost;           //FragmentTabHost控件
    private int TabIcon[]={R.drawable.selector_tab_home,R.drawable.selector_tab_order,
            R.drawable.selector_tab_mine};      //主框架底部的三个按钮控件
    private List<Tab> mTabs=new ArrayList<>();  //承载三个控件的实体Bean
    private LayoutInflater mInflate;            //获取Layout的View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTabs();
        
    }


    protected  void initView() {
        mTabhost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabhost.setup(this,getSupportFragmentManager(),R.id.realcontent);
        mInflate=getLayoutInflater();
    }

    /***
     * 初始化Tab
     */
    private void initTabs() {
        Tab tabHome=new Tab(TabIcon[0],R.string.activity_main_tabhome,HomeFragment.class);
        Tab tabHost=new Tab(TabIcon[1],R.string.activity_main_taborder,OrderFragment.class);
        Tab tabCatgroy=new Tab(TabIcon[2],R.string.activity_main_tabmine,MineFragment.class);
        mTabs.add(tabHome);
        mTabs.add(tabHost);
        mTabs.add(tabCatgroy);
        for (Tab tab:mTabs) {
            TabHost.TabSpec tabSpec= mTabhost.newTabSpec(getString(tab.getTabName()));
            tabSpec.setIndicator(initIndicator(tab));
            mTabhost.addTab(tabSpec,tab.getFragments(),null);
        }
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }

    /**
     * 设置一个Tab的Layout View
     * @param tab
     * @return
     */
    private View initIndicator(Tab tab) {
        View mView=mInflate.inflate(R.layout.main_tab_layout,null);
        ImageView ivIconTab= (ImageView) mView.findViewById(R.id.ivIconTab);
        TextView tvIconName= (TextView) mView.findViewById(R.id.tvIconName);
        ivIconTab.setImageResource(tab.getTabIcon());
        tvIconName.setText(tab.getTabName());
        return  mView;
    }

    /**
     * 回调orderFragment里面的监听事件
     */
    @Override
    public void onBtnGoSingleClick() {
        mTabhost.setCurrentTab(0);
    }
}
