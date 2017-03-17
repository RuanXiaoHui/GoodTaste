package www.formssi.goodtaste.fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.GoodsDetailActivity;
import www.formssi.goodtaste.adapter.HomeCategroyAdapter;
import www.formssi.goodtaste.adapter.HomeGoodsBaseAdapter;
import www.formssi.goodtaste.adapter.MyPagerAdapter;
import www.formssi.goodtaste.bean.HomeGoodsCategroyBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.utils.GoodsDataUtils;
import www.formssi.goodtaste.widget.Indicator;
import www.formssi.goodtaste.widget.MyOnPageChangeListener;

/**
 * A simple {@link Fragment} subclass.
 * 主页面
 */
public class HomeFragment extends Fragment {
    private View mView;
    private Indicator IndicatorHome;

    private List<HomeGoodsCategroyBean> good_Data1;
    private List<HomeGoodsCategroyBean> good_Data2;
    private LayoutInflater mInflater;
    private ViewPager vpHomeCategroy;
    private List<View> views;
    private ListView lvHomeGoods;
    private List<ShopBean> mDatas;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_home, container, false);
        mInflater=LayoutInflater.from(getActivity());
        initData();
        initView();
        initListener();

        return mView;
    }

    private void initData() {
        good_Data1=new ArrayList<>();
        good_Data2=new ArrayList<>();
        String goods_Name[]=getResources().getStringArray(R.array.home_categroy_IconName);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.home_categroy_Icon);
        for (int i = 0; i <goods_Name.length ; i++) {
            if (i<8){
                good_Data1.add(new HomeGoodsCategroyBean(goods_Name[i],typedArray.getResourceId(i,0)));
            }else{
                good_Data2.add(new HomeGoodsCategroyBean(goods_Name[i],typedArray.getResourceId(i,0)));
            }
        }
        mDatas= GoodsDataUtils.GetShop();


    }

    private void initView() {
        lvHomeGoods= (ListView) mView.findViewById(R.id.lvHomeGoods);
        View Layout_View=mInflater.inflate(R.layout.home_goods_categroy,null);

        vpHomeCategroy = (ViewPager) Layout_View.findViewById(R.id.vpHomeCategroy);
        IndicatorHome= (Indicator) Layout_View.findViewById(R.id.IndicatorHome);

        View mGrid1 = mInflater.inflate(R.layout.home_goods_gridview, null);
        GridView grigView_one = (GridView) mGrid1.findViewById(R.id.gvHomeCategroy);
        grigView_one.setAdapter(new HomeCategroyAdapter(good_Data1,getActivity()));

        View mGrid2 = mInflater.inflate(R.layout.home_goods_gridview, null);
        GridView grigView_two = (GridView) mGrid2.findViewById(R.id.gvHomeCategroy);
        grigView_two.setAdapter(new HomeCategroyAdapter(good_Data2,getActivity()));

        views = new ArrayList<View>();
        views.add(mGrid1);
        views.add(mGrid2);

        vpHomeCategroy.setAdapter(new MyPagerAdapter(views));
        lvHomeGoods.addHeaderView(Layout_View);

        lvHomeGoods.setAdapter(new HomeGoodsBaseAdapter(mDatas,getActivity()));


    }


    private void initListener() {
        vpHomeCategroy.setOnPageChangeListener(new MyOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                IndicatorHome.setIndicator(position,positionOffset);
            }
        });

        lvHomeGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("ShopBean",mDatas.get(position-1));
                startActivity(intent);
            }
        });
    }
}
