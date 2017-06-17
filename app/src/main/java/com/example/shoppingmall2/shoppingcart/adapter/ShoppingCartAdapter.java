package com.example.shoppingmall2.shoppingcart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall2.R;
import com.example.shoppingmall2.activity.GoodsInfoActivity;
import com.example.shoppingmall2.app.MyApplication;
import com.example.shoppingmall2.home.bean.GoodsBean;
import com.example.shoppingmall2.shoppingcart.utils.CartStorage;
import com.example.shoppingmall2.shoppingcart.view.AddSubView;
import com.example.shoppingmall2.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.shoppingmall2.home.adapter.HomeAdapter.GOODS_BEAN;

/**
 * Created by chenyuelun on 2017/6/14.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private final ArrayList<GoodsBean> datas;
    private final Context mContext;
    private final CheckBox checkboxAll;
    private final CheckBox cbAll;
    private final TextView tvShopcartTotal;


    public ShoppingCartAdapter(Context mContext, ArrayList<GoodsBean> datas, CheckBox checkboxAll, CheckBox cbAll, TextView tvShopcartTotal) {
        this.mContext = mContext;
        this.datas = datas;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        this.tvShopcartTotal = tvShopcartTotal;
        checkAll();
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            boolean isAllChecked = true;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    isAllChecked = false;
                    cbAll.setChecked(false);
                    checkboxAll.setChecked(false);
                }
            }
            if(isAllChecked) {
                cbAll.setChecked(true);
                checkboxAll.setChecked(true);
            }
        }else {
            //没有数据
            cbAll.setChecked(false);
            checkboxAll.setChecked(false);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_cart, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GoodsBean goodsBean = datas.get(position);
        holder.cbGov.setChecked(goodsBean.isChecked());
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText(goodsBean.getCover_price());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);

        holder.AddSubView.setValue(goodsBean.getNumber());
        holder.AddSubView.setMaxValue(10);
        holder.AddSubView.setMinValue(1);

        holder.AddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int number) {
                goodsBean.setNumber(number);
                CartStorage.getInstance(MyApplication.getContext()).updataData(goodsBean);
                showTotalPrice();
            }
        });
        showTotalPrice();
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("￥合计：" + getTotalPrice());
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    totalPrice += (Double.parseDouble(goodsBean.getCover_price()))*goodsBean.getNumber();
                    Log.e("TAG","totalPrice == " + totalPrice);
                }
            }
        }

        return totalPrice;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setCheckedAllOrNone(boolean checked) {
        if(datas.size()>0 && datas!= null) {
            for(int i = 0; i <datas.size() ; i++) {
                datas.get(i).setChecked(checked);
                notifyItemChanged(i);
            }
        }

    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_gov)
        CheckBox cbGov;
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @BindView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @BindView(R.id.AddSubView)
        com.example.shoppingmall2.shoppingcart.view.AddSubView AddSubView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            ivGov.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                    intent.putExtra(GOODS_BEAN,datas.get(getLayoutPosition()));
//                    mContext.startActivity(intent);
//                }
//            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsBean goodsBean = datas.get(getLayoutPosition());
                    goodsBean.setChecked(!goodsBean.isChecked());
                    notifyItemChanged(getLayoutPosition());
                    showTotalPrice();
                    checkAll();
                }
            });


        }
    }

    public void deleteData() {

        if(datas != null && datas.size() > 0){
            for(int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isChecked()){
                    datas.remove(goodsBean);
                    //同步到本地
                    CartStorage.getInstance(MyApplication.getContext()).deleteData(goodsBean);
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }

    }

}
