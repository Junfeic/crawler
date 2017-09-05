/**
 * ItemDaoImpl.java
 *
 * Copyright (c) 2015, 2016, Nanten and/or its affiliates. All rights reserved.
 * Nanten PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @history
 * Rev    Date        Rev by      Reason
 * PA1    2016-7-4     junhong     Created.
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

import cc.nanten.crawler.chajiecn.dao.ItemDao;
import cc.nanten.crawler.chajiecn.dao.util.JdbcManager;
import cc.nanten.crawler.chajiecn.entity.Item;

/**
 * @author junhong
 * @date   2016-7-4
 * @version 0.1
 */
public class ItemDaoImpl implements ItemDao {

	private static BitSet itemSet = new BitSet();
	
	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.ItemDao#insert(cc.nanten.crawler.chajiecn.entity.Item)
	 */
	public int insert(Item item) {
		Connection conn = null;
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return -1;
		}
	    int i = 0;
	    String sql = "INSERT INTO `item`" +
	    		"(`item_id`,`name`,`url`,`market_price`,`sell_price`,`sell_price_max`," +
	    		"`sell_count`,`comment_count`,`stock`,`cat1_id`,`cat2_id`,`cat3_id`," +
	    		"`shop_id`,`platform_id`) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	    	pstmt = (PreparedStatement) conn.prepareStatement(sql);
	    	setSqlParam(pstmt, item);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	          if (conn!=null) try {conn.close();}catch (Exception ignore) {}
	        }
	    
	    itemSet.set(item.getItemId().intValue());
	    
	    return i;
	}

	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.ItemDao#update(cc.nanten.crawler.chajiecn.entity.Item)
	 */
	public int update(Item item) {
		Connection conn = null;
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return -1;
		}
	    int i = 0;
	    String sql = "UPDATE `item`"
	    		+ "SET"
	    		+ "`item_id` = ?,"
	    		+ "`name` = ?,"
	    		+ "`url` = ?,"
	    		+ "`market_price` = ?,"
	    		+ "`sell_price` = ?,"
	    		+ "`sell_price_max` = ?,"
	    		+ "`sell_count` = ?,"
	    		+ "`comment_count` = ?,"
	    		+ "`stock` = ?,"
	    		+ "`cat1_id` = ?,"
	    		+ "`cat2_id` = ?,"
	    		+ "`cat3_id` = ?,"
	    		+ "`shop_id` = ?,"
	    		+ "`platform_id` = ?"
	    		+ "WHERE `id` = ?";
	    PreparedStatement pstmt;
	    try {
	    	pstmt = (PreparedStatement) conn.prepareStatement(sql);
	    	setSqlParam(pstmt, item);
	    	pstmt.setLong(15, item.getId());
	    	
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	          if (conn!=null) try {conn.close();}catch (Exception ignore) {}
	        }
	    return i;
	}

	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.ItemDao#delete(cc.nanten.crawler.chajiecn.entity.Item)
	 */
	public int delete(Item item) {
		Connection conn = null;
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return -1;
		}
	    int i = 0;
	    String sql = "DELETE FROM `item`"
	    		+ "WHERE `id` = ?";
	    PreparedStatement pstmt;
	    try {
	    	pstmt = (PreparedStatement) conn.prepareStatement(sql);
	    	pstmt.setLong(1, item.getId());
	    	
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	          if (conn!=null) try {conn.close();}catch (Exception ignore) {}
	        }
	    return i;
	}

	/* (non-Javadoc)
	 * @see cc.nanten.crawler.chajiecn.dao.ItemDao#getItemByItemId(cc.nanten.crawler.chajiecn.entity.Item)
	 */
	public boolean isItemExisted(Item item) {
		if(itemSet.get(item.getItemId().intValue())) {
			return true;
		}
		if(getItemByItemId(item.getItemId()) != null) {
			itemSet.set(item.getItemId().intValue());
			return true;
		}
		
		return false;
	}

	public Item getItemByItemId(Long itemId) {
		Connection conn = null;
		ResultSet rs=null;
		Item item = null;
		
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return null;
		}
	    
	    String sql = "SELECT `id`,        "
	    		+ "      `item_id`,       "
	    		+ "      `name`,          "
	    		+ "      `url`,           "
	    		+ "      `market_price`,  "
	    		+ "      `sell_price`,    "
	    		+ "      `sell_price_max`,"
	    		+ "      `sell_count`,    "
	    		+ "      `comment_count`, "
	    		+ "      `stock`,         "
	    		+ "      `cat1_id`,       "
	    		+ "      `cat2_id`,       "
	    		+ "      `cat3_id`,       "
	    		+ "      `shop_id`,       "
	    		+ "      `platform_id`    "
	    		+ " FROM `item`           "
	    		+ "WHERE `item_id` = ?         ";
	    PreparedStatement pstmt;
	    try {
	    	pstmt = (PreparedStatement) conn.prepareStatement(sql);
	    	pstmt.setLong(1, itemId);
	    	
	        rs = pstmt.executeQuery();
	        List<Item> list = fetchItem(rs);
	        if(list.size()>0) {
	        	item = list.get(0);
	        }
	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	          if (conn!=null) try {conn.close();}catch (Exception ignore) {}
	        }
		return item;
	}

	public Item getItemById(Long id) {
		Connection conn = null;
		ResultSet rs=null;
		Item item = null;
		
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return null;
		}
	    
		String sql = "SELECT `id`,           "
				+ "      `item_id`,       "
				+ "      `name`,          "
				+ "      `url`,           "
				+ "      `market_price`,  "
				+ "      `sell_price`,    "
				+ "      `sell_price_max`,"
				+ "      `sell_count`,    "
				+ "      `comment_count`, "
				+ "      `stock`,         "
				+ "      `cat1_id`,       "
				+ "      `cat2_id`,       "
				+ "      `cat3_id`,       "
				+ "      `shop_id`,       "
				+ "      `platform_id`    "
				+ " FROM `item`           "
				+ "WHERE `id` = ?         ";
		PreparedStatement pstmt;
	    try {
	    	pstmt = (PreparedStatement) conn.prepareStatement(sql);
	    	pstmt.setLong(1, id);
	    	
	        rs = pstmt.executeQuery();
	        List<Item> list = fetchItem(rs);
	        if(list.size()>0) {
	        	item = list.get(0);
	        }
	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	          if (conn!=null) try {conn.close();}catch (Exception ignore) {}
	        }
		return item;
	}

	private void setSqlParam(PreparedStatement pstmt, Item item) throws SQLException {
		pstmt.setLong(1, item.getItemId());
        pstmt.setString(2, item.getName());
        pstmt.setString(3, item.getUrl());
        pstmt.setBigDecimal(4, item.getMarketPrice());
        pstmt.setBigDecimal(5, item.getSellPrice());
        pstmt.setNull(6, Types.DECIMAL);  // no maxPrice in chajiecn
        try {
			pstmt.setInt(7, item.getSellCount());
		} catch (SQLException e) {
			pstmt.setNull(7, Types.INTEGER);
		}
        try {
			pstmt.setInt(8, item.getCommentCount());
		} catch (SQLException e) {
			pstmt.setNull(8, Types.INTEGER);
		}
        try {
			pstmt.setInt(9, item.getStock());
		} catch (Exception e) {
			pstmt.setNull(9, Types.INTEGER);
		}
        pstmt.setLong(10, item.getCat1Id());
        try {
			pstmt.setLong(11, item.getCat2Id());
		} catch (Exception e) {
			pstmt.setNull(11, Types.BIGINT);
		}
        try {
			pstmt.setLong(12, item.getCat3Id());
		} catch (Exception e) {
			pstmt.setNull(12, Types.BIGINT);
		}
        pstmt.setNull(13, Types.BIGINT); // no shopId in chajiecn
        pstmt.setInt(14, item.getPlatformId());
	}

	private List<Item> fetchItem(ResultSet rs) throws SQLException {
		Item item = null;
		List<Item> list = new ArrayList<Item>();
		while(rs.next()) {
			item = new Item();
			item.setId(rs.getLong("id"));
			item.setItemId(rs.getLong("item_id"));
			item.setName(rs.getString("name"));
			item.setUrl(rs.getString("url"));
			item.setMarketPrice(rs.getBigDecimal("market_price"));
			item.setSellPrice(rs.getBigDecimal("sell_price"));
			item.setSellPriceMax(rs.getBigDecimal("sell_price_max"));
			item.setSellCount(rs.getInt("sell_count"));
			item.setCommentCount(rs.getInt("comment_count"));
			item.setStock(rs.getInt("stock"));
			item.setCat1Id(rs.getLong("cat1_id"));
			item.setCat2Id(rs.getLong("cat2_id"));
			item.setCat3Id(rs.getLong("cat3_id"));
			item.setShopId(rs.getLong("shop_id"));
			item.setPlatformId(rs.getInt("platform_id"));
			
			list.add(item);
		}
		return list;
	}

	public int insertMemberPrice(Item item) {
		Connection conn = null;
		try {
			conn = JdbcManager.getConnection();
		} catch (SQLException e1) {
			return -1;
		}
		int i = 0;
		String sql = "INSERT INTO `item_price_member`"
				+ "(`item_id`,`price_common`,`price_pifa`,`price_gold`,`price_silver`,`price_diamond`,`platform_id`) VALUES  (?,?,?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, item.getItemId());
			pstmt.setBigDecimal(2, item.getPriceCommon());
			pstmt.setBigDecimal(3, item.getPricePifa());
			pstmt.setBigDecimal(4, item.getPriceGold());
			pstmt.setBigDecimal(5, item.getPriceSilver());
			pstmt.setBigDecimal(6, item.getPriceDiamond());
			pstmt.setInt(7, item.getPlatformId());
			
			i = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (Exception ignore) {
				}
		}
		return i;
	}
}
