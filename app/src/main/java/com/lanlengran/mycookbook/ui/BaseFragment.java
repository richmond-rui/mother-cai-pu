package com.lanlengran.mycookbook.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lanlengran.mycookbook.R;

/**
 * Created by dobest on 2016/9/28.
 */

public class BaseFragment extends Fragment {
   protected Context mContext;
    private Toolbar toolbar;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext =getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext =null;
    }
    protected void showActivity(Class cla){
        Intent it=new Intent(mContext,cla);
        startActivity(it);
    }

    protected void initTitle(View view,String title){
//        findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        titleTV= (TextView) findViewById(R.id.title_name);
//        titleTV.setText(title);
        toolbar= (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setNavigationIcon(R.drawable.icon_back);
    }
}
