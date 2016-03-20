package com.lanlengran.chinacaipu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

import com.lanlengran.chinacaipu.goods.CaiPuStep;
import com.lanlengran.chinacaipu.net.DetailsPageHtml;
import com.lanlengran.chinacaipu.utils.HtmlUtilActivity;

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
				// TODO �Զ����ɵķ������
				super.run();
				flashUL();
			}
		}.start();
		
		this.findViewById(R.id.details_button_end).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				finish();
			}
		});
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO �Զ����ɵķ������
				super.handleMessage(msg);
				if(msg.what==0){
					HtmlUtilActivity.println("handler �����ˡ�������������������������������");
					 dialog=CreatLoadDialog();	
				}else{
					HtmlUtilActivity.Toastshow(DetailsActivity.this, "�ܱ�Ǹ����ʱ������Ŷ����ѡ����������");
				}
				
				  
			}	
		};
	}
	private AlertDialog CreatLoadDialog(){
		LayoutInflater inflater=getLayoutInflater();
		View Layout=inflater.inflate(R.layout.html_loading_dialog, null);
		TextView tv=(TextView) Layout.findViewById(R.id.htm_loading_tv);
		ProgressBar pb=(ProgressBar) Layout.findViewById(R.id.html_loading_progressBar);
		
		tv.setText("��~~��~~��~~\nŬ~~��~~��~~��~~��~~Ŷ��\n");
		AlertDialog.Builder builder=new Builder(DetailsActivity.this);
		builder.setView(Layout);
		builder.create();
		final AlertDialog dialog=builder.show();
		return dialog;
		
	}
	private void flashUL() {
		HtmlUtilActivity.println(goodsurl);
		handler.sendEmptyMessage(0);
		detailsList=DetailsPageHtml.getHtmlContent(goodsurl);
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
			m.put("image", HtmlUtilActivity.getBitmap(c.getImage()));
			else
				m.put("image", null);
			mapList.add(m);
		}
		//Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
		

		final SimpleAdapter  adapter=new SimpleAdapter(DetailsActivity.this, mapList, R.layout.details_item
				, new String[]{"Practice","image"}, 
				new int[]{R.id.details_item_text01,R.id.details_item_image});
		  adapter.setViewBinder(new ViewBinder(){

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO �Զ����ɵķ������
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
								// TODO �Զ����ɵķ������
								lv.setAdapter(adapter);
							}
			});
			dialog.dismiss();
	}

	
}
