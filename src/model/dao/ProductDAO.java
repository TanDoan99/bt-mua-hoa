package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import library.JDBCConnectionUtil;
import model.bean.Product;

public class ProductDAO {
	private static Connection conn; 
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	public ArrayList<Product> getItems(){
		ArrayList<Product> listItems= new ArrayList<>();
		conn= JDBCConnectionUtil.getConnection();
		String sql ="SELECT*FROM hoa ORDER BY id_hoa ASC";
		try {
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()) {
				Product objPro=new Product(rs.getInt("id_hoa"), rs.getString("ten_hoa"), rs.getString("mo_ta"), rs.getString("hinh_anh"), rs.getInt("gia_ban"));
				listItems.add(objPro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listItems;
	}
	
}
