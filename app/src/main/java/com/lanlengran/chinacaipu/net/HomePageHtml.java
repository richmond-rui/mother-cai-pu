package com.lanlengran.chinacaipu.net;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HomePageHtml {

	public static Elements getHtmlContent(String htmlContent){
		Document document=Jsoup.parse(htmlContent);
		Elements ele_a=document.select("div.c_nav_m");
//		Log.i("qin",ele_a.toString());
//		HtmlUtil.println(ele_a.toString());
		Document doc_a=Jsoup.parse(ele_a.toString());
        Elements ele_b = doc_a.select("dt");
//		Log.i("qin",ele_b.toString());
        Document doc_b=Jsoup.parse(ele_b.toString());
        Elements ele_c = doc_b.select("a");
//		Log.i("qin",ele_c.toString());
		return ele_c;
	}
	public static List<String> getTitle(String htmlContent){
		List<String> list=new ArrayList<String>();
		Elements eles=getHtmlContent(htmlContent);
		for(int i=0;i<(eles.size()-1);i++){
			list.add(eles.get(i).text());
		}
//		for(Element e:eles){
//			list.add(e.text());
//		}
		return list;
	}
	public static List<String> getTitleUrl(String htmlContent){
		List<String> list=new ArrayList<String>();
		Elements eles=getHtmlContent(htmlContent);
		for(int i=0;i<(eles.size()-1);i++){
			list.add(eles.get(i).attr("href"));
		}
//		for(Element e:eles){
//			list.add(e.attr("href"));
//		}
		return list;
	}
}
