package www.formssi.goodtaste.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import www.formssi.goodtaste.R;

/**
 * Created by JackSon(阮超) on 2017/3/3.
 * 邮箱：769006026@qq.com
 * 本类是自定义Indicator下标点指示器
 */
public class Indicator extends View {

    /**背景色的画笔*/
    private Paint bgPaint;

    /**前景色的画笔**/
    private Paint ForePaint;

    /**绘制的偏移量*/
    private float moffect=15;

    /**绘制圆的半径**/
    private int mRadius=10;

    /***前景色的画笔颜色**/
    private int ForeColor=Color.RED;

    /***背景色的画笔颜色**/
    private int BgColor=Color.GRAY;

    /**设置圆的数量*/
    private int mNumber=2;

    public void setIndicator(int postion,float positionOffset){

        this.moffect=(postion+positionOffset)*3*mRadius+(mRadius+3);
        invalidate();
    }


    public Indicator(Context context) {
        this(context,null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray type=context.obtainStyledAttributes(attrs, R.styleable.Indicator,defStyleAttr,0);
        mRadius=type.getInteger(R.styleable.Indicator_Indicator_radius,10);
        mNumber=type.getInteger(R.styleable.Indicator_Indicator_number,2);
        BgColor=type.getColor(R.styleable.Indicator_Indicator_bgcolor,Color.BLUE);
        ForeColor=type.getColor(R.styleable.Indicator_Indicator_forecolor,Color.BLUE);
        type.recycle();
        initPaint();
    }

    //初始化画笔
    private void initPaint() {

        bgPaint=new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(1);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(BgColor);

        ForePaint=new Paint();
        ForePaint.setAntiAlias(true);
        ForePaint.setStrokeWidth(1);
        ForePaint.setStyle(Paint.Style.FILL);
        ForePaint.setColor(ForeColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        for (int i=0;i<mNumber;i++){
            canvas.drawCircle((mRadius+3)+(mRadius*3*i),(mRadius+3),mRadius,bgPaint);
        }
        canvas.drawCircle(moffect,(mRadius+3),mRadius,ForePaint);

    }
}
