package com.lanlengran.mycookbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lanlengran.mycookbook.AppConfig;
import com.lanlengran.mycookbook.R;
import com.lanlengran.mycookbook.bean.CookDetailBean;
import com.lanlengran.mycookbook.presenter.NormalCookDetailPresenter;
import com.lanlengran.mycookbook.ui.MVPBaseActivity;
import com.lanlengran.mycookbook.ui.iActivity.INormalCookDetailActivity;
import com.lanlengran.mycookbook.util.DensityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalCookDeatilActivity extends MVPBaseActivity<INormalCookDetailActivity,
        NormalCookDetailPresenter> implements INormalCookDetailActivity {
    @BindView(R.id.progeressbar)
    ProgressBar progeressbar;
    @BindView(R.id.detail_liearLayout)
    LinearLayout detailLiearLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String url;
    private String imageUrl;
    private String title;
    private List<CookDetailBean> cookDetailBeanList;
    private View mView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext).inflate(R.layout.activity_normal_cook_deatil, null, false);
        setContentView(mView);
        ButterKnife.bind(this);
        getIntentDate();
        initView();
        mPresenter.getDate();
    }

    @Override
    protected NormalCookDetailPresenter createPresenter() {
        return new NormalCookDetailPresenter();
    }

    private void initView() {
        initTitle(R.drawable.icon_back,title,title+url);


      //  setSupportActionBar(toolbar);
    }

    public void getIntentDate() {
        Intent it = getIntent();
        url = it.getStringExtra("url");
        imageUrl = it.getStringExtra("image");
        title = it.getStringExtra("title");
    }

    @Override
    public void showProgressBar() {
        progeressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progeressbar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar.make(progeressbar, getResources().getText(R.string.网络错误), Snackbar.LENGTH_LONG).
                setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.getDate();
                    }
                }).show();
    }

    @Override
    public void getDateSuccess(List<CookDetailBean> itemBeens) {
        for (CookDetailBean bean : itemBeens) {
            if (bean.isImage()) {
                final ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //     imageView.setAdjustViewBounds(true);
                imageView.setImageResource(R.drawable.image_loading);
                detailLiearLayout.addView(imageView);
                if (AppConfig.getHasImage(mContext)) {
                    Glide.with(mContext).load(bean.getInfo())
                            .asBitmap().fitCenter().
                            into(imageView);

                }
            } else {
                TextView textView = new TextView(mContext);
                textView.setText(bean.getInfo());
                textView.setPadding(DensityUtils.dip2px(mContext, 10), DensityUtils.dip2px(mContext, 5),
                        DensityUtils.dip2px(mContext, 10), DensityUtils.dip2px(mContext, 5));
                detailLiearLayout.addView(textView);
            }
        }
    }

    @Override
    public String getGoodsUrl() {
        return url;
    }

    @Override
    public String getGoodsImageUrl() {
        return imageUrl;
    }

}
