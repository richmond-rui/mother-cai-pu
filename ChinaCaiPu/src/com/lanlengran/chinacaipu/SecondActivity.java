package com.lanlengran.chinacaipu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.lanlengran.chinacaipu.goods.Goods;
import com.lanlengran.chinacaipu.net.SecondPageHtml;
import com.lanlengran.chinacaipu.utils.HtmlUtilActivity;

public class SecondActivity extends Activity {
	private String url_str;   //需要加载的Url
	private String first_url_str; //首页的url
	private ListView lv;
	private TextView tv,page_tv;
	private Button btn_up,btn_next;
	private List<Goods> goodsList;
	private Handler handler,handler2;
	private int now_page; //现在所在页数
	private boolean isFlash; //判断需要刷新与否
	private AlertDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		init();
		new FlashULThread().start();
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 自动生成的方法存根
				Intent it=new Intent(SecondActivity.this,DetailsActivity.class);
				it.putExtra("name", goodsList.get(arg2).getName());
				it.putExtra("goodsurl", goodsList.get(arg2).getGoodsurl());
				startActivity(it);
			}
		});
		
		btn_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if(now_page==1)
					HtmlUtilActivity.Toastshow(SecondActivity.this, "已经到首页了哦！");
				else{
					now_page--;
					url_str=first_url_str+"index_"+now_page+".html";
					isFlash=true;
				}
				
			}
		});
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				now_page++;
				url_str=first_url_str+"index_"+now_page+".html";
				isFlash=true;
			}
		});
		
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO 自动生成的方法存根
				super.handleMessage(msg);
				
				HtmlUtilActivity.println("handler 触发了。。。。。。。。。。。。。。。。");
					 dialog=CreatLoadDialog();
				
				
			}
			
		};
	}
	private AlertDialog CreatLoadDialog(){
		LayoutInflater inflater=getLayoutInflater();
		View Layout=inflater.inflate(R.layout.html_loading_dialog, null);
		TextView tv=(TextView) Layout.findViewById(R.id.htm_loading_tv);
		ProgressBar pb=(ProgressBar) Layout.findViewById(R.id.html_loading_progressBar);
		
		tv.setText("我~~正~~在~~\n努~~力~~加~~载~~中~~哦！\n");
		AlertDialog.Builder builder=new Builder(SecondActivity.this);
		builder.setView(Layout);
		builder.create();
		final AlertDialog dialog=builder.show();
		return dialog;
		
	}
	private void init() {
		now_page=1;
		isFlash=true;
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		tv=(TextView) this.findViewById(R.id.second_textView);
		page_tv=(TextView) this.findViewById(R.id.second_page_tv);
		lv=(ListView) this.findViewById(R.id.second_ListView1);
		btn_up=(Button) this.findViewById(R.id.second_button_last);
		btn_next=(Button) this.findViewById(R.id.second_button_next);
		
		
		tv.setText(bundle.getString("selectTitle"));
		first_url_str=bundle.getString("selectUrl");
		HtmlUtilActivity.println(first_url_str);
	}
	private void flashUL() {
		if(now_page==1)
			url_str=first_url_str;
		goodsList=	SecondPageHtml.getHtmlContent(url_str);
		
	//	SecondActivity.this.CreatLoadDialog(0);
		handler.sendEmptyMessage(0);
		
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		for(Goods g:goodsList){
			Map m=new HashMap<>();
			m.put("name", g.getName());
			m.put("introduce", g.getIntroduce());
			m.put("image", HtmlUtilActivity.getBitmap(g.getImage()));
			mapList.add(m);
		}
		//Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
		

		final SimpleAdapter  adapter=new SimpleAdapter(SecondActivity.this, mapList, R.layout.select_item
				, new String[]{"name","introduce","image"}, 
				new int[]{R.id.select_item_text01,R.id.select_item_text02,R.id.select_item_image});
		  adapter.setViewBinder(new ViewBinder(){

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
		  page_tv.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				page_tv.setText("第"+now_page+"页");
			}
		});
	//	  handler2.sendEmptyMessage(0);
		  dialog.dismiss();
	}
	class FlashULThread extends Thread{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			super.run();
			while(true){
				if(isFlash){
					flashUL();
					isFlash=false;
				}
				
			}
					
		}		
	}

}
