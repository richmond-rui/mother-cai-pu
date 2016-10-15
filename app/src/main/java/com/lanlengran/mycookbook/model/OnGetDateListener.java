package com.lanlengran.mycookbook.model;

import com.lanlengran.mycookbook.bean.CookBookItemBean;

import java.util.List;

/**
 * Created by dobest on 2016/9/29.
 */

public interface OnGetDateListener {
    void getSuccess(List<CookBookItemBean> itemBeens);
    void getError();
}
