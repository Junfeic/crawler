package cc.nanten.crawler.chajiecn.service;

import cc.nanten.crawler.chajiecn.dao.CategoryDao;
import cc.nanten.crawler.chajiecn.dao.impl.CategoryDaoImpl;
import cc.nanten.crawler.chajiecn.entity.Category;

public class CategoryPersist {
	
	CategoryDao dao = new CategoryDaoImpl();
	
	public int saveCategory(Category category) {
		return 0;
	}
	public int saveCategory(String id, String name, String url, String parentId, int platformId) {
		Category category = new Category();
		category.setId(Long.valueOf(id));
		category.setName(name);
		category.setUrl(url);
		category.setParentId(Long.valueOf(parentId));
		category.setPlatformId(platformId);
        if(dao.isCategoryExisted(category)) {
			return 0;
		}
		
		return dao.insert(category);
	}
}
