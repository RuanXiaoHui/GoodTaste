package www.formssi.goodtaste.activity.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.activity.mine.LoginActivity;
import www.formssi.goodtaste.activity.pay.ConfirmOrderActivity;
import www.formssi.goodtaste.adapter.GoodsCarAdapter;
import www.formssi.goodtaste.adapter.ShopDataAdapter;
import www.formssi.goodtaste.adapter.ShopMenuAdapter;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.GoodsMenu;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.utils.ContextUtil;
import www.formssi.goodtaste.widget.CustomScrollView;

public class GoodsDetailActivity extends BaseActivity implements CustomScrollView.ScrollViewListener {

    private CustomScrollView scLayoutView;   //滑动的ScrollView
    private RelativeLayout rlView;           //店铺背景布局
    private ListView lvLeftMenu;             //左侧菜单的ListView
    private ListView lvRightFoods;           //右侧的商品列表的ListView
    private ShopBean mShopBean;              //商店的Bean
    private List<GoodsMenu> mLeftMenu;       //左侧菜单的ListView数据源
    private List<FoodBean> mFoodBean;        //右侧商品的ListView数据源
    private List<FoodBean> mRefreshBean;     //右侧筛选商品的ListView数据源
    private ImageView iv_backTitleBar_back;  //顶部的返回按钮
    private RelativeLayout rltToolbar;        //顶部的TitleBar布局
    private TextView tv_backTitleBar_title;  //顶部Title的标题
    private TextView tv_backTitleBar_center_title;
    private ImageView ivCar;                 //购物车的img
    private TextView ivShopTime;             //配送时间
    private TextView ivShopDesc;             //店铺描述
    private TextView ivShopBusinessHours;    //营业时间
    private Button btnSubmitOrder;           //下单按钮
    private ImageView ivGoodsPhone;             //商家电话
    private TextView tvGoodsMoney;           //商品钱数
    private TextView tvShopMoney;            //配送费
    private ImageView ivGoodsInfo;               //商家信息图片展示
    private ShopDataAdapter mShopDataAdapter;//商店商品的适配器
    private List<FoodBean> mFoodConfirm;     //商店购物车
    private int Money = 0;                     //购买的总钱数
    private int mHeight = 0;                   //顶部背景图片的高度
    private Animation mCarAnimation = null;    //购物车的添加购物动画
    private SharedPreferences mContextSharedPreferences;
    private PopupWindow mPopWind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        scLayoutView = (CustomScrollView) findViewById(R.id.scLayoutView);
        rlView = (RelativeLayout) findViewById(R.id.rlView);
        rltToolbar = (RelativeLayout) findViewById(R.id.rltToolbar);
        tv_backTitleBar_title = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tv_backTitleBar_center_title = (TextView) findViewById(R.id.tv_backTitlebar_center_title);
        ivGoodsInfo = (ImageView) findViewById(R.id.ivGoodsInfo);
        lvLeftMenu = (ListView) findViewById(R.id.lvLeftMenu);
        lvRightFoods = (ListView) findViewById(R.id.lvRightFoods);
        iv_backTitleBar_back = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivShopTime = (TextView) findViewById(R.id.ivShopTime);
        ivShopDesc = (TextView) findViewById(R.id.ivShopDesc);
        ivShopBusinessHours = (TextView) findViewById(R.id.ivShopBusinessHours);
        tvGoodsMoney = (TextView) findViewById(R.id.tvGoodsMoney);
        tvShopMoney = (TextView) findViewById(R.id.tvShopMoney);
        ivCar = (ImageView) findViewById(R.id.ivCar);
        btnSubmitOrder = (Button) findViewById(R.id.btnSubmintOrder);
        ivGoodsPhone = (ImageView) findViewById(R.id.ivGoodsPhone);
        mFoodConfirm = new ArrayList<>();
        scLayoutView.smoothScrollTo(0, 0);
        btnSubmitOrder.setEnabled(false);
        ivCar.setEnabled(false);
        initScrollView();
    }

    @Override
    protected void initData() {
        mContextSharedPreferences = getSharedPreferences(ConstantConfig.SP_NAME, MODE_PRIVATE);
        mRefreshBean = new ArrayList<>();
        Intent intent = getIntent();
        mShopBean = (ShopBean) intent.getSerializableExtra("ShopBean");
        mLeftMenu = mShopBean.getShopMenu();
        mFoodBean = mShopBean.getFoods();
        tv_backTitleBar_title.setText(mShopBean.getShopName());
        ivGoodsInfo.setBackgroundResource(R.mipmap.icon_goods);
        ivShopTime.setText(getString(R.string.activity_goods_avg_time) + mShopBean.getDistributionTime() + "");
        ivShopDesc.setText(getString(R.string.activity_goods_desc) + mShopBean.getShopDesc() + "");
        ivShopBusinessHours.setText(getString(R.string.activity_goods_business_time) + mShopBean.getShopBusinessHours() + "");
        tvShopMoney.setText(getString(R.string.activity_goods_distribution_money) + mShopBean.getShopMoney() + "元");
        for (int i = 0; i < mFoodBean.size(); i++) {
            if (mFoodBean.get(i).getGoodsType().equals("1")) {
                mRefreshBean.add(mFoodBean.get(i));
            }
        }
        mCarAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_goodsdetail_car);
    }

    @Override
    protected void initListener() {
        lvLeftMenu.setAdapter(new ShopMenuAdapter(mLeftMenu, this));
        mShopDataAdapter = new ShopDataAdapter(mRefreshBean, this);
        lvRightFoods.setAdapter(mShopDataAdapter);
        setListViewHeightBasedOnChildren(lvRightFoods);

        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsMenu menu = mLeftMenu.get(position);
                int menuId = menu.getId();
                List<FoodBean> data = new ArrayList<>();
                for (int i = 0; i < mFoodBean.size(); i++) {
                    if (mFoodBean.get(i).getGoodsType().equals(String.valueOf(menuId))) {
                        data.add(mFoodBean.get(i));
                    }
                }
                mShopDataAdapter.addData(data);
            }
        });

        mShopDataAdapter.setOnExtralClickListener(new ShopDataAdapter.OnExtralClickListener() {
            @Override
            public void onClickMoney(int vue, Map<String, FoodBean> beans) {
                tvGoodsMoney.setText("￥" + vue + "元");
                //判断一下，如果金额比之前的大，也就是点击了添加按钮，那么就会启动动画
                if (vue > Money) {
                    ivCar.startAnimation(mCarAnimation);
                }
                Money = vue;
                if (vue == 0) {
                    btnSubmitOrder.setEnabled(false);
                    ivCar.setEnabled(false);
                    btnSubmitOrder.setTextColor(getResources().getColor(R.color.gray));
                } else {
                    btnSubmitOrder.setEnabled(true);
                    ivCar.setEnabled(true);
                    btnSubmitOrder.setTextColor(getResources().getColor(R.color.white));
                }
                mFoodConfirm.clear();
                for (Map.Entry<String, FoodBean> bean : beans.entrySet()) {
                    mFoodConfirm.add(bean.getValue());
                }
            }
        });

        //提交订单
        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLogin = mContextSharedPreferences.getBoolean("login", false);
                if (isLogin) {
                    Intent intent = new Intent(GoodsDetailActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("ShopBeans", mShopBean);
                    intent.putExtra("foodBeans", (Serializable) mFoodConfirm);
                    intent.putExtra("CountMoney", Money);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        //返回
        iv_backTitleBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //拨打商家电话
        ivGoodsPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AlertDialog.Builder builder= new AlertDialog.Builder(GoodsDetailActivity.this);
                builder.setMessage("确定拨打商家电话嘛？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (mShopBean.getShopPhone()!=null){
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mShopBean.getShopPhone()));
                                    startActivity(intent);
                                }
                            }
                        });
                builder.show();
            }
        });

        ivCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View mView = getLayoutInflater().inflate(R.layout.layout_goods_car, null);
                ListView mCarListView = (ListView) mView.findViewById(R.id.lvGoodsCar);
                mCarListView.setAdapter(new GoodsCarAdapter(mFoodConfirm, GoodsDetailActivity.this));
                setPopWinListViewHeight(mCarListView);
                mPopWind = new PopupWindow(GoodsDetailActivity.this);
                mPopWind.setContentView(mView);
                mPopWind.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopWind.setFocusable(true);
                mPopWind.setOutsideTouchable(true);
                mPopWind.setBackgroundDrawable(new ColorDrawable(0));
                //显示在底部bar的上方
                RelativeLayout rltLayout = (RelativeLayout) findViewById(R.id.rltGoodsPay);
                mView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth = mView.getMeasuredWidth();
                int popupHeight = mView.getMeasuredHeight();
                int[] location = new int[2];
                rltLayout.getLocationOnScreen(location);
                mPopWind.showAtLocation(rltLayout, Gravity.NO_GRAVITY, (location[0] + rltLayout.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
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
                mHeight = rlView.getHeight();
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void OnScrollViewChangeListener(int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            rltToolbar.setBackgroundColor(Color.argb(255, 0, 149, 254));
            tv_backTitleBar_title.setTextColor(Color.argb(255, 255, 255, 255));
            tv_backTitleBar_title.setVisibility(View.VISIBLE);
            iv_backTitleBar_back.setVisibility(View.VISIBLE);
            tv_backTitleBar_center_title.setVisibility(View.GONE);
        } else if (y > 5 && y <= mHeight / 3) {
            double bf = ((y * 1.0) / mHeight);
            double alpha = (bf * 255) * 3;
            tv_backTitleBar_title.setVisibility(View.VISIBLE);
            tv_backTitleBar_center_title.setVisibility(View.GONE);
            tv_backTitleBar_title.setTextColor(Color.argb(255 - (int) alpha, 255, 255, 255));
            tv_backTitleBar_title.setText(mShopBean.getShopName());
            iv_backTitleBar_back.setVisibility(View.VISIBLE);
        } else if (y > mHeight / 2 && y < mHeight) {
            double bf = ((y * 1.0) / mHeight);
            double alpha = (bf * 255);
            tv_backTitleBar_title.setVisibility(View.INVISIBLE);
            tv_backTitleBar_center_title.setVisibility(View.VISIBLE);
            tv_backTitleBar_center_title.setTextColor(Color.argb((int) alpha, 255, 255, 255));
            tv_backTitleBar_center_title.setText(mShopBean.getShopName());
            iv_backTitleBar_back.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ContextUtil.getInstance().isFinishGoodsActivity()) {
            ContextUtil.getInstance().setFinishGoodsActivity(false);
            GoodsDetailActivity.this.finish();
        }
    }

    //计算PopWindow中ListView的高，当高度超过四百的时候，设置最大高度为400
    public void setPopWinListViewHeight(ListView listView) {
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        if (params.height > 400) {
            params.height = 400;
        }
        listView.setLayoutParams(params);
    }
}
