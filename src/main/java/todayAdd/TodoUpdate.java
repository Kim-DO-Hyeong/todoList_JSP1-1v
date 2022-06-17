package todayAdd;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/todo/update")
public class TodoUpdate extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("할 일 수정 기능 시작");
		
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
		TodoInfo todoInfo = new TodoInfo(date, projectId, title, contents);
		
		// 기존의 할 일 정보를 새로운 할 일 정보로 변경한다.
		
		System.out.println("기존의 할 일 정보를 새로운 할 일 정보로 변경한다. ( 구현 예정 )");
		
		// "방금 등록한 할 일을 보여주는" 오늘 페이지로 이동을 지시
		response.sendRedirect("http://localhost/todolist/index-today.html?date="+t_date);
		
		System.out.println("할 일 수정 기능 종료");
	}

}


