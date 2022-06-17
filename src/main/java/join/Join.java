package join;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DatabaseManager;

@WebServlet("/join")
public class Join extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 전달 받은 값을 꺼낸다.
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		
		// 전달 받은 값을 하나의 회원 정보로 구성한다.
		MemberInfo newMemberInfo = new MemberInfo(id, pw, name);
		
		// 회원 정보를 DB에 저장한다.
		Connection conn = null;
		PreparedStatement selectPstmt = null;
		PreparedStatement insertPstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseManager.getConnection();
			
			// 3. 실행할 쿼리 작성
			String sql = "SELECT * FROM member_info WHERE memberID = ?";
			
			selectPstmt = DatabaseManager.getPreparedStatment(conn, sql);
			selectPstmt.setString(1, newMemberInfo.getId());

			rs = selectPstmt.executeQuery();
			if(rs.next()) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				return ;
			}
			
			sql = "INSERT INTO member_info(id, pw, name, joinDate) VALUES(?, ?, ?, ?)";
			
			// 4. 쿼리를 실행하고 결과를 가져올 객체 생성
			insertPstmt = DatabaseManager.getPreparedStatment(conn, sql);
			
			// 5. 쿼리 내 인덱스 파라미터 채우기
			insertPstmt.setString(1, newMemberInfo.getId());
			insertPstmt.setString(2, newMemberInfo.getPw());
			insertPstmt.setString(3, newMemberInfo.getName());
			insertPstmt.setString(4, LocalDateTime.now().toString());
			
			// 6. ( 4 ) 에서 생성한 객체를 통해 쿼리 실행 및 결과 저장
			int count = insertPstmt.executeUpdate();
			
			// 7. 결과 활용
			// 회원 가입 성공 시그널을 보낸다.
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeResultSet(rs);
			
			DatabaseManager.closePstmt(selectPstmt);
			
			DatabaseManager.closePstmt(insertPstmt);
			
			DatabaseManager.closeConnection(conn);
		}
	}
}





