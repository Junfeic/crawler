/**
 * CategoryDaoImpl.java
 *
 * Copyright (c) 2015, 2016, Nanten and/or its affiliates. All rights reserved.
 * Nanten PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @history
 * Rev    Date        Rev by      Reason
 * PA1    2016-7-6     junhong     Created.
 */
package cc.nanten.crawler.chajiecn.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import cc.nanten.crawler.chajiecn.dao.CategoryDao;
import cc.nanten.crawler.chajiecn.dao.util.JdbcManager;
import cc.nanten.crawler.chajiecn.entity.Category;

/**
 * @author junhong
 * @date   2016-7-6
 * @version 0.1
 */
public class CategoryDaoImpl implements CategoryDao {

	private static BitSet bitSet = new BitSet();
	
	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.CategoryDao#insert(cc.nanten.crawler.chajiecn.entity.Category)
	 */
	public int insert(Category cat) {
		Connection conn = null;
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return -1;
		}
	    int i = 0;
	    String sql = "INSERT INTO `category`" +
	    		"(`id`,`name`,`url`,`parent_id`,`platform_id`) VALUES  (?,?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	    	pstmt = (PreparedStatement) conn.prepareStatement(sql);
	    	setSqlParam(pstmt, cat);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	          if (conn!=null) try {conn.close();}catch (Exception ignore) {}
	        }
	    
	    bitSet.set(cat.getUrl().hashCode());
	    
	    return i;
	}

	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.CategoryDao#update(cc.nanten.crawler.chajiecn.entity.Category)
	 */
	public int update(Category cat) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.CategoryDao#delete(cc.nanten.crawler.chajiecn.entity.Category)
	 */
	public int delete(Category cat) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.CategoryDao#getCategoryById(java.lang.Long)
	 */
	public Category getCategoryById(Long catId) {
		Connection conn = null;
		ResultSet rs=null;
		Category category = null;
		
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return null;
		}
	    
		String sql = "SELECT `id`,        "
				+ "      `name`,          "
				+ "      `url`,           "
				+ "      `parent_id`,     "
				+ "      `platform_id`    "
				+ " FROM `category`       "
				+ "WHERE `id` = ?         ";
		PreparedStatement pstmt;
	    try {
	    	pstmt = (PreparedStatement) conn.prepareStatement(sql);
	    	pstmt.setLong(1, catId);
	    	
	        rs = pstmt.executeQuery();
	        List<Category> list = fetchCategory(rs);
	        if(list.size()>0) {
	        	category = list.get(0);
	        }
	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	          if (conn!=null) try {conn.close();}catch (Exception ignore) {}
	        }
		return category;
	}

	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.CategoryDao#isCategoryExisted(cc.nanten.crawler.chajiecn.entity.Category)
	 */
	public boolean isCategoryExisted(Category cat) {
		if(bitSet.get(cat.getUrl().hashCode())) {
			return true;
		}
		if(getCategoryById(cat.getId()) != null) {
			bitSet.set(cat.getUrl().hashCode());
			return true;
		}
		
		return false;
	}

	private void setSqlParam(PreparedStatement pstmt, Category cat) throws SQLException {
		try {
			pstmt.setLong(1, cat.getId());
		} catch (SQLException e) {
			pstmt.setNull(1, Types.BIGINT);
		}
        try {
			pstmt.setString(2, cat.getName());
		} catch (SQLException e) {
			pstmt.setNull(2, Types.VARCHAR);
		}
        try {
			pstmt.setString(3, cat.getUrl());
		} catch (SQLException e) {
			pstmt.setNull(3, Types.VARCHAR);
		}
        try {
			pstmt.setLong(4, cat.getParentId());
		} catch (SQLException e) {
			pstmt.setNull(4, Types.BIGINT);
		}
        try {
			pstmt.setInt(5, cat.getPlatformId());
		} catch (SQLException e) {
			pstmt.setNull(5, Types.INTEGER);
		}
	}

	private List<Category> fetchCategory(ResultSet rs) throws SQLException {
		Category category = null;
		List<Category> list = new ArrayList<Category>();
		while(rs.next()) {
			category = new Category();
			category.setId(rs.getLong("id"));
			category.setName(rs.getString("name"));
			category.setUrl(rs.getString("url"));
			category.setParentId(rs.getLong("parent_id"));
			category.setPlatformId(rs.getInt("platform_id"));
			
			list.add(category);
		}
		return list;
	}

}
