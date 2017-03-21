package www.formssi.goodtaste.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DialogUtils;
import www.formssi.goodtaste.utils.ImageLoader;
import www.formssi.goodtaste.utils.StringUtils;

public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQ_CAMERA = 100; //相机请求码
    private static final int REQ_ALBUM = 200; //相册请求码
    private static final int REQ_USERNAME = 300; //修改用户名
    private static final int REQ_TEL = 400; //修改手机号
    private static final int REQ_PWD = 500; //修改登录密码
    private static final int REQ_PAY_PWD = 600; //修改支付密码

    private TextView tvTitle; //标题
    private TextView tvUserName; //用户名
    private TextView tvTelephone; //手机
    private ImageView ivReturn; //返回
    private ImageView ivHeadPicture; //头像
    private RelativeLayout rlPortrait; //点击选择头像
    private RelativeLayout rlUsername; //点击修改用户名
    private RelativeLayout rlTelephone; //点击修改手机
    private RelativeLayout rlPayPassword; //点击修改支付密码
    private RelativeLayout rlLoginPassword; //点击登录密码

    private static String saveDir = Environment.getExternalStorageDirectory().getPath() + "/goodtaste/";
    private static File mPhotoFile; //拍照保存文件
    private Context mContext;
    private UserBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_backTitleBar_title);
        tvUserName = (TextView) findViewById(R.id.tv_personal_username);
        tvTelephone = (TextView) findViewById(R.id.tv_personal_phone);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivHeadPicture = (ImageView) findViewById(R.id.iv_personal_headprotrait);
        rlPortrait = (RelativeLayout) findViewById(R.id.rl_personal_portrait);
        rlUsername = (RelativeLayout) findViewById(R.id.rl_personal_username);
        rlTelephone = (RelativeLayout) findViewById(R.id.rl_personal_phone);
        rlPayPassword = (RelativeLayout) findViewById(R.id.rl_personal_pay_password);
        rlLoginPassword = (RelativeLayout) findViewById(R.id.rl_personal_login_password);
    }

    @Override
    protected void initData() {
        mContext = this;
        tvTitle.setText(R.string.fragment_personal_security);
        user = (UserBean) getIntent().getSerializableExtra("user");
        ImageLoader.display(mContext, user.getHeadProtrait(), ivHeadPicture);
        tvUserName.setText(user.getUserName());
        tvTelephone.setText(StringUtils.hideTelephone(user.getPhoneNumber()));
    }

    @Override
    protected void initListener() {
        ivReturn.setOnClickListener(this);
        rlPortrait.setOnClickListener(this);
        rlUsername.setOnClickListener(this);
        rlTelephone.setOnClickListener(this);
        rlPayPassword.setOnClickListener(this);
        rlLoginPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_backTitlebar_back: //返回按钮
                finish();
                break;
            case R.id.rl_personal_portrait:  //头像设置
                showChoiceHeadPicDialog();
                break;
            case R.id.rl_personal_username: //更新用户名
                Intent intent = new Intent(this, UpdateUserNameActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, REQ_USERNAME);
                break;
            case R.id.rl_personal_phone: //更改电话号码
                Intent intent2 = new Intent(this, UpdateTelephoneActivity.class);
                intent2.putExtra("tel", user.getPhoneNumber());
                startActivityForResult(intent2, REQ_TEL);
                break;
            case R.id.rl_personal_login_password: //更改登录密码
                Intent intent3 = new Intent(this, UpdateLoginPasswordActivity.class);
                intent3.putExtra("user", user);
                startActivityForResult(intent3, REQ_PWD);
                break;
            case R.id.rl_personal_pay_password: //更改支付密码
                Intent intent4 = new Intent(this, UpdatePayPasswordActivity.class);
                intent4.putExtra("user", user);
                startActivityForResult(intent4, REQ_PAY_PWD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_ALBUM) { //相册
                Uri uri = data.getData();
                ivHeadPicture.setImageURI(uri);
                Intent intent = new Intent(MineFragment.MY_ACTION);
                intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_ALBUM);
                intent.putExtra(MineFragment.MyReceiver.RESULT, uri);
                sendBroadcast(intent);
                DataBaseSQLiteUtil.updateHeadPicUrl(user.getPhoneNumber(), uri.toString());
            }
            if (requestCode == REQ_CAMERA) { //相机
                ivHeadPicture.setImageBitmap(BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()));
                Uri uri = Uri.fromFile(mPhotoFile);
                DataBaseSQLiteUtil.updateHeadPicUrl(user.getPhoneNumber(), uri.toString());
                Intent intent = new Intent(MineFragment.MY_ACTION);
                intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_CAMERA);
                intent.putExtra(MineFragment.MyReceiver.RESULT, mPhotoFile);
                sendBroadcast(intent);
            }
            if (requestCode == REQ_USERNAME) { //用户名
                String result = data.getStringExtra("result");
                tvUserName.setText(result);
                user.setUserName(result);
            }
            if (requestCode == REQ_TEL) { //手机号
                String result = data.getStringExtra("result");
                tvTelephone.setText(StringUtils.hideTelephone(result));
                user.setPhoneNumber(result);
            }
            if (requestCode == REQ_PWD) { //手机号
                user.setLoginPassword(data.getStringExtra("result"));
            }
            if (requestCode == REQ_PAY_PWD) { //手机号
                user.setPayPassword(data.getStringExtra("result"));
            }
        }
    }

    private void showChoiceHeadPicDialog() {
        DialogUtils.showChoiceHeadPicDialog(mContext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: //拍照
                        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            Toast.makeText(PersonalActivity.this, "手机存储不可用!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        mPhotoFile = new File(saveDir, "temp.jpg");
                        if (!mPhotoFile.getParentFile().exists()) {
                            try {
                                mPhotoFile.getParentFile().mkdirs();
                                mPhotoFile.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        camera();
                        break;
                    case 1: //从相册选择
                        album();
                        break;
                }
            }
        }, "选择头像", "相机", "相册");
    }

    /**
     * 调用系统相册选择照片
     */
    private void album() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_ALBUM);
    }

    /**
     * 调用系统相机拍照
     */
    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        startActivityForResult(intent, REQ_CAMERA);
    }

}
