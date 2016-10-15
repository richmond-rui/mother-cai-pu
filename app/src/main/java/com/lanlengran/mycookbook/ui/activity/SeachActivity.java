package com.lanlengran.mycookbook.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanlengran.mycookbook.R;
import com.lanlengran.mycookbook.ui.fragments.SeachCookBookFragment;

public class SeachActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        initView();
        initFragment();
    }

    private void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.seach_framelayout, new SeachCookBookFragment()).commit();

    }

}
