package com.example.cb.bcliulan;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
/**
 * Created by cb on 16-5-10.
 */
public class load extends Activity {
    private final static int Delay=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAGS_CHANGED);
        //getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.load);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(load.this,Home.class);
                load.this.startActivity(intent);
                load.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        },Delay);
    }
}
