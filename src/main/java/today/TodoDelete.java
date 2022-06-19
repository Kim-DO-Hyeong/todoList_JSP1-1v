package today;

import java.io.IOException;
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

import join.MemberInfo;
import projectAdd.ProjectInfo;
import todayAdd.TodoInfo;
import util.DatabaseManager;

@WebServlet("/todo/delete")
public class TodoDelete extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");
		String t_todoId= request.getParameter("todoId");
		int todoId = Integer.parseInt(t_todoId);
		
		HttpSession session = request.getSession();
		
		MemberInfo loginUserInfo = (MemberInfo) session.getAttribute("loginUserInfo");
		
		TodoInfo todoInfo = new TodoInfo();
		
		todoInfo.setTodoId(todoId);
		todoInfo.setMemberId(loginUserInfo.getMemberNumber());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
		
			
			conn= DatabaseManager.getConnection();
			
			String sql ="DELETE FROM todo_info WHERE todoNumber = ? AND memberNumber = ? ";
			
			pstmt = DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setInt(1, todoInfo.getTodoId());
			pstmt.setInt(2, todoInfo.getMemberId());
			
			pstmt.executeUpdate();
				
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		response.sendRedirect("/todolist/index-today.html?date="+date);
		
	}

}
