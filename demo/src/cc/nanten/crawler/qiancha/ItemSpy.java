/**
 * ItemSpy.java
 *
 * Copyright (c) 2015, 2016, GZECC and/or its affiliates. All rights reserved.
 * GZECC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @history
 * Rev    Date        Rev by      Reason
 * PA1    2016-7-4     junhong     Created.
 */
package com.gogbuy.crawler.qiancha;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gogbuy.crawler.chajiecn.StateResetUtil;

/**
 * @author junhong
 * @date   2016-7-4
 * @version 0.1
 */
public class ItemSpy {
	private static final Logger logger = Logger.getLogger(ItemSpy.class); 
	
	private static final String UA = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0";

	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		System.setProperty("http.maxRedirects", "50");
//		System.getProperties().setProperty("proxySet", "true");
		// 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
//		SpiderProxyUtil.changeProxy();
//		System.getProperties().setProperty("http.proxyHost", "61.162.223.41");
//		System.getProperties().setProperty("http.proxyPort", "9797");
		

//		String start_point="http://www.qiancha.cn/category-items-sort-default-p-"; //&page=1
		String start_point="http://www.qiancha.cn/search.php?encode=";
		int pageCount = 7;
		
		try {
			retieveProductPage(start_point, StateResetUtil.getCrawledPage("qianchacrawledpages"), pageCount);
		} catch (IOException e) {
//			System.getProperties().setProperty("http.proxyHost", "123.115.163.21");
//			System.getProperties().setProperty("http.proxyPort", "9999");
//			SpiderProxyUtil.changeProxy();
			retieveProductPage(start_point, StateResetUtil.getCrawledPage("qianchacrawledpages"), pageCount);
		}
	}

	private static void retieveProductPage(String pageUrl, int begin, int end) throws IOException {

		Document page = null;
		logger.debug("|商品名称|商品链接|商品售价|市场价|商品销量|评论量|关注人气");
		for(int idx = begin; idx<end+1; idx++) {
			logger.info("Getting product list of page: " + idx);
			StateResetUtil.setCrawledPage("qianchacrawledpages", idx);
//			String curUrl = pageUrl + idx + ".html";
			String curUrl = pageUrl + encodeReq(idx);
			page = Jsoup.connect(curUrl).userAgent(UA).get();  //using .proxy(arg0, arg1) intead of System..setProperties(ip and port)
//			Elements itemBoxes = page.getElementById("J_list").getElementsByTag("li");
			Elements itemBoxes = page.getElementsByClass("list_pic").get(0).getElementsByClass("item");
			for(Element boxItem: itemBoxes) {
				Element itemLink = boxItem.select("a[href]").get(1);
				
				String itemUrl = itemLink.attr("href");
				String itemName = itemLink.attr("title");
				
				Element ele = boxItem.getElementsByClass("goods-price").get(0);
				String price = ele.select("em.sale-price").text();
				String marcketPrice = ele.select("em.market-price").text();
				
				ele = boxItem.getElementsByClass("sell-stat").get(0);
			    String order = ele.select("a[href]").get(0).text();
			    String comments = ele.select("a[href]").get(1).text();
			    String fans = ele.select("a[href]").get(2).text();
			    
//				Element itemLink = boxItem.select("a[href]").get(0);
//				
//				String itemUrl = itemLink.attr("href");
//				String itemName = itemLink.attr("title");
//				
//				Element ele = itemLink.getElementsByClass("price").get(0);
//				String price = ele.select("span.f-l").text();
//				
//				ele = itemLink.getElementsByClass("comment").get(0);
//			    String order = ele.select("em.f-r i").text();
			    
			    logger.debug("|"+itemName + "|" +itemUrl + "|"+price + "|"+marcketPrice + "|"+order + "|"+comments + "|"+fans);
			    
//				retieveItemInfo(itemUrl);
//				try {
//					Thread.sleep(new java.util.Random().nextInt()%8192+20000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
		}
		logger.info("All jobs finished!");
	}

	private static String encodeReq(int idx) {
		StringBuilder sb = new StringBuilder();
		sb.append("a:14:{s:8:\"keywords\";s:0:\"\";s:8:\"category\";s:1:\"0\";s:5:\"brand\";s:1:\"0\";s:4:\"sort\";s:11:\"last_update\";s:5:\"order\";s:3:\"ASC\";s:9:\"min_price\";s:1:\"0\";s:9:\"max_price\";s:1:\"0\";s:6:\"action\";s:0:\"\";s:5:\"intro\";s:0:\"\";s:10:\"goods_type\";s:1:\"0\";s:5:\"sc_ds\";s:1:\"0\";s:8:\"outstock\";s:1:\"0\";s:4:\"page\";s:1:\"");
		sb.append(idx);
		sb.append("\";s:18:\"search_encode_time\";i:");
		sb.append(new java.util.Date().getTime());
		sb.append(";}");
		
		return (new sun.misc.BASE64Encoder()).encode( sb.toString().getBytes() ).replaceAll("\r\n", ""); 
	}
	
}
