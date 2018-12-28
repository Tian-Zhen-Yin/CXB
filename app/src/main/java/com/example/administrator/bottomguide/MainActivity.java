package com.example.administrator.bottomguide;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioGroup;

/*import com.example.administrator.bottomguide.Guide.BaseCustomActivity;*/

import static com.example.administrator.bottomguide.R.id.fragment_container;


public class MainActivity extends AppCompatActivity {
    private RadioGroup mTabRadioGroup;//按钮组
    private SparseArray<Fragment> mFragmentSparesArry;//fragment组
    private Fragment currentFragment=new Fragment();
    /*private IndexFragment index=IndexFragment.newInstance("体检");
    private contactFragment contact=contactFragment.newInstance("记录");
    private healthFragment health=healthFragment.newInstance("健康");
    */
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
        }
        currentFragment=targetFragment;
        return transaction;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);

        initView();
    }

    private void initView() {
        //底部导航栏
        mTabRadioGroup=findViewById(R.id.tabs_rg);


        mFragmentSparesArry=new SparseArray<>();
        mFragmentSparesArry.append(R.id.index_tab,IndexFragment.newInstance("体检"));
        mFragmentSparesArry.append(R.id.record_tab,settingFragment.newInstance("记录"));
        mFragmentSparesArry.append(R.id.contact_tab,contactFragment.newInstance("导航"));
        mFragmentSparesArry.append(R.id.settings_tab,healthFragment.newInstance("设置"));


        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               switchFragment(mFragmentSparesArry.get(checkedId)).commit();
                Log.d("当前Fragment",mFragmentSparesArry.get(checkedId).toString());
            }
        });

        //导航栏默认显示第一个
        getSupportFragmentManager().beginTransaction().add(fragment_container,mFragmentSparesArry.get(R.id.index_tab)).commit();

        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(this!=null) {
                    finishActivity(0);
                    startActivity(new Intent(MainActivity.this, GuideActivity.class).setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP)));

                }
            }
        });

    }

}
0