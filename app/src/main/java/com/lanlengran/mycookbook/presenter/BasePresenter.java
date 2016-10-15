package com.lanlengran.mycookbook.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by dobest on 2016/9/28.
 */

public abstract class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public void attachView(T view){
        mViewRef=new WeakReference<T>(view);
    }

    protected T getView(){
        try{
            return mViewRef.get();
        }
        catch (Exception e){

        }
        return null;
    }

    public boolean isViewAttached(){
        return mViewRef!=null&&mViewRef.get()!=null;
    }

    public void detachView(){
        if (mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
    }
}
