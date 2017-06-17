package com.example.shoppingmall2.shoppingcart.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoppingmall2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by chenyuelun on 2017/6/14.
 */

public class AddSubView extends LinearLayout {
    @BindView(R.id.iv_sub)
    ImageView ivSub;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tvValue.setText(value+ "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.add_sub_view, this);
        ButterKnife.bind(this,view);
        
        if(attrs != null) {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                ivAdd.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                ivSub.setImageDrawable(subDrawable);
            }

        }

    }

    @OnClick({R.id.iv_sub, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_sub:
                if(value > minValue) {
                    tvValue.setText(--value+"");
                }

                break;
            case R.id.iv_add:
                if(value < maxValue) {
                    tvValue.setText(++value+"");
                }
                break;
        }

        if(changeListener != null){
            changeListener.onNumberChange(value);
        }
    }

    private OnNumberChangeListener changeListener;
    public interface OnNumberChangeListener{
        //当商品数量变化的时候回调
        public void onNumberChange(int number);
    }

    /**
     * 设置商品数量变化的监听
     * @param l
     */
    public void  setOnNumberChangeListener( OnNumberChangeListener l){
        this.changeListener = l;
    }

}
