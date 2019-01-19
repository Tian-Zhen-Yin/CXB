package com.example.administrator.bottomguide;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
//自定义对话框格式,封装成一个自定义类
public class MyDialog extends Dialog {
    public MyDialog(Context context, int width, int height, View layout, int style) {
//继承上下文和stytle
        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        window.setAttributes(params);
    }

}
