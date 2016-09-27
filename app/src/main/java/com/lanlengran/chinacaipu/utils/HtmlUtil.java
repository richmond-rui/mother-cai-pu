package com.lanlengran.chinacaipu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 负责Html解析获取等工作
 * @author ruiqin
 *
 */
public class HtmlUtil {
    /**
     * 根据传递的url地址取得String 文档
     * @param url_str
     * @return
     * @throws IOException
     */
    public static String GetHtmlString(String url_str) throws IOException{
        URL url=new URL(url_str);
        URLConnection connection=url.openConnection();
        InputStream is=connection.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        StringBuffer sb=new StringBuffer();
        String tem;
        while((tem=br.readLine())!=null){
            sb.append(tem+"\n");
        }
        br.close();
        return sb.toString();
    }
    public static void println(String str){
       // System.out.println(str);
        Log.i("qin",str);
    }
    public static void Toastshow(Context context,String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
    public static Bitmap getBitmap(String BitmapUrl){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(BitmapUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

}
