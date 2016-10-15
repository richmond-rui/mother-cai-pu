package com.lanlengran.mycookbook.bean;

/**
 * Created by dobest on 2016/9/28.
 */

public class CookBookItemBean {
    private String title;
    private String info;
    private String imgUrl;
    private String GoodsUrl;

    public String getGoodsUrl() {
        return GoodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        GoodsUrl = goodsUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
