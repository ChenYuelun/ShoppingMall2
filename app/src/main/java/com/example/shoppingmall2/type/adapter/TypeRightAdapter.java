package com.example.shoppingmall2.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppingmall2.R;
import com.example.shoppingmall2.activity.GoodsInfoActivity;
import com.example.shoppingmall2.home.adapter.HomeAdapter;
import com.example.shoppingmall2.home.bean.GoodsBean;
import com.example.shoppingmall2.type.bean.TypeBean;
import com.example.shoppingmall2.utils.Constants;
import com.example.shoppingmall2.utils.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/6/17.
 */

public class TypeRightAdapter extends RecyclerView.Adapter {
    private final List<TypeBean.ResultBean.ChildBean> child;
    private final List<TypeBean.ResultBean.HotProductListBean> hot_product_list;
    private final List<TypeBean.ResultBean> datas;
    private final Context mContext;

    /**
     * 热卖推荐
     */
    private static final int HOT = 0;
    /**
     * 常用分类
     */
    private static final int COMMON = 1;


    /**
     * 当前类型
     */
    private int currentType = HOT;

    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;
        this.datas = result;
        hot_product_list = result.get(0).getHot_product_list();
        child = result.get(0).getChild();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else {
            currentType = COMMON;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            View itemView = View.inflate(mContext, R.layout.item_hot_right, null);
            return new HotViewHolder(itemView);
        } else if (viewType == COMMON) {
            View itemView = View.inflate(mContext, R.layout.item_common_right, null);
            return new CommonViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(hot_product_list);
        } else {
            int realPostion = position - 1;
            CommonViewHolder commonViewHolder = (CommonViewHolder) holder;
            commonViewHolder.setData(child.get(realPostion));
        }
    }

    @Override
    public int getItemCount() {
        return 1 + child.size();
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @BindView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<TypeBean.ResultBean.HotProductListBean> hot_product_list) {
            for (int i = 0; i < hot_product_list.size(); i++) {
                final TypeBean.ResultBean.HotProductListBean listBean = hot_product_list.get(i);
                LinearLayout mLinearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                params.setMargins(DensityUtil.dip2px(mContext, 5), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));
                mLinearLayout.setOrientation(LinearLayout.VERTICAL);
                mLinearLayout.setGravity(Gravity.CENTER);

                mLinearLayout.setTag(i);

                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));
                Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(imageView);
                mLinearLayout.addView(imageView, ivParams);

                TextView textView = new TextView(mContext);
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#ed3f3f"));
                textView.setText("￥" + listBean.getCover_price());
                mLinearLayout.addView(textView, tvParams);

                llHotRight.addView(mLinearLayout, params);

                mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, listBean.getName(), Toast.LENGTH_SHORT).show();
                        String cover_price = listBean.getCover_price();
                        String name = listBean.getName();
                        String figure = listBean.getFigure();
                        String product_id = listBean.getProduct_id();
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setFigure(figure);
                        goodsBean.setCover_price(cover_price);
                        goodsBean.setName(name);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
                        mContext.startActivity(intent);
                    }
                });


            }
        }


    }

    class CommonViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_ordinary_right)
        ImageView ivOrdinaryRight;
        @BindView(R.id.tv_ordinary_right)
        TextView tvOrdinaryRight;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;
        public CommonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(final TypeBean.ResultBean.ChildBean childBean) {
            //设置图片
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + childBean.getPic()).placeholder(R.drawable.new_img_loading_2)
                    .error(R.drawable.new_img_loading_2).into(ivOrdinaryRight);

            //设置名称
            tvOrdinaryRight.setText(childBean.getName());

            //设置点击事件
            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "name=="+childBean.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
