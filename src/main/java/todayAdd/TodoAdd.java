package todayAdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import join.MemberInfo;
import util.DatabaseManager;

@WebServlet("/todo/add")
public class TodoAdd extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("할 일 추가 기능 시작");
		
		request.setCharacterEncoding("UTF-8");
		
		// 전달 받은 값을 꺼낸다.
		String t_date = request.getParameter("date");
		String t_projectId = request.getParameter("projectId");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		LocalDate date = LocalDate.parse(t_date);
		int projectId = Integer.parseInt(t_projectId);
		
		System.out.println("date = " + date);
		System.out.println("projectId = " + projectId);
		System.out.println("title = " + title);
		System.out.println("contents = " + contents);
		
		// 전달 받은 값을 하나의 할 일 정보로 구성한다.
		TodoInfo newTodoInfo = new TodoInfo(date, projectId, title, contents);
		
		System.out.println("전달 받은 값을 하나의 할 일 정보로 구성했음");
		
		// 할 일 정보를 DB에 저장한다.
		
		System.out.println("할 일 정보를 DB에 저장했음( 구현 예정 )");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			HttpSession session = request.getSession();
			MemberInfo loginUserInfo =(MemberInfo) session.getAttribute("loginUserInfo");
			
			conn = DatabaseManager.getConnection();
			
			String sql="INSERT INTO todo_info(memberNumber,date,title,projectNumber,contents,registationDate) VALUES(?,?,?,?,?,?)";
			
			pstmt= DatabaseManager.getPreparedStatment(conn, sql);
			pstmt.setInt(1, loginUserInfo.getMemberNumber());
			pstmt.setString(2,newTodoInfo.getDate().toString());
			pstmt.setString(3, newTodoInfo.getTitle());
			
			if(newTodoInfo.getProjectId() == 0) {
				pstmt.setNull(3, Types.INTEGER);
			}else {
				pstmt.setInt(4, newTodoInfo.getProjectId());
			}
				
			
			pstmt.setString(5, newTodoInfo.getContents());
			pstmt.setString(6, LocalDateTime.now().toString());
			
			pstmt.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseManager.closePstmt(pstmt);
			DatabaseManager.closeConnection(conn);
		}
		
		// "방금 등록한 할 일을 보여주는" 오늘 페이지로 이동을 지시
		response.sendRedirect("http://localhost/todolist/index-today.html?date="+t_date);
		
		System.out.println("할 일 추가 기능 종료");
	}

}



