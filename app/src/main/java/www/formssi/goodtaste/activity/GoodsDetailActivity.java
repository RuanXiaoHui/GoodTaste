package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.adapter.ShopDataAdapter;
import www.formssi.goodtaste.adapter.ShopMenuAdapter;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.GoodsMenu;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.widget.CustomScrollView;

public class GoodsDetailActivity extends BaseActivity implements CustomScrollView.ScrollViewListener {

    private CustomScrollView scLayoutView;
    private RelativeLayout rlView;
    private ListView lvLeftMenu;
    private ListView lvRightFoods;
    private ShopBean mShopBean;
    private List<GoodsMenu> mLeftMenu;
    private List<FoodBean> mFoodBean;
    private List<FoodBean> mRefreshBean;
    private ImageView iv_backTitlebar_back;
    private RelativeLayout rlToobar;
    private TextView tv_backTitlebar_title;
    private ImageView ivCar;                   //购物车的img
    private ImageView ivShopimg;               //商店图片
    private TextView ivShopTime;              //配送时间
    private TextView ivShopDesc;              //店铺描述
    private TextView ivShopBusinessHours;    //营业时间
    private Button   btnSubmintOrder;        //下单按钮
    private TextView tvGoodsMoney;           //商品钱数
    private TextView tvShopMoney;            //配送费
    private TextView tv_backTitlebar_center_title;   //商店标题
    private ShopDataAdapter Adapter;
    private List<FoodBean> mFoodbConfirm;
    private int Money=0;
    private int mHeight=0;

    private Animation mCarAnimaton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        initScrollView();
        initData();
        initListener();

    }

    private void initView() {
        scLayoutView= (CustomScrollView) findViewById(R.id.scLayoutView);
        rlView= (RelativeLayout) findViewById(R.id.rlView);
        rlToobar= (RelativeLayout) findViewById(R.id.rlToobar);
        tv_backTitlebar_title= (TextView) findViewById(R.id.tv_backTitlebar_title);

        lvLeftMenu= (ListView) findViewById(R.id.lvLeftMenu);
        lvRightFoods= (ListView) findViewById(R.id.lvRightFoods);
        ivShopimg= (ImageView) findViewById(R.id.ivShopimg);
        iv_backTitlebar_back= (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivShopTime= (TextView) findViewById(R.id.ivShopTime);
        ivShopDesc= (TextView) findViewById(R.id.ivShopDesc);
        ivShopBusinessHours= (TextView) findViewById(R.id.ivShopBusinessHours);
        tvGoodsMoney= (TextView) findViewById(R.id.tvGoodsMoney);
        tvShopMoney= (TextView) findViewById(R.id.tvShopMoney);
        ivCar= (ImageView) findViewById(R.id.ivCar);
        btnSubmintOrder= (Button) findViewById(R.id.btnSubmintOrder);
        mFoodbConfirm=new ArrayList<>();
        scLayoutView.smoothScrollTo(0, 0);
        btnSubmintOrder.setEnabled(false);
        ivCar.setEnabled(false);
    }

    private void initData() {
        mRefreshBean=new ArrayList<>();
        Intent intent=getIntent();
        mShopBean= (ShopBean) intent.getSerializableExtra("ShopBean");
        mLeftMenu=mShopBean.getShopMenu();
        mFoodBean=mShopBean.getFoods();
        for (int i = 0; i <mFoodBean.size() ; i++) {
            if (mFoodBean.get(i).getGoodsType().equals("1")){
                mRefreshBean.add(mFoodBean.get(i));
                System.out.println(mFoodBean.get(i).getGoodsName());
            }
        }
        mCarAnimaton= AnimationUtils.loadAnimation(this,R.anim.scale_goodsdetail_car);
    }

    private void initListener() {
        ivShopTime.setText("平均配送时间:"+mShopBean.getShopBusinessHours());
        ivShopDesc.setText("店家描述："+mShopBean.getShopDesc());
        ivShopBusinessHours.setText("营业时间："+mShopBean.getShopBusinessHours());
        tvShopMoney.setText("另需配送费"+mShopBean.getShopMoney()+"元");
        lvLeftMenu.setAdapter(new ShopMenuAdapter(mLeftMenu,this));
        Adapter= new ShopDataAdapter(mRefreshBean,this);
        lvRightFoods.setAdapter(Adapter);
        setListViewHeightBasedOnChildren(lvRightFoods);

        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsMenu menu= mLeftMenu.get(position);
                int  menuId=menu.getId();
                List<FoodBean> data=new ArrayList<FoodBean>();
                for (int i = 0; i <mFoodBean.size() ; i++) {
                    if (mFoodBean.get(i).getGoodsType().equals(String.valueOf(menuId))){
                        data.add(mFoodBean.get(i));
                    }
                }
                Adapter.addData(data);
            }
        });

        Adapter.setOnExtralClickListener(new ShopDataAdapter.OnExtralClickListener() {
            @Override
            public void onClickMoney(int vue, Map<String, FoodBean> beans) {
                tvGoodsMoney.setText("￥"+vue+"元");

                //判断一下，如果金额比之前的大，也就是点击了添加按钮，那么就会启动动画
                if (vue>Money){
                    ivCar.startAnimation(mCarAnimaton);
                }

                Money=vue;
                if (vue==0){
                    btnSubmintOrder.setEnabled(false);
                    ivCar.setEnabled(false);
                    btnSubmintOrder.setTextColor(getResources().getColor(R.color.gray));
                }else{
                    btnSubmintOrder.setEnabled(true);
                    ivCar.setEnabled(true);
                    btnSubmintOrder.setTextColor(getResources().getColor(R.color.white));
                }
                mFoodbConfirm.clear();
                for (Map.Entry<String ,FoodBean> bean:beans.entrySet()) {
                    mFoodbConfirm.add(bean.getValue());
                }
            }
        });

        //提交订单
        btnSubmintOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsDetailActivity.this,ConfirmOrderActivity.class);
                intent.putExtra("ShopBeans",mShopBean);
                intent.putExtra("foodBeans",(Serializable) mFoodbConfirm);
                intent.putExtra("CountMoney",Money);
                startActivity(intent);
            }
        });

        //返回
        iv_backTitlebar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        scLayoutView.setOnScrollView(new CustomScrollView.ScrollViewListener() {
            @Override
            public void OnScrollViewChangeListener(int x, int y, int oldx, int oldy) {

            }
        });

    }


    //初始化ScrollView，并测量顶部背景图高度
    private void initScrollView() {
        ViewTreeObserver vto = rlView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mHeight=rlView.getHeight();
                scLayoutView.setOnScrollView(GoodsDetailActivity.this);
            }
        });

    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void OnScrollViewChangeListener(int x, int y, int oldx, int oldy) {

        if (y<=0){
            rlToobar.setBackgroundColor(Color.argb(120,0, 149, 254));
            tv_backTitlebar_title.setVisibility(View.GONE);
        }else if(y>0&&y<=mHeight/3){
            tv_backTitlebar_title.setVisibility(View.VISIBLE);
            tv_backTitlebar_title.setTextColor(Color.argb(100,255,255,255));
            tv_backTitlebar_title.setText(mShopBean.getShopName());
            rlToobar.setBackgroundColor(Color.argb(120,0, 149, 254));

        }else if (y>mHeight/3&&y<mHeight){

            double bf=((y*1.0)/mHeight);
            double alpha=(bf*255);
            tv_backTitlebar_title.setVisibility(View.VISIBLE);
            tv_backTitlebar_title.setTextColor(Color.argb((int)alpha,255,255,255));
            tv_backTitlebar_title.setText(mShopBean.getShopName());
            rlToobar.setBackgroundColor(Color.argb((int)alpha,0, 149, 254));

        }

    }
}
