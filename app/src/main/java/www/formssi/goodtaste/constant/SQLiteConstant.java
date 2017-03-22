package www.formssi.goodtaste.constant;

/**
 * 数据库常量类
 * 说明：定义有关数据库使用到的各种常量
 *
 * @author qq724418408
 */
public class SQLiteConstant {

    //数据库建库建表
    public static final String DB_NAME = "good_taste.db"; // 数据库名称
    public static final int DB_VERSION = 1; // 数据库版本
    public static final String TABLE_NAME_USER = "tb_user"; // 用户表
    public static final String TABLE_NAME_SHOP = "tb_shop"; // 商店表
    public static final String TABLE_NAME_FOOD = "tb_food"; // 食品表
    public static final String TABLE_NAME_ORDER = "tb_order"; // 订单表
    public static final String TABLE_NAME_ORDER_DETAIL = "tb_order_detail"; // 订单明细表
    public static final String TABLE_NAME_ADDRESS = "tb_address"; // 地址表
    public static final String TABLE_NAME_FEEDBACK = "tb_feedback"; // 反馈表

    //用户表字段
    public static final String COLUMN_USER_ID = "userId"; // 用户id
    public static final String COLUMN_USER_NAME = "userName"; // 用户名
    public static final String COLUMN_USER_PHONE = "phone"; // 手机号
    public static final String COLUMN_USER_SEX = "sex"; // 性别
    public static final String COLUMN_LOGIN_PWD = "loginPwd"; // 登录密码
    public static final String COLUMN_PAY_PWD = "payPwd"; // 支付密码
    public static final String COLUMN_USER_IMG_PATH = "userImgPath"; // 头像路径

    //商店表字段
    public static final String COLUMN_SHOP_ID = "shopId"; // 商店id
    public static final String COLUMN_SHOP_NAME = "shopName"; // 商店名称
    public static final String COLUMN_SHOP_PHONE = "shopPhone"; // 商家电话
    public static final String COLUMN_SHOP_IMG_PATH = "shopImgPath"; // 商店图像
    public static final String COLUMN_PACK_FEE = "packFee"; // 配送费
    public static final String COLUMN_SHOP_ADDRESS = "shopAddress"; // 商店地址
    public static final String COLUMN_SHOP_SCORE = "shopScore"; // 商店评分
    public static final String COLUMN_SHOP_TOTAL_SELL = "shopTotalSell"; // 商店总销量

    //食品标字段
    public static final String COLUMN_FOOD_ID = "foodId"; // 食品id
    public static final String COLUMN_FOOD_NAME = "foodName"; // 食品名称
    public static final String COLUMN_FOOD_IMG_PATH = "foodImgPath"; // 食品图像
    public static final String COLUMN_FOOD_PRICE = "foodPrice"; // 食品单价
    public static final String COLUMN_FOOD_BUY_COUNT = "foodBuyCount"; // 食品数量
    public static final String COLUMN_FOOD_SELL_COUNT = "foodSellCount"; // 食品销量
    public static final String COLUMN_FOOD_SCORE = "foodScore"; // 食品评分

    //订单表字段
    public static final String COLUMN_ORDER_ID = "orderId"; // 订单id
    public static final String COLUMN_ORDER_NUMBER = "orderNumber"; // 订单号
    public static final String COLUMN_ORDER_STATUS = "orderStatus"; // 订单状态
    public static final String COLUMN_ORDER_CONTENT = "orderContent"; // 订单内容
    public static final String COLUMN_ORDER_TOTAL_MONEY = "orderTotalMoney"; // 总金额
    public static final String COLUMN_DISC_MONEY = "discountMoney"; // 优惠金额
    public static final String COLUMN_ACTUAL_PAY = "actualPay"; // 实付金额
    public static final String COLUMN_ORDER_TIME = "orderTime"; // 下单时间
    public static final String COLUMN_PAY_TIME = "payTime"; // 支付时间

    //订单详情表字段
    public static final String COLUMN_ORDER_DETAIL_ID = "oderDetailId"; // 订单详情id

    //反馈表字段
    public static final String COLUMN_FEEDBACK_ID = "_id"; // id
    public static final String COLUMN_FEEDBACK_UID = "uid"; // 评论者id
    public static final String COLUMN_FEEDBACK_CONTENTS = "contents"; // 内容

    // 地址表字段
    public static final String COLUMN_ADDRESS_ID = "addressId"; // 送餐地址id
    public static final String COLUMN_TO_NAME = "toName"; // 收货人姓名
    public static final String COLUMN_TO_SEX = "toSex"; // 收货人性别
    public static final String COLUMN_TO_PHONE = "toPhone"; // 收货人手机号
    public static final String COLUMN_TO_ADDRESS = "toAddress"; // 收货人地址
    // 用户表的列名数组
    public static final String[] TABLE_USER_COLUMNS = new String[]{COLUMN_USER_ID, COLUMN_USER_NAME
            + " varchar(20),", COLUMN_USER_PHONE + " varchar(15),", COLUMN_USER_SEX + " varchar(5),",
            COLUMN_LOGIN_PWD + " varchar(20),", COLUMN_PAY_PWD + " varchar(6),", COLUMN_USER_IMG_PATH
            + " varchar(100),", COLUMN_ADDRESS_ID + " integer"};
    // 商店表的列名数组
    public static final String[] TABLE_SHOP_COLUMNS = new String[]{COLUMN_SHOP_ID, COLUMN_SHOP_NAME
            + " varchar(20),", COLUMN_SHOP_PHONE + " varchar(15),", COLUMN_SHOP_IMG_PATH +
            " varchar(100),", COLUMN_PACK_FEE + " varchar(10),", COLUMN_SHOP_ADDRESS + " varchar(30),",
            COLUMN_SHOP_TOTAL_SELL + " varchar(10),", COLUMN_SHOP_SCORE + " varchar(10)"};
    // 食品表的列名数组
    public static final String[] TABLE_FOOD_COLUMNS = new String[]{COLUMN_FOOD_ID, COLUMN_FOOD_NAME +
            " varchar(20),", COLUMN_FOOD_IMG_PATH + " varchar(10),", COLUMN_FOOD_PRICE + " varchar(10),",
            COLUMN_FOOD_SCORE + " varchar(10),", COLUMN_FOOD_BUY_COUNT + " varchar(10),",
            COLUMN_FOOD_SELL_COUNT + " varchar(10),", COLUMN_SHOP_ID + " integer"};
    // 订单表的列名数组
    public static final String[] TABLE_ORDER_COLUMNS = new String[]{COLUMN_ORDER_ID, COLUMN_SHOP_NAME
            + " varchar(20),", COLUMN_SHOP_IMG_PATH + " varchar(100),", COLUMN_ORDER_STATUS + " char(1),",
            COLUMN_ORDER_TOTAL_MONEY + " varchar(10),", COLUMN_DISC_MONEY + " varchar(10),", COLUMN_ACTUAL_PAY
            + " varchar(10),", COLUMN_ORDER_NUMBER + " varchar(50),", COLUMN_ORDER_CONTENT + " varchar(50),",
            COLUMN_PAY_TIME + " varchar(50),", COLUMN_ORDER_TIME + " varchar(50),", COLUMN_ADDRESS_ID
            + " integer,", COLUMN_SHOP_PHONE + " varchar(15),", COLUMN_PACK_FEE + " varchar(10),",
            COLUMN_SHOP_ID + " integer"};
    // 订单详情表的列名数组
    public static final String[] TABLE_ORDER_DETAIL_COLUMNS = new String[]{COLUMN_ORDER_DETAIL_ID,
            COLUMN_ORDER_NUMBER + " varchar(50),", COLUMN_FOOD_ID + " integer,", COLUMN_FOOD_NAME +
            " varchar(20),", COLUMN_FOOD_PRICE + " varchar(10),", COLUMN_FOOD_BUY_COUNT + " varchar(10)"};
    // 地址表的列名数组
    public static final String[] TABLE_ADDRESS_COLUMNS = new String[]{COLUMN_ADDRESS_ID, COLUMN_USER_ID
            + " integer,", COLUMN_TO_ADDRESS + " varchar(50),", COLUMN_TO_NAME + " varchar(20),",
            COLUMN_TO_SEX + " varchar(5),", COLUMN_TO_PHONE + " varchar(15)"};
    // 地址表的列名数组
    public static final String[] TABLE_FEEDBACK_COLUMNS = new String[]{COLUMN_FEEDBACK_ID, COLUMN_FEEDBACK_UID
            + " varchar(7),", COLUMN_FEEDBACK_CONTENTS + " varchar(50)"};

}
