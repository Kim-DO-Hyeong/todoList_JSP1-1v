package projectAdd;

import java.time.LocalDateTime;

public class ProjectInfo {
	private int projectNumber;
	private String projectName;
	private LocalDateTime registerDate;
	
	public ProjectInfo(String projectName) {
		this.projectName = projectName;
	}
	
	public ProjectInfo(String projectName, LocalDateTime registerDate) {
		this.projectName = projectName;
		this.registerDate = registerDate;
	}
	
	public ProjectInfo(int projectNumber,String projectName, LocalDateTime registerDate) {
		this.projectNumber = projectNumber;
		this.projectName = projectName;
		this.registerDate = registerDate;
	}
	
	// Getter & Setter 생략

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	public int getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}
	
	
}
