package com.example.shoppingmall2.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.shoppingmall2.R;
import com.example.shoppingmall2.base.BaseFragment;
import com.example.shoppingmall2.home.bean.HomeBean;
import com.example.shoppingmall2.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by chenyuelun on 2017/6/13.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();//"HomeFragment"
    @BindView(R.id.tv_search_home)
    TextView tvSearchHome;
    @BindView(R.id.tv_message_home)
    TextView tvMessageHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.ib_top)
    ImageButton ibTop;
    Unbinder unbinder;
    private String homeUrl;

    /**
     * 初始化控件
     * retur
     */
    @Override
    public View initView() {
        Log.e(TAG, "初始化主页控件");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        homeUrl = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(homeUrl)
                .build()
                .execute(new MyStringCallback());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "查看", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                Toast.makeText(mContext, "回到顶部", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e(TAG,"请求成功失败=="+e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG,"请求成功==");
            processData(response);

        }
    }

    private void processData(String json) {
        //解析数据
        HomeBean homeBean = JSON.parseObject(json,HomeBean.class);
        Log.e(TAG,"解析成功了=="+homeBean.getResult().getAct_info().get(0).getName());
    }

}
