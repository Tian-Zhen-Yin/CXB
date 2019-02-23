package com.example.administrator.bottomguide;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.bottomguide.Adapter.HealthAdapter;
import com.example.administrator.bottomguide.Model.Health;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class HealthActivity extends AppCompatActivity implements OnBannerListener {
    private List<Health> healthList=new ArrayList<>();
    private TextView mTips;
    private ArrayList<Integer> list_path;
    private ArrayList<String> list_title;
    Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        RecyclerView mList=(RecyclerView)findViewById(R.id.health_list);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mList.setLayoutManager(linearLayoutManager);

        //配置适配器
        HealthAdapter adapter=new HealthAdapter(healthList);
        mList.setAdapter(adapter);

        mTips=(TextView)findViewById(R.id.health_tips);
        //设置TextView纵向滑动
        mTips.setText(" 心率较快的原因可能是由于服列酒、浓茶、浓咖啡或者服用了某些药物所致.\n"+"\n"+" 高血脂是现在很常见的疾病,它是三高成员之一,是吃出来的疾病,常吃高油脂的食物,导致血脂升高.\n"
                +"\n"+"健康与我们的生活息息相关,饮食上做到一少一多,才能保证驾驶安全.");
        mTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        //初始化健康数据
        initHealth();
        iniBananer();
    }

    private void iniBananer() {banner = (Banner)findViewById(R.id.banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add(R.drawable.heart_cart);
        list_path.add(R.drawable.bp_cart);
        list_path.add(R.drawable.suger_cart);
        list_title.add("1");
        list_title.add("2");
        list_title.add("3");
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

    private void initHealth() {
        if(healthList.size()==0) {
            Health heart = new Health(R.drawable.heart, "105次 / 分", "较快");
            healthList.add(heart);
            Health heat = new Health(R.drawable.heat, "36.5"+"℃" , "正常");
            healthList.add(heat);
            Health blood_pressure = new Health(R.drawable.pressure, "118/70 mmHg", "正常");
            healthList.add(blood_pressure);
            Health blood_suger = new Health(R.drawable.suger, "5.1mmol / L", "正常");
            healthList.add(blood_suger);
            Health blood_fat = new Health(R.drawable.fat,"5.7"+"mmol / L","异常");
            healthList.add(blood_fat);
            Health blood_alochol=new Health(R.drawable.wind,"30"+"mg / 100ml","酒驾");
            healthList.add(blood_alochol);
        }
    }

    @Override
    public void OnBannerClick(int position) {

    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((Integer) path).into(imageView);
        }
    }
}
