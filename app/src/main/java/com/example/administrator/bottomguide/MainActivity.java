package com.example.administrator.bottomguide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.TimerTask;

/*import com.example.administrator.bottomguide.Guide.BaseCustomActivity;*/


public class MainActivity extends AppCompatActivity {
    private RadioGroup mTabRadioGroup;//按钮组
 /*   private SparseArray<Fragment> mFragmentSparesArry;//fragment组*/
    private Fragment currentFragment=new Fragment();
     ImageView IndexView;
     Boolean isChecked=false;
     RadioButton con_button,guide_button,device_button,personal_button;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);

        initView();

        //导航栏默认显示第一个
        /*getSupportFragmentManager().beginTransaction().add(fragment_container,mFragmentSparesArry.get(R.id.index_tab)).commit();
         */
    }
    /*动态切换Fragment*/
    private FragmentTransaction switchFragment(Fragment targetFragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(!targetFragment.isAdded())
        {
            //第一次使用switchFragment()时currentFragement为null,
            if(currentFragment!=null)
            {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment_container,targetFragment,currentFragment.getClass().getName());
        }
        else
        {
            transaction.hide(currentFragment)
                    .show(targetFragment);
            Log.e("YT",currentFragment.toString());
        }
        currentFragment=targetFragment;
        return transaction;
    }


    private void initView() {
        //底部导航栏
        mTabRadioGroup=findViewById(R.id.tabs_rg);

        IndexView=findViewById(R.id.index_tab);

        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.contact_tab:
                        switchFragment(ContactFragment.newInstance("紧急联系人")).commit();
                        IndexView.setImageDrawable(getResources().getDrawable(R.drawable.tab_index_default));
                        break;

                    case R.id.guide_tab:
                        if(this!=null)
                         {
                          MainActivity.this.currentFragment.isHidden();//当跳转到导航活动的时候,隐藏当前的fragment,否则会出现fragment叠加的现象
                         }
                        startActivity(new Intent(MainActivity.this, GuideActivity.class).setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP)));
                        IndexView.setImageDrawable(getResources().getDrawable(R.drawable.tab_index_default));
                        break;

                    case R.id.device_tab:
                        IndexView.setImageDrawable(getResources().getDrawable(R.drawable.tab_index_default));
                        switchFragment(DeviceFragment.newInstance("设备")).commit();
                        break;
                    case R.id.personal_tab:
                        IndexView.setImageDrawable(getResources().getDrawable(R.drawable.tab_index_default));
                        switchFragment(PersonalFragment.newInstance("个人")).commit();
                        break;

                }
                Log.e("YT",currentFragment.toString());
                isChecked=!isChecked;
            }
        });
        IndexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(IndexFragment.newInstance("首页")).commit();

                 if(v==IndexView) {
                    {
                         IndexView.setImageDrawable(getResources().getDrawable(R.drawable.tab_index_pressed));
                         Log.e("YT2",currentFragment.toString());

                     }
                 }

            }
        });
        //导航栏默认显示第一个
        switchFragment(IndexFragment.newInstance("首页")).commit();


    }

}
