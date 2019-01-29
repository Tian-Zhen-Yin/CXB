package com.example.administrator.bottomguide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bottomguide.DB.DatabaseHelper;

import java.net.URI;


public class ContactFragment extends Fragment{


    View view;
    private static final String ARG_PARAM1 = "param1";
    private ListView listView;
    private String mParam1;
    public EditText addName, addTel;
    public ImageView Update,Delete;
    public TextView name,tel;
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
                //弹出对话框,创建新的联系人,传入的字段为"",不能写成null,否则程序会报错.
                 dialogshow("","");
            }
        });
        return view;
    }


    private void getRelationFromDB()
    {
        final DatabaseHelper dbHelper=new DatabaseHelper(getContext());
        final Cursor cursor=dbHelper.query();

        //建立映射集
        final String[] from={"name","tel"};
        int[] to={R.id.name,R.id.tel};
        //设置适配器
        SimpleCursorAdapter scadapter=new SimpleCursorAdapter(getContext(),R.layout.relationlist,cursor,from,to);
        listView.setAdapter(scadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //初始化操作
                Update=view.findViewById(R.id.update);
                Delete=view.findViewById(R.id.delete);
                name=view.findViewById(R.id.name);
                tel=view.findViewById(R.id.tel);

                //Log.e("YT:",name.toString());
                final long temp=id;
                //删除信息
                Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                //修改联系人信息
                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogshow(name.getText().toString(),tel.getText().toString());
                    }
                });
                //点击电话号码,跳转到拨打页面
                tel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel: "+tel.getText().toString()));
                        startActivity(intent);
                    }
                });

            }
        });
        dbHelper.close();

    }
    private void dialogshow(final String Name, final String Tel) {

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
        /*dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);*/
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        addName=(EditText)v.findViewById(R.id.addName);
        Log.e("Editext",addName.getText().toString());
        addTel=(EditText) v.findViewById(R.id.addTel);

        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        final ContentValues values = new ContentValues();

        final DatabaseHelper dbHelper = new DatabaseHelper(getContext().getApplicationContext());

        //在弹出的对话框中显示联系人信息,如果之前未创建过,则显示为空,若创建过,则显示.
        addName.setText(Name.toCharArray(),0,Name.length());
        addTel.setText(Tel.toCharArray(),0,Tel.length());
        //保存按钮点击事件
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    values.put("name", addName.getText().toString());
                    values.put("tel", addTel.getText().toString());
                    //创建新的联系人
                    if(Name=="")
                    {
                        dbHelper.insert(values);

                    }
                    //修改信息
                    else if(Name!="")
                    {
                        dbHelper.update(values,Name);
                    }
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

        }else {
            //Fragment显示时调用

        }
    }
}
