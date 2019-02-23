package com.example.administrator.bottomguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.bottomguide.Adapter.HealthAdapter;
import com.example.administrator.bottomguide.Model.DataBean;
import com.example.administrator.bottomguide.Model.Health;
import com.example.administrator.bottomguide.View.PathView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class IndexFragment extends Fragment  {
    private DrawerLayout mDrawerLayout;
    private static final String ARG_PARAM1 = "param1";
    private Banner banner;
    private ArrayList<Integer> list_path;
    private ArrayList<String> list_title;
    private String mParam1;
    private Button navButton;
    private Button healhButton,environButton;
    private Fragment currentFragment=new Fragment();

    private DataBean dataBean;
    public ArrayList<Integer> data_source=new ArrayList<>( );
    private List<Health> healthList=new ArrayList<>();
    View recordView;


    public IndexFragment() {

    }
    //newInstance方法创建fragment
    public static IndexFragment newInstance(String param1) {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }



    //这里是设置侧滑栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(Build.VERSION.SDK_INT>=21)
        {
            View decorView=getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
           getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        recordView=inflater.inflate(R.layout.fragment_index, container, false);
        initview();


        return recordView;
    }
  /*  private FragmentTransaction switchFragment(Fragment targetFragment)
    {
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();

        if(!targetFragment.isAdded())
        {

            if(currentFragment!=null)
            {
                transaction.hide(currentFragment);
                Log.e("YT!",currentFragment.toString());
            }
            transaction.add(R.id.fragment_container,targetFragment,currentFragment.getClass().getName()).addToBackStack(null);
        }
        else
        {
            transaction.hide(currentFragment)
                    .show(targetFragment)
                    .addToBackStack(null);
            Log.e("YT",currentFragment.toString());
        }
        currentFragment=targetFragment;
        return transaction;
    }*/

    private void initview() {
        mDrawerLayout=(DrawerLayout)recordView.findViewById(R.id.drawer_layout);

        NavigationView navView=(NavigationView)recordView.findViewById(R.id.nav_view);
        navButton=(Button)recordView.findViewById(R.id.nav_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);//点击button,打开滑动菜单
            }
        });

        //设置健康数据跳转
        healhButton=(Button)recordView.findViewById(R.id.hea_button);
        healhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),HealthActivity.class);
                startActivity(intent);
            }
        });
        environButton=(Button)recordView.findViewById(R.id.env_button);
        environButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),EnvironmentActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
            /*     Toast.makeText(getActivity(),"隐藏fragment 1",Toast.LENGTH_SHORT).show();*/
        }else {
            //Fragment显示时调用
            /*Toast.makeText(getActivity(),"显示fragment 1",Toast.LENGTH_SHORT).show();*/
        }
    }
}
