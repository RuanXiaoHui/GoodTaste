package www.formssi.goodtaste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

public class GoodsDetailActivity extends BaseActivity {
    private ListView lvLeftMenu;
    private ListView lvRightFoods;
    private ShopBean mShopBean;
    private List<GoodsMenu> mLeftMenu;
    private List<FoodBean> mFoodBean;
    private List<FoodBean> mRefreshBean;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        lvLeftMenu= (ListView) findViewById(R.id.lvLeftMenu);
        lvRightFoods= (ListView) findViewById(R.id.lvRightFoods);
        ivShopimg= (ImageView) findViewById(R.id.ivShopimg);
        ivShopTime= (TextView) findViewById(R.id.ivShopTime);
        ivShopDesc= (TextView) findViewById(R.id.ivShopDesc);
        ivShopBusinessHours= (TextView) findViewById(R.id.ivShopBusinessHours);
        tvGoodsMoney= (TextView) findViewById(R.id.tvGoodsMoney);
        tvShopMoney= (TextView) findViewById(R.id.tvShopMoney);
        btnSubmintOrder= (Button) findViewById(R.id.btnSubmintOrder);
        tv_backTitlebar_center_title= (TextView) findViewById(R.id.tv_backTitlebar_center_title);
        mFoodbConfirm=new ArrayList<>();
        btnSubmintOrder.setEnabled(false);
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


    }
    private void initListener() {
        tv_backTitlebar_center_title.setText(mShopBean.getShopName());
        ivShopTime.setText("平均配送时间:"+mShopBean.getShopBusinessHours());
        ivShopDesc.setText("店家描述："+mShopBean.getShopDesc());
        ivShopBusinessHours.setText("营业时间："+mShopBean.getShopBusinessHours());
        lvLeftMenu.setAdapter(new ShopMenuAdapter(mLeftMenu,this));
        Adapter= new ShopDataAdapter(mRefreshBean,this);
        lvRightFoods.setAdapter(Adapter);

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
                if (vue==0){
                    btnSubmintOrder.setEnabled(false);
                }else{
                    btnSubmintOrder.setEnabled(true);
                }

                for (Map.Entry<String ,FoodBean> bean:beans.entrySet()) {
                    mFoodbConfirm.add(bean.getValue());
                }
            }
        });
        btnSubmintOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsDetailActivity.this,ConfirmOrderActivity.class);
                intent.putExtra("ShopBeans",mShopBean);
                intent.putExtra("foodBeans",(Serializable) mFoodbConfirm);
                startActivity(intent);
            }
        });

    }
}
