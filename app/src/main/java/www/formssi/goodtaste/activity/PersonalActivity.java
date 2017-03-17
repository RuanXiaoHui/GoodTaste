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
    private TextView tvTitle; //标题
    private ImageView ivReturn; //返回
    private ImageView ivHeadPicture; //头像
    private RelativeLayout rlPortrait; //点击选择头像
    private RelativeLayout rlUsername; //点击修改用户名
    private File mPhotoFile; //
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
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_backTitlebar_Title);
        ivReturn = (ImageView) findViewById(R.id.iv_backTitlebar_back);
        ivHeadPicture = (ImageView) findViewById(R.id.iv_personal_headprotrait);
        rlPortrait = (RelativeLayout) findViewById(R.id.rl_personal_portrait);
        rlUsername = (RelativeLayout) findViewById(R.id.rl_personal_username);
        ivReturn.setOnClickListener(this);
        rlPortrait.setOnClickListener(this);
        rlUsername.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_backTitlebar_back:
                finish();
                break;
            case R.id.rl_personal_portrait:
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setTitle("上传头像");
                builder.setItems(new String[]{"拍照", "从手机相册选择"},
                        new DialogInterface.OnClickListener() {
                            Intent intent;

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
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
                                    case 1:
                                        intent = new Intent(Intent.ACTION_PICK,
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent, REQ_ALBUM);
                                        break;
                                }
                            }
                        });
                builder.show();
                break;
            case R.id.rl_personal_username:
//                Intent intent = new Intent(PersonalActivity.this, )
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_ALBUM) {
                Uri uri = data.getData();
                ivHeadPicture.setImageURI(uri);
                Intent intent = new Intent(MineFragment.MY_ACTION);
                intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_ALBUM);
                intent.putExtra(MineFragment.MyReceiver.RESULT, uri);
                sendBroadcast(intent);
            }
            if (requestCode == REQ_CARME) {
                ivHeadPicture.setImageBitmap(BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()));
                Intent intent = new Intent(MineFragment.MY_ACTION);
                intent.putExtra(MineFragment.MyReceiver.CODE, MineFragment.MyReceiver.TYPE_CAMERA);
                intent.putExtra(MineFragment.MyReceiver.RESULT, mPhotoFile);
                sendBroadcast(intent);
            }
        }
    }
}
