package com.lanlengran.chinacaipu.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lanlengran.chinacaipu.goods.CaiPuStep;
import com.lanlengran.chinacaipu.utils.HtmlUtilActivity;

public class DetailsPageHtml {
/**
 * 输入html网页内容，返回一个包含CaiPuStep的List数组
 * @param htmlContent
 * @return List<CaiPuStep>
 */
	public static List<CaiPuStep> getHtmlContent(String htmlUrl){
		HtmlUtilActivity.println("开始解析Html"+htmlUrl);
		List<CaiPuStep> stepList=new ArrayList<CaiPuStep>();
	//	Document document=Jsoup.parse(htmlContent);
		Document document=null;
		try {
			document = Jsoup.connect(htmlUrl).get();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			
		}
		Elements ele_a=document.select("div.cp-show-main-step-bd");
		Document doc_a=Jsoup.parse(ele_a.toString());
		
        Elements ele_b = doc_a.select("div.summary");    
        Document doc_b=Jsoup.parse(ele_b.toString());

        Elements ele_c = doc_a.select("img[src$=.jpg]");
       Document doc_c=Jsoup.parse(ele_b.toString());
        
        Elements ele_d = document.select("table.cp-show-tab");
        
        Elements ele_e = document.select("img.cp-show-pic");
        Document doc_e=Jsoup.parse(ele_e.toString());
        Elements ele_f = doc_e.select("img[src$=.jpg]");
      
        
        HtmlUtilActivity.println("++++++++++++++++++++++\n"+ele_f.toString());
        StringBuffer sb=new StringBuffer(); 
        for(Element e:ele_d){
        //	HtmlUtilActivity.println(e.attr("src"));
        	sb.append(e.text()+"\n");
        }
        if(ele_b.size()!=0){
            CaiPuStep cp0=new CaiPuStep();
        	cp0.setI(0);
        	cp0.setPractice(sb.toString());
        	cp0.setImage(ele_f.get(0).attr("src"));
        	stepList.add(cp0);
        }

        for(int i=0;i<ele_b.size();i++){
        	CaiPuStep cp=new CaiPuStep();
        	cp.setI(i+1);
        	cp.setPractice(ele_b.get(i).text());
        	cp.setImage(ele_c.get(i).attr("src"));
        	stepList.add(cp);
        }
        for(CaiPuStep c:stepList){
        	HtmlUtilActivity.println(c.getI()+"");
//        	HtmlUtilActivity.println(c.getPractice());
//        	HtmlUtilActivity.println(c.getImage());
        }
        HtmlUtilActivity.println("结束解析Html");
		return stepList;
	}
	public static List<CaiPuStep> getHtmlContent2(String htmlUrl){
		HtmlUtilActivity.println("开始解析Html"+htmlUrl);
		List<CaiPuStep> stepList=new ArrayList<CaiPuStep>();
	//	Document document=Jsoup.parse(htmlContent);
		Document document=null;
		try {
			document = Jsoup.connect(htmlUrl).get();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			
		}
		Elements ele_a=document.select("div.content");
		Document doc_a=Jsoup.parse(ele_a.toString());
		
        Elements ele_b = doc_a.select("p");    
        Document doc_b=Jsoup.parse(ele_b.toString());

        Elements ele_c = doc_b.select("img[src$=.jpg]");
       Document doc_c=Jsoup.parse(ele_b.toString());
        
        Elements ele_d = doc_c.getElementsByTag("img");
        
        HtmlUtilActivity.println(ele_c.toString());
//        for(Element e:ele_c){
//        	HtmlUtilActivity.println(e.attr("src"));
//        }
        StringBuffer sb=new StringBuffer();
        for(int i=1;i<ele_b.size();i++){
        	
        	sb.append(ele_b.get(i).text()+"\n");
        	if(i%4==0){
        		CaiPuStep cp=new CaiPuStep();
        		cp.setI(i/4);
        		cp.setPractice(sb.toString());
        		if(i/4<ele_c.size())
        		cp.setImage(ele_c.get(i/4).attr("src"));
        		else
        		cp.setImage(null);
        		stepList.add(cp);
        		sb=new StringBuffer();
        	}        	
        }
//        for(Element  e:ele_c){
//        	HtmlUtilActivity.println(e.attr("src"));
////        	HtmlUtilActivity.println(c.getPractice());
////        	HtmlUtilActivity.println(c.getImage());
//        }
        HtmlUtilActivity.println("结束解析Html");
		return stepList;
	}
}
