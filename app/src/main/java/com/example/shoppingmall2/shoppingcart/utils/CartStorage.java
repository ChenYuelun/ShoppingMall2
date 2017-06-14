package com.example.shoppingmall2.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.shoppingmall2.home.bean.GoodsBean;
import com.example.shoppingmall2.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by chenyuelun on 2017/6/14.
 */

public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private static Context mContext;
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage(){
        sparseArray = new SparseArray<>();
        listToSparseArray();
    }

    private void listToSparseArray() {
        ArrayList<GoodsBean> arrayList = getAllData();
        for(int i = 0; i < arrayList.size(); i++) {
            GoodsBean goodsBean = arrayList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    public ArrayList<GoodsBean> getAllData() {

        return getLocalData();
    }

    private ArrayList<GoodsBean> getLocalData() {
        ArrayList<GoodsBean> localDatas = new ArrayList<>();
        String savaJson = CacheUtils.getString(mContext, JSON_CART);
        if(!TextUtils.isEmpty(savaJson)) {
            localDatas = new Gson().fromJson(savaJson,new TypeToken< ArrayList<GoodsBean>>(){}.getType());
        }
        return localDatas;
    }

    public static CartStorage getInstance(Context context){
        if(instance == null) {
            mContext = context;
            synchronized (CartStorage.class){
                if(instance == null) {
                    instance = new CartStorage();
                }
            }
        }
        return instance;
    }

    //添加数据
    public void addData(GoodsBean goodsBean){
        GoodsBean temp = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if(temp != null) {
            temp.setNumber(goodsBean.getNumber());
        }else {
            temp = goodsBean;
        }

        //更新内存中的集合
        sparseArray.put(Integer.parseInt(temp.getProduct_id()),temp);

        //保存到本地
        savaToLocal();
    }

    //删除数据
    public void deleteData(GoodsBean goodsBean){
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        savaToLocal();
    }

    //更新数据
    public void updataData(GoodsBean goodsBean){
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        savaToLocal();
    }


    //保存到本地
    private void savaToLocal() {
        ArrayList<GoodsBean> arrayList = sparseArrayToList();
        String json = new Gson().toJson(arrayList);
        CacheUtils.putString(mContext,JSON_CART,json);
    }

    private ArrayList<GoodsBean> sparseArrayToList() {
        ArrayList<GoodsBean> arrayList = new ArrayList<>();
        for(int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            arrayList.add(goodsBean);
        }
        return arrayList;
    }


}
