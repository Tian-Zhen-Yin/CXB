package com.example.administrator.bottomguide;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static android.support.v4.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TerminalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class TerminalFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private BluetoothManager bluetoothmanger;
    private BluetoothAdapter bluetoothadapter;

    


    public TerminalFragment() {
        // Required empty public constructor
    }


    public static TerminalFragment newInstance(String param1) {
        TerminalFragment fragment = new TerminalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_health, container, false);

    }
    /**
     * 判断蓝牙是否开启
     *
     * @return
     */
    public boolean blueisenable() {
        if (bluetoothadapter.isEnabled()) {
            return true;
        } else {
            return false;
        }
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
