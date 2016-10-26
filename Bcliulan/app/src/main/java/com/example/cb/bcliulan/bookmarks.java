package com.example.cb.bcliulan;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class bookmarks {
    private static int id=0;
    private static Sql sql=null;
    private static SqlUrl sqlurl=null;
    private SQLiteDatabase SD;
    private SQLiteDatabase UD;
    private Context c=null;
    private Context c1=null;
    public bookmarks(){
    }
    public void CC(Context c){
        this.c=c;
        sql=new Sql(c,"user");

    }
    public void curl(Context c){
        this.c1=c;sqlurl=new SqlUrl(c1,"url");
    }
    public String readUrl(int position){
        UD=sqlurl.getReadableDatabase();
        Cursor curl=UD.query("url",new String[]{"id","urls"},"id=?",new String[]{""+position},null,null,null);
        String url="";
        while(curl.moveToNext()){
            url=curl.getString(curl.getColumnIndex("urls"));
        }
        UD.close();
        return url;
    }
    public ArrayList<String> Read(){
        SD=sql.getReadableDatabase();
        ArrayList<String> hm=new ArrayList<String>();
        for(int idd=1;idd<=20;idd++){
        Cursor c=SD.query("user",new String[]{"id","title"},"id=?",new String[]{""+idd},null,null,null);
        //String url="";
        String title="";
        while(c.moveToNext()){
            title=c.getString(c.getColumnIndex("title"));
        }
       hm.add(title);
        }
        SD.close();
        return hm;
    }
    public boolean Write(String title,String url){//,String url
        SD=sql.getWritableDatabase();
        UD=sqlurl.getWritableDatabase();
        ContentValues cvurl=new ContentValues();
        ContentValues cv=new ContentValues();
        id++;
        cvurl.put("id",id);
        cvurl.put("urls",url);
        cv.put("id",id);
        cv.put("title",title);
        SD.insert("user",null,cv);
        UD.insert("url",null,cvurl);
        UD.close();
        SD.close();
        return true;
    }
}
