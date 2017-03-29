package www.formssi.goodtaste.fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.home.GoodsDetailActivity;
import www.formssi.goodtaste.adapter.HomeCategroyAdapter;
import www.formssi.goodtaste.adapter.HomeGoodsBaseAdapter;
import www.formssi.goodtaste.adapter.MyPagerAdapter;
import www.formssi.goodtaste.bean.HomeGoodsCategroyBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.utils.GoodsDataUtils;
import www.formssi.goodtaste.widget.Indicator;
import www.formssi.goodtaste.widget.MyOnPageChangeListener;

/***
 * HomeFragment
 */
public class HomeFragment extends Fragment {
    private View mView;
    private Indicator IndicatorHome;
    private List<HomeGoodsCategroyBean> good_Data1;
    private List<HomeGoodsCategroyBean> good_Data2;
    private LayoutInflater mInflater;
    private ViewPager vpHomeCateGroy;
    private List<View> views;
    private ListView lvHomeGoods;
    private List<ShopBean> mDatas;
    private SwipeRefreshLayout srlHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mInflater = LayoutInflater.from(getActivity());
        initData();
        initView();
        initListener();
        return mView;
    }

    private void initData() {
        good_Data1 = new ArrayList<>();
        good_Data2 = new ArrayList<>();
        String goods_Name[] = getResources().getStringArray(R.array.home_categroy_IconName);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.home_categroy_Icon);
        for (int i = 0; i < goods_Name.length; i++) {
            if (i < 8) {
                good_Data1.add(new HomeGoodsCategroyBean(goods_Name[i], typedArray.getResourceId(i, 0)));
            } else {
                good_Data2.add(new HomeGoodsCategroyBean(goods_Name[i], typedArray.getResourceId(i, 0)));
            }
        }
        mDatas = GoodsDataUtils.GetShop();
    }

    private void initView() {
        lvHomeGoods = (ListView) mView.findViewById(R.id.lvHomeGoods);
        srlHome= (SwipeRefreshLayout) mView.findViewById(R.id.srlHome);
        View Layout_View = mInflater.inflate(R.layout.home_goods_categroy, null);
        vpHomeCateGroy = (ViewPager) Layout_View.findViewById(R.id.vpHomeCategroy);
        IndicatorHome = (Indicator) Layout_View.findViewById(R.id.IndicatorHome);
        View mGrid1 = mInflater.inflate(R.layout.home_goods_gridview, null);
        GridView gridView_one = (GridView) mGrid1.findViewById(R.id.gvHomeCategroy);
        gridView_one.setAdapter(new HomeCategroyAdapter(good_Data1, getActivity()));
        View mGrid2 = mInflater.inflate(R.layout.home_goods_gridview, null);
        GridView gridView_two = (GridView) mGrid2.findViewById(R.id.gvHomeCategroy);
        gridView_two.setAdapter(new HomeCategroyAdapter(good_Data2, getActivity()));
        //设置下拉刷新按钮的样式
        srlHome.setProgressViewOffset(true,0,50);
        srlHome.setColorSchemeResources(R.color.appColor);
        views = new ArrayList<>();
        views.add(mGrid1);
        views.add(mGrid2);
        vpHomeCateGroy.setAdapter(new MyPagerAdapter(views));
        lvHomeGoods.addHeaderView(Layout_View);
        lvHomeGoods.setAdapter(new HomeGoodsBaseAdapter(mDatas, getActivity()));
    }

    private void initListener() {
        /**商品分类的ViewPager滑动监听，这里面进行了抽取掉两个不常用的方法**/
        vpHomeCateGroy.setOnPageChangeListener(new MyOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                IndicatorHome.setIndicator(position, positionOffset);
            }
        });

        /**ListView Item的点击事件**/
        lvHomeGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("ShopBean", mDatas.get(position - 1));
                startActivity(intent);
            }
        });

        /*****设置SwipeRefreshLayout的下拉刷新监听事件*/
        srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "数据刷新成功", Toast.LENGTH_SHORT).show();
                        srlHome.setRefreshing(false);
                    }
                },2000);

            }
        });

    }
}
