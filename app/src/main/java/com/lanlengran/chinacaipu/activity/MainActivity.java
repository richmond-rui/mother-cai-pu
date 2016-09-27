package com.lanlengran.chinacaipu.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lanlengran.chinacaipu.BaseActivity;
import com.lanlengran.chinacaipu.R;
import com.lanlengran.chinacaipu.net.HomePageHtml;
import com.lanlengran.chinacaipu.utils.HtmlUtil;

import java.io.IOException;
import java.util.List;

public class MainActivity extends BaseActivity {
private ListView lv;
private EditText et;
private List<String> titleList,titleUrlList;
private boolean isBtnDown=true;
private String urlStr,htmlContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.activity_first_page);

		//判断当前网络是否可用
		if(isNetWorkConnected()){
			
			setContentView(R.layout.activity_first_page);
			initTitle("妈妈的菜谱");

			lv=(ListView) MainActivity.this.findViewById(R.id.main_ListView1);
			
			new MyThread().start(); 
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO 自动生成的方法存根
					Intent intent=new Intent(MainActivity.this,SecondActivity.class);
					intent.putExtra("selectUrl", titleUrlList.get(arg2));
					intent.putExtra("selectTitle", titleList.get(arg2));
					startActivity(intent);
				}
			});
		}else{
			HtmlUtil.println("网络不可用");
			SetNetWorkDialog();
		}


	}
	 /**
	  * 判断网络状态
	  */
	private boolean isNetWorkConnected(){
		ConnectivityManager manager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info=manager.getActiveNetworkInfo();
		
		HtmlUtil.println("判断网络");
		boolean result;
		return (info!=null&&info.isConnected());
	}
	private void SetNetWorkDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("设置网络 ");
		builder.setMessage("网络错误请检查网络状态");
		builder.setPositiveButton("设置网络",new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent();
//			//	intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
//				startActivity(intent);
//				finish();
				Intent intent = new Intent();
				//类名一定要包含包名 
				intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
				
				startActivity(intent);
				finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		builder.create();
		builder.show();
	}
	class MyThread extends Thread{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			super.run();
			while(true)
			while (isBtnDown) {
				try {

					htmlContent = HtmlUtil
							.GetHtmlString("http://www.chinacaipu.com");
				//	List<String> list=new ArrayList<String>();
					titleList=HomePageHtml.getTitle(htmlContent);
					titleUrlList=HomePageHtml.getTitleUrl(htmlContent);
					final ListAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, titleList);
					HtmlUtil.println(""+titleList.size());
					lv.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO 自动生成的方法存根
							lv.setAdapter(adapter);
						}
					});
					
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				isBtnDown=false;
				
		//		String title=document.head().getElementsByTag("")
			}
		}
		
	}
}
