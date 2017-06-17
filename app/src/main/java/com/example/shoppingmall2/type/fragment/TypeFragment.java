package com.example.shoppingmall2.type.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoppingmall2.R;
import com.example.shoppingmall2.activity.MainActivity;
import com.example.shoppingmall2.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/6/13.
 */

public class TypeFragment extends BaseFragment {
    private static final String TAG = TypeFragment.class.getSimpleName();//"HomeFragment"
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    Unbinder unbinder;
    private java.lang.String[] titles = {"分类","标签"};
    private ArrayList<BaseFragment> fragments;
    private BaseFragment preFragment;
    /**
     * 初始化控件
     * retur
     */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initFragments();
        tl1.setTabData(titles);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Toast.makeText(mContext, position+"" , Toast.LENGTH_SHORT).show();
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        switchFragment(fragments.get(0));
    }


    private void switchFragment(BaseFragment currentFragment) {
        if(currentFragment != preFragment) {
            FragmentTransaction ft = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();

            if(!currentFragment.isAdded()){
                if(preFragment!= null){
                    ft.hide(preFragment);
                }
                ft.add(R.id.fl_type,currentFragment);
            }else{
                if(preFragment!= null){
                    ft.hide(preFragment);
                }
                ft.show(currentFragment);
            }
            ft.commit();
            preFragment = currentFragment;
        }
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.iv_type_search)
    public void onViewClicked() {
        Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
    }
}
