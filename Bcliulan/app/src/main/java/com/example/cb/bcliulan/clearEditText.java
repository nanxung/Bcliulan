package com.example.cb.bcliulan;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
/**
 * Created by cb on 16-5-16.
 */
public class clearEditText extends AutoCompleteTextView implements View.OnFocusChangeListener,TextWatcher{
    private Drawable mClearDrawable;
    private boolean hasFoucs;
    public clearEditText(Context context){
        this(context,null);
    }
    public clearEditText(Context context, AttributeSet attrs){
        this(context,attrs,android.R.attr.editTextStyle);
    }
    public clearEditText(Context context,AttributeSet attrs,int defStyle)
    {
        super(context,attrs,defStyle);
        init();
    }
    public void init()
    {
        mClearDrawable=getCompoundDrawables()[2];//返回自己设置的图片Returns drawables for the left, top, right, and bottom borders.
        if(mClearDrawable==null)//如果没有设置，自己调用
        {
            mClearDrawable=getResources().getDrawable(
                    R.drawable.urlbar_delete_p
            );
        }
        mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(true);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
    /* @说明：isInnerWidth, isInnerHeight为ture，触摸点在删除图标之内，则视为点击了删除图标
     * event.getX() 获取相对应自身左上角的X坐标
     * event.getY() 获取相对应自身左上角的Y坐标
     * getWidth() 获取控件的宽度
     * getHeight() 获取控件的高度
     * getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离
     * getPaddingRight() 获取删除图标右边缘到控件右边缘的距离
     * isInnerWidth:
     *  getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     *  getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     * isInnerHeight:
     *  distance 删除图标顶部边缘到控件顶部边缘的距离
     *  distance + height 删除图标底部边缘到控件顶部边缘的距离
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = getCompoundDrawables()[2].getBounds();
                int height = rect.height();
                int distance = (getHeight() - height) / 2;
                boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() -getPaddingRight());
                boolean isInnerHeight = y > distance && y < (distance + height);
                if (isInnerWidth && isInnerHeight) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    /**
     * 当ClearEditText焦点发生变化的时候，
     * 输入长度为零，隐藏删除图标，否则，显示删除图标
     */

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        this.hasFoucs=hasFocus();
        if(hasFoucs)
        {
            setClearIconVisible(getText().length()>0);//如果edittext中有，那么显示图片
        }else{
            setClearIconVisible(false);//否则图片不显示
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
    protected void setClearIconVisible(boolean visible)
    {
        Drawable right=visible?mClearDrawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(hasFoucs)
        {
            setClearIconVisible(text.length()>0);//修改了输入框图片也出现
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
