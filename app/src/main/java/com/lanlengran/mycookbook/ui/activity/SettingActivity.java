package com.lanlengran.mycookbook.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanlengran.mycookbook.AppConfig;
import com.lanlengran.mycookbook.R;
import com.lanlengran.mycookbook.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.switch_has_image)
    ImageView switchHasImage;
    @BindView(R.id.wifi_has_image)
    ImageView wifiHasImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle(R.drawable.icon_back, "设置");
        switchHasImage.setSelected(AppConfig.getHasImage(this));
        wifiHasImage.setSelected(AppConfig.getHasWifiImage(this));
    }


    @OnClick({R.id.switch_has_image, R.id.wifi_has_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_has_image:
                if (switchHasImage.isSelected()) {
                    switchHasImage.setSelected(false);
                } else {
                    switchHasImage.setSelected(true);
                }
                AppConfig.saveHasImage(mContext, switchHasImage.isSelected());
                break;
            case R.id.wifi_has_image:
                if (wifiHasImage.isSelected()) {
                    wifiHasImage.setSelected(false);
                } else {
                    wifiHasImage.setSelected(true);
                    cheackNetState();
                }
                AppConfig.saveHasImage(mContext, wifiHasImage.isSelected());
                break;
        }
    }

    private void cheackNetState(){
        ConnectivityManager manager= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=manager.getActiveNetworkInfo();
        if (netInfo==null){
            Toast.makeText(mContext,"无网络！请开启网络",Toast.LENGTH_SHORT).show();
            return;
        }
        int type=netInfo.getType();
        if (type==ConnectivityManager.TYPE_MOBILE){
            Toast.makeText(mContext,"现在处于是移动网络，请注意流量消耗",Toast.LENGTH_SHORT).show();
            if (AppConfig.getHasWifiImage(mContext)){
                AppConfig.saveHasImage(mContext,false);
            }
        }
        if (type==ConnectivityManager.TYPE_WIFI){
            Toast.makeText(mContext,"现在处于WIFI网络,请放心使用",Toast.LENGTH_SHORT).show();
            if (AppConfig.getHasWifiImage(mContext)) {
                AppConfig.saveHasImage(mContext, true);
            }
        }
    }
}
