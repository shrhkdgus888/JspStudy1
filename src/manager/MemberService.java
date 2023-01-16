package manager;

import java.sql.SQLException;
import java.util.List;

public class MemberService {
	//MemberService를 참조할 수 있는 instance 생성
	private static MemberService instance;
	private MemberDao memberDao;
	//MemberService의 생성자로 memberDao에 담도록.
	private MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	//MemberService를 반환해주는 getInstance메서드 정의
	public static MemberService getInstance() {
		synchronized(MemberService.class){
			if(instance == null) {
				instance = new MemberService(MemberDao.getInstance());
			}
		}
		return instance;
	}
	
	
	
	//1. 등록기능 / 서비스의 로직 예시(Dao클래스와 방식이 다르다.)
		//- Dao는 그냥 받으면 저장,목록반환.
		//- 서비스는 사용자가 요청한것이 무엇인가에 따라 그것에 따라 처리하는 것.
	//사용자가 서비스로 요청을 했다면 어떻게 할꺼냐?
	public boolean regist(MemberVo vo) 
	{
		

		//int ret 변수는 Dao에 insert한 값인데, 
		//Dao의 insertMember 메서드는 int값을 반환하며, 쿼리를 전송하는 역할을 한다.
		//만약 쿼리 전송이 성공했다면, 1행이 삽입이 성공하였다고 콘솔창에 결과를 나타내는데,
		//즉, ret == 1은 쿼리 전송에 성공하여 return을 한다는 뜻이고(=다시 원래화면으로)
		int ret = memberDao.insertMember(vo);
			if(ret == 1){
			return true;
			//나머지 실패하였다면, catch(SQLException e) {e.printStackTrace();}에 의해 에러가 뜨게 된다.
			}
			return false;
	}
	
	//2. 조회기능
	public MemberVo read(int num) {
		return memberDao.selectMember(num);
	}
	
	//3. 전체조회기능
	public List<MemberVo> listAll(){
		return memberDao.selectMemberAll();
	}
	//4. 수정
		//검색했던 기존멤버(searchMember)의 기존 패스워드(getMemberPw)랑 전달받은 vo에 있던 패스워드(vo.getMemberPw())가
		//동일하다면 변경해라. 동일하지않다면 변경하지 마라. 성공과 실패 여부를 알려주기 위해 자료반환형은 void가 아닌 boolean으로!
	public boolean edit(MemberVo vo, String memberPwOld) {
		//MemberDao의 Update 메서드 중 업데이트가 성공하면 result값이 1이 되므로, 
		//단순히 1(업데이트 성공)과 -1(업데이트 실패)를 구분하기 위해 변수를 -1로 설정해놓는것.
		int result = -1;
		//modify.jsp 관련 순서
		//1. vo, memberPwOld 를 받아서 (public void edit(MemberVo vo, String memberPwOld)) 
		//2. memberDao에(memberDao.)
		//3. 해당 번호를 가지고 와서(selectMember(vo.getNum());)
		//4. searchMember 검색된 멤버 객체(기존멤버정보)에 넣어준다.
		MemberVo searchMember = memberDao.selectMember(vo.getNum());
		//5. searchMember(기존멤버정보)에서 가져온 getMemberPw 정보와 현재 전달받은 memberPwOld와 같다면,
		if(searchMember.getMemberPw().equals(memberPwOld)) {
		//6. (memberPwNew(새로운 비밀번호)=vo)로 업데이트(수정)를 해라.
			result = memberDao.updateMember(vo); //result값이 -1인데, updateMember 메서드에서 업데이트가 성공했다면 1로 변함.
		//7.맞지 않다면 하지마라. 
		}
		//return할때, result가 1(업데이트 성공)과 같다면 true, 같지 않다면(업데이트 실패) false
		return(result == 1) ? true : false;
	}
	//5. 탈퇴
	public boolean remove(int num) {
		int ret = memberDao.deleteMember(num);
		//삼항연산자 사용
		//ret 값이 1일때,(=1행이 삭제되었을 때) true, 아니면 false
		return (ret == 1) ? true : false;
	}
	
}
