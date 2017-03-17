package www.formssi.goodtaste.constant;

/**
 * 数据库常量类
 * 说明：定义有关数据库使用到的各种常量
 *
 * @author qq724418408
 */
public class SQLiteConstant {

    public static final String DB_NAME = "good_taste.db"; // 数据库名称
    public static final int DB_VERSION = 1; // 数据库版本
    public static final String TABLE_NAME_USER = "tb_user"; // 用户表
    public static final String TABLE_NAME_SHOP = "tb_shop"; // 商店表
    public static final String TABLE_NAME_FOOD = "tb_food"; // 食品表
    public static final String TABLE_NAME_ORDER = "tb_order"; // 订单表
    public static final String TABLE_NAME_ORDER_DETAIL = "tb_order_detail"; // 订单明细表
    public static final String TABLE_NAME_ADDRESS = "tb_address"; // 地址表
    public static final String[] TABLE_USER_COLUMNS = new String[]{"userId", "userName varchar(20),",
            "phone varchar(15),", "sex char(1),", "login_pwd varchar(20),", "pay_pwd varchar(6),",
            "image_path varchar(100)"}; // 用户表的列名数组
    public static final String[] TABLE_SHOP_COLUMNS = new String[]{"shopId", "name varchar(20),",
            "phone varchar(15),", "picture varchar(10),", "giveCosts varchar(10),", "address varchar(30),",
            "score varchar(10)"}; // 商店表的列名数组
    public static final String[] TABLE_FOOD_COLUMNS = new String[]{"foodId", "name varchar(20),",
            "picture varchar(10),", "price varchar(10),", "score varchar(10),", "number varchar(10),",
            "shopId interger"}; // 食品表的列名数组
    public static final String[] TABLE_ORDER_COLUMNS = new String[]{"orderId", "shopName varchar(20),",
            "shopPicture varchar(10),", "status char(1),", "foodName varchar(20),", "price varchar(10),",
            "orderTime time"}; // 订单表的列名数组
    public static final String[] TABLE_ORDER_DETAIL_COLUMNS = new String[]{"orderDetailId", "userName varchar(20),",
            "phone varchar(15),", "sex char(1),", "login_pwd varchar(20),", "pay_pwd varchar(6),",
            "image_path varchar(100)"}; // 订单详情表的列名数组
    public static final String[] TABLE_ADDRESS_COLUMNS = new String[]{"addressId", "userId interger,",
            "address varchar(50)"}; // 地址表的列名数组

}
