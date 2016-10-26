package com.example.cb.bcliulan;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.LogRecord;

public class Home extends Activity {
    private String str=null;
    private WebView w2=null;
    private Handler h=null;
    private EditText e1=null;
    private Button front=null;
    private Button home=null;
    private long time=0;
    private Button menu=null;
    private Button chuangkou=null;
    private String url=null;
    private Button next=null;
    private String s;
    final boolean itemsChecked[]=new boolean[]{true,true,false,true,true};
    private ProgressBar pbar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button bb=(Button)findViewById(R.id.bb);
        pbar=(ProgressBar)findViewById(R.id.bar);
        e1=(EditText)this.findViewById(R.id.e1);
        front=(Button)findViewById(R.id.front);
        next=(Button)findViewById(R.id.next);
        home=(Button)findViewById(R.id.home);
        chuangkou=(Button)findViewById(R.id.chuankou);
        menu=(Button)findViewById(R.id.caidan);
        GridView gv=(GridView)findViewById(R.id.g1);
        ArrayList<HashMap<String,Object>> data=new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<images.length;i++)
        {
            HashMap<String,Object>hash=new HashMap<String, Object>();
            hash.put("ItemImage",images[i]);
            hash.put("ItemText",imagesName[i]);
            data.add(hash);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,data,R.layout.item,new String[]{"ItemImage","ItemText"},new int[]{R.id.ItemImage,R.id.ItemText});
        gv.setAdapter(simpleAdapter);
        gv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        s=URL[position];
                        lian1();
                    }
                }
        );
        menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String items[]=new String[]{"书签","历史记录","设置","帮助","退出"};
                        AlertDialog.Builder builde=new AlertDialog.Builder(Home.this);
                        builde.setTitle("工具栏");
                        builde.setIcon(R.drawable.ic_refresh_inverse);
                        builde.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which)
                                {
                                    case 0:
                                        book();break;
                                    case 1:ListHistory();break;
                                    case 2:builder();break;
                                    case 3:
                                        Intent intent=new Intent();
                                        intent.setClass(Home.this,help.class);
                                        startActivity(intent);
                                        Home.this.finish();
                                        break;
                                    case 4:
                                        System.exit(0);
                                }
                            }
                        });
                        builde.create().show();
                    }
                }
        );
        home.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Home.this,"已到达首页面",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent ii=getIntent();
                        s=ii.getStringExtra("url1");
                        lian1();
                    }
                }
        );
        front.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Home.this,"已到达首页",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        bb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s=e1.getText().toString();
                        lian1();
                    }
                }
        );
    }
    public void ListHistory() {
        Intent in = new Intent(Home.this, Listhistory.class);
        in.putExtra("url1",w2.getUrl());
        startActivity(in);
        Home.this.finish();
    }
    public void lian1(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(Home.this,MainActivity.class);
                intent.putExtra("url",s);
                startActivity(intent);
                Home.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        },0);

    }
    public void book(){
        Intent in=new Intent(Home.this,book.class);
        startActivity(in);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        Home.this.finish();
    }
    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if((KeyCode==KeyEvent.KEYCODE_BACK))
        {
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            if(System.currentTimeMillis()-time>2000)
            {
                time=System.currentTimeMillis();
            }
            else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(KeyCode,event);
    }
    public void builder(){
        final String items[]=new String[]{"BuiltInZoomControls","JavaScript","DisplayZoomControls","UseWideViewPort","AppCacheEnabled"};
        AlertDialog.Builder builder=new AlertDialog.Builder(Home.this);
        builder.setTitle("设置");
        builder.setIcon(R.drawable.umeng_fb_write_normal);
        builder.setMultiChoiceItems(items, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                itemsChecked[which]=isChecked;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result="";
                for(int i=0;i<itemsChecked.length;i++)
                {
                    if(itemsChecked[i]){
                        result+=items[i]+",";
                    }
                }
                if(!"".equals(result.trim()))
                {
                    result=result.substring(0,result.length()-1);
                    Toast.makeText(Home.this,"启用："+result,Toast.LENGTH_SHORT).show();
                }
            }
        }).create().show();
    }
    private final int[] images=new int[]{R.drawable.a,R.drawable.b,
            R.drawable.e,R.drawable.l,R.drawable.f,R.drawable.m,R.drawable.n
            ,R.drawable.x,R.drawable.z};
    private final String[] imagesName=new String[]{
            "阅读","百度","新闻","pptv","1905","美图","爱奇艺","新浪","优酷","搜狐"
    };
    private final String[] URL=new String[]{"www.qidian.com","www.baidu.com","news.baidu.com","www.pptv.com",
            "www.1905.com","image.baidu.com","www.iqiyi.com","www.sina.com","www.youku.com"
    ,"www.sohu.com"};
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_DOWN)
        {
            View v=getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                e1.clearFocus();
                return true;
            }
        }
        return false;
    }
}
