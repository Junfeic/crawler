package cc.nanten.crawler.chajiecn.entity;

import java.math.BigDecimal;

public class Item {
	private Long id;
	private Long itemId;
	private String name;
	private String url;
	private BigDecimal marketPrice;
	private BigDecimal sellPrice;
	private BigDecimal sellPriceMax;
	private int sellCount;
	private int commentCount;
	private int stock;
	private Long cat1Id;
	private Long cat2Id;
	private Long cat3Id;
	private Long shopId;
	private int platformId;
	
	private BigDecimal priceCommon;  // 会员普通价
	private BigDecimal pricePifa;    // 批发价
	private BigDecimal priceGold;    // 金牌会员价
	private BigDecimal priceSilver;  // 银牌会员价
	private BigDecimal priceDiamond; // 钻石会员价
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	public BigDecimal getSellPriceMax() {
		return sellPriceMax;
	}
	public void setSellPriceMax(BigDecimal sellPriceMax) {
		this.sellPriceMax = sellPriceMax;
	}
	public int getSellCount() {
		return sellCount;
	}
	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Long getCat1Id() {
		return cat1Id;
	}
	public void setCat1Id(Long cat1Id) {
		this.cat1Id = cat1Id;
	}
	public Long getCat2Id() {
		return cat2Id;
	}
	public void setCat2Id(Long cat2Id) {
		this.cat2Id = cat2Id;
	}
	public Long getCat3Id() {
		return cat3Id;
	}
	public void setCat3Id(Long cat3Id) {
		this.cat3Id = cat3Id;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public int getPlatformId() {
		return platformId;
	}
	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	public BigDecimal getPriceCommon() {
		return priceCommon;
	}
	public void setPriceCommon(BigDecimal priceCommon) {
		this.priceCommon = priceCommon;
	}
	public BigDecimal getPricePifa() {
		return pricePifa;
	}
	public void setPricePifa(BigDecimal pricePifa) {
		this.pricePifa = pricePifa;
	}
	public BigDecimal getPriceGold() {
		return priceGold;
	}
	public void setPriceGold(BigDecimal priceGold) {
		this.priceGold = priceGold;
	}
	public BigDecimal getPriceSilver() {
		return priceSilver;
	}
	public void setPriceSilver(BigDecimal priceSilver) {
		this.priceSilver = priceSilver;
	}
	public BigDecimal getPriceDiamond() {
		return priceDiamond;
	}
	public void setPriceDiamond(BigDecimal priceDiamond) {
		this.priceDiamond = priceDiamond;
	}
	
	
}
