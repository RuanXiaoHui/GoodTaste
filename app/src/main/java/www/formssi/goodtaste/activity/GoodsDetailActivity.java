package www.formssi.goodtaste.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;

public class GoodsDetailActivity extends BaseActivity {
    private ListView lvLeftMenu;
    private ListView lvRightFoods;
    private List<Map<String,String>> mLeftData;


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
    }

    private void initData() {
        mLeftData=new ArrayList<>();
        for (int i = 0; i <17 ; i++) {
            Map<String,String> map=new HashMap<>();
            map.put("menu","菜单"+i);
            mLeftData.add(map);
        }
    }
    private void initListener() {
        SimpleAdapter sim=new SimpleAdapter(this,mLeftData,R.layout.item_goods_leftmenu,new String[]{"menu"},new int[]{R.id.tv});
        lvLeftMenu.setAdapter(sim);
    }
}
