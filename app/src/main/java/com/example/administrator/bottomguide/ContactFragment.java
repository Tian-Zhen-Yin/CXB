package com.example.administrator.bottomguide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.administrator.bottomguide.DB.DatabaseHelper;


public class ContactFragment extends Fragment{


    View view;
    private static final String ARG_PARAM1 = "param1";
    private ListView listView;
    private String mParam1;
    public EditText addName, addTel;
    public ContactFragment() {
        // Required empty public constructor

    }


    public static ContactFragment newInstance(String param1) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_contact, container, false);
        listView= view.findViewById(R.id.listView);//引用一个list来接收系统联系人的信息
        getRelationFromDB();//数据库操作方法
        ImageView add= view.findViewById(R.id.image);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出对话框
                 dialogshow();
            }
        });
        return view;
    }



    private void getRelationFromDB()
    {
        final DatabaseHelper dbHelper=new DatabaseHelper(getContext());
        final Cursor cursor=dbHelper.query();
        final String[] from={"name","tel"};
        int[] to={R.id.name,R.id.tel};

        SimpleCursorAdapter scadapter=new SimpleCursorAdapter(getContext(),R.layout.relationlist,cursor,from,to);
        listView.setAdapter(scadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long temp=id;
                AlertDialog.Builder adBuilder=new AlertDialog.Builder(getActivity());
                adBuilder.setMessage("确认要删除联系人吗?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.del((int) temp);
                        Cursor cursor = dbHelper.query();
                        String[] from = { "name", "tel"};
                        int[] to={ R.id.name,R.id.tel};
                        SimpleCursorAdapter scadapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),R.layout.relationlist,cursor,from,to);
                        listView.setAdapter(scadapter);

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog=adBuilder.create();
                alertDialog.show();
            }
        });
        dbHelper.close();

    }
    private void dialogshow() {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        //加载自定义的格式
        View v=inflater.inflate(R.layout.addrelstion,null);
        //确定，取消

        Button btn_save = v.findViewById(R.id.dialog_btn_save);
        Button btn_cancel = v.findViewById(R.id.dialog_btn_cancel);

        final Dialog dialog=new MyDialog(getContext(),0,0,v,R.style.DialogTheme);
        dialog.setCancelable(true);
        dialog.show();
        //解决dialog中EditText不能弹出键盘的问题
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        addName=(EditText)v.findViewById(R.id.addName);
        Log.e("Editext",addName.getText().toString());
        addTel=(EditText) v.findViewById(R.id.addTel);

        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        final ContentValues values = new ContentValues();

        final DatabaseHelper dbHelper = new DatabaseHelper(getContext().getApplicationContext());
        //保存的点击事件
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put("name",addName.getText().toString());
                values.put("tel",addTel.getText().toString());
                dbHelper.insert(values);
                getRelationFromDB();
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
            Toast.makeText(getActivity(),"隐藏fragment 4",Toast.LENGTH_SHORT).show();
        }else {
            //Fragment显示时调用
            Toast.makeText(getActivity(),"显示fragment 4",Toast.LENGTH_SHORT).show();
        }
    }
}
