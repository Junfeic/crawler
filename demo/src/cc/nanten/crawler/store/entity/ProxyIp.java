package com.gogbuy.crawler.store.entity;

/**
 * Created by yopai on 2016/5/26.
 */
public class ProxyIp {
    private String host;
    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    public ProxyIp(String ip, String port) {
    	setHost(ip);
    	setPort(port);
    }
}
