package com.lanlengran.mycookbook.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.lanlengran.mycookbook.R;
import com.lanlengran.mycookbook.receiver.NetStateReceiver;
import com.lanlengran.mycookbook.ui.BaseActivity;
import com.lanlengran.mycookbook.ui.fragments.BakedCookBookFragment;
import com.lanlengran.mycookbook.ui.fragments.ChineseCookBookFragment;
import com.lanlengran.mycookbook.ui.fragments.EncyclopediaCookBookFragment;
import com.lanlengran.mycookbook.ui.fragments.NormalCookBookFragment;
import com.lanlengran.mycookbook.ui.fragments.OtherCookBookFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_fragment)
    FrameLayout mainFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private List<Fragment> fragments;
    private int selPos=-1;
    private NetStateReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();  //初始化fragment
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
//        ImageLoader.getInstance().init(configuration);
        registerReceiver();     //注册监听网络改变的广播接收器
    }

    private void initFragment() {
        /**
        新建一个fragment数组存储所要显示的所有要显示的，fragment方便虎门切换
         */
        fragments=new ArrayList<>();
        fragments.add(new NormalCookBookFragment());
        fragments.add(new BakedCookBookFragment());
        fragments.add(new ChineseCookBookFragment());
        fragments.add(new EncyclopediaCookBookFragment());
        fragments.add(new OtherCookBookFragment());
        switFragment(fragments.get(0),getResources().getString(R.string.家常菜谱),0);

    }

    private void initView() {
//        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //注册侧滑界面按钮监听
        navigationView.setNavigationItemSelectedListener(this);
        initTitle(getResources().getString(R.string.家常菜谱));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /**
         *根据点击按钮切换fragment
         */
        int id = item.getItemId();
        if (id == R.id.nav_normal_cookbook) {
            // Handle the camera action
            switFragment(fragments.get(0),getResources().getString(R.string.家常菜谱),0);
        } else if (id == R.id.nav_bakery_cookbook) {
            switFragment(fragments.get(1),getResources().getString(R.string.烘焙屋),1);
        } else if (id == R.id.nav_chinese_cookbook) {
            switFragment(fragments.get(2),getResources().getString(R.string.中国菜谱),2);
        } else if (id == R.id.nav_encyclopaedia_cookbook) {
            switFragment(fragments.get(3),getResources().getString(R.string.厨房百科),3);
        } else if (id == R.id.nav_other_cookbook) {
            switFragment(fragments.get(4),getResources().getString(R.string.外国菜谱),4);
        } else if (id == R.id.nav_share) {
            //实现分享功能
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "我正在用超级好用的《我的厨房》应用，你也来试试吧？下载地址：http://www.baidu.com");
            shareIntent.setType("text/plain");
            //设置分享列表的标题，并且每次都显示分享列表
            this.startActivity(Intent.createChooser(shareIntent, "分享到"));
        } else if (id == R.id.nav_setting) {
            Intent goSettingIt=new Intent(this,SettingActivity.class);
            startActivity(goSettingIt);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switFragment(Fragment fragment, String title,int pos) {
        if (pos==selPos){
            return;
        }
//        getSupportActionBar().setTitle(title);
        changeTitle(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,fragment).commit();
        selPos=pos;

    }

    private  void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver=new NetStateReceiver();
        this.registerReceiver(myReceiver, filter);
    }

    private  void unregisterReceiver(){
        this.unregisterReceiver(myReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁activity的时候取消网络监听
        unregisterReceiver();
    }
}
