package com.example.administrator.bottomguide;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;


public class StyleActivity extends AppCompatActivity {
    private RadioGroup mTabRadioGroup;//按钮组
    private SparseArray<Fragment> mFragmentSparesArry;//fragment组
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
        mFragmentSparesArry.append(R.id.today_tab,IndexFragment.newInstance("体检"));
        mFragmentSparesArry.append(R.id.record_tab,settingFragment.newInstance("记录"));
        mFragmentSparesArry.append(R.id.contact_tab,contactFragment.newInstance("导航"));
        mFragmentSparesArry.append(R.id.settings_tab,healthFragment.newInstance("设置"));


        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mFragmentSparesArry.get(checkedId)).commit();
            }
        });
        //导航栏默认显示第一个
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,mFragmentSparesArry.get(R.id.today_tab)).commit();
        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StyleActivity.this, MainActivity.class));
            }
        });

    }
}
