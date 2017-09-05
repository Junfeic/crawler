/**
 * JdbcOperator.java
 *
 * Copyright (c) 2015, 2016, Nanten and/or its affiliates. All rights reserved.
 * Nanten PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @history
 * Rev    Date        Rev by      Reason
 * PA1    2016-7-4     junhong     Created.
 */
package cc.nanten.crawler.chajiecn.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
 
/**
 * @author junhong
 * @date 2016-7-4
 * @version 0.1
 */
public class JdbcManager {
	private static PoolProperties p = new PoolProperties();
	private static DataSource datasource = new DataSource();
	
	static{
        p.setUrl("jdbc:mysql://rdsvd74brlq8tl7tat3ia.mysql.rds.aliyuncs.com:3306/temptask18");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("gzecc");
        p.setPassword("Gzecc1234");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
          "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
       
        datasource.setPoolProperties(p);
	}
	@Deprecated
	public static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://rdsvd74brlq8tl7tat3ia.mysql.rds.aliyuncs.com:3306/temptask18";
		String username = "gzecc";
		String password = "Gzecc1234";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, username,
					password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }
}
