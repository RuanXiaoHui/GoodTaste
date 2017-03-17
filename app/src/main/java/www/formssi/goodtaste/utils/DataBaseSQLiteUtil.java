package www.formssi.goodtaste.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static www.formssi.goodtaste.constant.SQLiteConstant.DB_NAME;
import static www.formssi.goodtaste.constant.SQLiteConstant.DB_VERSION;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_ADDRESS_COLUMNS;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_FOOD_COLUMNS;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_NAME_ADDRESS;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_NAME_FOOD;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_NAME_ORDER;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_NAME_ORDER_DETAIL;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_NAME_SHOP;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_NAME_USER;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_ORDER_COLUMNS;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_ORDER_DETAIL_COLUMNS;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_SHOP_COLUMNS;
import static www.formssi.goodtaste.constant.SQLiteConstant.TABLE_USER_COLUMNS;

/**
 * 数据库操作工具类
 * 使用之前先openDataBase
 * 说明：定义有关数据库操作的增、删、改、查
 *
 * @author qq724418408
 */
public class DataBaseSQLiteUtil {

    private static SQLiteDatabase mDatabase;
    private static Context mContext = ContextUtil.getInstance();
    private static ContactDBOpenHelper mDbOpenHelper; // 数据库帮助类

    /**
     * 打开数据库
     */
    public static void openDataBase() {
        mDbOpenHelper = new ContactDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mDbOpenHelper.getWritableDatabase(); // 获取可写数据库
        } catch (SQLException e) {
            mDatabase = mDbOpenHelper.getReadableDatabase(); // 获取只读数据库
        }
    }

    /**
     * 记得关闭数据库
     */
    public static void closeDataBase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }


    /**
     * 数据表打开帮助类
     */
    private static class ContactDBOpenHelper extends SQLiteOpenHelper {

        public ContactDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            StringBuffer sb = new StringBuffer();
            String sql1 = createTable(TABLE_NAME_USER, TABLE_USER_COLUMNS);
            String sql2 = createTable(TABLE_NAME_SHOP, TABLE_SHOP_COLUMNS);
            String sql3 = createTable(TABLE_NAME_FOOD, TABLE_FOOD_COLUMNS);
            String sql4 = createTable(TABLE_NAME_ORDER, TABLE_ORDER_COLUMNS);
            String sql5 = createTable(TABLE_NAME_ORDER_DETAIL, TABLE_ORDER_DETAIL_COLUMNS);
            String sql6 = createTable(TABLE_NAME_ADDRESS, TABLE_ADDRESS_COLUMNS);
            sb.append(sql1);
            sb.append(sql2);
            sb.append(sql3);
            sb.append(sql4);
            sb.append(sql5);
            sb.append(sql6);
            db.execSQL(sb.toString());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    /**
     * 创建表的方法
     *
     *
     * @param tableName 表名
     * @param columns   列名数组：new String[]{"id", "name varchar(20),", "phone varchar(15)"}
     * @return 创建表的数据库语句
     */
    public static String createTable(String tableName, String... columns) {
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists " + tableName + " ("
                + columns[0] + " integer primary key autoincrement, ");
        for (int i = 1; i < columns.length; i++) {
            sb.append(" " + columns[i] + " ");
        }
        sb.append(");");
        return sb.toString();
    }

}
