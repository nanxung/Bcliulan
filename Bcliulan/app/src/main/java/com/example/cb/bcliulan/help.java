package com.example.cb.bcliulan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by cb on 16-5-17.
 */
public class help extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView t1=(TextView)findViewById(R.id.t1);
        t1.setText("该软件由本人独立开发,本人将不断更新！如有需要，可联系邮箱418732021@qq.com");
        Button b1=(Button)findViewById(R.id.b1);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setClass(help.this,MainActivity.class);
                        String ss=intent.getStringExtra("url1");
                        intent.putExtra("url",ss);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        help.this.finish();
                    }
                }
        );
    }
}
