package com.dong.server.spring.controller;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dong.server.spring.entity.Industry;
import com.mysql.jdbc.Connection;

public class TestLyxUc {

	
	
	
	private static Connection getConnmysql() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://10.10.10.188:3306/lyx_ucenter";
	    String username = "root";
	    String password = "lyx@20161111";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	
	private static Connection getConnoracle() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://10.10.10.188:3306/lyx_oracle_ucenter";
	    String username = "root";
	    String password = "lyx@20161111";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	
	
	private static int insert(Industry indus) {
	    Connection conn = getConnmysql();
	    int i = 0;
	    String sql = "INSERT INTO t_zsuserindustryatt (fuserid, findustryid, fstate, fsort,  fctime, futime) "
	    		+ "values (?,?,1,?,NOW(),NOW())";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        
	        String[] ids = indus.getIndustry().split(",") ;
	        for(String id : ids){
	        	pstmt.setString(1, indus.getUserid());
	 	        pstmt.setString(2, id);
	 	        pstmt.setString(3, indus.getSort());
	 	        pstmt.addBatch();
	        }
	        
	        pstmt.executeBatch();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	private static List<Industry> getAll() {
	    Connection conn = getConnoracle();
	    String select_sql = "select t.USER_ID userid,t.ATTENTION_INDUSTRY industry,t.USER_ORDER sort "+
	    		 "from lyx_oracle_ucenter.tab_uum_user_detail t  "+ 
	    		"where t.user_id in "+
	    		"(select user_id from lyx_oracle_ucenter.tab_uum_users where company_id = 1) "+
	    		" and t.ATTENTION_INDUSTRY is not null  ";
	    PreparedStatement pstmt;
	   // List<Industry> indus = new ArrayList<Industry>();
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(select_sql);
	        ResultSet rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        System.out.println("============================");
	        while (rs.next()) {
	        	String indudids = rs.getString("industry");
	        	Industry indus = new Industry();
	        	indus.setIndustry(indudids);
	        	indus.setSort( rs.getString("sort"));
	        	indus.setUserid( rs.getString("userid"));
	        	insert(indus);
	        	System.out.println("插入@￥%……完成"+indus.getIndustry().split(",").length+"条数据");
	        }
	            System.out.println("============================");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	public static void main(String[] args) {

		getAll();
	}

}
