package www.formssi.goodtaste.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.formssi.goodtaste.R;

/**
 * A simple {@link Fragment} subclass.
 * 外卖页面
 */
public class HomeFragment extends Fragment {
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_home, container, false);;

        return mView;
    }

}
