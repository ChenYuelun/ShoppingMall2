package com.example.shoppingmall2.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall2.R;
import com.example.shoppingmall2.home.bean.HomeBean;
import com.example.shoppingmall2.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/6/13.
 */
class HotViewHolder extends RecyclerView.ViewHolder {
    private final Context mContext;
    @BindView(R.id.tv_more_hot)
    TextView tvMoreHot;
    @BindView(R.id.gv_hot)
    MyGridView gvHot;

    public HotViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        ButterKnife.bind(this, itemView);
    }

    public void setData(final List<HomeBean.ResultBean.HotInfoBean> hot_info) {
        HotAdapter adapter = new HotAdapter(mContext, hot_info);
        gvHot.setAdapter(adapter);
        gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, hot_info.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
