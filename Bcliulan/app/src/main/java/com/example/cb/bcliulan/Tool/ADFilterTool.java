package com.example.cb.bcliulan.Tool;

import android.content.Context;
import android.content.res.Resources;

import com.example.cb.bcliulan.R;

/**
 * Created by cb on 16-10-18.
 */

public class ADFilterTool {
    public static boolean hasAd(Context context,String url){
        Resources res =context.getResources();
        String[] adUrls=res.getStringArray(R.array.adBlockUrl);
        for(String adUrl:adUrls){
            if(url.contains(adUrl)){
                return true;
            }
        }
        return false;
    }
}
