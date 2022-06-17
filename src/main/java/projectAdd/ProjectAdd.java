package projectAdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/project/add")
public class ProjectAdd extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("프로젝트 추가 기능 시작");
		
		request.setCharacterEncoding("UTF-8");
		
		// 전달 받은 값을 꺼낸다.
		String projectName = request.getParameter("projectName");
		
		System.out.println("projectName = " + projectName);
		
		// 전달 받은 값을 하나의 프로젝트 정보로 구성한다.
		ProjectInfo newProjectInfo = new ProjectInfo(projectName);
		
		System.out.println("전달 받은 값을 하나의 프로젝트 정보로 구성");
		
		// 프로젝트 정보를 DB에 저장한다.
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 프로젝트 정보를 저장하는 테이블에 프로젝트 정보를 저장한다.
		
		// 프로젝트 페이지로 이동을 지시
		response.sendRedirect("http://localhost/todolist/index-project.html");
		
		System.out.println("프로젝트 추가 기능 종료");
	}

}


