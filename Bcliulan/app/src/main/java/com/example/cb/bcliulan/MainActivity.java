package com.example.cb.bcliulan;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cb.bcliulan.Tool.ADFilterTool;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cb on 16-5-10.
 */
public class MainActivity extends Activity {
    private String str = null;
    public static WebView w2 = null;
    private EditText e1 = null;
    private AudioManager audio = null;
    private Button front = null;
    private Button home = null;
    private Button chuangkou = null;
    private Button menu = null;
    private String url = null;
    private Button next = null;
    private Button b1 = null;
    private Sql sql;
   private bookmarks bk=null;
    private SQLiteDatabase SD;
    final boolean itemsChecked[] = new boolean[]{true, true, false, true, true};
    private ProgressBar pbar = null;
    private GoogleApiClient client;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bk=new bookmarks();
        bk.CC(this);
        //sql = new Sql(MainActivity.this,"user");
        Button bb = (Button) findViewById(R.id.bb);
        pbar = (ProgressBar) findViewById(R.id.bar);
        w2 = (WebView) findViewById(R.id.w2);
        chajian();
        Intent intent = getIntent();
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        url = intent.getStringExtra("url");
        e1 = (EditText) this.findViewById(R.id.e1);
        if (!url.contains("http") && url.contains(".")) {
            if (!url.contains("http://") && !url.contains("www"))
                lian("http://www." + url);
            else
                lian("http://" + url);
        } else
            lian(url);
        front = (Button) findViewById(R.id.front);
        next = (Button) findViewById(R.id.next);
        home = (Button) findViewById(R.id.home);
        chuangkou = (Button) findViewById(R.id.chuankou);
        w2.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        bookmarks bk=new bookmarks();
                        bk.CC(MainActivity.this);
                        bk.curl(MainActivity.this);
                        bk.Write(w2.getTitle(),w2.getUrl());
                        Toast.makeText(MainActivity.this, "已添加当前页面", Toast.LENGTH_SHORT).show();
                    return false;
                    }
                    }
        );
        menu = (Button) findViewById(R.id.caidan);
        menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String items[] = new String[]{"书签", "历史记录", "设置", "帮助", "退出"};
                        AlertDialog.Builder builde = new AlertDialog.Builder(MainActivity.this);
                        builde.setTitle("工具栏");
                        builde.setIcon(R.drawable.ic_refresh_inverse);
                        builde.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        book();
                                        break;
                                    case 1:
                                        ListHistory();
                                        break;
                                    case 2:
                                        builder();
                                        break;
                                    case 3:
                                        Intent intent = new Intent();
                                        intent.setClass(MainActivity.this, help.class);
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        startActivity(intent);
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
        chuangkou.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        w2.reload();
                    }
                }
        );
        home.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Handler().postDelayed(
                                new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, Home.class);
                                intent.putExtra("url1", w2.getOriginalUrl());
                                startActivity(intent);
                                MainActivity.this.finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        }, 0);

                    }
                }
        );
        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        w2.goForward();
                        WebSettings w1;

                    }
                }
        );
        front.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        w2.goBack();
                    }
                }
        );

        bb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String es = e1.getText().toString();
                        if (es.contains("http")) {
                            str = "";
                            lian(str);
                        } else {
                            str = "http://";
                            lian(str);
                        }
                    }
                }
        );
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public class MyWebView1 extends WebViewClient {
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            e1.setText(w2.getUrl());
//            return true;
//        }
        private  String homeurl;
        private Context context;

        public MyWebView1(Context context,String homeurl) {
            this.context = context;
            this.homeurl = homeurl;
        }
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            url = url.toLowerCase();
            if(!url.contains(homeurl)){
                if (!ADFilterTool.hasAd(context, url)) {
                    return super.shouldInterceptRequest(view, url);
                }else{
                    return new WebResourceResponse(null,null,null);

                }
            }else{
                return super.shouldInterceptRequest(view, url);
            }


        }
    }
    public void chajian() {
        w2.getSettings().setBuiltInZoomControls(itemsChecked[0]);//放大
        w2.getSettings().setDisplayZoomControls(itemsChecked[2]);//隐藏控制按钮
        w2.getSettings().setUseWideViewPort(itemsChecked[3]);//无限放大
        w2.getSettings().setJavaScriptEnabled(itemsChecked[1]);
        w2.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        w2.getSettings().setAppCacheEnabled(itemsChecked[4]);
        w2.getSettings().setUseWideViewPort(true);
        w2.getSettings().setSupportZoom(true);
        //w2.getSettings().setBlockNetworkLoads(true);
        w2.getSettings().setDomStorageEnabled(true);
        w2.setWebViewClient(new MyWebView1(this,url));
        w2.setWebChromeClient(
                new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100)
                {
                    pbar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    if (View.INVISIBLE == pbar.getVisibility()) {
                        pbar.setVisibility(View.VISIBLE);
                    }
                    pbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    public void book() {
        Intent in = new Intent(MainActivity.this, book.class);
        in.putExtra("url1",w2.getUrl());
        startActivity(in);
        MainActivity.this.finish();
    }
    public void ListHistory() {
        Intent in = new Intent(MainActivity.this, Listhistory.class);
        in.putExtra("url1",w2.getUrl());
        startActivity(in);
        MainActivity.this.finish();
    }
    public void lian(String str) {
        str += e1.getText().toString();
        if (!str.contains("http")) {
            str = url;
            str = "http://www.baidu.com/#wd=" + str;
            w2.loadUrl(str);
        } else {
            w2.loadUrl(str);
            e1.setText(str);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String url = bundle.getString(w2.getTitle());
            w2.loadUrl(url);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if ((KeyCode == KeyEvent.KEYCODE_BACK) && w2.canGoBack()) {
            w2.goBack();
        }
        if ((KeyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);

        }
        if ((KeyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);
        }
        return true;
    }

    public void builder() {
        final String items[] = new String[]{"BuiltInZoomControls", "JavaScript", "DisplayZoomControls", "UseWideViewPort", "AppCacheEnabled"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("设置");
        builder.setIcon(R.drawable.umeng_fb_write_normal);
        builder.setMultiChoiceItems(items, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                itemsChecked[which] = isChecked;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = "";
                for (int i = 0; i < itemsChecked.length; i++) {
                    if (itemsChecked[i]) {
                        result += items[i] + ",";
                    }
                }
                if (!"".equals(result.trim())) {
                    result = result.substring(0, result.length() - 1);
                    Toast.makeText(MainActivity.this, "启用：" + result, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }
}
