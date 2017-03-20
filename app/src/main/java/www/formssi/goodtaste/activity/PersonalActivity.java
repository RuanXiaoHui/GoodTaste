package www.formssi.goodtaste.activity;

import android.app.AlertDialog;
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

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.fragment.MineFragment;

public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQ_ALBUM = 200; //相册请求码
    private static final int REQ_CARME = 100; //相机请求码
    private static final int REQ_USERNAME = 400; //修改用户名
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
    private File mPhotoFile; //拍照保存文件
    private String saveDir = Environment.getExternalStorageDirectory().getPath() + "/goodtaste/";
    private UserBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        user = (UserBean) getIntent().getSerializableExtra("user");
        initView();
        fillData();
        tvTitle.setText(R.string.fragment_personal_security);
    }

    private void fillData() {

        String headProtrait = user.getHeadProtrait();
        if (headProtrait != null) {
            Picasso.with(this)
                    .load(Uri.parse(headProtrait))
                    .into(ivHeadPicture);
        }
        String userName = user.getUserName();
        if (userName != null) {
            tvUserName.setText(userName);
        }
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_title);
        tvUserName = (TextView) findViewById(R.id.tv_personal_username);
        tvTelephone = (TextView) findViewById(R.id.tv_personal_phone);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivHeadPicture = (ImageView) findViewById(R.id.iv_personal_headprotrait);
        rlPortrait = (RelativeLayout) findViewById(R.id.rl_personal_portrait);
        rlUsername = (RelativeLayout) findViewById(R.id.rl_personal_username);
        rlTelephone = (RelativeLayout) findViewById(R.id.rl_personal_phone);
        rlPayPassword = (RelativeLayout) findViewById(R.id.rl_personal_pay_password);
        rlLoginPassword = (RelativeLayout) findViewById(R.id.rl_personal_login_password);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setTitle("上传头像");
                builder.setItems(new String[]{"拍照", "从手机相册选择"},
                        new DialogInterface.OnClickListener() {
                            Intent intent;

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0: //拍照
                                        String state = Environment.getExternalStorageState();
                                        if (state.equals(Environment.MEDIA_MOUNTED)) {
                                            mPhotoFile = new File(saveDir, "temp.jpg");
                                            mPhotoFile.delete();
                                            if (!mPhotoFile.getParentFile().exists()) {
                                                try {
                                                    mPhotoFile.getParentFile().mkdirs();
                                                    mPhotoFile.createNewFile();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(PersonalActivity.this, "照片创建失败!",
                                                            Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                            }
                                        }
                                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                                        startActivityForResult(intent, REQ_CARME);
                                        break;
                                    case 1: //从相册选择
                                        intent = new Intent(Intent.ACTION_PICK,
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent, REQ_ALBUM);
                                        break;
                                }
                            }
                        });
                builder.show();
                break;

            case R.id.rl_personal_username: //更新用户名
                Intent intent = new Intent(this, UpdateUserNameActivity.class);
                startActivityForResult(intent, REQ_USERNAME);
                break;

            case R.id.rl_personal_phone: //更改电话号码
                Intent intent2 = new Intent(this, UpdateTelephoneActivity.class);
                startActivityForResult(intent2, REQ_USERNAME);
                break;
            case R.id.rl_personal_login_password: //更在登录密码
                Intent intent3 = new Intent(this, UpdateLoginPasswordActivity.class);
                startActivityForResult(intent3, REQ_USERNAME);
                break;
            case R.id.rl_personal_pay_password: //更改支付密码
                Intent intent4 = new Intent(this, UpdatePayPasswordActivity.class);
                startActivityForResult(intent4, REQ_USERNAME);
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
            }
            if (requestCode == REQ_CARME) { //相机
                ivHeadPicture.setImageBitmap(BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()));
                Intent intent = new Intent(MineFragment.MY_ACTION);
                intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_CAMERA);
                intent.putExtra(MineFragment.MyReceiver.RESULT, mPhotoFile);
                sendBroadcast(intent);
            }
            if (requestCode == REQ_USERNAME) { //用户名
                tvUserName.setText(data.getStringExtra("result"));
            }

        }
    }
}
