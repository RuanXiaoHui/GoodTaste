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
import www.formssi.goodtaste.bean.AddressBean;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.constant.OrderState;
import www.formssi.goodtaste.constant.SQLiteConstant;

import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ACTUAL_PAY;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ADDRESS_ID;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_DISC_MONEY;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_BUY_COUNT;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_ID;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_NAME;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_FOOD_PRICE;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ORDER_ID;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ORDER_NUMBER;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ORDER_STATUS;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ORDER_TIME;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_ORDER_TOTAL_MONEY;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_PACK_FEE;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_PAY_TIME;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_SHOP_ID;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_SHOP_IMG_PATH;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_SHOP_NAME;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_TO_ADDRESS;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_TO_NAME;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_TO_PHONE;
import static www.formssi.goodtaste.constant.SQLiteConstant.COLUMN_TO_SEX;
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
 * <p>
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

    //测试    插入方法
    public static void insertOrder() {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(SQLiteConstant.COLUMN_SHOP_NAME, "好味道");//名字
        values.put(SQLiteConstant.COLUMN_SHOP_IMG_PATH, R.mipmap.ic_finish + "");//图片
        values.put(SQLiteConstant.COLUMN_ORDER_STATUS, 1 + "");//状态
        values.put(SQLiteConstant.COLUMN_ACTUAL_PAY, "23");//价格
        values.put(SQLiteConstant.COLUMN_ORDER_NUMBER, "1234");//订单号
        values.put(SQLiteConstant.COLUMN_ORDER_CONTENT, "宫保鸡丁");//内容
        values.put(SQLiteConstant.COLUMN_ORDER_TIME, "2017-03-17 12:33");//下单时间
        values.put(SQLiteConstant.COLUMN_PAY_TIME, "2017-03-17 12:33");//支付时间
        values.put(SQLiteConstant.COLUMN_ORDER_TOTAL_MONEY, "30");//总金额
        values.put(SQLiteConstant.COLUMN_DISC_MONEY, "7");//优惠金额
        mDatabase.insert("tb_order", null, values);
        values.put(SQLiteConstant.COLUMN_SHOP_NAME, "好味道");//名字
        values.put(SQLiteConstant.COLUMN_SHOP_IMG_PATH, R.mipmap.ic_finish + "");//图片
        values.put(SQLiteConstant.COLUMN_ORDER_STATUS, 2 + "");//状态
        values.put(SQLiteConstant.COLUMN_ACTUAL_PAY, "23");//价格
        values.put(SQLiteConstant.COLUMN_ORDER_NUMBER, "1234");//订单号
        values.put(SQLiteConstant.COLUMN_ORDER_CONTENT, "宫保鸡丁");//内容
        values.put(SQLiteConstant.COLUMN_ORDER_TIME, "2017-03-17 12:33");//下单时间
        values.put(SQLiteConstant.COLUMN_PAY_TIME, "2017-03-17 12:33");//支付时间
        values.put(SQLiteConstant.COLUMN_ORDER_TOTAL_MONEY, "30");//总金额
        values.put(SQLiteConstant.COLUMN_DISC_MONEY, "7");//优惠金额
        mDatabase.insert("tb_order", null, values);
        values.put(SQLiteConstant.COLUMN_SHOP_NAME, "好味道");//名字
        values.put(SQLiteConstant.COLUMN_SHOP_IMG_PATH, R.mipmap.ic_finish + "");//图片
        values.put(SQLiteConstant.COLUMN_ORDER_STATUS, 3 + "");//状态
        values.put(SQLiteConstant.COLUMN_ACTUAL_PAY, "23");//价格
        values.put(SQLiteConstant.COLUMN_ORDER_NUMBER, "1234");//订单号
        values.put(SQLiteConstant.COLUMN_ORDER_CONTENT, "宫保鸡丁");//内容
        values.put(SQLiteConstant.COLUMN_ORDER_TIME, "2017-03-17 12:33");//下单时间
        values.put(SQLiteConstant.COLUMN_PAY_TIME, "2017-03-17 12:33");//支付时间
        values.put(SQLiteConstant.COLUMN_ORDER_TOTAL_MONEY, "30");//总金额
        values.put(SQLiteConstant.COLUMN_DISC_MONEY, "7");//优惠金额
        mDatabase.insert("tb_order", null, values);
        values.put(SQLiteConstant.COLUMN_SHOP_NAME, "好味道");//名字
        values.put(SQLiteConstant.COLUMN_SHOP_IMG_PATH, R.mipmap.ic_finish + "");//图片
        values.put(SQLiteConstant.COLUMN_ORDER_STATUS, 4 + "");//状态
        values.put(SQLiteConstant.COLUMN_ACTUAL_PAY, "23");//价格
        values.put(SQLiteConstant.COLUMN_ORDER_NUMBER, "1234");//订单号
        values.put(SQLiteConstant.COLUMN_ORDER_CONTENT, "宫保鸡丁");//内容
        values.put(SQLiteConstant.COLUMN_ORDER_TIME, "2017-03-17 12:33");//下单时间
        values.put(SQLiteConstant.COLUMN_PAY_TIME, "2017-03-17 12:33");//支付时间
        values.put(SQLiteConstant.COLUMN_ORDER_TOTAL_MONEY, "30");//总金额
        values.put(SQLiteConstant.COLUMN_DISC_MONEY, "7");//优惠金额
        mDatabase.insert("tb_order", null, values);
        closeDataBase();
    }

    /**
     * 查找订单的方法
     *
     * @param status 订单的状态 (状态在orderState类中)
     * @return
     */
    public static List<OrderBean> queryOrder(int status) {
        List<OrderBean> orderBeanList = new ArrayList<>();
        openDataBase();
        Cursor cursor;
        if (status == OrderState.ALL) {
            cursor = mDatabase.rawQuery("select * from " + SQLiteConstant.TABLE_NAME_ORDER, null);
        } else {
            cursor = mDatabase.rawQuery("select * from " + SQLiteConstant.TABLE_NAME_ORDER + " where " + SQLiteConstant.COLUMN_ORDER_STATUS + " = " + status, null);
        }
        orderBeanList = new ArrayList<>();
        while (cursor.moveToNext()) {
            OrderBean orderBean = new OrderBean();
            String id = String.valueOf(cursor.getInt(0));
            String shopName = cursor.getString(1);
            String shopPicture = cursor.getString(2);
            String strStatus = cursor.getString(3);
            String price = cursor.getString(6);
            String orderNumber = cursor.getString(7);
            String orderContent = cursor.getString(8);
            String orderTime = cursor.getString(10);
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
     * 第page行开始,返回count行数据
     *
     * @param page
     * @param count
     * @return
     */
    public List<Object> testQueryAll(int page, int count) {
        /**
         * 分页查询参数
         *
         * @param table:表名
         * @param columns:要查询的列名
         * @param selection:查询条件
         * @param selectionArgs:条件中用了占位符的参数
         * @param groupBy:数据分组
         * @param having:分组后的条件
         * @param orderBy:排序方式
         * @param limit:分页查询
         **/
        Cursor cursor = mDatabase.query(TABLE_NAME_ORDER, null, null, null, null, null, null, page + "," + count); // 第page行开始,返回count行数据
        return new ArrayList<>();
    }

    /**
     * 确认订单
     *
     * @param shopBean
     * @param orderBean
     * @return
     */
    public long addOrder(ShopBean shopBean, OrderBean orderBean) {
        ContentValues values = new ContentValues();
        return mDatabase.insert(TABLE_NAME_ORDER, null, values);
    }

    /**
     * 通过id查询订单表
     *
     * @return 订单列表
     */
    public static List<OrderBean> getOrderBeansById(String orderId) {
        String[] projection = {COLUMN_SHOP_ID, COLUMN_SHOP_IMG_PATH, COLUMN_SHOP_NAME, COLUMN_ORDER_STATUS
                , COLUMN_ORDER_TOTAL_MONEY, COLUMN_PACK_FEE, COLUMN_DISC_MONEY, COLUMN_ACTUAL_PAY,
                COLUMN_ORDER_NUMBER, COLUMN_ORDER_TIME, COLUMN_PAY_TIME, COLUMN_ADDRESS_ID};
        Cursor cursor = mDatabase.query(TABLE_NAME_ORDER, projection, COLUMN_ORDER_ID + "= ?",
                new String[]{orderId}, null, null, null);
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        OrderBean o = new OrderBean();
        List<OrderBean> list = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) { //
            o.setStoreId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_SHOP_ID)))); // 商店id
            o.setShopPicture(cursor.getInt(cursor.getColumnIndex(COLUMN_SHOP_IMG_PATH))); // 商店图像
            o.setShopName(cursor.getString(cursor.getColumnIndex(COLUMN_SHOP_NAME))); // 商店名称
            o.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_STATUS))); // 订单状态
            o.setOrderTotalMoney(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_TOTAL_MONEY))); // 总金额
            o.setDistributingFee(cursor.getString(cursor.getColumnIndex(COLUMN_PACK_FEE))); // 配送费
            o.setDiscountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_DISC_MONEY))); // 优惠金额
            o.setActualPayment(cursor.getString(cursor.getColumnIndex(COLUMN_ACTUAL_PAY))); // 实付金额
            o.setOrderNum(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_NUMBER))); // 订单号
            o.setOrderTime(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_TIME))); // 下单时间
            o.setPayTime(cursor.getString(cursor.getColumnIndex(COLUMN_PAY_TIME))); // 支付时间
            o.setAddressId(cursor.getInt(cursor.getColumnIndex(COLUMN_ADDRESS_ID))); // 送餐地址id
            o.setFoodBeanList(getOrderDetailsBeansById(orderId));
            list.add(o);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * 通过id查询订单详情表
     * 获得食品列表
     *
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
     * 通过地址id查询地址信息
     *
     * @param id
     * @return
     */
    public static String getAddressById(String id) {
        String[] projection = {COLUMN_ADDRESS_ID, COLUMN_TO_NAME, COLUMN_TO_PHONE, COLUMN_TO_SEX, COLUMN_TO_ADDRESS}; //
        Cursor cursor = mDatabase.query(TABLE_NAME_ADDRESS, projection, COLUMN_ADDRESS_ID + "= ?",
                new String[]{id}, null, null, null);
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        AddressBean addressBean = new AddressBean();
        for (int i = 0; i < resultCounts; i++) {
            addressBean.setAddressId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_ADDRESS_ID))));
            addressBean.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TO_NAME))); // 收货人姓名
            addressBean.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_TO_PHONE))); // 收货人电话
            addressBean.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_TO_SEX))); // 收货人性别
            addressBean.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_TO_ADDRESS))); // 收货地址
            cursor.moveToNext();
        }
        return addressBean.toAddressString();
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
            db.execSQL(sql1); // 创建用户表
            String sql2 = createTable(TABLE_NAME_SHOP, TABLE_SHOP_COLUMNS);
            db.execSQL(sql2); // 创建商店表
            String sql3 = createTable(TABLE_NAME_FOOD, TABLE_FOOD_COLUMNS);
            db.execSQL(sql3); // 创建食品表
            String sql4 = createTable(TABLE_NAME_ORDER, TABLE_ORDER_COLUMNS);
            db.execSQL(sql4); // 创建订单表
            String sql5 = createTable(TABLE_NAME_ORDER_DETAIL, TABLE_ORDER_DETAIL_COLUMNS);
            db.execSQL(sql5); // 创建订单详情表
            String sql6 = createTable(TABLE_NAME_ADDRESS, TABLE_ADDRESS_COLUMNS);
            db.execSQL(sql6); // 创建地址表
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
