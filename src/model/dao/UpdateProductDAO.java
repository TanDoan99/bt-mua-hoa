package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import library.JDBCConnectionUtil;
import model.bean.Product;

public class UpdateProductDAO {
	private static Connection conn; 
	private static PreparedStatement pst;
	private static ResultSet rs;
	
	public static Product getItem(int id) {
		conn= JDBCConnectionUtil.getConnection();
		String sql="SELECT * FROM hoa WHERE id_hoa= ? ";
		Product objPro=null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs=pst.executeQuery();
			if(rs.next()) {
				objPro =new  Product(rs.getInt("id_hoa"), rs.getString("ten_hoa"), rs.getString("mo_ta"), rs.getString("hinh_anh"), rs.getInt("gia_ban"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnectionUtil.close(rs, pst, conn);
		}
		
		return objPro;
	}
	public static int Updateitems(Product objPro) {
		conn= JDBCConnectionUtil.getConnection();
		int result=0;
		String sql = "UPDATE hoa SET  ten_hoa= ? , mo_ta= ? , hinh_anh = ? , gia_ban = ? ";
		 try {
			pst=conn.prepareStatement(sql);
			pst.setString(2, objPro.getTenHoa());
			pst.setString(3, objPro.getMoTa());
			pst.setString(4, objPro.getHinhAnh());
			pst.setInt(5, objPro.getGiaBan());
			result=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnectionUtil.close(rs, pst, conn);
		}
		
		return result;
	}
	
}
