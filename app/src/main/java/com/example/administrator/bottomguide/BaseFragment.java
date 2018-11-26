package com.example.administrator.bottomguide;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public abstract  class BaseFragment extends Fragment{
    protected BaseActivity mActivity;

    protected abstract void initView(View view,Bundle savedInstancsState);

    //获取布局文件Id
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity()
    {
        return mActivity;
    }
    //注册
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.mActivity=(BaseActivity)context;
    }
    //添加fragment
    protected void addFragment(BaseFragment fragment)
    {
        if(null!=fragment)
        {
            getHoldingActivity().addFragment(fragment);
        }
    }
    //移除fragment
    protected void removeFragment()
    {
        getHoldingActivity().removeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        initView(view,savedInstanceState);
        return view;
    }
}
