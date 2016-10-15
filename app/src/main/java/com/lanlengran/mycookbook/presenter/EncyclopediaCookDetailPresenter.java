package com.lanlengran.mycookbook.presenter;

import android.os.Handler;

import com.lanlengran.mycookbook.bean.CookDetailBean;
import com.lanlengran.mycookbook.model.EncyclopediaCookDetailModel;
import com.lanlengran.mycookbook.model.INormalCookDetailModel;
import com.lanlengran.mycookbook.model.OnGetCookDetailListener;
import com.lanlengran.mycookbook.ui.iActivity.INormalCookDetailActivity;

import java.util.List;

/**
 * Created by dobest on 2016/9/29.
 */

public class EncyclopediaCookDetailPresenter extends BasePresenter<INormalCookDetailActivity> implements INormalCookDetailPresenter{
    private INormalCookDetailModel normalCookDetailModel;
    private Handler handler=new Handler();
    @Override
    public void getDate() {
        normalCookDetailModel=new EncyclopediaCookDetailModel();
        getView().showProgressBar();
        normalCookDetailModel.getData(getView().getGoodsUrl(), new OnGetCookDetailListener() {

            @Override
            public void getSuccess(final List<CookDetailBean> cookBeens) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                      //  cookBeens.get(0).setImageUrl(getView().getGoodsImageUrl());
                        getView().getDateSuccess(cookBeens);
                        getView().hideProgressBar();
                    }
                });
            }

            @Override
            public void getError() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().showError();
                        getView().hideProgressBar();
                    }
                });

            }
        });
    }
}
