package com.example.shoppingmall2.shoppingcart.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall2.app.MyApplication;
import com.example.shoppingmall2.base.BaseFragment;
import com.example.shoppingmall2.home.bean.GoodsBean;
import com.example.shoppingmall2.shoppingcart.utils.CartStorage;

import java.util.ArrayList;

/**
 * Created by chenyuelun on 2017/6/13.
 */

public class ShoppingCartFragment extends BaseFragment {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();//"HomeFragment"
    private TextView textView;

    /**
     * 初始化控件
     * retur
     */
    @Override
    public View initView() {
        Log.e(TAG,"初始化主页控件...");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"绑定数据到控件上...");
        textView.setText("ShoppingCartFragment");
        ArrayList<GoodsBean> allData = CartStorage.getInstance(MyApplication.getContext()).getAllData();
        for(int i = 0; i < allData.size(); i++) {
            Log.e("TAG",""+allData.get(i).toString());

        }
    }
}
