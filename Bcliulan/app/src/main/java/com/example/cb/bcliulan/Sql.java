package com.example.cb.bcliulan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cb on 16-5-30.
 */
public class Sql extends SQLiteOpenHelper {
    public Sql(Context c, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(c,name,factory,version);
    }
    public Sql(Context c,String name,int version){
        this(c,name,null,version);
    }
    public Sql(Context c,String name){
        this(c,name,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(id int,title varchar(100))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
