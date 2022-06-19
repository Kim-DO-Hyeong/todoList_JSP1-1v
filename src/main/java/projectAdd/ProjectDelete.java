package projectAdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import join.MemberInfo;
import util.DatabaseManager;

@WebServlet("/project/delete")
public class ProjectDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int projectNumber = Integer.parseInt(request.getParameter("projectNumber"));
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 프로젝트 정보를 저장하는 테이블에 프로젝트 정보를 저장한다.
		try {
			
			HttpSession session = request.getSession();
			MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo");
			
			conn = DatabaseManager.getConnection();
			
			String sql = "DELETE FROM project_info WHERE projectNumber = ? AND memberNumber = ?";
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setInt(1, projectNumber);
			pstmt.setInt(2, loginUserInfo.getMemberNumber());
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		response.sendRedirect("/todolist/index-project.html");
	}

}
