package com.example.shoppingmall2.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall2.R;
import com.example.shoppingmall2.home.bean.HomeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/6/13.
 */
class RecommendViewHolder extends RecyclerView.ViewHolder {
    private final Context mContext;
    @BindView(R.id.tv_more_recommend)
    TextView tvMoreRecommend;
    @BindView(R.id.gv_recommend)
    GridView gvRecommend;

    public RecommendViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        Unbinder bind = ButterKnife.bind(this, itemView);
    }

    public void setData(final List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {
        RecommendAdapter adapter = new RecommendAdapter(mContext, recommend_info);
        gvRecommend.setAdapter(adapter);
        gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, recommend_info.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
