package www.formssi.goodtaste.utils;

import android.app.Application;

/**
 * 全局上下文
 *
 * @author qq724418408
 */
public class ContextUtil extends Application {

    private static ContextUtil instance;

    public  boolean isFinishGoodsActivity;

    public static void setInstance(ContextUtil instance) {
        ContextUtil.instance = instance;
    }

    /***
     * 是否FinishGoodsActivity
     * 1.当点击去结算的时候，这个时候来到下单界面
     *     1.一旦点击提交订单那么在返回的时候直接FinishGoodsActivity
     *     2.未点击的时候，那么我们就返回到GoodActivity
     * @return
     */
    public boolean isFinishGoodsActivity() {
        return isFinishGoodsActivity;
    }

    public void setFinishGoodsActivity(boolean finishGoodsActivity) {
        isFinishGoodsActivity = finishGoodsActivity;
    }

    public static ContextUtil getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isFinishGoodsActivity=false;
    }

}
