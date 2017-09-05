package cc.nanten.crawler.chajiecn.service;

import java.math.BigDecimal;

import cc.nanten.crawler.chajiecn.dao.ItemDao;
import cc.nanten.crawler.chajiecn.dao.impl.ItemDaoImpl;
import cc.nanten.crawler.chajiecn.entity.Item;

public class ItemPersist {

	ItemDao dao = new ItemDaoImpl();
	
	public int saveItem(Item item) {
		return 0;
	}

	public boolean isItemExisted(String itemUrl) {
		Item item = new Item();
		String itemId = itemUrl.split("=")[1];
		item.setItemId(Long.valueOf(itemId));
		item.setUrl(itemUrl);
		return dao.isItemExisted(item);
	}
	
	public int saveItem(String itemId, String itemName, String itemUrl, String marketPrice,
			String sellPrice, String sellPriceMax, String sellCount,
			String commentCount, String stock, String cat1Id, String cat2Id,
			String cat3Id, String shopId, int platformId, String priceCommon,
			String pricePifa, String priceGold, String priceSilver,
			String priceDiamond) {
		Item item = new Item();
		item.setItemId(Long.valueOf(itemId));
		item.setName(itemName);
		item.setUrl(itemUrl);
		if(dao.isItemExisted(item)) {
			return 0;
		}
		item.setMarketPrice(marketPrice == null?null:new BigDecimal(marketPrice));
		item.setSellPrice(sellPrice == null?null:new BigDecimal(sellPrice));
		item.setSellPriceMax(sellPriceMax == null?null:new BigDecimal(sellPriceMax));
		item.setSellCount(sellCount == null?null:Integer.valueOf(sellCount));
		item.setCommentCount(commentCount == null?null:Integer.valueOf(commentCount));
		item.setStock(stock == null?null:Integer.valueOf(stock));
		item.setCat1Id(cat1Id == null?null:Long.valueOf(cat1Id));
		item.setCat2Id(cat2Id == null?null:Long.valueOf(cat2Id));
		item.setCat3Id(cat3Id == null?null:Long.valueOf(cat3Id));
		item.setShopId(shopId == null?null:Long.valueOf(shopId));
		item.setPlatformId(platformId);
		
		item.setPriceCommon(priceCommon == null?null:new BigDecimal(priceCommon));
		item.setPricePifa(pricePifa == null?null:new BigDecimal(pricePifa));
		item.setPriceGold(priceGold == null?null:new BigDecimal(priceGold));
		item.setPriceSilver(priceSilver == null?null:new BigDecimal(priceSilver));
		item.setPriceDiamond(priceDiamond == null?null:new BigDecimal(priceDiamond));

		int i = dao.insert(item);
		dao.insertMemberPrice(item);
		
		return i;
	}

}
