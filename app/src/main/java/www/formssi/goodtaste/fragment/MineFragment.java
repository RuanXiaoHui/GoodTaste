package www.formssi.goodtaste.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.activity.PersonalActivity;
import www.formssi.goodtaste.activity.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 * 我的
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private ImageView ivSetting; //设置图标
    private RelativeLayout rlPersonal; //个人中心
    private TextView tvDddress; //收货地址

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return mView;
    }

    private void initView() {
        ivSetting = (ImageView) mView.findViewById(R.id.iv_setting);
        rlPersonal = (RelativeLayout) mView.findViewById(R.id.rl_mine_personal);
        tvDddress = (TextView) mView.findViewById(R.id.tv_mine_address);

        ivSetting.setOnClickListener(this);
        rlPersonal.setOnClickListener(this);
        tvDddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mine_personal:
                intent = new Intent(getActivity(), PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_address:
//                intent = new Intent(getActivity(), );
//                startActivity(intent);
                break;
        }
    }
}
