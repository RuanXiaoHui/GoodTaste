package www.formssi.goodtaste.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.formssi.goodtaste.R;

/**
 * Created by Administrator on 2017/2/25.
 * 邮箱：769006026@qq.com
 * 包名：cn.jackson.numberaddsubdemo
 * 描述：自定义加减控件
 */
public class NumberSubAdd extends LinearLayout implements View.OnClickListener {
    private LayoutInflater mInflate;
    private Button btnAdd;
    private Button btnSub;
    private TextView tv;
    private int maxNum;
    private int minNum;
    private int vue;

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getVue() {
        String v=tv.getText().toString();
        if (!v.equals("")){
            vue=Integer.parseInt(v);
        }
        return vue;
    }

    public void setVue(int vue) {
        tv.setText(vue+"");
        this.vue = vue;
    }

    ////////////对外提供事件点击接口///////////////////
    public interface NumBerSubAddClick{
        void AddBtnOnClick(View v, int vue);
        void SubBtnOnclick(View v, int vue);

    }
    public NumBerSubAddClick nubNumBerSubAddClick;

    public void setNubNumBerSubAddClick(NumBerSubAddClick nubNumBerSubAddClick) {
        this.nubNumBerSubAddClick = nubNumBerSubAddClick;
    }
    //////////////////////////////////////////////

    public NumberSubAdd(Context context) {
        this(context,null);
    }

    public NumberSubAdd(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumberSubAdd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflate=LayoutInflater.from(context);
        init();
        if(attrs!=null){
            TintTypedArray Array= TintTypedArray.obtainStyledAttributes(context,attrs, R.styleable.NumberSubAdd,defStyleAttr,0);
            int minNum1=Array.getInt(R.styleable.NumberSubAdd_minVue,0);
            setMinNum(minNum1);
            int maxNum1=Array.getInt(R.styleable.NumberSubAdd_maxVue,10);
            setMaxNum(maxNum1);
            int vue1=Array.getInt(R.styleable.NumberSubAdd_vue,1);
            setVue(vue1);
            Drawable AddDrawable=Array.getDrawable(R.styleable.NumberSubAdd_AddBackground);
            Drawable SubDrawable=Array.getDrawable(R.styleable.NumberSubAdd_SubBackground);
            Drawable TextDrawable=Array.getDrawable(R.styleable.NumberSubAdd_textViewBackground);
            setButtonAddBackgroud(AddDrawable);
            setButtonSubBackgroud(SubDrawable);
            setTexViewBackground(TextDrawable);
            Array.recycle();
        }
    }

    private void init() {
        View view=mInflate.inflate(R.layout.addsub_item,this,true);
        btnAdd= (Button) view.findViewById(R.id.Add);
        btnSub= (Button) view.findViewById(R.id.Sub);
        tv= (TextView) view.findViewById(R.id.tv);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        if (R.id.Add==view.getId()){
            AddNumber();
            if (nubNumBerSubAddClick!=null){
                nubNumBerSubAddClick.AddBtnOnClick(view,vue);
            }
        }else if(R.id.Sub==view.getId()){
            SubNumber();
            if (nubNumBerSubAddClick!=null){
                nubNumBerSubAddClick.SubBtnOnclick(view,vue);
            }
        }
    }
    //加法
    private void AddNumber(){
        if(vue<maxNum){
            vue=vue+1;
            btnSub.setEnabled(true);
        }
        tv.setText(vue+"");
    }
    //减法
    private void SubNumber(){
        if(vue>minNum){
            vue=vue-1;
        }
        tv.setText(vue+"");
    }

    public void setTexViewBackground(Drawable drawable){
        tv.setBackgroundDrawable(drawable);
    }

    public void setTextViewBackground(int drawableId){
        setTexViewBackground(getResources().getDrawable(drawableId));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonAddBackgroud(Drawable backgroud){
        this.btnAdd.setBackground(backgroud);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonSubBackgroud(Drawable backgroud){
        this.btnSub.setBackground(backgroud);
    }

}
