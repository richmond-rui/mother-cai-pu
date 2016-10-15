package com.lanlengran.mycookbook.model;

import com.lanlengran.mycookbook.net.NetUtil;

/**
 * Created by dobest on 2016/9/29.
 */

public class NormalCookDetailModel implements INormalCookDetailModel {
    @Override
    public void getData(final String addressUrl, final OnGetCookDetailListener getdatelistener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    getdatelistener.getSuccess(NetUtil.getCookDetail(addressUrl));

                } catch (Exception e) {
                    getdatelistener.getError();
                }
            }
        }.start();


    }
}
