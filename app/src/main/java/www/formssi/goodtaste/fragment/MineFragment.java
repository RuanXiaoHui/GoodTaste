package www.formssi.goodtaste.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import www.formssi.goodtaste.activity.LoginActivity;
import www.formssi.goodtaste.activity.PersonalActivity;
import www.formssi.goodtaste.activity.ReceiveAddressActivity;
import www.formssi.goodtaste.activity.SettingActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.ImageLoader;
import www.formssi.goodtaste.utils.SPUtils;
import www.formssi.goodtaste.utils.StringUtils;

import static android.R.attr.action;
import static android.R.attr.imageButtonStyle;

/**
 * A simple {@link Fragment} subclass.
 * 我的
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    public static final String MY_ACTION = "com.formssi.mine";

    private ImageView ivSetting; //设置图标
    private RelativeLayout rlPersonal; //个人中心
    private TextView tvAddress; //收货地址
    private TextView tvUserName; //用户名
    private TextView tvPhoneNum; //电话号码
    private ImageView ivHeadPicture; // 用户头像

    private Context mContext;
    private UserBean userBean;
    private boolean hasLogin; // 判断用户是否登录
    private String telephone;
    private BroadcastReceiver mReceive; //接收用户信息改变的广播

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceive = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(MY_ACTION);
        getContext().registerReceiver(mReceive, intentFilter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        initDate();
        validateView();
    }

    private void initView(View view) {
        ivSetting = (ImageView) view.findViewById(R.id.iv_setting);
        ivHeadPicture = (ImageView) view.findViewById(R.id.iv_mine_headprotrait);
        rlPersonal = (RelativeLayout) view.findViewById(R.id.rl_mine_personal);
        tvAddress = (TextView) view.findViewById(R.id.tv_mine_address);
        tvUserName = (TextView) view.findViewById(R.id.tv_mine_username);
        tvPhoneNum = (TextView) view.findViewById(R.id.tv_mine_telephone);
    }

    private void initDate() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        hasLogin = sharedPreferences.getBoolean("login", false);
        telephone = SPUtils.getTel(getContext());
    }

    private void initListener() {
        ivSetting.setOnClickListener(this);
        rlPersonal.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
    }

    private void validateView() {
        if (hasLogin) {
            getUser();
            if (userBean != null) {
                String headProtrait = userBean.getHeadProtrait();
                ImageLoader.display(mContext,headProtrait,ivHeadPicture);
                String userName = userBean.getUserName();
                if (!TextUtils.isEmpty(userName)) {
                    tvUserName.setText(userName);
                }
                String phoneNumber = StringUtils.hideTelephone(userBean.getPhoneNumber());
                tvPhoneNum.setText(phoneNumber);
            }
        } else {
            ivHeadPicture.setImageResource(R.mipmap.icon_mine_headprotrait);
            tvUserName.setText("");
            tvPhoneNum.setText("");
        }
    }

    /**
     * 根据手机号获取用户
     */
    private void getUser() {
        telephone = SPUtils.getTel(getContext());
        userBean = DataBaseSQLiteUtil.queryUserByTel(telephone);
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mReceive);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (!hasLogin) {
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
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

        public static final String CODE = "code"; // code 的 key
        public static final String RESULT = "result"; // 结果的key

        public static final int TYPE_CAMERA = 1; // 相机
        public static final int TYPE_ALBUM = 2; // 相册
        public static final int TYPE_USERNAME = 3; // 用户名
        public static final int TYPE_TELEPHONE = 4; // 电话号码
        public static final int TYPE_LOGOUT = 5; // 登出
        public static final int TYPE_LOGIN = 6; // 登录

        @Override
        public void onReceive(Context context, Intent intent) {
            getUser();
            int codeType = intent.getIntExtra(CODE, -1);
            if (codeType == TYPE_ALBUM) { //相册
                ImageLoader.display(mContext,userBean.getHeadProtrait(),ivHeadPicture);
            } else if (codeType == TYPE_CAMERA) { // 相机
                ImageLoader.display(mContext,userBean.getHeadProtrait(),ivHeadPicture);
            } else if (codeType == TYPE_USERNAME) { // 修改用户名
                tvUserName.setText(userBean.getUserName());
            } else if (codeType == TYPE_LOGIN) { // 登录成功
                hasLogin = true;
                String s = StringUtils.hideTelephone(userBean.getPhoneNumber());
                tvPhoneNum.setText(s);
                tvUserName.setText(userBean.getUserName());
            } else if (codeType == TYPE_TELEPHONE) { // 修改手机号码
                String s = StringUtils.hideTelephone(userBean.getPhoneNumber());
                tvPhoneNum.setText(s);
                tvUserName.setText(userBean.getUserName());
            } else if (codeType == TYPE_LOGOUT) { // 登出
                hasLogin = intent.getBooleanExtra("login", false);
                validateView();
            }
        }
    }
}
