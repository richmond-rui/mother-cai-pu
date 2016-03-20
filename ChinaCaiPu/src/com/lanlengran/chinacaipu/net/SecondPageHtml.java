package com.lanlengran.chinacaipu.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lanlengran.chinacaipu.goods.Goods;
import com.lanlengran.chinacaipu.utils.HtmlUtilActivity;

public class SecondPageHtml {
	private static Document document=null;
/**
 * 输入html网页内容，返回一个包含goods的List数组
 * @param htmlContent
 * @return List<Goods>
 */
	public static List<Goods> getHtmlContent(String htmlUrl){
		List<Goods> goodList=new ArrayList<Goods>();
		
		try {
			document = Jsoup.connect(htmlUrl).get();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	//	HtmlUtilActivity.println(htmlContent);
		Elements ele_a=document.select("ul.c_conlist");
		Document doc_a=Jsoup.parse(ele_a.toString());
		
        Elements ele_b = doc_a.select("li");    
        Document doc_b=Jsoup.parse(ele_b.toString());

        Elements ele_c = doc_b.select("div > a");
        Document doc_c=Jsoup.parse(ele_b.toString());
        
        Elements ele_d = doc_c.select("img");

        /**
         * 取得标签为font的文件
         */
        Elements ele_e = doc_b.getElementsByTag("font");
//        for(Element e:ele_e){
//        	HtmlUtilActivity.println(e.text());
//		}
        /**
         * 从ele_c中取得菜品的链接地址，从ele_d中取得菜品的名称，图片，从ele_e中取得菜品的介绍
         */
        for(int i=0;i<ele_d.size();i++){
        	Goods good=new Goods();
        	good.setGoodsurl(ele_c.get(i).attr("href"));
        	good.setName(ele_d.get(i).attr("alt"));
        	good.setImage(ele_d.get(i).attr("src"));
        	good.setIntroduce(ele_e.get(i).text());
        	goodList.add(good);
        }
//        for(Goods g:goodList){
//        	HtmlUtilActivity.println(g.getName());
//        	HtmlUtilActivity.println(g.getGoodsurl());
//        	HtmlUtilActivity.println(g.getImage());
//        	HtmlUtilActivity.println(g.getIntroduce());
//        }
		return goodList;
	}
	
}
