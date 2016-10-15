package com.lanlengran.mycookbook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 芮勤 on 2016/10/1.
 */

public class AppConfig {
    private static final  String haiImagTag ="HAS_IMAGE";
    private static final  String wifiImagTag ="WIFI_IMAGE";
    private static int isHasImage=-1;

    public static void saveHasImage(Context context, boolean hasImage) {
        SharedPreferences.Editor editor = context.getSharedPreferences(haiImagTag,
                Activity.MODE_PRIVATE).edit();
        editor.putBoolean(haiImagTag,hasImage);
        editor.commit();
        if (hasImage){
            isHasImage=1;
        }else {
            isHasImage=0;
        }
    }
    //获得是否加载图片的设置
    public static boolean getHasImage(Context context){
        if (isHasImage==-1){
            SharedPreferences pref = context.getSharedPreferences(haiImagTag,
                    Activity.MODE_PRIVATE);
            if (pref.getBoolean(haiImagTag,true)){
                isHasImage=1;
            }else {
                isHasImage=0;
            }
        }
       if (isHasImage==0){
           return false;
       }else {
           return true;
       }

    }

    public static void saveWifiImage(Context context, boolean wifiImage) {
        SharedPreferences.Editor editor = context.getSharedPreferences(wifiImagTag,
                Activity.MODE_PRIVATE).edit();
        editor.putBoolean(wifiImagTag,wifiImage);
        editor.commit();
    }
    //获得是否加载图片的设置
    public static boolean getHasWifiImage(Context context){
            SharedPreferences pref = context.getSharedPreferences(wifiImagTag,
                    Activity.MODE_PRIVATE);
            if (pref.getBoolean(wifiImagTag,false)){
                return true;
            }else {
               return false;
            }


    }
}
