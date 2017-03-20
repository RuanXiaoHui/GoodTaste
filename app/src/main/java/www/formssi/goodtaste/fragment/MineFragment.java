package www.formssi.goodtaste.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.PersonalActivity;
import www.formssi.goodtaste.activity.ReceiveAddressActivity;
import www.formssi.goodtaste.activity.SettingActivity;
import www.formssi.goodtaste.bean.UserBean;

import static android.R.attr.action;

/**
 * A simple {@link Fragment} subclass.
 * 我的
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    public static final String MY_ACTION = "com.formssi.mine";

    private View mView;
    private ImageView ivSetting; //设置图标
    private RelativeLayout rlPersonal; //个人中心
    private TextView tvAddress; //收货地址
    private TextView tvUserName; //用户名
    private TextView tvPhoneNum; //电话号码
    private BroadcastReceiver receive;
    private ImageView ivHeadPicture;
    private UserBean userBean;

    private static final String TAG = "MineFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: ");
        userBean = new UserBean();
        receive = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(MY_ACTION);
        getContext().registerReceiver(receive, intentFilter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        fillData();
        return mView;
    }

    private void fillData() {
        String headProtrait = userBean.getHeadProtrait();
        if (headProtrait != null) {
            Picasso.with(getContext())
                    .load(Uri.parse(headProtrait))
                    .into(ivHeadPicture);
        }
        String userName = userBean.getUserName();
        if (userName != null) {
            tvUserName.setText(userName);
        }
    }

    private void initView() {
        ivSetting = (ImageView) mView.findViewById(R.id.iv_setting);
        ivHeadPicture = (ImageView) mView.findViewById(R.id.iv_mine_headprotrait);
        rlPersonal = (RelativeLayout) mView.findViewById(R.id.rl_mine_personal);
        tvAddress = (TextView) mView.findViewById(R.id.tv_mine_address);
        tvUserName = (TextView) mView.findViewById(R.id.tv_mine_username);
        tvPhoneNum = (TextView) mView.findViewById(R.id.tv_mine_telephone);

        ivSetting.setOnClickListener(this);
        rlPersonal.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mine_personal:
                intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra("user", userBean);
                startActivity(intent);
                break;
            case R.id.tv_mine_address:
                intent = new Intent(getActivity(), ReceiveAddressActivity.class);
                startActivity(intent);
                break;

        }
    }

    public class MyReceiver extends BroadcastReceiver {

        public static final String CODE = "code";// code 的 key
        public static final String RESULT = "result"; // 结果的key

        public static final int TYPE_CAMERA = 1;//xianji
        public static final int TYPE_ALBUM = 2;//xiangce
        public static final int TYPE_USERNAME = 3;//xiangce

        @Override
        public void onReceive(Context context, Intent intent) {
            int codeType = intent.getIntExtra(CODE, -1);
            if (codeType == TYPE_ALBUM) {
                Uri uri = intent.getParcelableExtra(RESULT);
                userBean.setHeadProtrait(uri.toString());
                Picasso.with(getContext())
                        .load(uri)
                        .into(ivHeadPicture);
//                ivHeadPicture.setImageURI(uri);
            } else if (codeType == TYPE_CAMERA) {
                File file = (File) intent.getSerializableExtra(RESULT);
                Uri uri = Uri.fromFile(file);
                userBean.setHeadProtrait(uri.toString());
                Picasso.with(getContext())
                        .load(uri)
                        .into(ivHeadPicture);
//                ivHeadPicture.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            } else if (codeType == TYPE_USERNAME) {
                String userName = intent.getStringExtra(RESULT);
                userBean.setUserName(userName);
                tvUserName.setText(userName);
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        getContext().unregisterReceiver(receive);
        super.onDestroy();
    }
}
