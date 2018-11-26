package com.example.administrator.bottomguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public abstract  class BaseActivity extends AppCompatActivity {


    //get the layout id
    protected abstract int getContentViewId();
    //get the layout id of fragment
    protected abstract int getFragmentContentId();
    //添加fragment
    protected void addFragment(BaseFragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(),fragment,fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
    //remove fragment
    protected void removeFragment()
    {   //获取回退栈中所有事务数量大于1的时候,执行回退操作,等于1的时候代表当前Activity只剩一个Fragment,直接finish当前Activity
        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            getSupportFragmentManager().popBackStack();
        }else
        {
            finish();
        }
    }

    //返回键返回事件

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(KeyEvent.KEYCODE_BACK==keyCode)
        {
            //当事务数量等于1的时候.直接finish
            if(getSupportFragmentManager().getBackStackEntryCount()==1)
            {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
