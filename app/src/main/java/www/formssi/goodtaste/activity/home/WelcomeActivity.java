package www.formssi.goodtaste.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    private ImageView ivWelcomeIcon;
    private Animation mIvAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivWelcomeIcon= (ImageView) findViewById(R.id.ivWelcomeIcon);
    }

    @Override
    protected void initData() {
        mIvAnimation= AnimationUtils.loadAnimation(this,R.anim.translate_welcome_logo);
    }

    @Override
    protected void initListener() {
        ivWelcomeIcon.startAnimation(mIvAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
