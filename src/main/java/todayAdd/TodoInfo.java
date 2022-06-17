package todayAdd;

import java.time.LocalDate;

public class TodoInfo {
	private LocalDate date;
	private int projectId;
	private String title;
	private String contents;
	
	public TodoInfo(LocalDate date, int projectId, String title, String contents) {
		this.date = date;
		this.projectId = projectId;
		this.title = title;
		this.contents = contents;
	}
	
	// Getter & Setter 생략

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




