package www.formssi.goodtaste.utils;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;

import www.formssi.goodtaste.bean.EventBean;
import www.formssi.goodtaste.bean.FoodBean;
import www.formssi.goodtaste.bean.OrderBean;
import www.formssi.goodtaste.bean.ShopBean;
import www.formssi.goodtaste.bean.UserBean;
import www.formssi.goodtaste.constant.ConstantConfig;
import www.formssi.goodtaste.constant.OrderState;

import static www.formssi.goodtaste.utils.DateUtil.YYYYMMDDHHMMSS;
import static www.formssi.goodtaste.utils.DateUtil.getCurrentDate;

/**
 * 订单工具类
 *
 * @author qq724418408
 */
public final class OrderUtil {

    public static void main(String[] a) {
        System.out.println("订单号：" + getOrderNumber("17607842058"));
        OrderBean orderBean = new OrderBean();
        orderBean.setDistributingFee("4.0");
        orderBean.setDiscountMoney("10.0");
        orderBean.setOrderTotalMoney("28.89");
        System.out.println("实付金额：" + getOrderActualPayment(orderBean));
        FoodBean foodBean = new FoodBean();
        foodBean.setGoodsBuynumber(7);
        foodBean.setGoodsMoney("12.5");
        System.out.println("食品金额：" + getFoodTotalMoney(foodBean));
    }

    /**
     * 下单操作
     * 说明：首次下单或者再来一单时调用
     *
     * @param shopBean
     * @param bean     需要setDistributingFee、setDiscountMoney、setOrderTotalMoney
     * @param userBean
     * @return
     */
    public static OrderBean addOrder(ShopBean shopBean, OrderBean bean, UserBean userBean) {
        OrderBean orderBean = new OrderBean();
        orderBean.setShopBean(shopBean); //商店实体类
        orderBean.setOrderNum(OrderUtil.getOrderNumber(userBean.getPhoneNumber()));//订单号
        orderBean.setStatus(OrderState.NOT_PAY + "");//订单状态
        orderBean.setOrderTotalMoney(bean.getOrderTotalMoney());//订单总金额
        orderBean.setOrderContent(OrderUtil.createOrderContent(shopBean.getFoods()));//订单内容
        orderBean.setShopPhone(shopBean.getShopPhone());
        orderBean.setDiscountMoney(bean.getDiscountMoney()); //优惠金额
        orderBean.setDistributingFee(shopBean.getShopMoney());//配送费
        orderBean.setActualPayment(getOrderActualPayment(bean));//实付金额
        orderBean.setOrderTime(DateUtil.getCurrentTime()); //下单时间
        String addressId = userBean.getAddressId();
        orderBean.setAddressId(Integer.parseInt((addressId == null) ? "-1" : addressId));  //送餐地址Id
        return orderBean;
    }

    /**
     * 计算实付金额
     *
     * @param orderBean
     * @return
     */
    public static String getOrderActualPayment(OrderBean orderBean) {
        BigDecimal distributingFee = new BigDecimal(orderBean.getDistributingFee()); // 配送费
        BigDecimal discountMoney = new BigDecimal(orderBean.getDiscountMoney()); // 优惠金额
        BigDecimal totalMoney = new BigDecimal(orderBean.getOrderTotalMoney()); // 优惠金额
        String total = totalMoney.add(distributingFee.subtract(discountMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return total;
    }

    /**
     * 计算食品总金额
     *
     * @param foodBean
     * @return
     */
    public static String getFoodTotalMoney(FoodBean foodBean) {
        BigDecimal number = new BigDecimal(foodBean.getGoodsBuynumber()); // 数量
        BigDecimal price = new BigDecimal(foodBean.getGoodsMoney()); // 单价
        return number.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 订单号：当前时间+用户手机号后四位
     *
     * @param phone
     * @return orderNumber
     */
    public static String getOrderNumber(String phone) {
        StringBuffer sb = new StringBuffer();
        sb.append(getCurrentDate(YYYYMMDDHHMMSS));
        sb.append(getPhoneAfter4(phone, 4));
        return sb.toString();
    }

    /**
     * 生成订单的内容：获取食品名称
     *
     * @param list 食品列表
     * @return 订单内容
     */
    public static String createOrderContent(List<FoodBean> list) {
        int totalCount = 0;
        StringBuffer sb = new StringBuffer();
        if (list.size() > 0) {
            for (FoodBean f : list) {
                totalCount += f.getGoodsBuynumber(); // 计算所有商品数量
            }
            sb.append(list.get(0).getGoodsName());
            if (totalCount > 1) { // 当商品数量大于1的时候显示等多少件
                sb.append("等" + totalCount + "件食品");
            }
        }
        return sb.toString();
    }

    /**
     * 获取str后n位
     *
     * @param str
     * @param n
     * @return
     */
    public static String getPhoneAfter4(String str, int n) {
        String result = null;
        if (!"".equals(str)) {
            if (str.length() > n) {
                result = str.substring(str.length() - n, str.length());
            }
        }
        return result;
    }

    /**
     * 0000 - 9999
     *
     * @param num
     * @return
     */
    public static String getNumber(int num) {
        num++;
        String result = "";
        switch ((num + "").length()) {
            case 1:
                result = "000" + num;
                break;
            case 2:
                result = "0" + num;
                break;
            case 3:
                result = "0" + num;
                break;
            case 4:
                result = "" + num;
                break;
            default: // 此处代表编号已经超过了9999，从0重新开始
                result = "0000";
                break;
        }
        return result;
    }

    /**
     * 催单，并发货
     *
     * @param orderId
     */
    public static void reminderOrder(final String orderId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 修改订单状态为送餐中
                if (DataBaseSQLiteUtil.updateOrderState(orderId, OrderState.DELIVERY_ING) > 0) {
                    String msg = "商家已发货";
                    EventBean eventBean = new EventBean();
                    eventBean.setAction(ConstantConfig.REMIND_ORDER);
                    eventBean.setMessage(msg);
                    ToastUtil.showToast(msg);
                    EventBus.getDefault().post(eventBean); // 发送修改成功信息
                }
            }
        }, 3000); // 三秒后修改订单状态为送餐中
    }

    /**
     * 确认收货
     *
     * @param orderBean
     */
    public static void confirmReceipt(final OrderBean orderBean) {
        // 修改订单状态为未评价
        if (DataBaseSQLiteUtil.confirmReceiptOrder(orderBean) > 0) {
            String msg = "未评价";
            EventBean eventBean = new EventBean();
            eventBean.setAction(ConstantConfig.CONFIRM_RECEIPT);
            eventBean.setMessage(msg);
            ToastUtil.showToast(msg);
            EventBus.getDefault().post(eventBean);
        }
    }

    /**
     * 获取订单倒计时
     *
     * @param orderMillisUntilFinished 剩余时间 单位毫秒
     * @param tv                       显示倒计时
     * @param btn                      在线支付按钮
     * @param tip can null
     * @param tvText
     * @param btnText
     * @return
     */
    public static CountDownTimer setCountDownTime(long orderMillisUntilFinished, final TextView tv,
                                                  final String tip, final String tvText,
                                                  final Button btn, final String btnText) {

        CountDownTimer timer = new CountDownTimer(orderMillisUntilFinished, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int minute = (int) (millisUntilFinished / 1000 / 60); //分
                int second = 0;  //秒
                String strTime = null;
                if (minute < 60) {
                    second = (int) ((millisUntilFinished / 1000 % 60));
                    strTime = unitFormat(minute) + ":" + unitFormat(second);
                }
                tv.setText(((tip == null) ? "" : tip) + strTime);
                btn.setEnabled(true);
            }

            @Override
            public void onFinish() {
                tv.setText(tvText);
                btn.setText(btnText);
                btn.setEnabled(false);
            }
        };
        return timer;
    }

    /**
     * 倒计时显示格式
     *
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String result = null;
        if (i >= 0 && i < 10)
            result = "0" + Integer.toString(i);
        else
            result = "" + i;
        return result;
    }

}
