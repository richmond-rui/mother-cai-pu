package com.lanlengran.chinacaipu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dobest on 2016/9/27.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.title_back)
    TextView titleBack;
    @BindView(R.id.title_name)
    TextView titleName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void initTitle(String title) {
        ButterKnife.bind(this);
        titleName.setText(title);
    }

    protected void initTitle(String title,boolean isBack) {
        ButterKnife.bind(this);
        titleName.setText(title);
        if (isBack){
            titleBack.setVisibility(View.VISIBLE);
        }else {
            titleBack.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.title_back)
    public void onClick() {
        finish();
    }
}
