package com.lanlengran.mycookbook.net;

import android.util.Log;

import com.lanlengran.mycookbook.bean.CookBookItemBean;
import com.lanlengran.mycookbook.bean.CookDetailBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetUtil {
    private static Document document = null;
    private static int nowPage=1;

    /**
     * 输入html网页内容，返回一个包含CookBookItemBean的List数组
     *
     * @param htmlUrl
     * @return List<CookBookItemBean>
     */
    public static List<CookBookItemBean> getCookItemContent(String htmlUrl) throws IOException {
        List<CookBookItemBean> goodList = new ArrayList<CookBookItemBean>();

        Log.i("qin","加载的网址："+htmlUrl);
        document = Jsoup.connect(htmlUrl).get();


        //	HtmlUtil.println(htmlContent);
        Elements ele_a = document.select("ul.c_conlist");
        Document doc_a = Jsoup.parse(ele_a.toString());

        Elements ele_b = doc_a.select("li");
        Document doc_b = Jsoup.parse(ele_b.toString());

        Elements ele_c = doc_b.select("div > a");
        Document doc_c = Jsoup.parse(ele_b.toString());

        Elements ele_d = doc_c.select("img");

        /**
         * 取得标签为font的文件
         */
        Elements ele_e = doc_b.getElementsByTag("font");

        /**
         * 从ele_c中取得菜品的链接地址，从ele_d中取得菜品的名称，图片，从ele_e中取得菜品的介绍
         */
        for (int i = 0; i < ele_d.size(); i++) {
            CookBookItemBean good = new CookBookItemBean();
            good.setGoodsUrl(ele_c.get(i).attr("href"));
            good.setTitle(ele_d.get(i).attr("alt"));
            good.setImgUrl(ele_d.get(i).attr("src"));
            good.setInfo(ele_e.get(i).text());
            goodList.add(good);
            Log.i("qin","html:"+good.getGoodsUrl());
        }

        return goodList;
    }
    /**
     * 输入html地址，返回一个包含goods的List数组
     *
     * @param htmlUrl
     * @return List<CookDetailBean>
     */
    public static List<CookDetailBean> getCookDetail(String htmlUrl) throws IOException {
        List<CookDetailBean> cookDetailList = new ArrayList<CookDetailBean>();



            document = Jsoup.connect(htmlUrl).get();


        //       Log.i("qin",document.toString());
//        //	HtmlUtil.println(htmlContent);


        Elements stepEleBA = document.select("[class=cp-show-tab]");
        if (stepEleBA.toString().length()<2){
            Elements stepEleAA = document.select("[class=content]");
            String stepEleAC=stepEleAA.toString().replace("img","p");
            Elements stepEleAB =  Jsoup.parse(stepEleAC).select("p");
            String stepEleAD=stepEleAB.toString().replace("src=\"",">");
            String stepEleAE=stepEleAD.toString().replace("\" onload=","<onload=");
            Elements stepEleAF= Jsoup.parse(stepEleAE).select("p");
           // Log.i("qin","stepEleAB\n"+stepEleAE.size());
            for (int i=0;i<stepEleAF.size();i++){
                String content=stepEleAF.get(i).text();
                if (content==null||content.length()==0){
                    continue;
                }
                int k=content.indexOf("jpg");
                if (k>0){
                    cookDetailList.add(new CookDetailBean(content,true));
                }else {
                    cookDetailList.add(new CookDetailBean(content,false));
                }
            }
            int resule0=stepEleAA.toString().indexOf("文章推荐");
            int resule1=stepEleAA.toString().indexOf("小贴士");
            if (resule0<0&&resule1<0){
                nowPage=1;
                cookDetailList=getCookDetailSecPage(htmlUrl,cookDetailList);
            }
         //   Log.i("qin","stepEleAB\n"+stepEleAF.toString());
        }else {
            //获取菜品描述
            Elements ele_a = document.select("[name=description]");

            String info  = ele_a.get(0).attr("content");
            cookDetailList.add(new CookDetailBean(info,false));


            Elements stepEleBB = stepEleBA.select("th,td");
            StringBuffer sb=new StringBuffer();

            for (Element e: stepEleBB){
                String context=e.text().replace(" ","             ");
                sb.append(context+"\n");
            }
            Log.i("qin","sb\n"+sb.toString());
            cookDetailList.add(new CookDetailBean(sb.toString(),false));

            Elements stepEleBC = document.select("[class=cp-show-main-t1],[class=cp-show-main-step],[class=cp-show-main-trick]");
            String stepEleBD=stepEleBC.toString().replace("img src=\"","div class=\"summary\">");
            String stepEleBE=stepEleBD.toString().replace("jpg\">","jpg</div>")
//                    .replace("<div class=\"cp-show-main-step\">","")
//                    .replace("<div class=\"cp-show-main-step-bd\">","")
//                    .replace("<div class=\"cp-show-main-step-item\">","")

                    ;
            Document doc_BF = Jsoup.parse(stepEleBE.toString());
            Elements stepEleBG = doc_BF.select("[class=summary],img");
            for (int i=0;i<stepEleBG.size();i++){
                String content=stepEleBG.get(i).text();
            //    Log.i("qin",i+"content\n"+content.toString());
                if (content==null||content.length()==0){
                    continue;
                }
                int k=content.indexOf("jpg");
                if (k>0){
                    cookDetailList.add(new CookDetailBean(content,true));
                }else {
                    int j=content.indexOf("gif");
                    if (j>0){
//                        Log.i("qin","gif="+content);
                        content=content.replace("\">","");
//                        Log.i("qin","gif======="+content);
                        cookDetailList.add(new CookDetailBean(content,true));
                    }else {
                        cookDetailList.add(new CookDetailBean(content,false));
                    }

                }
            }
//            Log.i("qin","doc_BF\n"+doc_BF.toString());
//            Log.i("qin","stepEleBG\n"+stepEleBG.toString());

        }
//
//        Elements ele_c = doc_b.select("div > a");
//        Document doc_c = Jsoup.parse(ele_b.toString());
//
//        Elements ele_d = doc_c.select("img");

   //     Log.i("qin","stepEleA"+stepEleA.toString());
 //       Log.i("qin","info"+info);
        return cookDetailList;
    }

    /**
     * 输入html地址，返回一个包含goods的List数组
     *
     * @param htmlUrl
     * @return List<CookDetailBean>
     */
    public static List<CookDetailBean> getCookDetailSecPage(String htmlUrl, List<CookDetailBean> cookDetailList)  {


        try {
            nowPage++;
           String newUrl=htmlUrl.replace(".html","_"+nowPage+".html");
        //    Log.i("qin","新UIL"+newUrl);
            document = Jsoup.connect(newUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //       Log.i("qin",document.toString());
//        //	HtmlUtil.println(htmlContent);


        Elements stepEleBA = document.select("[class=cp-show-tab]");

            Elements stepEleAA = document.select("[class=content]");
            String stepEleAC=stepEleAA.toString().replace("img","p");
            Elements stepEleAB =  Jsoup.parse(stepEleAC).select("p");
            String stepEleAD=stepEleAB.toString().replace("src=\"",">");
            String stepEleAE=stepEleAD.toString().replace("\" onload=","<onload=");
            Elements stepEleAF= Jsoup.parse(stepEleAE).select("p");
            // Log.i("qin","stepEleAB\n"+stepEleAE.size());
            for (int i=0;i<stepEleAF.size();i++){
                String content=stepEleAF.get(i).text();
                if (content==null||content.length()==0){
                    continue;
                }
                int k=content.indexOf("jpg");
                if (k>0){
                    cookDetailList.add(new CookDetailBean(content,true));
                }else {
                    cookDetailList.add(new CookDetailBean(content,false));
                }
            }
        int resule0=stepEleAA.toString().indexOf("文章推荐");
        int resule1=stepEleAA.toString().indexOf("小贴士");
        if (resule0<0&&resule1<0){
            if (nowPage<=4)
            cookDetailList=getCookDetailSecPage(htmlUrl,cookDetailList);
        }
//            Log.i("qin","stepEleAB\n"+stepEleAF.toString());

        return cookDetailList;
    }
}
