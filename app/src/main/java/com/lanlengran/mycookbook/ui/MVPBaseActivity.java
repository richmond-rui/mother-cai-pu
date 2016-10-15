package com.lanlengran.mycookbook.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lanlengran.mycookbook.presenter.BasePresenter;

/**
 * Created by dobest on 2016/9/28.
 */

public abstract class MVPBaseActivity<V,T extends BasePresenter<V>> extends BaseActivity{
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        mPresenter.attachView((V)this);
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
