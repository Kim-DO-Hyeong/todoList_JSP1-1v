package today;

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

@WebServlet("/todo/my_project")
public class TodoMyProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo"); 
		
		List<ProjectInfo> projectInfoList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
		
			
			conn= DatabaseManager.getConnection();
			
			String sql ="SELECT * FROM project_info WHERE memberNumber = ?";
			
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setInt(1, loginUserInfo.getMemberNumber());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int projectNumber= rs.getInt("projectNumber");
				String name = rs.getString("name");
				String t_registerDate = rs.getString("registerDate");
				
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
				LocalDateTime registerDate = LocalDateTime.parse(t_registerDate,df);
				
				ProjectInfo projectInfo = new ProjectInfo(projectNumber, name, registerDate);
				
				projectInfoList.add(projectInfo);
				
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closeResultSet(rs);
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		if(projectInfoList.size() == 0 ) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}else {
			JSONArray jsonArray = new JSONArray();
			
			for(ProjectInfo nth : projectInfoList) {
				int projectNumber = nth.getProjectNumber();
				String name = nth.getProjectName();
				LocalDateTime registerDate= nth.getRegisterDate();

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("projectNumber", projectNumber);
				jsonObject.put("name", name);
				jsonObject.put("registerDate", registerDate);
				
				jsonArray.put(jsonObject);
			}
			
			response.setContentType("application/json;charset=UTF-8");
			
			PrintWriter output = response.getWriter();
			output.print(jsonArray);
			output.close();
		}
				
	
	}
}
