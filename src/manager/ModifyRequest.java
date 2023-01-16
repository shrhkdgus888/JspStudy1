package manager;
//jsp useBean이 동작하기 위해서 요청정보를 수정하는 폼(registForm)에서
//파라미터 이름으로 셋팅되는 5가지 이름(num,member(Id,PwOld,PwNew),nickName)이 동일해야만 찾아서 넣어줄 수 있기 때문에 요청정보를
//파라미터로 담을 수 있게 ModifyRequest클래스를 선언함.
public class ModifyRequest {
	
	private int num;
	private String memberId;
	private String memberPwOld;
	private String memberPwNew;
	private String nickName;
	
	// Alt+Shift+S → R
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPwOld() {
		return memberPwOld;
	}
	public void setMemberPwOld(String memberPwOld) {
		this.memberPwOld = memberPwOld;
	}
	public String getMemberPwNew() {
		return memberPwNew;
	}
	public void setMemberPwNew(String memberPwNew) {
		this.memberPwNew = memberPwNew;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}
