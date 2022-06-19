package projectAdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import join.MemberInfo;
import util.DatabaseManager;

@WebServlet("/project/update")
public class ProjectUpdate extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// 전달 받은 값을 꺼낸다.
		int projectNumber = Integer.parseInt(request.getParameter("projectNumber"));
		String projectName = request.getParameter("projectName");
		
		// 전달 받은 값을 하나의 프로젝트 정보로 구성한다.
		ProjectInfo projectInfo = new ProjectInfo(projectName);
		projectInfo.setProjectNumber(projectNumber);
		
		
		// 기존의 프로젝트 정보를 새로운 프로젝트 정보로 변경한다.
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 프로젝트 정보를 저장하는 테이블에 프로젝트 정보를 저장한다.
		try {
			HttpSession session = request.getSession();
			MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo");
			
			conn = DatabaseManager.getConnection();
			
			String sql = "UPDATE project_info SET NAME = ? WHERE projectNumber = ? AND memberNumber = ?";
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setString(1, projectInfo.getProjectName());
			pstmt.setInt(2, projectNumber);
			pstmt.setInt(3,loginUserInfo.getMemberNumber());
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		// 프로젝트 페이지로 이동을 지시
		response.sendRedirect("http://localhost/todolist/index-project.html");
		
	}

}


