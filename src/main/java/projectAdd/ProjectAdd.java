package projectAdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DatabaseManager;

@WebServlet("/project/add")
public class ProjectAdd extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// 전달 받은 값을 꺼낸다.
		String projectName = request.getParameter("projectName");
		
		
		// 전달 받은 값을 하나의 프로젝트 정보로 구성한다.
		ProjectInfo newProjectInfo = new ProjectInfo(projectName);
		
		// 프로젝트 정보를 DB에 저장한다.
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 프로젝트 정보를 저장하는 테이블에 프로젝트 정보를 저장한다.
		try {
			conn = DatabaseManager.getConnection();
			
			String sql = "INSERT INTO project_info(name,registerDate) VALUES(?,?)";
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			
			pstmt.setString(1, newProjectInfo.getProjectName());
			pstmt.setString(2, LocalDateTime.now().toString());
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		// 프로젝트 페이지로 이동을 지시
		response.sendRedirect("http://localhost/todolist/index-project.html");
		
	}

}


