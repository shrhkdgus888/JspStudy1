package manager;
//jsp useBean�� �����ϱ� ���ؼ� ��û������ �����ϴ� ��(registForm)����
//�Ķ���� �̸����� ���õǴ� 5���� �̸�(num,member(Id,PwOld,PwNew),nickName)�� �����ؾ߸� ã�Ƽ� �־��� �� �ֱ� ������ ��û������
//�Ķ���ͷ� ���� �� �ְ� ModifyRequestŬ������ ������.
public class ModifyRequest {
	
	private int num;
	private String memberId;
	private String memberPwOld;
	private String memberPwNew;
	private String nickName;
	
	// Alt+Shift+S �� R
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
