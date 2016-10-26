package com.example.cb.bcliulan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class book extends Activity {
    public static bookmarks bm=null;
    public static bookmarks bmurl=null;
    private ListView l1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.list);
        super.onCreate(savedInstanceState);
        bm=new bookmarks();
        bm.CC(this);
        bmurl=new bookmarks();
        bmurl.curl(this);
        ArrayList<String> bk=bm.Read();
        l1=(ListView)findViewById(R.id.l1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bk);
        l1.setAdapter(adapter);
        l1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=bmurl.readUrl(position+1);
                Intent i=new Intent(book.this,MainActivity.class);
                i.putExtra("url",url);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                book.this.finish();
            }
        });
        Button b1=(Button)findViewById(R.id.b1);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i=new Intent(book.this,MainActivity.class);
                        String ss=i.getStringExtra("url1");
                        if(ss==null)
                            ss="www.baidu.com";
                        i.putExtra("url",ss);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        book.this.finish();
                    }
                }
        );
    }
}
