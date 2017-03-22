package www.formssi.goodtaste.utils;

import android.content.Context;
import android.content.SharedPreferences;

import www.formssi.goodtaste.constant.ConstantConfig;

/**
 * Created by qkldev003 on 2017/3/21.
 */

public class SPUtils {

    public static void putString(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.commit();
    }
    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    public static void updateTel(Context context, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("telephone",value);
        edit.commit();
    }

    public static String getTel(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("telephone","");
    }

    public static String getString(Context context, String key, String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defValue);
    }
}
