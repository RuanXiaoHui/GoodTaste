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
    private boolean hasLogin;

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
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        hasLogin = sharedPreferences.getBoolean("login", false);
        String telephone = sharedPreferences.getString("telephone", "");
        String userName = "";
        if (!TextUtils.isEmpty(telephone)) {
            userName = "_" + telephone.substring(telephone.length() - 4);
        }
        userBean.setUserName(userName);
        userBean.setPhoneNumber(telephone);
        validateView();
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

        public static final String CODE = "code";// code 的 key
        public static final String RESULT = "result"; // 结果的key

        public static final int TYPE_CAMERA = 1;//相机
        public static final int TYPE_ALBUM = 2;//相册
        public static final int TYPE_USERNAME = 3;//用户名
        public static final int TYPE_TELEPHONE = 4;//电话号码
        public static final int TYPE_LOGIN_STATE = 5;// 登录状态改变

        @Override
        public void onReceive(Context context, Intent intent) {
            int codeType = intent.getIntExtra(CODE, -1);
            if (codeType == TYPE_ALBUM) { //相册
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
            } else if (codeType == TYPE_TELEPHONE) {
                hasLogin = intent.getBooleanExtra("login", false);
                String userTelephone = intent.getStringExtra(RESULT);
                userTelephone = userTelephone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                userBean.setPhoneNumber(userTelephone);
                tvPhoneNum.setText(userTelephone);
                String userName = "_" + userTelephone.substring(userTelephone.length() - 4);
                userBean.setUserName(userName);
                tvUserName.setText(userName);
            } else if (codeType == TYPE_LOGIN_STATE) {
                hasLogin = intent.getBooleanExtra("login", false);
                validateView();
            }
        }
    }

    private void validateView() {
        if (hasLogin) {
            String headProtrait = userBean.getHeadProtrait();
            if (!TextUtils.isEmpty(headProtrait)) {
                Picasso.with(getContext())
                        .load(Uri.parse(headProtrait))
                        .into(ivHeadPicture);
            }
            String userName = userBean.getUserName();
            if (!TextUtils.isEmpty(userName)) {
                tvUserName.setText(userName);
            }
            String phoneNumber = userBean.getPhoneNumber();
            if (!TextUtils.isEmpty(phoneNumber)) {
                tvPhoneNum.setText(phoneNumber);
            }
        } else {
            ivHeadPicture.setImageResource(R.mipmap.icon_mine_headprotrait);
            tvUserName.setText("");
            tvPhoneNum.setText("");
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        getContext().unregisterReceiver(receive);
        super.onDestroy();
    }
}
