package today;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import todayAdd.TodoInfo;
import util.DatabaseManager;

@WebServlet("/todo/detail")
public class TodoDetail extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int todoNumber = Integer.parseInt("todoNumber");
		
		HttpSession session = request.getSession();
		MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo"); 
		

		TodoInfo todoInfo = new TodoInfo();
		
		todoInfo.setTodoId(todoNumber);
		todoInfo.setMemberId(loginUserInfo.getMemberNumber());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
		
			
			conn= DatabaseManager.getConnection();
			
			String sql ="SELECT * todo_info WHERE todoNumber = ? AND memberNumber = ?";
			
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setInt(1, todoNumber);
			pstmt.setInt(2, loginUserInfo.getMemberNumber());
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				String t_date = rs.getString("date");
				int projectNumber = rs.getInt("projectNumber");
				String title = rs.getString("title");
				String contents = rs.getString("contents");
				String t_registerDate = rs.getString("t_registerDate");
				
				LocalDate date = LocalDate.parse(t_date);
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
				LocalDateTime registerDate = LocalDateTime.parse(t_registerDate,df);
				
				todoInfo.setDate(date);
				todoInfo.setProjectId(projectNumber);
				todoInfo.setTitle(title);
				todoInfo.setContents(contents);
				todoInfo.setRegisterDate(registerDate);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		JSONObject json = new JSONObject();
		json.put("date", todoInfo.getDate().toString());
		json.put("projectNumber",todoInfo.getProjectId());
		json.put("title",todoInfo.getTitle());
		json.put("contents",todoInfo.getContents());
		json.put("registerDate",todoInfo.getRegisterDate().toString());
		
		response.setContentType("application/json;charset=UTF-8");
		
		PrintWriter output = response.getWriter();
		output.print(json);
		output.close();
		
	}

}