package com.lanlengran.mycookbook.ui.iActivity;

import com.lanlengran.mycookbook.bean.CookDetailBean;

import java.util.List;

/**
 * Created by dobest on 2016/9/29.
 */

public interface INormalCookDetailActivity {
    void showProgressBar();
    void hideProgressBar();
    void showError();
    void getDateSuccess(List<CookDetailBean> itemBeens);
    String getGoodsUrl();
    String getGoodsImageUrl();
}
