package join;

public class MemberInfo {
	private int memberNumber;
	private String id;
	private String pw;
	private String name;
	
	public MemberInfo(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	public MemberInfo(String id, String pw, String name) {
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	
	/* Getter & Setter 생략 */
	
	public int getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
