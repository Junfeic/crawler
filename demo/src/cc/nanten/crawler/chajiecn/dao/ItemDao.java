package cc.nanten.crawler.chajiecn.dao;

import cc.nanten.crawler.chajiecn.entity.Item;

public interface ItemDao {

	public int insert(Item item);
	public int insertMemberPrice(Item item);
	public int update(Item item);
	public int delete(Item item);
	public Item getItemByItemId(Long itemId);
	public Item getItemById(Long id);
	public boolean isItemExisted(Item item);
}
