package com.example.cb.bcliulan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cb on 16-6-7.
 */
public class Listhistory extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.list);
        super.onCreate(savedInstanceState);
        ListView ll=(ListView)findViewById(R.id.l1);
        ArrayList<String> al=new ArrayList<>();
        int size=MainActivity.w2.copyBackForwardList().getSize();
        for(int i=0;i<size;i++){
            al.add(MainActivity.w2.copyBackForwardList().getItemAtIndex(i).getTitle());
        }
        ArrayAdapter<String> a=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
        ll.setAdapter(a);
        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=MainActivity.w2.copyBackForwardList().getItemAtIndex(position).getUrl();
                Intent i=new Intent(Listhistory.this,MainActivity.class);
                i.putExtra("url",url);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                Listhistory.this.finish();
            }
        });
        Button b1=(Button)findViewById(R.id.b1);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Listhistory.this,MainActivity.class);
                        String ss=i.getStringExtra("url1");
                        if(ss==null){
                            ss="www.baidu.com";
                        }
                        i.putExtra("url",ss);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        Listhistory.this.finish();
                    }
                }
        );
    }
}
