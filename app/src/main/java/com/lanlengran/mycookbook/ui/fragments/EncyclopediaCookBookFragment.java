package com.lanlengran.mycookbook.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lanlengran.mycookbook.R;
import com.lanlengran.mycookbook.adapter.CookBookItemAdapter;
import com.lanlengran.mycookbook.bean.CookBookItemBean;
import com.lanlengran.mycookbook.presenter.NormalCookBookPresenter;
import com.lanlengran.mycookbook.ui.MVPBaseFragment;
import com.lanlengran.mycookbook.ui.activity.EncyclopediaCookDeatilActivity;
import com.lanlengran.mycookbook.ui.iFragments.INormalCookBookFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EncyclopediaCookBookFragment extends MVPBaseFragment<INormalCookBookFragment, NormalCookBookPresenter> implements INormalCookBookFragment {


    @BindView(R.id.progeressbar)
    ProgressBar progeressbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private List<CookBookItemBean> cookBookItemBeenList;
    private boolean isfresh = true;
    private LinearLayoutManager linearLayoutManager;
    private int childCount;
    private int totalCount;
    private int lastCount;

    public EncyclopediaCookBookFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_normal_cook_book, container, false);
        ButterKnife.bind(this, view);
        initView();
        mPresenter.refresh();
        return view;
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        cookBookItemBeenList = new ArrayList<>();
        CookBookItemAdapter adapter = new CookBookItemAdapter(mContext, cookBookItemBeenList, EncyclopediaCookDeatilActivity.class);
        recyclerview.setAdapter(adapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //   Log.i("qin", "dx=" + dx + ",dy=" + dy);
                if (dy > 0) {
                    childCount = linearLayoutManager.getChildCount();
                    totalCount = linearLayoutManager.getItemCount();
                    lastCount = linearLayoutManager.findFirstVisibleItemPosition();
//                    Log.i("qin", "childCount" + childCount);
//                    Log.i("qin", "totalCount" + totalCount);
//                    Log.i("qin", "lastCount" + lastCount);

                    if (!mPresenter.isLoading && (childCount + lastCount) >= totalCount) {
                        mPresenter.getDate();
//                        Toast.makeText(mContext, "加载下一页", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isfresh = true;
                mPresenter.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected NormalCookBookPresenter createPresenter() {
        return new NormalCookBookPresenter();
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
        Snackbar.make(view, getResources().getText(R.string.网络错误), Snackbar.LENGTH_LONG).
                setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isfresh)
                            mPresenter.refresh();
                        else
                            mPresenter.getDate();
                    }
                }).show();
    }

    @Override
    public void getSuccess(List<CookBookItemBean> lists) {
        //    Snackbar.make(view, getResources().getText(R.string.加载成功), Snackbar.LENGTH_SHORT).show();
        if (isfresh) {
            cookBookItemBeenList.clear();
            isfresh = false;
            Snackbar.make(view, getResources().getText(R.string.刷新完成), Snackbar.LENGTH_SHORT).show();
        }else {
            Snackbar.make(view, getResources().getText(R.string.加载成功), Snackbar.LENGTH_SHORT).show();
        }


        for (CookBookItemBean cookBookItemBean : lists) {
            cookBookItemBeenList.add(cookBookItemBean);
        }
        recyclerview.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void alreadLoading() {
        Snackbar.make(view, getResources().getText(R.string.已经加载), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public String getHomePageURL() {
        return "http://www.chinacaipu.com/menu/chufang/";
    }
}
