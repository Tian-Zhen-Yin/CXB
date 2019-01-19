package com.example.administrator.bottomguide.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.TableLayout;

public class DatabaseHelper extends SQLiteOpenHelper {
    //建表操作,固定的格式
    private static final String DB_NAME="Contact";
    private static final String TABLE_NAME="Relation";
    private static final String CREATE_TABLE="create table relation(_id integer primary key autoincrement, "
            +"name text, "
            +"tel text);";


    private SQLiteDatabase db;
    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,2);

    }
    //添加
    public void insert(ContentValues values)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    //删除
    public void del(int id)
    {   //如果数据库不存在,创建一个新的数据库
        if(db==null)
            db=getWritableDatabase();
        db.delete(TABLE_NAME,"_id = ?",new String[]{String.valueOf(id)});
    }
    //查询
    public Cursor query()
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    public void close()
    {//数据库不为空,关闭数据库
        if(db!=null)
        {
            db.close();
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    this.db=db;
    db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
