/**
 * SpiderProxyUtil.java
 *
 * Copyright (c) 2015, 2016, Nanten and/or its affiliates. All rights reserved.
 * Nanten PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @history
 * Rev    Date        Rev by      Reason
 * PA1    2016-7-7     junhong     Created.
 */
package cc.nanten.crawler.chajiecn;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import cc.nanten.crawler.store.entity.ProxyIp;

/**
 * @author junho.chen@outlook.com
 * @date   2016-7-7
 * @version 0.1
 */
public class SpiderProxyUtil {
	private static Logger logger = Logger.getLogger(SpiderProxyUtil.class); 

	public static void changeProxy() throws IOException {
		logger.info("Try to change proxy for inet");
		ProxyIp proxy = getAvailableProxy();
		if(proxy == null) {
			logger.warn("Change inet proxy failed.");
		}
		
		System.getProperties().setProperty("http.proxyHost", proxy.getHost());
		System.getProperties().setProperty("http.proxyPort", proxy.getPort());
		
		logger.info("Change inet proxy success.");
	}
	
	public static ProxyIp getAvailableProxy() throws IOException {
		logger.info("Getting available proxy");
		
        String orderid="tid=**********";
        String num="num=2";
        String longlife="longlife=20";
        String format="format=xml";
        String url = "http://qsrdk.daili666api.com/ip/?"+orderid+"&"+num+"&"+longlife+"&"+format;
        
        Document res = Jsoup.connect(url).ignoreContentType(true).get(); 
        logger.debug(res);
        
        String ip = res.getElementsByTag("host").get(0).text();
        String port = res.getElementsByTag("port").get(0).text();
    	logger.info("IP: " + ip + " port: " + port);
    	
        ProxyIp proxy = null;
        if(!StringUtil.isBlank(ip) && !StringUtil.isBlank(port)) {
        	proxy = new ProxyIp(ip, port);
        }
        
		return proxy;
	}
	
	public static void main(String[] args) throws IOException {
		ProxyIp p = getAvailableProxy();
		Document res = Jsoup.connect("http://www.chajiecn.com/product/product.aspx?TopCateID=0&page=2").proxy(p.getHost(), Integer.valueOf(p.getPort())).get();  //using .proxy(arg0, arg1)
		logger.debug(res);
	}
}
