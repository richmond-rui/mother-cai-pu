package com.lanlengran.chinacaipu.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lanlengran.chinacaipu.R;
import com.lanlengran.chinacaipu.goods.CaiPuStep;
import com.lanlengran.chinacaipu.net.DetailsPageHtml;
import com.lanlengran.chinacaipu.utils.HtmlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends Activity {
    private String goodsurl;
    private List<CaiPuStep> detailsList;
    private ListView lv;
    private Handler handler;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        lv=(ListView) this.findViewById(R.id.details_ListView1);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        goodsurl=bundle.getString("goodsurl");

        new Thread(){

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                super.run();
                flashUL();
            }
        }.start();
        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO 自动生成的方法存根
                super.handleMessage(msg);
                if(msg.what==0){
                    HtmlUtil.println("handler 触发了。。。。。。。。。。。。。。。。");
                    dialog=CreatLoadDialog();
                }else{
                    HtmlUtil.Toastshow(DetailsActivity.this, "很抱歉！暂时无内容哦，请选择其他菜谱");
                }


            }
        };
    }
    private AlertDialog CreatLoadDialog(){
        LayoutInflater inflater=getLayoutInflater();
        View Layout=inflater.inflate(R.layout.html_loading_dialog, null);
        TextView tv=(TextView) Layout.findViewById(R.id.htm_loading_tv);
        ProgressBar pb=(ProgressBar) Layout.findViewById(R.id.html_loading_progressBar);

        tv.setText("我~~正~~在~~\n努~~力~~加~~载~~中~~哦！\n");
        AlertDialog.Builder builder=new AlertDialog.Builder(DetailsActivity.this);
        builder.setView(Layout);
        builder.create();
        final AlertDialog dialog=builder.show();
        return dialog;

    }
    private void flashUL() {
        HtmlUtil.println(goodsurl);
        handler.sendEmptyMessage(0);
        detailsList= DetailsPageHtml.getHtmlContent(goodsurl);
        if(detailsList.size()==0){
            detailsList=DetailsPageHtml.getHtmlContent2(goodsurl);
            if(detailsList.size()==0){
                dialog.dismiss();
                handler.sendEmptyMessage(1);
                finish();
            }
        }


        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
        for(CaiPuStep c:detailsList){
            Map m=new HashMap<>();
            m.put("i", c.getI());
            m.put("Practice", c.getPractice());
            if(c.getImage()!=null)
                m.put("image", HtmlUtil.getBitmap(c.getImage()));
            else
                m.put("image", null);
            mapList.add(m);
        }
        //Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)


        final SimpleAdapter adapter=new SimpleAdapter(DetailsActivity.this, mapList, R.layout.details_item
                , new String[]{"Practice","image"},
                new int[]{R.id.details_item_text01,R.id.details_item_image});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder(){

            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO 自动生成的方法存根
                if( (view instanceof ImageView) & (data instanceof Bitmap) ) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }

                return false;
            }



        });
        lv.post(new Runnable() {

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                lv.setAdapter(adapter);
            }
        });
        dialog.dismiss();
    }


}
