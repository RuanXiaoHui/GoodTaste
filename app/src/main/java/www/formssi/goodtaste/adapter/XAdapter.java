package www.formssi.goodtaste.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by john on 2017/3/16.
 */

public abstract class XAdapter extends BaseAdapter {

    protected List<Object> list;
    protected Context context;

    public XAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
