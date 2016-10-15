package com.lanlengran.mycookbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lanlengran.mycookbook.AppConfig;
import com.lanlengran.mycookbook.R;
import com.lanlengran.mycookbook.bean.CookBookItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dobest on 2016/9/28.
 */

public class CookBookItemAdapter extends RecyclerView.Adapter {

    private List<CookBookItemBean> cookBookItemBeenList;
    private Context mContext;
    private Class clz;
    private int width;
    private Animation animation;
    private long lastTime=0;

    public CookBookItemAdapter(Context context, List<CookBookItemBean> cookBookItemBeen,Class clz) {
        this.cookBookItemBeenList = cookBookItemBeen;
        mContext = context;
        this.clz=clz;
        WindowManager wm= (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        width=wm.getDefaultDisplay().getWidth();
        animation=new TranslateAnimation(width,0,0,0);
        animation.setDuration(500);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (cookBookItemBeenList.get(position).getTitle().equals("test")){
            ((MyViewHolder)holder).itemView.setVisibility(View.GONE);
        }
        ((MyViewHolder)holder).title.setText(cookBookItemBeenList.get(position).getTitle());
        ((MyViewHolder)holder).info.setText(cookBookItemBeenList.get(position).getInfo());
        ((MyViewHolder) holder).imageView.setImageResource(R.drawable.image_loading);
        if (AppConfig.getHasImage(mContext)){
            Glide.with(mContext).load(cookBookItemBeenList.get(position).getImgUrl()).into(((MyViewHolder) holder).imageView);
        }

        ((MyViewHolder)holder).itemView.setTag(position);
//        ((MyViewHolder)holder).itemView.setAnimation(animation);
//        animation.start();
        if((System.currentTimeMillis()-lastTime)>50)
        startAnima(((MyViewHolder)holder).itemView);
        lastTime=System.currentTimeMillis();
    }

    private void startAnima(View itemView) {
        itemView.setTranslationX(width);
        itemView.animate()
                .translationX(0)
                .setDuration(100)
                .setInterpolator(new DecelerateInterpolator(3f))
                .setDuration(700)
                .start();
    }

    @Override
    public int getItemCount() {
        return cookBookItemBeenList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        public ImageView imageView;
//        public TextView title;
//        public TextView info;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.momre_btn)
        ImageView momreBtn;
        @BindView(R.id.info)
        TextView info;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent it=new Intent(mContext, clz);
            it.putExtra("url",cookBookItemBeenList.get((Integer) v.getTag()).getGoodsUrl());
            it.putExtra("image",cookBookItemBeenList.get((Integer) v.getTag()).getImgUrl());
            it.putExtra("title",cookBookItemBeenList.get((Integer) v.getTag()).getTitle());
            mContext.startActivity(it);
        }
    }
}
