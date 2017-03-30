package www.formssi.goodtaste.constant;

/**
 * 常量类
 * 说明：包含Intent请求码、结果码、action等
 * Created by sn on 2017/3/17.
 */

public final class ConstantConfig {

    public static final int ORDER_REMARK_REQUEST = 0x0001; // 订单备注请求码
    public static final int ORDER_REMARK_RESULT = 0x0002; // 订单备注结果码
    public static final int ADD_NEW_ADREES_REQUEST = 0x0003; // 新增地址请求码
    public static final int ADD_NEW_ADREES_RESULT = 0x0004; // 新增地址结果码
    public static final int EDIT_ADREES_REQUEST = 0x0005; // 修改地址请求码
    public static final int EDIT_ADREES_RESULT = 0x0006; // 修改地址结果码
    public static final int OREDER_REDDRESS_REQUEST = 0x0007; // 选择收货地址请求码
    public static final int OREDER_REDDRESS_RESULT = 0x0008; // 选择收货地址结果码

    public static final int CALL_PHONE_REQUEST_CODE = 0x1000; // 拨号权限请求码

    public static final int ORDER_TIME_MAX_CAN_PAY = 15; // 订单最大可支付时间 单位是秒

    public static final String INTENT_USER_ID = "userId";//用户id
    public static final String INTENT_ORDER_ID = "orderId";//orderFragment跳转到orderDetailActivity携带订单id的key
    public static final String INTENT_ORDER_NUM = "orderNum";//orderFragment跳转到orderDetailActivity携带订单id的key
    public static final String INTENT_STORE_NAME = "storeName";//storeName key
    public static final String INTENT_ACTUAL_PAYMENT = "totalPay";//storeName key
    public static final String INTENT_ORDER_TIME_MILLIS = "orderTimeMillis";//orderTimeMillis key

    /**
     * mine
     */
    public static final String SP_NAME = "sp_name";//SP的文件名

    //EventBean 携带的action
    public static final String PAY_COUNT_DOWN_TIME = "pay_for_order_count_down";//支付订单倒计时
    public static final String REMIND_ORDER = "reminderOrder"; // 催单
    public static final String CONFIRM_RECEIPT = "confirmReceipt"; // 确认收货

    public static final String LOGIN = "login"; //登录
    public static final String USER = "user"; //用户信息
    public static final String RESULT = "result"; //返回
}
