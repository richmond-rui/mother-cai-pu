package com.lanlengran.mycookbook.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lanlengran.mycookbook.R;
import com.lanlengran.mycookbook.ui.activity.SeachActivity;

/**
 * Created by dobest on 2016/9/28.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected TextView titleTV;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected void initTitle(String title) {
        try{
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(title);

            toolbar.inflateMenu(R.menu.detail_menu);
            toolbar.setOnMenuItemClickListener(new myOptionsItemSelect(null));
        }catch (Exception e){

        }

    }
    protected void initTitle(int btnIcon, String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        toolbar.setNavigationIcon(btnIcon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    protected void initTitle(int btnIcon, String title,String shareString) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setNavigationIcon(btnIcon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new myOptionsItemSelect(shareString+getString(R.string.分享后缀)));
    }


    protected void changeTitle(String title) {
        toolbar.setTitle(title);
    }

    protected void showActivity(Class cla) {
        Intent it = new Intent(mContext, cla);
        startActivity(it);
    }

    class myOptionsItemSelect implements Toolbar.OnMenuItemClickListener {
        private String shareString;

        public myOptionsItemSelect(String shareString) {
            this.shareString = shareString;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_seanch) {
                // Handle the camera action
                showActivity(SeachActivity.class);
            }
            if (id == R.id.action_share) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                if (shareString == null) {
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "我正在用超级好用的“我的厨房”应用，你也来试试！"+getString(R.string.分享后缀));
                }else {
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                }
                shareIntent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
            return false;
        }
    }
}
