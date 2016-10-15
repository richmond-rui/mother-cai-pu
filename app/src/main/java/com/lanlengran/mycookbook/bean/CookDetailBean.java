package com.lanlengran.mycookbook.bean;

/**
 * Created by dobest on 2016/9/29.
 */

public class CookDetailBean {
    private String info;
    private boolean isImage;

    public CookDetailBean(String info,boolean isImage){
        this.info=info;
        this.isImage=isImage;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }
}
