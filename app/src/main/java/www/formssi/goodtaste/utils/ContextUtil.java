package www.formssi.goodtaste.utils;

import android.app.Application;

/**
 * 全局上下文
 *
 * @author qq724418408
 */
public class ContextUtil extends Application {

    private static ContextUtil instance;

    public static ContextUtil getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
