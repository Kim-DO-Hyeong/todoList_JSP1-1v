package todayAdd;

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
import javax.servlet.http.HttpSession;

import join.MemberInfo;
import util.DatabaseManager;

@WebServlet("/todo/update")
public class TodoUpdate extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("할 일 수정 기능 시작");
		
		request.setCharacterEncoding("UTF-8");
		
		// 전달 받은 값을 꺼낸다.
		String t_todoId = request.getParameter("todoId");
		String t_date = request.getParameter("date");
		String t_projectId = request.getParameter("projectId");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		int todoId = Integer.parseInt(t_todoId);
		LocalDate date = LocalDate.parse(t_date);
		int projectId = Integer.parseInt(t_projectId);
		
		System.out.println("date = " + date);
		System.out.println("projectId = " + projectId);
		System.out.println("title = " + title);
		System.out.println("contents = " + contents);
		
		// 전달 받은 값을 하나의 할 일 정보로 구성한다.
		TodoInfo todoInfo = new TodoInfo(todoId,date, projectId, title, contents);
		
		// 기존의 할 일 정보를 새로운 할 일 정보로 변경한다.
		
		System.out.println("기존의 할 일 정보를 새로운 할 일 정보로 변경한다. ( 구현 예정 )");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			HttpSession session = request.getSession();
			MemberInfo loginUserInfo =(MemberInfo) session.getAttribute("loginUserInfo");
			
			conn = DatabaseManager.getConnection();
			
			String sql="UPDATE todo_info SET date = ?, projectNumber = ?, title = ?, contents = ? WHERE todoNumber = ? AND memberNumber = ?";
			
			pstmt= DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setString(1,todoInfo.getDate().toString());
			pstmt.setInt(2, todoInfo.getProjectId());
			pstmt.setString(3, todoInfo.getTitle());
			pstmt.setString(3, todoInfo.getContents());
			pstmt.setInt(5, todoInfo.getTodoId());
			pstmt.setInt(6, loginUserInfo.getMemberNumber());
			
			pstmt.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		// "방금 등록한 할 일을 보여주는" 오늘 페이지로 이동을 지시
		response.sendRedirect("http://localhost/todolist/index-today.html?date="+t_date);
		
		System.out.println("할 일 수정 기능 종료");
	}

}


