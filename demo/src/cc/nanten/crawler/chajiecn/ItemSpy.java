/**
 * ItemSpy.java
 *
 * Copyright (c) 2015, 2016, Nanten and/or its affiliates. All rights reserved.
 * Nanten PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @history
 * Rev    Date        Rev by      Reason
 * PA1    2016-7-4     junhong     Created.
 */
package cc.nanten.crawler.chajiecn;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cc.nanten.crawler.chajiecn.service.CategoryPersist;
import cc.nanten.crawler.chajiecn.service.ItemPersist;

/**
 * @author junho.chen@outlook.com
 * @date   2016-7-4
 * @version 0.1
 */
public class ItemSpy {
	private static final Logger logger = Logger.getLogger(ItemSpy.class); 
	
	private static final String site = "http://chajiecn.com";
	private static final String ajaxGetStock = "http://chajiecn.com/Ajax/Product/GetStock.aspx?specproid=";
	private static final String UA = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0";

	private static CategoryPersist catService = new CategoryPersist();
	private static ItemPersist itemService = new ItemPersist();
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.setProperty("http.maxRedirects", "50");
		System.getProperties().setProperty("proxySet", "true");
		// 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		SpiderProxyUtil.changeProxy();
//		System.getProperties().setProperty("http.proxyHost", "61.162.223.41");
//		System.getProperties().setProperty("http.proxyPort", "9797");
		

		String start_point="http://www.chajiecn.com/product/product.aspx?TopCateID=0&page="; //&page=1
		int pageCount = 41;
		
		try {
			retieveProductPage(start_point, StateResetUtil.getCrawledPage("crawledpages"), pageCount);
		} catch (IOException e) {
//			System.getProperties().setProperty("http.proxyHost", "123.115.163.21");
//			System.getProperties().setProperty("http.proxyPort", "9999");
			SpiderProxyUtil.changeProxy();
			retieveProductPage(start_point, StateResetUtil.getCrawledPage("crawledpages"), pageCount);
		}
	}

	private static void retieveProductPage(String pageUrl, int begin, int end) throws IOException {

		/* debug fragment begin
		File input = new File("D:\\junhong\\chajiecn\\page.html");
		Document doc = Jsoup.parse(input, "UTF-8", "");
		Element productList = doc.getElementsByClass("product_list").get(0);
		Elements boxes = productList.select("h5.name");
		for(Element boxItem: boxes) {
			Element itemLink = boxItem.select("a[href]").get(0);
			String itemUrl = itemLink.attr("href");
			retieveItemInfo(itemUrl);
		}
		/* debug fragment end */
		
		/*
		 * <div class="product_list></div>
		 *   <ul>
		 *     <li>
		 *         <div class="picture></div>
		 *         <div class="price></div>
		 *         <div class="name>
		 *           <a href="/product/productinfo.aspx?id=1668" title="2015年老同志布朗春晓普洱熟茶357g/饼">
		 *             2015年老同志布朗春晓普洱熟茶357g/饼
		 *           </a>
		 *         </div>
		 *         <div class="pico></div>
		 *     </li>
		 *     ...<li></li>
		 *   </ul>
		 * 
		 */
		
		Document page = null;
		for(int idx = begin; idx<end+1; idx++) {
			logger.info("Getting product list of page: " + idx);
			StateResetUtil.setCrawledPage("crawledpages", idx);
			page = Jsoup.connect(pageUrl + idx).userAgent(UA).get();  //using .proxy(arg0, arg1) intead of System..setProperties(ip and port)
			Elements itemBoxes = page.getElementsByClass("name");
			for(Element boxItem: itemBoxes) {
				Element itemLink = boxItem.select("a[href]").get(0);
				String itemUrl = itemLink.attr("href");
				retieveItemInfo(itemUrl);
//				try {
//					Thread.sleep(new java.util.Random().nextInt()%8192+20000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
		}
		logger.info("All jobs finished!");
	}
	
	private static void retieveItemInfo(String itemUrl) throws IOException {
		logger.info("Getting item detail: "+itemUrl);
		if(itemService.isItemExisted(itemUrl)) {
			logger.info("Already existed!");
			return;
		}
		logger.info("Remote getting ... ");
		String itemId;
		String itemName;
		String marketPrice;
		String sellPrice;
		String sellPriceMax = null;
		String sellCount;
		String commentCount;
		String stock;
		String cat1Id = null;
		String cat2Id = null;
		String cat3Id = null;
		String shopId = null;
		int platformId = 2;
		
		String priceCommon;
		String pricePifa;
		String priceGold;
		String priceSilver;
		String priceDiamond;
		
		String categoryId;
		String categoryName;
		String categoryUrl;
		String parentId;
		
		itemId = itemUrl.split("=")[1];
		
		/* debug fragment begin 
		File input = new File("D:\\junhong\\chajiecn\\item.html");
		Document doc = Jsoup.parse(input, "UTF-8", "");
		/* debug fragment end */
		
		Document doc = Jsoup.connect(site + itemUrl).userAgent(UA).get();
		itemName = doc.getElementById("proInfoName").text();
		
		Element productInfo = doc.getElementsByClass("info").get(0); // 商品详情
		Elements info = productInfo.select("span.mp b");
		marketPrice = info.text();
		info = productInfo.select("span.sp b");
		
		sellPrice = info.get(0).text();
		priceCommon = info.get(1).text();
		pricePifa = info.get(2).text();
		priceGold = info.get(3).text();
		priceSilver = info.get(4).text();
		priceDiamond = info.get(5).text();
		
		stock = Jsoup.connect(ajaxGetStock+itemId).ignoreContentType(true).execute().body(); 
		
		info = doc.select("dl.salesvol dd b");
		sellCount = info.get(0).text();
		commentCount = info.get(1).text();
		
		Element category = doc.getElementsByClass("location").get(0);
		Elements links = category.getElementsByTag("a");
		categoryName = links.get(0).text();
		categoryUrl = links.get(0).attr("href");
		categoryId = categoryUrl.split("=")[2];
		parentId = "0";
		catService.saveCategory(categoryId, categoryName, categoryUrl, parentId, platformId);
		cat1Id = categoryId;
		
		if(links.size() > 1) {
		parentId = categoryId;
		categoryName = links.get(1).text();
		categoryUrl = links.get(1).attr("href");
		categoryId = categoryUrl.split("=")[2];
		catService.saveCategory(categoryId, categoryName, categoryUrl, parentId, platformId);
		cat2Id = categoryId;
		}
		if(links.size() > 2) {
			parentId = categoryId;
			categoryName = links.get(2).text();
			categoryUrl = links.get(2).attr("href");
			categoryId = categoryUrl.split("=")[2];
			catService.saveCategory(categoryId, categoryName, categoryUrl, parentId, platformId);
			cat3Id = categoryId;
			}
		
		itemService.saveItem(itemId, itemName, itemUrl, marketPrice, sellPrice, sellPriceMax, sellCount, commentCount, stock, cat1Id, cat2Id, cat3Id, shopId, platformId, priceCommon, pricePifa, priceGold, priceSilver, priceDiamond);
		
		logger.info("Done.");
	}
	
}
