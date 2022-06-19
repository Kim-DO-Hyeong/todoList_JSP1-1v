package project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import join.MemberInfo;
import projectAdd.ProjectInfo;
import util.DatabaseManager;

/**
 * Servlet implementation class Project
 */
@WebServlet("/project/list")
public class ProjectList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DB에서 프로젝트 목록을 가져온다 
		List<ProjectInfo> projectInfoList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		try {
			HttpSession session = request.getSession();
			MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo");
			
			
			conn = DatabaseManager.getConnection();
			
			String sql = "SELECT * FROM project_info WHERE memberNumber = ?";
			pstmt=DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setInt(1, loginUserInfo.getMemberNumber());
			
			rs=pstmt.executeQuery();
			
			JSONArray jsonArray = new JSONArray();
			
			
			
			while(rs.next()) {
				String projectName = rs.getString("name");
				String t_registerDate = rs.getString("registerDate");
				String projectNumber = rs.getString("projectNumber");
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
				LocalDateTime registerDate = LocalDateTime.parse(t_registerDate,df);
				
				ProjectInfo projectInfo = new ProjectInfo(projectName,registerDate);
				
				projectInfoList.add(projectInfo);
				
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("projectName",projectName);
				jsonObject.put("registerDate",registerDate);
				jsonObject.put("projectNumber",projectNumber);
				
				jsonArray.put(jsonObject);
			}
			
			
			
			
			
			
			if(jsonArray.length() == 0) {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}else {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter output = response.getWriter();
				
				output.print(jsonArray);
				output.close();
				
			}
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closeResultSet(rs);
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		// 가져온 프로젝트 목록을 클라이언트에게 전달한다 
	
	}

}
