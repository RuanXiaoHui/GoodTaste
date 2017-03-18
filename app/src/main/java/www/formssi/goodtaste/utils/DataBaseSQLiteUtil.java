package www.formssi.goodtaste.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import www.formssi.goodtaste.R;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.constant.OrderState;

import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_BUY_COUNT;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_ID;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_NAME;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_PRICE;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ORDER_ID;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_SHOP_ID;
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
 *
 * 说明：
 * 1.定义有关数据库操作的增、删、改、查
 * 2.使用之前先openDataBase
 *
 * @author qq724418408
 */
public class DataBaseSQLiteUtil {

    private static SQLiteDatabase mDatabase;
    private static Context mContext = ContextUtil.getInstance();
    private static ContactDBOpenHelper mDbOpenHelper; // 数据库帮助类


    public static void insertOrder() {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put("shopName", "好味道");
        values.put("shopPicture", R.mipmap.ic_finish + "");
        values.put("status", 1 + "");
        values.put("price", "23");
        values.put("orderNumber", "1234");
        values.put("orderContent", "宫保鸡丁");
        values.put("orderTime", "2017-03-17 12:33");
        mDatabase.insert("tb_order", null, values);
        values.put("shopName", "好味道");
        values.put("shopPicture", R.mipmap.ic_finish + "");
        values.put("status", 2 + "");
        values.put("price", "23");
        values.put("orderNumber", "1234");
        values.put("orderContent", "宫保鸡丁");
        values.put("orderTime", "2017-03-17 12:33");
        mDatabase.insert("tb_order", null, values);
        values.put("shopName", "好味道");
        values.put("shopPicture", R.mipmap.ic_finish + "");
        values.put("status", 3 + "");
        values.put("price", "23");
        values.put("orderNumber", "1234");
        values.put("orderContent", "宫保鸡丁");
        values.put("orderTime", "2017-03-17 12:33");
        mDatabase.insert("tb_order", null, values);
        values.put("shopName", "好味道");
        values.put("shopPicture", R.mipmap.ic_finish + "");
        values.put("status", 4 + "");
        values.put("price", "23");
        values.put("orderNumber", "1234");
        values.put("orderContent", "宫保鸡丁");
        values.put("orderTime", "2017-03-17 12:33");
        mDatabase.insert("tb_order", null, values);
        values.put("shopName", "好味道");
        values.put("shopPicture", R.mipmap.ic_finish + "");
        values.put("status", 5 + "");
        values.put("price", "23");
        values.put("orderNumber", "1234");
        values.put("orderContent", "宫保鸡丁");
        values.put("orderTime", "2017-03-17 12:33");
        mDatabase.insert("tb_order", null, values);
        closeDataBase();
    }


    public static List<OrderBean> queryOrder(int status) {
        List<OrderBean> orderBeanList = new ArrayList<>();
        openDataBase();
        Cursor cursor;
        if (status == OrderState.ALL) {
            cursor = mDatabase.rawQuery("select * from tb_order", null);
        } else {
            cursor = mDatabase.rawQuery("select * from tb_order where status = " + status, null);
        }
        orderBeanList = new ArrayList<>();
        while (cursor.moveToNext()) {
            OrderBean orderBean = new OrderBean();
            String id = String.valueOf(cursor.getInt(0));
            String shopName = cursor.getString(1);
            String shopPicture = cursor.getString(2);
            String strStatus = cursor.getString(3);
            String price = cursor.getString(4);
            String orderNumber = cursor.getString(5);
            String orderContent = cursor.getString(6);
            String orderTime = cursor.getString(7);
            orderBean.setOrderId(id);
            orderBean.setShopName(shopName);
            orderBean.setShopImgPath(shopPicture);
            orderBean.setStatus(strStatus);
            orderBean.setActualPayment(price);
            orderBean.setShopPicture(Integer.valueOf(shopPicture));
            orderBean.setStatus(strStatus);
            orderBean.setActualPayment(price);
            orderBean.setOrderTime(orderTime);
            orderBean.setOrderContent(orderContent);
            orderBean.setOrderNum(orderNumber);
            orderBeanList.add(orderBean);
        }
        cursor.close();
        closeDataBase();
        return orderBeanList;

    }


    /**
     * 通过id查询订单表
     *
     * @return
     */
    public static List<OrderBean> getOrderBeansById(String orderId) {
        String[] projection = {"", "", ""};
        Cursor cursor = mDatabase.query(TABLE_NAME_ORDER, projection, COLUMN_ORDER_ID + "= ?",
                new String[]{orderId}, null, null, null);
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        OrderBean o = new OrderBean();
        List<OrderBean> list = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            o.setStoreId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_SHOP_ID))));
            list.add(o);
            cursor.moveToNext();
        }
        cursor.close();
        o.setFoodBeanList(getOrderDetailsBeansById(orderId));
        list.add(o);
        return list;
    }

    /**
     * 通过id查询订单详情表
     * 获得食品列表
     * @param orderId
     * @return
     */
    public static List<FoodBean> getOrderDetailsBeansById(String orderId) {
        String[] projection = {COLUMN_FOOD_ID, COLUMN_FOOD_NAME, COLUMN_FOOD_PRICE, COLUMN_FOOD_BUY_COUNT}; //
        Cursor cursor = mDatabase.query(TABLE_NAME_ORDER_DETAIL, projection, COLUMN_ORDER_ID + "= ?",
                new String[]{orderId}, null, null, null);
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<FoodBean> list = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            FoodBean food = new FoodBean();
            food.setGoodsId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_ID))));
            food.setGoodsName(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME)));
            food.setGoodsMoney(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_PRICE)));
            food.setGoodsNumber(cursor.getColumnIndex(COLUMN_FOOD_BUY_COUNT));
            list.add(food);
            cursor.moveToNext();
        }
        return list;
    }


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
            String sql1 = createTable(TABLE_NAME_USER, TABLE_USER_COLUMNS);
            db.execSQL(sql1);
            String sql2 = createTable(TABLE_NAME_SHOP, TABLE_SHOP_COLUMNS);
            db.execSQL(sql2);
            String sql3 = createTable(TABLE_NAME_FOOD, TABLE_FOOD_COLUMNS);
            db.execSQL(sql3);
            String sql4 = createTable(TABLE_NAME_ORDER, TABLE_ORDER_COLUMNS);
            db.execSQL(sql4);
            String sql5 = createTable(TABLE_NAME_ORDER_DETAIL, TABLE_ORDER_DETAIL_COLUMNS);
            db.execSQL(sql5);
            String sql6 = createTable(TABLE_NAME_ADDRESS, TABLE_ADDRESS_COLUMNS);
            db.execSQL(sql6);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    /**
     * 创建表的方法
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
