package www.formssi.goodtaste.bean;

/**
 * Created by john on 2017/3/28.
 */

public class EventBean {

    private String action;
    private String message;
    private int countDownTime;  //倒计时

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(int countDownTime) {
        this.countDownTime = countDownTime;
    }
}
