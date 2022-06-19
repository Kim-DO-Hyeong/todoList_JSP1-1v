package project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import join.MemberInfo;
import projectAdd.ProjectInfo;
import util.DatabaseManager;

@WebServlet("/project/detail")
public class ProjectDetail extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int projectNumber = Integer.parseInt(request.getParameter("projectNumber"));
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			HttpSession session = request.getSession();
			MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo");
			
			conn = DatabaseManager.getConnection();
			
			String sql =  "SELECT * FROM project_info WHERE projectNumber = ? AND projectNumber =?";
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setInt(1, projectNumber);
			pstmt.setInt(2, loginUserInfo.getMemberNumber());
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			String projectName = rs.getString("name");
			String t_registerDate = rs.getString("registerDate");
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			LocalDateTime registerDate = LocalDateTime.parse(t_registerDate,df);
			
			ProjectInfo projectInfo = new ProjectInfo(projectNumber,projectName,registerDate);
			
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("projectName",projectName);
			jsonObject.put("registerDate",registerDate);
			jsonObject.put("projectNumber",projectNumber);
			
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter output = response.getWriter();
			
			output.print(jsonObject);
			output.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closeResultSet(rs);
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		
		
	}

}
