package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import library.JDBCConnectionUtil;
import model.bean.Product;

public class AddProductDAO {
	private static Connection conn; 
	private static PreparedStatement pst;
	private static ResultSet rs;
	
	public static int additems(Product objAdd) {
		conn= JDBCConnectionUtil.getConnection();
		int result=0;
		String sql = "INSERT INTO hoa (ten_hoa, mo_ta, hinh_anh, gia_ban) VALUES (?, ?, ?, ?)";
		 try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, objAdd.getTenHoa());
			pst.setString(2, objAdd.getMoTa());
			pst.setString(3, objAdd.getHinhAnh());
			pst.setInt(4, objAdd.getGiaBan());
			result=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnectionUtil.close(rs, pst, conn);
		}
		
		return result;
	}
	
}
