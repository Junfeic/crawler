/**
 * CategoryDao.java
 *
 * Copyright (c) 2015, 2016, Nanten and/or its affiliates. All rights reserved.
 * Nanten PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @history
 * Rev    Date        Rev by      Reason
 * PA1    2016-7-4     junhong     Created.
 */
package cc.nanten.crawler.chajiecn.dao;

import cc.nanten.crawler.chajiecn.entity.Category;

/**
 * @author junhong
 * @date   2016-7-4
 * @version 0.1
 */
public interface CategoryDao {
	public int insert(Category cat);
	public int update(Category cat);
	public int delete(Category cat);
	public Category getCategoryById(Long catId);
	public boolean isCategoryExisted(Category cat);
}
