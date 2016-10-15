package com.lanlengran.mycookbook.model;

import com.lanlengran.mycookbook.bean.CookDetailBean;

import java.util.List;

/**
 * Created by dobest on 2016/9/29.
 */

public interface OnGetCookDetailListener {
    void getSuccess(List<CookDetailBean> cookBeens);
    void getError();
}
