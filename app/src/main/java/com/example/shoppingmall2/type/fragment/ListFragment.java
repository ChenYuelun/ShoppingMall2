package com.example.shoppingmall2.type.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.shoppingmall2.R;
import com.example.shoppingmall2.base.BaseFragment;
import com.example.shoppingmall2.type.adapter.TypeLeftAdapter;
import com.example.shoppingmall2.type.adapter.TypeRightAdapter;
import com.example.shoppingmall2.type.bean.TypeBean;
import com.example.shoppingmall2.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dalvik.annotation.TestTarget;
import okhttp3.Call;

/**
 * Created by chenyuelun on 2017/6/16.
 */

public class ListFragment extends BaseFragment {
    private static final String TAG = ListFragment.class.getSimpleName();//"HomeFragment"
    @BindView(R.id.list_type)
    ListView listType;
    @BindView(R.id.recycler_type)
    RecyclerView recyclerType;
    Unbinder unbinder;
    private TypeLeftAdapter typeLeftAdapter;
    private TypeRightAdapter typeRightAdapter;

    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};

    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};
    private List<TypeBean.ResultBean> result;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type_leftlist, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        typeLeftAdapter = new TypeLeftAdapter(mContext,titles);
        listType.setAdapter(typeLeftAdapter);
        listType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeLeftAdapter.setSelectPosition(position);
                typeLeftAdapter.notifyDataSetChanged();
                getDataFromNet(urls[position]);
            }
        });

        getDataFromNet(urls[0]);
    }

    private void getDataFromNet(String url) {
        System.out.println(url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());

    }



    private class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG","请求失败");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG","请求成功" + id);
            processData(response);
        }


    }

    private void processData(String json) {
        TypeBean typeBean = JSON.parseObject(json, TypeBean.class);
        result = typeBean.getResult();
        if(result != null && result.size() > 0) {
            typeRightAdapter = new TypeRightAdapter(mContext,result);
            recyclerType.setAdapter(typeRightAdapter);
            GridLayoutManager manager = new GridLayoutManager(mContext,3);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position ==0){
                        return 3;
                    }else{
                        return 1;
                    }

                }
            });
            recyclerType.setLayoutManager(manager);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
