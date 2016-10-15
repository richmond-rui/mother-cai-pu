package com.lanlengran.mycookbook.presenter;

import android.os.Handler;

import com.lanlengran.mycookbook.bean.CookBookItemBean;
import com.lanlengran.mycookbook.model.INormalCookBookModel;
import com.lanlengran.mycookbook.model.NormalCookBookModel;
import com.lanlengran.mycookbook.model.OnGetDateListener;
import com.lanlengran.mycookbook.ui.iFragments.INormalCookBookFragment;

import java.util.List;

/**
 * Created by dobest on 2016/9/28.
 */

public class NormalCookBookPresenter extends BasePresenter<INormalCookBookFragment> implements INormalCookBookPresenter{
    private INormalCookBookModel normalCookBookModel;
    private Handler handler=new Handler();
    public boolean isLoading=false;     //保存当前是否在加载状态
    public static int nowPage =0;   //记录需要加载的页数
    @Override
    public void getDate() {
        normalCookBookModel=new NormalCookBookModel();
        getView().showProgressBar();
        if (isLoading){     //如果已经在刷新中，则取消此次的刷新，并返回一个已刷新警告
            getView().alreadLoading();
            return;
        }
        isLoading=true;
   //     Log.i("qin","家常菜网址："+getView().getHomePageURL()+"index_"+ (nowPage+2));
        normalCookBookModel.getData(getView().getHomePageURL()+"index_"+ (nowPage+2)+".html", new OnGetDateListener() {
            @Override
            public void getSuccess(final List<CookBookItemBean> itemBeens) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().getSuccess(itemBeens);
                        getView().hideProgressBar();
                        isLoading=false;
                        nowPage++;
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
                        isLoading=false;
                    }
                });

            }
        });
    }

    @Override
    public void refresh() {
        normalCookBookModel=new NormalCookBookModel();
        getView().showProgressBar();
        if (isLoading){
           getView().alreadLoading();
            return;
        }
        isLoading=true;
        normalCookBookModel.getData(getView().getHomePageURL(), new OnGetDateListener() {
            @Override
            public void getSuccess(final List<CookBookItemBean> itemBeens) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().getSuccess(itemBeens);
                        getView().hideProgressBar();
                        isLoading=false;
                        nowPage=0;
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
                        isLoading=false;
                    }
                });

            }
        });
    }
}
