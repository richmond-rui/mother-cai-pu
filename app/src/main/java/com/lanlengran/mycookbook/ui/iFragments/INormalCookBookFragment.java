package com.lanlengran.mycookbook.ui.iFragments;

import com.lanlengran.mycookbook.bean.CookBookItemBean;

import java.util.List;

/**
 * Created by dobest on 2016/9/28.
 */

public interface INormalCookBookFragment {
    void showProgressBar();
    void hideProgressBar();
    void showError();
    void getSuccess(List<CookBookItemBean> lists);
    void alreadLoading();
    String getHomePageURL();
}
