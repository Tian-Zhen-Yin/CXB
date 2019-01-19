package com.example.administrator.bottomguide.View;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.bottomguide.DB.DatabaseHelper;
import com.example.administrator.bottomguide.R;

public class AddRelationActivity extends AppCompatActivity {

    private EditText addName, addTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrelstion);

        addName=(EditText)findViewById(R.id.addName);
        addTel=(EditText)findViewById(R.id.addTel);
    }


    public void save(View view){
        final ContentValues values = new ContentValues();
        values.put("name",addName.getText().toString());
        values.put("tel",addTel.getText().toString());
        final DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        final AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        adBuilder.setMessage("确认保存记录吗？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogI, int which) {
                dbHelper.insert(values);
                Intent intent = getIntent();
                setResult(0x111,intent);
                AddRelationActivity.this.finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogI, int which) {}
        });
        AlertDialog alertDialog = adBuilder.create();
        alertDialog.show();
    }

}
