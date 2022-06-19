package todayAdd;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoInfo {
	private LocalDate date;
	private int projectId;
	private int todoId;
	private int memberId;
	private String title;
	private String contents;
	private LocalDateTime registerDate;
	
	public TodoInfo() {
		
	}
	
	public TodoInfo(LocalDate date, int projectId, String title, String contents) {
		this.date = date;
		this.projectId = projectId;
		this.title = title;
		this.contents = contents;
	}
	
	public TodoInfo(int todoId,LocalDate date, int projectId, String title, String contents) {
		this.todoId= todoId;
		this.date = date;
		this.projectId = projectId;
		this.title = title;
		this.contents = contents;
	}
	// Getter & Setter 생략
	
	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
	
	public int getMemberId() {
		return memberId;
	}

	

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public int getTodoId() {
		return todoId;
	}



	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}
	
	public LocalDate getDate() {
		return date;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}




