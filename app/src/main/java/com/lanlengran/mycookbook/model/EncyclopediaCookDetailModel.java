package com.lanlengran.mycookbook.model;

import com.lanlengran.mycookbook.net.NetUtil;

import org.jsoup.nodes.Document;

/**
 * Created by dobest on 2016/9/29.
 */

public class EncyclopediaCookDetailModel implements INormalCookDetailModel {
    private static Document document = null;
    @Override
    public void getData(final String addressUrl, final OnGetCookDetailListener getdatelistener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    getdatelistener.getSuccess(NetUtil.getCookDetail("http://www.chinacaipu.com/"+addressUrl));

                } catch (Exception e) {
                    getdatelistener.getError();
                }
            }
        }.start();


    }


}
