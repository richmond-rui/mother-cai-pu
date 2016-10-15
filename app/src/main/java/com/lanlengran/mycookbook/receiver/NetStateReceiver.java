package com.lanlengran.mycookbook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.lanlengran.mycookbook.AppConfig;

/**
 * Created by 芮勤 on 2016/10/2.
 */

public class NetStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=manager.getActiveNetworkInfo();
        if (netInfo==null){
            Toast.makeText(context,"无网络！请开启网络",Toast.LENGTH_SHORT).show();
            return;
        }
        int type=netInfo.getType();
        if (type==ConnectivityManager.TYPE_MOBILE){
            Toast.makeText(context,"现在处于是移动网络，请注意流量消耗",Toast.LENGTH_SHORT).show();
            if (AppConfig.getHasWifiImage(context)){
                AppConfig.saveHasImage(context,false);
            }
        }
        if (type==ConnectivityManager.TYPE_WIFI){
            Toast.makeText(context,"现在处于WIFI网络,请放心使用",Toast.LENGTH_SHORT).show();
            if (AppConfig.getHasWifiImage(context)) {
                AppConfig.saveHasImage(context, true);
            }
        }
    }
}
