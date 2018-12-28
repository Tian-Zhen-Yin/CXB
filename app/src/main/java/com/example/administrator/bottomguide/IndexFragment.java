package com.example.administrator.bottomguide;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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


public class IndexFragment extends Fragment implements OnBannerListener {
    private DrawerLayout mDrawerLayout;
    private static final String ARG_PARAM1 = "param1";
    private Banner banner;
    private ArrayList<Integer> list_path;
    private ArrayList<String> list_title;
    private String mParam1;
    private Button navButton;
    private PathView pathView;
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
        recordView=inflater.inflate(R.layout.fragment_index, container, false);
        initData();
        //data_source.add(1);
        pathView=(PathView)recordView.findViewById(R.id.path_view); //动画路径
        pathView.setDataList(data_source);
        //设置轮播器
        initBanner();
        //设置抽屉
        mDrawerLayout=(DrawerLayout)recordView.findViewById(R.id.drawer_layout);
        NavigationView navView=(NavigationView)recordView.findViewById(R.id.nav_view);
        navButton=(Button)recordView.findViewById(R.id.nav_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);//点击button,打开滑动菜单
            }
        });

        initHealth();//初始化健康数据
        RecyclerView recyclerView=(RecyclerView)recordView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(recordView.getContext());

        recyclerView.setLayoutManager(layoutManager);
        HealthAdapter adapter=new HealthAdapter(healthList);
        recyclerView.setAdapter(adapter);

        return recordView;
    }

    private void initHealth() {
        if(healthList.size()==0) {
            Health heart = new Health(R.drawable.hearaterate01, "心率", "65");
            healthList.add(heart);
            Health blood_pressure = new Health(R.drawable.bloodpressure, "血压", "125");
            healthList.add(blood_pressure);
            Health blood_suger = new Health(R.drawable.suger, "血糖", "60");
            healthList.add(blood_suger);
            Health heat = new Health(R.drawable.tiweng, "体温", "37.1");
            healthList.add(heat);
        }
    }

    private void initData() {
        for(int i=0;i<50;i++)
        {
            data_source.add(i);
        }
    }

    //初始化轮播器
    private void initBanner() {
        banner = (Banner)recordView.findViewById(R.id.banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add(R.drawable.car01);
        list_path.add(R.drawable.car02);
        list_path.add(R.drawable.car03);
        list_path.add(R.drawable.touxiang);
        list_title.add("汽车人");
        list_title.add("变身");
        list_title.add("hello");
        list_title.add("yoga");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Accordion);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第"+position+"张轮播图");
    }
    //加载，播放图片
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((Integer) path).into(imageView);
        }

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
            Toast.makeText(getActivity(),"隐藏fragment 1",Toast.LENGTH_SHORT).show();
        }else {
            //Fragment显示时调用
            Toast.makeText(getActivity(),"显示fragment 1",Toast.LENGTH_SHORT).show();
        }
    }
}
