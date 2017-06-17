package com.example.shoppingmall2.shoppingcart.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall2.R;
import com.example.shoppingmall2.activity.MainActivity;
import com.example.shoppingmall2.app.MyApplication;
import com.example.shoppingmall2.base.BaseFragment;
import com.example.shoppingmall2.home.bean.GoodsBean;
import com.example.shoppingmall2.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.shoppingmall2.shoppingcart.utils.CartStorage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/6/13.
 */

public class ShoppingCartFragment extends BaseFragment {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();//"HomeFragment"
    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    Unbinder unbinder;
    private ArrayList<GoodsBean> datas;
    private ShoppingCartAdapter adapter;
    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;


    @Override
    public View initView() {
        Log.e(TAG, "初始化主页控件...");
        View view = View.inflate(mContext, R.layout.fragment_shop_cart, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        datas = CartStorage.getInstance(MyApplication.getContext()).getAllData();
        if(datas != null && datas.size() >0){
            tvShopcartEdit.setTag(ACTION_EDIT);
            //有数据-空布局隐藏
            llEmptyShopcart.setVisibility(View.GONE);
            //设置适配器
            adapter = new ShoppingCartAdapter(mContext,datas,checkboxAll,cbAll,tvShopcartTotal);
            recyclerview.setAdapter(adapter);
            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));


        }else{
            //没有数据-空布局显示
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.btn_check_out, R.id.cb_all, R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
//                Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                if((int)tvShopcartEdit.getTag() == ACTION_EDIT) {
                    showDelete();
                }else {
                    tvShopcartEdit.setTag(ACTION_EDIT);
                    hideDelete();
                }
                break;
            case R.id.checkbox_all:
//                Toast.makeText(mContext, "全选", Toast.LENGTH_SHORT).show();
                boolean checked = checkboxAll.isChecked();
                adapter.setCheckedAllOrNone(checked);
                adapter.showTotalPrice();
                break;
            case R.id.btn_check_out:
                Toast.makeText(mContext, "去结算", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb_all:
                Toast.makeText(mContext, "删除的全选", Toast.LENGTH_SHORT).show();
                boolean checked2 = cbAll.isChecked();
                adapter.setCheckedAllOrNone(checked2);
                adapter.showTotalPrice();
                break;
            case R.id.btn_delete:
               // Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
                adapter.deleteData();
                adapter.checkAll();
                showEempty();
                break;
            case R.id.btn_collection:
                Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
                Toast.makeText(mContext, "去逛逛", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("checkid",R.id.rb_home);
                startActivity(intent);
                break;
        }
    }

    private void showEempty() {
        if(datas == null || datas.size() == 0) {
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

    private void hideDelete() {
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        llCheckAll.setVisibility(View.VISIBLE);
        llDelete.setVisibility(View.GONE);
        adapter.setCheckedAllOrNone(true);
        adapter.checkAll();
        adapter.showTotalPrice();

    }

    private void showDelete() {
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        llCheckAll.setVisibility(View.GONE);
        llDelete.setVisibility(View.VISIBLE);
        adapter.setCheckedAllOrNone(false);
        adapter.checkAll();

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();

    }
}
