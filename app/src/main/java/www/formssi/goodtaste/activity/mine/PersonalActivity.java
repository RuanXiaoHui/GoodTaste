package www.formssi.goodtaste.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.fragment.MineFragment;
import www.formssi.goodtaste.utils.DataBaseSQLiteUtil;
import www.formssi.goodtaste.utils.DialogUtils;
import www.formssi.goodtaste.utils.ImageLoader;
import www.formssi.goodtaste.utils.StringUtils;
import www.formssi.goodtaste.utils.ToastUtil;

public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQ_CAMERA = 100; //相机请求码
    private static final int REQ_ALBUM = 200; //相册请求码
    private static final int REQ_USERNAME = 300; //修改用户名
    private static final int REQ_TEL = 400; //修改手机号
    private static final int REQ_PWD = 500; //修改登录密码
    private static final int REQ_PAY_PWD = 600; //修改支付密码
    private static final int REQUEST_CUT = 700; //图片修改

    private TextView tvTitle; //标题
    private TextView tvUserName; //用户名
    private TextView tvTelephone; //手机
    private TextView tvPayPassword; //设置支付密码
    private ImageView ivReturn; //返回
    private ImageView ivHeadPicture; //头像
    private RelativeLayout rlPortrait; //点击选择头像
    private RelativeLayout rlUsername; //点击修改用户名
    private RelativeLayout rlTelephone; //点击修改手机
    private RelativeLayout rlPayPassword; //点击修改支付密码
    private RelativeLayout rlLoginPassword; //点击登录密码

    private static String saveDir = Environment.getExternalStorageDirectory().getAbsolutePath();
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
        tvPayPassword = (TextView) findViewById(R.id.tv_personal_pay_password);
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
        user = (UserBean) getIntent().getSerializableExtra(ConstantConfig.USER);
        if (TextUtils.isEmpty(user.getPayPassword())) {
            tvPayPassword.setText(getString(R.string.not_set));
        } else {
            tvPayPassword.setText(getString(R.string.modify));
        }
        fillData();
    }

    private static final String TAG = "PersonalActivity";

    private void fillData() {
        String headProtrait = user.getHeadProtrait();
        if (TextUtils.isEmpty(headProtrait)) {
            ivHeadPicture.setImageResource(R.mipmap.icon_mine_headprotrait);
        } else {
            ImageLoader.displayHead(mContext, Uri.parse(headProtrait), ivHeadPicture);
        }
        Log.e(TAG, "validateView: " + headProtrait);
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
                intent.putExtra(ConstantConfig.USER, user);
                startActivityForResult(intent, REQ_USERNAME);
                break;
            case R.id.rl_personal_phone: //更改电话号码
                Intent intent2 = new Intent(this, UpdateTelephoneActivity.class);
                intent2.putExtra(ConstantConfig.USER, user);
                startActivityForResult(intent2, REQ_TEL);
                break;
            case R.id.rl_personal_login_password: //更改登录密码
                Intent intent3 = new Intent(this, UpdateLoginPasswordActivity.class);
                intent3.putExtra(ConstantConfig.USER, user);
                startActivityForResult(intent3, REQ_PWD);
                break;
            case R.id.rl_personal_pay_password: //更改支付密码
                Intent intent4 = new Intent(this, UpdatePayPasswordActivity.class);
                intent4.putExtra(ConstantConfig.USER, user);
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
                crop(uri);
            } else if (requestCode == REQ_CAMERA) { //相机
                Uri uri = Uri.fromFile(mPhotoFile);
                crop(uri);
            } else if (requestCode == REQUEST_CUT) { //图片剪切
//                ivHeadPicture.setImageBitmap(BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()));
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    File file = createFile(saveDir, "new_temp.png");
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Uri uri = Uri.fromFile(file);
                    DataBaseSQLiteUtil.updateHeadPicUrl(user.getPhoneNumber(), uri.toString());
                    user.setHeadProtrait(uri.toString());
                    Intent intent = new Intent(MineFragment.MY_ACTION);
                    intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_CUT);
                    intent.putExtra(MineFragment.MyReceiver.RESULT, file);
                    sendBroadcast(intent);
                }
            } else {
                user = (UserBean) data.getSerializableExtra(ConstantConfig.RESULT);
            }
            fillData();
        }
    }

    private void showChoiceHeadPicDialog() {
        DialogUtils.showChoiceHeadPicDialog(mContext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: //拍照
                        reqPermission();
                        break;
                    case 1: //从相册选择
                        album();
                        break;
                }
            }
        }, "选择头像", "相机", "相册");
    }

    /**
     * 拍照权限
     */
    private void reqPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PersonalActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            camera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                ToastUtil.showToast(getString(R.string.toast_sdcard_error));
                return;
            }
            camera();
        } else {
            ToastUtil.showToast("没有权限");
        }
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
//        mPhotoFile = createFile(saveDir, "temp.png");
        mPhotoFile = new File(saveDir, "temp.jpg");
        if (!mPhotoFile.getParentFile().exists()) {
            try {
                mPhotoFile.getParentFile().mkdirs();
                mPhotoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        startActivityForResult(intent, REQ_CAMERA);
    }

    private File createFile(String path, String name) {
        File file = new File(path, name);
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 图片剪切
     *
     * @param uri uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 图片格式
        intent.putExtra("outputFormat", "PNG");
        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // true:不返回uri,返回bitmap，false：返回uri
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CUT);
    }
}
