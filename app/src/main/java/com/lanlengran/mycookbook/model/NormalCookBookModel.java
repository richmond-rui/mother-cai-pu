package com.lanlengran.mycookbook.model;

import com.lanlengran.mycookbook.net.NetUtil;

/**
 * Created by dobest on 2016/9/29.
 */

public class NormalCookBookModel implements INormalCookBookModel {
    @Override
    public void getData(final String addressUrl, final OnGetDateListener getDateListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    getDateListener.getSuccess(NetUtil.getCookItemContent(addressUrl));

                } catch (Exception e) {
                    getDateListener.getError();
                }
            }
        }.start();


    }
}
