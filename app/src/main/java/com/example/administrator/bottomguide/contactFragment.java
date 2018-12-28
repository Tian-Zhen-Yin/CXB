package com.example.administrator.bottomguide;

import android.Manifest;

import android.content.pm.PackageManager;
import android.database.Cursor;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class contactFragment extends Fragment{

    ArrayAdapter<String> adapter;
    List<String> content=new ArrayList<>();
    View view;
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;



    public contactFragment() {
        // Required empty public constructor

    }


    public static contactFragment newInstance(String param1) {
        contactFragment fragment = new contactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_contact, container, false);

        ListView list=(ListView)view.findViewById(R.id.contacts_view);//引用一个list来接收系统联系人的信息
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,content);
        list.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        else
        {
            readContacts();
        }

        return view;
    }

    private void readContacts() {
        Cursor cursor=null;
        try{
            cursor=getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor!=null)
            {
                while(cursor.moveToNext())
                {
                    String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    content.add(name+"\n"+phone);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(cursor!=null)
            {
                cursor.close();
            }
        }


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
            Toast.makeText(getActivity(),"显示fragment 4",Toast.LENGTH_SHORT).show();
        }
    }
}
