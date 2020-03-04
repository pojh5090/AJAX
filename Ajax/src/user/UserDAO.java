package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/AJAX";
			String dbID ="root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<User> search(String userName) {
		String SQL = "SELECT * FROM USER WHERE userName LIKE ?";
		ArrayList<User> userList = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + userName + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserName(rs.getString(1));
				user.setUserAge(rs.getInt(2));
				user.setUserGender(rs.getString(3));
				user.setUserEmail(rs.getString(4));
				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;  //함수를 불러올 때 리스트를 반환함으로써 사용자 정보를 불러옴!
	}
	
	//밑에 이 함수는 회원가입 정보 데베로 날려주는 함수~~ + 정상반환하면 1을 반환함!
	public int register(User user) {
		String SQL = "INSERT INTO USER VALUES(?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, user.getUserAge());
			pstmt.setString(3, user.getUserGender());
			pstmt.setString(4, user.getUserEmail());
			return pstmt.executeUpdate();  //
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
}
