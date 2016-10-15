package com.lanlengran.mycookbook.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lanlengran.mycookbook.presenter.BasePresenter;

/**
 * Created by dobest on 2016/9/28.
 */

public abstract class MVPBaseFragment<V,T extends BasePresenter<V>> extends BaseFragment{
    protected T mPresenter;
    

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter= (T) createPresenter();
        mPresenter.attachView((V)this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    protected abstract BasePresenter createPresenter();
}
