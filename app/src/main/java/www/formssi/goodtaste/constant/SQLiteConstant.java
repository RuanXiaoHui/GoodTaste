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
    public static final String CRATE_TABLE_ADDRESS_SQL = "tb_address"; // 创建地址表

}
