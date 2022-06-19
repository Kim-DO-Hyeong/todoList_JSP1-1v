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
import todayAdd.TodoInfo;
import util.DatabaseManager;

@WebServlet("/todo/list")
public class TodoList extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");
	
		HttpSession session = request.getSession();
		
		MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo");
	
		TodoInfo todoInfo = new TodoInfo();
		
		todoInfo.setDate(LocalDate.parse(date));
		todoInfo.setMemberId(loginUserInfo.getMemberNumber());
		
		List<TodoInfo> todoInfoList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		
			
			conn= DatabaseManager.getConnection();
			
			String sql ="SELECT * FROM todo_info WHERE date = ? AND memberNumber = ?";
			
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setString(1, todoInfo.getDate().toString());
			pstmt.setInt(2, todoInfo.getMemberId());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int todoNumber= rs.getInt("todoNumber");
				int projectNumber = rs.getInt("projectNumber");
				String title = rs.getString("title");
				String contents = rs.getString("contents");
				String t_registerDate = rs.getString("registerDate");
				
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
				LocalDateTime registerDate = LocalDateTime.parse(t_registerDate,df);
				
				TodoInfo nth = new TodoInfo();
				nth.setTodoId(todoNumber);
				nth.setProjectId(projectNumber);
				nth.setTitle(title);
				nth.setContents(contents);
				nth.setRegisterDate(registerDate);
				
				todoInfoList.add(nth);
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closeResultSet(rs);
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		if(todoInfoList.size() == 0 ) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}else {
			JSONArray jsonArray = new JSONArray();
			
			for(TodoInfo nth : todoInfoList) {
				int todoNumber = nth.getTodoId();
				int projectNumber = nth.getProjectId();
				String title = nth.getTitle();
				String contents = nth.getContents();
				LocalDateTime registerDate= nth.getRegisterDate();

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("todoNumber", todoNumber);
				jsonObject.put("projectNumber", projectNumber);
				jsonObject.put("title", title);
				jsonObject.put("contents", contents);
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
